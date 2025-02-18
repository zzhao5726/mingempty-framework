package top.mingempty.concurrent.record.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.concurrent.record.model.TaskExample;
import top.mingempty.concurrent.record.model.TaskPo;

import java.util.List;

@Mapper
public interface TaskMapper {
    long countByExample(TaskExample example);

    int deleteByExample(TaskExample example);

    int deleteByPrimaryKey(String taskId);

    int insert(TaskPo row);

    int insertSelective(TaskPo row);

    List<TaskPo> selectByExampleWithBLOBs(TaskExample example);

    List<TaskPo> selectByExample(TaskExample example);

    TaskPo selectByPrimaryKey(String taskId);

    int updateByExampleSelective(@Param("row") TaskPo row, @Param("example") TaskExample example);

    int updateByExampleWithBLOBs(@Param("row") TaskPo row, @Param("example") TaskExample example);

    int updateByExample(@Param("row") TaskPo row, @Param("example") TaskExample example);

    int updateByPrimaryKeySelective(TaskPo row);

    int updateByPrimaryKeyWithBLOBs(TaskPo row);

    int updateByPrimaryKey(TaskPo row);

    int batchDelete(@Param("taskIdS") List<String> taskIdS);

    int batchInsert(@Param("recordList") List<TaskPo> recordList);
}