package com.shanqb.demo.tabview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.Gson;
import com.shanqb.demo.R;
import com.shanqb.demo.adapter.QiandaoRecordAdapter;
import com.shanqb.demo.bean.QiandaoItemBean;
import com.shanqb.demo.utils.QiandaoRecord;
import com.shanqb.demo.utils.SharedPreConstants;
import com.shanqb.demo.utils.SharedPreferencesUtil;
import com.shanqb.demo.utils.XToastUtils;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QiandaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QiandaoFragment extends BaseFragment {

    Unbinder unbinder;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.qiandaoSumtextView)
    TextView qiandaoSumtextView;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.qiandaoBtn)
    Button qiandaoBtn;
    @BindView(R.id.qiandaoRecyclerView)
    RecyclerView qiandaoRecyclerView;
    @BindView(R.id.qiandaoHead_imageView)
    ImageView qiandaoHeadImageView;
    @BindView(R.id.qiandao_consLayout)
    ConstraintLayout qiandaoConsLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int qiandaoSumInteger;//累计签到天数
    private QiandaoRecord qiandaoRecord;
    private QiandaoRecordAdapter adapter;

    public QiandaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QiandaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QiandaoFragment newInstance(String param1, String param2) {
        QiandaoFragment fragment = new QiandaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    /**
     * 初始化签到数据
     *
     * @return
     */
    private QiandaoRecord initQiandaoData() {
        QiandaoRecord qiandaoRecordTemp = new QiandaoRecord();
        qiandaoRecordTemp.setWeekofYear(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));

        List<QiandaoItemBean> qiandaoItemBeanList = new ArrayList<>();
        qiandaoItemBeanList.add(new QiandaoItemBean(QiandaoItemBean.benzhou.周一, false));
        qiandaoItemBeanList.add(new QiandaoItemBean(QiandaoItemBean.benzhou.周二, false));
        qiandaoItemBeanList.add(new QiandaoItemBean(QiandaoItemBean.benzhou.周三, false));
        qiandaoItemBeanList.add(new QiandaoItemBean(QiandaoItemBean.benzhou.周四, false));
        qiandaoItemBeanList.add(new QiandaoItemBean(QiandaoItemBean.benzhou.周五, false));
        qiandaoItemBeanList.add(new QiandaoItemBean(QiandaoItemBean.benzhou.周六, false));
        qiandaoItemBeanList.add(new QiandaoItemBean(QiandaoItemBean.benzhou.周日, false));
        qiandaoRecordTemp.setQiandaoItemBeanList(qiandaoItemBeanList);


        String qiandaoJson = new Gson().toJson(qiandaoRecordTemp);
        SharedPreferencesUtil.setStringValue(getActivity().getApplicationContext(), SharedPreConstants.QIANDAO_RECORD, qiandaoJson);

        return qiandaoRecordTemp;
    }

    @Override
    public void fetchData() {

        //设置签到页背景图
        setQiandaoBG();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        qiandaoRecyclerView.setLayoutManager(layoutManager);

        qiandaoSumInteger = SharedPreferencesUtil.getIntValue(getActivity(), SharedPreConstants.QIANDAO_SUM, 0);
        String qiandaoRecordJson = SharedPreferencesUtil.getStringValue(getActivity().getApplicationContext(), SharedPreConstants.QIANDAO_RECORD, "");


        if (!TextUtils.isEmpty(qiandaoRecordJson)) {
            qiandaoRecord = new Gson().fromJson(qiandaoRecordJson, QiandaoRecord.class);
            if (qiandaoRecord != null) {

                int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
                if (week == qiandaoRecord.getWeekofYear()) {//有本周数据
                    QiandaoItemBean qiandaoItemBean = qiandaoRecord.getQiandaoItemBeanList().get(getQiandanIndex());
                    if (qiandaoItemBean.isQiandao()) {
                        qiandaoBtn.setEnabled(false);
                        qiandaoBtn.setText(getString(R.string.jingriyiqiandao));
                    }

                    initQiandaoRevcordView();

                } else {//没有本周数据，需要重新初始化
                    qiandaoRecord = initQiandaoData();
                    initQiandaoRevcordView();
                }

            } else {
                //初始化签到数据
                qiandaoRecord = initQiandaoData();
                initQiandaoRevcordView();
            }
        } else {
            //初始化签到数据
            qiandaoRecord = initQiandaoData();
            initQiandaoRevcordView();
        }
        refreshQiandaoSumView();
    }


    /**
     * 获取当期日期时本周的第几天。从周一开始，周一为0
     * @return
     */
    private int getQiandanIndex() {
        int dayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);//本周第几天。从周日开始，周日为1
        Log.e(getTag(), "onCreate: dayIndex=" + dayIndex);
        int qiandaoIndex;
        if (dayIndex==1){//如果时周日，需要修改值为6
            qiandaoIndex = 6;
        }else {
            qiandaoIndex = dayIndex - 2;
        }
        return qiandaoIndex;
    }

    private void setQiandaoBG() {

        String qdbgImageUrl = SharedPreferencesUtil.getStringValue(this.getContext().getApplicationContext(),SharedPreConstants.qdbg,"");
        if (!TextUtils.isEmpty(qdbgImageUrl)){
            ImageRequest imageRequest = new ImageRequest(
                    qdbgImageUrl,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            qiandaoConsLayout.setBackground(DrawableUtils.bitmap2Drawable(response));
                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            myVolley.getmQueue().add(imageRequest);
        }

    }

    private void initQiandaoRevcordView() {
        adapter = new QiandaoRecordAdapter(qiandaoRecord.getQiandaoItemBeanList());

        GridLayoutManager channelLayoutManager = new GridLayoutManager(getActivity(), 7);
        qiandaoRecyclerView.setLayoutManager(channelLayoutManager);
        qiandaoRecyclerView.addItemDecoration(new GridDividerItemDecoration(getContext(), 7));
        qiandaoRecyclerView.setHasFixedSize(true);
        qiandaoRecyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qiandao, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.qiandaoBtn)
    public void onClick() {

        qiandaoSumInteger = qiandaoSumInteger + 1;
        SharedPreferencesUtil.setIntValue(getActivity(), SharedPreConstants.QIANDAO_SUM, qiandaoSumInteger);

        qiandaoRecord.getQiandaoItemBeanList().get(getQiandanIndex()).setQiandao(true);
        adapter.notifyDataSetChanged();

        XToastUtils.toast(getString(R.string.qiandao_success_tishi));

        String qiandaoJson = new Gson().toJson(qiandaoRecord);
        SharedPreferencesUtil.setStringValue(getActivity().getApplicationContext(), SharedPreConstants.QIANDAO_RECORD, qiandaoJson);

        qiandaoBtn.setEnabled(false);
        qiandaoBtn.setText(getString(R.string.jingriyiqiandao));

        refreshQiandaoSumView();
    }

    private void refreshQiandaoSumView() {

        qiandaoSumtextView.setText(qiandaoSumInteger + "天");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}