package com.example.alim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class ViewPagerImageAdapterMarketDetails extends PagerAdapter {
    private Context context;
    private String[] imageUrls;
    LayoutInflater inflater;
//     ImageView imageView ;
//     ViewGroup viewGroupContainer;

    ViewPagerImageAdapterMarketDetails(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {

       final ImageView imageView = new ImageView(context);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(imageUrls[position]);
        storageReference.getBytes(1024*1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        imageView.setImageBitmap(bitmap);
                        Log.d("checked","load ok");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Log.d("checked",e.toString()) ;
            }
        });
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
