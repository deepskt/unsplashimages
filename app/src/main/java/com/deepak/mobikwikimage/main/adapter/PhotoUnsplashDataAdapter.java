package com.deepak.mobikwikimage.main.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.deepak.mobikwikimage.Config;
import com.deepak.mobikwikimage.R;
import com.deepak.mobikwikimage.database.PhotoUnsplash;
import com.deepak.mobikwikimage.interfase.DataRefreshListener;
import com.deepak.mobikwikimage.loader.PhotoUnsplashDataLoader;
import com.deepak.mobikwikimage.util.Tracer;

import java.util.ArrayList;
import java.util.List;

public class PhotoUnsplashDataAdapter extends RecyclerView.Adapter<PhotoUnsplashDataAdapter.PhotoUnspalshDataAdapterVH> {
    private final String TAG = Config.logger + PhotoUnsplashDataAdapter.class.getSimpleName();

    private List<PhotoUnsplash> mlist;
    private PhotoUnsplashDataLoader photoUnsplashDataLoader;
    private DataRefreshListener mListener;
    private int currentPosition;

    public PhotoUnsplashDataAdapter(DataRefreshListener dataRefreshListener) {
        mListener = dataRefreshListener;
    }

    public void setDataMap(List<PhotoUnsplash> photoFlickrList) {
        Tracer.debug(TAG," setDataMap "+" "+photoFlickrList.size());
        mlist = new ArrayList<>();
        mlist = photoFlickrList;
        notifyItemChanged(0, this.mlist.size());
        notifyDataSetChanged();
    }

    @Override
    public PhotoUnspalshDataAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new PhotoUnspalshDataAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(PhotoUnspalshDataAdapterVH holder, int position) {
        Tracer.debug(TAG, " onBindViewHolder " + " ");
        currentPosition = position;
        final PhotoUnsplash photoUnsplash = mlist.get(position);
        Tracer.debug(TAG," onBindViewHolder "+" "+photoUnsplash.getId()+" "+photoUnsplash.getImageData());
        if(getBitmap(photoUnsplash.getImageData()) != null){
                holder.avPhoto.setImageBitmap(getBitmap(photoUnsplash.getImageData()));
        }else{
          //  holder.avPhoto.setImageResource(R.mipmap.image_not_found);
        }

        holder.avPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(photoUnsplash);
            }
        });
        if(position == mlist.size()-1){
            mListener.onDataFetch();
        }
    }

    private Bitmap getBitmap(byte[] data){
        Tracer.debug(TAG," getBitmap "+" "+data);
        if(data != null) {
            Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
            return image;
        }else{
            return null;
        }

    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class PhotoUnspalshDataAdapterVH extends RecyclerView.ViewHolder {

        private ImageView avPhoto;

        public PhotoUnspalshDataAdapterVH(View view) {
            super(view);
            avPhoto = (ImageView) view.findViewById(R.id.avphoto);
        }
    }
}



