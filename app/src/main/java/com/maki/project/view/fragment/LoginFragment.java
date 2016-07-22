package com.maki.project.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.maki.project.R;
import com.maki.project.utils.ColorBitmapUtil3;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录页面
 */
public class LoginFragment extends Fragment {
    @BindView(R.id.bili_logo)
    ImageView biliLogo;
    @BindView(R.id.iv_22)
    ImageView iv22;
    @BindView(R.id.iv_33)
    ImageView iv33;

    @BindView(R.id.ed_user_nickname)
    AutoCompleteTextView edUserNickname;
    @BindView(R.id.ed_user_pwd)
    EditText edUserPwd;

    @BindView(R.id.v_nick_name_bottom_line)
    View vNickNameBottomLine;
    @BindView(R.id.v_pass_word_bottom_line)
    View vPassWordBottomLine;

    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.ll_clear_name_layout)
    LinearLayout llClearNameLayout;
    @BindView(R.id.ll_clear_pwd_layout)
    LinearLayout llClearPwdLayout;

    @BindView(R.id.iv_clear_nick_name)
    ImageView ivClearNickName;
    @BindView(R.id.iv_clear_pass_word)
    ImageView ivClearPassWord;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;


    @BindView(R.id.button_layout)
    LinearLayout buttonLayout;
    @BindView(R.id.iv_test)
    ImageView ivTest;
    @BindView(R.id.tips_layout)
    RelativeLayout tipsLayout;
    @BindView(R.id.rl_login_root_layout)
    RelativeLayout rlLoginRootLayout;

    View mView;
    private boolean mNickNameHasFocus;
    private boolean mPassWordHasFocus;

    private float mNickNameLayoutHeightPx;
    private float mEditBottomLineHeightPx;
    private float mTipLayoutHeightPx;
    private float mNickNameAutoTextViewDropDownHeight;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }
        try {
            mView = inflater.inflate(R.layout.fragment_login_layout, container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ButterKnife.bind(this, mView);
        initViewValue();
        getBitmap();



        return mView;
    }


    private void initViewValue(){
        mNickNameHasFocus = true;
        mPassWordHasFocus = false;

        mTipLayoutHeightPx = getActivity().getResources().getDimension(R.dimen.login_tips_height);
        mNickNameLayoutHeightPx = getActivity().getResources().getDimension(R.dimen.login_edit_layout_height);
        mEditBottomLineHeightPx = getActivity().getResources().getDimension(R.dimen.edit_bottom_line_height);

        edUserNickname.setDropDownBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.autocomplete_text_bg));
        vNickNameBottomLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        vPassWordBottomLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_dark));

    }


    private  void getBitmap(){



    }


    /**
     * 图片着色  粉红
     * @return
     */
    private Bitmap getColorBitmap() {
        int color = ContextCompat.getColor(getContext(), R.color.theme_color_secondary);
        return ColorBitmapUtil3.getColorBitmap(getContext().getResources(), R.mipmap.ic_login_username_default, color, 255);
    }















}
