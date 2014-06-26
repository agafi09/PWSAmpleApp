package com.applaudo.phunwaresampleapp;

import java.util.ArrayList;

import com.applaudo.phunwaresampleapp.common.Constants;
import com.applaudo.phunwaresampleapp.common.Message;
import com.applaudo.phunwaresampleapp.dataaccesslayer.Networking;
import com.applaudo.phunwaresampleapp.dataaccesslayer.RemoteProvider;
import com.applaudo.phunwaresampleapp.entitylayer.ServiceActionResult;
import com.applaudo.phunwaresampleapp.entitylayer.ServiceActionResult.ServiceActionResultCode;
import com.applaudo.phunwaresampleapp.entitylayer.Venue;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;

public class VenueMainActivity extends ActionBarActivity 
implements VenuesFragment.OnVenueSelectedListener, 
		   RemoteProvider.OnContentDownloadCompletedListener{

	private static final String VENUE_LIST = "venus_list";
	private static final String DETAIL_FRAGMENT_TAG = "detail_fragment_tag";
	public ArrayList<Venue> mVenueArrayList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.venue_main);

		// creates the venue fragment and add it to the container - Phones only
		if (findViewById(R.id.container) != null) {
			if (savedInstanceState != null) {
				return;
			}
			VenuesFragment mVenueFragment = new VenuesFragment();
			mVenueFragment.setArguments(getIntent().getExtras());

			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, mVenueFragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home: 
			onBackPressed();
		default:
			return true;   
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mVenueArrayList != null)
			outState.putParcelableArrayList(VENUE_LIST, mVenueArrayList);
	}

	@Override  
	public void onRestoreInstanceState(Bundle savedInstanceState) {  
		super.onRestoreInstanceState(savedInstanceState);  

		mVenueArrayList = savedInstanceState.getParcelableArrayList(VENUE_LIST); 

	}
	
	@Override
	public void onVenueSelected(int position) {
		Networking mNetworking = new Networking(this);
		if (mNetworking.isOnline()) {
			final ActionBar mBar = getSupportActionBar();
			mBar.setTitle("Details");

			VenueDetailFragment mVenueDetailFragment = (VenueDetailFragment) getSupportFragmentManager()
					.findFragmentById(R.id.venue_detail_fragment);

			if(mVenueDetailFragment != null) {
				mVenueDetailFragment.updateVenue(mVenueArrayList.get(position));
				mVenueDetailFragment.updateVenueView();
			}else{
				VenueDetailFragment mNewVenueDetailFragment = new VenueDetailFragment();
				mNewVenueDetailFragment.updateVenue(mVenueArrayList.get(position));

				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, mNewVenueDetailFragment,DETAIL_FRAGMENT_TAG);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		}else{
			Message.showErrorMessageWithAlert(this, 
					"Network Error", 
					"No internet connection avaiable, please check your network settings", 
					"Close");
		}
	}
	
	/**
	 * Callback method that it's been called after a image was downloaded
	 * Notify the fragments in order to update the UI
	 */
	@Override
	public void onContentDownloadCompletedListener(ServiceActionResult result) {
		ArrayList<Venue> tempResult = (ArrayList<Venue>)result.getResult();
		if (result.getReturnCode()==ServiceActionResultCode.SERVICE_CODE_SUCCESS) {
			if (tempResult != null) {
				mVenueArrayList = tempResult;

				// Get the venues fragment (for phones)
				if (findViewById(R.id.container) != null) {
					VenuesFragment mVenueFragment = (VenuesFragment) getSupportFragmentManager()
							.findFragmentById(R.id.container);
					if(mVenueFragment != null) {
						mVenueFragment.updateVenueList(mVenueArrayList);
					}
				}else{
					// Get the venues fragment (for tablets)
					if (findViewById(R.id.venues_fragment) != null) {
						VenuesFragment mVenueFragmentLarge = (VenuesFragment) getSupportFragmentManager()
								.findFragmentById(R.id.venues_fragment);
						if(mVenueFragmentLarge != null && mVenueFragmentLarge.isInLayout()) {
							mVenueFragmentLarge.updateVenueList(mVenueArrayList);
						}
					}
				}

				if (findViewById(R.id.venue_detail_fragment) != null) {
					VenueDetailFragment mVenueDetailFragment = (VenueDetailFragment) getSupportFragmentManager()
							.findFragmentById(R.id.venue_detail_fragment);

					if(mVenueDetailFragment != null) {
						mVenueDetailFragment.updateVenue(mVenueArrayList.get(0));
					}
				}
			}
		}else{
			Message.showErrorMessageWithAlert(this, 
					"Error", 
					"There was and error downloading the content please try again", 
					"Close");
		}
	}

	/**
	 * Method that sets the title for the ActionBar
	 */
	public void setActionBarTitle(String title) {
		final ActionBar mBar = getSupportActionBar();

		if (title==Constants.ACTIONBAR_DETAILS_TITLE) {
			// set the details title only if is not Tablet
			if (findViewById(R.id.container) != null) {
				mBar.setTitle(title);
				mBar.setDisplayHomeAsUpEnabled(true);
				mBar.setHomeButtonEnabled(true);
			}
		}
		else{
			mBar.setTitle(title);
			mBar.setDisplayHomeAsUpEnabled(false);
			mBar.setHomeButtonEnabled(false);

		}
	}

	/**
	 * Callback method that it's been called after a image was downloaded
	 * Notify the fragments in order to update the UI
	 */
	@Override
	public void onImageDownloadCompletedListener(Bitmap result) {

		// Get the reference of the detail fragment (for tablets)
		VenueDetailFragment mVenueDetailFragment = (VenueDetailFragment) getSupportFragmentManager()
				.findFragmentById(R.id.venue_detail_fragment);
		// if the reference is null then it's because the fragment was added manually (for phones)
		if(mVenueDetailFragment == null) {
			mVenueDetailFragment = (VenueDetailFragment) getSupportFragmentManager()
					.findFragmentByTag(DETAIL_FRAGMENT_TAG);
		}
		if (mVenueDetailFragment != null)
			mVenueDetailFragment.updateVenueDetailImage(result);

	}

}
