package com.maki.project.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maki.project.R;
import com.maki.project.base.activity.ToolBarActivity;
import com.maki.project.model.bean.Meizi;
import com.maki.project.presenter.MeiziMainPresenter;
import com.maki.project.ui.adapter.MeiziAdapter;
import com.maki.project.ui.iview.IMeiziMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Administrator on 2016/8/8.
 */
public class MeiziMainActivity extends ToolBarActivity<MeiziMainPresenter> implements IMeiziMainView {
    private MeiziAdapter adapter;
    private MeiziMainPresenter presenter;
    private List<Meizi> meiziList;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @OnClick(R.id.fab)
    public void onClick() {
    }

    /**
     * BaseActivity
     * @return
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_meizi_main;
    }

    /**
     * BaseActivity
     * BasePresenter presenter.init() 执行iView.initView();
     * presenter 持有iview  并执行iveiew.func()
     */
    @Override
    protected void initPresenter() {
        presenter = new MeiziMainPresenter(this,this);
        presenter.init();
    }

    /**
     * IBaseView接口
     */
    @Override
    public void initView() {
        meiziList = new ArrayList<>();
        meiziList.add(getMeiziData());
        meiziList.add(getMeiziData());
        meiziList.add(getMeiziData());
        meiziList.add(getMeiziData());
        adapter = new MeiziAdapter(meiziList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        presenter.fetchMeiziData(1);
    }

    int iii=0;

    public Meizi  getMeiziData(){
        Meizi  bean= new Meizi();
        bean.set_id((iii++)+"");
        return bean;

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showNoMoreData() {

    }

    @Override
    public void showMeiziList(List<Meizi> meiziList) {
        this.meiziList.clear();
        this.meiziList.addAll(meiziList);
        adapter.notifyDataSetChanged();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
       // ButterKnife.bind(this);
    }


}
