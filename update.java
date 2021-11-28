

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class update extends HttpServlet {

    public String user;
    public String npass;
    public String cpass;
    public Connection con;
    public Statement stmt;
    public ResultSet rs;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html");
		PrintWriter out=response.getWriter();
                 user=request.getParameter("user");
                 npass=request.getParameter("npwd");
                 cpass=request.getParameter("cpwd");
                 
                 try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con= DriverManager.getConnection("Jdbc:Odbc:db","","");
                        stmt = con.createStatement();
                        if(npass.equals(cpass))
                        {
                            try{
                        String sqlquery = "update login set password='"+npass+"' where username='"+user+"'";
			rs = stmt.executeQuery(sqlquery);
                            }catch(Exception e1){}
                          response.sendRedirect("updates.jsp");
                           
               }
                        else
                         {
                          response.sendRedirect("error.jsp");
                         } 
                        rs.close();
                    }
                
                catch(Exception ex){
               			      }

    }
}
