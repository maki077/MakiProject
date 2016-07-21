package com.maki.project.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maki.project.R;
import com.maki.project.view.fragment.Page1Fragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Administrator on 2016/7/19.
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.iv_toolbar_avatar)
    ImageView ivToolbarAvatar;
    @Bind(R.id.iv_toolbar_account_badge)
    ImageView ivToolbarAccountBadge;
    @Bind(R.id.iv_toolbar_notice_badge)
    ImageView ivToolbarNoticeBadge;
    @Bind(R.id.nick_name)
    TextView nickName;
    @Bind(R.id.ll_toolbar_navigation)
    LinearLayout llToolbarNavigation;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        intToolbar();
        initNavigationView();
        initFloatingActionButton();
        initViewPager();
    }


    private void intToolbar() {
        //toolbar.inflateMenu(R.menu.bilibili_toolbar_menu); //这里加载无效
        toolbar.setTitle("Title");//AppBarLayout的高度大于toolbar+tablayout 才能显示
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.transparent));//标题颜色
        toolbar.setSubtitle("Subtitle");
        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.transparent));//副标题颜色
        toolbar.setLogo(null);//logo
        toolbar.setNavigationIcon(null);//导航图标,最左边的图标
        setSupportActionBar(toolbar);
    }

    /**
     * 右上角menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bilibili_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.toolbar_game_center:
                Toast.makeText(MainActivity.this, "游戏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_menu_download:
                Toast.makeText(MainActivity.this, "下载", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_menu_search:
                Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.nick_name, R.id.ll_toolbar_navigation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nick_name:
                break;
            case R.id.ll_toolbar_navigation:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    /**
     * 侧滑页面点击事件处理
     */
    public void initNavigationView() {
        if (navView != null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int drawerWidth = displayMetrics.widthPixels * 1 / 2; //侧滑宽度
            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(drawerWidth, DrawerLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.START;
            navView.setLayoutParams(params);
            View headerView = navView.getHeaderView(0);
            initNavigationViewHeadView(headerView);
            setupDrawerContent(navView);
        }
    }

    private void initNavigationViewHeadView(View headerView) {
        if (headerView != null) {
            ImageView headImageView = (ImageView) headerView.findViewById(R.id.user_avatar);
            headImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "去登录....", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();//关闭抽屉
                }
            });

            TextView nickName = (TextView) headerView.findViewById(R.id.user_nick_text);
            nickName.setText("BilibiliAA");
        }
    }

    /**
     * 侧滑 menuitem 点击监听
     *
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_home: {
                        Toast.makeText(MainActivity.this, "首页", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.nav_offline_manager: {
                        Toast.makeText(MainActivity.this, "离线缓存", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.nav_favorites: {
                        Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.nav_histories: {
                        Toast.makeText(MainActivity.this, "历史记录", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.nav_following: {
                        Toast.makeText(MainActivity.this, "关注的人", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.nav_pay: {
                        Toast.makeText(MainActivity.this, "我的钱包", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.nav_theme: {
                        Toast.makeText(MainActivity.this, "主题选择", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.nav_app_wall: {
                        Toast.makeText(MainActivity.this, "应用推荐", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_settings: {
                        Toast.makeText(MainActivity.this, "设置与帮助", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    default: {
                        break;
                    }
                }
                item.setChecked(true);//点击了把它设为选中状态
                drawerLayout.closeDrawers();//关闭抽屉
                return true;
            }
        });
    }

    /**
     *
     */
    private void initFloatingActionButton() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }
    }

    private void initViewPager(){


        ArrayList<Fragment> fgList = new ArrayList<>();
        fgList.add(new Page1Fragment());
        fgList.add(new Page1Fragment());
        fgList.add(new Page1Fragment());
        String[] tabTitle = {"直播","动画","游戏"};
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(),fgList,tabTitle);
        viewpager.setAdapter(adapter);
        tabs.setupWithViewPager(viewpager);//tab 和 viewpager同步
    }



    private class MyViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fgList;
        private String[] tabTitle;

        private MyViewPagerAdapter(FragmentManager fm){
            super(fm);
        }
        private MyViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fgList,String[] tabTitle){
            super(fm);
            this.fgList = fgList;
            this.tabTitle = tabTitle;
        }

        @Override
        public Fragment getItem(int position) {
            return fgList.get(position);
        }

        @Override
        public int getCount() {
            return fgList.size();
        }
        /**
         * 和pagerview同步
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }








}
