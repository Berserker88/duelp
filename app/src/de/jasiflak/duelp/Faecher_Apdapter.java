package de.jasiflak.duelp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Faecher_Apdapter extends BaseAdapter//ArrayAdapter<Fach> 
{
	private final Faecher context;
	private List<Fach> mFaecher;

	public Faecher_Apdapter (Context context, List<Fach> faecher)
	{	
		//super(context, R.layout.faecher_layout, faecher);

		this.context = (Faecher) context;
		this.mFaecher = faecher;
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
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
			textView.setText(mFaecher.get(position).getmName());	
		}
		else
		{
			if(mFaecher.get(position).ismCheckedIn() != 0)
			{
				chkBox.setChecked(true);	
			}
			else
			{
				chkBox.setChecked(false);
				
			}
			
			OnClickListener checkBoxListener;
			checkBoxListener = new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					context.updateCheckedStateOnServer(position);
					notifyDataSetChanged();
						context.refreshData();
				};
			};
			
			chkBox.setOnClickListener(checkBoxListener);
			
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
			try {
				Date date = dateFormatter.parse(mFaecher.get(position).getmDate());
				dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
				textView.setText(mFaecher.get(position).getmName()+" - "+dateFormatter.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
				Log.i("Debug","Parsing fail: " + e.getLocalizedMessage());
				textView.setText(mFaecher.get(position).getmName());	
			}
			
			
			int margin = 75;
			for (int i= 0; i<mFaecher.get(position).getmRating();i++ )
			{
				ImageView iv = new ImageView(context);
				iv.setScaleX((float) 0.3);
				iv.setScaleY((float) 0.3);
				iv.setImageResource(R.drawable.duelp_rating_bar_full);
		 
				RelativeLayout rl = (RelativeLayout) rowView.findViewById(R.id.faecherRelativeLayout);
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				    RelativeLayout.LayoutParams.WRAP_CONTENT,
				    RelativeLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(0, 5, margin ,0);
				lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				rl.addView(iv, lp);	
				
				margin+=20;
				
			}
				
		}

		//DEBUG
		/*for (Fach f : this.mFaecher)
		{
			Log.i("Debug","Fach: "+f.toString());
		}*/
		
		
 
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