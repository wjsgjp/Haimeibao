package com.example.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Message {
    /**
     * 消息ID
     */
    private Integer id;

    /**
     * 发送者ID
     */
    private Integer senderId;

    /**
     * 接收者ID
     */
    private Integer receiverId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date createTime;
} 