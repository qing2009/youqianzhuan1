package com.shanqb.wanka.tabview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shanqb.wanka.R;
import com.shanqb.wanka.adapter.NewsCardViewListAdapter;
import com.shanqb.wanka.bean.NewInfo;
import com.shanqb.wanka.bean.NewInfo163;
import com.shanqb.wanka.bean.NewInfo163Item;
import com.shanqb.wanka.bean.NewInfo163_car;
import com.shanqb.wanka.bean.NewInfo163_tiyu;
import com.shanqb.wanka.bean.NewInfo163_toutiao;
import com.shanqb.wanka.bean.NewInfo163_yule;
import com.shanqb.wanka.bean.NewInfo163_yundong;
import com.shanqb.wanka.inter.MyQueueResponse;
import com.shanqb.wanka.utils.Utils;
import com.xuexiang.xui.adapter.recyclerview.DividerItemDecoration;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author XUE
 * @since 2019/5/9 11:54
 */
public class SimpleListFragment extends BaseFragment implements MyQueueResponse {

    Unbinder unbinder;

    private static final String KEY_IS_SPECIAL = "key_is_special";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsCardViewListAdapter mAdapter;
    private String requestUrl;
    private String new163_type;


    public static SimpleListFragment getInstance(String requestUrl,String new163_type){
        SimpleListFragment simpleListFragment = new SimpleListFragment();
        Bundle b = new Bundle();
        b.putString("requestUrl",requestUrl);
        b.putString("new163_type",new163_type);
        simpleListFragment.setArguments(b);
        return simpleListFragment;
    }



    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter = new NewsCardViewListAdapter());
//        mAdapter.refresh(getSpecialDemoNewInfos());
        requestGetQueue(false,requestUrl,this);

        swipeRefreshLayout.setEnabled(false);
        mAdapter.setOnItemClickListener(new RecyclerViewHolder.OnItemClickListener<NewInfo163Item>() {
            @Override
            public void onItemClick(View itemView, NewInfo163Item item, int position) {
                Utils.goWeb(getContext(),item.getUrl());
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.include_recycler_view_refresh, container, false);
        unbinder = ButterKnife.bind(this, view);

        requestUrl = getArguments().getString("requestUrl");
        new163_type = getArguments().getString("new163_type");

        initViews();
        return view;
    }


    @Override
    public void fetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 用于占位的空信息
     *
     * @return
     */
    public static List<NewInfo> getSpecialDemoNewInfos() {
        List<NewInfo> list = new ArrayList<>();


        list.add(new NewInfo("Flutter", "Flutter学习指南App,一起来玩Flutter吧~")
                .setSummary("Flutter是谷歌的移动UI框架，可以快速在iOS、Android、Web和PC上构建高质量的原生用户界面。 Flutter可以与现有的代码一起工作。在全世界，Flutter正在被越来越多的开发者和组织使用，并且Flutter是完全免费、开源的。同时它也是构建未来的Google Fuchsia应用的主要方式。")
                .setDetailUrl("https://juejin.im/post/5e39a1b8518825497467e4ec")
                .setImageUrl("https://pic4.zhimg.com/v2-1236d741cbb3aabf5a9910a5e4b73e4c_1200x500.jpg"));

        list.add(new NewInfo("Android UI", "XUI 一个简洁而优雅的Android原生UI框架，解放你的双手")
                .setSummary("涵盖绝大部分的UI组件：TextView、Button、EditText、ImageView、Spinner、Picker、Dialog、PopupWindow、ProgressBar、LoadingView、StateLayout、FlowLayout、Switch、Actionbar、TabBar、Banner、GuideView、BadgeView、MarqueeView、WebView、SearchView等一系列的组件和丰富多彩的样式主题。\n")
                .setDetailUrl("https://juejin.im/post/5c3ed1dae51d4543805ea48d")
                .setImageUrl("https://user-gold-cdn.xitu.io/2019/1/16/1685563ae5456408?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        list.add(new NewInfo("Android", "XUpdate 一个轻量级、高可用性的Android版本更新框架")
                .setSummary("XUpdate 一个轻量级、高可用性的Android版本更新框架。本框架借鉴了AppUpdate中的部分思想和UI界面，将版本更新中的各部分环节抽离出来，形成了如下几个部分：")
                .setDetailUrl("https://juejin.im/post/5b480b79e51d45190905ef44")
                .setImageUrl("https://user-gold-cdn.xitu.io/2018/7/13/16492d9b7877dc21?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));


        list.add(new NewInfo("Android/HTTP", "XHttp2 一个功能强悍的网络请求库，使用RxJava2 + Retrofit2 + OKHttp进行组装")
                .setSummary("一个功能强悍的网络请求库，使用RxJava2 + Retrofit2 + OKHttp组合进行封装。还不赶紧点击使用说明文档，体验一下吧！")
                .setDetailUrl("https://juejin.im/post/5b6b9b49e51d4576b828978d")
                .setImageUrl("https://user-gold-cdn.xitu.io/2018/8/9/1651c568a7e30e02?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        list.add(new NewInfo("源码", "Android源码分析--Android系统启动")
                .setSummary("其实Android系统的启动最主要的内容无非是init、Zygote、SystemServer这三个进程的启动，他们一起构成的铁三角是Android系统的基础。")
                .setDetailUrl("https://juejin.im/post/5c6fc0cdf265da2dda694f05")
                .setImageUrl("https://user-gold-cdn.xitu.io/2019/2/22/16914891cd8a950a?imageView2/0/w/1280/h/960/format/webp/ignore-error/1"));

        return list;
    }

    @Override
    public void onResponse(String actonString, String response) {
        NewInfo163 newInfo163 = null;

        switch (new163_type) {
            case "头条":
                newInfo163 = new Gson().fromJson(response, NewInfo163_toutiao.class);
                break;
            case "汽车":
                newInfo163 = new Gson().fromJson(response, NewInfo163_car.class);
                break;
            case "娱乐":
                newInfo163 = new Gson().fromJson(response, NewInfo163_yule.class);
                break;
            case "体育":
                newInfo163 = new Gson().fromJson(response, NewInfo163_tiyu.class);
                break;
            case "运动":
                newInfo163 = new Gson().fromJson(response, NewInfo163_yundong.class);
                break;
        }

        if (newInfo163!=null && newInfo163.getNewInfo163Items()!=null && newInfo163.getNewInfo163Items().size()>0){
            mAdapter.refresh(newInfo163.getNewInfo163Items());
        }
    }

    @Override
    public void onErrorResponse(String actonString, VolleyError error) {

    }
}
