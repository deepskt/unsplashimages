package com.deepak.mobikwikimage.loader;

import com.deepak.mobikwikimage.Config;
import com.deepak.mobikwikimage.interfase.BaseListener;
import com.deepak.mobikwikimage.interfase.PhotoUnsplashDataListner;
import com.deepak.mobikwikimage.interfase.UnplashImageDataListener;
import com.deepak.mobikwikimage.manager.DBManager;
import com.deepak.mobikwikimage.manager.DbMapper;
import com.deepak.mobikwikimage.model.ErrorD;
import com.deepak.mobikwikimage.model.Photo;
import com.deepak.mobikwikimage.net.UrlResolver;
import com.deepak.mobikwikimage.util.JsonUtil;
import com.deepak.mobikwikimage.util.Tracer;
import com.loopj.android.http.RequestParams;

import java.util.List;

public class PhotoUnsplashDataLoader extends BaseLoader {
    private static final String TAG = Config.logger + PhotoUnsplashDataLoader.class.getSimpleName();

    public void getPhotoDataList(PhotoUnsplashDataListner listener, int page){
        String url = UrlResolver.withPath(UrlResolver.EndPoints.data, String.valueOf(page));
        RequestParams params = new RequestParams();
        params.add("client_id","238b4f660e017edb7dadc5ce864869daf68441fd58249d0f773123334f11ef9f");
        requestGet(url, params, listener);
    }

    public void getImageData(UnplashImageDataListener listener, String url){
        Tracer.debug(TAG," getImageData "+" "+url);
        requestSyncGet(url,null,listener);
    }

    @Override
    protected void onSuccess(byte[] data, BaseListener baseListener) {
        if(baseListener instanceof UnplashImageDataListener){
            ((UnplashImageDataListener) baseListener).imageData(data);
        }
    }

    @Override
    protected void onSuccess(String json, BaseListener baseListner) {
        if(baseListner instanceof PhotoUnsplashDataListner){
            List<Photo>list =  (List)JsonUtil.objectify(json, List.class);
            if(list!= null && list.size()>0){
                DBManager.insertIntoDb(DbMapper.setPhotoListData(list,1));
                ((PhotoUnsplashDataListner) baseListner).onData(list);
            }else {
                baseListner.onFailure(new ErrorD("No Data Found", 0));
            }
        }
    }

    @Override
    protected void onFailure(ErrorD error, BaseListener baseListner, String json) {
        baseListner.onFailure(error);
    }
}
