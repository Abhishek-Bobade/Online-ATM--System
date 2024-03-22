package bankServer;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
public class Banking extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	Connection con;
    public Banking() {
        super();
    }
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:oci8:@orcl","abhishek","2001");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void destroy() {
		
	}
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String s1=request.getParameter("pincode");
		try {
			String sql = "select * from atm where pin_no=?";
			PreparedStatement pstm=con.prepareStatement(sql);
			pstm.setString(1,s1);
			ResultSet rs=pstm.executeQuery();
			PrintWriter pw=response.getWriter();
			pw.println("<html>");
			pw.println("<style>td{width:150px;height:15px;}</style>");
			pw.println("<body bgcolor=grey text=white><center><h1>");
				if(rs.next()) {
					String r1 = rs.getString("PIN_NO");
					String r2 = rs.getString("NAME");
					String r3 = rs.getString("BALENCE");
					pw.println("Welcome, "+r2+"</h1><hr><br>");
					pw.println("<table>");
					pw.println("<tr><td>Name</td><td>"+r2+"</td></tr>");
					pw.println("<tr><td>Pin Number:</td><td>"+r1+"</td></tr>");
					pw.println("<tr><td>Balence:</td><td>"+r3+"</td></tr>");
					pw.println("</center></table>");
				}
				else {
					pw.println("Invaild Pin Number");
				}
			pw.println("</center></body></html>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
