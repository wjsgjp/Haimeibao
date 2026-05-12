package com.example.mapper;

import com.example.entity.Message;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MessageMapper {
    /**
     * 插入消息
     */
    int insert(Message message);

    /**
     * 查询两个用户之间的聊天记录
     */
    List<Message> selectByUsers(@Param("userId1") Integer userId1, @Param("userId2") Integer userId2);

    /**
     * 查询与指定用户有过聊天记录的用户ID列表
     */
    List<Integer> selectContactUserIds(@Param("userId") Integer userId);
} 