package com.yjs.dataaccess.utils;

import com.google.common.util.concurrent.RateLimiter;



/**
 * 令牌桶限流
 *
 * create by jiangsongy on 2019/3/10
 */
public class GuavaRateLimiterTest {

	/**
	 * 令牌桶算法
	 * 令牌桶算法是一个存放固定容量令牌（token）的桶，按照固定速率往桶里添加令牌。令牌桶算法基本可以用下面的几个概念来描述：
	 *
	 * 假如用户配置的平均发送速率为r，则每隔1/r秒一个令牌被加入到桶中。
	 *
	 * 桶中最多存放b个令牌，当桶满时，新添加的令牌被丢弃或拒绝。
	 *
	 * 当一个n个字节大小的数据包到达，将从桶中删除n个令牌，接着数据包被发送到网络上。
	 *
	 * 如果桶中的令牌不足n个，则不会删除令牌，且该数据包将被限流（要么丢弃，要么缓冲区等待）。
	 */

	public static void main(String[] args) throws InterruptedException {
		RateLimiter rateLimiter = RateLimiter.create(2);

		Thread.sleep(1000L);
		for (int i = 1; i < 7; i++) {
			if (rateLimiter.tryAcquire()) {
				System.out.println(i + "获取到令牌");
			} else {
				System.out.println(i + "没获取到令牌");
			}
		}
	}


}
