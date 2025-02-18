package top.mingempty.event.record.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.event.record.model.PublisherRecordExample;
import top.mingempty.event.record.model.PublisherRecordPo;

import java.util.List;

@Mapper
public interface PublisherRecordMapper {
    long countByExample(PublisherRecordExample example);

    int deleteByExample(PublisherRecordExample example);

    int deleteByPrimaryKey(String eventId);

    int insert(PublisherRecordPo row);

    int insertSelective(PublisherRecordPo row);

    List<PublisherRecordPo> selectByExampleWithBLOBs(PublisherRecordExample example);

    List<PublisherRecordPo> selectByExample(PublisherRecordExample example);

    PublisherRecordPo selectByPrimaryKey(String eventId);

    int updateByExampleSelective(@Param("row") PublisherRecordPo row, @Param("example") PublisherRecordExample example);

    int updateByExampleWithBLOBs(@Param("row") PublisherRecordPo row, @Param("example") PublisherRecordExample example);

    int updateByExample(@Param("row") PublisherRecordPo row, @Param("example") PublisherRecordExample example);

    int updateByPrimaryKeySelective(PublisherRecordPo row);

    int updateByPrimaryKeyWithBLOBs(PublisherRecordPo row);

    int updateByPrimaryKey(PublisherRecordPo row);

    int batchDelete(@Param("eventIdS") List<String> eventIdS);

    int batchInsert(@Param("recordList") List<PublisherRecordPo> recordList);
}