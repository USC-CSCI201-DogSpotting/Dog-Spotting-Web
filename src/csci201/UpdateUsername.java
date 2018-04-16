package csci201;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateUsername
 */
@WebServlet("/UpdateUsername")
public class UpdateUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* database starts */
		// variables
		boolean isValid = false;
		System.out.println("updateusername");
		String currUsername = (String) request.getSession().getAttribute("currentusername");
		String newUsername = request.getParameter("changeusername");
		System.out.println("currUsername: " + currUsername + " newUsername: " + newUsername);
		List<String> allUsernames = new ArrayList<String>();
		
		if (newUsername == null || newUsername.equals("")) { // blank inputs
			PrintWriter pw = response.getWriter();
			request.setAttribute("username_err", "Username cannot be blank");
			pw.println("Username cannot be blank");
			pw.flush();
			pw.close();
			return;
		}
		if(newUsername.equals(currUsername)) { // same username
			PrintWriter pw = response.getWriter();
			request.setAttribute("username_err", "Username cannot be the same as the old Username");
			pw.println("Username cannot be the same as the old Username");
			pw.flush();
			pw.close();
			return;
		}
		if (newUsername.length() > 15) { // character limit
			PrintWriter pw = response.getWriter();
			request.setAttribute("username_err", "Username can only be up to 15 characters");
			pw.println("Username can only be up to 15 characters");
			pw.flush();
			pw.close();
			return;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			// find userID
			ps2 = conn.prepareStatement("SELECT * FROM User");
			rs2 = ps2.executeQuery();
			while(rs2.next()) {
				System.out.println("adding");
				allUsernames.add(rs2.getString("username"));
			}
			PrintWriter pw=  response.getWriter();
			if(allUsernames != null) {
				System.out.println("test1");
				for(int i = 0; i < allUsernames.size();i++) {
					if(allUsernames.get(i).equals(newUsername)){
						System.out.println("test2");
						//usernameOnly = false;		
						pw.println("Sorry, this username is taken");
						pw.flush();
						return;
						}
					}
					
				}
			ps = conn.prepareStatement("SELECT userID FROM User WHERE username=?");
			ps.setString(1, currUsername);
			rs = ps.executeQuery();
			int userID = 0;
			while (rs.next()) { // get userID
				userID = rs.getInt("userID");
			}
			ps.close();
			rs.close();
			
			System.out.println("UserID: " + userID);
			
			// update username
			ps4 = conn.prepareStatement("UPDATE User SET username = ? WHERE userID =?");
			System.out.println("newUsername");
			ps4.setString(1, newUsername);
			System.out.println("userID");
			ps4.setInt(2, userID);
			System.out.println("execute");
			ps4.executeUpdate();
			isValid = true;
			ps4.close();
			
			/*ps3 = conn.prepareStatement("UPDATE Post SET username = ? WHERE userID =?");
			System.out.println("newUsername");
			ps3.setString(1, newUsername);
			System.out.println("userID");
			ps3.setInt(2, userID);
			System.out.println("execute");
			ps3.executeUpdate();
			ps3.close();*/
			//rs3.close();
			//isValid = true;
			//ps.close();
			//ps.close();
			// add to the newUsername to all the posts in the database
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}	try {
				if (rs2 != null) {
					rs2.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}try {
				if (rs3 != null) {
					rs3.close();
				}
				if (ps3 != null) {
					ps3.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		
		/* database ends */
		if(isValid) {
			HttpSession s = request.getSession();
			System.out.println("curru: " + s.getAttribute("currentusername") + " currp: " + s.getAttribute("currentpassword"));
			s.setAttribute("currentusername", newUsername);
			request.setAttribute("username", newUsername);
		}
	}
}
