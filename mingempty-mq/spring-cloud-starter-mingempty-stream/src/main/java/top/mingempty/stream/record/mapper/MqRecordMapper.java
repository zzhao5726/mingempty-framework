package top.mingempty.stream.record.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mingempty.stream.record.model.MqRecordExample;
import top.mingempty.stream.record.model.MqRecordPo;

import java.util.List;

@Mapper
public interface MqRecordMapper {
    long countByExample(MqRecordExample example);

    int deleteByExample(MqRecordExample example);

    int deleteByPrimaryKey(String mqId);

    int insert(MqRecordPo row);

    int insertSelective(MqRecordPo row);

    List<MqRecordPo> selectByExampleWithBLOBs(MqRecordExample example);

    List<MqRecordPo> selectByExample(MqRecordExample example);

    MqRecordPo selectByPrimaryKey(String mqId);

    int updateByExampleSelective(@Param("row") MqRecordPo row, @Param("example") MqRecordExample example);

    int updateByExampleWithBLOBs(@Param("row") MqRecordPo row, @Param("example") MqRecordExample example);

    int updateByExample(@Param("row") MqRecordPo row, @Param("example") MqRecordExample example);

    int updateByPrimaryKeySelective(MqRecordPo row);

    int updateByPrimaryKeyWithBLOBs(MqRecordPo row);

    int updateByPrimaryKey(MqRecordPo row);

    int batchDelete(@Param("mqIdS") List<String> mqIdS);

    int batchInsert(@Param("recordList") List<MqRecordPo> recordList);
}