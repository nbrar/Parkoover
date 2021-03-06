package com.grasshoppers.parkfinder.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.grasshoppers.parkfinder.client.GUI.Login;
import com.grasshoppers.parkfinder.client.GUI.PreferenceList;
import com.grasshoppers.parkfinder.client.GUI.Results;
import com.grasshoppers.parkfinder.client.GUI.Search;
import com.grasshoppers.parkfinder.client.GUI.Signup;
import com.grasshoppers.parkfinder.client.modeldata.Park;
import com.grasshoppers.parkfinder.client.modeldata.PreferencePark;
import com.grasshoppers.parkfinder.client.modeldata.User;
import com.grasshoppers.parkfinder.client.widget.weather.Weather;
import com.grasshoppers.parkfinder.shared.StringMethods;
public class GUIController extends Composite{

	private VerticalPanel verticalPanel = new VerticalPanel();
	private HorizontalPanel horizontalPanel = new HorizontalPanel();
	private HorizontalPanel statusPanel = new HorizontalPanel();
	private Label facebookLogin = new Label("Not Logged Into Facebook");
	private List<Weather> weathers;
	private ServiceController service;
	//private String html = "http://parky.beastspider.cloudbees.net/";
	//private String appid = "354332208044523";
	private String html = "http://127.0.0.1:8888/ParkFinder.html?gwt.codesvr=127.0.0.1:9997";
	private String appid = "241791142650990";

	private List<String> facList = Arrays.asList("Ball Hockey","Baseball Diamonds",
			"Basketball Courts"
			,"Beaches"
			,"Bowling Greens"
			,"Community Centres"
			,"Community Halls"
			,"Cricket Pitches"
			,"Disc Golf Courses"
			,"Dogs Off-Leash Areas"
			,"Field Hockey"
			,"Field Houses"
			,"Food Concessions"
			,"Football Fields"
			,"Golf Courses"
			,"Jogging Trails"
			,"Lacrosse Boxes"
			,"Lighted Fields"
			,"Outdoor Roller Hockey Rinks"
			,"Picnic Sites"
			,"Playgrounds"
			,"Restaurants"
			,"Rinks"
			,"Rugby Fields"
			,"Running Tracks"
			,"Skateboard Parks"
			,"Soccer Fields"
			,"Softball"
			,"Special Feature"
			,"Swimming Pools"
			,"Tennis Courts"
			,"Ultimate Fields"
			,"Wading Pool"
			,"Washroom"
			,"Water/Spray Parks"
);
	
	private List<String> hoodList = Arrays.asList("Arbutus Ridge"
			,"Downtown"
			,"Dunbar-Southlands"
			,"Fairview"
			,"Grandview-Woodland"
			,"Hastings-Sunrise"
			,"Kensington-Cedar Cottage"
			,"Kerrisdale"
			,"Killarney"
			,"Kitsilano"
			,"Marpole"
			,"Mount Pleasant"
			,"Oakridge"
			,"Renfrew-Collingwood"
			,"Riley-Little Mountain"
			,"Shaughnessy"
			,"South Cambie"
			,"Strathcona"
			,"Sunset"
			,"Victoria-Fraserview"
			,"West End"
			,"West Point Grey");
	private User user = null;
	
	
	
	
	
	public GUIController(ServiceController service) {
		this.service = service;
		
	
	initWidget(verticalPanel);
	
	
	String hash = Window.Location.getHash();
	String token = null;
	if(hash!=null&&hash.length()>10){
	System.out.println(hash);
	int end = hash.lastIndexOf("&");
	token = hash.substring(14, end);
	System.out.println(token);
	}
	
	
	
	
	
	
	final String isToken = token;
	
	String sessionID = Cookies.getCookie("sid");
	if(sessionID==null) {
		goToLogIn();
	} else {
		LoginService.Util.getInstance().getUserFromSession(token, new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				goToLogIn();
			}

			@Override
			public void onSuccess(User result) {
				if (result==null){
					goToLogIn();
				} else {
					setUser(result);
					buttonToSearch();
					if(result.isFacebookLogin()) facebookLogin.setText("Logged Into Facebook.");;
					// Change the following for online
				//	if (isToken!=null){ Window.Location.replace("http://127.0.0.1:8888/ParkFinder.html?gwt.codesvr=127.0.0.1:9997");
					if (isToken!=null){ Window.Location.replace(html);
					}
				}	
			}
		});
	}
	
	
	
	//service.getWeatherData();
		getWeatherSource();
		
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel.setSize("450px", "300px");
		
		statusPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		statusPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		statusPanel.setSize("450px", "30px");
		statusPanel.add(facebookLogin);
	
	
	
	
	
	Button facebookLoginButton = new Button("Facebook Login");
	facebookLoginButton.addClickHandler(new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			goFacebookSignIn();
		}
		
		
	});
	statusPanel.add(facebookLoginButton);
