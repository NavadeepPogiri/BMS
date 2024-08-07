
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Closeacc
 */
@WebServlet("/Closeacc")
public class Closeacc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Closeacc() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			String accno = request.getParameter("accno");
			String name = request.getParameter("name");
			String password = request.getParameter("password");

			
			Class.forName("oracle.jdbc.driver.OracleDriver");

			
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "navadeep", "welcome");

			PreparedStatement ps = con.prepareStatement("SELECT * FROM sdfcbank WHERE accno=? AND name=? AND password=?");
			ps.setString(1, accno);
			ps.setString(2, name);
			ps.setString(3, password);
			ResultSet rs = ps.executeQuery();

			
			if (rs.next()) {
				PreparedStatement ps1 = con.prepareStatement("UPDATE sdfcbank SET status='Deactivated' WHERE accno=? AND password=?");
				ps1.setString(1, accno);
				ps1.setString(2, password);
				int i = ps1.executeUpdate();

				if (i > 0) 
				{
					out.println("YOUR ACCOUNT IS DEACTIVATED");
				} 
				else 
				{
					out.println("Failed to deactivate the account.");
				}
			} 
			else 
			{
				out.println("Invalid account details.");
			}

			con.close();
		} 
		catch (Exception ex)
		{
			out.println(ex);
		}
	}

}
