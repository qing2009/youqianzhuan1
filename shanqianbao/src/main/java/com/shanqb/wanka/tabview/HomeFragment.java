package com.shanqb.wanka.tabview;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shanqb.wanka.R;
import com.shanqb.wanka.adapter.RecyclerViewBannerAdapter2;
import com.shanqb.wanka.utils.DemoDataProvider;
import com.shanqb.wanka.utils.Utils;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yx on 16/4/3.
 */
public class HomeFragment extends BaseFragment implements ITabClickListener, BannerLayout.OnBannerItemClickListener {

    Unbinder unbinder;

//    @BindView(R.id.taskGetMoney_imgView)
//    RoundCornerImageView taskGetMoneyImgView;
    @BindView(R.id.bl_horizontal)
    BannerLayout blHorizontal;//轮播图

    private RecyclerViewBannerAdapter2 mAdapterHorizontal;//轮播图

    private ViewPager mViewPager;

    @BindView(R.id.home_kuaizhuan)
    ImageView imageView;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    //浮动按钮按下时x坐标
    private float downX;
    //浮动按钮按下时y坐标
    private float downY;
    /**
     * 按下时浮层x坐标
     */
    float downViewX = 0;

    @Override
    public void fetchData() {

        setShouyiView();
    }

    /**
     * 显示收益
     */
    private void setShouyiView() {
        Log.e("ProfileFragment", "setShouyiView() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPager = getActivity().findViewById(R.id.viewpager);
        //刷新总收益、可提现
        setShouyiView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        //轮播图
        blHorizontal.setAdapter(mAdapterHorizontal = new RecyclerViewBannerAdapter2(DemoDataProvider.urls));
        mAdapterHorizontal.setOnBannerItemClickListener(this);
//        touchImg();
        return view;
    }

    @Override
    public void onMenuItemClick() {

    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.home_mahua, R.id.home_xiaoshuo, R.id.home_xinwen, R.id.home_kuaizhuan, R.id.game1_statrt, R.id.game2_statrt, R.id.game3_statrt, R.id.game4_statrt, R.id.game5_statrt, R.id.game6_statrt, R.id.game7_statrt, R.id.game8_statrt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_mahua:
                Utils.goWeb(getContext(), "http://www.chinjgeriatr.com/m/index.html");
                break;
            case R.id.home_xiaoshuo:
                Utils.goWeb(getContext(), "https://3g.ali213.net/news/");
                break;
            case R.id.home_xinwen:
                Utils.goWeb(getContext(), "http://m.yxdown.com/news/");
                break;
            case R.id.home_kuaizhuan:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.game1_statrt:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/lvzitiaotiao");
                break;
            case R.id.game2_statrt:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/splash");
                break;
            case R.id.game3_statrt:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/zipai");
                break;
            case R.id.game4_statrt:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/hextris");
                break;
            case R.id.game5_statrt:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/xiaoniaofeifei/");
                break;
            case R.id.game6_statrt:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/jump/");
                break;
            case R.id.game7_statrt:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/daoguo/");
                break;
            case R.id.game8_statrt:
                Utils.goWeb(getContext(), "http://8.133.178.205/game/123suansu/");
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    public void touchImg(){
//        FloatingActionButton fab = this.getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //获取屏幕宽度
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        final int with = outMetrics.widthPixels;
//        final int height = outMetrics.heightPixels;
//        imageView.setVisibility(View.VISIBLE);
//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                //返回false，是点击事件；返回true，不记为点击事件 参考链接  https://blog.csdn.net/u014043113/article/details/74778414
//                switch (event.getAction()) {
//
//                    case MotionEvent.ACTION_DOWN:
//                        //按下
//                        downX = event.getX();
//                        downY = event.getY();
//                        downViewX = imageView.getX();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        //移动
//                        //移动的距离
//                        float moveX = event.getX() - downX;// event.getX() 移动的X距离
//                        float moveY = event.getY() - downY;// event.getY() 移动的Y距离
//                        //当前view= X,Y坐标
//                        float viewX = imageView.getX();
//                        float viewY = imageView.getY();
//                        //view的宽高
//                        int viewHeigth = imageView.getWidth();
//                        int viewWidth = imageView.getHeight();
//
//                        //X当超出屏幕,取最大值
//                        if (viewX + moveX + viewWidth > with) {
//                            //靠右
//                            imageView.setX(with - viewWidth);
//                        } else if (viewX + moveX <= 0) {
//                            //靠右
//                            imageView.setX(0);
//                        } else {
//                            //正常
//                            imageView.setX(viewX + moveX);
//                        }
//                        //Y当超出屏幕,取最大值
//                        if (viewY + moveY + viewHeigth > height) {
//                            //靠下
//                            imageView.setY(height - viewHeigth);
//                        } else if (viewY + moveY <= 0) {
//                            //靠上
//                            imageView.setY(0);
//                        } else {
//                            //正常
//                            imageView.setY(viewY + moveY);
//                        }
//                        return true;
//
//
//                    case MotionEvent.ACTION_UP:
//                        //松手
//                        float upX = imageView.getX();
//                        //屏幕中心点
//                        float center = with / 2;
//                        if (imageView.getX() > center) {
//                            //靠右
//                            imageView.setX(with - imageView.getWidth());
//                        } else {
//                            imageView.setX(0);
//                        }
//                        //按下时与松手时X值一致的话，就干点别的事情
//                        if (downViewX == upX) {
//                            return false;
//                        } else {
//                            return true;
//                        }
//                }
//
//                return false;
//            }
//        });
    }
}
