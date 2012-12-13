package de.jasiflak.duelp;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
import android.view.View;

public class FaecherDetail extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faecher_detail_layout);
		
		Intent intent = getIntent();
		((TextView)(findViewById(R.id.textView1))).setText("Es wurde "+intent.getStringExtra("selected")+ " gewählt!");
		
		
	}
}
