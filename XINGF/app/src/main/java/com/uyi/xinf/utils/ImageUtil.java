package com.uyi.xinf.utils;

import android.graphics.Bitmap;
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
}
