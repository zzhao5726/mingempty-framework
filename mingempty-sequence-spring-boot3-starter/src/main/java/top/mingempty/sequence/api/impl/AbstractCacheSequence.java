package top.mingempty.sequence.api.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Pair;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.sequence.api.CacheSequence;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.exception.SequenceException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于队列的缓存序号生成器
 * <p>
 * 需要保证{@link AbstractCacheSequence#max()}方法获取数据的准确的，否则会造成数据重复等问题
 * <p>
 * 性能依赖于{@link AbstractCacheSequence#max()}方法的实现以及步长{@link AbstractCacheSequence#step()}的大小。
 *
 * @author zzhao
 */
@Slf4j
public abstract class AbstractCacheSequence implements CacheSequence<Long> {
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newThreadPerTaskExecutor(Thread
            .ofVirtual()
            .name("Seq-Cache-Virtual-Thread-", 0)
            .factory());

    /**
     * 读写锁
     * 保证队列线程安全
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 序号类型
     */
    private final SeqRealizeEnum seqRealize;

    /**
     * 序号名称
     */
    private volatile String seqName;

    /**
     * 步长
     */
    private volatile int step;

    /**
     * 定义队列
     */
    private volatile ArrayBlockingQueue<Long> arrayBlockingQueue;

    protected AbstractCacheSequence(SeqRealizeEnum seqRealize, String seqName) {
        this(seqRealize, seqName, 50);
    }

    protected AbstractCacheSequence(SeqRealizeEnum seqRealize, String seqName, int step) {
        this.step = Assert.checkBetween(step, 1, Integer.MAX_VALUE, "The step size must be greater than zero");
        this.arrayBlockingQueue = new ArrayBlockingQueue<>(step * 2);
        this.seqRealize = Assert.notNull(seqRealize, "seqRealize can not be empty");
        this.seqName = Assert.notEmpty(seqName, "seqName can not be empty");
    }

    protected void init() {
        EXECUTOR_SERVICE.submit(() -> {
            try {
                updateCache();
            } finally {
                readWriteLock.readLock().unlock();
            }
        });
    }

    /**
     * 下一个序号
     */
    @Override
    public Long next() {
        try {
            if (step() == 1) {
                return next2();
            }
            readWriteLock.readLock().lock();
            Long sequence = arrayBlockingQueue.poll();
            if (sequence != null) {
                return sequence;
            }
            readWriteLock.readLock().unlock();
            int updateCache = updateCache();
            if (updateCache > 0) {
                return arrayBlockingQueue.poll();
            }
            throw new SequenceException("seq00000001");
        } catch (Exception e) {
            throw new SequenceException("seq00000001", e);
        } finally {
            readWriteLock.readLock().unlock();
            init();
        }
    }

    /**
     * 优化，当步长为1时，直接获取并返回
     */
    private Long next2() {
        try {
            readWriteLock.writeLock().lock();
            Long sequenceByNew = arrayBlockingQueue.poll();
            if (sequenceByNew != null) {
                return sequenceByNew;
            }
            Pair<Long, Integer> max = max();
            if (max.getValue() == 1) {
                return max.getKey();
            }
            updateQueue(max);
            return arrayBlockingQueue.poll();
        } catch (Exception e) {
            log.error("序号[{}]更新缓存异常", seqName, e);
            return null;
        } finally {
            //返回前一定给加一把锁
            readWriteLock.readLock().lock();
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 更新数据到队列
     *
     * @param max
     */
    private void updateQueue(Pair<Long, Integer> max) {
        if (max.getValue() > step) {
            //只有新的步长大于旧的步长时，才需要重置队列
            List<Long> oldSeq = new ArrayList<>();
            arrayBlockingQueue.drainTo(oldSeq);
            arrayBlockingQueue = new ArrayBlockingQueue<>(max.getValue() * 2, false, oldSeq);
        }
        changeStep(max.getValue());
        for (long i = max.getKey() - max.getValue() + 1; i <= max.getKey(); i++) {
            arrayBlockingQueue.add(i);
        }
    }


    /**
     * 步长取值
     */
    @Override
    public int step() {
        return step;
    }

    /**
     * 当前缓存大小
     */
    @Override
    public int cacheSize() {
        try {
            readWriteLock.readLock().lock();
            return arrayBlockingQueue.size();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 更新缓存值
     * <p>
     * 当返回值为0时，表示缓存更新失败
     * 当返回值为1时，表示更新缓存成功
     * 当返回值为2时，表示无需更新缓存
     */
    @Override
    public int updateCache() {
        if (!isUpdateStep()) {
            //返回前一定给加一把锁
            readWriteLock.readLock().lock();
            return 2;
        }
        try {
            readWriteLock.writeLock().lock();
            if (!isUpdateStep()) {
                return 2;
            }
            updateQueue(max());
            return 1;
        } catch (Exception e) {
            log.error("序号[{}]更新缓存异常", seqName, e);
            return 0;
        } finally {
            //返回前一定给加一把锁
            readWriteLock.readLock().lock();
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 获取新的最大值和步长
     * <p>
     * key：获取到的最大值
     * value：步长
     */
    protected abstract Pair<Long, Integer> max();


    /**
     * 获取当前的序号名称
     */
    public String seqName() {
        return seqName;
    }


    /**
     * 获取当前的key
     */
    public String key() {
        return seqRealize.intiSeqName(seqName);
    }

    /**
     * 设置一个新的序号名称
     *
     * @param seqName
     */
    public void changeSeqName(String seqName) {
        this.seqName = seqName;
    }

    /**
     * 设置一个新的步长
     * 当步长小于等于0时，默认设置为10
     *
     * @param step 步长
     */
    public void changeStep(int step) {
        this.step = step <= 0 ? 10 : step;
    }


    /**
     * 序号实现机制
     */
    public abstract SeqRealizeEnum seqRealize();
}
