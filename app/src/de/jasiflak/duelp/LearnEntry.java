package de.jasiflak.duelp;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class LearnEntry 
{
	int mId;
	Date mDatum;
	String mFach;
	Date mStart;
	Date mEnde;
	String mOrt;
	boolean mFruehstuek;

	//leerer Konstruktor
	public LearnEntry()
	{}
	
	//Konstruktor
	public LearnEntry(int id, Date datum, String fach, Date start, Date ende, String ort, boolean fruehstuek)
	{
		this.mId = id;
		this.mDatum = datum;
		this.mFach = fach;
		this.mStart =start;
		this.mEnde = ende;
		this.mOrt = ort;
		this.mFruehstuek = fruehstuek;
	}
	
	//Konstruktor
	public LearnEntry(Date datum, String fach, Date start, Date ende, String ort, boolean fruehstuek)
	{
		this.mDatum = datum;
		this.mFach = fach;
		this.mStart =start;
		this.mEnde = ende;
		this.mOrt = ort;
		this.mFruehstuek = fruehstuek;
	}
	
	public LearnEntry(String id, String datum, String fach, String start, String ende, String ort, String fruehstueck) 
	{
		SimpleDateFormat  formatDate = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat  formatTime = new SimpleDateFormat("HH:mm");
		
		this.mId = Integer.parseInt(id);
		try 
		{
			this.mDatum = formatDate.parse(datum);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.mFach = fach; 
		
		try {
			
			this.mStart = formatTime.parse(start);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
			this.mEnde = formatTime.parse(ende);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.mOrt = ort;
		
		if (fruehstueck == "ja")
		{	
			this.mFruehstuek = true ;
		}
		else
		{
			this.mFruehstuek = false;
		}
		
		Log.i("debug", "Id: "+ mId+ " Datum: " + mDatum.toString() + " Fach: " +mFach + " Start: "+ mStart.toString() + " Ende: " +mEnde.toString() + " Ort: " + mOrt);
			
	}

	//##### GET UND SET METHODEN #####
	public int getId()
	{
		return this.mId;
	}
	
	public void setId(int id)
	{
		this.mId = id;
	}
	
	public String getDate()
	{
		return this.mDatum.toString();
	}
	
	public void setDate(Date datum)
	{
		this.mDatum = datum;
	}
	
	public String getFach()
	{
		return this.mFach;
	}
	
	public void setFach(String fach)
	{
		this.mFach = fach;
	}
	
	public String getStart()
	{
		return this.mStart.toString();
	}
	
	public void setStart(Date start)
	{
		this.mStart = start;
	}
	
	public String getEnde()
	{
		return this.mEnde.toString();
	}
	
	public void setEnde(Date ende)
	{
		this.mEnde = ende;
	}
	
	public String getOrt()
	{
		return this.mOrt;
	}
	
	public void setOrt(String ort)
	{
		this.mOrt = ort;
	}
	
	public boolean getFruehstueck()
	{
		return this.mFruehstuek;
	}
	
	public void setFruehstueck(boolean fruehstueck)
	{
		this.mFruehstuek = fruehstueck;
	}
}
