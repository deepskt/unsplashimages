package com.deepak.mobikwikimage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.mobikwikimage.loader.PhotoUnsplashDataLoader;
import com.deepak.mobikwikimage.main.MainViewModel;
import com.deepak.mobikwikimage.main.adapter.PhotoUnsplashDataAdapter;
import com.deepak.mobikwikimage.main.dialog.ProgressDialog;

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
    }


}
