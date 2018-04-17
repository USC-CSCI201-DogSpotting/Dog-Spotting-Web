package csci201;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ValidateUsername
 */
@WebServlet("/ValidateUsername")
public class ValidateUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("validateusername");
		HttpSession ses = request.getSession();
		//String otherUsername = request.getParameter("otherusername");
		String u = (String) ses.getAttribute("otherusername");
				//portalUtil.getHttpServletRequest(renderRequest);
		//String otherUsername = (String) request.getSession().getAttribute("otherusername");
		String otherUsername = request.getParameter("otherusername");
		request.setAttribute("otherusername", otherUsername);
		ses.setAttribute("otherusername", otherUsername);
		// set the other username as an attribute
		System.out.println("otherusernames: " + otherUsername +" u: " + u);
		String next = "/OtherProfile.jsp";
		System.out.println("test1");
		//RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);	
		System.out.println("test2");
		//dispatch.forward(request, response);
		}

}
