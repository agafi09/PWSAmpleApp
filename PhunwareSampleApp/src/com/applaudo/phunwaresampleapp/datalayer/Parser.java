package com.applaudo.phunwaresampleapp.datalayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.applaudo.phunwaresampleapp.entitylayer.*;

public class Parser {
	
	/**
	 * Method parses a given JSON string parameter
	 * Returns a list of Venues
	 */
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

}
