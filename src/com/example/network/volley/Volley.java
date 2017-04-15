package com.example.network.volley;

import java.util.concurrent.FutureTask;

import com.example.network.httptask.HttpTask;
import com.example.network.httptask.JsonHttpTask;
import com.example.network.listener.IDataListener;
import com.example.network.listener.IHttpListener;
import com.example.network.listener.JsonHttpListener;

public class Volley {

	public static <T,M> void sendRequest(T requestInfo,String url,Class<M> response,IDataListener<M> dataListener){
		IHttpListener listener=(IHttpListener) new JsonHttpListener<M>(response, dataListener);
		HttpTask httpTask=new JsonHttpTask(requestInfo, url, listener);
		//Callable与Runnable的功能大致相似，Callable中有一个call()函数，但是call()函数有返回值
		
		//Executor就是Runnable和Callable的调度容器，Future就是对于具体的Runnable或者Callable任务的执行结果进行
//		取消、查询是否完成、获取结果、设置结果操作
		
		//FutureTask则是一个RunnableFuture<V>，而RunnableFuture实现了Runnbale又实现了Futrue<V>这两个接口
//		与Runnable不同,FutureTask执行结束后,可以通过task.get()方法来获取返回数据
		ThreadPoolManage.getThreadPoolManage().execute(new FutureTask<Object>(httpTask,null));
	};

}
