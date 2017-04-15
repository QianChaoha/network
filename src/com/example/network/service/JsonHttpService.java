package com.example.network.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.network.listener.IHttpListener;
/**
 * 此类主要功能是请求数据后将获取到的数据交给IHttpListener(或者实现类),实现类会转化为具体的格式,比如实体类
 * @author qianchao
 * @date 2017-4-15下午9:11:01
 * @description
 */
public class JsonHttpService implements IHttpService {
	private IHttpListener listener;
	private HttpClient client = new DefaultHttpClient();
	private String url;
	/**
	 * 请求参数字节数组
	 */
	private byte[] requestData;
	private HttpRequestBase httpRequestBase;

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 在子线程中执行
	 */
	@Override
	public void excue() {
		httpRequestBase = new HttpPost(url);
		if (requestData != null) {
			ByteArrayEntity entity = new ByteArrayEntity(requestData);
			// 设置请求参数
			((HttpPost) httpRequestBase).setEntity(entity);
		}
		try {
			client.execute(httpRequestBase, new HttpResponseHandle());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setHttpCallBack(IHttpListener listener) {
		this.listener = listener;
	}

	@Override
	public void setRequestData(byte[] requestData) {
		this.requestData = requestData;
	}

	class HttpResponseHandle extends BasicResponseHandler {
		@Override
		public String handleResponse(HttpResponse response) throws HttpResponseException, IOException {
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if (listener != null) {
						InputStream inputStream = entity.getContent();
						listener.onSuccess(inputStream);
					}
				}
			} else {
				if (listener != null) {
					listener.onError();
				}
			}
			return super.handleResponse(response);
		}
	}
}
