package com.grasshoppers.parkfinder.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.grasshoppers.parkfinder.client.modeldata.Facility;
import com.grasshoppers.parkfinder.client.modeldata.Park;

import sun.font.CreatedFontTracker;

public class ParkModel extends DBManager {

	public static final String DBNAME = "Park";
	public static final String TABLE_FACILITY = "Facility";
	public static final String TABLE_NEIGHBORHOOD = "Neighborhood";
	public static final String TABLE_HAS_FACILITY = "Has_Facility";
	public static final String TABLE_IN_NEIGHBORHOOD = "In_Neighborhood";	
	
	
	public List<Park> findParks(String park, String neighbourhood, String facility) {
		Connection con = getConnection();
		String query;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Park> parks = new ArrayList<Park>();
		try {
			if (park!=null&&neighbourhood==null&&facility==null) {
				query = "SELECT p.id AS Park_Id, p.name, p.official, p.street_number,p.street_name,p.ew_street,p.ns_street,"
						+ "p.map_x_loc,p.map_y_loc,p.hectares,f.id AS Facility_Id, f.type,f.feature,f.location,f.note,f.summer_hours,"
						+ "f.winter_hours,h.count,n.id AS Neighborhood_Id, n.name AS Neighborhood_Name, n.url"
						+ " FROM "+DBNAME+" p,"+TABLE_FACILITY+" f,"+TABLE_HAS_FACILITY+" h,"
						+TABLE_NEIGHBORHOOD+" n, "+TABLE_IN_NEIGHBORHOOD+" i"
						+ " WHERE p.id=i.park_id AND n.id=i.neighbourhood_id AND f.id=h.facility_id AND p.id=h.park_id"
						+ " AND p.name LIKE ?"
						+ " ORDER BY p.name";
				ps = con.prepareStatement(query);
				ps.setString(1, "%"+park+"%");
			} else if (park==null&&neighbourhood!=null&&facility==null) {
				query = "SELECT p.id AS Park_Id, p.name, p.official, p.street_number,p.street_name,p.ew_street,p.ns_street,"
						+ "p.map_x_loc,p.map_y_loc,p.hectares,f.id AS Facility_Id, f.type,f.feature,f.location,f.note,f.summer_hours,"
						+ "f.winter_hours,h.count,n.id AS Neighborhood_Id, n.name AS Neighborhood_Name, n.url"
						+ " FROM "+DBNAME+" p,"+TABLE_FACILITY+" f,"+TABLE_HAS_FACILITY+" h,"
						+TABLE_NEIGHBORHOOD+" n, "+TABLE_IN_NEIGHBORHOOD+" i"
						+ " WHERE p.id=i.park_id AND n.id=i.neighbourhood_id AND f.id=h.facility_id AND p.id=h.park_id"
						+ " AND n.name LIKE ?"
						+ " ORDER BY p.name";
				ps = con.prepareStatement(query);
				ps.setString(1, "%"+neighbourhood+"%");
			} else if (park==null&&neighbourhood==null&&facility!=null){
				query = "SELECT p.id AS Park_Id, p.name, p.official, p.street_number,p.street_name,p.ew_street,p.ns_street,"
						+ "p.map_x_loc,p.map_y_loc,p.hectares,f.id AS Facility_Id, f.type,f.feature,f.location,f.note,f.summer_hours,"
						+ "f.winter_hours,h.count,n.id AS Neighborhood_Id, n.name AS Neighborhood_Name, n.url"
						+ " FROM "+DBNAME+" p,"+TABLE_FACILITY+" f,"+TABLE_HAS_FACILITY+" h,"
						+TABLE_NEIGHBORHOOD+" n, "+TABLE_IN_NEIGHBORHOOD+" i"
						+ " WHERE p.id=i.park_id AND n.id=i.neighbourhood_id AND f.id=h.facility_id AND p.id=h.park_id"
						+ " AND p.id IN (SELECT p.id FROM park p,facility f, has_facility h WHERE f.id=h.facility_id AND p.id=h.park_id AND f.type LIKE ?)"
						+ " ORDER BY p.name";
				ps = con.prepareStatement(query);
				ps.setString(1, "%"+facility+"%");
			} else if (park!=null&&neighbourhood!=null&&facility==null){
				query = "SELECT p.id AS Park_Id, p.name, p.official, p.street_number,p.street_name,p.ew_street,p.ns_street,"
						+ "p.map_x_loc,p.map_y_loc,p.hectares,f.id AS Facility_Id, f.type,f.feature,f.location,f.note,f.summer_hours,"
						+ "f.winter_hours,h.count,n.id AS Neighborhood_Id, n.name AS Neighborhood_Name, n.url"
						+ " FROM "+DBNAME+" p,"+TABLE_FACILITY+" f,"+TABLE_HAS_FACILITY+" h,"
						+TABLE_NEIGHBORHOOD+" n, "+TABLE_IN_NEIGHBORHOOD+" i"
						+ " WHERE p.id=i.park_id AND n.id=i.neighbourhood_id AND f.id=h.facility_id AND p.id=h.park_id"
						+ " AND p.name LIKE ? AND n.name LIKE ?"
						+ " ORDER BY p.name";
				ps = con.prepareStatement(query);
				ps.setString(1, "%"+park+"%");
				ps.setString(2, "%"+neighbourhood+"%");
			} else if (park!=null&&neighbourhood==null&&facility!=null){
				query = "SELECT p.id AS Park_Id, p.name, p.official, p.street_number,p.street_name,p.ew_street,p.ns_street,"
						+ "p.map_x_loc,p.map_y_loc,p.hectares,f.id AS Facility_Id, f.type,f.feature,f.location,f.note,f.summer_hours,"
						+ "f.winter_hours,h.count,n.id AS Neighborhood_Id, n.name AS Neighborhood_Name, n.url"
						+ " FROM "+DBNAME+" p,"+TABLE_FACILITY+" f,"+TABLE_HAS_FACILITY+" h,"
						+TABLE_NEIGHBORHOOD+" n, "+TABLE_IN_NEIGHBORHOOD+" i"
						+ " WHERE p.id=i.park_id AND n.id=i.neighbourhood_id AND f.id=h.facility_id AND p.id=h.park_id"
						+ " AND p.name LIKE ?"
						+ " AND p.id IN (SELECT p.id FROM park p,facility f, has_facility h WHERE f.id=h.facility_id AND p.id=h.park_id AND f.type LIKE ?)"
						+ " ORDER BY p.name";
				ps = con.prepareStatement(query);
				ps.setString(1, "%"+park+"%");
				ps.setString(2, "%"+facility+"%");
			} else if (park==null&&neighbourhood!=null&&facility!=null){
				query = "SELECT p.id AS Park_Id, p.name, p.official, p.street_number,p.street_name,p.ew_street,p.ns_street,"
						+ "p.map_x_loc,p.map_y_loc,p.hectares,f.id AS Facility_Id, f.type,f.feature,f.location,f.note,f.summer_hours,"
						+ "f.winter_hours,h.count,n.id AS Neighborhood_Id, n.name AS Neighborhood_Name, n.url"
						+ " FROM "+DBNAME+" p,"+TABLE_FACILITY+" f,"+TABLE_HAS_FACILITY+" h,"
						+TABLE_NEIGHBORHOOD+" n, "+TABLE_IN_NEIGHBORHOOD+" i"
						+ " WHERE p.id=i.park_id AND n.id=i.neighbourhood_id AND f.id=h.facility_id AND p.id=h.park_id"
						+ " AND n.name LIKE ?"
						+ " AND p.id IN (SELECT p.id FROM park p,facility f, has_facility h WHERE f.id=h.facility_id AND p.id=h.park_id AND f.type LIKE ?)"
						+ " ORDER BY p.name";
				ps = con.prepareStatement(query);
				ps.setString(1, "%"+neighbourhood+"%");
				ps.setString(2, "%"+facility+"%");
			} else if (park!=null&&neighbourhood!=null&&facility!=null){
				query = "SELECT p.id AS Park_Id, p.name, p.official, p.street_number,p.street_name,p.ew_street,p.ns_street,"
						+ "p.map_x_loc,p.map_y_loc,p.hectares,f.id AS Facility_Id, f.type,f.feature,f.location,f.note,f.summer_hours,"
						+ "f.winter_hours,h.count,n.id AS Neighborhood_Id, n.name AS Neighborhood_Name, n.url"
						+ " FROM "+DBNAME+" p,"+TABLE_FACILITY+" f,"+TABLE_HAS_FACILITY+" h,"
						+TABLE_NEIGHBORHOOD+" n, "+TABLE_IN_NEIGHBORHOOD+" i"
						+ " WHERE p.id=i.park_id AND n.id=i.neighbourhood_id AND f.id=h.facility_id AND p.id=h.park_id"
						+ " AND n.name LIKE ?"
						+ " AND p.id IN (SELECT p.id FROM park p,facility f, has_facility h WHERE f.id=h.facility_id AND p.id=h.park_id AND f.type LIKE ?)"
						+ " AND p.name LIKE ?"
						+ " ORDER BY p.name";
				ps = con.prepareStatement(query);
				ps.setString(1, "%"+neighbourhood+"%");
				ps.setString(2, "%"+facility+"%");
				ps.setString(3, "%"+park+"%");
			} else if (park==null&&neighbourhood==null&&facility==null){
				query = "SELECT p.id AS Park_Id, p.name, p.official, p.street_number,p.street_name,p.ew_street,p.ns_street,"
						+ "p.map_x_loc,p.map_y_loc,p.hectares,f.id AS Facility_Id, f.type,f.feature,f.location,f.note,f.summer_hours,"
						+ "f.winter_hours,h.count,n.id AS Neighborhood_Id, n.name AS Neighborhood_Name, n.url"
						+ " FROM "+DBNAME+" p,"+TABLE_FACILITY+" f,"+TABLE_HAS_FACILITY+" h,"
						+TABLE_NEIGHBORHOOD+" n, "+TABLE_IN_NEIGHBORHOOD+" i"
						+ " WHERE p.id=i.park_id AND n.id=i.neighbourhood_id AND f.id=h.facility_id AND p.id=h.park_id"
						+ " ORDER BY p.name";
				ps = con.prepareStatement(query);
			} 
			
			rs = ps.executeQuery();
			
			List<Integer> ids = new ArrayList<Integer>();
			Park parkToAdd = new Park();
			while(rs.next()){
				int id = rs.getInt("Park_Id");
				if (ids.isEmpty()) {
					parkToAdd = ModelFactory.makePark(rs);
					parkToAdd.addFacility(ModelFactory.makeFacility(rs));
					
					ids.add(id);
				} else if (!ids.contains(id)){
					parks.add(parkToAdd);
					parkToAdd = ModelFactory.makePark(rs);
					parkToAdd.addFacility(ModelFactory.makeFacility(rs));
					
					ids.add(id);

				} else {
					// TODO Check to see if parkToAdd is null or not
					parkToAdd.addFacility(ModelFactory.makeFacility(rs));
					//parkToAdd.addFacility(ModelFactory.makeFacility(rs));
				}

			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			if (con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return parks;
		
	}

	/**
	 * 
	 * @param name
	 * @param url 
	 * @return NeighborhoodId
	 * (name, url) MUST BE UNIQUE
	 * 
	 */
	public static int getNeighborhoodId(String name, String url) {
		Connection conn = getConnection();
		try {

			Statement stmt = conn.createStatement();
			String query = "SELECT id FROM " + 
							TABLE_NEIGHBORHOOD + 
							" WHERE name = '" + name + "'" +
							" AND url = '" + url + "';";
			
			ResultSet rs = stmt.executeQuery(query);
			rs.first();
			int returnId = rs.getInt("id");
			System.out.println("Hood ID is: " + returnId);
			conn.close();
			return returnId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<String> getAllFacilityTypes() {
		Connection conn = getConnection();
		
		try {
			List<String> typeList = new ArrayList<String>();
			Statement stmt = conn.createStatement();
			String query = "SELECT DISTINCT type FROM " + 
								TABLE_FACILITY;
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				typeList.add(rs.getString("type"));
				//System.out.println("Type added: " + rs.getString("type"));
			}
			conn.close();
			return typeList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getAllNeighborhoodNames() {
		Connection conn = getConnection();
		
		try {
			List<String> nameList = new ArrayList<String>();
			Statement stmt = conn.createStatement();
			String query = "SELECT DISTINCT name FROM " + 
					TABLE_NEIGHBORHOOD;
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				nameList.add(rs.getString("name"));
			}
			return nameList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param type
	 * @param count
	 * @param location 
	 * @return FacilityId
	 * (type, feature, location) MUST BE UNIQUE
	 * 
	 */
	public static int getFacilityId(String type, String feature, String location) {
		Connection conn = getConnection();
		try {

			Statement stmt = conn.createStatement();
			String query = "SELECT id FROM " + 
							TABLE_FACILITY + 
							" WHERE type = '" + type + "'" +
							" AND feature = '" + feature + "'" +
							" AND location = '" + location + "';";
			
			ResultSet rs = stmt.executeQuery(query);
			rs.first();
			int returnId = rs.getInt("id");
			System.out.println("Facility ID is: " + returnId);
			conn.close();
			return returnId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] args){
		
			System.out.println(new ParkModel().findParks(null, "ridge","ball").get(0).getName());
			System.out.println(new ParkModel().findParks(null, "ridge","ball").get(0).getName());
			System.out.println(new ParkModel().findParks(null, "ridge","ball").get(0).getName());
			System.out.println(new ParkModel().findParks(null, "ridge","ball").get(0).getName());
			System.out.println(new ParkModel().findParks(null, "ridge","ball").get(0).getName());
		
	}

}
