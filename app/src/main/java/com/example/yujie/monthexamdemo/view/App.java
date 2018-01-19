package com.example.yujie.monthexamdemo.view;

import android.app.Application;

import com.example.yujie.monthexamdemo.util.CrashHandler;
import com.example.yujie.monthexamdemo.util.ImagePipelineConfigUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by yujie on 2018/1/18.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this, ImagePipelineConfigUtils.getDefaultImagePipelineConfig(this));
        CrashHandler.getInstace().init(this);
    }
}
