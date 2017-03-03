package com.wytiger.common.imageloader;

import com.wytiger.common.imageloader.impl.GlideImpl;

/**
 * description:策略模式封装图片加载框架
 * Created by wytiger on 2017-2-5.
 */

public class ImageLoader {
    public static IImageInterface getImageLoader() {

//        return PicassoImpl.getInstance();
        return GlideImpl.getInstance();
    }

}
