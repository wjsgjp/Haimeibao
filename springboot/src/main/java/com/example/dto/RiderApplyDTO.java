package com.example.dto;

import lombok.Data;

@Data
public class RiderApplyDTO {
    private Integer userId;
    private String name;
    private String sex;
    private String college;
    private String majorClass;
    private String studentCard;  // base64 字符串
    private String healthCard;   // base64 字符串
} 