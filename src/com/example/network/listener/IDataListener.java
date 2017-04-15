package com.example.network.listener;
/**
 * 请求结果的监听,此接口一般要求将返回数据转为实体类M
 * @author qianchao
 * @date 2017-4-15下午9:23:02
 * @description
 */
public interface IDataListener<M> {
	void onSuccess(M data);
	void onError();
}
