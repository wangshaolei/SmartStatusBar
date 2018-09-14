package com.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;


/**
 * @author ChayChan
 * @description: 底部tab条目
 * @date 2017/6/23  9:14
 */
public class BottomBarItem extends LinearLayout {

    private Context mContext;
    private int mIconNormalResourceId;//普通状态图标的资源id
    private int mIconSelectedResourceId;//选中状态图标的资源id
    private String mText;//文本
    private int mTextSize = 12;//文字大小 默认为12sp
    private int mTextColorNormal = 0xFF999999;    //描述文本的默认显示颜色
    private int mTextColorSelected = 0xFF46C01B;  //述文本的默认选中显示颜色
    private int mMarginTop = 0;//文字和图标的距离,默认0dp
    private int mIconWidth;//图标的宽度
    private int mIconHeight;//图标的高度
    private int mItemPadding;//BottomBarItem的padding


    private ImageView mImageView;
    private TextView mTvUnread;
    private TextView mTvNotify;
    private TextView mTvMsg;
    private NoPaddingTextView mTextView;

    private int mUnreadTextSize = 10; //未读数默认字体大小10sp
    private int mMsgTextSize = 6; //消息默认字体大小6sp
    private int unreadNumThreshold = 99;//未读数阈值
    private int mUnreadTextColor;//未读数字体颜色
    private Drawable mUnreadTextBg;
    private int mMsgTextColor;
    private Drawable mMsgTextBg;
    private Drawable mNotifyPointBg;

    public BottomBarItem(Context context) {
        this(context, null);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarItem);

        initAttrs(ta); //初始化属性

        ta.recycle();

        checkValues();//检查值是否合法

