package com.example.network.volley;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理类，类似Volley，只适合小数据，高并发。大数据放到队列里会占用时间，影响后面的请求
 * 
 * @author qianchao
 * @date 2017-4-9下午3:20:43
 * @description
 */
public class ThreadPoolManage {
	private static volatile ThreadPoolManage threadPoolManage = null;
	private static ThreadPoolExecutor threadPoolExecutor;
	/**
	 * 阻塞队列,将需要执行的线程放到这个队列里,线程池执行的线程就是取自此队列
	 */
	private LinkedBlockingQueue<FutureTask<?>> service = new LinkedBlockingQueue<FutureTask<?>>();
	/**
	 * 当Executor已经关闭（即执行了executorService.shutdown()方法后），并且Executor将有限边界
	 * 用于最大线程数量和工作队列容量，且已经饱和时，在方法execute()中提交的新任务将被拒绝。
	 * 在以上述情况下，execute 方法将调用其 RejectedExecutionHandler 的 
	 * RejectedExecutionHandler.rejectedExecution() 方法。
	 */
	private RejectedExecutionHandler handler = new RejectedExecutionHandler() {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try {
				service.put(new FutureTask<Object>(r, null));// 将拒绝的线程加到队列
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	/**
	 * 实例化一个请求
	 */
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			while (true) {
				FutureTask futureTask = null;
				try {
					// take()是一个阻塞式方法
					futureTask = service.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (futureTask != null) {
					threadPoolExecutor.execute(futureTask);
				}
			}

		}
	};

	public <V> void execute(FutureTask<V> task) {
		if (task!=null) {
			try {
				//添加至请求队列
				service.put(task);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private ThreadPoolManage() {
		threadPoolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
		// 执行一个请求
		threadPoolExecutor.execute(runnable);
		// 设置线程池的拒绝策略为handler    
		threadPoolExecutor.setRejectedExecutionHandler(handler);
	}

	public static ThreadPoolManage getThreadPoolManage() {
		if (threadPoolManage == null) {
			synchronized (ThreadPoolManage.class) {
				if (threadPoolManage == null) {
					threadPoolManage = new ThreadPoolManage();
				}
			}
		}
		return threadPoolManage;
	}
}
