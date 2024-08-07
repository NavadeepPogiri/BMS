

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
 * Servlet implementation class Withdraw
 */
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Withdraw() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String accno = request.getParameter("accno");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		double withdraw = Double.parseDouble(request.getParameter("amount"));

		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "navadeep", "welcome");
			PreparedStatement ps = con.prepareStatement("select * from sdfcbank where accno=? and name=? and password=?");
			ps.setString(1, accno);
			ps.setString(2, name);
			ps.setString(3, password);
            
			ResultSet rs = ps.executeQuery();
			double amount = 0.0;
			if (rs.next())

			{
				amount = rs.getDouble(5);

			}
			amount = amount - withdraw;
			ps = con.prepareStatement("update sdfcbank set amount=? where accno=? and name=? and password=?");
			ps.setDouble(1, amount);
			ps.setString(2, accno);
			ps.setString(3, name);
			ps.setString(4, password);
			
			int i = ps.executeUpdate();
			if(i==1) {
				out.println("Rs " + withdraw + " Withdrawn successfully <br>");
				out.println("<b>Current Balance: " +amount+ "</b>");
			}
			else {
				out.print("enter valid details");
			}
			con.close();

		} catch (Exception ex) {
			out.println(ex);

		}
	}

}
