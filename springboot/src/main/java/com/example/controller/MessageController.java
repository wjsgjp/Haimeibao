package com.example.controller;

import com.example.common.Result;
import com.example.entity.Message;
import com.example.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 发送消息
     */
    @PostMapping("/send")
    public Result send(@RequestBody Message message) {
        try {
            messageService.sendMessage(message);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "发送消息失败：" + e.getMessage());
        }
    }

    /**
     * 获取聊天记录
     */
    @GetMapping("/chat/{userId1}/{userId2}")
    public Result getChatHistory(@PathVariable Integer userId1, @PathVariable Integer userId2) {
        try {
            List<Message> messages = messageService.getChatHistory(userId1, userId2);
            return Result.success(messages);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "获取聊天记录失败：" + e.getMessage());
        }
    }
} 