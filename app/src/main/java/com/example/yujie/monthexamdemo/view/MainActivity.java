package com.example.yujie.monthexamdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yujie.monthexamdemo.R;
import com.example.yujie.monthexamdemo.model.DataDataBean;
import com.example.yujie.monthexamdemo.util.Api;
import com.example.yujie.monthexamdemo.util.RetrofitHelper;
import com.example.yujie.monthexamdemo.view.fragment.BQML;
import com.example.yujie.monthexamdemo.view.fragment.BZBM;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image_left;
    private TextView bzbm;
    private TextView bqml;
    private ImageView image_right;
    private FrameLayout frame_layout;
    private String str;
    private ArrayList<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        hideActionBar();

        switchFragment(0);

        RetrofitHelper.getApiService(Api.BASE_URL)
                .getData(Api.URL, Api.getParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        imageUrls = new ArrayList<>();
                        Gson gson = new Gson();
                        DataDataBean dataDataBean = gson.fromJson(s, DataDataBean.class);
                        List<DataDataBean.LayoutsBean> layouts = dataDataBean.getLayouts();
                        for (int i = 0; i < layouts.size(); i++) {
                            imageUrls.add(layouts.get(i).getPicUrl());
                        }
                    }
                });
    }

    private void switchFragment(int index) {
        switch (index) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BZBM()).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BQML()).commit();
                break;
            default:
                break;
        }
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void initView() {
        image_left = (ImageView) findViewById(R.id.image_left);
        bzbm = (TextView) findViewById(R.id.bzbm);
        bqml = (TextView) findViewById(R.id.bqml);
        image_right = (ImageView) findViewById(R.id.image_right);
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);

        image_left.setOnClickListener(this);
        bzbm.setOnClickListener(this);
        bqml.setOnClickListener(this);
        image_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_left:
                str.contains("error");
                break;
            case R.id.bzbm:
                switchFragment(0);
                break;
            case R.id.bqml:
                switchFragment(1);
                break;
            case R.id.image_right:
                Intent intent = new Intent(MainActivity.this, PicActivity.class);
                intent.putStringArrayListExtra("list", imageUrls);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
