package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<String> getTipiPorzione(Double calories) {
		String sql = "select distinct(portion_display_name) as nome " + 
				"from portion " + 
				"where calories < ?";
		
		List<String> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, calories);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("nome"));
			}
			
			conn.close();
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Adiacenza> getTipiEPeso(Double calories) {
		String sql = "select p1.portion_display_name as t1, p2.portion_display_name as t2, " + 
				"count(distinct(p1.food_code)) as peso " + 
				"from portion p1, portion p2 " + 
				"where p1.food_code = p2.food_code " +  
				"and p1.portion_display_name <> p2.portion_display_name " + 
				"and p1.calories < ? " + 
				"and p2.calories < ? " + 
				"group by p1.portion_display_name, p2.portion_display_name ";
		
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, calories);
			st.setDouble(2, calories);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(new Adiacenza(rs.getString("t1"), 
										rs.getString("t2"), 
										rs.getInt("peso")));
			}
			
			conn.close();
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/*
	 * METODO CON VARIE CHIAMATE AL DAO
select distinct(p1.portion_id)
from portion p1, portion p2
where p1.food_code = p1.food_code
and p1.portion_id < p2.portion_id
and p1.portion_display_name = "Cup"
and p2.portion_display_name = "Sandwich"

	 */

}
