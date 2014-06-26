package com.applaudo.phunwaresampleapp;

import java.util.ArrayList;

import com.applaudo.phunwaresampleapp.dataaccesslayer.Networking;
import com.applaudo.phunwaresampleapp.entitylayer.Venue;
import com.applaudo.phunwaresampleapp.common.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applaudo.phunwaresampleapp.dataaccesslayer.RemoteProvider;

public class VenuesFragment extends ListFragment{

	private Context mContext;
	private static final String TAG = "VenuesFragment";
	private static final String VENUE_LIST = "venue_list";
	private ArrayList<Venue> mVenueList = null;
	
	private OnVenueSelectedListener mCallback;
	public interface OnVenueSelectedListener {
		public void onVenueSelected(int position);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		if(savedInstanceState == null) {
			Networking mNetworking = new Networking(mContext);
			if (mNetworking.isOnline()){
				RemoteProvider remoteProvider = RemoteProvider.getInstance();
				RemoteProvider.initRemoteProvider(mContext);
				remoteProvider.downloadVenuesList();
			}else{
				Message.showErrorMessageWithAlert(mContext, 
												  "Network Error", 
												  "No internet connection avaiable, please check your network settings", 
												  "Close");
			}
		}else{
			mVenueList = savedInstanceState.getParcelableArrayList(VENUE_LIST);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((VenueMainActivity) getActivity()).setActionBarTitle(Constants.ACTIONBAR_HOME_TITLE);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify the fragment it's implementing the communication interface
		try {
			mCallback = (OnVenueSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnVenueSelectedListener");
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		// If both fragments are visible at the same time (for Tablets layout)
		if (getFragmentManager().findFragmentById(R.id.container) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
		updateVenueList(mVenueList);
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		if (mVenueList != null)
			outState.putParcelableArrayList(VENUE_LIST, mVenueList);

	}
	
	/**
	 * Click listener that sends the signal to the callback method
	 * Sends the current position selected as a parameter
	 */
	public void onListItemClick(ListView list, View view, int position, long id) {
		mCallback.onVenueSelected(position);
		getListView().setItemChecked(position, true);
	}
	
	/**
	 * Method that updates UI with the venue list downloaded from the amazon S3 bucket
	 */
	public void updateVenueView(ArrayList<Venue>list) {
		if(list != null) {
			Log.e(TAG, "Updating List");
			setListShownNoAnimation(true);
			mVenueList = list;
			int layout = android.R.layout.simple_list_item_2;
			setListAdapter(new ArrayAdapter<Venue>(mContext, layout, android.R.id.text1, mVenueList) {
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					View view = super.getView(position, convertView, parent);
					TextView venueTitle = (TextView) view
							.findViewById(android.R.id.text1);
					TextView venueAddress = (TextView) view
							.findViewById(android.R.id.text2);
					venueTitle.setText(mVenueList.get(position).getName());
					venueAddress.setText(mVenueList.get(position).getAddress());
					return view;
				}
			});
		}
	}
	
	/**
	 * Method that updates the global venue list with the list downloaded from the amazon S3 bucket
	 */
	public void updateVenueList(ArrayList<Venue>list) {
		mVenueList = (ArrayList<Venue>) list; 
		updateVenueView (mVenueList);
	}
	
}
