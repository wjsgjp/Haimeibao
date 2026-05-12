<template>
	<view class="container">
		<view class="contact-list">
			<view class="contact-item" v-for="contact in contacts" :key="contact.id" @click="toChat(contact)">
				<image class="avatar" :src="contact.avatar || '/static/default-avatar.png'" mode="aspectFill"></image>
				<view class="contact-info">
					<text class="name">{{contact.name}}</text>
					<text class="role">{{contact.role === 'BUSINESS' ? '商家' : '用户'}}</text>
				</view>
				<uni-icons type="right" size="16" color="#666"></uni-icons>
			</view>
		</view>
		
		<!-- 空状态 -->
		<view class="empty" v-if="contacts.length === 0">
			<text>暂无联系人</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				contacts: [],
				user: uni.getStorageSync('xm-user')
			}
		},
		onShow() {
			this.loadContacts()
		},
		methods: {
			loadContacts() {
				if (!this.user || !this.user.id) {
					uni.showToast({
						icon: 'none',
						title: '请先登录'
					})
					return
				}
				console.log(this.user.id)
				this.$request.get(`/user/contacts/${this.user.id}`).then(res => {
					if (res.code === '200') {
						this.contacts = res.data || []
					} else {
						uni.showToast({
							icon: 'none',
							title: res.msg || '获取联系人失败'
						})
					}
				}).catch(err => {
					console.error('获取联系人失败：', err)
					uni.showToast({
						icon: 'none',
						title: '获取联系人失败'
					})
				})
			},
			toChat(contact) {
				uni.navigateTo({
					url: `/pages/chat/chat?contactId=${contact.id}&contactName=${contact.name}`
				})
			}
		}
	}
</script>

<style>
.container {
	padding: 20rpx;
}

.contact-list {
	background-color: #FFFFFF;
	border-radius: 10rpx;
}

.contact-item {
	display: flex;
	align-items: center;
	padding: 20rpx;
	border-bottom: 1px solid #EEEEEE;
}

.contact-item:last-child {
	border-bottom: none;
}

.avatar {
	width: 80rpx;
	height: 80rpx;
	border-radius: 50%;
	margin-right: 20rpx;
}

.contact-info {
	flex: 1;
}

.name {
	font-size: 28rpx;
	color: #333333;
}

.role {
	font-size: 24rpx;
	color: #999999;
	margin-left: 20rpx;
}

.empty {
	text-align: center;
	padding: 100rpx 0;
	color: #999999;
	font-size: 28rpx;
}
</style>