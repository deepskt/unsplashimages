package com.deepak.mobikwikimage.main;


import androidx.lifecycle.ViewModel;

import com.deepak.mobikwikimage.Config;
import com.deepak.mobikwikimage.database.PhotoUnsplash;
import com.deepak.mobikwikimage.manager.DBManager;
import com.deepak.mobikwikimage.util.Tracer;

import java.util.List;

public class MainViewModel extends ViewModel {
    private static final String TAG = Config.logger + MainViewModel.class.getSimpleName();

    // TODO: Implement the ViewModel
    private List<PhotoUnsplash> mPhotolist;

    public List<PhotoUnsplash> getPhotolist(int page){
        Tracer.debug(TAG," getPhotolist "+" ");
        mPhotolist = DBManager.getPhotos(page);
        return mPhotolist;
    }
}
