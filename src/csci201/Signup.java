package csci201;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		/* database starts */
		// variables
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String retypepassword = request.getParameter("retypepassword");
		String imageUrl = request.getParameter("url");
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("retype: " + retypepassword);
		boolean isValid = true;

		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (!password.equals(retypepassword)) {
			System.out.println("not same");

			// PROCESSING VALID BOOLEAN AND FORWARD TO APPROPRIATE PAGE
			PrintWriter pw = response.getWriter();
			request.setAttribute("signup_err", "Passwords must match.");
			pw.println("Passwords must match.");
			pw.flush();
			pw.close();

		} else if (username.length() > 15 || password.length() > 15 || retypepassword.length() > 15) {
			System.out.println("more than 15 characters");

			// PROCESSING VALID BOOLEAN AND FORWARD TO APPROPRIATE PAGE
			PrintWriter pw = response.getWriter();
			request.setAttribute("signup_err", "Username and password must be 15 characters or less.");
			pw.println("Username and password must be 15 characters or less.");
			pw.flush();
			pw.close();
		} else if(imageUrl.length()>300){
			PrintWriter pw = response.getWriter();
			request.setAttribute("signup_err", "Image URL must be less than 300 characters.");
			pw.println("Image URL must be less than 300 characters.");
			pw.flush();
			pw.close();
			
		}else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager
						.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
				st = conn.createStatement();
				ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
				ps.setString(1, username); // set first variable in prepared statement
				rs = ps.executeQuery();
				while (rs.next()) { // check if username alrady taken
					isValid = false;
					System.out.println("username = " + username + " is already taken!");
				}
				if (isValid) {
					ps = conn.prepareStatement("INSERT INTO User (username, password, picture) VALUES (?, ?, ?)");
					ps.setString(1, username);
					ps.setString(2, password);
					ps.setString(3, imageUrl);
					ps.executeUpdate();
				}
			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
			} catch (ClassNotFoundException cnfe) {
				System.out.println("ClassNotFoundException: " + cnfe.getMessage());
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

			System.out.println("isValid: " + isValid);

			// PROCESSING VALID BOOLEAN AND FORWARD TO APPROPRIATE PAGE
			PrintWriter pw = response.getWriter();
			if (isValid) {
				HttpSession s = request.getSession();
				s.setAttribute("currentusername", username);
				s.setAttribute("loggedin", true);
				// next = "/HomeFeed.jsp";
				System.out.println("inside signup success");

			} else {
				System.out.println("inside signup failed");
				// next = "/GuestPage.jsp";
				request.setAttribute("signup_err", "Please enter a valid username and password");
				pw.println("Please enter a valid username and password");
				pw.flush();
			}
			pw.close();
		}

		// RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
		//
		// try {
		// dispatch.forward(request, response);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ServletException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
