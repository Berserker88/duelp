package de.jasiflak.duelp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TermineListe extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.termine_liste_layout);
		
		TextView tv_back = (TextView) findViewById(R.id.tv_list_back);
		tv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public void onDestroy() {
		Log.i("info", "destroy!!!!");
		super.onDestroy();
	}
}
