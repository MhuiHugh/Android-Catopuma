package hugh.catopuma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;

import hugh.catopuma.R;
import hugh.catopuma.bean.MessageEvent;

/**
 * 启动页，引导页，广告页
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    @Override
    public void init(Bundle savedInstanceState) {
        //需要EventBus时注册
        EventBus.getDefault().register(this);
        splashHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 事件处理
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void actDeal(MessageEvent messageEvent) {
        //EventBus事件处理
        if (messageEvent.getAction().equals("")) {

        }
    }

    public Handler splashHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://到首页
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    SplashActivity.super.finishActivity();
                    break;
            }
        }
    };

}
