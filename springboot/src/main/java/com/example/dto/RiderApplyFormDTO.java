package com.example.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RiderApplyFormDTO {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 申请人姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业班级
     */
    private String majorClass;

    /**
     * 学生证照片
     */
    private MultipartFile studentCard;

    /**
     * 健康证照片
     */
    private MultipartFile healthCard;
} 