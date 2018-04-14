package csci201;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class RankUpdate {
	public static void main(String [] args) {
		new RankUpdate();
	}
	
	public RankUpdate() {
		// check date
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int currDate = year * 10000 + month * 100 + day;
		System.out.println("Curr Date: " + currDate);
		
		/* check database */
		String[] rankOption = {"dailyrenew", "monthlyrenew", "yearlyrenew"};
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		try {
			for(int i = 0; i < 3; i++) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
				ps = conn.prepareStatement("SELECT infonum FROM GeneralInfo WHERE infoname = ?");
				ps.setString(1, rankOption[i]);
				rs = ps.executeQuery();
				while(rs.next()) {
					int renewDate = rs.getInt("infonum");
					System.out.println("Renew Date: " + renewDate);
					if((i == 0 && renewDate < currDate) || // daily
							(i == 1 && renewDate / 100 < currDate / 100) || // monthly
							(i == 2 && renewDate / 10000 < currDate / 10000)){ // yearly
						renewRank(conn, i);
						ps2 = conn.prepareStatement(
								"UPDATE GeneralInfo " +
								"SET infonum = ? " +
								"WHERE infoname = ?");
						ps2.setInt(1, currDate);
						ps2.setString(2, rankOption[i]);
						ps2.executeUpdate();
					}
				}
			}
		} catch(SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
		} catch(ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException sqle) {
				System.out.println("SQLException in closing: " + sqle.getMessage());
			}
		}
		System.out.println("Updated Ranks");
	}
	
	private void renewRank(Connection conn, int tableNum) {
		// determine table name
		String tableName = "";
		String postField = "";
		if(tableNum == 0) {
			tableName = "DailyRank";
			postField = "dailylike";
		}else if(tableNum == 1) {
			tableName = "MonthlyRank";
			postField = "monthlylike";
		}else {
			tableName = "YearlyRank";
			postField = "yearlylike";
		}
		System.out.println("Updating " + tableName);
		// update table
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		try {
			// getting the new rank
			ps = conn.prepareStatement("SELECT postID FROM Post ORDER BY ? DESC LIMIT 100");
			ps.setString(1, postField);
			rs = ps.executeQuery();
			// updating existing
			// get current number
			String query = "SELECT COUNT(*) FROM " + tableName;
			ps2 = conn.prepareStatement(query);
			rs2 = ps2.executeQuery();
			int currRankNum = 0;
			while(rs2.next()) {
				currRankNum = rs2.getInt(1);
				System.out.println(currRankNum);
			}
			ps2.close();
			// update existing
			query = "UPDATE " + tableName + " SET postID = ? WHERE rankID = ?";
			ps2 = conn.prepareStatement(query);
			for(int i = 1; i <= currRankNum; i++) {
				rs.next();
				int tempPostID = rs.getInt(1);
				ps2.setInt(1, tempPostID);
				ps2.setInt(2, i);
				ps2.executeUpdate();
			}
			ps2.close();
			// insert if more than existing rows
			query = "INSERT INTO " + tableName + " (postID) VALUES (?)";
			ps2 = conn.prepareStatement(query);
			while(rs.next()) {
				System.out.println("here");
				int tempPostID = rs.getInt(1);
				System.out.println(tempPostID);
				ps2.setInt(1, tempPostID);
				ps2.executeUpdate();
			}
			ps2.close();
			ps.close();
			// zero the post table
			query = "UPDATE Post SET " + postField + " = 0";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("SQLException in renewRank: " + sqle.getMessage());
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
				if(rs != null) {
					rs.close();
				}
				if(ps2 != null) {
					ps2.close();
				}
				if(rs2 != null) {
					rs2.close();
				}
			} catch(SQLException sqle) {
				System.out.println("SQLException in closing in renewRank: " + sqle.getMessage());
			}
		}
	}
}
