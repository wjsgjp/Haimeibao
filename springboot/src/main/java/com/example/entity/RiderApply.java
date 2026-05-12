package com.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RiderApply {
    private Integer id;
    private Integer userId;
    private String name;
    private String sex;
    private String college;
    private String majorClass;
    private byte[] studentCard;
    private byte[] healthCard;
    private String status;
    private Date createTime;
} 