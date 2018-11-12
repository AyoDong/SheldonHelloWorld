package com.sheldon.async.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {

	private ExecutorService executor = Executors.newFixedThreadPool(1);

	public void asyncTask() {
		executor.submit(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				int sum = 0;
				for (int i = 0; i < 1000; i++) {
					sum += i;
				}

				System.out.println(sum);
			}
		});
	}
}
