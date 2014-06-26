package com.applaudo.phunwaresampleapp.entitylayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class ScheduleItem  implements Parcelable {
	@SerializedName("start_date")
	private String mStartDate;
	@SerializedName("end_date")
	private String mEndDate;
	
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss Z");
	
	public ScheduleItem() {  
	} 

	public ScheduleItem(Parcel in) {  
	     readFromParcel(in);  
	} 


	public String getStartDate() {
		return mStartDate;
	}
	
	@SuppressLint("SimpleDateFormat")
	public Date getStartDateWithFormat() throws ParseException {
		return FORMATTER.parse(mStartDate);
	}

	public void setStartDate(String startDate) {
		mStartDate = startDate;
	}

	public String getEndDate() {
		return mEndDate;
	}
	
	@SuppressLint("SimpleDateFormat")
	public Date getEndDateWithFormat() throws ParseException {
		return FORMATTER.parse(mEndDate);
	}

	public void setEndDate(String endDate) {
		mEndDate = endDate;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof ScheduleItem) {
			result = mStartDate.equals(((ScheduleItem) o).getStartDate())
					&& mEndDate.equals(((ScheduleItem) o).getEndDate());
		}
		return result;
	}

	@Override
	public int hashCode() {
		String s = getStartDate() + getEndDate();
		return s.hashCode();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mStartDate);
		dest.writeString(mEndDate);
	}
	
	private void readFromParcel(Parcel in) {    
		mStartDate = in.readString();
		mEndDate = in.readString();
    }  
	
	public static final Parcelable.Creator<ScheduleItem> CREATOR = new Parcelable.Creator<ScheduleItem>() {
	public ScheduleItem createFromParcel(Parcel in) {
	    return new ScheduleItem(in);
	}

	public ScheduleItem[] newArray(int size) {
	    return new ScheduleItem[size];
	}
	};
	

}