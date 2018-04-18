package notification;

import java.io.IOException;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetNotifications
 */
@WebServlet("/GetNotifications")
public class GetNotifications extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* get notifications */
		String username = request.getParameter("username");
		Stack<Notification> userNotifications = NotificationSocket.getUserNotifications(username);
		Gson gson = new Gson();
		String json = gson.toJson(userNotifications);
		System.out.println("Json: " + json);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}

}
