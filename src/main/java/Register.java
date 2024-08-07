
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try {
			String accno=request.getParameter("accno");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String cpassword = request.getParameter("cpassword");
			double amount=Double.parseDouble(request.getParameter("amount"));
			String address=request.getParameter("address");
			String mobile = request.getParameter("mobile");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "navadeep", "welcome");

			PreparedStatement ps = con.prepareStatement("insert into sdfcbank values(?,?,?,?,?,?,?,default)");
			ps.setString(1, accno);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setString(4, cpassword);
			ps.setDouble(5, amount);
			ps.setString(6, address);
			ps.setString(7, mobile);
			int i=0;
			
			if(amount>1000) {
			i = ps.executeUpdate();
			}
			else {
				out.println("please deposit some money");
			}
			if(i==1) {
			out.println("New user register Succesfully");
			}
			con.close();
			
		}
		catch(Exception ex) {
			out.println(ex);
		}
	}

}
