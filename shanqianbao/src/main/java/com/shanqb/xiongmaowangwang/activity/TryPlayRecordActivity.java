package com.shanqb.xiongmaowangwang.activity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shanqb.xiongmaowangwang.R;
import com.shanqb.xiongmaowangwang.bean.BaseJsonResponse2;
import com.shanqb.xiongmaowangwang.bean.TryPlayListResponse;
import com.shanqb.xiongmaowangwang.utils.ActionConstants;
import com.shanqb.xiongmaowangwang.utils.Global;
import com.shanqb.xiongmaowangwang.utils.NetworkUtils;
import com.shanqb.xiongmaowangwang.utils.SharedPreConstants;
import com.shanqb.xiongmaowangwang.utils.SharedPreferencesUtil;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.shanqb.xiongmaowangwang.adapter.PlayListAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TryPlayRecordActivity extends MyBaseActivity {
    @BindView(R.id.recordRecyView)
    RecyclerView recordRecyView;
    @BindView(R.id.tryPlay_titlebartryPlay_titlebar)
    TitleBar tryPlayTitlebartryPlayTitlebar;
//    @BindView(R.id.refresh_layout)
//    BaseSwipeRefreshLayout refreshLayout;

    private int page = 1;
    private int size = 1000;

    private LinearLayoutManager layoutManager;
    private PlayListAdapter adapter;

    @Override
    public void initLayout() {
        setContentView(R.layout.activity_tryplay_record);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void initData() {
        getOrderList();
    }


    @Override
    public void initWeight() {
        tryPlayTitlebartryPlayTitlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        this.layoutManager = new LinearLayoutManager(this);
        this.layoutManager.setOrientation(1);
        this.recordRecyView.setLayoutManager(this.layoutManager);
        adapter = new PlayListAdapter(mQueue);
        recordRecyView.setAdapter(adapter);
    }

    private void getOrderList() {
        if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
            Toast.makeText(this, getString(R.string.net_close), Toast.LENGTH_SHORT).show();
            return;

        } else {
            showLoadingDialog();

            String loginUrl = Global.BASE_INTER_URL + ActionConstants.INTER_ORDERLIST;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(getClass().toString(), response);
                    dismissLoadingDialog();

                    TryPlayListResponse listResponse = new Gson().fromJson(response, new TypeToken<TryPlayListResponse>() {
                    }.getType());
                    if (listResponse != null) {
                        if (BaseJsonResponse2.STATE_SUCCESS.equals(listResponse.getState())) {

                            if (listResponse.getData() != null && listResponse.getData().size() > 0) {

                                adapter.setData(listResponse.getData());
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(TryPlayRecordActivity.this, getString(R.string.noRecord), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(TryPlayRecordActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TryPlayRecordActivity.this, getString(R.string.load_error), Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissLoadingDialog();
                    Log.e(getClass().toString(), error.getMessage(), error);
                    Toast.makeText(TryPlayRecordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(ActionConstants.ORDERLIST_MEMBERCODE, SharedPreferencesUtil.getStringValue(TryPlayRecordActivity.this, SharedPreConstants.merCode, ""));
                    map.put(ActionConstants.ORDERLIST_PAGE, page + "");
                    map.put(ActionConstants.ORDERLIST_SIZE, size + "");
                    return map;
                }
            };
            mQueue.add(stringRequest);
        }
    }





}
