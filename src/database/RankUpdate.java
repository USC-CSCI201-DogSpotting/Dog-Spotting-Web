package database;

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
	
	private void renewRank(Connection conn, int periodNum) {
		// determine table name
		String[] rankOption = {"dailylike", "monthlylike", "yearlylike"};
		System.out.println("Updating " + rankOption[periodNum]);
		// update post table
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// getting the new rank
			ps = conn.prepareStatement("UPDATE Post SET " + rankOption[periodNum] + " = 0");
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
			} catch(SQLException sqle) {
				System.out.println("SQLException in closing in renewRank: " + sqle.getMessage());
			}
		}
	}
}
