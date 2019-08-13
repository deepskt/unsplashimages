package com.deepak.mobikwikimage.manager;

import com.deepak.mobikwikimage.Config;
import com.deepak.mobikwikimage.database.PhotoUnsplash;
import com.deepak.mobikwikimage.model.Photo;
import com.deepak.mobikwikimage.util.JsonUtil;
import com.deepak.mobikwikimage.util.Tracer;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbMapper {

    private static final String TAG = Config.logger + DbMapper.class.getSimpleName();

    public static List<PhotoUnsplash> setPhotoListData(List<Photo> photolist, int page) {
        Tracer.debug(TAG," setPhotoListData "+" "+photolist.size());
        List<PhotoUnsplash>photoFlickrs = new ArrayList<>();
        String url;
        for(Object object: photolist){
            String json = JsonUtil.jsonify(object);
            Tracer.debug(TAG," setPhotoListData "+" "+json);
            Photo photo = (Photo)JsonUtil.objectify(json,Photo.class);
            PhotoUnsplash photoUnsplash = new PhotoUnsplash();
            photoUnsplash.setId(photo.getId());
            photoUnsplash.setUrls(getFirstURL(photo.getUrls()));
            photoUnsplash.setImageData(null);
            photoUnsplash.setPage(page);
            Tracer.debug(TAG," setPhotoListData "+" "+JsonUtil.jsonify(photoUnsplash));
            photoFlickrs.add(photoUnsplash);
        }
        return photoFlickrs;
    }

    private static String getFirstURL(HashMap<String, String> urls) {
        for(String key: urls.keySet()){
            if(urls.get(key)!=null){
                return urls.get(key);
            }
        }
        return null;
    }
}
