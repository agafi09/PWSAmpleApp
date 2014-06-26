package com.applaudo.phunwaresampleapp.dataaccesslayer;

import java.util.Collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.applaudo.phunwaresampleapp.datalayer.Parser;
import com.applaudo.phunwaresampleapp.entitylayer.ServiceActionResult;
import com.applaudo.phunwaresampleapp.entitylayer.ServiceActionResult.ServiceActionResultCode;
import com.applaudo.phunwaresampleapp.entitylayer.Venue;

/**
 * Singleton class that downloads all the content and images from the amazon S3 bucket
 */
public class RemoteProvider {
	 
	private static final String URL = "https://s3.amazonaws.com/jon-hancock-phunware/nflapi-static.json";
	
	private static Context mContext;
	private static RemoteProvider mInstance;
	
	private ServiceActionResult mSarObject;  
	
	/**
	 * Interface that declares the callback methods that will notify the activity and fragments that implements it.	
	 */
	private static OnContentDownloadCompletedListener mCallback;
	public interface OnContentDownloadCompletedListener{
		public void onContentDownloadCompletedListener(ServiceActionResult result);
		public void onImageDownloadCompletedListener(Bitmap result);
	}
	

	/**
	 * Returns a new instance or and old one if was created previously
	 */
	public static RemoteProvider getInstance(){
        if (mInstance == null){
        	mInstance = new RemoteProvider();
        }
        return mInstance;
    }
	
	/**
	 * Method that initialize the singleton class 	
	 */
	public static void initRemoteProvider (Context con){
		mContext = con;
		mCallback = (OnContentDownloadCompletedListener) mContext;
	}
	
	/**
	 * Method that downloads the JSON content from the amazon S3 bucket
	 */
	public void downloadVenuesList(){
	         
		// Create a single queue
        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest getRequest = new StringRequest(URL,
            new Response.Listener<String>() 
            {
                @Override
                public void onResponse(String response) {        
                    mSarObject = new ServiceActionResult();
            		
            		Collection<Venue> loObject = Parser.parseVenueResponseWithGson(response);
            		
            		if (loObject != null) {
            			mSarObject.setResult(loObject);
            			mSarObject.setMessage("Success");
            			mSarObject.setReturnCode(ServiceActionResultCode.SERVICE_CODE_SUCCESS);
            		} else {
            			mSarObject.setResult(null);
            			mSarObject.setMessage("Failed");
            			mSarObject.setReturnCode(ServiceActionResultCode.SERVICE_CODE_FAILURE);
            		}
            		
            		mCallback.onContentDownloadCompletedListener(mSarObject);
            		
                }
            }, 
            new Response.ErrorListener() 
            {
                 @Override
                 public void onErrorResponse(VolleyError error) {            
                    mSarObject = new ServiceActionResult();
                    mSarObject.setResult(null);
        			mSarObject.setMessage(error.toString());
        			mSarObject.setReturnCode(ServiceActionResultCode.SERVICE_CODE_FAILURE);
        			mCallback.onContentDownloadCompletedListener(mSarObject);
               }
            }
        );
 
        queue.add(getRequest);
	}
	
	/**
	 * Method that downloads the image for a given URL
	 */
	public void downloadImageWithURL(String imageUrl){
        
		// Create a single queue
        RequestQueue queue = Volley.newRequestQueue(mContext);
        
        ImageRequest getRequest = new ImageRequest(imageUrl,
            new Response.Listener<Bitmap>() 
            {
                @Override
                public void onResponse(Bitmap response) {        
                	mCallback.onImageDownloadCompletedListener(response);
                }
            },
            0,0,null, 
            new Response.ErrorListener() 
            {
                 @Override
                 public void onErrorResponse(VolleyError error) {            
                    Log.d("Error.Response", error.toString());
                    mCallback.onImageDownloadCompletedListener(null);
               }
            }
        );
 
        queue.add(getRequest);
	}
}
