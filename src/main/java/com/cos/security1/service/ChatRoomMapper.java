package com.cos.security1.service;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ChatRoomMapper {
    @Update("UPDATE count_table SET count = 0")
    void resetUserCount();
}
