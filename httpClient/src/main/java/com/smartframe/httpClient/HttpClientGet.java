package com.smartframe.httpClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.message.BasicNameValuePair;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 通过GET 方式请求
 * @author Administrator
 *
 */
public class HttpClientGet {
	
	
	private static final OkHttpClient mOkHttpClient = new OkHttpClient();
	
	
    static{
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }
    
    
    public static String getReqBuild(HttpUrl.Builder urlBuilder)throws IOException{
    	Request.Builder reqBuild = new Request.Builder();
		reqBuild.url(urlBuilder.build());
			Response response = OkHttpUtil.execute(reqBuild.build());
	        if (response.isSuccessful()) {
	            String responseUrl = response.body().string();
	            return responseUrl;
	        } else {
	            throw new IOException("Unexpected code " + response);
	        }
    }

    
    public static String getStringFromServer(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = OkHttpUtil.execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    
    
    public static String getStringFromServer(String url, List<BasicNameValuePair> params) throws IOException{
    	url = OkHttpUtil.attachHttpGetParams(url, params);
        Request request = new Request.Builder().url(url).build();
        Response response = OkHttpUtil.execute(request);
        if (response.isSuccessful()) {
            String responseUrl = response.body().string();
            return responseUrl;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    
    

}
