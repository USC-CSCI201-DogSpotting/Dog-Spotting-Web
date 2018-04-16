package csci201;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Settings
 */
@WebServlet("/Settings")
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;    
   
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub'
		//HttpSession session = request.getSession();
		System.out.println("-----------------settingserv");
		String currUsername = (String) request.getSession().getAttribute("currentusername");
		String currPassword = (String) request.getSession().getAttribute("currentpassword");
		String newUsername = request.getParameter("changeusername");
		String newPassword = (String) request.getParameter("changepassword");
		String rePassword = (String) request.getParameter("repassword");
		String changeImg = (String) request.getParameter("changeimage");
		List<String> allUsernames = new ArrayList<String>();
		List<String> allPasswords = new ArrayList<String>();
		
		System.out.println("username: " + currUsername + " password: "  + currPassword);
		System.out.println("username1: " + newUsername + " password1: "  + newPassword);
		System.out.println("retype: " + rePassword + " changeimage: " + changeImg);
		boolean isValid = false;

		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement st2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Statement st3 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		int userID = 0;
		
		if (newUsername == null || newUsername.equals("") || newPassword == null || newPassword.equals("")) {
			PrintWriter pw = response.getWriter();
			request.setAttribute("change_err", "Username and/ or password cannot be blank");
			pw.println("Username and/ or password cannot be blank");
			pw.flush();
			pw.close();
			return;
		}
		if(newUsername.equals(currUsername) || newPassword.equals(currPassword)) {
			PrintWriter pw = response.getWriter();
			request.setAttribute("change_err", "Username and/ or password cannot be the same as the old Username and/ or password");
			pw.println("Username and/ or password cannot be the same as the old Username and/ or password");
			if(!(newPassword.equals(rePassword))) { // for non same retype of password
				pw.println("Passwords do not match");
			}
			pw.flush();
			pw.close();
			return;
		}
		if(!(newPassword.equals(rePassword))) { // for non same retype of password
			PrintWriter pw = response.getWriter();
			request.setAttribute("change_err", "Passwords do not match");
			pw.println("Passwords do not match");
			pw.flush();
			pw.close();
			return;
		}
		if (newUsername.length() > 15 || newPassword.length() > 15) {
			PrintWriter pw = response.getWriter();
			request.setAttribute("change_err", "Username and/ or password can only be up to 15 characters");
			pw.println("Username and/ or password can only be up to 15 characters");
			pw.flush();
			pw.close();
			return;
		}
		try {
			System.out.println("Starting Check");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
			ps.setString(1, currUsername); // set first variable in prepared statement
			rs = ps.executeQuery();
			System.out.println("rs start");
			// check if user exists and check password
			while (rs.next()) {
				System.out.println("rs next");
				userID = rs.getInt("userID");
				String dataPassword = rs.getString("password");
				System.out.println(dataPassword);
				if (!(dataPassword.equals(newPassword))) {
					System.out.println(newPassword);
					System.out.println("password correct");
					isValid = true;
				}
			}
		
			st2 = conn.createStatement();
			ps2 = conn.prepareStatement("SELECT * FROM User");
			rs2 = ps2.executeQuery();
			while(rs2.next()) {
				System.out.println("adding");
				allUsernames.add(rs2.getString("username"));
				allPasswords.add(rs2.getString("password"));
			}
			if (isValid) {	
					System.out.println("adding to the same userID in the database");
					ps3 = conn.prepareStatement("INSERT INTO User (username, password, picture) VALUES (?, ?, ?)");
					System.out.println("newUsername: " + newUsername + " newPassword: " +newPassword + " changeImg: " +  changeImg);
					ps3.setString(1, newUsername);
					ps3.setString(2, newPassword);
					ps3.setString(3, changeImg);
					ps3.executeUpdate();
			}
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			System.out.println("finally");
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
			try {
				if (rs2 != null) {
					rs2.close();
				}
				if (st2 != null) {
					st2.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		/* database ends */

		/* output boolean isValid */
		/* output int userID if valid */
		System.out.println("isValid: " + isValid);
		System.out.println(userID+ " : "+ newPassword + " : " + newUsername);

		// PROCESSING VALID BOOLEAN AND FORWARD TO APPROPRIATE PAGE
		PrintWriter pw = response.getWriter();
		boolean usernameOnly = true;
		
		if(allUsernames != null && allPasswords != null) {
			System.out.println("test1");
			for(int i = 0; i < allUsernames.size();i++) {
				if(allUsernames.get(i).equals(newUsername)){
					System.out.println("test2");
					usernameOnly = false;
					if(allPasswords.get(i).equals(newPassword)){
						System.out.println("test3");
						pw.println("Sorry, this user already exists");
						pw.flush();
						return;
					}
				}
				else if(usernameOnly == false) {
					System.out.println("test4");
					pw.println("Sorry, this username already exists");
					pw.flush();
					return;
				}
			}
		}
		else {
			// do nothing <- means empty database
		}
		if (isValid) {
			System.out.println("inside settings changes successful");
			HttpSession s = request.getSession();
			System.out.println("curru: " + s.getAttribute("currentusername") + " currp: " + s.getAttribute("currentpassword"));
			//s.setAttribute("currentusername", newUsername);
			//s.setAttribute("currentpassword", newPassword);		
		} else {
			System.out.println("inside settings failed");
			request.setAttribute("change_err", "Please enter a valid username and password");
			pw.println("Please enter a valid username and password");
			pw.flush();
		}
		pw.close();
		// Todo List:
		// buttons for list of followers and following isn't working 
		// Logout isn't working, just redirects and doesn't actually log out
		// start the otherprofile.jsp; same functionality as userprofile <- except it has unfollow/ follow instead of settings 
		// *add functionality of nav bar and all necesssary things to the program * if all main things done
	}

}
