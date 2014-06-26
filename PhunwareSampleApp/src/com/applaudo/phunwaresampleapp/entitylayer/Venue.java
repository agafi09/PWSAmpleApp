package com.applaudo.phunwaresampleapp.entitylayer;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class Venue implements Parcelable{

	@SerializedName("address")
	private String address;
	@SerializedName("city")
   	private String city;
	@SerializedName("description")
   	private String description;
	@SerializedName("id")
   	private double id;
	@SerializedName("image_url")
   	private String image_url;
	@SerializedName("latitude")
   	private double latitude;
	@SerializedName("longitude")
   	private double longitude;
	@SerializedName("name")
   	private String name;
	@SerializedName("pcode")
   	private double pcode;
   	@SerializedName("phone")
   	private String phone;
   	@SerializedName("schedule")
   	private ArrayList<ScheduleItem> schedule= new ArrayList<ScheduleItem>();;
   	@SerializedName("state")
   	private String state;
   	@SerializedName("ticket_link")
   	private String ticket_link;
   	@SerializedName("tollfreephone")
   	private String tollfreephone;
   	@SerializedName("zip")
   	private String zip;

 	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address = address;
	}
 	public String getCity(){
		return this.city;
	}
	public void setCity(String city){
		this.city = city;
	}
 	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
 	public double getId(){
		return this.id;
	}
	public void setId(double id){
		this.id = id;
	}
 	public String getImage_url(){
		return this.image_url;
	}
	public void setImage_url(String image_url){
		this.image_url = image_url;
	}
 	public double getLatitude(){
		return this.latitude;
	}
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
 	public double getLongitude(){
		return this.longitude;
	}
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public double getPcode(){
		return this.pcode;
	}
	public void setPcode(double pcode){
		this.pcode = pcode;
	}
 	public String getPhone(){
		return this.phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
 	public ArrayList<ScheduleItem> getSchedule(){
		return this.schedule;
	}
	public void setSchedule(ArrayList<ScheduleItem> schedule){
		this.schedule = schedule;
	}
 	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state = state;
	}
 	public String getTicket_link(){
		return this.ticket_link;
	}
	public void setTicket_link(String ticket_link){
		this.ticket_link = ticket_link;
	}
 	public String getTollfreephone(){
		return this.tollfreephone;
	}
	public void setTollfreephone(String tollfreephone){
		this.tollfreephone = tollfreephone;
	}
 	public String getZip(){
		return this.zip;
	}
	public void setZip(String zip){
		this.zip = zip;
	}
	
	public Venue() {
    }
	
	public Venue(double mId, double mPcode, double mLatitude, double mLongitude,
			String mName, String mAddress, String mCity, String mState,
			String mZip, String mPhone, String mTollFreePhone,
			String mDescription, String mTicketLink, String mImageUrl,
			ArrayList<ScheduleItem> mSchedule) {
		super();
		id = mId;
		pcode = mPcode;
		latitude = mLatitude;
		longitude = mLongitude;
		name = mName;
		address = mAddress;
		city = mCity;
		state = mState;
		zip = mZip;
		phone = mPhone;
		tollfreephone = mTollFreePhone;
		description = mDescription;
		ticket_link = mTicketLink;
		image_url = mImageUrl;
		schedule = mSchedule;
	}
	
	private Venue(Parcel in) {
        address = in.readString();
        city = in.readString();
        description = in.readString();
        id = in.readDouble();
        image_url = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
        pcode = in.readDouble();
        phone = in.readString();
        in.readTypedList(schedule, ScheduleItem.CREATOR);
        state = in.readString();
        ticket_link = in.readString();
        tollfreephone = in.readString();
        zip = in.readString();
    }
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(address);
		dest.writeString(city);
		dest.writeString(description);
		dest.writeDouble(id);
		dest.writeString(image_url);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeString(name);
       	dest.writeDouble(pcode);
       	dest.writeString(phone);
       	dest.writeTypedList(schedule);
       	dest.writeString(state);
       	dest.writeString(ticket_link);
       	dest.writeString(tollfreephone);
       	dest.writeString(zip);
	}
	
	public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator<Venue>() {
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}

