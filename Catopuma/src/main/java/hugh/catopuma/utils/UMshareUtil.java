package hugh.catopuma.utils;

import android.app.Activity;
import android.graphics.BitmapFactory;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import hugh.catopuma.R;
import hugh.catopuma.constant.Constant;

/**
 * 分享工具类封装
 */
public class UMshareUtil {

    private Activity activity;
    /**
     * 是否在分享结束重置分享内容为""
     */
    private boolean isRestShare = false;

    /**
     * 需要分享的平台
     */
    private SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]{
            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.SINA};

    public UMshareUtil(Activity context) {
        this.activity = context;
    }

    public void shareUM() {
        new ShareAction(activity).setDisplayList(displaylist)
                .setCallback(shareListener)
                .setShareboardclickCallback(shareBoardlistener)
                .open();
    }

    /**
     * 设置需要分享的内容
     *
     * @param title
     * @param content
     * @param linkUrl
     * @param picUrl
     */
    public UMshareUtil setShare(String title, String content, String linkUrl, String picUrl) {
        Constant.SHARE_TITLE = title;
        Constant.SHARE_CONTENT = content;
        Constant.SHARE_URL = linkUrl;
        Constant.SHARE_PICURL = picUrl;
        return this;
    }

    /**
     * 设置是否在分享结束重置分享内容数据为""
     *
     * @param share,true分享完清理
     * @return
     */
    public UMshareUtil setResetShare(boolean share) {
        isRestShare = share;
        return this;
    }

    /**
     * 分享面板监听
     */
    ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            ShareAction shareAction = new ShareAction(activity).setPlatform(share_media);
            //设置点击跳转地址
            UMWeb web = new UMWeb(Constant.SHARE_URL);
            //标题
            web.setTitle(Constant.SHARE_TITLE);
            UMImage umImage = null;
            //无图片地址使用默认图片
            if (Constant.SHARE_PICURL.isEmpty()) {
                umImage = new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher_background));
            } else {//有图片地址加载图片
                umImage = new UMImage(activity, Constant.SHARE_PICURL);
            }
            if (null != umImage) {
                umImage.compressStyle = UMImage.CompressStyle.SCALE;
                web.setThumb(umImage);
            }
            web.setDescription(Constant.SHARE_CONTENT);
            //微信朋友圈&QQ空间分享将内容替换成标题
            if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE || share_media == SHARE_MEDIA.QZONE) {
                shareAction.withText(Constant.SHARE_CONTENT);
            } else {
                shareAction.withText(Constant.SHARE_TITLE);
            }
            //短信和Email将跳转地址放入内容
            if (share_media == SHARE_MEDIA.EMAIL || share_media == SHARE_MEDIA.SMS) {
                shareAction.withText(Constant.SHARE_CONTENT + Constant.SHARE_URL);
            } else {
                //没有图片微信只有文本
                if (StringUtil.isNotEmpty(Constant.SHARE_PICURL)) {
                    UMImage shareImg = new UMImage(activity, Constant.SHARE_PICURL);
                    shareAction.withMedia(shareImg);
                }
                shareAction.withText(Constant.SHARE_CONTENT);
            }
            shareAction.withMedia(web).share();
        }
    };

    /**
     * 分享结果监听
     */
    UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            //ToastShow.showInCenter("分享成功");
            resetShare();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            //ToastShow.showInCenter("分享失败");
            LogUtil.v("platform" + platform + "    throwable" + t);
            resetShare();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            //ToastShow.showInCenter("分享已取消");
            resetShare();
        }
    };

    /**
     * 清理分享数据内容，内部使用方法
     */
    private void resetShare() {
        if (!isRestShare) {
            return;
        }
        Constant.SHARE_TITLE = "";
        Constant.SHARE_CONTENT = "";
        Constant.SHARE_URL = "";
        Constant.SHARE_PICURL = "";
    }

}
