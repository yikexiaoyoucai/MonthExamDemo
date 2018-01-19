package com.example.yujie.monthexamdemo.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yujie.monthexamdemo.R;
import com.example.yujie.monthexamdemo.base.BaseFragment;
import com.example.yujie.monthexamdemo.model.DataDataBean;
import com.example.yujie.monthexamdemo.presenter.PresenterImpl;
import com.example.yujie.monthexamdemo.presenter.IPresenter;
import com.example.yujie.monthexamdemo.util.Api;
import com.example.yujie.monthexamdemo.view.MainActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * Created by yujie on 2018/1/18.
 */

public class BZBM extends BaseFragment<IPresenter, PresenterImpl> implements IPresenter {

    private TextView date_text;
    private ViewPager viewPager;
    private List<DataDataBean.LayoutsBean> layouts;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bzbm, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        date_text = view.findViewById(R.id.date);
        viewPager = view.findViewById(R.id.view_pager);
    }

    @Override
    protected PresenterImpl initPresenter() {
        return new PresenterImpl();
    }

    @Override
    protected void initData() {
        presenter.getData(Api.BASE_URL, Api.URL, Api.getParams());
    }

    @Override
    public void onPreSuccess(DataDataBean dataDataBean) {
        layouts = dataDataBean.getLayouts();
        date_text.setText(dataDataBean.getDate());
        viewPager.setAdapter(new VPAdapter());
    }

    @Override
    public void onPreError() {

    }

    class VPAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return layouts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getActivity());
            simpleDraweeView.setAspectRatio((float) 1);
            //使用控件,并添加到布局中
            // 图片的地址
            Uri uri = Uri.parse(layouts.get(position).getPicUrl());
            // 图片的请求
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .build();
            // 加载图片的控制
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setOldController(simpleDraweeView.getController())
                    .setImageRequest(request)
                    .build();
            simpleDraweeView.setBackgroundColor(Color.WHITE);
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
            GenericDraweeHierarchy FIT_XY = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY).build();
            simpleDraweeView.setHierarchy(FIT_XY);
            // 加载图片
            simpleDraweeView.setController(controller);
            container.addView(simpleDraweeView);
            return simpleDraweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
