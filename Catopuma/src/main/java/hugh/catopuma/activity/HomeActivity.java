package hugh.catopuma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import org.xutils.view.annotation.ContentView;

import hugh.catopuma.MyApplication;
import hugh.catopuma.R;
import hugh.catopuma.constant.Config;
import hugh.catopuma.constant.Constant;
import hugh.catopuma.utils.LogUtil;
import hugh.catopuma.utils.SoftKeyboardUtil;
import hugh.catopuma.widget.ToastShow;

/**
 * Home
 */
@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    /**
     * 再按一次退出程序
     */
    long exitTime = 0;

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (softKeyBoardShowing) {
                SoftKeyboardUtil.hideSoftKeyboard(this);
            } else {
                exitApp();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    //---------------------------

    /**
     * 退出程序
     */
    public void exitApp() {
        LogUtil.i("exitApp()");
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastShow.showInBottom(getString(R.string.common_exit_app));
            exitTime = System.currentTimeMillis();
        } else {
            Constant.userLoged = false;
            finishActivity();
            //退出所有Activity
            MyApplication.getInstance().sendBroadcast(new Intent().setAction(Config.ACT_EXIT_APP));
        }
    }

}
