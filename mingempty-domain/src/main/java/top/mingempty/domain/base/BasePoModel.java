package top.mingempty.domain.base;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据库实体基础类
 *
 * @author zzhao
 */
@Schema(title = "数据库实体基础类", description = "数据库实体基础类")
public interface BasePoModel extends Serializable {

    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

    LocalDateTime getUpdateTime();

    void setUpdateTime(LocalDateTime updateTime);

    String getCreateOperator();

    void setCreateOperator(String createOperator);

    String getUpdateOperator();

    void setUpdateOperator(String updateOperator);
}
