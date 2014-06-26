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

	private Context context;
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
		context = getActivity();
		if(savedInstanceState == null) {
			Networking mNetworking = new Networking(context);
			if (mNetworking.isOnline()){
				RemoteProvider remoteProvider = RemoteProvider.getInstance();
				RemoteProvider.initRemoteProvider(context);
				remoteProvider.downloadVenuesList();
			}else{
				Message.showErrorMessageWithAlert(context, 
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
	
	public void onListItemClick(ListView list, View view, int position, long id) {
		mCallback.onVenueSelected(position);
		getListView().setItemChecked(position, true);
	}
	
	public void updateVenueView(ArrayList<Venue>list) {
		if(list != null) {
			Log.e(TAG, "Updating List");
			setListShownNoAnimation(true);
			mVenueList = list;
			int layout = android.R.layout.simple_list_item_2;
			setListAdapter(new ArrayAdapter<Venue>(context, layout, android.R.id.text1, mVenueList) {
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
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		if (mVenueList != null)
			outState.putParcelableArrayList(VENUE_LIST, mVenueList);

	}
	
	public void updateVenueList(ArrayList<Venue>list) {
		mVenueList = (ArrayList<Venue>) list; 
		updateVenueView (mVenueList);
	}
	
}
