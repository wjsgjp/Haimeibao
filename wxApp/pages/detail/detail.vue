<template>
	<view style="padding: 20rpx;">
		<!-- 商家信息 -->
		<view class="box" style="display: flex;">
			<view style="flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
				<view style="font-size: 36rpx; font-weight: bold;">
					<text>{{ business.name || '' }}</text>
					<uni-icons type="star-filled" size="18" v-if="business.isCollect" @click="saveCollect()"
						color="orange" style="margin-left: 18rpx;"></uni-icons>

					<uni-icons type="star-filled" size="18" color="#999" @click="saveCollect()" v-else
						style="margin-left: 18rpx;"></uni-icons>
				</view>
				<view>
					<text style="padding-right: 10rpx; border-right: 2rpx solid #ccc;">平台配送</text>
					<text style="padding: 0 10rpx; border-right: 2rpx solid #ccc;">免配送费</text>
					<text style="padding: 0 10rpx;">30分钟送达</text>
				</view>
				<view>
					地址：{{ business.address || '' }}
				</view>
				<view>
					联系电话：{{ business.phone || '' }}
				</view>
			</view>

			<view style="width: 200rpx; height: 200rpx;">
				<image :src="business.avatar" style="width: 100%; height: 100%; border-radius: 50%;"></image>
			</view>
		</view>
		<!-- 商家信息结束 -->

		<!-- 商家的分类商品列表和评价信息 -->
		<view style="margin: 20rpx 0;">
			<view
				style=" background-color: #fff; display: flex; border-radius: 10rpx; overflow: hidden; margin-bottom: 10rpx;">
				<view style="padding: 10rpx;text-align: center; " :class="{'active': current === '全部商品'}"
					@click="current = '全部商品'">全部商品
				</view>
				<view style="padding: 10rpx; width: fit-content;text-align: center; "
					:class="{'active': current === '评价'}" @click="current = '评价'">评价</view>
				<view style="padding: 10rpx; width: fit-content;text-align: center; "
					:class="{'active': current === '在线联系'}" @click="current = '在线联系'">在线联系</view>
			</view>

			<!-- 分类商品展示 -->
			<view v-if="current === '全部商品'"
				style="display: flex; background-color: #fff; border-radius: 10rpx; padding: 10rpx;">
				<view style="width: 200rpx; text-align: center;">
					<view v-for="item in categoryList" :key="item.id" class="category-item"
						:class="{ 'category-active' : item.id === activeCategoryId }" @click="loadGoods(item.id)">
						{{ item.name }}
					</view>
				</view>

				<scroll-view scroll-y="true" style="height: calc(100vh - 480rpx);">
					<!-- 商品列表开始 -->
					<view style="padding: 20rpx;">
						<view style="display: flex; grid-gap: 20rpx; margin-bottom: 20rpx;" v-for="item in goodsList"
							:key="item.id">
							<view style="width: 200rpx; height: 200rpx;">
								<image :src="item.img"
									style="width: 200rpx; height: 200rpx; border-radius: 10rpx; display: block;">
								</image>
							</view>
							<view
								style="flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
								<view style="font-size: 32rpx; font-weight: bold;">{{ item.name }}</view>
								<view style="font-size: 24rpx;">{{ item.descr }}</view>
								<view>
									<text class="mini-btn" v-if="item.discount != 1 ">{{ item.discount * 10 }}折</text>
									<text class="mini-btn" v-else>无折扣</text>
									<text style="font-size: 24rpx; margin-left: 10rpx;">已售 {{item.saleCount}}</text>
								</view>
								<view>
									<text style="text-decoration: line-through;"
										v-if="item.discount != 1 ">￥{{ item.price }}</text>
									<text style="color: orangered; margin-left: 10rpx;">￥{{ item.money }}</text>
									<text class="mini-btn" style="margin-left: 5rpx;">到手价</text>
								</view>
								<view>
									<text class="mini-btn-fill" @click="addCart(item)">选购</text>
								</view>
							</view>
						</view>
					</view>
					<!-- 商品列表结束 -->
				</scroll-view>

			</view>

			<!-- 评价列表 -->
			<scroll-view scroll-y="true" style="height: calc(100vh - 480rpx);" v-if="current === '评价'">
				<view class="box" v-if="commentList">
					<view v-for="item in commentList" :key="item.id"
						style="border-bottom: 2rpx solid #eee; padding: 20rpx 0;">
						<view style="display: flex; grid-gap: 20rpx;">
							<view style="display: flex; flex-direction: column; align-items: center; grid-gap: 10rpx;">
								<image :src="item.userAvatar" style="width: 120rpx; border-radius: 50%;"
									mode="widthFix"></image>
								<view>{{ item.userName }}</view>
							</view>
							<view
								style="flex: 1; display: flex; flex-direction: column; justify-content: space-between;">
								<view style="flex: 1; padding-top: 10rpx; font-size: 24rpx;">{{ item.content }}
								</view>
								<uni-rate :value="item.star" readonly></uni-rate>
							</view>
							<view style="font-size: 24rpx; color: #888; display: flex; align-items: flex-end;">
								{{ item.time }}
							</view>
						</view>
					</view>
				</view>
				<view class="box" v-else> 还没有用户评价，继续努力吧~</view>
			</scroll-view>
			<!-- 评价列表结束 -->

		</view>
		<!-- 商家的分类商品列表和评价结束 -->

		<uni-goods-nav :fill="true" :options="options" :buttonGroup="buttonGroup" @click="onClick"
			@buttonClick="buttonClick" />

		<uni-popup ref="popup" type="bottom" background-color="#fff">
			<scroll-view style="max-height: 70vh;" scroll-y="true">
				<view style="padding: 40rpx 40rpx 140rpx 40rpx;">
					<view style="text-align: right; margin-bottom: 10rpx; color: #999;" @click="deleteAll"
						v-if="cartList.length">
						<uni-icons style="position: relative; top: 4rpx;" type="trash" size="16"
							color="#999"></uni-icons>
						清空
					</view>
					<view v-else style="align-items: center;">
						<view style="margin-left: 140rpx;">购物车还没有商品，快去添加吧~</view>
					</view>
					<view v-for="(item, index) in cartList" :key="index" style="display: flex; margin-bottom: 20rpx;"
						v-if="item.goods">
						<view style="width: 100rpx; height: 100rpx;">
							<image style="width: 100%; height: 100%; display: inline-block;" :src="item.goods.img">
							</image>
						</view>
						<view
							style="flex: 1; margin-left: 20rpx; display: flex; flex-direction: column; justify-content: space-around;">
							<view style="flex: 1;">{{ item.goods.name }}</view>
							<view style="flex: 1; color: red; display: flex; align-items: flex-end;">
								<view style="flex: 1;">￥{{ item.goods.money }}</view>
								<view style="flex: 1; display: flex; justify-content: right;">
									<uni-number-box :min="1" v-model="item.num"
										@change="updateCart(item)"></uni-number-box>
								</view>
							</view>
						</view>
					</view>
					<view style="margin-top: 40rpx; text-align: right;" v-if="amount.amount">
						原价：<text>￥{{ amount.amount }}</text></view>
					<view style="margin-top: 10rpx; text-align: right;" v-if="amount.discount">优惠金额：<text
							style="color: red;">-￥{{ amount.discount }}</text></view>
					<view style="margin-top: 10rpx; text-align: right;" v-if="amount.money">实付金额：<text
							style="color: red; font-size: 32rpx;">￥{{ amount.money }}</text></view>
				</view>
			</scroll-view>
		</uni-popup>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				businessId: 0,
				business: {},
				commentList: [],
				categoryList: [],
				activeCategoryId: 0,
				goodsList: [],

				options: [{
					icon: 'cart',
					text: '购物车',
					info: 2
				}],
				buttonGroup: [{
					text: '立即下单',
					backgroundColor: '#ffa200',
					color: '#fff'
				}],
				user: uni.getStorageSync('xm-user'),
				cartList: [],
				amount: {},
				current: '全部商品',
			}
		},
		onShow() {
			let allPages = getCurrentPages() //获取当前页面栈的实例；
			let lastPages = allPages.length - 1 // 获得倒数第二个元素的索引；
			let option = allPages[lastPages].options // 获得上个页面传递的参数；
			// this.businessId = option.businessId
			this.businessId = uni.getStorageSync("businessId")
			console.log("xx", this.businessId)
			this.load()
			this.loadCart()
		},
		methods: {
			saveCollect() {
				this.$request.post('/collect/saveCollect', {
					userId: this.user.id,
					businessId: this.businessId
				}).then(res => {
					if (res.code === '200') {
						uni.showToast({
							icon: this.business.isCollect != null ? 'error' : 'success',
							title: this.business.isCollect != null ? '取消收藏' : '收藏成功'
						})
						setTimeout(() => {
							this.load()
						}, 500)
					} else {
						uni.showToast({
							icon: 'error',
							title: res.msg
						})
					}
				})
			},
			buttonClick() {
				if (this.cartList.length == 0) {
					uni.showToast({
						icon: 'none',
						title: '还未选购商品'
					})
					return;
				}

				uni.navigateTo({
					url: '/pages/confirm/confirm'
				})
				uni.setStorageSync('xm-orders', {
					businessId: this.businessId
				})
			},
			deleteAll() {
				this.$request.del('/cart/detByBusinessId/' + this.businessId + '/' + this.user.id).then(res => {
					if (res.code === '200') {
						uni.showToast({
							icon: 'success',
							title: '操作成功'
						})
						this.loadCart()
					} else {
						uni.showToast({
							icon: 'error',
							title: res.msg
						})
					}
				})
			},
			updateCart(cart) {
				this.$request.put('/cart/update', cart).then(res => {
					if (res.code === '200') {
						this.loadCart()
					} else {
						uni.showToast({
							icon: 'error',
							title: res.msg
						})
					}
				})
			},
			onClick() {
				// 点击购物车图标触发
				this.$refs.popup.open('bottom')
			},
			loadCart() {
				this.$request.get('/cart/selectAll', {
					userId: this.user.id,
					businessId: this.businessId
				}).then(res => {
					this.cartList = res.data || []
					
					this.options[0].info = this.cartList.length


					// this.options[0].info = this.cartList.reduce((total, item) => total + Number(item.num || 0), 0)
				})

				this.$request.get('/cart/calc', {
					userId: this.user.id,
					businessId: this.businessId
				}).then(res => {
					this.amount = res.data || {}
					console.log(this.amount)
				})
			},
			addCart(goods) {
				this.$request.post('/cart/add', {
					goodsId: goods.id,
					num: 1,
					businessId: this.businessId,
					userId: this.user.id
				}).then(res => {
					if (res.code === '200') {
						uni.showToast({
							icon: 'success',
							title: '加入成功'
						})
						this.loadCart()
					} else {
						uni.showToast({
							icon: 'error',
							title: res.msg
						})
					}
				})
			},
			load() {
				this.$request.get('/business/selectById/' + this.businessId).then(res => {
					this.business = res.data || {}

				})
				this.$request.get('/comment/selectAll').then(res => {
					this.commentList = res.data;
				})

				this.$request.get('/category/selectAll', {
					businessId: this.businessId
				}).then(res => {
					this.categoryList = res.data || []
					this.activeCategoryId = this.categoryList.length > 0 ? this.categoryList[0].id : 0
					this.loadGoods(this.activeCategoryId)
				})
			},
			loadGoods(categoryId) {
				this.activeCategoryId = categoryId
				this.$request.get('/goods/selectAll', {
					categoryId: categoryId
				}).then(res => {
					this.goodsList = res.data || []
				})
			}
		}
	}
</script>

<style>
	.category-item {
		padding: 15rpx;
		background-color: #eee;
		border-bottom: 2rpx solid #fff;
	}

	.category-active {
		background-color: #fff;
		color: orange;
		border-left: 5rpx solid orange;
	}

	.mini-btn {
		border: 2rpx solid orangered;
		padding: 0 4rpx;
		font-size: 22rpx;
		color: orangered;
		border-radius: 5rpx;
		font-weight: bold;
	}

	.mini-btn-fill {
		width: 50rpx;
		height: 50rpx;
		background-color: orange;
		padding: 0 4rpx;
		color: #fff;
		font-size: 30rpx;
		border-radius: 5rpx;
	}

	.active {
		background-color: #ffd100;
	}
</style>
