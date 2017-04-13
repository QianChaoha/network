package com.example.network;

public class BaseData {
//{"resultcode":"101","reason":"错误的请求KEY!","result":null,"error_code":10001}
	public String resultcode;
	public String reason;
	public String result;
	public String error_code;

	@Override
	public String toString() {
		return "resultcode:" + resultcode + " reason:" + reason + " result:" + result + " error_code:" + error_code;
	}
}
