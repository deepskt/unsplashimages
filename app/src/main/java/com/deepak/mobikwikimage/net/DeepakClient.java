package com.deepak.mobikwikimage.net;


import com.deepak.mobikwikimage.Config;
import com.deepak.mobikwikimage.util.Tracer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/*
* @author deepsindeep
* */
public class DeepakClient {
    /*
     * Tag for logs
     */
    public static final String TAG = Config.logger + DeepakClient.class.getSimpleName();

    /**
     * time out for the get or post request.
     */
    private final int timeout = 20000;

    private final AsyncHttpClient clientGet;
    private final  SyncHttpClient syncHttpClientGet;
    private MySSLSocketFactory socketFactory;

    /**
     * GBAsycnHttpClient instance
     */
    private static DeepakClient _instance;

    /**
     * private GBAsyncHttpClient constructer
     *
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    private DeepakClient() throws NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {
        Tracer.debug(TAG, "[RhAsyncHttpClient] _ " + "" + "");
        clientGet = new AsyncHttpClient();
        clientGet.setTimeout(timeout);
        clientGet.setMaxRetriesAndTimeout(0,timeout);
        clientGet.setResponseTimeout(timeout);

        syncHttpClientGet = new SyncHttpClient();
        syncHttpClientGet.setTimeout(timeout);
        syncHttpClientGet.setMaxRetriesAndTimeout(0,timeout);
        syncHttpClientGet.setResponseTimeout(timeout);

        try {
            SchemeRegistry schemeRegitry1 = clientGet.getHttpClient().getConnectionManager().getSchemeRegistry();
            schemeRegitry1.register(new Scheme("https", new TlsSniSocketFactory(), 443));
            clientGet.setLoggingEnabled(true);

        } catch (Exception e) {
            Tracer.debug(TAG, " DelhiveryClientUMS " + " " + e);
            e.printStackTrace();
        }

        try {
            SchemeRegistry schemeRegitry1 = syncHttpClientGet.getHttpClient().getConnectionManager().getSchemeRegistry();
            schemeRegitry1.register(new Scheme("https", new TlsSniSocketFactory(), 443));
            syncHttpClientGet.setLoggingEnabled(true);

        } catch (Exception e) {
            Tracer.debug(TAG, " DelhiveryClientUMS " + " " + e);
            e.printStackTrace();
        }
        addHeaderValues();
    }

    /*
    * Method to set headers value in Sync and Async Clients
    */
    private void addHeaderValues() {
        clientGet.addHeader("Accept", "application/json");
        clientGet.addHeader("Content-Type", "application/json");
        clientGet.addHeader("Accept-Encoding", "gzip");

        syncHttpClientGet.addHeader("Accept", "application/json");
        syncHttpClientGet.addHeader("Content-Type", "image/jpeg");
    }

    /**
     * static method to make {@link DeepakClient} singleton
     *
     * @return if _instance is null then create a {@link DeepakClient}
     * instance and return
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    public static DeepakClient getInstance() throws UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException {
        Tracer.debug(TAG, "[getInstance] _ " + "" + "");

        if (_instance == null) {
            _instance = new DeepakClient();
        }
        return _instance;
    }

    /**
     * @param url
     * @param responseHandler
     */
    public void get(String url, RequestParams params,
                    AsyncHttpResponseHandler responseHandler) {
        clientGet.get(url, params, responseHandler);
    }

    /**
     * @param url
     * @param responseHandler
     */
    public void syncget(String url, RequestParams params,
                    BinaryHttpResponseHandler responseHandler) {
        syncHttpClientGet.get(url, params, responseHandler);
    }


}
