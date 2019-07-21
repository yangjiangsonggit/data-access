package com.yjs.dataaccess.utils;


import java.util.concurrent.LinkedBlockingQueue;

/**
 * create by jiangsongy on 2019/3/18
 */
public class QueueTest {

	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

		for (int i = 0; i < 10; i++) {
			queue.put(i);
		}

		for (int i = 0; i < 10; i++) {
			System.out.println(queue.take());
		}

	}
}
