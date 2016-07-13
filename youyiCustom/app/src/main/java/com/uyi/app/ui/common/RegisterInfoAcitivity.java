package com.uyi.app.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.uyi.app.Constens;
import com.uyi.app.UserInfoManager;
import com.uyi.app.model.bean.HealthInfo;
import com.uyi.app.model.bean.UserInfo;
import com.uyi.app.ui.custom.BaseActivity;
import com.uyi.app.ui.custom.HeaderView;
import com.uyi.app.ui.custom.SystemBarTintManager.SystemBarConfig;
import com.uyi.app.utils.T;
import com.uyi.app.utils.UYIUtils;
import com.uyi.app.utils.ValidationUtils;
import com.uyi.custom.app.R;
import com.volley.RequestErrorListener;
import com.volley.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 修改资料
 * @author user
 *
 */
@ContentView(R.layout.register_inf0_one)
public class RegisterInfoAcitivity extends BaseActivity {
	private LayoutInflater mInflater;
	int index = 1;//fragment页码

	@ViewInject(R.id.headerView) private HeaderView headerView;
	
//	@ViewInject(R.id.register_info_one) 				private ScrollView register_info_one;
	@ViewInject(R.id.register_info_two) 	private LinearLayout register_info_two;
	@ViewInject(R.id.register_info_gerenjiwangbinshi) 	private EditText register_info_gerenjiwangbinshi;
	@ViewInject(R.id.register_info_chuanrangbingshi) 	private EditText register_info_chuanrangbingshi;
	@ViewInject(R.id.register_info_waishang) 			private EditText register_info_waishang;
	@ViewInject(R.id.register_info_shoushushi) 			private EditText register_info_shoushushi;
	@ViewInject(R.id.register_info_liucanshi) 			private EditText register_info_liucanshi;
	@ViewInject(R.id.register_info_yuejing) 			private EditText register_info_yuejing;


	@ViewInject(R.id.register_info_zhongdushi) 			private EditText register_info_zhongdushi;
	@ViewInject(R.id.register_info_shuxueshi) 			private EditText register_info_shuxueshi;
	@ViewInject(R.id.register_info_qitabinshi) 		private EditText register_info_qitabinshi;
	@ViewInject(R.id.register_info_jiazhubinshi) 		private EditText register_info_jiazhubinshi;
	@ViewInject(R.id.register_info_yufangjiezhonshi) 		private EditText register_info_yufangjiezhonshi;
	@ViewInject(R.id.register_info_xitonghuigu) 		private EditText register_info_xitonghuigu;
	@ViewInject(R.id.register_info_two_submit) 			private Button register_info_two_submit;

	@ViewInject(R.id.register_info_three) 	private LinearLayout register_info_three;
	@ViewInject(R.id.register_info_guomingshi) 			private EditText register_info_guomingshi;
	@ViewInject(R.id.register_info_chengyingdeyaowu) 			private EditText register_info_chengyingdeyaowu;
	@ViewInject(R.id.addmedicationsituation) 			private TextView addmedicationsituation;
	@ViewInject(R.id.register_info_three_submit) 			private Button register_info_three_submit;

	@ViewInject(R.id.register_info_four) 	private LinearLayout register_info_four;
	@ViewInject(R.id.regester_info_xueguanfashengshijian) 			private TextView regester_info_xueguanfashengshijian;
	@ViewInject(R.id.regester_info_xueguanfashengleixing) 			private TextView regester_info_xueguanfashengleixing;
	@ViewInject(R.id.register_info_qitaleixin) 			private EditText register_info_qitaleixin;
	@ViewInject(R.id.register_info_four_submit) 			private Button register_info_four_submit;

	@ViewInject(R.id.register_info_muqianfuyaoqingkuang) private EditText register_info_muqianfuyaoqingkuang;
	@ViewInject(R.id.register_info_qitabuchongqingkuang) private EditText register_info_qitabuchongqingkuang;
	@ViewInject(R.id.register_info_submit) 				private Button register_info_submit;
	
	@ViewInject(R.id.register_info_liucanshi_layout) 	private LinearLayout register_info_liucanshi_layout;
	@ViewInject(R.id.register_info_yuejing_layout) 		private LinearLayout register_info_yuejing_layout;
	
//	@ViewInject(R.id.register_info_two) 	private LinearLayout register_info_two;
@ViewInject(R.id.register_info_five) 	private LinearLayout register_info_five;
	@ViewInject(R.id.register_info_return) 	private Button register_info_return;

