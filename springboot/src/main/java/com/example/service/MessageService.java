package com.example.service;

import com.example.entity.Message;
import com.example.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService {

    @Resource
    private MessageMapper messageMapper;

    /**
     * 发送消息
     */
    public void sendMessage(Message message) {
        messageMapper.insert(message);
    }

    /**
     * 获取聊天记录
     */
    public List<Message> getChatHistory(Integer userId1, Integer userId2) {
        return messageMapper.selectByUsers(userId1, userId2);
    }
} 