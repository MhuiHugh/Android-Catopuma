package hugh.catopuma.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

import hugh.catopuma.R;
import hugh.catopuma.utils.StringUtil;

/**
 * 导航栏,默认显示返回图片和“返回”,黑色背景
 * Created by on 2016/5/21.
 */
public class CustomBackBar extends LinearLayout implements View.OnClickListener {

    private ImageView rightImgv;
    private RelativeLayout leftRl;
    private Context cont;

    /**
     * 根布局，可设置背景色
     */
    private RelativeLayout rootBg;
    /**
     * 返回图片，默认有返回图片
     */
    private ImageView imgView;
    /**
     * 标题，默认不显示
     */
    private TextView titleTv;
    /**
     * 右按钮，默认不显示
     */
    private TextView rightTv;

    private OnCustomBackBarClickListener listener;

    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public CustomBackBar(Context context) {
        super(context);
        cont = context;
    }

    public CustomBackBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        cont = context;
        LayoutInflater.from(context).inflate(R.layout.view_backbar, this, true);
        TypedArray typeA = context.obtainStyledAttributes(attrs,
                R.styleable.CustomBackBar);
        boolean show = typeA.getBoolean(R.styleable.CustomBackBar_backShowImg, true);
        String titleStr = typeA.getString(R.styleable.CustomBackBar_backTitle);
        String rightStr = typeA.getString(R.styleable.CustomBackBar_backRight);
        int bgColor = typeA.getColor(R.styleable.CustomBackBar_backBgColor, cont.getResources().getColor(R.color.white));
        int resourceId = typeA.getResourceId(R.styleable.CustomBackBar_backRightImg, 0);
        typeA.recycle();
        rootBg = (RelativeLayout) findViewById(R.id.back_bg);
        imgView = (ImageView) findViewById(R.id.back_imgv);
        titleTv = (TextView) findViewById(R.id.back_title_tv);
        rightTv = (TextView) findViewById(R.id.back_right_tv);
        rightImgv = (ImageView) findViewById(R.id.back_right_iv);
        leftRl = (RelativeLayout) findViewById(R.id.back_left_rl);

        rootBg.setBackgroundColor(bgColor);
        if (resourceId != 0) {
            rightImgv.setImageResource(resourceId);
        } else {
            rightImgv.setVisibility(View.GONE);
        }
        if (show) {
            imgView.setVisibility(VISIBLE);
        } else {
            imgView.setVisibility(GONE);
        }
        if (StringUtil.isEmpty(titleStr)) {
            titleTv.setVisibility(GONE);
        } else {
            titleTv.setVisibility(VISIBLE);
            titleTv.setText(titleStr);
        }
        if (StringUtil.isEmpty(rightStr)) {
            rightTv.setVisibility(GONE);
        } else {
            rightTv.setVisibility(VISIBLE);
            rightTv.setText(rightStr);
        }
        leftRl.setOnClickListener(this);
        titleTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        rightImgv.setOnClickListener(this);
    }

    public CustomBackBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onClick(View view) {
        if (listener == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.back_left_rl:
                listener.onBackClick(view);
                break;
            case R.id.back_title_tv:
                listener.onTitleClick(view);
                break;
            case R.id.back_right_tv:
            case R.id.back_right_iv:
                listener.onRightClick(view);
                break;
        }
    }

    /**
     * 点击监听接口
     */
    public interface OnCustomBackBarClickListener {
        void onBackClick(View view);

        void onTitleClick(View view);

        void onRightClick(View view);
    }

    public void setOnCustomBackBarClickListener(OnCustomBackBarClickListener listener) {
        this.listener = listener;
    }


    /**
     * 设置back title 显示隐藏状态
     *
     * @param show
     */
    public void setBackTitleVeiwShow(int show) {
        if (show == VISIBLE) {
            titleTv.setVisibility(VISIBLE);
        } else if (show == INVISIBLE) {
            titleTv.setVisibility(INVISIBLE);
        } else if (show == GONE) {
            titleTv.setVisibility(GONE);
        }
    }

    /**
     * 设置back right 显示隐藏状态
     *
     * @param show
     */
    public void setBackRightVeiwShow(int show) {
        rightTv.setVisibility(show);

    }

    /**
     * 视图内容的设置
     *
     * @param src
     */
    public void setImgViewDrawable(Drawable src) {
        imgView.setImageDrawable(src);
    }

    public void setTitleTvStr(String str) {
        titleTv.setText(str);
    }

    public void setTitleTvSize(int size) {
        titleTv.setTextSize(size);
    }

    public void setTitleTvColor(int color) {
        titleTv.setTextColor(color);
    }

    public void setRightTvStr(String str) {
        rightTv.setText(str);
    }

    public void setRightTvSize(int size) {
        rightTv.setTextSize(size);
    }

    public void setRightTvColor(int color) {
        rightTv.setTextColor(color);
    }

    //--------------自定义控件宽高适配
    @Override
    public AutoLinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoLinearLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
