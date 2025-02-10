package top.mingempty.domain.base;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 数据库实体逻辑删除基础类
 *
 * @author zzhao
 */
@Schema(title = "数据库实体逻辑删除基础类", description = "数据库实体逻辑删除基础类")
public interface BaseDeletePoModel extends BasePoModel {

    String getDeleteStatus();

    void setDeleteStatus(String deleteStatus);

    LocalDateTime getDeleteTime();

    void setDeleteTime(LocalDateTime deleteTime);

    String getDeleteOperator();

    void setDeleteOperator(String deleteOperator);
}
