package top.mingempty.event.record.model;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 基于事务的事件
 */
@Getter
public abstract class MeRecordApplicationEvent
        extends ApplicationEvent {

    /**
     * 事件唯一ID
     */
    @NotNull
    private final String eventId;

    /**
     * 事件类型
     */
    @NotNull
    private final String eventType;

    /**
     * 业务号码
     */
    @Setter
    private String bizNo = "";

    public MeRecordApplicationEvent() {
        this("txEvent");
    }

    public MeRecordApplicationEvent(Object source) {
        this(source, Clock.systemDefaultZone());
    }


    public MeRecordApplicationEvent(Object source, Clock clock) {
        this("def", source, clock);
    }

    public MeRecordApplicationEvent(String eventType, Object source) {
        this(eventType, source, Clock.systemDefaultZone());
    }


    public MeRecordApplicationEvent(String eventType, Object source, Clock clock) {
        this(UUID.randomUUID().toString(true), eventType, source, clock);
    }

    public MeRecordApplicationEvent(String eventId, String eventType, Object source) {
        this(eventId, eventType, source, Clock.systemDefaultZone());
    }

    public MeRecordApplicationEvent(String eventId, String eventType, Object source, Clock clock) {
        super(source, clock);
        Assert.notEmpty(eventId, "transactional event id can not be empty");
        Assert.notEmpty(eventType, "transactional event type can not be empty");
        this.eventId = eventId;
        this.eventType = eventType;
    }

}
