package com.grasshoppers.parkfinder.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.grasshoppers.parkfinder.client.modeldata.Park;
import com.grasshoppers.parkfinder.client.modeldata.PreferencePark;
import com.grasshoppers.parkfinder.client.modeldata.User;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ParkSearchServiceAsync {
	void findParkServer(String name, String neighbourhood, String facility, AsyncCallback<List<Park>> callback)	throws IllegalArgumentException;
	void getPark(AsyncCallback<String> callback);
	void getUser(String username, String password, AsyncCallback<User> callback);
	void getAllFacTypes(AsyncCallback<List<String>> callback);
	void getAllHoodNames(AsyncCallback<List<String>> callback);
	void findPrefParks(int UID, AsyncCallback<List<PreferencePark>> callback);
	void createNewUser(String name, String password, String firstName, String lastName, String address, String city, String province, String country, AsyncCallback<Boolean> callback);
	void removeParkRating(int uid, int pid, AsyncCallback<String> callback);
}
