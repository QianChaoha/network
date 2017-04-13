package com.example.network;

public interface IDataListener<M> {
	void onSuccess(M data);
	void onError();
}
