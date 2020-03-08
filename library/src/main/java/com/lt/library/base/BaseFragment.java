package com.lt.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @作者: LinTan
 * @日期: 2019/4/14 20:58
 * @版本: 1.0
 * @描述: //Fragment的基类。
 * 1.0: Initial Commit
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutResId(), container, false);
        bindView(view);//绑定视图，eg: findViewById()
        bindData();//绑定数据，eg: getIntent().getExtras()，getSharedPreferences()
        initView(view);//初始化视图，eg: textView.setText()
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);//初始化数据，eg: 获取网络数据
        initListener();//初始化监听器，eg: setOnClickListener()
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.wtf("BaseFragment", getClass().getSimpleName());
    }//注意，使用show()和hide()时，Fragment不会回调onPause()

    protected abstract int setLayoutResId();

    protected abstract void bindView(View view);

    protected abstract void bindData();

    protected abstract void initView(View view);

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initListener();
}
