package top.mingempty.cache.redis.entity.serializer;

import cn.hutool.core.util.ObjUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import top.mingempty.commons.util.JsonUtil;

/**
 * 重新序列化方式，如果为字符串，则直接进行返回
 *
 * @author zzhao
 */
public class MeJackson2JsonRedisSerializer<T> extends Jackson2JsonRedisSerializer<T> {
    private final ObjectMapper redisObjectMapper;

    public MeJackson2JsonRedisSerializer(ObjectMapper mapper, Class<T> type) {
        super(mapper, type);
        this.redisObjectMapper = mapper;
    }

    @Override
    public byte[] serialize(T value) throws SerializationException {
        if (value instanceof String) {
            return ((String) value).getBytes();
        }
        return super.serialize(value);
    }


    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ObjUtil.isEmpty(bytes)) {
            return null;
        }

        // 只要不是json类型的直接返回
        String str = new String(bytes);
        if (JsonUtil.isJson(redisObjectMapper, str)) {
            // 只要是JSON，就进行序列化
            return super.deserialize(bytes);
        }

        // 否则按照默认逻辑进行反序列化
        return (T) str;
    }
}
