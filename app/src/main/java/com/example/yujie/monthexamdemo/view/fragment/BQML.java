package com.example.yujie.monthexamdemo.view.fragment;

import android.database.DataSetObserver;
import android.graphics.drawable.BitmapDrawable;
import android.net.IpPrefix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yujie.monthexamdemo.R;
import com.example.yujie.monthexamdemo.base.BaseFragment;
import com.example.yujie.monthexamdemo.model.DataDataBean;
import com.example.yujie.monthexamdemo.presenter.IPresenter;
import com.example.yujie.monthexamdemo.presenter.PresenterImpl;
import com.example.yujie.monthexamdemo.util.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yujie on 2018/1/18.
 */

public class BQML extends BaseFragment<IPresenter, PresenterImpl> implements IPresenter, View.OnClickListener {

    private TextView date_text;
    private TextView mldh;
    private ExpandableListView listView;
    private List<DataDataBean.LayoutsBean> layouts;
    private PopupWindow popupWindow;
    private List<String> dhs;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bqml, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        date_text = view.findViewById(R.id.date);
        mldh = view.findViewById(R.id.mldh);
        listView = view.findViewById(R.id.list_view);

        mldh.setOnClickListener(this);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(getContext(), layouts.get(i).getList().get(i1).getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
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
        dhs = new ArrayList<>();
        date_text.setText(dataDataBean.getDate());
        layouts = dataDataBean.getLayouts();
        listView.setAdapter(new ELAdapter());
        for (int i = 0; i < layouts.size(); i++) {
            dhs.add(layouts.get(i).getName());
            listView.expandGroup(i);
        }
    }

    @Override
    public void onPreError() {

    }

    @Override
    public void onClick(View view) {
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.mldh, null, false);
        ListView list = view1.findViewById(R.id.list_view);
        list.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dhs));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listView.setSelectedGroup(i);
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(view1,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(mldh);
    }

    class ELAdapter extends BaseExpandableListAdapter{


        @Override
        public int getGroupCount() {
            return layouts.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return layouts.get(i).getList().size();
        }

        @Override
        public Object getGroup(int i) {
            return layouts.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return layouts.get(i).getList().get(i1).getTitle();
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            GroupViewHolder holder;
            if(view == null){
                view = View.inflate(getContext(), R.layout.layout_group, null);
                holder = new GroupViewHolder();
                holder.group_name = view.findViewById(R.id.group_name);
                view.setTag(holder);
            }else{
                holder = (GroupViewHolder) view.getTag();
            }

            holder.group_name.setText(layouts.get(i).getName());

            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            ChildViewHolder holder;
            if(view == null){
                view = View.inflate(getContext(), R.layout.layout_child, null);
                holder = new ChildViewHolder();
                holder.child_title = view.findViewById(R.id.child_title);
                view.setTag(holder);
            }else{
                holder = (ChildViewHolder) view.getTag();
            }

            holder.child_title.setText(layouts.get(i).getList().get(i1).getTitle());

            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        class GroupViewHolder{
            TextView group_name;
        }

        class ChildViewHolder{
            TextView child_title;
        }
    }
}
