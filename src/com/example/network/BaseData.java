package com.example.network;

public class BaseData {
	public BaseData() {
	}

	// {"resultcode":"101","reason":"错误的请求KEY!","result":null,"error_code":10001}
	public String resultcode;
	public String reason;
	public String result;
	public int error_code;


	public String getResultcode() {
		return resultcode;
	}


	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public int getError_code() {
		return error_code;
	}


	public void setError_code(int error_code) {
		this.error_code = error_code;
	}


	@Override
	public String toString() {
		return "resultcode:" + resultcode + " reason:" + reason + " result:" + result + " error_code:" + error_code;
	}
}
