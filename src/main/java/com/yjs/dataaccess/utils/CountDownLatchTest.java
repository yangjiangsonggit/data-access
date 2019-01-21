package com.yjs.dataaccess.utils;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * create by jiangsongy on 2019/1/21
 */
public class CountDownLatchTest {


	public static void main(String[] args) throws InterruptedException {
		Executor executor = Executors.newFixedThreadPool(2);
		CountDownLatch countDownLatch = new CountDownLatch(2);

		long id = Thread.currentThread().getId();
		System.out.println("testThread is completed:" + id);

		TestThread testThread1 = new TestThread(countDownLatch);
		TestThread testThread2 = new TestThread(countDownLatch);
		System.out.println("start" + LocalDateTime.now().toString());
		executor.execute(testThread1);
		executor.execute(testThread2);

		countDownLatch.await();
		System.out.println("end" + LocalDateTime.now().toString());

	}



	public static class TestThread implements Runnable {

		private CountDownLatch countDownLatch;

		TestThread(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				long id = Thread.currentThread().getId();
				System.out.println("testThread is completed:" + id);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				countDownLatch.countDown();
			}

		}
	}


}
