package com.neticon.neticon.repository.mybatis.mapper;

import com.neticon.neticon.common.domain.RoomInfo;
import com.neticon.neticon.common.domain.RoomInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RoomInfoMapper {
    int countByExample(RoomInfoExample example);

    int deleteByExample(RoomInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoomInfo record);

    int insertSelective(RoomInfo record);

    List<RoomInfo> selectByExampleWithRowbounds(RoomInfoExample example, RowBounds rowBounds);

    List<RoomInfo> selectByExample(RoomInfoExample example);

    RoomInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoomInfo record, @Param("example") RoomInfoExample example);

    int updateByExample(@Param("record") RoomInfo record, @Param("example") RoomInfoExample example);

    int updateByPrimaryKeySelective(RoomInfo record);

    int updateByPrimaryKey(RoomInfo record);
}