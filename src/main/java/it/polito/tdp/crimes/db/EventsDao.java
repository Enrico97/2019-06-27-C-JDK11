package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> listaReati() {
		String sql = "SELECT distinct offense_category_id FROM events ORDER BY offense_category_id" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_category_id"));
					
				} catch (Throwable t) {
					t.printStackTrace();
					
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listaGiorni() {
		String sql = "SELECT distinct DAY(reported_date) FROM events ORDER BY DAY(reported_date)" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("DAY(reported_date)"));
					
				} catch (Throwable t) {
					t.printStackTrace();
					
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> vertici(String categoria, int giorno) {
		String sql = "SELECT distinct offense_type_id FROM events WHERE offense_category_id=? and DAY(reported_date)=? ORDER BY offense_type_id";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setInt(2, giorno);
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_type_id"));
					
				} catch (Throwable t) {
					t.printStackTrace();
					
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Adiacenza> archi(String categoria, int giorno) {
		String sql = "SELECT distinct e1.offense_type_id as a1, e2.offense_type_id as a2, count(distinct e1.precinct_id) as cnt FROM events e1, events e2 WHERE e1.offense_category_id=? and e2.offense_category_id=? and DAY(e1.reported_date)=? and DAY(e2.reported_date)=? and e1.offense_type_id>e2.offense_type_id GROUP BY e1.offense_type_id, e2.offense_type_id";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setString(2, categoria);
			st.setInt(3, giorno);
			st.setInt(4, giorno);
			
			List<Adiacenza> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Adiacenza(res.getString("a1"), res.getString("a2"), res.getInt("cnt")));
					
				} catch (Throwable t) {
					t.printStackTrace();
					
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}
