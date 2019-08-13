package com.deepak.mobikwikimage.net;



import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public abstract class ResponseHandler extends AsyncHttpResponseHandler {


    public abstract void onFailure(String arg1, int statusCode);


    public abstract void onSuccess(String json, int statusCode);


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        onSuccess(new String(responseBody), statusCode);
    }


    @Override
    public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
        if (responseBody != null)
            onFailure(new String(responseBody), statusCode);
        else onFailure(null, statusCode);
    }

}
