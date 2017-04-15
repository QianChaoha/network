package com.example.network.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonHttpListener<M> implements IHttpListener<M> {

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
		Log.e("Reponse", content);
		Gson gson = new Gson();
		final M m=gson.fromJson(content, responseClass);
		boolean result = handler.post(new Runnable() {

			@Override
			public void run() {
				if (listener != null) {
					listener.onSuccess(m);
				}
			}
		});
	}

	public Type getType() {
		Type type = new TypeToken<M>() {}.getType();
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