//	}
	verticalPanel.add(statusPanel);
	verticalPanel.add(horizontalPanel);
	
	}
	
//=============================================================================================================
//		GOTOs
//=============================================================================================================
	public void goFacebookSignIn() {
	//	Window.Location.assign("https://www.facebook.com/dialog/oauth?client_id=354332208044523&response_type=token&redirect_uri="+GWT.getModuleBaseURL() + "parseloginservice?");
	//	Window.Location.assign("https://www.facebook.com/dialog/oauth?client_id=354332208044523&redirect_uri="+GWT.getModuleBaseURL() + "parseloginservice?");
		Window.Location.assign("https://www.facebook.com/dialog/oauth?client_id="+appid+"&response_type=token&scope=create_event&redirect_uri="+html);//http://127.0.0.1:8888/ParkFinder.html?gwt.codesvr=127.0.0.1:9997");
	//	Window.Location.assign("https://www.facebook.com/dialog/oauth?client_id=354332208044523&response_type=token&scope=create_event&redirect_uri="+Window.Location.getHref());
		
		//Use the following for local development
	//	Window.Location.assign("https://www.facebook.com/dialog/oauth?client_id=241791142650990&response_type=token&scope=create_event&redirect_uri=http://127.0.0.1:8888/ParkFinder.html?gwt.codesvr=127.0.0.1:9997");
		
	}
	
	
		public void goToSignUp() {
			statusPanel.setVisible(false);
			horizontalPanel.clear();
			Signup signup = new Signup(this);
			horizontalPanel.add(signup);
		}


		public void logout() {
			service.logout();
			goToLogIn();
		}
		
		public void goToLogIn() {
			facebookLogin.setText("Not Logged Into Facebook.");
			statusPanel.setVisible(false);
			service.logout();
			horizontalPanel.clear();
			Login login = new Login(this);
			user = null;
			horizontalPanel.add(login);
		}
		

		public void buttonToPrefList() {
		//	service.getPrefList();
			horizontalPanel.clear();
			if (user!=null) {
				statusPanel.setVisible(true);
				PreferenceList pList = new PreferenceList(this, user.getPreferenceList());
				horizontalPanel.add(pList);
			} else {
				warnPopup("User not logged in.");
				goToLogIn();
			}
		}
		
		public void buttonToSearch() {
		//	service.getSearchAssets();
			goToSearch();
		}
		
		public void goToSavedResult() {
			if (user.getLastSearch().isEmpty())
				warnPopup("Last search result is empty!");
			else
				displayParks(this.user.getLastSearch());
		}
		
//=============================================================================================================
//		Triggers
//=============================================================================================================
		public void makeFacebookEvent(String event, String location,String description, Date starTime, Date endTime) {
			if(user.getAccessToken()!=null)
			service.makeFacebookEvent(user.getAccessToken(), event, location, description, starTime, endTime);
			else { 
				Window.alert("Please Login To Facebook");
				facebookLogin.setText("Not Logged into Facebook");
			}
		}
		
		
		public void buttonDoSignIn(String username, String password, Boolean rememberMe ) {
			service.getUserLogIn(username, password, rememberMe);
		}
		
	/*	//DO NOT TOUCH: service callback
		public void setFacList(List<String> list) {
			this.facList = list;
		}
		
		//DO NOT TOUCH: service callback
		public void setHoodList(List<String> result) {
			this.hoodList = result;
		}
	*/	
		//DO NOT TOUCH: service callback for signIN
		public void signIntoAcc(User user, boolean remembered) {
			if (user == null) {
				Window.alert("Username or password is wrong."); 
			} else {
				//Window.alert("Logged on: "+user.getId()+", "+user.getUser_name()+", with Remember Me: "+remembered);
				System.out.println("Logged on: "+user.getId()+", "+user.getUser_name()+", with Remember Me: "+remembered);
				this.user = user;
				warnPopup("Welcome "+user.getUser_name()+"!");
				goToSearch();
			}
		}
		
		public void buttonDoSignUp(String username, String password, String firstName, String lastName, String address, 
				String country, String city, String province) {
			if (username.length() == 0 || password.length() == 0) {
				Window.alert("Username or password must be filled."); 
			} else {
				service.createNewUser(username, password, firstName, lastName, address, city, province, country);
				goToLogIn();
			}
		}

