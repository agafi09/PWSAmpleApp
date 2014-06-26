package com.applaudo.phunwaresampleapp.common;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class Message {
	
	/**
	 * Method that presents a Toast with a custom message
	 */
	public static void message(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Method that presents a Toast with a custom title, message and negative button
	 */
	public static void showErrorMessageWithAlert(Context context, String messageTitle,String messageDescription, String closeButtonTitle){
		new AlertDialog.Builder(context)
		.setTitle(messageTitle)
		.setMessage(messageDescription)
		.setNegativeButton(closeButtonTitle,null)
		.show();
	}
}
