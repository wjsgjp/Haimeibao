<template>
	<view style="padding: 40rpx;">
		<view
			style="padding: 20rpx; margin: 80rpx 0; background-color: #fff; box-shadow: 0 2rpx 10rpx rgba(0,0,0,.1); border-radius: 10rpx;">
			<view style="margin: 50rpx 224rpx; font-size: 40rpx;">欢迎登录</view>
			<uni-forms ref="form" :rules="rules" :modelValue="form" validateTrigger='blur'>
				<uni-forms-item required name="username">
					<uni-easyinput prefixIcon="person" v-model="form.username" placeholder="请输入账号" />
				</uni-forms-item>
				<uni-forms-item required name="password">
					<uni-easyinput prefixIcon="locked" type="password" v-model="form.password" placeholder="请输入密码" />
				</uni-forms-item>
				<uni-forms-item>
					<button @click="login()"
						style="background-color: #ffd100; border-color: #ffd100; height: 70rpx; line-height: 70rpx;">登
						录</button>
				</uni-forms-item>
				<uni-forms-item>
					<view style="text-align: right;">还没有账号？去 <navigator style="display: inline-block; color: dodgerblue; 
										margin-left: 8rpx;" url="/pages/register/register">注册</navigator>
					</view>
				</uni-forms-item>
			</uni-forms>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					role: 'USER'
				},

				rules: {
					// 对username字段进行必填验证
					username: {
						// username 字段的校验规则
						rules: [
							// 校验 username 不能为空
							{
								required: true,
								errorMessage: '请输入账号',
							},
							// 对username字段进行长度验证
							{
								minLength: 3,
								maxLength: 10,
								errorMessage: '账号长度在 {minLength} 到 {maxLength} 个字符',
							}
						],
					},
					password: {
						// password 字段的校验规则
						rules: [
							// 校验 password 不能为空
							{
								required: true,
								errorMessage: '请输入密码',
							},
							// 对password字段进行长度验证
							{
								minLength: 3,
								maxLength: 10,
								errorMessage: '密码长度在 {minLength} 到 {maxLength} 个字符',
							}
						],
					}
				}
			}
		},
		methods: {
			login() {
				this.$refs.form.validate().then(res => {
					this.$request.post('/login', this.form).then(res => {
						if (res.code == '200') {
							uni.showToast({
								icon: 'success',
								title: '登录成功'
							})
							//用户信息存入缓存
							uni.setStorageSync('xm-user', res.data)
							//跳转主页
							setTimeout(() => {
								uni.switchTab({
									url: '/pages/index/index'
								})
							}, 500)

						} else {
							uni.showToast({
								icon: 'error',
								title: res.msg
							})
						}
					})
				})

			}
		}
	}
</script>

<style>

</style>