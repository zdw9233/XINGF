package com.uyi.app.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.ui.dialog.HeaderMenuDialog;
import com.uyi.app.ui.dialog.RightMenuDialog;
import com.uyi.app.utils.DensityUtils;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;


/**
 * 标题栏View
 *
 * @author chao
 */
public class HeaderView extends LinearLayout implements View.OnClickListener {


    private LinearLayout view_header_layout;

    private LinearLayout view_header_left_layout;
    private LinearLayout view_header_left_layout_return;//返回
    private RoundedImageView view_header_left;

    private TextView view_header_title;//标题
    private LinearLayout view_header_layout_content;//选项卡

    private TextView view_header_one;//卡一
    private TextView view_header_two;//卡二
    private TextView view_header_three;//卡三

    private HeaderMenuDialog headerMenuDialog;
    private RightMenuDialog rightMenuDialog;


    private LinearLayout view_header_right;
    private ImageView view_header_right_icon;


    public OnTabChanage onTabChanage;

    private Context context;

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_header, this);
        view_header_layout = find(this, R.id.view_header_layout);
        view_header_left_layout = find(this, R.id.view_header_left_layout);
        view_header_left = find(this, R.id.view_header_left);
        view_header_title = find(this, R.id.view_header_title);
        view_header_layout_content = find(this, R.id.view_header_layout_content);
        view_header_one = find(this, R.id.view_header_one);
        view_header_two = find(this, R.id.view_header_two);
        view_header_three = find(this, R.id.view_header_three);
        view_header_right = find(this, R.id.view_header_right);
        view_header_right_icon = find(this, R.id.view_header_right_icon);
        view_header_left_layout_return = find(this, R.id.view_header_left_layout_return);

        //隐藏Left
        view_header_left_layout_return.setVisibility(View.GONE);
        view_header_left.setVisibility(View.GONE);

        //隐藏标题
        view_header_title.setVisibility(View.GONE);

        //隐藏tab
        view_header_layout_content.setVisibility(View.GONE);

        //隐藏Right
        view_header_right_icon.setVisibility(View.GONE);

        view_header_one.setOnClickListener(this);
        view_header_two.setOnClickListener(this);
        view_header_three.setOnClickListener(this);


        headerMenuDialog = new HeaderMenuDialog(context);
    }


    public void setKitkat(SystemBarConfig systemBarConfig) {
        if (systemBarConfig != null) {
            LayoutParams params = (LayoutParams) view_header_layout.getLayoutParams();
            params.height = systemBarConfig.getStatusBarHeight() + params.height;
            int leftOrRight = DensityUtils.dp2px(context, 10);
            view_header_layout.setLayoutParams(params);
            view_header_layout.setPadding(leftOrRight, systemBarConfig.getStatusBarHeight(), leftOrRight, 0);
        }
    }

    /**
     * 选中项
     *
     * @param postion
     */
    public void selectTabItem(int postion) {
        if (postion == 1) {
            view_header_one.performClick();
        } else if (postion == 2) {
            view_header_two.performClick();
        } else if (postion == 3) {
            view_header_three.performClick();
        }
    }

    /**
     * 设置第选项卡文字
     *
     * @return
     */
    public HeaderView setTitleTabs(String[] tabsText) {
        view_header_one.setVisibility(View.GONE);
        view_header_two.setVisibility(View.GONE);
        view_header_three.setVisibility(View.GONE);
        if (tabsText.length >= 1) {
            view_header_one.setVisibility(View.VISIBLE);
            view_header_one.setText(tabsText[0]);
        }
        if (tabsText.length >= 2) {
            view_header_two.setVisibility(View.VISIBLE);
            view_header_two.setText(tabsText[1]);
        }
        if (tabsText.length >= 3) {
            view_header_three.setVisibility(View.VISIBLE);
            view_header_three.setText(tabsText[2]);
        }
        return this;
    }

    /**
     * 显示左边头像
     *
     * @param showLeft
     * @return
     */
    public HeaderView showLeftHeader(boolean showLeft, String imageUrl) {
        if (showLeft) {
            view_header_left.setVisibility(View.VISIBLE);
            view_header_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    headerMenuDialog.show();
                }
            });
            if (imageUrl != null) {
                ImageCacheManager.loadImage(imageUrl, ImageCacheManager.getImageListener(view_header_left, null, null));
            }

        } else {
            view_header_left.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 获取左边头像View
     *
     * @return
     */
    public RoundedImageView getLeftHeaderImageView() {
        return view_header_left;
    }

    /**
     * 显示左边返回键
     *
     * @param show
     * @return
     */
    public HeaderView showLeftReturn(boolean show) {
        if (show) {
            view_header_left_layout_return.setVisibility(View.VISIBLE);
            if (context instanceof Activity) {
                final Activity activity = (Activity) context;
                view_header_left_layout_return.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
//						activity.onBackPressed();
                    }
                });
            }
        } else {
            view_header_left_layout_return.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 显示中间文字标题
     *
     * @param showTitle
     * @return
     */
    public HeaderView showTitle(boolean showTitle) {
        if (showTitle) {
            view_header_title.setVisibility(View.VISIBLE);
        } else {
            view_header_title.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 设置标题颜色
     *
     * @param color
     * @return
     */
    public HeaderView setTitleColor(int color) {
        view_header_title.setTextColor(color);
        return this;
    }

    /**
     * 显示中间tab
     *
     * @param showTab
     * @return
     */
    public HeaderView showTab(boolean showTab) {
        if (showTab) {
            view_header_layout_content.setVisibility(View.VISIBLE);
        } else {
            view_header_layout_content.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 显示右边菜单
     *
     * @param showRight
     * @return
     */
    public HeaderView showRight(boolean showRight) {
        if (showRight) {
            view_header_right_icon.setVisibility(View.VISIBLE);
            view_header_right_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightMenuDialog = new RightMenuDialog(context);
                    rightMenuDialog.show();
                }
            });
        } else {
            view_header_right_icon.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 设置左边点击事件
     *
     * @param clickListener
     * @return
     */
    public HeaderView setLeftOnClick(OnClickListener clickListener) {
        view_header_left_layout.setOnClickListener(clickListener);
        return this;
    }

    /**
     * 设置标题  文字标题
     *
     * @param title
     * @return
     */
    public HeaderView setTitle(String title) {
        view_header_title.setText(title);
        return this;
    }

    /**
     * 设置背景颜色   titile 为白色
     *
     * @param color
     * @return
     */
    public HeaderView setHeaderBackgroundColor(int color) {
        view_header_title.setTextColor(getResources().getColor(R.color.white));
        view_header_layout.setBackgroundColor(color);
        return this;
    }


    /**
     * 设置右边图标
     *
     * @param id
     * @return
     */
    public HeaderView setRightIcon(int id) {
        view_header_right_icon.setBackgroundResource(id);
        return this;
    }

    /**
     * 设置右边点击事件
     *
     * @param listener
     * @return
     */
    public HeaderView setRightOnClick(OnClickListener listener) {
        view_header_right.setOnClickListener(listener);
        return this;
    }

    /**
     * 右边布局
     *
     * @return
     */
    public LinearLayout getRightLayout() {
        return view_header_right;
    }


    public void setOnTabChanage(OnTabChanage onTabChanage) {
        this.onTabChanage = onTabChanage;
    }


    public <T extends View> T find(View v, int id) {
        return (T) v.findViewById(id);
    }

    public interface OnTabChanage {
        public void onChanage(int postion);
    }

    @Override
    public void onClick(View v) {
        view_header_one.setBackgroundResource(0);
        view_header_one.setTextColor(getResources().getColor(R.color.list_item_content_color));
        view_header_two.setBackgroundResource(0);
        view_header_two.setTextColor(getResources().getColor(R.color.list_item_content_color));
        view_header_three.setBackgroundResource(0);
        view_header_three.setTextColor(getResources().getColor(R.color.list_item_content_color));
        if (v.getId() == R.id.view_header_one) {
            view_header_one.setTextColor(getResources().getColor(R.color.white));
            view_header_one.setBackgroundResource(R.drawable.blue_bg);
            if (onTabChanage != null) {
                onTabChanage.onChanage(1);
            }
        } else if (v.getId() == R.id.view_header_two) {
            view_header_two.setTextColor(getResources().getColor(R.color.white));
            view_header_two.setBackgroundResource(R.drawable.blue_bg);
            if (onTabChanage != null) {
                onTabChanage.onChanage(2);
            }
        } else if (v.getId() == R.id.view_header_three) {
            view_header_three.setTextColor(getResources().getColor(R.color.white));
            view_header_three.setBackgroundResource(R.drawable.blue_bg);
            if (onTabChanage != null) {
                onTabChanage.onChanage(3);
            }
        }

    }
}
