package com.applaudo.phunwaresampleapp;

import java.text.ParseException;
import java.util.Date;

import com.applaudo.phunwaresampleapp.common.Constants;
import com.applaudo.phunwaresampleapp.common.DateConverter;
import com.applaudo.phunwaresampleapp.common.Message;
import com.applaudo.phunwaresampleapp.dataaccesslayer.RemoteProvider;
import com.applaudo.phunwaresampleapp.entitylayer.Venue;
import com.applaudo.phunwaresampleapp.entitylayer.ScheduleItem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class VenueDetailFragment extends Fragment{
	
	private final static String VENUE_IMAGE = "venue_image";
	private final static String CURRENT_VENUE = "venus_list";
	
	private Venue mCurrentVenue = null;
	private Context context;
	private Bitmap mVenueImage = null;
	private Boolean isViewRotated = false; 

	private ShareActionProvider mShareActionProvider;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			isViewRotated = true;
			mVenueImage = savedInstanceState.getParcelable(VENUE_IMAGE);
			mCurrentVenue = savedInstanceState.getParcelable(CURRENT_VENUE);
			
		}else{
			isViewRotated = false;
		}
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_venue_detail_view, container, false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mVenueImage != null)
			outState.putParcelable(VENUE_IMAGE, mVenueImage);

		if (mCurrentVenue != null)
			outState.putParcelable(CURRENT_VENUE, mCurrentVenue);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public void onStart() {
		super.onStart();	
		if (isViewRotated) {
			updateVenueDetailFromRestore();
		} else  
			updateVenueView();		
	}


	@Override
	public void onResume() {
		super.onResume();
		// set action bar title
		((VenueMainActivity) getActivity()).setActionBarTitle(Constants.ACTIONBAR_DETAILS_TITLE);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.venue_main, menu);
		super.onCreateOptionsMenu(menu, inflater);
		// Get the ActionProvider for later usage
		mShareActionProvider =  (ShareActionProvider)
				MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_share));

		if (mCurrentVenue != null) {
			// Init with the share button false
			menu.findItem(R.id.menu_share).setVisible(true);
			updateShareInfo(mCurrentVenue);
		} else {
			menu.findItem(R.id.menu_share).setVisible(true);
			shareVenue("");	
		}
	}
	
	/**
	 * Sets the current venue selected 
	 */
	public void updateVenue(Venue newVenue) {
		mCurrentVenue = newVenue;
	}
	/**
	 * Checks if a current venue exists and calls the method that updates the UI
	 */
	public void updateVenueView() {
		if (mCurrentVenue != null)
			updateVenueDetail(mCurrentVenue);
	}
	
	/**
	 * Method that updates the UI with the venue information that was saved onSaveInstanceState method
	 */
	public void updateVenueDetailFromRestore() {
		if(mCurrentVenue != null) {
			Venue venueObj = mCurrentVenue;
			TextView mVenueTitleText = (TextView) getActivity().findViewById(R.id.venue_detail_title_text);
			mVenueTitleText.setText(venueObj.getName());

			TextView mVenueAddressText = (TextView) getActivity().findViewById(R.id.venue_detail_address_text);
			mVenueAddressText.setText(venueObj.getAddress());

			ImageView mVenueImageView = (ImageView)getActivity().findViewById(R.id.venue_detail_image);

			// Load image from web only if available if not should show the default "Not Found Image"
			if (!venueObj.getImage_url().isEmpty()) {
				if (mVenueImage != null)
					mVenueImageView.setImageBitmap(mVenueImage);
				else{
					// call the task that will download the Image
					RemoteProvider remoteProvider = RemoteProvider.getInstance();
					RemoteProvider.initRemoteProvider(context);
					remoteProvider.downloadImageWithURL(venueObj.getImage_url());
					// Load a temporary image
					mVenueImageView.setImageDrawable(getResources().getDrawable(R.drawable.downloadingimage));
				}
			}else{
				mVenueImageView.setImageDrawable(getResources().getDrawable(R.drawable.dummyimage));
			}

			String venueScheduleText = "";
			if (venueObj.getSchedule() != null)
				for (ScheduleItem schedule : venueObj.getSchedule()) {
					Date startDate = null;
					Date endDate = null;
					try {
					    startDate = schedule.getStartDateWithFormat();
					    endDate = schedule.getEndDateWithFormat();
					} catch (ParseException e) {
					    startDate = null;
					    endDate = null;
					}
					if (startDate != null && endDate != null) {
						String formatedSchedule = DateConverter.getScheduleDateFormat(startDate,endDate);
						venueScheduleText += formatedSchedule + "\n";
					}
				}

			TextView mVenueScheduleText = (TextView) getActivity().findViewById(R.id.venue_detail_schedule_text);
			mVenueScheduleText.setText(venueScheduleText);

			// update share info in the actionbar
			updateShareInfo(venueObj);
		}
	}

	/**
	 * Method that updates the UI with the selected venue
	 */
	public void updateVenueDetail(Venue venueObj) {
		isViewRotated = false;
		mVenueImage = null;
		TextView mVenueTitleText = (TextView) getActivity().findViewById(R.id.venue_detail_title_text);
		mVenueTitleText.setText(venueObj.getName());

		TextView mVenueAddressText = (TextView) getActivity().findViewById(R.id.venue_detail_address_text);
		mVenueAddressText.setText(venueObj.getAddress());

		ImageView mVenueImage = (ImageView)getActivity().findViewById(R.id.venue_detail_image);

		// Load image from web only if available if not should show the default "Not Found Image"
		if (!venueObj.getImage_url().isEmpty()) {
			RemoteProvider remoteProvider = RemoteProvider.getInstance();
			RemoteProvider.initRemoteProvider(context);
			remoteProvider.downloadImageWithURL(venueObj.getImage_url());
			// Load a temporary image
			mVenueImage.setImageDrawable(getResources().getDrawable(R.drawable.downloadingimage));
		}else{
			mVenueImage.setImageDrawable(getResources().getDrawable(R.drawable.dummyimage));
		}

		String venueScheduleText = "";
		if (venueObj.getSchedule() != null)
			for (ScheduleItem schedule : venueObj.getSchedule()) {
				Date startDate = null;
				Date endDate = null;
				try {
				    startDate = schedule.getStartDateWithFormat();
				    endDate = schedule.getEndDateWithFormat();
				} catch (ParseException e) {
				    startDate = null;
				    endDate = null;
				}
				
				if (startDate != null && endDate != null) {
					String formatedSchedule = DateConverter.getScheduleDateFormat(startDate,endDate);
					venueScheduleText += formatedSchedule + "\n";
				}
			}

		TextView mVenueScheduleText = (TextView) getActivity().findViewById(R.id.venue_detail_schedule_text);
		mVenueScheduleText.setText(venueScheduleText);

		// update share info in the actionbar
		updateShareInfo(venueObj);
	}
	
	/**
	 * Callback method that sets the image if available in the venue information
	 * This method is called from the VenueMainActivity
	 */
	public void updateVenueDetailImage(Bitmap image) {
		if (image != null){ 
			mVenueImage = image;
			ImageView mVenueImage = (ImageView)getActivity().findViewById(R.id.venue_detail_image);
			mVenueImage.setImageBitmap(image);
		}else{
			Message.showErrorMessageWithAlert(context, "Error", "Uppss... there was a problem downloading the image", "Close");
		}
	}
	
	/**
	 * Method that updates the venue information to be share
	 */
	public void updateShareInfo(Venue venueObj) {
		if	(venueObj != null) {
			// setup the sharing
			String infoToShare = "Venue: "+venueObj.getName() + " Address: "+venueObj.getAddress();
			shareVenue(infoToShare);
		}
	}

	/**
	 * Method that initialize the share Intent with the venue information to be share
	 */
	public void shareVenue(String textToShare) {

		// populate the share intent with data
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, textToShare);
		if (mShareActionProvider != null)
			mShareActionProvider.setShareIntent(intent);
	} 
}