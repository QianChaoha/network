package com.example.network.httptask;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import android.util.Log;

import com.example.network.listener.IHttpListener;
import com.example.network.service.IHttpService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 此类将请求参数和url处理后交给IHttpService去执行
 * @author qianchao
 * @date 2017-4-15下午9:09:22
 * @description
 */
public abstract class HttpTask implements Runnable {
	private IHttpService service;
	private String code = "utf-8";

	public <T> HttpTask(T requestInfo, String url, IHttpListener listener) {
		this.service = getHttpService(url, listener);
		if (requestInfo != null) {
			Gson gson = new Gson();
			Type type = new TypeToken<T>() {
			}.getType();
			String gStr = gson.toJson(requestInfo, type);
			try {
				Log.e("Request", url + "   param    " + gStr);
				service.setRequestData(gStr.getBytes(getCode()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		service.excue();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public abstract <T> IHttpService getHttpService(String url, IHttpListener listener);

}
