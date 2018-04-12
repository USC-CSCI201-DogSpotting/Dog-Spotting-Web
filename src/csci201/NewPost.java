package csci201;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
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
		description = request.getParameter("descr");
		tag1 = request.getParameter("tag1");
		tag2 = request.getParameter("tag2");
		tag3 = request.getParameter("tag3");
		tag4 = request.getParameter("tag4");
		tag5 = request.getParameter("tag5");

		String next = "/HomeFeed.jsp";
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);

		if (tag1.trim().equals("") && tag2.trim().equals("") && tag3.trim().equals("") && tag4.trim().equals("")
				&& tag5.trim().equals("")) {
			next = "/HomeFeed.jsp"; // checks if the user inputed at least 1 tags
			request.setAttribute("tagError", "Need at least 1 tag to continue");
			dispatch = getServletContext().getRequestDispatcher(next);
			validInputs = false;
		}
		if (pic.trim().equals("") && imageURL.trim().equals("")) {
			next = "/HomeFeed.jsp"; // checks if the user inputed a file or a url for the image
			request.setAttribute("imgError", "Need an imageURL to continue");
			dispatch = getServletContext().getRequestDispatcher(next);
			validInputs = false;
		}
		if (description.trim().equals("")) {
			next = "/HomeFeed.jsp"; // checks if the user inputed a description
			request.setAttribute("descriptionError", "Need a description to continue");
			dispatch = getServletContext().getRequestDispatcher(next);
			validInputs = false;
		}
		if (!validInputs) { // if there is any of these contents not filled out, then the appropriate error
							// message is outputed
			dispatch.forward(request, response);
		}

		if (validInputs) {
			System.out.println(
					"username: " + username + " img: " + imageURL + " descrip: " + description + " tag1: " + tag1);
			Connection conn = null;
			Statement st = null;
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

				// insert new post
				// System.out.println("test2");
				ps = conn.prepareStatement(
						"INSERT INTO Post (userID, image, description, tag1, tag2, tag3, tag4, tag5, dailylike, weeklylike, monthlylike) "
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
			// request.setAttribute("posts", post);
			/* database ends */
			next = "/HomeFeed.jsp"; // where do we want to go after the user posts?
			request.setAttribute("postFinish", "Thank you for posting");
			dispatch = getServletContext().getRequestDispatcher(next);
			dispatch.forward(request, response);
		}
	}
}