	@ViewInject(R.id.register_info_one) 	private LinearLayout register_info_one;
	@ViewInject(R.id.register_height) 			private EditText register_height;
	@ViewInject(R.id.register_weight) 			private EditText register_weight;
	@ViewInject(R.id.register_info_one_jiankangzhuangkuang) 			private EditText register_info_one_jiankangzhuangkuang;
	@ViewInject(R.id.register_info_one_submit) 			private Button register_info_one_submit;


	@ViewInject(R.id.add_view_layout) 	private LinearLayout add_view_layout;
	private HealthInfo healthInfo;	//病人ID
	private UserInfo userInfo;
	private String gender;			//性别
	public int update = 0;			//1为更新信息
	String height = "";
	String weight = "";
	String healthCondition = "";
	String chronicDiseaseType = "0";
	String medical = "";
	String infection ="";
	String trauma = "";
	String operation = "";
	String pregnancy = "";
	String menstruation ="";
	String allergic ="";
	String blood ="";
	String familyMedical ="";
	String others ="";
	String vaccinationHistory ="";
	String retrospection ="";
//	String menstruation ="";
//	String menstruation ="";
//	String menstruation ="";
//	String menstruation ="";
	
	@Override
	protected void onInitLayoutAfter() {
		update = getIntent().getIntExtra("update", 0);
		gender = getIntent().getStringExtra("gender");
		
		if(gender != null && ValidationUtils.equlse(UYIUtils.convertGender(gender), "男")){
			register_info_liucanshi_layout.setVisibility(View.GONE);
			register_info_yuejing_layout.setVisibility(View.GONE);
		}
		headerView.showTitle(true);
		headerView.setTitle(update==0?"填写健康资料":"修改健康资料");
		headerView.setHeaderBackgroundColor(getResources().getColor(R.color.blue));
		healthInfo = new HealthInfo();
		replaceView(1);
		mInflater = LayoutInflater.from(this) ;
		userInfo = UserInfoManager.getLoginUserInfo(activity);
//		if(userInfo != null){
//			Loading.bulid(activity, null).show();
//			RequestManager.getObject(Constens.ACCOUNT_DETAIL, activity, new Listener<JSONObject>() {
//				public void onResponse(JSONObject data) {
//					try {
//						Loading.bulid(activity, null).dismiss();
//						JSONObject healthInfo = data.getJSONObject("healthInfo");
//						register_info_gerenjiwangbinshi.setText(JSONObjectUtils.getString(healthInfo, "medical") );
//						register_info_chuanrangbingshi.setText(JSONObjectUtils.getString(healthInfo, "infection") );
//						register_info_waishang.setText(JSONObjectUtils.getString(healthInfo, "trauma"));
//						register_info_shoushushi.setText(JSONObjectUtils.getString(healthInfo, "operation") );
//
//
//						register_info_liucanshi.setText(JSONObjectUtils.getString(healthInfo, "pregnancy"));
//						register_info_yuejing.setText(JSONObjectUtils.getString(healthInfo, "menstruation"));
//
//						register_info_guomingshi.setText(JSONObjectUtils.getString(healthInfo, "allergic"));
//						register_info_shuxueshi.setText(JSONObjectUtils.getString(healthInfo, "blood"));
//						register_info_jiazhubinshi.setText(JSONObjectUtils.getString(healthInfo, "familyMedical"));
//						register_info_muqianfuyaoqingkuang.setText(JSONObjectUtils.getString(healthInfo, "current"));
//						register_info_qitabuchongqingkuang.setText(JSONObjectUtils.getString(healthInfo, "others"));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}
	}

	public void replaceView(int index){
		this.index = index;
		register_info_one.setVisibility(View.GONE);
		register_info_two.setVisibility(View.GONE);
		register_info_three.setVisibility(View.GONE);
		register_info_four.setVisibility(View.GONE);
		register_info_five.setVisibility(View.GONE);
		if(index == 1){
			register_info_one.setVisibility(View.VISIBLE);
		}
		if(index == 2){
			register_info_two.setVisibility(View.VISIBLE);
		}
		if(index == 3){
			register_info_three.setVisibility(View.VISIBLE);
		}
		if(index == 4){
			register_info_four.setVisibility(View.VISIBLE);
		}
		if(index == 5){
			register_info_five.setVisibility(View.VISIBLE);
		}
	}
	
