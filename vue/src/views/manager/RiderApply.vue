<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入姓名" style="width: 200px" v-model="params.name" ></el-input>
      <el-select v-model="params.sex" placeholder="性别" style="width: 120px; margin-left: 10px" >
        <el-option label="男" value="男"></el-option>
        <el-option label="女" value="女"></el-option>
      </el-select>
      <el-select v-model="params.status" placeholder="状态" style="width: 120px; margin-left: 10px" >
        <el-option label="审核中" value="审核中"></el-option>
        <el-option label="已通过" value="已通过"></el-option>
        <el-option label="已拒绝" value="已拒绝"></el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 10px" @click="load" >查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="reset" >重置</el-button>
    </div>

    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" align="center"></el-table-column>
      <el-table-column prop="sex" label="性别" width="80" align="center"></el-table-column>
      <el-table-column prop="college" label="学院" align="center"></el-table-column>
      <el-table-column prop="majorClass" label="班级" align="center"></el-table-column>
      <el-table-column prop="status" label="状态" align="center">
        <template v-slot="scope">
          <el-tag type="warning" v-if="scope.row.status === '审核中'">审核中</el-tag>
          <el-tag type="success" v-if="scope.row.status === '已通过'">已通过</el-tag>
          <el-tag type="danger" v-if="scope.row.status === '已拒绝'">已拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="申请时间" align="center"></el-table-column>
      <el-table-column label="操作" width="280" align="center">
        <template v-slot="scope">
          <el-button type="primary" size="mini" @click="viewImages(scope.row)">查看证件</el-button>
          <el-button type="success" size="mini" @click="handleAudit(scope.row, '通过')" 
                     v-if="scope.row.status === '审核中'">通过</el-button>
          <el-button type="danger" size="mini" @click="handleAudit(scope.row, '拒绝')" 
                     v-if="scope.row.status === '审核中'">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 图片查看对话框 -->
    <el-dialog title="证件图片" :visible.sync="imageDialogVisible" width="60%">
      <div style="text-align: center">
        <div class="image-container">
          <h3>学生证</h3>
          <el-image 
            style="width: 400px" 
            :src="images.studentCard" 
            :preview-src-list="[images.studentCard]">
          </el-image>
        </div>
        <div class="image-container">
          <h3>健康证</h3>
          <el-image 
            style="width: 400px" 
            :src="images.healthCard" 
            :preview-src-list="[images.healthCard]">
          </el-image>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'RiderApply',
  data() {
    return {
      params: {
        name: '',
        sex: '',
        college: '',
        status: ''
      },
      tableData: [],
      imageDialogVisible: false,
      images: {
        studentCard: '',
        healthCard: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      // 构建查询参数，只包含有值的字段
      const queryParams = {}
      if (this.params.name) queryParams.name = this.params.name
      if (this.params.sex) queryParams.sex = this.params.sex
      if (this.params.status) queryParams.status = this.params.status

      console.log('发送的查询参数：', queryParams)  // 打印发送的查询参数

      this.$request.get('/riderApply/listWithoutImages', {
        params: queryParams
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data
          console.log('===== 获取到的骑手申请列表 =====')
          this.tableData.forEach((item, index) => {
            console.log(`申请${index + 1}:`, {
              id: item.id,
              姓名: item.name,
              性别: item.sex,
              学院: item.college,
              班级: item.majorClass,
              状态: item.status,
              申请时间: item.createTime
            })
          })
          console.log('================================')
        } else {
          console.log('请求失败：', res.msg)
        }
      }).catch(err => {
        console.log('请求错误：', err)
      })
    },
    reset() {
      this.params = {
        name: '',
        sex: '',
        college: '',
        status: ''
      }
      this.load()
    },
    viewImages(row) {
      this.$request.get(`/riderApply/getImages/${row.id}`).then(res => {
        if (res.code === '200') {
          this.images = res.data
          console.log('获取到的证件图片：', this.images)
          this.imageDialogVisible = true
        }
      })
    },
    handleAudit(row, status) {
      // 将中文状态转换为API所需的英文状态
      const apiStatus = status === '通过' ? 'pass' : 'reject'
      
      this.$confirm(`确认${status}该申请吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log('准备审核的申请：', row)
        console.log('审核状态：', apiStatus)
        
        // 修改为PUT请求，使用路径参数
        this.$request.put(`/riderApply/audit/${row.id}/${apiStatus}`).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功')
            this.load()  // 重新加载列表
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.search {
  margin-bottom: 20px;
}
.image-container {
  margin: 20px 0;
}
</style> 