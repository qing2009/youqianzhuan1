package com.shanqb.mayi.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.viewpager.widget.ViewPager;

import com.shanqb.mayi.R;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.banner.transform.DepthTransformer;
import com.xuexiang.xui.widget.banner.transform.FadeSlideTransformer;
import com.xuexiang.xui.widget.banner.transform.FlowTransformer;
import com.xuexiang.xui.widget.banner.transform.RotateDownTransformer;
import com.xuexiang.xui.widget.banner.transform.RotateUpTransformer;
import com.xuexiang.xui.widget.banner.transform.ZoomOutSlideTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示数据
 *
 * @author xuexiang
 * @since 2018/11/23 下午5:52
 */
public class DemoDataProvider {

    public static String[] titles = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };

//    public static String[] urls = new String[]{//640*360 360/640=0.5625
//            "https://doc.91taojin.com.cn/Resource/Uploads/ueditor/image/20190122/1548126023772820.jpg",
//            "https://doc.91taojin.com.cn/Resource/Uploads/ueditor/image/20190122/1548126023363058.jpg",//无心法师:生死离别!月牙遭虐杀
//            "https://admin369.91taojin.com/Resource/Uploads/ueditor/image/20181019/1539928334944451.gif",//花千骨:尊上沦为花千骨
//            "https://admin369.91taojin.com/Resource/Uploads/ueditor/image/20181019/1539928380283588.gif",//综艺饭:胖轩偷看夏天洗澡掀波澜
//            "https://admin369.91taojin.com/Resource/Uploads/ueditor/image/20181019/1539928376365024.gif",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
//    };

    public static Integer[] urls = new Integer[]{//640*360 360/640=0.5625
           R.drawable.lunbo01,
           R.drawable.lunbo02,
           R.drawable.lunbo03,
           R.drawable.lunbo04,
    };

//    public static List<BannerItem> getBannerList() {
//        ArrayList<BannerItem> list = new ArrayList<>();
//        for (int i = 0; i < urls.length; i++) {
//            BannerItem item = new BannerItem();
//            item.imgUrl = urls[i];
//            item.title = titles[i];
//
//            list.add(item);
//        }
//
//        return list;
//    }



    private static List<AdapterItem> getGridItems(Context context, int titleArrayId, int iconArrayId) {
        List<AdapterItem> list = new ArrayList<>();
        String[] titles = ResUtils.getStringArray(titleArrayId);
        Drawable[] icons = ResUtils.getDrawableArray(context, iconArrayId);
        for (int i = 0; i < titles.length; i++) {
            list.add(new AdapterItem(titles[i], icons[i]));
        }
        return list;
    }

    public static List<Object> getUserGuides() {
        List<Object> list = new ArrayList<>();
        list.add(R.drawable.homepage_img1);
        list.add(R.drawable.homepage_img2);
        list.add(R.drawable.homepage_img3);
        list.add(R.drawable.homepage_img4);
        return list;
    }

    public static Class<? extends ViewPager.PageTransformer>[] transformers = new Class[]{
            DepthTransformer.class,
            FadeSlideTransformer.class,
            FlowTransformer.class,
            RotateDownTransformer.class,
            RotateUpTransformer.class,
            ZoomOutSlideTransformer.class,
    };


    public static String[] dpiItems = new String[]{
            "480 × 800",
            "1080 × 1920",
            "720 × 1280",
    };


}
