package com.example.network;

public interface IHttpService {
	void setUrl(String url);
	/**
	 * 执行请求
	 */
	void excue();
	/**
	 * 设置请求监听
	 * @param listener
	 */
	void setHttpCallBack(IHttpListener listener);
	/**
	 * 设置请求参数
	 * @param requestData
	 */
	void setRequestData(byte[] requestData);
}
