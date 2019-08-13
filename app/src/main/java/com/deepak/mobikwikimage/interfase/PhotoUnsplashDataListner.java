package com.deepak.mobikwikimage.interfase;


import com.deepak.mobikwikimage.model.Photo;

import java.util.List;

public interface PhotoUnsplashDataListner extends BaseListener {
    void onData(List<Photo> data);
}
