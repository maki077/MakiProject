package com.maki.project.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maki.project.R;
import com.maki.project.base.activity.ToolBarActivity;
import com.maki.project.model.bean.Meizi;
import com.maki.project.presenter.MeiziMainPresenter;
import com.maki.project.ui.adapter.MeiziAdapter;
import com.maki.project.ui.iview.IMeiziMainView;
import com.maki.project.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Administrator on 2016/8/8.
 */
public class MeiziMainActivity extends ToolBarActivity<MeiziMainPresenter> implements IMeiziMainView ,SwipeRefreshLayout.OnRefreshListener{
    private MeiziAdapter adapter;
    private MeiziMainPresenter presenter;
    private List<Meizi> meiziList;
    private int page=1;
    private boolean isRefresh = true;
    private boolean canLoading = true;
    private boolean isScrollingToBottom = true;

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
        adapter = new MeiziAdapter(meiziList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        onUpScroll();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.fetchMeiziData(page);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // recyclerView.smoothScrollToPosition(0);
                ToastUtil.showToast(MeiziMainActivity.this,"toobar");
            }
        });
    }
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.fetchMeiziData(page);
    }

    /**
     * 向上滑动
     */
    public void onUpScroll(){
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isScrollingToBottom = dy > 0;
//                if (floatingActionButton != null) {
//                    if (isScrollingToBottom) {
//                        if (floatingActionButton.isShown())
//                            floatingActionButton.hide();
//                    } else {
//                        if (!floatingActionButton.isShown())
//                            floatingActionButton.show();
//                    }
//                }
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && isScrollingToBottom) {
                        if (canLoading) {
                            presenter.fetchMeiziData(page);
                            canLoading = false;
                        }
                    }
                }
            }
        });
    }



    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        ToastUtil.showToast(this,"全部加载完毕");
    }

    @Override
    public void showMeiziList(List<Meizi> meiziList) {
        canLoading = true;
        page++;

        if(isRefresh){
            this.meiziList.clear();
            this.meiziList.addAll(meiziList);
            isRefresh = false;
        }else{
            this.meiziList.addAll(meiziList);
        }
        adapter.notifyDataSetChanged();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
       // ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }


}
