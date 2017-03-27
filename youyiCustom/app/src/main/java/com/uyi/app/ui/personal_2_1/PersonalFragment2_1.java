package com.uyi.app.ui.personal_2_1;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.ui.Main2_1;
import com.uyi.app.ui.common.LoginActivity;
import com.uyi.app.ui.common.UpdatePasswordActivity;
import com.uyi.app.ui.custom.BaseFragment;
import com.uyi.app.ui.custom.RoundedImageView;
import com.uyi.app.ui.custom.SystemBarTintManager;
import com.uyi.app.ui.dialog.MessageConform;
import com.uyi.app.ui.personal.message.MessageActivity;
import com.uyi.app.utils.JSONObjectUtils;
import com.uyi.app.utils.L;
import com.uyi.custom.app.R;
import com.volley.ImageCacheManager;
import com.volley.RequestManager;

import org.json.JSONObject;

/**
 * 我的（新） Created byzdw on 2016/6/19.
 */
public class PersonalFragment2_1 extends BaseFragment implements MessageConform.OnMessageClick {
    @ViewInject(R.id.fragment_personal_2_1)
    private LinearLayout fragment_personal_2_1;
    @ViewInject(R.id.title_tab3)
    private TextView title_tab3;
    @ViewInject(R.id.personal_head)
    private RoundedImageView personal_head;
    @ViewInject(R.id.personal_servece)
    private TextView personal_servece;
    @ViewInject(R.id.messge_new)
    private TextView messge_new;
    private MessageConform conform;
    @Override
    protected int getLayoutResouesId() {
        return R.layout.fragment_personal_2_1;
    }

    @Override
    protected void onInitLayoutAfter() {

    }
    public void getNews() {
        RequestManager.getObject(Constens.DOCTOR_HEALTH_NEWS, getActivity(), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.e("PersonNEW==",data.toString());
                    if(data.getInt("message")> 0){
                        messge_new.setVisibility(View.VISIBLE);
                    }else{
                        messge_new.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        getNews();
        RequestManager.getObject(Constens.ACCOUNT_INFO, getActivity(), new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject data) {
                try {
                    L.e("ONE", data.toString());
                    ImageCacheManager.loadImage(data.getString("icon"), ImageCacheManager.getImageListener(personal_head, null, null));
                    title_tab3.setText(data.getString("realName"));
                    personal_servece.setText(JSONObjectUtils.getString(data, "servicePackageName"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onBuildVersionGT_KITKAT(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        fragment_personal_2_1.setPadding(0,systemBarConfig.getStatusBarHeight(),0,0);
    }

    @OnClick({
            R.id.back_to_tab_1,//返回
            R.id.title_messge,//messge
            R.id.lay1,//个人资料
            R.id.lay2,//基本健康信息
            R.id.lay3,//既往史
            R.id.lay4,//药物情况
            R.id.lay5,//血管事件
            R.id.lay6,//修改密码
            R.id.exit//退出
    })
    public void click(View v) {
        switch (v.getId()) {
            case   R.id.back_to_tab_1:
                Main2_1.main2_1.changeFragment(0);
                break;
            case  R.id.title_messge:
                getContext().startActivity(new Intent(context, MessageActivity.class));
                break;
            case  R.id.lay1:
                Intent intent1= new Intent(getContext(), PersonalActivity2_1.class);
                getContext().startActivity(intent1);
                break;
            case  R.id.lay2:
                Intent intent2= new Intent(getContext(), BasicInfoActivity.class);
                getContext().startActivity(intent2);
                break;
            case  R.id.lay3:
                Intent intent3= new Intent(getContext(), PastHistoryactivity.class);
                getContext().startActivity(intent3);
                break;
            case  R.id.lay4:
                Intent intent4  = new Intent(getContext(), MedicineInfoActivity.class);
                getContext().startActivity(intent4);
                break;
            case  R.id.lay5:
                Intent intent5= new Intent(getContext(), BloodInfoActivity.class);
                getContext().startActivity(intent5);
                break;
            case  R.id.lay6:
                Intent intent6 = new Intent(getContext(), UpdatePasswordActivity.class);
                getContext().startActivity(intent6);
                break;
            case  R.id.exit:
                			if(conform == null){
				conform = new MessageConform(getContext(), MessageConform.MessageType.CONFORM);
			}
			conform.setTitle("提示").setContent("确认要退出吗?").setOnMessageClick(this);
			conform.show();
                break;
        }
    }


    @Override
    public void onClick(MessageConform.Result result, Object object) {

    if (result == MessageConform.Result.OK) {
        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
        UserInfoManager.clearLoginUserInfo(getContext());
    }
    }
}
