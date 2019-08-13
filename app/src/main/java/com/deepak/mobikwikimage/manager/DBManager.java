package com.deepak.mobikwikimage.manager;

import android.database.sqlite.SQLiteDatabase;

import com.deepak.mobikwikimage.Config;
import com.deepak.mobikwikimage.MobikwikApplication;
import com.deepak.mobikwikimage.database.DaoMaster;
import com.deepak.mobikwikimage.database.DaoSession;
import com.deepak.mobikwikimage.database.PhotoUnsplash;
import com.deepak.mobikwikimage.database.PhotoUnsplashDao;
import com.deepak.mobikwikimage.util.Tracer;

import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.util.List;

/*
 *
 * @author deepsindeep
 * */

public class DBManager {
    private static final String TAG = Config.logger + DBManager.class.getSimpleName();

    /**
     * DB ReArch
     */
     private static PhotoUnsplashDao mPhotoUnsplashDao;


    public synchronized static void init() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MobikwikApplication.getsAppContext(), "MyDB", null);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Tracer.debug(TAG, " init " + " DB version" + db.getVersion());
        DaoSession daoSession = new DaoMaster(db).newSession(IdentityScopeType.None);
        mPhotoUnsplashDao = daoSession.getPhotoUnsplashDao();
    }

    public synchronized static void clearDB(boolean clearNSL) {
        Tracer.debug(TAG, " clearDB " + " ");
    }

    public synchronized static  void insertIntoDb(List<PhotoUnsplash>list){
        Tracer.debug(TAG," insertIntoDb "+" "+list.size());
        mPhotoUnsplashDao.insertOrReplaceInTx(list);
    }

    public synchronized  static List<PhotoUnsplash>getPhotos(int page){
        Tracer.debug(TAG," getPhotos "+" ");
        return mPhotoUnsplashDao.queryBuilder().where(PhotoUnsplashDao.Properties.Page.eq(page),PhotoUnsplashDao.Properties.ImageData.isNotNull()).list();
    }

    public static void updatePhoto(PhotoUnsplash photoFlickr) {
        mPhotoUnsplashDao.updateInTx(photoFlickr);
    }

    public static List<PhotoUnsplash> getPhotoWithoutImageData(int page) {
        return mPhotoUnsplashDao.queryBuilder().where(PhotoUnsplashDao.Properties.Page.eq(page),PhotoUnsplashDao.Properties.ImageData.isNull()).limit(5).list();

    }
}