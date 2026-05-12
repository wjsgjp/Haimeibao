<template>
	<view class="container">
		<uni-forms ref="formRef" :modelValue="formData" :rules="rules">
			<uni-forms-item label="姓名" name="name">
				<uni-easyinput v-model="formData.name" placeholder="请输入姓名" />
			</uni-forms-item>
			
			<uni-forms-item label="性别" name="sex">
				<uni-data-checkbox v-model="formData.sex" :localdata="sexOptions" />
			</uni-forms-item>
			
			<uni-forms-item label="学院" name="college">
				<uni-easyinput v-model="formData.college" placeholder="请输入学院" />
			</uni-forms-item>
			
			<uni-forms-item label="专业班级" name="majorClass">
				<uni-easyinput v-model="formData.majorClass" placeholder="请输入专业班级" />
			</uni-forms-item>
			
			<uni-forms-item label="学生证照片" name="studentCard">
				<uni-file-picker 
					v-model="formData.studentCard" 
					file-mediatype="image"
					mode="grid"
					@select="selectStudentCard"
					:limit="1"
					:image-styles="imageStyles"
					title="最多选择1张图片"
				/>
			</uni-forms-item>
			
			<uni-forms-item label="健康证照片" name="healthCard">
				<uni-file-picker 
					v-model="formData.healthCard" 
					file-mediatype="image"
					mode="grid"
					@select="selectHealthCard"
					:limit="1"
					:image-styles="imageStyles"
					title="最多选择1张图片"
				/>
			</uni-forms-item>
		</uni-forms>
		
		<button class="submit-btn" @click="submitForm">提交申请</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				formData: {
					name: '',
					sex: '',
					college: '',
					majorClass: '',
					studentCard: [],
					healthCard: []
				},
				sexOptions: [{
					text: '男',
					value: '男'
				}, {
					text: '女',
					value: '女'
				}],
				rules: {
					name: {
						rules: [{
							required: true,
							errorMessage: '请输入姓名'
						}]
					},
					sex: {
						rules: [{
							required: true,
							errorMessage: '请选择性别'
						}]
					},
					college: {
						rules: [{
							required: true,
							errorMessage: '请输入学院'
						}]
					},
					majorClass: {
						rules: [{
							required: true,
							errorMessage: '请输入专业班级'
						}]
					},
					studentCard: {
						rules: [{
							validateFunction: (rule, value, data, callback) => {
								if (this.formData.studentCard.length === 0) {
									callback('请上传学生证照片')
								}
								return true
							}
						}]
					},
					healthCard: {
						rules: [{
							validateFunction: (rule, value, data, callback) => {
								if (this.formData.healthCard.length === 0) {
									callback('请上传健康证照片')
								}
								return true
							}
						}]
					}
				},
				imageStyles: {
					width: 200,
					height: 200,
					border: {
						radius: '5px'
					}
				},
				user: uni.getStorageSync('xm-user'),
				baseUrl: 'http://localhost:9090'
			}
		},
		methods: {
			// 添加图片压缩方法
			compressImage(tempFilePath) {
				return new Promise((resolve, reject) => {
					uni.compressImage({
						src: tempFilePath,
						quality: 80,
						success: res => {
							resolve(res.tempFilePath)
						},
						fail: err => {
							reject(err)
						}
					})
				})
			},
			
			// 添加图片转base64方法
			imageToBase64(filePath) {
				return new Promise((resolve, reject) => {
					uni.getFileSystemManager().readFile({
						filePath: filePath,
						encoding: 'base64',
						success: res => {
							resolve(res.data)
						},
						fail: err => {
							reject(err)
						}
					})
				})
			},
			
			// 修改提交表单方法
			async submitForm() {
				try {
					await this.$refs.formRef.validate()
					
					if (!this.formData.studentCard[0] || !this.formData.healthCard[0]) {
						uni.showToast({
							icon: 'error',
							title: '请选择所有必需的图片'
						})
						return
					}
					
					// 构建请求数据
					const requestData = {
						userId: this.user.id,
						name: this.formData.name,
						sex: this.formData.sex,
						college: this.formData.college,
						majorClass: this.formData.majorClass
					}
					
					// 使用 $request 发送请求，参考 confirm.vue 的写法
					this.$request.post('/riderApply/apply', requestData).then(res => {
						if (res.code === '200') {
							uni.showToast({
								icon: 'success',
								title: '申请提交成功'
							})
							setTimeout(() => {
								uni.navigateBack()
							}, 500)
						} else {
							uni.showToast({
								icon: 'error',
								title: res.msg || '提交失败'
							})
						}
					}).catch(err => {
						console.error('提交失败：', err)
						uni.showToast({
							icon: 'error',
							title: '提交失败，请重试'
						})
					})
					
				} catch (error) {
					console.error('表单验证错误：', error)
					uni.showToast({
						icon: 'error',
						title: error.errMsg || '表单验证失败'
					})
				}
			},
			
			// 修改图片选择处理方法
			selectStudentCard(e) {
				if (e.tempFiles && e.tempFiles.length > 0) {
					const file = e.tempFiles[0]
					console.log('选择的学生证图片大小：', file.size / 1024 + 'KB')
					
					if (file.size > 2 * 1024 * 1024) {
						uni.showToast({
							title: '图片不能大于2MB，请压缩后重试',
							icon: 'none'
						})
						return
					}
					
					this.formData.studentCard = [{
						...file,
						tempFilePath: e.tempFilePaths[0]
					}]
					this.$refs.formRef.validateField('studentCard')
				}
			},
			
			selectHealthCard(e) {
				if (e.tempFiles && e.tempFiles.length > 0) {
					const file = e.tempFiles[0]
					console.log('选择的健康证图片大小：', file.size / 1024 + 'KB')
					
					if (file.size > 2 * 1024 * 1024) {
						uni.showToast({
							title: '图片不能大于2MB，请压缩后重试',
							icon: 'none'
						})
						return
					}
					
					this.formData.healthCard = [{
						...file,
						tempFilePath: e.tempFilePaths[0]
					}]
					this.$refs.formRef.validateField('healthCard')
				}
			}
		}
	}
</script>

<style>
.container {
	padding: 20rpx;
}
.submit-btn {
	margin-top: 40rpx;
	background-color: #007AFF;
	color: #FFFFFF;
}
</style>

