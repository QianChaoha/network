package com.example.network;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HttpTask implements Runnable {
	private IHttpService service;

	public <T> HttpTask(T requestInfo, String url, IHttpListener listener) {
		this.service = new JsonHttpService();
		this.service.setUrl(url);
		this.service.setHttpCallBack(listener);
		if (requestInfo != null) {
			Gson gson = new Gson();
			Type type = new TypeToken<T>() {}.getType();
			String gStr = gson.toJson(requestInfo, type);
//			String gStr = JSON.toJSONString(requestInfo);
			System.out.println(gStr);
			try {
				service.setRequestData(gStr.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

	}

	void excute() {
		
	}

	@Override
	public void run() {
		service.excue();
	}
}
