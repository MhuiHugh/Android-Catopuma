package hugh.catopuma.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import hugh.catopuma.activity.BaseActivity;
import hugh.catopuma.utils.LogUtil;

/**
 * Fragment基类
 * Created by on 2016/8/5.
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {

    protected BaseActivity mBaseActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.v("onCreateView()-" + this.getClass().getSimpleName());
        return container;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtil.v("onViewCreated()-" + this.getClass().getSimpleName());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.v("onCreate()-" + this.getClass().getSimpleName());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.v("onStart()-" + this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.v("onResume()-" + this.getClass().getSimpleName());
        MobclickAgent.onPageStart(fragmentName());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.v("onPause()-" + this.getClass().getSimpleName());
        MobclickAgent.onPageEnd(fragmentName());
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.v("onStop()-" + this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.v("onDestroy()-" + this.getClass().getSimpleName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.v("onActivityResult()-" + this.getClass().getSimpleName());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.v("onSaveInstanceState()-" + this.getClass().getSimpleName());
    }

    //------自定义方法--------------------------------

    /**
     * Fragment的名称，可重写用于友盟页面跳转统计
     *
     * @return
     */
    public  String fragmentName(){
        return this.getClass().getSimpleName();
    }

}
