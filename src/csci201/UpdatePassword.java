package csci201;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdatePassword
 */
@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* database starts */
		// variables
		boolean isValid = false;
		System.out.println("updatepassword");
		String currUsername = (String) request.getSession().getAttribute("currentusername");
		String currPassword = (String) request.getSession().getAttribute("currentpassword");
		String newPassword = request.getParameter("changepassword");
		String retypePassword = request.getParameter("retypepassword");
		System.out.println("currUsername: " + currUsername + " currPassword: "  + currPassword + " newPassword: " + newPassword + " retypePassword: " + retypePassword);
		List<String> allPasswords = new ArrayList<String>();
		
		if(newPassword.equals(retypePassword)) { // same username
			PrintWriter pw = response.getWriter();
			request.setAttribute("password_err", "Password must be the for both fields");
			pw.println("Password must be the for both fields");
			pw.flush();
			pw.close();
			return;
		}	
		if(newPassword.equals(currUsername)) { // same username
			PrintWriter pw = response.getWriter();
			request.setAttribute("password_err", "Password cannot be the same as the old Password");
			pw.println("Password cannot be the same as the old Password");
			pw.flush();
			pw.close();
			return;
		}
		if (newPassword == null || newPassword.equals("")) { // blank inputs
			PrintWriter pw = response.getWriter();
			request.setAttribute("password_err", "Password cannot be blank");
			pw.println("Password cannot be blank");
			pw.flush();
			pw.close();
			return;
		}
		
		if (newPassword.length() > 15) { // character limit
			PrintWriter pw = response.getWriter();
			request.setAttribute("password_err", "Password can only be up to 15 characters");
			pw.println("Password can only be up to 15 characters");
			pw.flush();
			pw.close();
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			// find userID
			ps2 = conn.prepareStatement("SELECT * FROM User");
			rs2 = ps2.executeQuery();
			while(rs2.next()) {
				System.out.println("adding");
				allPasswords.add(rs2.getString("password"));
			}
			PrintWriter pw = response.getWriter();
			if(allPasswords != null) {
				System.out.println("test1");
				for(int i = 0; i < allPasswords.size();i++) {
					if(allPasswords.get(i).equals(newPassword)){
						System.out.println("test2");
						//usernameOnly = false;		
						pw.println("You've already used this password");
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
			
			// update username
			ps = conn.prepareStatement("UPDATE User SET password = ? WHERE userID =?");
			ps.setString(1, newPassword);
			ps.setInt(2, userID);
			ps.executeUpdate();
			isValid = true;
			ps.close();
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
			}try {
				if (rs2 != null) {
					rs2.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		if(isValid) {
			HttpSession s = request.getSession();
			System.out.println("curru: " + s.getAttribute("currentusername") + " currp: " + s.getAttribute("currentpassword"));	
			s.setAttribute("currentpassword", newPassword);		
		}
		/* database ends */
	}

}
