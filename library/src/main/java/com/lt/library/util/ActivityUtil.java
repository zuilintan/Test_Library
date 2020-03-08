package com.lt.library.util;

import android.app.Activity;

import java.util.Stack;

/**
 * @作者: LinTan
 * @日期: 2018/12/29 19:53
 * @版本: 1.0
 * @描述: //Activity的工具类。配合BaseActivity，管理Activity入栈出栈。
 * 源址: 《第一行代码(第二版)》中 2.6 活动的最佳实践内的Example
 * 1.0: Initial Commit
 * 1.1: 添加方法，判断当前Activity是否为BackStack中最后一个
 */

public class ActivityUtil {

    private static Stack<Activity> sActivityStack = new Stack<>();

    private ActivityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void addActivity(Activity activity) {
        sActivityStack.add(activity);
    }

    public static void removeActivity(Activity activity) {
        sActivityStack.remove(activity);
    }

    public static Activity getLastActivity() {
        return sActivityStack.lastElement();
    }

    public static Activity getFirstActivity() {
        return sActivityStack.firstElement();
    }

    public static void finishAllActivity() {
        for (Activity activity : sActivityStack) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        sActivityStack.clear();
    }
}
