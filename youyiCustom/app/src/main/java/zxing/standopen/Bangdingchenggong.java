package zxing.standopen;


import com.uyi.custom.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Bangdingchenggong extends Activity {
	private TextView sabmit,bianhao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		  setContentView(R.layout.group_bangding_sucess);
		  sabmit = (TextView) findViewById(R.id.bangding_queding);
		  bianhao = (TextView) findViewById(R.id.chonxingbiaohaochengg);
		  String info=this.getIntent().getStringExtra("info");
		  bianhao.setText(info);
		  sabmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bangdingchenggong.this.finish();
				
			}
		});
	}
}
