package com.uyi.app.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Executors;

/**
 * 图片异步加载类 Created by Pirate on 14-5-28.
 */
public class ImageUtil {
    
    /**
     * 加载图片
     *
     * @param url
     *            地址
     * @param draweeView
     *            控件
     */
    public static void load(String url, final SimpleDraweeView draweeView) {
        if (url == null)
            url = "";
        load(url, draweeView, null);
    }
    
    /**
     * 加载图片
     *
     * @param url
     *            地址
     * @param draweeView
     *            控件
     * @param listener
     *            下载Bitmap监听
     */
    public static void load(final String url,
                            final SimpleDraweeView draweeView,
                            final ImageLoadListener listener) {
        Uri uri;
        // 创建Uri
        if (url == null)
            uri = Uri.parse("");
        else
            uri = Uri.parse(url);
        // 创建加载监听
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            
            @Override
            public void onFinalImageSet(String id,
                                        ImageInfo imageInfo,
                                        Animatable animatable) {
                if (listener != null)
                    loadBitmap(url, listener);
            }
        };
        // 创建控制器
        @SuppressWarnings("unchecked")
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                                            .setUri(uri)
                                            .setTapToRetryEnabled(true)
                                            .setControllerListener(controllerListener)
                                            .build();
                                            
        // 设置控制器
        draweeView.setController(controller);
    }
    
    /**
     * 获取图片的Bitmap
     *
     * @param url
     *            地址
     * @param listener
     *            下载Bitmap监听
     */
    public static void loadBitmap(String url,
                                  final ImageLoadListener listener) {
        // 判断图片地址
        if (url == null)
            url = "";
        // 创建图片加载
        ImagePipeline pipeline = Fresco.getImagePipeline();
        // 创建图片请求
        ImageRequest request = ImageRequest.fromUri(url);
        // 获取数据源
        DataSource<CloseableReference<CloseableImage>> dataSource = pipeline.fetchDecodedImage(request,
                                                                                               null);
        // 获取资源
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(@Nullable Bitmap bitmap) {
                listener.loadBitmap(bitmap);
            }
            
            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                dataSource.close();
            }
        }, Executors.newSingleThreadExecutor());
    }
    
    /**
     * Bitmap监听
     */
    public interface ImageLoadListener {
        void loadBitmap(Bitmap bitmap);
    }


    public interface OnImageCompressDone{
        void onCompressDone();
    }

    /**
     * http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
     * 官网：获取压缩后的图片
     *
     * @param res
     * @param resId
     * @param reqWidth
     *            所需图片压缩尺寸最小宽度
     * @param reqHeight
     *            所需图片压缩尺寸最小高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    /**
     * 官网：获取压缩后的图片
     *
     * @param reqWidth
     *            所需图片压缩尺寸最小宽度
     * @param reqHeight
     *            所需图片压缩尺寸最小高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String filepath,
                                                     int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }
    public static Bitmap decodeSampledBitmapFromBitmap(Bitmap bitmap,
                                                       int reqWidth, int reqHeight) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] data = baos.toByteArray();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }
    /**
     * 计算压缩比例值(改进版 by touch_ping)
     *
     * 原版2>4>8...倍压缩 当前2>3>4...倍压缩
     *
     * @param options
     *            解析图片的配置信息
     * @param reqWidth
     *            所需图片压缩尺寸最小宽度O
     * @param reqHeight
     *            所需图片压缩尺寸最小高度
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int picheight = options.outHeight;
        final int picwidth = options.outWidth;
        int targetheight = picheight;
        int targetwidth = picwidth;
        int inSampleSize = 1;
        if (targetheight > reqHeight || targetwidth > reqWidth) {
            while (targetheight >= reqHeight && targetwidth >= reqWidth) {
                inSampleSize += 1;
                targetheight = picheight / inSampleSize;
                targetwidth = picwidth / inSampleSize;
            }
        }
        return inSampleSize;
    }
}
