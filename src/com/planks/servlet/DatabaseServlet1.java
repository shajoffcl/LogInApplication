package com.planks.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DatabaseServlet1 extends HttpServlet {
	Connection con=null;
	PreparedStatement ps=null;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","experiment","jdbc");
			
			if(con!=null) {
				ps=con.prepareStatement("select id,name,course,branch,email,address from student where email=? and password=?");
			}
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	PrintWriter pw=null;
	ResultSet rs=null;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		resp.setContentType("text/html");
		pw=resp.getWriter();
		
		String email=req.getParameter("email");
		String pass=req.getParameter("pass");
		
		try {
			if(ps!=null) {
				ps.setString(1, email);
				ps.setString(2, pass);
				
				rs=ps.executeQuery();
			}
			if(rs.next()) {
				pw.println("<h1 style='color:red'>Successfully login!</h1>");
				pw.println("<h2>Student Details::</h2>");
				pw.println("Students id:"+rs.getInt(1)+"<br/>");
				pw.println("Student name:"+rs.getString(2)+"<br/>");
				pw.println("Student course:"+rs.getString(3)+"<br/>");
				pw.println("Student branch:"+rs.getString(4)+"<br/>");
				pw.println("Student E-mail:"+rs.getString(5)+"<br/>");
				pw.println("Student address:"+rs.getString(6)+"<br/>");
			}
			else {
				pw.println("<h1 style='color:red'>Invalid email or password!</h1>");
				pw.println("<h1 style='color:red'>Please try again once time.</h1>");
			}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		pw.println("<a href='signIn.html'>Back to login</a>");
			
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		try {
			rs.close();
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		pw.close();
		try {
			ps.close();
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		try {
			con.close();
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}

}


