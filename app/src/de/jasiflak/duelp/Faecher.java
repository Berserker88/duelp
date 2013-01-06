package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class Faecher extends ListActivity 
{
	static final String[] MOBILE_OS = new String[] { "Android", "iOS",
		"WindowsMobile", "Blackberry"};
	
	public void onCreate(Bundle savedInstanceState)
	{
		
		Log.i("Debug","Create test...");

		super.onCreate(savedInstanceState);
		//setContentView(R.layout.faecher_layout);
		
		//setListAdapter(new ArrayAdapter<String>(this, R.layout.list_mobile,
		//		R.id.label, MOBILE_OS));

		setListAdapter(new Faecher_Apdapter(this, MOBILE_OS));
		
		//Checkbox
		final CheckBox chkBox = (CheckBox) findViewById(R.id.chkbox);
		 
		OnClickListener checkBoxListener;
		checkBoxListener =new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				if(chkBox.isChecked())
				{
					Log.i("Debug","Checkbox is checked..");	
				}	
				else
				{
					Log.i("Debug","Checkbox is checked..");	
				}
			};
		};
		

		//DATASOURCE
		/*ArrayList<String> valueList = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
		{
			valueList.add("value" + i);
		}*/

		//LIST

		/*ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
	    final ListView lv = (ListView)findViewById(R.id.listView1);
	    lv.setAdapter(adapter);	*/

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Log.i("Debug","item select....");
		Intent intent = new Intent();
		intent.setClassName(getPackageName(), getPackageName()+".FaecherDetail");
		//intent.putExtra("selected",MOBILE_OS[position]);
		startActivity(intent);
	}
	

  
	    
}
