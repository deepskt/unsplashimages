package com.deepak.mobikwikimage.loader;
import com.deepak.mobikwikimage.Config;
import com.deepak.mobikwikimage.interfase.BaseListener;
import com.deepak.mobikwikimage.model.ErrorD;
import com.deepak.mobikwikimage.net.DeepakClient;
import com.deepak.mobikwikimage.net.ResponseHandler;
import com.deepak.mobikwikimage.util.JsonUtil;
import com.deepak.mobikwikimage.util.Tracer;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public abstract class BaseLoader {
    private final String TAG = Config.logger + BaseLoader.class.getSimpleName();
    public DeepakClient mClient;

    protected abstract void onSuccess(byte[] data, BaseListener baseListener);

    protected abstract void onSuccess(String json, BaseListener baseListner);

    protected abstract void onFailure(ErrorD error, BaseListener baseListner, String json);

    public BaseLoader() {
        Tracer.debug(TAG, "[BaseLoader] _ " + "");
        try {
            mClient = DeepakClient.getInstance();
        } catch (Exception e) {
            Tracer.debug(TAG, " BaseLoader" + " " + e);
            e.printStackTrace();
        }
    }

    /*
    * Get Request
    * @params url, url params , token, Base listner for callback
    * */
    protected void requestGet(String url, RequestParams reqParams,
                              final BaseListener mListner) {

        final String loggerUrl = url;
        final String requestData = "URL:" + url + " " + "ResquestParam:" + JsonUtil.jsonify(reqParams);
        Tracer.debug(TAG," requestGet "+" "+requestData);
            try {

                mClient.get(url, reqParams, new ResponseHandler() {

                    @Override
                    public void onSuccess(String str, int statuscode) {
                        Tracer.debug(TAG, "[requestGet] _ onSuccess" + "  url  " + loggerUrl + " Json " + str);
                        if (str != null) {
                                BaseLoader.this.onSuccess(str, mListner);
                        } else {
                            BaseLoader.this.onFailure(getErrorD(str,statuscode), mListner, str);
                        }
                    }

                    @Override
                    public void onFailure(String json, int statuscode) {
                        Tracer.debug(TAG, " onFailure " + json + " " + statuscode);
                        BaseLoader.this.onFailure(getErrorD(json, statuscode), mListner,
                                json);
                    }
                });
            } catch (Exception e) {
                Tracer.debug(TAG, " requestGet Exceprtion " + e);
                BaseLoader.this.onFailure(new ErrorD("Json Error", 111),
                        mListner, null);
            }
    }


    /*
     * Get Request
     * @params url, url params , token, Base listner for callback
     * */
    protected void requestSyncGet(String url, RequestParams reqParams,
                              final BaseListener mListner) {

        final String loggerUrl = url;
        final String requestData = "URL:" + url + " " + "ResquestParam:" + JsonUtil.jsonify(reqParams);
        Tracer.debug(TAG," requestSyncGet "+" "+requestData);
        try {
            String[] allowedContentTypes = new String[] { "application/pdf", "image/png", "image/jpeg" };

            mClient.syncget(url, reqParams, new BinaryHttpResponseHandler(allowedContentTypes) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                    BaseLoader.this.onSuccess(binaryData, mListner);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                    BaseLoader.this.onFailure(getErrorD(error.getMessage(), statusCode), mListner,
                            null);
                }

            });
        } catch (Exception e) {
            Tracer.debug(TAG, " requestGet Exceprtion " + e);
            BaseLoader.this.onFailure(new ErrorD("Json Error", 111),
                    mListner, null);
        }
    }


    private ErrorD getErrorD(String jsonMessage, int statuscode) {
        Tracer.debug(TAG, " getErrorD " + " ");
         return new ErrorD(jsonMessage,statuscode);
    }

}
