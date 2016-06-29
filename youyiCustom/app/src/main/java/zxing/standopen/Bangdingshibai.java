package zxing.standopen;


import com.uyi.custom.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Bangdingshibai extends Activity {
	private TextView sabmit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		  setContentView(R.layout.group_bangding_dafiat);
		  sabmit = (TextView) findViewById(R.id.bangdingshibai_queding);
		  sabmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bangdingshibai.this.finish();
				
			}
		});
	}
}
