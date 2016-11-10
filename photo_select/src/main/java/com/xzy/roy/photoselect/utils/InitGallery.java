package com.xzy.roy.photoselect.utils;

import android.content.Context;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by roy on 2016/11/7.
 */
public class InitGallery {

    private static InitGallery ourInstance = new InitGallery();

    public static InitGallery getInstance() {
        return ourInstance;
    }

    private InitGallery() {
    }

    public void initGallery(Context application) {
        ThemeConfig theme = new ThemeConfig.Builder().build();

        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setEnablePreview(true)
                .build();

        ImageLoader imageLoader = new GlideImageLoader();
        CoreConfig config = new CoreConfig.Builder(application, imageLoader, theme).setFunctionConfig(functionConfig).build();
        GalleryFinal.init(config);
    }
}