        init();//初始化相关操作
    }

    private void initAttrs(TypedArray ta) {
        mIconNormalResourceId = ta.getResourceId(R.styleable.BottomBarItem_iconNormal, -1);
        mIconSelectedResourceId = ta.getResourceId(R.styleable.BottomBarItem_iconSelected, -1);

        mText = ta.getString(R.styleable.BottomBarItem_itemText);
        mTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemTextSize, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics()));

        mTextColorNormal = ta.getColor(R.styleable.BottomBarItem_textColorNormal, mTextColorNormal);
        mTextColorSelected = ta.getColor(R.styleable.BottomBarItem_textColorSelected, mTextColorSelected);

        mMarginTop = ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemMarginTop, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMarginTop, getResources().getDisplayMetrics()));

        mIconWidth = ta.getDimensionPixelSize(R.styleable.BottomBarItem_iconWidth, 0);
        mIconHeight = ta.getDimensionPixelSize(R.styleable.BottomBarItem_iconHeight, 0);
        mItemPadding = ta.getDimensionPixelSize(R.styleable.BottomBarItem_itemPadding, 0);

        mUnreadTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_unreadTextSize, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mUnreadTextSize, getResources().getDisplayMetrics()));
        mUnreadTextColor = ta.getColor(R.styleable.BottomBarItem_unreadTextColor, 0xFFFFFFFF);
        mUnreadTextBg = ta.getDrawable(R.styleable.BottomBarItem_unreadTextBg);

        mMsgTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_msgTextSize, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mMsgTextSize, getResources().getDisplayMetrics()));
        mMsgTextColor = ta.getColor(R.styleable.BottomBarItem_msgTextColor, 0xFFFFFFFF);
        mMsgTextBg = ta.getDrawable(R.styleable.BottomBarItem_msgTextBg);

        mNotifyPointBg = ta.getDrawable(R.styleable.BottomBarItem_notifyPointBg);

        unreadNumThreshold = ta.getInteger(R.styleable.BottomBarItem_unreadThreshold,99);
    }

    /**
     * 检查传入的值是否完善
     */
    private void checkValues() {
        if (mIconNormalResourceId == -1) {
            throw new IllegalStateException("您还没有设置默认状态下的图标，请指定iconNormal的图标");
        }

        if (mIconSelectedResourceId == -1) {
            throw new IllegalStateException("您还没有设置选中状态下的图标，请指定iconSelected的图标");
        }

        if (mUnreadTextBg == null){
            mUnreadTextBg = getResources().getDrawable(R.drawable.shape_unread);
        }

        if (mMsgTextBg == null){
            mMsgTextBg = getResources().getDrawable(R.drawable.shape_msg);
        }

        if (mNotifyPointBg == null){
            mNotifyPointBg = getResources().getDrawable(R.drawable.shape_notify_point);
        }
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        View view = initView();

        mImageView.setImageResource(mIconNormalResourceId);

        if (mIconWidth != 0 && mIconHeight != 0) {
            //如果有设置图标的宽度和高度，则设置ImageView的宽高
            FrameLayout.LayoutParams imageLayoutParams = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
            imageLayoutParams.width = mIconWidth;
            imageLayoutParams.height = mIconHeight;
            mImageView.setLayoutParams(imageLayoutParams);
        }

        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);//设置底部文字字体大小

        mTvUnread.setTextSize(TypedValue.COMPLEX_UNIT_PX, mUnreadTextSize);//设置未读数的字体大小
        mTvUnread.setTextColor(mUnreadTextColor);//设置未读数字体颜色
        mTvUnread.setBackground(mUnreadTextBg);//设置未读数背景

        mTvMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMsgTextSize);//设置提示文字的字体大小
        mTvMsg.setTextColor(mMsgTextColor);//设置提示文字的字体颜色
        mTvMsg.setBackground(mMsgTextBg);//设置提示文字的背景颜色

        mTvNotify.setBackground(mNotifyPointBg);//设置提示点的背景颜色

        mTextView.setTextColor(mTextColorNormal);//设置底部文字字体颜色
        mTextView.setText(mText);//设置标签文字

        LayoutParams textLayoutParams = (LayoutParams) mTextView.getLayoutParams();
        textLayoutParams.topMargin = mMarginTop;
        mTextView.setLayoutParams(textLayoutParams);

        addView(view);
    }

    @NonNull
    private View initView() {
        View view = View.inflate(mContext, R.layout.item_bottom_bar, null);
        if (mItemPadding != 0) {
            //如果有设置item的padding
            view.setPadding(mItemPadding, mItemPadding, mItemPadding, mItemPadding);
        }
        mImageView = view.findViewById(R.id.iv_icon);
        mTvUnread = view.findViewById(R.id.tv_unred_num);
        mTvMsg = view.findViewById(R.id.tv_msg);
        mTvNotify = view.findViewById(R.id.tv_point);
        mTextView = view.findViewById(R.id.tv_text);
        return view;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public NoPaddingTextView getTextView() {
        return mTextView;
    }

    public void setIconNormalResourceId(int mIconNormalResourceId) {
        this.mIconNormalResourceId = mIconNormalResourceId;
    }

    public void setIconSelectedResourceId(int mIconSelectedResourceId) {
        this.mIconSelectedResourceId = mIconSelectedResourceId;
    }

    public void setStatus(boolean isSelected) {
        mImageView.setImageDrawable(getResources().getDrawable(isSelected ? mIconSelectedResourceId : mIconNormalResourceId));
        mTextView.setTextColor(isSelected ? mTextColorSelected : mTextColorNormal);
    }

    private void setTvVisiable(TextView tv) {
        //都设置为不可见
        mTvUnread.setVisibility(GONE);
        mTvMsg.setVisibility(GONE);
        mTvNotify.setVisibility(GONE);

        tv.setVisibility(VISIBLE);//设置为可见
    }

    public int getUnreadNumThreshold() {
        return unreadNumThreshold;
    }

    public void setUnreadNumThreshold(int unreadNumThreshold) {
        this.unreadNumThreshold = unreadNumThreshold;
    }

    /**
     * 设置未读数
     */
    public void setUnreadNum(int unreadNum) {
        setTvVisiable(mTvUnread);
        if (unreadNum <= 0) {
            mTvUnread.setVisibility(GONE);
        } else if (unreadNum <= unreadNumThreshold) {
            mTvUnread.setText(String.valueOf(unreadNum));
        } else {
            mTvUnread.setText(String.format(Locale.CHINA, "%d+", unreadNumThreshold));
        }
    }

    public void setMsg(String msg) {
        setTvVisiable(mTvMsg);
        mTvMsg.setText(msg);
    }

    public void hideMsg() {
        mTvMsg.setVisibility(GONE);
    }

    public void showNotify() {
        setTvVisiable(mTvNotify);
    }

    public void hideNotify() {
        mTvNotify.setVisibility(GONE);
    }
}
