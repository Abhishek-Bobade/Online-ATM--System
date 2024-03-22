package bankServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class Banking_3 extends GenericServlet implements Servlet {
	Connection con;
	float avl;

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:oci8:@orcl", "abhishek", "2001");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String s1 = request.getParameter("pincode");
		String s2 = request.getParameter("Amount");
		int s22 = Integer.parseInt(s2);
		try {
			PrintWriter pw = response.getWriter();
			if (s22 > 0 && s22 % 100 == 0) {
				PreparedStatement tm = con.prepareStatement("select * from atm where pin_no=?");
				tm.setString(1, s1);
				ResultSet rs1 = tm.executeQuery();
				if (rs1.next()) {
					String avlbal = rs1.getString("BALENCE");
					avl = Integer.parseInt(avlbal);
				} else {
					pw.println("No data found");
				}
				PreparedStatement pm = con.prepareStatement("update atm set BALENCE =(?)where pin_no =(?)");
				float val = avl - s22;
				if (avl >= s22) {
					String setval = String.valueOf(val);
					pm.setString(1, (setval));
					pm.setString(2, s1);
					pm.executeUpdate();

					PreparedStatement pstm = con.prepareStatement("select * from atm where pin_no=?");
					pstm.setString(1, s1);
					ResultSet rs = pstm.executeQuery();
					pw.println("<html><head><title>Withdrawn</title></head><body bgcolor=grey text=white><center><h1>");
					if (rs.next()) {
						String r1 = rs.getString("Name");
						String r2 = rs.getString("PIN_NO");
						String r3 = rs.getString("BALENCE");
						pw.println("Welcome, " + r1 + "</h1><hr><br>");
						pw.println("<table>");
						pw.println("<tr><td>Name</td><td>" + r1 + "</td></tr>");
						pw.println("<tr><td>Pin Number:</td><td>" + r2 + "</td></tr>");
						pw.println("<tr><td>Amount WithDrawn:</td><td>" + s2 + "</td></tr>");
						pw.println("<tr><td>Total Balence:</td><td>" + r3 + "</td></tr>");
						pw.println("</table>");
					} else {
						pw.println("Invaild Pin Number..");
					}
				} else {
					pw.println("Invalid Amount");
				}
			} else {
				pw.println("Invalid Amount / Amount must be Multiple of 100");
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
