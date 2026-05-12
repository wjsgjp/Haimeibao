package com.example.controller;

import com.example.common.Result;
import com.example.dto.RiderApplyDTO;
import com.example.dto.RiderApplyFormDTO;
import com.example.entity.RiderApply;
import com.example.service.RiderApplyService;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.util.HashMap;

/**
 * 骑手申请相关接口
 */
@RestController
@RequestMapping("/riderApply")
@CrossOrigin
public class RiderApplyController {

    @Resource
    private RiderApplyService riderApplyService;

    @Resource
    private UserService userService;

    /**
     * 提交骑手申请
     */
    @PostMapping("/apply")
    @CrossOrigin
    public Result apply(@RequestBody RiderApplyFormDTO dto) {
        try {
            System.out.println("开始处理骑手申请..." + dto.getName());
            
            // 创建RiderApply对象并设置属性
            RiderApply riderApply = new RiderApply();
            riderApply.setUserId(dto.getUserId());
            riderApply.setName(dto.getName());
            riderApply.setSex(dto.getSex());
            riderApply.setCollege(dto.getCollege());
            riderApply.setMajorClass(dto.getMajorClass());
            
            if (dto.getStudentCard() != null) {
                riderApply.setStudentCard(dto.getStudentCard().getBytes());
            }
            if (dto.getHealthCard() != null) {
                riderApply.setHealthCard(dto.getHealthCard().getBytes());
            }
            
            // 设置初始状态为审核中
            riderApply.setStatus("审核中");
            
            riderApplyService.add(riderApply);
            System.out.println("骑手申请处理完成");
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "申请提交失败：" + e.getMessage());
        }
    }

    /**
     * 获取图片
     */
    @GetMapping("/image/{id}/{type}")
    public void getImage(@PathVariable Integer id, 
                        @PathVariable String type,
                        HttpServletResponse response) throws IOException {
        RiderApply riderApply = riderApplyService.selectById(id);
        byte[] imageData;
        
        if ("student".equals(type)) {
            imageData = riderApply.getStudentCard();
        } else if ("health".equals(type)) {
            imageData = riderApply.getHealthCard();
        } else {
            return;
        }
        
        response.setContentType("image/jpeg");
        response.getOutputStream().write(imageData);
    }

    /**
     * 查询申请列表
     */
    @GetMapping("/list")
    public Result list(RiderApply riderApply) {
        List<RiderApply> list = riderApplyService.selectAll(riderApply);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result page(RiderApply riderApply,
                      @RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<RiderApply> page = riderApplyService.selectPage(riderApply, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 审核申请
     * @param id 申请ID
     * @param status 审核状态 (pass-通过, reject-拒绝)
     */
    @PutMapping("/audit/{id}/{status}")
    public Result audit(@PathVariable Integer id, @PathVariable String status) {
        try {
            // 1. 查询申请记录
            RiderApply riderApply = riderApplyService.selectById(id);
            if (riderApply == null) {
                return Result.error("404", "未找到对应的申请记录");
            }

            // 2. 更新申请状态
            RiderApply updateApply = new RiderApply();
            updateApply.setId(id);
            
            if ("pass".equals(status)) {
                updateApply.setStatus("已通过");
                // 更新用户角色为骑手
                userService.updateUserRole(riderApply.getUserId(), "RIDER");
            } else if ("reject".equals(status)) {
                updateApply.setStatus("已拒绝");
            } else {
                return Result.error("400", "无效的审核状态");
            }
            
            riderApplyService.updateById(updateApply);
            return Result.success();
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "审核失败：" + e.getMessage());
        }
    }

    /**
     * 查询申请详情
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        RiderApply riderApply = riderApplyService.selectById(id);
        return Result.success(riderApply);
    }

    /**
     * 获取申请列表(不含图片数据)
     */
    @GetMapping("/listWithoutImages")
    public Result listWithoutImages(RiderApply riderApply) {

        List<RiderApply> list = riderApplyService.selectAll(riderApply);
        System.out.println("获取列表数据："+list.size());
        // 清空图片数据
        for (RiderApply apply : list) {
            apply.setStudentCard(null);
            apply.setHealthCard(null);
            System.out.println("ID: " + apply.getId());
            System.out.println("用户ID: " + apply.getUserId());
            System.out.println("姓名: " + apply.getName());
            System.out.println("性别: " + apply.getSex());
            System.out.println("学院: " + apply.getCollege());
            System.out.println("专业班级: " + apply.getMajorClass());
            System.out.println("状态: " + apply.getStatus());
            System.out.println("创建时间: " + apply.getCreateTime());
            System.out.println("------------------------");
        }
        return Result.success(list);
    }

    /**
     * 获取申请的图片信息
     */
    @GetMapping("/getImages/{id}")
    public Result getImages(@PathVariable Integer id) {
        try {
            RiderApply riderApply = riderApplyService.selectById(id);
            if (riderApply == null) {
                return Result.error("404", "未找到对应的申请记录");
            }
            
            // 创建返回的数据结构
            Map<String, Object> imageInfo = new HashMap<>();
            
            // 如果有学生证图片，转换为Base64
            if (riderApply.getStudentCard() != null) {
                String studentCardBase64 = Base64.getEncoder().encodeToString(riderApply.getStudentCard());
                imageInfo.put("studentCard", studentCardBase64);
            }
            
            // 如果有健康证图片，转换为Base64
            if (riderApply.getHealthCard() != null) {
                String healthCardBase64 = Base64.getEncoder().encodeToString(riderApply.getHealthCard());
                imageInfo.put("healthCard", healthCardBase64);
            }
            
            return Result.success(imageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "获取图片信息失败：" + e.getMessage());
        }
    }

    /**
     * 提交骑手申请（使用Base64图片）
     */
    @PostMapping("/applyWithBase64")
    @CrossOrigin
    public Result applyWithBase64(@RequestBody RiderApplyDTO dto) {
        try {
            System.out.println("开始处理骑手申请(Base64)..." + dto.getName());
            
            // 创建RiderApply对象并设置属性
            RiderApply riderApply = new RiderApply();
            riderApply.setUserId(dto.getUserId());
            riderApply.setName(dto.getName());
            riderApply.setSex(dto.getSex());
            riderApply.setCollege(dto.getCollege());
            riderApply.setMajorClass(dto.getMajorClass());
            
            // 转换Base64字符串为字节数组
            if (dto.getStudentCard() != null && !dto.getStudentCard().isEmpty()) {
                String base64Image = dto.getStudentCard();
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                riderApply.setStudentCard(Base64.getDecoder().decode(base64Image));
            }
            
            if (dto.getHealthCard() != null && !dto.getHealthCard().isEmpty()) {
                String base64Image = dto.getHealthCard();
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                riderApply.setHealthCard(Base64.getDecoder().decode(base64Image));
            }
            
            // 设置初始状态为审核中
            riderApply.setStatus("审核中");
            
            riderApplyService.add(riderApply);
            System.out.println("骑手申请处理完成");
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("500", "申请提交失败：" + e.getMessage());
        }
    }
} 