package de.jasiflak.duelp;
public class Fach {

	private int mId;
	private String mName;
	private String mDate;
	private int mRating;
	private boolean mCheckedIn;
	
	public Fach (int id,String name, String date, int rat, boolean checked)
	{
		this.mId = id;
		this.mName = name;
		this.mDate = date;
		this.mRating = rat;
		this.mCheckedIn = checked;
	}
	
	public boolean ismCheckedIn() {
		return mCheckedIn;
	}
	public void setmCheckedIn(boolean mCheckedIn) {
		this.mCheckedIn = mCheckedIn;
	}
	public int getmRating() {
		return mRating;
	}
	public void setmRating(int mRating) {
		this.mRating = mRating;
	}
	public String getmDate() {
		return mDate;
	}
	public void setmDate(String mDate) {
		this.mDate = mDate;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	
	public String toString() {
		   return this.mId + " - "+ this.getmName() + " - " + this.getmDate()+ " - " + this.getmRating();
		}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}
}
