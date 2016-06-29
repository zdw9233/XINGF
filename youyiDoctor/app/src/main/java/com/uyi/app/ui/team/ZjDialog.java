package com.uyi.app.ui.team;

import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uyi.doctor.app.R;
import com.volley.ImageCacheManager;









/**
* 自定义dialog
* @author Mr.Xu
*
*/
public class ZjDialog extends Dialog {
        //定义回调事件，用于dialog的点击事件
      
        
    
        Map<String, Object> doctor ;
       ImageView icon;
       TextView name,info,byzx,zzx;
       Button exit;
        public ZjDialog(Context context, Map<String, Object> doctor) {
                super(context,R.style.dialog);
                this.doctor = doctor;
               
        }
        
        @Override
        protected void onCreate(Bundle savedInstanceState) { 
                super.onCreate(savedInstanceState);
                setContentView(R.layout.dialog_item_zhuanjia);
           
                icon = (ImageView)findViewById(R.id.item_zhuanjia_icon);
                name = (TextView) findViewById(R.id.item_zhuanjia_name);
                info = (TextView) findViewById(R.id.item_zhuanjia_info);
                byzx = (TextView) findViewById(R.id.team_zixunshu);
                zzx = (TextView) findViewById(R.id.team_zonzixunshu);
                exit = (Button) findViewById(R.id.doctor_exit);
                name.setText(doctor.get("realName").toString());
                info.setText(doctor.get("info").toString());
                byzx.setText(doctor.get("currentNum")+"");
                zzx.setText(doctor.get("totalNum")+"");
                ImageCacheManager.loadImage((doctor.get("icon").toString()), ImageCacheManager.getImageListener(icon, null, null));
                exit.setOnClickListener(clickListener);
        }
        
        private View.OnClickListener clickListener = new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                       
                	ZjDialog.this.dismiss();
                }
        };

}
