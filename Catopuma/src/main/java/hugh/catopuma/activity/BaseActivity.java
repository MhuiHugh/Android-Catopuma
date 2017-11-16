package hugh.catopuma.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import hugh.catopuma.R;
import hugh.catopuma.constant.Config;
import hugh.catopuma.constant.Constant;
import hugh.catopuma.utils.AppInformationUtil;
import hugh.catopuma.utils.LogUtil;
import hugh.catopuma.utils.SoftKeyboardUtil;

/**
 * Activity基类
 * Created by on 2016/6/6.
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    /**
     * 广播用于接收类似退出程序通知
     */
    private final BaseBroadcastReceiver baseBroadcastReceiver = new BaseBroadcastReceiver();

    //输入法是否显示
    protected boolean softKeyBoardShowing = false;

    //------------生命周期方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.v("onCreate()-" + this.getClass().getSimpleName());
        x.view().inject(this);
        initBase(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.v("onStart()-" + this.getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.v("onResume()-" + this.getClass().getSimpleName());
        MobclickAgent.onPageStart(activityName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.v("onPause()-" + this.getClass().getSimpleName());
        MobclickAgent.onPageEnd(activityName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.v("onStop()-" + this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(baseBroadcastReceiver);
        EventBus.getDefault().unregister(this);
        LogUtil.v("onDestroy()-" + this.getClass().getSimpleName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.v("onActivityResult()-" + this.getClass().getSimpleName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //---------------------------事件监听

    /**
     * 重写事件分发，解决点击软键盘外隐藏键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (SoftKeyboardUtil.isShouldHideInput(v, ev)) {
                SoftKeyboardUtil.hideSoftKeyboard(this);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            //进入测试
            if (AppInformationUtil.isApkDebugable()) {
                //startActivity(new Intent(this, TestActivity.class));
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (softKeyBoardShowing) {
                SoftKeyboardUtil.hideSoftKeyboard(this);
            } else {
                this.finishActivity();
            }
        }
        return false;
    }

    //--------------------------可重写或抽象方法

    /**
     * onCreate调用
     *
     * @param savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);

    /**
     * 清理内存数据，退出当前Activity
     */
    public void finishActivity() {
        fixLeakInput();
        this.finish();
    }

    /**
     * Activity的名称，用于友盟页面统计
     *
     * @return
     */
    public String activityName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 沉浸式状态栏设置，子类可重写覆盖
     *
     * @return true使用BaseActivity配置
     */
    protected boolean setImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                //状态栏字体是深色，不写默认为亮色
                .statusBarDarkFont(true)
                //修改flyme OS状态栏字体颜色
                .flymeOSStatusBarFontColor(R.color.black)
                //使用该属性必须指定状态栏的颜色，不然状态栏透明，很难看
                .fitsSystemWindows(true)
                .init();
        return true;
    }

    //-----------------------------自定义方法

    /**
     * 初始化
     */
    private void initBase(Bundle savedInstanceState) {
        //广播过滤器设置
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Config.ACT_EXIT_APP);
        this.registerReceiver(baseBroadcastReceiver, intentFilter);
        //沉浸式状态栏
        setImmersionBar();
        //键盘显隐监听
        initSoftKeyboardListener();
    }

    /**
     * 软键盘显隐状态监听，子类需要可重写
     */
    protected void initSoftKeyboardListener() {
        //软键盘显隐状态监听
        SoftKeyboardUtil.observeSoftKeyboard(this, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(final int softKeybardHeight, boolean visible) {
                if (visible) {
                    softKeyBoardShowing = true;
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (softKeybardHeight < Config.KEYBOARD_HIDE_HEIGHT) {
                                softKeyBoardShowing = false;
                            }
                        }
                    }, 260);
                }
            }
        });
    }

    /**
     * 修复输入法显隐检测内存泄露
     */
    private void fixLeakInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            InputMethodManager.class.getDeclaredMethod("windowDismissed", IBinder.class).invoke(imm, getWindow().getDecorView().getWindowToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * finish all Activity
     */
    private void finishAllActivity() {
        LogUtil.v("finishAllActivity()");
        Constant.appRuning = false;
        this.finishActivity();
    }

    //------------------------广播监听

    /**
     * 广播接受，BaseActivity使用EventBus其它子Activity必须用规避
     */
    private class BaseBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            String action = arg1.getAction();
            //退出所有Activity(退出app)
            if (action.equals(Config.ACT_EXIT_APP)) {
                finishAllActivity();
            }
        }
    }

}
