package com.maki.project.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.maki.project.R;
import com.maki.project.utils.ToastUtil;
import com.maki.project.view.fragment.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.fragment_content_layout)
    FrameLayout fragmentContentLayout;

    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            setDefaultFragment();
        }
        intToolbar();
    }

    private void setDefaultFragment() {
        if (mLoginFragment == null) {
            Bundle bundle = new Bundle();
            mLoginFragment = new LoginFragment();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_content_layout, mLoginFragment, "mLoginFragment");
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_login_menu, menu);
        return true;
    }

    private void intToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("");
            toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));//标题颜色
            toolbar.setSubtitle("");
            toolbar.setSubtitleTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));//副标题颜色
            toolbar.setLogo(null);
            toolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.toolbar_back_icon));//导航图标,最左边的图标
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(LoginActivity.this, R.string.turn_back);
                }
            });
        }else{
            System.out.println("toolbar:"+toolbar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.forget_password: {
                ToastUtil.showToast(LoginActivity.this, getResources().getString(R.string.forget_password));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
