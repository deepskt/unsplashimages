package com.deepak.mobikwikimage;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.mobikwikimage.backgroundTask.ImageDataDBTask;
import com.deepak.mobikwikimage.database.PhotoUnsplash;
import com.deepak.mobikwikimage.interfase.DataRefreshListener;
import com.deepak.mobikwikimage.interfase.PhotoUnsplashDataListner;
import com.deepak.mobikwikimage.loader.PhotoUnsplashDataLoader;
import com.deepak.mobikwikimage.main.MainViewModel;
import com.deepak.mobikwikimage.main.adapter.PhotoUnsplashDataAdapter;
import com.deepak.mobikwikimage.main.dialog.ProgressDialog;
import com.deepak.mobikwikimage.manager.DBManager;
import com.deepak.mobikwikimage.model.ErrorD;
import com.deepak.mobikwikimage.model.Photo;
import com.deepak.mobikwikimage.util.Tracer;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = Config.logger + MainActivity.class.getSimpleName();

    private AppCompatImageView avPre;
    private AppCompatImageView avNext;
    private TextView tvPage;
    private RecyclerView recyclerView;
    private ProgressDialog mProgressDialog;
    private int currentPage = 1;
    private MainViewModel mViewModel;
    private List<PhotoUnsplash> photoFlickrList;
    private PhotoUnsplashDataLoader mFLoader;
    private PhotoUnsplashDataAdapter mPhotoUnsplashDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
       // avNext  = (AppCompatImageView) findViewById(R.id.avNext);
        //avNext.setOnClickListener(this);
       // avPre = (AppCompatImageView)findViewById(R.id.avPre);
        //avPre.setOnClickListener(this);
        //tvPage = (TextView)findViewById(R.id.tv_current_page);
        recyclerView = (RecyclerView)findViewById(R.id.rvPhoto);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //tvPage.setText(String.valueOf(currentPage));
        mProgressDialog = new ProgressDialog(this);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mFLoader = new PhotoUnsplashDataLoader();
        mPhotoUnsplashDataAdapter = new PhotoUnsplashDataAdapter(adapterRefreshListener);
        recyclerView.setAdapter(mPhotoUnsplashDataAdapter);
        loadPage(photoUnsplashDataListner,currentPage);

    }

    private void getImageData() {
        Tracer.debug(TAG," getImageData "+" "+currentPage);
        Tracer.debug(TAG," getImageData "+" "+DBManager.getPhotoWithoutImageData(currentPage).size());
        if(DBManager.getPhotoWithoutImageData(currentPage).size() > 0) {
            mProgressDialog.show("Loading Image Data...");
            ImageDataDBTask imageDataDBTask = new ImageDataDBTask(dataRefreshListener);
            imageDataDBTask.execute(DBManager.getPhotoWithoutImageData(currentPage));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.avNext:
                currentPage++;
                loadPage(photoUnsplashDataListner,currentPage);
                break;
            case R.id.avPre:
                currentPage--;
                loadPage(photoUnsplashDataListner,currentPage);
        }

    }

    private void loadPage(PhotoUnsplashDataListner photoUnsplashDataListner, int currentPage) {
        if(currentPage > 0) {
            if(mViewModel.getPhotolist(currentPage)!= null && mViewModel.getPhotolist(currentPage).size() > 0){
                setData();
            }else {
                mProgressDialog.show("Fetching Images data . . .");
                mFLoader.getPhotoDataList(photoUnsplashDataListner, currentPage);
            }
        }
    }

    protected void setData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            //    tvPage.setText(String.valueOf(currentPage));
                mPhotoUnsplashDataAdapter.setDataMap(mViewModel.getPhotolist(currentPage));
            }
        });
    }

    PhotoUnsplashDataListner photoUnsplashDataListner = new PhotoUnsplashDataListner() {
        @Override
        public void onData(List<Photo>list) {
            mProgressDialog.dismiss();
            getImageData();
        }

        @Override
        public void onFailure(ErrorD errorResponse) {
            mProgressDialog.changeToError(errorResponse.getMessage());

        }
    };

    DataRefreshListener dataRefreshListener = new DataRefreshListener() {
        @Override
        public void onRefresh() {
            mProgressDialog.dismiss();
            loadPage(photoUnsplashDataListner,currentPage);
        }

        @Override
        public void onDataFetch() {
           // getImageData();
        }

        @Override
        public void onClick(PhotoUnsplash photoUnsplash) {

        }
    };

    DataRefreshListener adapterRefreshListener = new DataRefreshListener() {
        @Override
        public void onRefresh() {
        }

        @Override
        public void onDataFetch() {
            getImageData();
        }

        @Override
        public void onClick(PhotoUnsplash photoUnsplash) {
            Tracer.debug(TAG," onClick "+" ");
                //Todo Open Activity
        }
    };

}
