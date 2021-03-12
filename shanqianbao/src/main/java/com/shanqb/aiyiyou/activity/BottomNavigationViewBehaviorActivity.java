/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.shanqb.aiyiyou.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shanqb.aiyiyou.R;
import com.shanqb.aiyiyou.tabview.BaseFragment;
import com.shanqb.aiyiyou.tabview.HomePageFragment;
import com.shanqb.aiyiyou.tabview.ProfileFragment;
import com.xuexiang.xui.adapter.FragmentAdapter;

import butterknife.BindView;

public class BottomNavigationViewBehaviorActivity extends MyBaseActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;


    int[] menuItemIds = new int[]{R.id.item_dashboard, R.id.item_photo,};
    String[] titles = new String[]{"主页", "我的"};


    @Override
    public void initLayout() {
        setContentView(R.layout.activity_bottom_navigationview_behavior);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initWeight() {
        initTitle();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<BaseFragment>(getSupportFragmentManager());
        adapter.addFragment(new HomePageFragment(), titles[0]);
        adapter.addFragment(new ProfileFragment(), titles[1]);

        viewPager.setOffscreenPageLimit(titles.length - 1);
        viewPager.setAdapter(adapter);

    }

    protected void initTitle() {
//        toolbar.setNavigationIcon(R.drawable.back_ic);
        toolbar.setTitle(getString(R.string.app_name));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        viewPager.addOnPageChangeListener(this);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }




    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigation.getMenu().getItem(position).setChecked(true);
        toolbar.setTitle(titles[position]);
//        bottomNavigation.setSelectedItemId(menuItemIds[position]);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.item_dashboard:
                viewPager.setCurrentItem(0, true);
                toolbar.setTitle(menuItem.getTitle());
                return true;
            case R.id.item_photo:
                viewPager.setCurrentItem(1, true);
                toolbar.setTitle(menuItem.getTitle());
                return true;
        }
        return false;
    }


}
