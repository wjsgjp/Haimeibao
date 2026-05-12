### 1. 提交骑手申请
- **接口URL**: `/riderApply/apply`
- **请求方式**: POST
- **请求类型**: multipart/form-data
- **请求参数**:
  - name: 姓名
  - sex: 性别
  - college: 学院
  - majorClass: 专业班级
  - studentCard: 学生证图片文件
  - healthCard: 健康证图片文件
- **返回示例**:
json
{
"code": "200",
"msg": "操作成功",
"data": null
}

### 6. 获取图片
- **接口URL**: `/riderApply/image/{id}/{type}`
- **请求方式**: GET
- **请求参数**:
  - id: 申请记录ID
  - type: 图片类型(student/health)
- **返回格式**: image/jpeg

### 7. 获取申请列表(不含图片)
- **接口URL**: `/riderApply/listWithoutImages`
- **请求方式**: GET
- **请求参数**: 
  - userId: 用户ID (可选,Integer)
  - name: 姓名 (可选,String)
  - sex: 性别 (可选,String)
  - college: 学院 (可选,String)
  - status: 申请状态 (可选,String)
- **返回示例**:

json
{
"code": "200",
"msg": "操作成功",
"data": [
{
"id": 1,
"userId": 1,
"name": "张三",
"sex": "男",
"college": "计算机学院",
"majorClass": "软件工程2班",
"status": "审核中",
"createTime": "2024-01-01 12:00:00"
}
// ... 更多数据
]
}

获取申请的图片信息
- **接口URL**: `/riderApply/getImages/{id}`
- **请求方式**: GET
- **请求参数**: 
  - id: 申请记录ID (路径参数)
- **返回示例**:

json
{
"code": "200",
"msg": "操作成功",
"data": {
"studentCard": "base64编码的图片数据...",
"healthCard": "base64编码的图片数据..."
}
}

9. 审核骑手申请
- **接口URL**: `/riderApply/audit/{id}/{status}`
- **请求方式**: PUT
- **请求参数**: 
  - id: 申请记录ID (路径参数)
  - status: 审核状态 (路径参数)
    - pass: 通过
    - reject: 拒绝
- **返回示例**:
json
{
"code": "200",
"msg": "操作成功",
"data": null
}

13. 获取联系人列表
- **接口URL**: `/user/contacts/{userId}`
- **请求方式**: GET
- **请求参数**: 
  - userId: 用户ID (路径参数)
- **返回示例**:

json
{
"code": "200",
"msg": "操作成功",
"data": [
{
"id": 1,
"username": "商家1",
"name": "商家名称",
"role": "BUSINESS",
// ... 其他用户信息
}
// ... 更多用户
]
}

1. 发送消息
- **接口URL**: `/message/send`
- **请求方式**: POST
- **请求类型**: application/json
- **请求参数**:
{
"senderId": 1, // 发送者ID
"receiverId": 2, // 接收者ID
"content": "你好！" // 消息内容
}
- **返回示例**:
{
"code": "200",
"msg": "操作成功",
"data": null
}



### 2. 获取聊天记录
- **接口URL**: `/message/chat/{userId1}/{userId2}`
- **请求方式**: GET
- **请求参数**: 
  - userId1: 用户1的ID (路径参数)
  - userId2: 用户2的ID (路径参数)
- **返回示例**:
{
"code": "200",
"msg": "操作成功",
"data": [
{
"id": 1,
"senderId": 1,
"receiverId": 2,
"content": "你好！",
"createTime": "2024-01-01 12:00:00"
},
{
"id": 2,
"senderId": 2,
"receiverId": 1,
"content": "你也好！",
"createTime": "2024-01-01 12:01:00"
}
// ... 更多消息
]
}