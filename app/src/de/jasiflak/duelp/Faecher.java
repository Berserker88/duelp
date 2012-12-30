package de.jasiflak.duelp;

import java.util.ArrayList;

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
import android.view.View;

public class Faecher extends ListActivity 
{
	static final String[] MOBILE_OS = new String[] { "Android", "iOS",
		"WindowsMobile", "Blackberry"};
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.faecher_layout);
		
		//setListAdapter(new ArrayAdapter<String>(this, R.layout.list_mobile,
		//		R.id.label, MOBILE_OS));

		setListAdapter(new Faecher_Apdapter(this, MOBILE_OS));
		
		
		
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
	    
	    
	    //ONCLICK
	   /* lv.setOnItemClickListener(new OnItemClickListener()
	    {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	    	{
	    		Intent intent = new Intent();
	    		intent.setClassName(getPackageName(), getPackageName()+".FaecherDetail");
	    		intent.putExtra("selected", lv.getAdapter().getItem(arg2).toString());
	    		startActivity(intent);
	    	}
	    });*/
	    
	    
	    
	}
}
