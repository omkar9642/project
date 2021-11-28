
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class login extends HttpServlet {

    public String user;
    public String pass;
    public Connection con;
    public Statement stmt;
    public ResultSet rs;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html");
		PrintWriter out=response.getWriter();
                 user=request.getParameter("user");
                 pass=request.getParameter("pwd");
                 
                 try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con= DriverManager.getConnection("Jdbc:Odbc:db","","");
                        String sqlquery = "select username,password  from login where username='"+user+"' and password='"+pass+"'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlquery);
			if(rs.next())
			{
                           
                         response.sendRedirect("home.jsp");	
                        }
                        else
                         {
                          response.sendRedirect("error.jsp");
                         }   rs.close();   
                    }
                
                catch(Exception ex){}
                 

    }
}
