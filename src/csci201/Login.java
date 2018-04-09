package csci201;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/* database starts */
		// variables
		String username = request.getParameter("loginusername");
		String password = request.getParameter("loginpassword");
		boolean isValid = false;
		String next = "";

		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int userID = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
			ps.setString(1, username); // set first variable in prepared statement
			rs = ps.executeQuery();
			// check if user exists and check password
			while (rs.next()) {
				userID = rs.getInt("userID");
				String dataPassword = rs.getString("password");
				if(dataPassword.equals(password)) {
					isValid = true;
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
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
		}
		/* database ends */
		
		
		/* output boolean isValid */
		/* output int userID if valid */
		
		//PROCESSING VALID BOOLEAN AND FORWARD TO APPROPRIATE PAGE
		if(isValid) {
			HttpSession s = request.getSession();
			s.setAttribute("currentusername", username);
			s.setAttribute("currentuserid", userID);
			s.setAttribute("loggedin", true);
			next = "/HomeFeed.jsp";
			
		}else {
			
			next = "/GuestPage.jsp";
			request.setAttribute("login_err", "Please enter a valid username and password");
		}
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
		
    	try {
    		dispatch.forward(request,response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
