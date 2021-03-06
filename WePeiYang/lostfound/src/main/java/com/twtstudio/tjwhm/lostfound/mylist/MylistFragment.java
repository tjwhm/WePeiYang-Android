package com.twtstudio.tjwhm.lostfound.mylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twtstudio.tjwhm.lostfound.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tjwhm on 2017/7/4.
 **/

public class MylistFragment extends Fragment implements MylistContract.MylistView {

    @BindView(R.id.mylist_recyclerView)
    RecyclerView mylist_recyclerView;
    @BindView(R.id.mylist_progress)
    ProgressBar mylist_progress;
    @BindView(R.id.mylist_nodata)
    LinearLayout mylist_nodata;

    private MylistTableAdapter tableAdapter;
    private LinearLayoutManager layoutManager;
    private MylistBean mylistBean;
    private String lostOrFound;
    MylistContract.MylistPresenter mylistPresenter = new MylistPresenterImpl(this);

    public static MylistFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString("index", type);
        MylistFragment fragment = new MylistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mylist, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        lostOrFound = bundle.getString("index");
        initValues();
        // TODO: 2017/7/7 做我的列表的下拉加载下一页 
        mylistPresenter.loadMylistData(lostOrFound, 1);
        return view;
    }

    @Override
    public void setMylistData(MylistBean mylistBean) {
        this.mylistBean.error_code = mylistBean.error_code;
        this.mylistBean.message = mylistBean.message;
        this.mylistBean.data = mylistBean.data;
        tableAdapter.notifyDataSetChanged();
        mylist_progress.setVisibility(View.GONE);
        if (this.mylistBean.data.size() == 0) {
            mylist_nodata.setVisibility(View.VISIBLE);
        } else {
            mylist_nodata.setVisibility(View.GONE);
        }
    }

    private void initValues() {
        mylist_nodata.setVisibility(View.GONE);
        mylist_progress.setVisibility(View.VISIBLE);
        mylistBean = new MylistBean();
        mylistBean.data = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        mylist_recyclerView.setLayoutManager(layoutManager);
        tableAdapter = new MylistTableAdapter(mylistBean, getActivity(),lostOrFound);
        mylist_recyclerView.setAdapter(tableAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 2017/7/7 做我的列表的下拉加载下一页
        mylistPresenter.loadMylistData(lostOrFound,1);
    }
}
