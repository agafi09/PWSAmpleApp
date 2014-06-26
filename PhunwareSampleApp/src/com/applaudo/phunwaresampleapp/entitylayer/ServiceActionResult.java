package com.applaudo.phunwaresampleapp.entitylayer;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceActionResult implements Parcelable {

	
	public final int SERVICE_CODE_SUCCESS = 1;
	public final int SERVICE_CODE_RECORDSFOUND = 2;
	public final int SERVICE_CODE_NORECORDSFOUND = 3;
	public final int SERVICE_CODE_ERROR = 4;
	public final int SERVICE_CODE_WARNING = 5;
	public final int SERVICE_CODE_FAILURE = 6;
	public final int SERVICE_CODE_USERNOTFOUND = 7;
	public final int SERVICE_CODE_SESSIONEXPIRED = 12;

	private Object result;
	private String message;
	private int returnCode;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(count);
		dest.writeString(message);
		dest.writeInt(returnCode);
		dest.writeSerializable( (Serializable) (result));
	}

	public ServiceActionResult(Parcel in) {
		this.count = in.readInt();
		this.message = in.readString();
		this.returnCode = in.readInt();
		this.result = in.readSerializable();
	}

	public ServiceActionResult() {

	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public ServiceActionResult createFromParcel(Parcel in) {
			return new ServiceActionResult(in);
		}

		public ServiceActionResult[] newArray(int size) {
			return new ServiceActionResult[size];
		}
	};

	
}