package de.jasiflak.duelp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class Faecher_Apdapter extends BaseAdapter//ArrayAdapter<Fach> 
{
	private final Context context;
	private List<Fach> mFaecher;

	public Faecher_Apdapter (Context context, List<Fach> faecher)
	{	
		//super(context, R.layout.faecher_layout, faecher);

		this.context = context;
		this.mFaecher = faecher;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.i("Debug","Adapter:GetView..");

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.faecher_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		
		//Checkbox
		final CheckBox chkBox = (CheckBox) rowView.findViewById(R.id.chkbox);
		
		
		//Set checkbox visibility
		if (position == 0)
		{
			chkBox.setVisibility(View.INVISIBLE);	
		}
		else
		{
			if(mFaecher.get(position).ismCheckedIn() != 0)
			{
				chkBox.setChecked(true);	
			}
			
			OnClickListener checkBoxListener;
			checkBoxListener = new OnClickListener()
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
			
			chkBox.setOnClickListener(checkBoxListener);
		}

		//DEBUG
		/*for (Fach f : this.mFaecher)
		{
			Log.i("Debug","Fach: "+f.toString());
		}*/
		
		textView.setText(mFaecher.get(position) .getmName());

		return rowView;
	}

	@Override
	public int getCount() {
		return this.mFaecher.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mFaecher.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}