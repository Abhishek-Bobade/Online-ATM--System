package bankServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/newuser")
public class Createuser extends GenericServlet implements Servlet {
	Connection con;

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:oci8:@orcl", "abhishek", "2001");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String s1 = req.getParameter("name");
		String s2 = req.getParameter("pin_no");
		String s3 = req.getParameter("amount");
		try {
			PrintWriter pw = res.getWriter();
			PreparedStatement ptsm = con.prepareStatement("insert into atm values (?,?,?)");
			ptsm.setString(1, s2);
			ptsm.setString(2, s1);
			ptsm.setString(3, s3);
			ptsm.executeUpdate();
			pw.println("Registraction done for " + s1);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}
