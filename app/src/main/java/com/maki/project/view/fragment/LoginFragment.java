package com.maki.project.view.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.maki.project.R;
import com.maki.project.utils.ColorBitmapUtil3;
import com.maki.project.utils.ToastUtil;
import com.maki.project.view.adapter.AutoTextViewAdapter;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 登录页面
 */
public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();

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

    private Bitmap mNickNameFocusedBitmap;
    private Bitmap mPassWordFocusedBitmap;
    //邮箱自动补全
    private AutoTextViewAdapter mAutoTextViewAdapter;
    private String[] mAUTO_EMAILS;
    //Rxjava
    Subscription mSubscription;


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
        initAutoTextAdapter();

        return mView;
    }


    private void initAutoTextAdapter() {
        mAUTO_EMAILS = getActivity().getResources().getStringArray(R.array.mail_filter);
        mAutoTextViewAdapter = new AutoTextViewAdapter(getActivity());
        edUserNickname.setAdapter(mAutoTextViewAdapter);
        edUserNickname.setThreshold(1);//输入1个字符时就开始检测，默认为2个

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int newContentHeight =rlLoginRootLayout.getHeight();
        mNickNameAutoTextViewDropDownHeight = newContentHeight - (mNickNameLayoutHeightPx + mTipLayoutHeightPx + mEditBottomLineHeightPx);

        if (mNickNameAutoTextViewDropDownHeight < 0) {
            mNickNameAutoTextViewDropDownHeight = newContentHeight - mNickNameLayoutHeightPx - mEditBottomLineHeightPx;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // edUserNickname.setDropDownHeight((int) mNickNameAutoTextViewDropDownHeight);
                edUserNickname.setDropDownHeight((int) mNickNameAutoTextViewDropDownHeight);
            }
        });


    }


    private void initViewValue() {
        mNickNameHasFocus = true;
        mPassWordHasFocus = false;

        mTipLayoutHeightPx = getActivity().getResources().getDimension(R.dimen.login_tips_height);
        mNickNameLayoutHeightPx = getActivity().getResources().getDimension(R.dimen.login_edit_layout_height);
        mEditBottomLineHeightPx = getActivity().getResources().getDimension(R.dimen.edit_bottom_line_height);

        edUserNickname.setDropDownBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.autocomplete_text_bg));
        vNickNameBottomLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        vPassWordBottomLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_dark));


    }

    /**
     * Filter — 过滤，过滤掉没有通过谓词测试的数据项，只发射通过测试的
     */
    private void getBitmap() {
        mSubscription= createBitmapObservable().subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).filter(new Func1<Bitmap, Boolean>() {
            @Override
            public Boolean call(Bitmap bitmap) {
                return bitmap != null && !bitmap.isRecycled();  //返回真假  满足这条件的对象传递下去
            }
        }).map(new Func1<Bitmap, Drawable>() {
            @Override
            public Drawable call(Bitmap bitmap) {
                mNickNameFocusedBitmap = bitmap;
                Drawable drawable = new BitmapDrawable(getActivity().getResources(), bitmap);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                return drawable;
            }
        }).filter(new Func1<Drawable, Boolean>() {
            @Override
            public Boolean call(Drawable drawable) {
                return drawable != null;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Drawable>() {
            @Override
            public void call(Drawable drawable) {
                if (mNickNameHasFocus) {
                    edUserNickname.setCompoundDrawables(drawable, null, null, null);
                }
            }
        });


        int color = ContextCompat.getColor(getActivity(), R.color.theme_color_secondary);
        //rxjava效果等于与这个获取bitmap对象 再设置图形
        // mNickNameFocusedBitmap = ColorBitmapUtil3.getColorBitmap(getActivity().getResources(), R.mipmap.ic_login_username_default, color, 255);
        mPassWordFocusedBitmap = ColorBitmapUtil3.getColorBitmap(getActivity().getResources(), R.mipmap.ic_login_password_default, color, 255);
    }


    /**
     * 图片着色  粉红 将灰色转粉色选中状态
     *
     * @return
     */
    private Bitmap getColorBitmap() {
        int color = ContextCompat.getColor(getContext(), R.color.theme_color_secondary);
        return ColorBitmapUtil3.getColorBitmap(getContext().getResources(), R.mipmap.ic_login_username_default, color, 255);
    }

    /**
     * 图片着色 粉红 可观察着的对象
     * Create — 通过调用观察者的方法从头创建一个Observable
     * Defer — 在观察者订阅之前不创建这个Observable，为每一个观察者创建一个新的Observable
     * Just — 将对象或者对象集合转换为一个会发射这些对象的Observable
     *
     * @return
     */
    private Observable<Bitmap> createBitmapObservable() {
        Observable<Bitmap> observable = Observable.defer(new Func0<Observable<Bitmap>>() {
            @Override
            public Observable<Bitmap> call() {
                return Observable.just(getColorBitmap());
            }
        });
        return observable;//可以直接这里写Observable.defer()
    }


    @OnFocusChange({R.id.ed_user_nickname, R.id.ed_user_pwd})
    void onFocusChanged(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.ed_user_nickname: {
                if (hasFocus) {
                    Logger.d("nickname onFocusChange hasFocus = true");

                    if (mNickNameFocusedBitmap != null && !mNickNameFocusedBitmap.isRecycled()) {
                        //左边的头像变为粉红色
                        Drawable drawable = new BitmapDrawable(getActivity().getResources(), mNickNameFocusedBitmap);
                        // 这一步必须要做,否则不会显示.
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        edUserNickname.setCompoundDrawables(drawable, null, null, null);
                    }

                    //EditText的底部显示粉红色的细线
                    vNickNameBottomLine.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

                    mNickNameHasFocus = true;

                    //获取了焦点同时文本内容不为空显示clear图标
                    if (edUserNickname != null && !TextUtils.isEmpty(edUserNickname.getText())) {
                        llClearNameLayout.setVisibility(View.VISIBLE);
                    }

                } else {
                    Logger.d("nickname onFocusChange hasFocus != true");

                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_login_username_default);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    edUserNickname.setCompoundDrawables(drawable, null, null, null);

                    vNickNameBottomLine.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.gray_dark));

                    mNickNameHasFocus = false;

                    llClearNameLayout.setVisibility(View.INVISIBLE);
                }
                break;
            }
            case R.id.ed_user_pwd: {
                if (hasFocus) {
                    Logger.d("password onFocusChange hasFocus = true");

                    if (mPassWordFocusedBitmap != null && !mPassWordFocusedBitmap.isRecycled()) {
                        Drawable drawable = new BitmapDrawable(getActivity().getResources(), mPassWordFocusedBitmap);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        edUserPwd.setCompoundDrawables(drawable, null, null, null);
                    }

                    vPassWordBottomLine.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

                    iv22.setImageResource(R.mipmap.ic_22_hide);
                    iv33.setImageResource(R.mipmap.ic_33_hide);

                    mPassWordHasFocus = true;


                    if (edUserPwd != null && !TextUtils.isEmpty(edUserPwd.getText())) {
                        llClearPwdLayout.setVisibility(View.VISIBLE);
                    }

                } else {
                    Logger.d("password onFocusChange hasFocus != true");

                    Drawable drawable = ContextCompat.getDrawable(getActivity(), R.mipmap.ic_login_password_default);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    edUserPwd.setCompoundDrawables(drawable, null, null, null);

                    vPassWordBottomLine.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_dark));

                    iv22.setImageResource(R.mipmap.ic_22);
                    iv33.setImageResource(R.mipmap.ic_33);

                    mPassWordHasFocus = false;

                    llClearPwdLayout.setVisibility(View.INVISIBLE);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * 在输入法右下角监听其点击事件
     */
    @OnEditorAction({R.id.ed_user_nickname, R.id.ed_user_pwd})
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {

            // android:imeOptions="actionNext"
            // XML  android:singleLine="true"
            case R.id.ed_user_nickname: {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    edUserNickname.clearFocus();
                    edUserPwd.requestFocus();
                    ToastUtil.showToast(getActivity(), "点击下一步");
                    return true;
                }
                break;
            }

            // XML android:imeOptions="actionDone"
            // XML  android:singleLine="true"
            case R.id.ed_user_pwd: {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    ToastUtil.showToast(getActivity(), "点击结束");
                    return true;
                }

                break;
            }

            default: {
                break;
            }
        }
        return false;
    }

    @OnTextChanged(value = R.id.ed_user_nickname, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onAfterTextChangedNickName(CharSequence s) {
        String nickName = s.toString();

        //自动补全邮箱
        mAutoTextViewAdapter.mList.clear();
        if (nickName.contains("@")) {
            autoAddEmails(nickName);
            mAutoTextViewAdapter.notifyDataSetChanged();
        }



        String passWord = edUserPwd.getText().toString();
        if (!TextUtils.isEmpty(nickName) && !TextUtils.isEmpty(passWord)) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }

        if (!TextUtils.isEmpty(nickName)) {
            llClearNameLayout.setVisibility(View.VISIBLE);
        } else {
            llClearNameLayout.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * EditText 文字编辑后的监听
     */

    @OnTextChanged(value = R.id.ed_user_pwd, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onAfterTextChangedPassWord(CharSequence s) {
        String passWord = s.toString();
        String nickName = edUserNickname.getText().toString();
        if (!TextUtils.isEmpty(nickName) && !TextUtils.isEmpty(passWord)) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }

        if (!TextUtils.isEmpty(passWord)) {
            llClearPwdLayout.setVisibility(View.VISIBLE);
        } else {
            llClearPwdLayout.setVisibility(View.INVISIBLE);
        }
    }


    @OnClick({R.id.ll_clear_name_layout, R.id.ll_clear_pwd_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_clear_name_layout:
                edUserNickname.requestFocus();
                edUserNickname.setText("");
                break;
            case R.id.ll_clear_pwd_layout:
                edUserPwd.requestFocus();
                edUserPwd.setText("");
                break;
        }
    }

    /**
     * 自动补全邮箱
     * @param input
     */
    private void autoAddEmails(String input) {
        Log.d(TAG,"input:" + input);
        String autoEmail;
        if (input != null && input.length() > 0) {
            for (int i = 0; i < mAUTO_EMAILS.length; ++i) {
                if (input.contains("@")) {//包含“@”则开始过滤
                    //12@16
                    //01234
                    String filter = input.substring(input.indexOf("@") + 1, input.length());//获取过滤器，即根据输入“@”之后的内容过滤出符合条件的邮箱

                    Log.d(TAG,"filter:" + filter);
                    if (mAUTO_EMAILS[i].contains(filter)) {//符合过滤条件
                        autoEmail = input.substring(0, input.indexOf("@") + 1) + mAUTO_EMAILS[i];//用户输入“@”之前的内容加上自动填充的内容即为最后的结果
                        mAutoTextViewAdapter.mList.add(autoEmail);
                    }
                } else {
                    autoEmail = input + mAUTO_EMAILS[i];
                    mAutoTextViewAdapter.mList.add(autoEmail);
                }
            }
            Log.d(TAG,"mAutoTextViewAdapter.mList.size():" + mAutoTextViewAdapter.mList.size());

            float textHeight = getActivity().getResources().getDimension(R.dimen.auto_complete_nick_name_layout_height);
            float sumHeight = mAutoTextViewAdapter.mList.size() * textHeight;

            if (sumHeight >= mNickNameAutoTextViewDropDownHeight) {
                edUserNickname.setDropDownHeight((int) mNickNameAutoTextViewDropDownHeight);
            } else {
                edUserNickname.setDropDownHeight((int) sumHeight);
            }
            Log.d(TAG,"mNickNameAutoTextViewDropDownHeight:" + mNickNameAutoTextViewDropDownHeight);
            Log.d(TAG,"sumHeight:" + sumHeight);
        }
    }


    @Override
    public void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        super.onDestroy();
    }
}
