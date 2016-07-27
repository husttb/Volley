package com.demo.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.demo.volley.bean.LoginBean;
import com.demo.volley.nethelp.NetworkHelper;
import com.demo.volley.nethelp.UIDataListener;
import com.demo.volley.tool.ToolImage;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * @author dong.xu
 * @Email:99799543@qq.com
 * 2016/07/27
 * 1 Volley二次封装
 * 2 利用volley下载图片 并加入公司Referer 防止盗链
 */
public class MainActivity extends AppCompatActivity implements UIDataListener ,View.OnClickListener{

	ImageView img;
	Button reqBtn;
	//换成公司URL链接
	//返回服务器数据：
	//{
	//	"code": "0000",
	//		"msg": "操作成功",
	//		"data": {
	//	"uid": 9999999,
	//			"userName": "18550009543",
	//			"nickName": null,
	//			"ico": null,
	//			"accessToken": "123_test",
	//			"regMsg": null
	//}
	//}
	//此URL 换成你需要请求的连接
    String url = "http://qas-ecosystem-api.ecovacs.cn/v1/private/5ee1724328a09dff91a42b0202d1e837/eco_e/1.0.0/rd4/1/user/login_test?account=18550009543&password=25f9e794323b453885f5181f1b624d0b";
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		img = (ImageView) findViewById(R.id.imageView);
		reqBtn = (Button) findViewById(R.id.reqbutton);
        //下载图片并已加入公司相关Referer
		ToolImage.setImageLoader(img,"http://www.sinaimg.cn/dy/slidenews/2_img/2016_30/730_1867800_413643.jpg",this,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

		reqBtn.setOnClickListener(this);

	}

	@Override public void onDataChanged(JSONObject data, String tag) {

		//正确返回的服务器数据
		Log.i("onDataChanged", data.toString());
        //返回data字段数据  ：{"uid":9999999,"userName":"18550009543","nickName":null,"ico":null,"accessToken":"123_test","regMsg":null}
		Gson gson = new Gson();
		LoginBean status = gson.fromJson(data.toString(), LoginBean.class);
		Log.i("onDataChanged", status.getUserName());


	}

	@Override public void onErrorHappened(String errorCode, String errorMessage, String tag) {
		//正确返回的服务器数据出错
	}

	@Override public void onTokenTimeOutError(String code) {
		//服务器Token失效
	}

	@Override public void onClick(View v) {
		switch (v.getId()) {
			case R.id.reqbutton://volley 网络请求
				NetworkHelper networkHelper = new NetworkHelper(this);
				networkHelper.sendGETRequest(url, null,MainActivity.this,"requestTag",true);
		   break;

		}


	}
}
