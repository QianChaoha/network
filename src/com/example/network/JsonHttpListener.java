package com.example.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

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
		final M m = gson.fromJson(content, responseClass);
		System.out.println();
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