/*
		public void goToPrefList(List<PreferencePark> prefPark) {
			horizontalPanel.clear();
			PreferenceList pList = new PreferenceList(this, prefPark);
			horizontalPanel.add(pList);
			
		}
		*/
		
		public void doSearch(String park, String neighborhood,String facility) {
			park = StringMethods.retString(park);
			facility = StringMethods.retString(facility);
			neighborhood = StringMethods.retString(neighborhood);
			service.getParkList(park, neighborhood, facility);

		}

		public void displayParks(List<Park> parks) {
			horizontalPanel.clear();
			Results results = new Results(this, parks);
			horizontalPanel.add(results);
			user.setLastSearch(parks);
		}		

		public void sortDisplayParks(List<Park> parks, int sortType) {
			horizontalPanel.clear();
			if (sortType == 0) {
				Collections.sort(parks, Park.ParkNameComparator);
			} else if (sortType == 1) {
				Collections.sort(parks, Park.ParkRatingComparator);
			} else if (sortType == 2) {
				Collections.sort(parks, Park.ParkSizeComparator);
			}
			Results results = new Results(this, parks);
			horizontalPanel.add(results);
			user.setLastSearch(parks);
		}
		
		public void sortPrefParks(List<PreferencePark> parks, int sortType) {
			horizontalPanel.clear();
			if (sortType == 0) {
				Collections.sort(parks, PreferencePark.PrefParkNameComparator);
			} else if (sortType == 1) {
				Collections.sort(parks, PreferencePark.PrefParkRatingComparator);
			} else if (sortType == 2) {
				Collections.sort(parks, PreferencePark.PrefParkSizeComparator);
			}
			PreferenceList results = new PreferenceList(this, parks);
			horizontalPanel.add(results);
			statusPanel.setVisible(true);
		}
		
		public void warnPopup (String warning) {
			Window.alert(warning);
		}

		public void goToSearch() {
			horizontalPanel.clear();
			
			Search search = new Search(this, this.facList, this.hoodList);
			horizontalPanel.add(search);
			statusPanel.setVisible(true);
		}

//=============================================================================================================
//		Info Retrieval
//=============================================================================================================
		
		public void setUser(User user){
			this.user = user;
		}
		
		public User getUser() {
			return user;
		}

		public void createNewParkRating(int userId, int parkId, int rating, String comment, Park park) {
			String time = getCurrentTime();
			
			service.createNewParkRating(userId, parkId, rating, comment, time);
			user.addPreferences(new PreferencePark(park, rating, comment, time));
		}
		
		public void deleteParkRating(int userId, int parkId, PreferencePark park) {
			horizontalPanel.clear();
			service.deleteParkRating(userId, parkId);
			user.getPreferenceList().remove(park);
			
			
		}
		
		public void modifyParkRating(int userId, int parkId, int rating, String comment, PreferencePark park) {
			
			String time = getCurrentTime();
			service.modifyRating(userId, parkId, rating, comment, time, park);

			PreferencePark temp = park;
			user.getPreferenceList().remove(temp);
			temp.setComment(comment);
			temp.setRating(rating);
			temp.setTime(time);
			
			user.addPreferences(temp);
		}
		
		public String getCurrentTime() {
		//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
		//	return dateFormat.format(date);
			return date.toString();
		}

		public List<Weather> getWeather() {
			return weathers;
		}
		
		public void setWeathers(List<Weather> result) {
			this.weathers = result;
			
		}

		public void getWeatherSource() {
			service.getWeatherSource();
		//	return null;
		}
		
		

	}
	
	

