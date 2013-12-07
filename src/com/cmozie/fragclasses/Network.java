/*
 * project 			onTap
 * 
 * package			com.cmozie.fragclasses
 * 
 * name				cameronmozie
 * 
 * date				Dec 6, 2013
 */
package com.cmozie.fragclasses;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class Network.
 */
public class Network {
	
	static Boolean connection = false;
	static String connectionType = "NotReady";
	
	/**
	 * Gets the connection type.
	 *
	 * @param context the context
	 * @return the connection type
	 */
	public static String getConnectionType(Context context){
		networkData(context);
		return connectionType;
		
	}
	
	/**
	 * Gets the connection status.
	 *
	 * @param context the context
	 * @return the connection status
	 */
	public static Boolean getConnectionStatus (Context context){
		networkData(context);
		return connection;
		
	}

/**
 * Network data.
 *
 * @param context the context
 */
private static void networkData(Context context){
	ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo info = connectManager.getActiveNetworkInfo();
	 
	if (info != null) {
		if (info.isConnected()) {
			connectionType = info.getTypeName();
			connection = true;
		}
	}
}

/**
 * URL string response.
 *
 * @param url the url
 * @return the string
 */
public static String URLStringResponse(URL url){
	String response = "";
	
	try {
		URLConnection urlConnect = url.openConnection();
		BufferedInputStream bin = new BufferedInputStream(urlConnect.getInputStream());
		
		byte[] bytes = new byte[1024];
		
		int readBytes = 0;
		
		StringBuffer bufferedResponse = new StringBuffer();
		
		while((readBytes = bin.read(bytes)) != -1){
			response = new String(bytes,0,readBytes);
			bufferedResponse.append(response);
			
		}
		return bufferedResponse.toString();
	
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	
	return response;
	
}
}
