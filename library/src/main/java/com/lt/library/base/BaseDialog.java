package com.lt.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialog extends DialogFragment {

    private Context mContext;
    private int mLayoutResId;
    private int mWidth;//宽度
    private int mHeight;//高度
    private int mAnimStyle = 0;//过场动画
    private int mHorizontalMargin = 0;//左右边距
    private float mDimAmount = 0.6F;//外围昏暗度
    private boolean mOutCancel = false;//是否外部取消
    private boolean mBottomShow = false;//是否底部显示

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Dialog);
        mLayoutResId = setLayoutResId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(mLayoutResId, container, false);
        init(BaseViewHolder.newInstance(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();

            layoutParams.dimAmount = mDimAmount;//设置外围昏暗度

            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle);
            }//设置过场动画

            if (mBottomShow) {
                layoutParams.gravity = Gravity.BOTTOM;
            }//设置显示位置

            if (mWidth == 0) {
                layoutParams.width = getScreenWidth(getContext()) - 2 * dp2px(getContext(), mHorizontalMargin);
            } else {
                layoutParams.width = dp2px(getContext(), mWidth);
            }//设置宽度

            if (mHeight == 0) {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                layoutParams.height = dp2px(getContext(), mHeight);
            }//设置高度

            window.setAttributes(layoutParams);
        }
        setCancelable(mOutCancel);//设置外部取消
    }

    /**
     * 设置宽高
     */
    public BaseDialog setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    /**
     * 设置左右边距
     */
    public BaseDialog setHorizontalMargin(int horizontalMargin) {
        mHorizontalMargin = horizontalMargin;
        return this;
    }

    /**
     * 设置是否底部显示
     */
    public BaseDialog setBottomShow(boolean bottomShow) {
        mBottomShow = bottomShow;
        return this;
    }

    /**
     * 设置是否允许点击外围取消
     */
    public BaseDialog setOutCancel(boolean outCancel) {
        mOutCancel = outCancel;
        return this;
    }

    /**
     * 设置外围昏暗度
     */
    public BaseDialog setDimAmout(@FloatRange(from = 0, to = 1) float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    /**
     * 设置过场动画
     */
    public BaseDialog setAnimStyle(@StyleRes int animStyle) {
        mAnimStyle = animStyle;
        return this;
    }

    //显示Dialog
    public BaseDialog show(FragmentManager manager) {
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }

    /**
     * 获得屏幕宽度
     */
    public int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay()
                     .getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * dp转px
     */
    public int dp2px(Context context, float dpValue) {
        float density = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                  dpValue,
                                                  context.getResources().getDisplayMetrics());
        return (int) (density + 0.5F);
    }

    protected abstract int setLayoutResId();

    protected abstract void init(BaseViewHolder vh, BaseDialog dialog);
}
