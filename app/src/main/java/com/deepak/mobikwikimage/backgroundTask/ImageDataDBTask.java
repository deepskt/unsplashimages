package com.deepak.mobikwikimage.backgroundTask;

import android.os.AsyncTask;

import com.deepak.mobikwikimage.Config;
import com.deepak.mobikwikimage.database.PhotoUnsplash;
import com.deepak.mobikwikimage.interfase.DataRefreshListener;
import com.deepak.mobikwikimage.interfase.UnplashImageDataListener;
import com.deepak.mobikwikimage.loader.PhotoUnsplashDataLoader;
import com.deepak.mobikwikimage.manager.DBManager;
import com.deepak.mobikwikimage.model.ErrorD;
import com.deepak.mobikwikimage.util.Tracer;

import java.util.ArrayList;
import java.util.List;

public class ImageDataDBTask extends AsyncTask<List<PhotoUnsplash>, Void, Boolean> {
    private final String TAG = Config.logger + ImageDataDBTask.class.getSimpleName();

    private PhotoUnsplash photoUnsplash;
    private PhotoUnsplashDataLoader mPhotoUnsplashDataLoader;
    private DataRefreshListener mDataRefreshListener;
    private int count=0;
    private List<PhotoUnsplash> photoFlickrList;


    public ImageDataDBTask( DataRefreshListener dataRefreshListener) {
        Tracer.debug(TAG," ImageDataDBTask "+" ");
        mPhotoUnsplashDataLoader = new PhotoUnsplashDataLoader();
        mDataRefreshListener = dataRefreshListener;
        photoFlickrList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(List<PhotoUnsplash>... params) {
        Tracer.debug(TAG," doInBackground "+" "+params[0].size());
        count = 0;
        if(params[0]!= null) {
            photoFlickrList = params[0];
            return getData(count);
        }else{
            return false;
        }
    }

    private Boolean getData(int index) {
        Tracer.debug(TAG," getData "+" "+index);
        if(index < photoFlickrList.size()){
            photoUnsplash = photoFlickrList.get(index);
            if(photoUnsplash.getImageData() == null) {
                Tracer.debug(TAG," getData "+" "+ photoUnsplash.getUrls());
                mPhotoUnsplashDataLoader.getImageData(unplashImageDataListener, photoUnsplash.getUrls());
            }else{
                count++;
                getData(count);
            }
        }else{
            return false;
        }
        return true;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Tracer.debug(TAG," onPostExecute "+" ");
        mDataRefreshListener.onRefresh();
    }

    UnplashImageDataListener unplashImageDataListener = new UnplashImageDataListener() {
        @Override
        public void imageData(byte[] data) {
            photoUnsplash.setImageData(data);
            DBManager.updatePhoto(photoUnsplash);
            count++;
            getData(count);
        }

        @Override
        public void onFailure(ErrorD errorResponse) {
            count++;
            getData(count);
        }
    };
}
