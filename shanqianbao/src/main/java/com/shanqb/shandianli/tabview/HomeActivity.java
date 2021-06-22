package com.shanqb.shandianli.tabview;

import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pceggs.workwall.util.PceggsWallUtils;
import com.shanqb.shandianli.R;
import com.shanqb.shandianli.activity.MyBaseActivity;
import com.shanqb.shandianli.bean.LoginResponse;
import com.shanqb.shandianli.utils.ActionConstants;
import com.shanqb.shandianli.utils.Global;
import com.shanqb.shandianli.utils.NetworkUtils;
import com.shanqb.shandianli.utils.SharedPreConstants;
import com.shanqb.shandianli.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends MyBaseActivity implements TabLayout.OnTabClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<TabItem> tabs;
    private ActionBar actionBar;

    ArrayList<BaseFragment> baseFragments = new ArrayList<>();


    @Override
    public void initLayout() {
        setContentView(R.layout.activity_home);
        //初始化8.0配置
        PceggsWallUtils.setAuthorities(getPackageName()+".fileprovider");
    }

    @Override
    public void initBundleData() {

    }

    public void initData() {
    }

    @Override
    public void initWeight() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
//            actionBar.setTitle(R.string.app_name);
//            actionBar.setBackgroundDrawable(getDrawable(R.drawable.home_actionbar_bg));
        }

        tabs = new ArrayList<>();
//        tabs.add(new TabItem(R.drawable.selector_tab_homepage, R.string.homePage, R.string.app_name, HomePageFragment.class));
//        tabs.add(new TabItem(R.drawable.selector_tab_profile, R.string.me, R.string.me, ProfileFragment.class));
        tabs.add(new TabItem(R.drawable.selector_tab_homepage, R.string.homePage, R.string.app_name));
        tabs.add(new TabItem(R.drawable.selector_tab_zhuanqian, R.string.get_money, R.string.get_money));
//        tabs.add(new TabItem(R.drawable.selector_tab_faxian, R.string.faxian, R.string.faxian));
        tabs.add(new TabItem(R.drawable.selector_tab_faxian, R.string.qiandao, R.string.qiandao));
        tabs.add(new TabItem(R.drawable.selector_tab_profile, R.string.me, R.string.me));

        mTabLayout.initData(tabs, this);
        mTabLayout.setCurrentTab(0);

//        baseFragments.add(new NewsFragment());
        baseFragments.add(new HomeFragment());
//        baseFragments.add(new VideosFragment());
        baseFragments.add(new HomePageFragment());
//        baseFragments.add(new FaxianFragment());
        baseFragments.add(new QiandaoFragment());
        baseFragments.add(new ProfileFragment());

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(),baseFragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {

                    mTabLayout.setCurrentTab(position);
//                    actionBar.setTitle(tabs.get(position).titleResId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabClick(TabItem tabItem) {
        try {
//            actionBar.setTitle(tabItem.titleResId);
            mViewPager.setCurrentItem(tabs.indexOf(tabItem));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public class FragAdapter extends FragmentPagerAdapter {


        ArrayList<BaseFragment> baseFragments;

        public FragAdapter(FragmentManager fm,ArrayList<BaseFragment> baseFragments) {
            super(fm);
            this.baseFragments = baseFragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            BaseFragment fragment = null;
            try {

                if (baseFragments!=null && arg0<baseFragments.size()){
                    fragment = baseFragments.get(arg0);
                }
//                return tabs.get(arg0).tagFragmentClz.newInstance();

//            } catch (InstantiationException e) {
//                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return baseFragments==null?0:baseFragments.size();
        }

    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        getUserInfo();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
        Log.d(getLocalClassName(), "onResume() called");
    }


    public void getUserInfo() {
        try {
            if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
                return;
            }

            String merCode = SharedPreferencesUtil.getStringValue(this,SharedPreConstants.merCode,"");
            if (!TextUtils.isEmpty(merCode)) {

                showLoadingDialog();

                String loginUrl = Global.BASE_INTER_URL + ActionConstants.INTER_MYINFO;
                Log.d(getLocalClassName(), "loginUrl: "+loginUrl);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(getClass().toString(), response);
                        dismissLoadingDialog();

                        LoginResponse loginResponse = new Gson().fromJson(response, new TypeToken<LoginResponse>() {
                        }.getType());
                        if (loginResponse != null) {
                            if (loginResponse.isSuccess()) {

                                if (loginResponse.getData() != null) {
                                    SharedPreferences preferences = SharedPreferencesUtil.getInterface(getApplicationContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt(SharedPreConstants.userid, loginResponse.getData().getId());
                                    editor.putString(SharedPreConstants.loginCode, loginResponse.getData().getLoginCode());
//                                    editor.putString(SharedPreConstants.loginPwd, loginResponse.getData().getLoginPwd());
                                    editor.putString(SharedPreConstants.realName, loginResponse.getData().getRealName());
                                    editor.putString(SharedPreConstants.merCode, loginResponse.getData().getMerCode());
                                    editor.putString(SharedPreConstants.merName, loginResponse.getData().getMerName());
                                    editor.putString(SharedPreConstants.merPhoto, loginResponse.getData().getMerPhoto());
                                    editor.putString(SharedPreConstants.merPhone, loginResponse.getData().getMerPhone());
                                    editor.putString(SharedPreConstants.allAmt, loginResponse.getData().getAllAmt()+"");
                                    editor.putString(SharedPreConstants.txAmt, loginResponse.getData().getTxAmt()+"");
                                    editor.putString(SharedPreConstants.shareCode, loginResponse.getData().getShareCode() + "");
                                    editor.commit();


//                                    try {
//                                        for (BaseFragment baseFragment:baseFragments) {
//                                            baseFragment.fetchData();
//                                        }
//
////                                    } catch (InstantiationException e) {
////                                        e.printStackTrace();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }

                                } else {
                                    Toast.makeText(HomeActivity.this, getString(R.string.getUserInfo_error), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(HomeActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(HomeActivity.this, getString(R.string.getUserInfo_error), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissLoadingDialog();
                        Log.e(getClass().toString(), error.getMessage(), error);
                        Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(ActionConstants.MERCODE, merCode);
                        return map;
                    }
                };
                mQueue.add(stringRequest);

            }
        } catch (Exception e) {
            e.printStackTrace();
            dismissLoadingDialog();
        }
    }
}
