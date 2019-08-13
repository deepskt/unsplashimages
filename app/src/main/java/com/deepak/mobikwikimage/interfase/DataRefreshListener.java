package com.deepak.mobikwikimage.interfase;

import com.deepak.mobikwikimage.database.PhotoUnsplash;

public interface DataRefreshListener {
    void onRefresh();
    void onDataFetch();
    void onClick(PhotoUnsplash photo);
}
