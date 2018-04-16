package csci201;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class NewPost
 */
@WebServlet("/NewPost")
public class NewPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// System.out.println("newpostservelt");
		/* database starts */
		// variables
		System.out.println("******************");
		System.out.println("newpost");
		PrintWriter pw = response.getWriter();

		String username = "";
		String pic = "";
		String imageURL = "";
		String description = "";
		String tag1 = "";
		String tag2 = "";
		String tag3 = "";
		String tag4 = "";
		String tag5 = "";
		HttpSession s = request.getSession();
		boolean validInputs = true;
		username = (String) s.getAttribute("currentusername");
		pic = request.getParameter("pic");
		imageURL = request.getParameter("img");
		description = request.getParameter("description");
		tag1 = request.getParameter("tag1");
		tag2 = request.getParameter("tag2");
		tag3 = request.getParameter("tag3");
		tag4 = request.getParameter("tag4");
		tag5 = request.getParameter("tag5");
		System.out.println("Check before sending errors username: " + username + " pic: " + pic + " img: " + imageURL
				+ " descrip: " + description + " tag1: " + tag1);
		if (description == null) {
			description = "";
		}
		if (imageURL == null) {
			imageURL = "";
		}

		boolean tagValid = true;
		boolean imgValid = true;
		boolean descrValid = true;
		if (tag1.trim().equals("") && tag2.trim().equals("") && tag3.trim().equals("") && tag4.trim().equals("")
				&& tag5.trim().equals("")) {
			// next = "/HomeFeed.jsp"; // checks if the user inputed at least 1 tags
			System.out.println("need 1 tag");
			request.setAttribute("tagError", "Need at least 1 tag to continue");
			// dispatch = getServletContext().getRequestDispatcher(next);
			validInputs = false;
			tagValid = false;
		}
		// if(pic == null) System.out.println("testnul111111111111111111111111");
		// if ((pic.trim().equals("") || pic == null)&&
		// check for both pic and url
		if (imageURL.trim().equals("")) {
			System.out.println("need img");
			// next = "/HomeFeed.jsp"; // checks if the user inputed a file or a url for the
			// image
			request.setAttribute("imgError", "Need an imageURL to continue");
			// dispatch = getServletContext().getRequestDispatcher(next);
			validInputs = false;
			imgValid = false;
		}
		if (description.trim().equals("")) {
			System.out.println("need description");
			// next = "/HomeFeed.jsp"; // checks if the user inputed a description
			request.setAttribute("descriptionError", "Need a description to continue");
			// dispatch = getServletContext().getRequestDispatcher(next);
			validInputs = false;
			descrValid = false;
		}
		System.out.println("Check after sending username: " + username + " img: " + imageURL + " descrip: "
				+ description + " tag1: " + tag1);
		if (!validInputs) { // if there is any of these contents not filled out, then the appropriate error
							// message is outputed
			// request.setAttribute("login_err", "Please enter a valid username and
			// password");
			pw.println("Please enter a valid ");
			if (!descrValid) {
				pw.println("description");
			}
			if (!imgValid) {
				if (!descrValid) {
					pw.println("and");
				}
				pw.println(" image");
			}
			if (!tagValid) {
				if (!descrValid || !imgValid) {
					pw.println("and");
				}
				pw.println(" tag");
			}
			pw.flush();
			pw.close();
		}

		if (validInputs) {
			System.out.println(
					"username: " + username + " img: " + imageURL + " descrip: " + description + " tag1: " + tag1);
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager
						.getConnection("jdbc:mysql://localhost/DogSpotting?user=root&password=root&useSSL=false");
				// find userID
				// System.out.println("test0");
				ps = conn.prepareStatement("SELECT userID FROM User WHERE username=?");
				// ps.setString(1, username);
				ps.setString(1, username);
				// System.out.println("test0.1");
				rs = ps.executeQuery();
				int userID = 0;
				// System.out.println("test1");
				while (rs.next()) { // get userID
					userID = rs.getInt("userID");
				}
				ps.close();
				// insert new post
				// System.out.println("test2");
				ps = conn.prepareStatement(
						"INSERT INTO Post (userID, image, description, tag1, tag2, tag3, tag4, tag5, dailylike, monthlylike, yearlylike) "
								+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0)");
				// System.out.println("test3");
				ps.setLong(1, userID);
				// System.out.println("test4");
				ps.setString(2, imageURL);
				ps.setString(3, description);
				ps.setString(4, tag1);
				ps.setString(5, tag2);
				ps.setString(6, tag3);
				ps.setString(7, tag4);
				ps.setString(8, tag5);
				ps.executeUpdate();
				// System.out.println("test11");
			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
			} catch (ClassNotFoundException cnfe) {
				System.out.println("ClassNotFoundException: " + cnfe.getMessage());
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
				}
			}
			pw.flush();
			pw.close();
			/* database ends */
		}
	}
}
