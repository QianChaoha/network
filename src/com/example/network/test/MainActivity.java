package com.example.network.test;


import com.example.network.R;
import com.example.network.R.layout;
import com.example.network.listener.IDataListener;
import com.example.network.volley.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void requestNetwork(View view) {
		User user=new User("zhangsan", 20);
		String url="http://op.juhe.cn/trainTickets/cityCode";
		Volley.sendRequest(user, url, BaseData.class, new IDataListener<BaseData>() {

			@Override
			public void onSuccess(BaseData data) {
				System.out.println(data.toString());
			}

			@Override
			public void onError() {
				
			}
		});
	}
}
