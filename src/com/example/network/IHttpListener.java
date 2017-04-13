package com.example.network;

import java.io.InputStream;
/**
 * 
 * @author qianchao
 * @date 2017-4-8下午9:56:18
 * @description 处理请求结果
 */
public interface IHttpListener<M> {
	void onSuccess(InputStream inputStream);
	void onError();
}
