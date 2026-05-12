<template>
	<view class="chat-container">
		<!-- 聊天记录区域 -->
		<scroll-view class="chat-list" scroll-y="true" :scroll-top="scrollTop" @scrolltoupper="loadMoreMessages">
			<view class="message-item" v-for="msg in messages" :key="msg.id" :class="{'message-mine': msg.senderId === user.id}">
				<view class="message-content">
					<text>{{msg.content}}</text>
				</view>
				<text class="message-time">{{formatTime(msg.createTime)}}</text>
			</view>
		</scroll-view>
		
		<!-- 输入区域 -->
		<view class="input-area">
			<input class="message-input" 
				v-model="inputContent" 
				type="text" 
				placeholder="请输入消息"
				@confirm="sendMessage"
			/>
			<button class="send-btn" @click="sendMessage" :disabled="!inputContent.trim()">发送</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				messages: [],
				inputContent: '',
				user: uni.getStorageSync('xm-user'),
				contact: null,
				scrollTop: 0,
				timer: null // 用于定时刷新消息
			}
		},
		onLoad(options) {
			// 获取传递过来的联系人信息
			this.contact = {
				id: options.contactId,
				name: options.contactName
			}
			// 设置导航栏标题
			uni.setNavigationBarTitle({
				title: this.contact.name
			})
			
			this.loadMessages()
			
			// 启动定时刷新
			this.timer = setInterval(() => {
				this.loadMessages(false)
			}, 3000) // 每3秒刷新一次
		},
		onUnload() {
			// 页面卸载时清除定时器
			if(this.timer) {
				clearInterval(this.timer)
			}
		},
		methods: {
			// 加载消息记录
			loadMessages(scrollToBottom = true) {
				this.$request.get(`/message/chat/${this.user.id}/${this.contact.id}`).then(res => {
					if(res.code === '200') {
						this.messages = res.data || []
						if(scrollToBottom) {
							this.$nextTick(() => {
								// 滚动到底部
								this.scrollTop = 9999
							})
						}
					}
				}).catch(err => {
					console.error('获取消息失败：', err)
					uni.showToast({
						icon: 'none',
						title: '获取消息失败'
					})
				})
			},
			
			// 发送消息
			sendMessage() {
				if(!this.inputContent.trim()) return
				
				const message = {
					senderId: this.user.id,
					receiverId: this.contact.id,
					content: this.inputContent.trim()
				}
				
				this.$request.post('/message/send', message).then(res => {
					if(res.code === '200') {
						// 清空输入框
						this.inputContent = ''
						// 重新加载消息
						this.loadMessages()
					} else {
						uni.showToast({
							icon: 'none',
							title: res.msg || '发送失败'
						})
					}
				}).catch(err => {
					console.error('发送消息失败：', err)
					uni.showToast({
						icon: 'none',
						title: '发送失败'
					})
				})
			},
			
			// 格式化时间
			formatTime(timeStr) {
				if(!timeStr) return ''
				const date = new Date(timeStr)
				const today = new Date()
				const isToday = date.toDateString() === today.toDateString()
				
				if(isToday) {
					return date.toLocaleTimeString('zh-CN', {hour: '2-digit', minute: '2-digit'})
				} else {
					return date.toLocaleDateString('zh-CN') + ' ' + 
						   date.toLocaleTimeString('zh-CN', {hour: '2-digit', minute: '2-digit'})
				}
			},
			
			// 加载更多消息（预留接口）
			loadMoreMessages() {
				// TODO: 实现加载历史消息
			}
		}
	}
</script>

<style>
.chat-container {
	display: flex;
	flex-direction: column;
	height: 100vh;
	background-color: #f5f5f5;
}

.chat-list {
	flex: 1;
	padding: 20rpx;
}

.message-item {
	margin-bottom: 20rpx;
	display: flex;
	flex-direction: column;
}

.message-mine {
	align-items: flex-end;
}

.message-content {
	max-width: 70%;
	padding: 20rpx;
	border-radius: 10rpx;
	background-color: #ffffff;
	word-break: break-all;
}

.message-mine .message-content {
	background-color: #007AFF;
	color: #ffffff;
}

.message-time {
	font-size: 24rpx;
	color: #999999;
	margin-top: 10rpx;
}

.input-area {
	padding: 20rpx;
	background-color: #ffffff;
	border-top: 1rpx solid #eeeeee;
	display: flex;
	align-items: center;
}

.message-input {
	flex: 1;
	height: 80rpx;
	background-color: #f5f5f5;
	border-radius: 40rpx;
	padding: 0 30rpx;
	margin-right: 20rpx;
}

.send-btn {
	width: 120rpx;
	height: 80rpx;
	line-height: 80rpx;
	text-align: center;
	background-color: #007AFF;
	color: #ffffff;
	border-radius: 40rpx;
	font-size: 28rpx;
}

.send-btn[disabled] {
	background-color: #cccccc;
}
</style>