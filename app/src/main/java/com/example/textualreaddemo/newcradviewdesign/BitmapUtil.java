package com.example.textualreaddemo.newcradviewdesign;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class BitmapUtil {
    private static final int INTERVAL =2;
    private static final float REFLECT_RATIO = 0.2f;
    public void reflectionBitmap(byte[] bytes , ImageView imageview , SimpleTarget<Bitmap> simpleTarget){
        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                handleBitmap(resource , simpleTarget);
            }
        };

        RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(20))
                .diskCacheStrategy(DiskCacheStrategy.DATA)//设置缓存原始数据
                .override(300,1000);
        Glide.with(imageview)//imageview为待填充图片的view对象
                .asBitmap()
                .load(bytes)//url为待加载的网络图片的url链接
                .apply(options)
                .into(target);
    }

    private void handleBitmap(Bitmap bitmap , SimpleTarget<Bitmap> simpleTarget){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap canvasBitmap = Bitmap.createBitmap(width , (int)(height*(1f+REFLECT_RATIO)+INTERVAL) , bitmap.getConfig());
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawBitmap(bitmap , 0 ,0 , null);
        //倒影
        Matrix matrix = new Matrix();
        matrix.setScale(1 , -1);
        Bitmap reflect = Bitmap.createBitmap(bitmap,0 , (int)(height*(1f-REFLECT_RATIO)) , width , (int)(height*REFLECT_RATIO) , matrix ,false);
        canvas.drawBitmap(reflect , 0 , height+INTERVAL , null);
        //渐变色
        LinearGradient gradient = new LinearGradient(0 ,0 ,0,reflect.getHeight() , 0x00ffffff , 0x50ffffff , Shader.TileMode.MIRROR);
        Paint paint = new Paint();
        paint.setShader(gradient);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawRect(0,height+INTERVAL , width , canvasBitmap.getHeight() , paint);
        simpleTarget.onResourceReady(canvasBitmap , null);
    }
}
