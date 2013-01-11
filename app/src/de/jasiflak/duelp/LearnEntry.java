package de.jasiflak.duelp;



public class LearnEntry 
{
	int mId;
	String mDatum;
	String mFach;
	String mStart;
	String mEnde;
	String mOrt;
	String mFruehstueck;

	//leerer Konstruktor
	public LearnEntry()
	{}
	
	public LearnEntry(int id, String datum, String fach, String start, String ende, String ort, String fruehstueck)
	{
		this.mId = id;
		this.mDatum = datum;
		this.mFach = fach;
		this.mStart = start;
		this.mEnde = ende;
		this.mOrt = ort;
		this.mFruehstueck = fruehstueck;
	}
	
	public LearnEntry(String datum, String fach, String start, String ende, String ort, String fruehstueck)
	{
		this.mDatum = datum;
		this.mFach = fach;
		this.mStart = start;
		this.mEnde = ende;
		this.mOrt = ort;
		this.mFruehstueck = fruehstueck;
	}
	public String toString() 
	{
		String string;
		string = mDatum+";"+mFach+";"+mStart+";"+mEnde+";"+mOrt+";"+mFruehstueck;
		
		return string;
	}
	
	public static LearnEntry fromString(String lernplan) 
	{
		LearnEntry learnEntry = new LearnEntry();
		String parsed[] = lernplan.split(";");

		learnEntry.mDatum = parsed[0];
		learnEntry.mFach = parsed[1];
		learnEntry.mStart = parsed[2];
		learnEntry.mEnde = parsed[3];
		learnEntry.mOrt = parsed[4];
		learnEntry.mFruehstueck = parsed[5];
		
		return learnEntry;
	}
	
	//Konstruktor
	/*public LearnEntry(int id, Date datum, String fach, Date start, Date ende, String ort, boolean fruehstuek)
	{
		this.mId = id;
		this.mDatum = datum;
		this.mFach = fach;
		this.mStart =start;
		this.mEnde = ende;
		this.mOrt = ort;
		this.mFruehstuek = fruehstuek;
	}
	*/
	
	/*
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
	*/
	
	/*public LearnEntry(String id, String datum, String fach, String start, String ende, String ort, String fruehstueck) 
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
	*/

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
		return this.mDatum;
	}
	
	public void setDate(String datum)
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
		return this.mStart;
	}
	
	public void setStart(String start)
	{
		this.mStart = start;
	}
	
	public String getEnde()
	{
		return this.mEnde;
	}
	
	public void setEnde(String ende)
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
	
	public String getFruehstueck()
	{
		return this.mFruehstueck;
	}
	
	public void setFruehstueck(String fruehstueck)
	{
		this.mFruehstueck = fruehstueck;
	}
}
