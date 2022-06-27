package com.example.textualreaddemo.newcradviewdesign;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.NewsListData;
import com.example.textualreaddemo.basebean.newsdata.PictureBean;
import com.example.textualreaddemo.network.NewsDataUtility;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SetPictureUtil {

    private Activity context;

    public SetPictureUtil(Activity context) {
        this.context = context;
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    public List<PictureBean> getPictureList(String body){
        DisplayMetrics metric = new DisplayMetrics();//获取屏幕
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);//是获取到Activity的实际屏幕信息。
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels / 3 * 2;   // 屏幕高度（像素）
        View view1 = LayoutInflater.from(context).inflate(R.layout.layout_news_list_picture_title_item,null,false);
        View view2 = LayoutInflater.from(context).inflate(R.layout.layout_news_list_no_picture_title_item,null,false);

        List<PictureBean> list = new ArrayList<>();
        for (NewsListData data : getNewsData(body)){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PictureBean pictureBean = new PictureBean();

            if (data.getImgList() != null){
                layoutView(view1,width,height);
                TextView itemTitle = view1.findViewById(R.id.news_list_title);
                TextView itemText = view1.findViewById(R.id.news_list_text);
                ImageView imageView = view1.findViewById(R.id.news_list_image);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        try {
                            bitmap = Glide.with(context).asBitmap()
                                    .load(data.getImgList().get(0))
                                    .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                                    .get();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        itemText.setText(data.getDigest());
                        itemTitle.setText(data.getTitle());
                        imageView.setImageBitmap(bitmap);
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadBitmapFromView(view1).compress(Bitmap.CompressFormat.PNG, 100, baos);
            }else {
                layoutView(view2,width,height);
                TextView itemTitle = view2.findViewById(R.id.news_list_title);
                TextView itemText = view2.findViewById(R.id.news_list_text);
                itemText.setText(data.getDigest());
                itemTitle.setText(data.getTitle());
                loadBitmapFromView(view2).compress(Bitmap.CompressFormat.PNG, 100, baos);
            }

            //转化图片
            byte[] bytes=baos.toByteArray();

            pictureBean.setId(data.getNewsId());
            pictureBean.setPictureByte(bytes);
            list.add(pictureBean);
        }
        GlideCacheUtil.getInstance().clearImageAllCache(context);
        return list;
    }

    private List<NewsListData> getNewsData(String body){
        return NewsDataUtility.handleNewsListResponse(body).getData();
    }

    //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
    private void layoutView(View v, int width, int height) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

}
