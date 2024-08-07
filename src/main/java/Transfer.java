

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
 * Servlet implementation class Transfer
 */
@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			String accno = request.getParameter("accno");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String taccno=request.getParameter("taccno");
			double tamount=Double.parseDouble(request.getParameter("amount"));
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "navadeep", "welcome");
			PreparedStatement ps1 = con.prepareStatement("select * from sdfcbank where accno=? and name=? and password=? ");
			ps1.setString(1, accno);
			ps1.setString(2, name);
			ps1.setString(3, password);
			
			ResultSet rs = ps1.executeQuery();
			double samount=0.0;
			if (rs.next())

			{
                 samount=rs.getDouble("amount");
				

			}
			if(samount>=tamount) {
			samount=samount-tamount;
			
			PreparedStatement ps2 = con.prepareStatement("update sdfcbank set amount=? where accno=? ");
			ps2.setDouble(1, samount);
			ps2.setString(2, accno);
			
			int i=ps2.executeUpdate();
			
			/*
			ps1.setString(3, name);
			ps1.setString(4, password);
			*/
			
			/*
			out.println("Rs "+tamount+" transferred successfully <br>");
			out.println("Current Balance: "+samount);
			*/
			 PreparedStatement ps3 = con.prepareStatement("select * from sdfcbank where accno=?");
			 ps3.setString(1, taccno);
			 
			 ResultSet rs1=ps3.executeQuery();
			
			
			double ramount=0.0;
			if (rs1.next())

			{
                 ramount=rs1.getDouble("amount");
				

			}

			ramount=ramount+tamount;
			
			PreparedStatement ps4=con.prepareStatement("update sdfcbank set amount=? where accno=?");
			ps4.setDouble(1, ramount);
			ps4.setString(2, taccno);
			
			
			int i1=ps4.executeUpdate();
			if(i1>0)
			{
				out.println("Amount Transfered Successfully....");
			}
			}
			else {
				out.print("NO BALANCE RA NANNA");
			}
			con.close();
			
			
		} catch (Exception ex) 
		{
			out.println(ex);
		}
	}

}
