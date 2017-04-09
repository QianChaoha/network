package com.example.network;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HttpTask<T> implements Runnable {
	private IHttpService service;

	public <T> HttpTask(T requestInfo, String url, IHttpListener listener) {
		this.service = new JsonHttpService();
		this.service.setUrl(url);

		if (requestInfo != null) {
			Gson gson = new Gson();
			Type type = new TypeToken<T>() {
			}.getType();
			String gStr = gson.toJson(requestInfo, type);
		}

	}

	void excute() {
	}

	@Override
	public void run() {
		service.excue();
	}

	public Type getEType() {
      ParameterizedType pType = (ParameterizedType) this.getClass().;
      Type[] types = pType.getActualTypeArguments();
      if (types.length > 1) {
          return types[1];
      } else {
          return types[0];
      }
  }}