	@OnClick({R.id.register_info_return,R.id.register_info_submit,R.id.register_info_one_submit,R.id.register_info_two_submit,R.id.register_info_three_submit,R.id.register_info_four_submit,R.id.addmedicationsituation})
	public void onUpdate(View v){
		if(v.getId() == R.id.register_info_return){
			finish();
		}else if(v.getId() == R.id.addmedicationsituation){

			View view = mInflater.inflate(R.layout.item_medicationsituation,null);

			add_view_layout.addView(view);

		}else if(v.getId() == R.id.register_info_one_submit){
			replaceView(2);
		}else if(v.getId() == R.id.register_info_two_submit){
			replaceView(3);
		}else if(v.getId() == R.id.register_info_three_submit){
			replaceView(4);
		}else if(v.getId() == R.id.register_info_four_submit){
			if(userInfo == null){
				T.showLong(activity, "获取注册信息失败!");
				return;
			}
			healthInfo.setMedical(		register_info_gerenjiwangbinshi.getText().toString());
			healthInfo.setInfection(	register_info_chuanrangbingshi.getText().toString());
			healthInfo.setTrauma(		register_info_waishang.getText().toString());
			healthInfo.setOperation(	register_info_shoushushi.getText().toString());
			healthInfo.setPregnancy(	register_info_liucanshi.getText().toString());
			healthInfo.setMenstruation(	register_info_yuejing.getText().toString());
			healthInfo.setAllergic(		register_info_guomingshi.getText().toString());
			healthInfo.setBlood(		register_info_shuxueshi.getText().toString());
			healthInfo.setFamilyMedical(register_info_jiazhubinshi.getText().toString());
			healthInfo.setCurrent(		register_info_muqianfuyaoqingkuang.getText().toString());
			healthInfo.setOthers(		register_info_qitabuchongqingkuang.getText().toString());
			healthInfo.setUserId(userInfo.userId);
			try {
				JSONObject healthInfoObject = new JSONObject();
				if(!ValidationUtils.isNull(healthInfo.getMedical())){
					healthInfoObject.put("medical", healthInfo.getMedical());
				}

				if(!ValidationUtils.isNull(healthInfo.getInfection())){
					healthInfoObject.put("infection", healthInfo.getInfection());
				}

				if(!ValidationUtils.isNull(healthInfo.getTrauma())){
					healthInfoObject.put("trauma", healthInfo.getTrauma());
				}

				if(!ValidationUtils.isNull(healthInfo.getOperation())){
					healthInfoObject.put("operation", healthInfo.getOperation());
				}

				if(!ValidationUtils.isNull(healthInfo.getPregnancy())){
					healthInfoObject.put("pregnancy", healthInfo.getPregnancy());
				}

				if(!ValidationUtils.isNull(healthInfo.getMenstruation())){
					healthInfoObject.put("menstruation", healthInfo.getMenstruation());
				}

				if(!ValidationUtils.isNull(healthInfo.getAllergic())){
					healthInfoObject.put("allergic", healthInfo.getAllergic());
				}

				if(!ValidationUtils.isNull(healthInfo.getBlood())){
					healthInfoObject.put("blood", healthInfo.getBlood());
				}

				if(!ValidationUtils.isNull(healthInfo.getFamilyMedical())){
					healthInfoObject.put("familyMedical", healthInfo.getFamilyMedical());
				}

				if(!ValidationUtils.isNull(healthInfo.getCurrent())){
					healthInfoObject.put("current", healthInfo.getCurrent());
				}

				if(!ValidationUtils.isNull(healthInfo.getOthers())){
					healthInfoObject.put("others", healthInfo.getOthers());
				}
				JSONObject params  = new JSONObject();
				params.put("healthInfo", healthInfoObject);
				RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject arg0) {
						if(update == 1){
							T.showShort(activity, "修改成功!");
						}else{
							register_info_one.setVisibility(View.GONE);
							register_info_two.setVisibility(View.VISIBLE);
							headerView.setTitle("填写完成");
						}
					}
				}, new RequestErrorListener() {
					@Override
					public void requestError(VolleyError e) {
						if(update == 1){
							T.showShort(activity, "修改成功!");
						}else{
							register_info_one.setVisibility(View.GONE);
							register_info_two.setVisibility(View.VISIBLE);
							headerView.setTitle("填写完成");
						}
					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
//			replaceView(5);
		} else if(v.getId() == R.id.register_info_submit){
//			if(userInfo == null){
//				T.showLong(activity, "获取注册信息失败!");
//				return;
//			}
//			healthInfo.setMedical(		register_info_gerenjiwangbinshi.getText().toString());
//			healthInfo.setInfection(	register_info_chuanrangbingshi.getText().toString());
//			healthInfo.setTrauma(		register_info_waishang.getText().toString());
//			healthInfo.setOperation(	register_info_shoushushi.getText().toString());
//			healthInfo.setPregnancy(	register_info_liucanshi.getText().toString());
//			healthInfo.setMenstruation(	register_info_yuejing.getText().toString());
//			healthInfo.setAllergic(		register_info_guomingshi.getText().toString());
//			healthInfo.setBlood(		register_info_shuxueshi.getText().toString());
//			healthInfo.setFamilyMedical(register_info_jiazhubinshi.getText().toString());
//			healthInfo.setCurrent(		register_info_muqianfuyaoqingkuang.getText().toString());
//			healthInfo.setOthers(		register_info_qitabuchongqingkuang.getText().toString());
//			healthInfo.setUserId(userInfo.userId);
//			try {
//				JSONObject healthInfoObject = new JSONObject();
//				if(!ValidationUtils.isNull(healthInfo.getMedical())){
//					healthInfoObject.put("medical", healthInfo.getMedical());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getInfection())){
//					healthInfoObject.put("infection", healthInfo.getInfection());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getTrauma())){
//					healthInfoObject.put("trauma", healthInfo.getTrauma());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getOperation())){
//					healthInfoObject.put("operation", healthInfo.getOperation());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getPregnancy())){
//					healthInfoObject.put("pregnancy", healthInfo.getPregnancy());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getMenstruation())){
//					healthInfoObject.put("menstruation", healthInfo.getMenstruation());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getAllergic())){
//					healthInfoObject.put("allergic", healthInfo.getAllergic());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getBlood())){
//					healthInfoObject.put("blood", healthInfo.getBlood());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getFamilyMedical())){
//					healthInfoObject.put("familyMedical", healthInfo.getFamilyMedical());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getCurrent())){
//					healthInfoObject.put("current", healthInfo.getCurrent());
//				}
//
//				if(!ValidationUtils.isNull(healthInfo.getOthers())){
//					healthInfoObject.put("others", healthInfo.getOthers());
//				}
//				JSONObject params  = new JSONObject();
//				params.put("healthInfo", healthInfoObject);
//				RequestManager.postObject(Constens.ACCOUNT_UPDATE, activity, params, new Response.Listener<JSONObject>() {
//					public void onResponse(JSONObject arg0) {
//						if(update == 1){
//							T.showShort(activity, "修改成功!");
//						}else{
//							register_info_one.setVisibility(View.GONE);
//							register_info_five.setVisibility(View.VISIBLE);
//							headerView.setTitle("填写完成");
//						}
//					}
//				}, new RequestErrorListener() {
//					@Override
//					public void requestError(VolleyError e) {
//						if(update == 1){
//							T.showShort(activity, "修改成功!");
//						}else{
//							register_info_one.setVisibility(View.GONE);
//							register_info_five.setVisibility(View.VISIBLE);
//							headerView.setTitle("填写完成");
//						}
//					}
//				});
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
		}
	}

	@Override
	public void onBackPressed() {
		if(this.index == 2){
			replaceView(1);
		}else if(this.index == 3){
			replaceView(2);
		}else if(this.index == 4){
			replaceView(3);
		}else{
			super.onBackPressed();
		}
	}

	@Override
	protected void onBuildVersionGT_KITKAT(SystemBarConfig systemBarConfig) {
		headerView.setKitkat(systemBarConfig);
	}
}
