package csci201;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.google.gson.Gson;
/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        /* database starts */
        // variables
        System.out.println("profileserv");
        HttpSession s =  request.getSession();
        //String username = (String) s.getAttribute("currentusername");
        
        String username1 = (String) request.getParameter("username");
        String username = (String) request.getSession().getAttribute("currentusername");
        List<Post> ownPosts = new ArrayList<Post>(); // user's own posts
        List<Post> likePosts = new ArrayList<Post>(); // user's liked posts
        List<String> followingUsernames = new ArrayList<String>(); // usernames that user follows
        List<String> followerUsernames = new ArrayList<String>(); // usernames that follow the user
        
        int limit = 100;
        System.out.println("username: " + username + " from the passed argutment: " + username1);
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
            // get userID
            ps = conn.prepareStatement("SELECT userID FROM User WHERE username=?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            int userID = 0;
            while (rs.next()) {
                userID = rs.getInt("userID");
            }
            System.out.println("userID: " + userID);
            // get user's posts
            ps = conn.prepareStatement("SELECT u.username, u.picture, p.userID, p.postID, p.image, p.description, p.tag1, p.tag2, p.tag3, p.tag4, p.tag5 " +
                    "FROM Post p, User u " +
                    "WHERE p.userID = u.userID " +
                    "AND u.userID = ? " +
                    "LIMIT " + limit);
            ps.setLong(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) { // add in posts
                // load tags
                List<String> tags = new ArrayList<String>();
                if(rs.getString("tag1") != null) { tags.add(rs.getString("tag1")); }
                if(rs.getString("tag2") != null) { tags.add(rs.getString("tag2")); }
                if(rs.getString("tag3") != null) { tags.add(rs.getString("tag3")); }
                if(rs.getString("tag4") != null) { tags.add(rs.getString("tag4")); }
                if(rs.getString("tag5") != null) { tags.add(rs.getString("tag5")); }
                // load comments
                int postID = rs.getInt("postID");
                List<Comment> comments = new ArrayList<Comment>();
                ps2 = conn.prepareStatement("SELECT c.commentID, u.username, u.picture, c.content FROM Comment c, User u " + 
                        "WHERE postID=? AND c.userID = u.userID");
                ps2.setLong(1, postID); // set first variable in prepared statement
                System.out.println("postID: " + postID);
                rs2 = ps2.executeQuery();
                while(rs2.next()) {
                    System.out.println("CommenID!!!");
                    System.out.println("content: " + rs2.getString("content"));
                    System.out.println("commentID: " + rs2.getInt("commentID"));
                    System.out.println("username: " + rs2.getString("username"));
                    Comment tempComment = new Comment(rs2.getInt("commentID"), rs2.getString("username"), rs2.getString("content"));
                    System.out.println("here");
                    comments.add(tempComment);
                    System.out.println("here");
                }
                System.out.println("here");
                Post tempPost = new Post(postID, rs.getString("image"), rs.getString("username"), rs.getString("picture"), rs.getString("description"), tags, comments);
                ownPosts.add(tempPost);
            }
            ps.close();
            rs.close();
            ps2.close();
            // get user's liked posts
            ps = conn.prepareStatement("SELECT u.username, u.picture, p.userID, p.postID, p.image, p.description, p.tag1, p.tag2, p.tag3, p.tag4, p.tag5 " +
                    "FROM User u, Post p, Likes l " +
                    "WHERE p.postID = l.postID " +
                    "AND u.userID = p.userID " +
                    "AND l.userID = ? " +
                    "LIMIT 100");
            ps.setLong(1, userID);
            rs = ps.executeQuery();
            ps2 = conn.prepareStatement("SELECT u.username, c.commentID, c.content FROM Comment c, User u " + 
                    "WHERE postID=? AND c.userID = u.userID");
            ps3 = conn.prepareStatement("SELECT * FROM Likes WHERE userID = ? AND postID = ? AND valid = 1");
            ps3.setInt(1, userID);
            ps4 = conn.prepareStatement("Select * FROM Follow WHERE followerID = ? AND followingID = ? AND valid = 1");
            ps4.setInt(1, userID);
            while (rs.next()) { // add in posts
                // load tags
                List<String> tags = new ArrayList<String>();
                if(rs.getString("tag1") != null) { tags.add(rs.getString("tag1")); }
                if(rs.getString("tag2") != null) { tags.add(rs.getString("tag2")); }
                if(rs.getString("tag3") != null) { tags.add(rs.getString("tag3")); }
                if(rs.getString("tag4") != null) { tags.add(rs.getString("tag4")); }
                if(rs.getString("tag5") != null) { tags.add(rs.getString("tag5")); }
                // load comments
                int postID = rs.getInt("postID");
                List<Comment> comments = new ArrayList<Comment>();
                ps2.setLong(1, postID); // set first variable in prepared statement
                rs2 = ps2.executeQuery();
                while(rs2.next()) {
                    Comment tempComment = new Comment(rs2.getInt("commentID"), rs2.getString("username"), rs2.getString("content"));
                    comments.add(tempComment);
                }
                Post tempPost = new Post(postID, rs.getString("image"), rs.getString("username"), rs.getString("picture"), rs.getString("description"), tags, comments);
                // check like and comment if loggedin
                ps3.setInt(2, postID);
                rs3 = ps3.executeQuery();
                if(rs3.next()) {
                    tempPost.setIsLike(true);
                }
                int postUserID = rs.getInt("userID");
                ps4.setInt(2, postUserID);
                rs4 = ps4.executeQuery();
                if(rs4.next()) {
                    tempPost.setIsFollow(true);
                }
                likePosts.add(tempPost);
            }
            // get followings
            ps = conn.prepareStatement("SELECT u.username FROM Follow f, User u WHERE f.followerID = ? AND f.followingID = u.userID");
            ps.setLong(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) { // add in followings
                followingUsernames.add(rs.getString("username"));
            }
            // get followers
            ps = conn.prepareStatement("SELECT u.username FROM Follow f, User u WHERE f.followingID = ? AND f.followerID = u.userID");
            ps.setLong(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) { // add in followings
                followerUsernames.add(rs.getString("username"));
            }
        } catch (SQLException sqle) {
        	sqle.printStackTrace();
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
            	sqle.printStackTrace();
                System.out.println("sqle: " + sqle.getMessage());
            }
            try {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            } catch (SQLException sqle) {
            	sqle.printStackTrace();
                System.out.println("sqle: " + sqle.getMessage());
            }
            try {
                if (rs3 != null) {
                    rs3.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            } catch (SQLException sqle) {
            	sqle.printStackTrace();
                System.out.println("sqle: " + sqle.getMessage());
            }
            try {
                if (rs4 != null) {
                    rs4.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            } catch (SQLException sqle) {
            	sqle.printStackTrace();
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
        /* database ends */
        
//      s.setAttribute("ownPosts",ownPosts);
//      s.setAttribute("likePosts", likePosts);
//      s.setAttribute("followingUsernames",followingUsernames);// = new ArrayList<String>(); // usernames that user follows
//      s.setAttribute("followerUsernames", followerUsernames);
        
//      request.setAttribute("ownPosts",ownPosts);
//      request.setAttribute("likePosts", likePosts);
//      request.setAttribute("followingUsernames",followingUsernames);// = new ArrayList<String>(); // usernames that user follows
//      request.setAttribute("followerUsernames", followerUsernames);
        System.out.println("size: " + ownPosts.size());
        
        Gson gson = new Gson();
        String followerString = gson.toJson(followerUsernames);
        String followingString = gson.toJson(followingUsernames);
        String postsString = gson.toJson(ownPosts);
        String likedString = gson.toJson(likePosts);
        JSONObject jsonObject = new JSONObject();
        
        System.out.println("followerString: " + followerString + " followingString: "+ followingString + " postsString: "+ postsString+ " likedString: " + likedString);
        try {
            jsonObject.put("followerUsernames", followerString);
            jsonObject.put("followingUsernames", followingString);
            jsonObject.put("ownPosts", postsString);
            jsonObject.put("likePosts", likedString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//      System.out.println(limit + " " + posts.size());
//      System.out.println(json);
        //json += gson.toJson(followingUsernames);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
       // response.getWriter().write(json);
        
        /* output List<Post> posts */
    }
}