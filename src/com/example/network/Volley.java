package com.example.network;

import java.util.concurrent.FutureTask;

public class Volley {

	public static <T,M> void sendRequest(T requestInfo,String url,Class<M> response,IDataListener<M> dataListener){
		IHttpListener listener=(IHttpListener) new JsonHttpListener<M>(response, dataListener);
		HttpTask httpTask=new HttpTask<T>(requestInfo, url, listener);
		ThreadPoolManage.getThreadPoolManage().execute(new FutureTask(httpTask,null));
	};

}
