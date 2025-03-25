package top.mingempty.sequence.enums;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 序号实现机制枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "序号实现机制枚举")
public enum SeqRealizeEnum {


    /**
     * Redis
     */
    @Schema(title = "redis")
    Redis("SEQUENCE:", ":"),

    /**
     * zookeeper
     */
    @Schema(title = "Zookeeper")
    Zookeeper("/SEQUENCE/", "/"),

    /**
     * 数据库
     */
    @Schema(title = "数据库")
    Database("", "|"),

    ;


    /**
     * 分隔符
     */
    @Schema(title = "分隔符")
    private final String separator;

    /**
     * 前缀
     */
    @Schema(title = "前缀")
    private final String keyPrefix;

    SeqRealizeEnum(String keyPrefix, String separator) {
        this.separator = separator;
        this.keyPrefix = keyPrefix;
    }

    /**
     * 初始化名称
     *
     * @param seqName
     * @return
     */
    public String intiSeqName(String seqName) {
        return this.getKeyPrefix().concat(seqName);
    }
}
