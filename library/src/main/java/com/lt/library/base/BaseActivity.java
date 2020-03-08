package com.lt.library.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.lt.library.util.ActivityUtil;

/**
 * @作者: LinTan
 * @日期: 2018/12/29 19:13
 * @版本: 1.1
 * @描述: //Activity的基类。配合ActivityUtil，管理所有Activity的出入栈，并输出当前Activity名称。
 * 源址: 《第一行代码》中 2.6 活动的最佳实践内的Example
 * 1.0: Initial Commit
 * 1.1: 重写onBackPressed()，双击Back键退出
 * 1.2: 优化onBackPressed()判断逻辑，非返回栈最后的Activity时正常单击Back键返回
 */

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {
    private long mPressBackTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.addActivity(this);
        setContentView(setLayoutResId());
        bindView();//绑定视图，eg: findViewById()
        bindData();//绑定数据，eg: getIntent().getExtras()，getSharedPreferences()
        initView();//初始化视图，eg: textView.setText()
        initData(savedInstanceState);//初始化数据，eg: 获取网络数据
        initListener();//初始化监听器，eg: setOnClickListener()
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.wtf("BaseActivity", getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (ActivityUtil.getFirstActivity() == this) {//判断当前Activity是否位于BackStack中栈底
            if ((System.currentTimeMillis() - mPressBackTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT)
                     .show();
                mPressBackTime = System.currentTimeMillis();
            } else {
                ActivityUtil.finishAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());//杀掉进程
            }
        } else {
            super.onBackPressed();
        }
    }

    protected abstract int setLayoutResId();

    protected abstract void bindView();

    protected abstract void bindData();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initListener();
}
