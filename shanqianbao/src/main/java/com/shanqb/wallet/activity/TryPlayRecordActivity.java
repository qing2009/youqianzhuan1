package com.shanqb.wallet.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shanqb.wallet.R;
import com.shanqb.wallet.bean.BaseJsonResponse2;
import com.shanqb.wallet.bean.TryPlayListResponse;
import com.shanqb.wallet.test.BaseRecyclerViewAdapter;
import com.shanqb.wallet.utils.AcitonConstants;
import com.shanqb.wallet.utils.BitmapCache;
import com.shanqb.wallet.utils.Global;
import com.shanqb.wallet.utils.NetworkUtils;
import com.shanqb.wallet.utils.SharedPreConstants;
import com.shanqb.wallet.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TryPlayRecordActivity extends MyBaseActivity {
    @BindView(R.id.recordRecyView)
    RecyclerView recordRecyView;
    @BindView(R.id.back_imgView)
    ImageView backImgView;
    @BindView(R.id.title_textView)
    TextView titleTextView;
//    @BindView(R.id.refresh_layout)
//    BaseSwipeRefreshLayout refreshLayout;

    private int page = 1;
    private int size = 1000;

    private LinearLayoutManager layoutManager;
    PlayListAdapter adapter;

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
        titleTextView.setText(getString(R.string.tryPlay));

        this.layoutManager = new LinearLayoutManager(this);
        this.layoutManager.setOrientation(1);
        this.recordRecyView.setLayoutManager(this.layoutManager);
        adapter = new PlayListAdapter();
        recordRecyView.setAdapter(adapter);
    }

    private void getOrderList() {
        if (!NetworkUtils.checkNetworkConnectionState(this)) {//未连接到网络
            Toast.makeText(this, getString(R.string.net_close), Toast.LENGTH_SHORT).show();
            return;

        } else {
            showLoadingDialog();

            String loginUrl = Global.BASE_INTER_URL + AcitonConstants.INTER_ORDERLIST;
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
                    map.put(AcitonConstants.ORDERLIST_MEMBERCODE, SharedPreferencesUtil.getStringValue(TryPlayRecordActivity.this, SharedPreConstants.merCode, ""));
                    map.put(AcitonConstants.ORDERLIST_PAGE, page + "");
                    map.put(AcitonConstants.ORDERLIST_SIZE, size + "");
                    return map;
                }
            };
            mQueue.add(stringRequest);
        }
    }


    @OnClick(R.id.back_imgView)
    public void onViewClicked() {
        onBackPressed();
    }


    class PlayListAdapter extends BaseRecyclerViewAdapter {

        List<TryPlayListResponse.DataBean> data;

        public PlayListAdapter() {
            super(R.layout.tryplay_record_item);
        }

        @Override
        public void onBindViewData(ViewHolder var1, int var2) {
            try {
                TryPlayListResponse.DataBean bean = data.get(var2);
                if (bean != null) {
                    ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
                    NetworkImageView gameIconImgView = (NetworkImageView) var1.getImageView(R.id.gameIcon_imageView);
                    gameIconImgView.setImageUrl(bean.getAppIcon(), imageLoader);
                    TextView income_textView = (TextView) var1.getTextView(R.id.income_textView);
                    income_textView.setText("+" + bean.getUserFee() + "元");
                    TextView gameName_textView = (TextView) var1.getTextView(R.id.gameName_textView);
                    gameName_textView.setText(bean.getAdName());
                    TextView gameDes_textView = (TextView) var1.getTextView(R.id.gameDes_textView);
                    gameDes_textView.setText("完成任务：" + bean.getTaskName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public void setData(List<TryPlayListResponse.DataBean> data) {
            this.data = data;
        }
    }


}
