package top.mingempty.event.record.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.event.record.model.ListenerRecordExample;
import top.mingempty.event.record.model.ListenerRecordPo;

import java.util.List;

@Mapper
public interface ListenerRecordMapper {
    long countByExample(ListenerRecordExample example);

    int deleteByExample(ListenerRecordExample example);

    int deleteByPrimaryKey(String eventListenerId);

    int insert(ListenerRecordPo row);

    int insertSelective(ListenerRecordPo row);

    List<ListenerRecordPo> selectByExample(ListenerRecordExample example);

    ListenerRecordPo selectByPrimaryKey(String eventListenerId);

    int updateByExampleSelective(@Param("row") ListenerRecordPo row, @Param("example") ListenerRecordExample example);

    int updateByExample(@Param("row") ListenerRecordPo row, @Param("example") ListenerRecordExample example);

    int updateByPrimaryKeySelective(ListenerRecordPo row);

    int updateByPrimaryKey(ListenerRecordPo row);

    int batchDelete(@Param("eventListenerIdS") List<String> eventListenerIdS);

    int batchInsert(@Param("recordList") List<ListenerRecordPo> recordList);
}