package com.planks.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DatabaseServlet extends HttpServlet {
	Connection con=null;
	PreparedStatement ps=null;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","experiment","jdbc");
			
			if(con!=null) {
				ps=con.prepareStatement("insert into student values(?,?,?,?,?,?,?)");
			}
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	PrintWriter pw=null;
	int result=0;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		resp.setContentType("text/html");
		pw=resp.getWriter();
		
		String sid=req.getParameter("id");
		int id=Integer.parseInt(sid);
		String name=req.getParameter("sname");
		String course=req.getParameter("course");
		String branch=req.getParameter("branch");
		String email=req.getParameter("email");
		String adrs=req.getParameter("adrs");
		String pass=req.getParameter("pass");
		
		try {
			if(ps!=null) {
				ps.setInt(1, id);
				ps.setString(2, name);
				ps.setString(3, course);
				ps.setString(4, branch);
				ps.setString(5, email);
				ps.setString(6, adrs);
				ps.setString(7, pass);
				
				result=ps.executeUpdate();
			}
				if(result==0) {
					pw.println("<h1 style='color:red'>Registration failed!, please try again</h1>");	
				}
				else {
					pw.println("<h1 style='color:red'> Registration complete!</h1>");
					pw.println("<h2> Submitted detail is::</h2>");
					pw.println("<h3>Student id:"+id+ "</h3>");
					pw.println("<h3>Student name:"+name+ "</h3>");
					pw.println("<h3>Student course:"+course+ "</h3>");
					pw.println("<h3>Student branch:"+branch+ "</h3>");
					pw.println("<h3>Student E-mail:"+email+ "</h3>");
					pw.println("<h3>Student address:"+adrs+ "</h3>");
				}
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		pw.println("<a href='signUp.html'>Back to SignUp</a>");
			
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
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
