package com.uyi.app.ui.healthinfo;

import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.custom.spiner.SpinerPopWindow;
import com.uyi.custom.app.R;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2016/8/24.
 */


public class CustomInfoFragment extends BaseFragment{
    //个人资料
    @ViewInject(R.id.register_header_image)
    private RoundedImageView register_header_image;
    @ViewInject(R.id.register_shen)
    private TextView register_shen;
    @ViewInject(R.id.register_city)
    private TextView register_city;
    @ViewInject(R.id.register_address)
    private EditText register_address;
    @ViewInject(R.id.register_chushennianyue)
    private TextView register_chushennianyue;
    @ViewInject(R.id.register_phone)
    private EditText register_phone;
    @ViewInject(R.id.register_mobile)
    private EditText register_mobile;
    @ViewInject(R.id.register_email)
    private EditText register_email;
    @ViewInject(R.id.register_card)
    private EditText register_card;
    @ViewInject(R.id.register_zhiye)
    private EditText register_zhiye;
    @ViewInject(R.id.register_three_submit)
    private Button register_three_submit;
    private SpinerPopWindow spinerPopWindow;
    /**
     * 省
     */
    private List<String> provinces = new ArrayList<String>();
    private JSONArray provincesJSON = new JSONArray();

    /**
     * 市
     */
    private List<String> province = new ArrayList<String>();
    private JSONArray provinceJSON = new JSONArray();

    /**
     * 1 问题
     * 2 性别
     * 3省
     * 4市
     */
    public int spinerIndex = 1;
    Bitmap photo;
    private File tempFile = new File(Environment.getExternalStorageDirectory(), "header.jpg");

    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_update_user_info;
    }

    @Override
    protected void onInitLayoutAfter() {








    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {

    }
}
