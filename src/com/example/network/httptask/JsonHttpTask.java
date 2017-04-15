package com.example.network.httptask;

import com.example.network.listener.IHttpListener;
import com.example.network.service.IHttpService;
import com.example.network.service.JsonHttpService;

public class JsonHttpTask extends HttpTask {

	public <T> JsonHttpTask(T requestInfo, String url, IHttpListener listener) {
		super(requestInfo, url, listener);
	}

	@Override
	public <T> IHttpService getHttpService(String url, IHttpListener listener) {
		IHttpService httpService=new JsonHttpService();
		httpService.setHttpCallBack(listener);
		httpService.setUrl(url);
		return httpService;
	}

}
