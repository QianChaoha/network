package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonHttpListener<M> implements IHttpListener {

	 private Class<M> responseClass;
	private IDataListener<M> listener;
	private Handler handler = new Handler(Looper.getMainLooper());

	public JsonHttpListener(Class<M> responseClass, IDataListener<M> listener) {
		super();
		 this.responseClass = responseClass;
		this.listener = listener;
	}

	@Override
	public void onSuccess(InputStream inputStream) {
		String content = convertStreamToString(inputStream);
		Gson gson = new Gson();
//		final M m = gson.fromJson(content,responseClass);
		System.out.println("=====content "+content);
		content=content.replace("/n", "");
		System.out.println("=====content "+content);
//		{"resultcode":"101","reason":"错误的请求KEY!","result":null,"error_code":10001}/n

//		final M m = JSON.parseObject("",responseClass);
//		BaseData data=gson.fromJson(content, BaseData.class);
		Type type = new TypeToken<M>() {}.getType();
		final M m=gson.fromJson(content, type);
		System.out.println("====="+m);
		boolean result = handler.post(new Runnable() {

			@Override
			public void run() {
				if (listener != null) {
					listener.onSuccess(m);
				}
			}
		});
		System.out.println();
	}

	public Type getType() {
		Type type = new TypeToken<M>() {}.getType();
		System.out.println("type  "+type);
		return type;
	}

	@Override
	public void onError() {

	}

	public String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {

			while ((line = reader.readLine()) != null) {

				sb.append(line + "/n");

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return sb.toString();

	}

}
