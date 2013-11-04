package com.grasshoppers.parkfinder.server;

import java.util.List;

import com.grasshoppers.parkfinder.client.ParkSearchService;
import com.grasshoppers.parkfinder.client.modeldata.Park;
import com.grasshoppers.parkfinder.client.modeldata.User;
import com.grasshoppers.parkfinder.model.ParkModel;
import com.grasshoppers.parkfinder.model.UserModel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ParkSearchServiceImpl extends RemoteServiceServlet implements
		ParkSearchService {

	@Override
	public List<Park> findParkServer(String name, String neighbourhood,
			String facility) throws IllegalArgumentException {
			name = retString(name);
			facility = retString(facility);
			neighbourhood = retString(neighbourhood);
				
		
		
		
		  List<Park> parks = new ParkModel().findParks(name, neighbourhood, facility);
		return parks;
	}

	
	public String getPark() {
	//	User user = UserModel.getUser("superman", "kryptonite");
		List<Park> parks = new ParkModel().findParks(null, "ridge","ball");
		
		return parks.get(0).getName();
	}

	
	public String retString (String string) {
		string = string.trim();
		if (string.equals(""))
			return null;
		else return string;
	}
	

}
