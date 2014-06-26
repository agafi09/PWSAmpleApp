package com.applaudo.phunwaresampleapp.datalayer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.applaudo.phunwaresampleapp.entitylayer.*;

public class Parser {

	public static ArrayList<Venue> parseVenueResponseWithGson(String jsonString) {
		ArrayList<Venue> mVenues = null;
		try {
			if (jsonString != null) {
				Log.d("Parser.GSON",jsonString);
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.create();
				List<Venue> venues = new ArrayList<Venue>();
				venues = Arrays.asList(gson.fromJson(jsonString, Venue[].class));
                mVenues = new ArrayList<Venue>(venues);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mVenues;
	}
	
	public static ArrayList<Venue> parseVenueResponse(String jsonString) {
		ArrayList<Venue> mVenues = null;
		try {
			if (jsonString != null) {
				mVenues = getListVenue(jsonString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mVenues;
	}

	public static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		return sb.toString();
	}

	public static ArrayList<Venue> getListVenue(String result) {
		ArrayList<Venue> venueList = new ArrayList<Venue>();
		try {
			JSONArray venueJSON = new JSONArray(result);
			for (int i = 0; i < venueJSON.length(); i++) {
				JSONObject jsonVenueObject = venueJSON.getJSONObject(i);
				Venue ven = populateVenue(jsonVenueObject);
				venueList.add(ven);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return venueList;
	}

	private static Venue populateVenue(JSONObject venueObject) {
		Venue ven = new Venue();
		ArrayList<ScheduleItem> listSchedule = new ArrayList<ScheduleItem>();
		try {
			JSONArray scheduleArray = venueObject.getJSONArray("schedule");
			if (scheduleArray.toString().equalsIgnoreCase("[]")) {
				listSchedule = null;
			} else {
				for (int i = 0; i < scheduleArray.length(); i++) {
					JSONObject obj = scheduleArray.getJSONObject(i);
					ScheduleItem scheduleItem = populateSchedule(obj);
					listSchedule.add(scheduleItem);
				}
			}

		}catch (JSONException e1) {
			e1.printStackTrace();
		}

		try {
			ven = new Venue(venueObject.getDouble("id"), 
					  venueObject.getDouble("pcode"), 
					  venueObject.getDouble("latitude"), 
					  venueObject.getDouble("longitude"),
					  venueObject.getString("name"), 
					  venueObject.getString("address"), 
					  venueObject.getString("city"), 
					  venueObject.getString("state"),
					  venueObject.getString("zip"), 
					  venueObject.getString("phone"), 
					  venueObject.getString("tollfreephone"), 
					  venueObject.getString("description"),
					  venueObject.getString("ticket_link"), 
					  venueObject.getString("image_url").trim(), 
					  listSchedule);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return ven;
	}

	private static ScheduleItem populateSchedule(JSONObject scheduleObject) throws JSONException {
		ScheduleItem schedule = new ScheduleItem();
	//	try {
			schedule.setStartDate(scheduleObject.getString("start_date"));
			schedule.setEndDate(scheduleObject.getString("end_date"));
	//	} catch (ParseException e) {
	//		e.printStackTrace();
	//	}

		return schedule;
	}

}
