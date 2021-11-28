import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NewServlet extends HttpServlet {
       
        public static int[] credits={0,0,0,0,0,0,0,0,0};
	public static double sgpa=0.0;
	public static int[] gp={0,0,0,0,0,0,0,0,0};
	public static String regno,en,semdate="",acy="",spdate="",name="",fname="";
        public static int ex[]={0,0,0,0,0,0,0,0,0};
	public static int tot[]={0,0,0,0,0,0,0,0,0};
	public static String exs[]={"","","","","","","","",""};
	public static String ins[]={"","","","","","","","",""};
	public static int in[]={0,0,0,0,0,0,0,0,0};
	public static int slno;
	public static String semtable="s",batch="",creditstable="cr";
	public static int sem=0;
	public static boolean pof=false;
	public static String branch[]={"BT","CE","CH","CS","EC","EE","EI","IT","ME"};
	public static String pdate[]={"","","","","","","","",""};
	public static int pass[]={0,0,0,0,0,0,0,0,0};
	public static Connection con,con1;
	public static ResultSet rs;
	public static Statement stmt,stmt1;
	public static int tc=0,tgp=0;
	public static boolean elig=false;
        public String fileName;
        
        
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
                response.setContentType("text/html");
		PrintWriter out=response.getWriter();
         
         batch=request.getParameter("batch");
         sem=Integer.parseInt(request.getParameter("sem"));
         semdate=request.getParameter("semdate");
         acy=request.getParameter("acy");
         fileName=request.getParameter("files");

         semtable+=sem;
	 creditstable+=sem;
         
         try{
			String url = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+fileName+";readonly=0;";
			
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection(url,"","");
                        
		}
		catch(Exception e)
        {
            out.println("The  Exception is"+e);
			
        }
            try{
            stmt=con.createStatement();
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con1=DriverManager.getConnection("Jdbc:Odbc:db","","");
		stmt1=con1.createStatement();
                
            }catch(Exception e7){}
		for(String b:branch)  
		for(int y=10;y<=14;y++)
		for(int i=401;i<=650;i++){
			regno="Y"+y+"A"+b+""+i;
			//regno="Y10ABT413";
                      
                        try{
				rs=stmt.executeQuery("select * from [reg$] where REGDNO='"+regno+"'");
		while(rs.next()){
			elig=true;
			name=rs.getString(2);
			
			for(int j=3;j<=11;j++)
				exs[j-3]=rs.getString(j);
			slno=rs.getInt("SLNO");
			slno+=1;
			for(int j=0;j<=8;j++){
				if(exs[j].equals("A"))
					ex[j]=-1;
				else if(exs[j].equals("_")||exs[j].equals("-"))
					ex[j]=-2;
				else
					ex[j]=new Integer(exs[j]);
			}
			rs=stmt.executeQuery("select * from [reg$] where SLNO="+slno);
			while(rs.next()){
				fname=rs.getString(2);
				for(int j=3;j<=11;j++)
					ins[j-3]=rs.getString(j);
				for(int j=0;j<=8;j++){
					if(ins[j].equals("A"))
						in[j]=-1;
					else
						in[j]=new Integer(ins[j]);
				}
			}
		}
	
                        
			//reval(b,sem);
			rs=stmt1.executeQuery("select * from cr"+sem+" where BRANCH='"+b+"'");
		while(rs.next()){
			for(int p=0;p<9;p++){
				credits[p]=Integer.parseInt(rs.getString(p+2));
			}		
		}
		rs.close();
			for(int q=0;q<9;q++){
			if(ex[q]>=24&&(ex[q]+in[q])>=90)
				gp[q]=10;
			else if(ex[q]>=24&&(ex[q]+in[q])>=80)
				gp[q]=9;
			else if(ex[q]>=24&&(ex[q]+in[q])>=70)
				gp[q]=8;
			else if(ex[q]>=24&&(ex[q]+in[q])>=60)
				gp[q]=7;
			else if(ex[q]>=24&&(ex[q]+in[q])>=50)
				gp[q]=6;
			else if(ex[q]>=24&&(ex[q]+in[q])>=40)
				gp[q]=5;
			else
				gp[q]=0;
		}
			sgpa=0.0;
		double tn=0.0,td=0.0;
		for(int r=0;r<9;r++){
			tn+=(double)(credits[r]*gp[r]);
			td+=(double)credits[r];
			sgpa=tn/td;
		}
                        
                        }catch(Exception e6){}
                        pof=true;
		
		for(int k=0;k<6;k++){ 
			if(ex[k]==-2||ex[k]==-1||(ex[k]<24||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		for(int k=6;k<9;k++){
			if(ex[k]==-2||ex[k]==-1||(ex[k]<30||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		
		for(int n=0;n<9;n++)
			if(pass[n]==1){
				
			}
			else{
				pof=false;
				
			}
			
			if(pof){
				spdate=semdate;
			}
			else
				spdate="-";
			for(int j=0;j<9;j++){
				if(ex[j]<0)
					ex[j]=0;
				if(in[j]<0)
					in[j]=0;
				tot[j]=ex[j]+in[j];
				tc+=credits[j];
				tgp+=gp[j];
			}
			
			if(elig){
                            try {
                                String query="insert into "+ semtable+"(regdno, name, fname, e1, e2, e3, e4, e5, e6, e7, e8, e9, i1, i2, i3, i4, i5, i6, i7, i8, i9, t1, t2, t3, t4, t5, t6, t7, t8, t9, pd1, pd2, pd3, pd4, pd5, pd6, pd7, pd8, pd9, cr1, cr2, cr3, cr4, cr5, cr6, cr7, cr8, cr9, gp1, gp2, gp3, gp4, gp5, gp6, gp7, gp8, gp9, spdate, tc, tg, sgpa, acy, attemptdate)  values('"+regno+"','"+name+"','"+fname+"','"+exs[0]+"','"+exs[1]+"','"+exs[2]+"','"+exs[3]+"','"+exs[4]+"','"+exs[5]+"','"+exs[6]+"','"+exs[7]+"','"+exs[8]+"','"+ins[0]+"','"+ins[1]+"','"+ins[2]+"','"+ins[3]+"','"+ins[4]+"','"+ins[5]+"','"+ins[6]+"','"+ins[7]+"','"+ins[8]+"','"+Integer.toString(tot[0])+"','"+Integer.toString(tot[1])+"','"+Integer.toString(tot[2])+"','"+Integer.toString(tot[3])+"','"+Integer.toString(tot[4])+"','"+Integer.toString(tot[5])+"','"+Integer.toString(tot[6])+"','"+Integer.toString(tot[7])+"','"+Integer.toString(tot[8])+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+pdate[6]+"','"+pdate[7]+"','"+pdate[8]+"','"+Integer.toString(credits[0])+"','"+Integer.toString(credits[1])+"','"+Integer.toString(credits[2])+"','"+Integer.toString(credits[3])+"','"+Integer.toString(credits[4])+"','"+Integer.toString(credits[5])+"','"+Integer.toString(credits[6])+"','"+Integer.toString(credits[7])+"','"+Integer.toString(credits[8])+"','"+Integer.toString(gp[0])+"','"+Integer.toString(gp[1])+"','"+Integer.toString(gp[2])+"','"+Integer.toString(gp[3])+"','"+Integer.toString(gp[4])+"','"+Integer.toString(gp[5])+"','"+Integer.toString(gp[6])+"','"+Integer.toString(gp[7])+"','"+Integer.toString(gp[8])+"','"+spdate+"','"+Integer.toString(tc)+"','"+Integer.toString(tgp)+"','"+Double.toString(sgpa)+"','"+acy+"','"+semdate+"')";
		
                                stmt1.executeUpdate(query);
                            } catch (Exception ex) {
                                Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
			elig=false;
			tc=0;
			tgp=0;
		}
		if(sem!=11||sem!=12){
		for(String b:branch)  
		for(int y=10;y<=15;y++)
		for(int i=401;i<=650;i++){
			regno="L"+y+"A"+b+""+i;
			//regno="Y10ABT413";
			try{
                        	rs=stmt.executeQuery("select * from [reg$] where REGDNO='"+regno+"'");
		while(rs.next()){
			elig=true;
			name=rs.getString(2);
			
			for(int j=3;j<=11;j++)
				exs[j-3]=rs.getString(j);
			slno=rs.getInt("SLNO");
			slno+=1;
			for(int j=0;j<=8;j++){
				if(exs[j].equals("A"))
					ex[j]=-1;
				else if(exs[j].equals("_")||exs[j].equals("-"))
					ex[j]=-2;
				else
					ex[j]=new Integer(exs[j]);
			}
			rs=stmt.executeQuery("select * from [reg$] where SLNO="+slno);
			while(rs.next()){
				fname=rs.getString(2);
				for(int j=3;j<=11;j++)
					ins[j-3]=rs.getString(j);
				for(int j=0;j<=8;j++){
					if(ins[j].equals("A"))
						in[j]=-1;
					else
						in[j]=new Integer(ins[j]);
				}
			}
		}
	
			//reval(b,sem);
			rs=stmt1.executeQuery("select * from cr"+sem+" where BRANCH='"+b+"'");
		while(rs.next()){
			for(int p=0;p<9;p++){
				credits[p]=Integer.parseInt(rs.getString(p+2));
			}		
		}
		rs.close();
			for(int q=0;q<9;q++){
			if(ex[q]>=24&&(ex[q]+in[q])>=90)
				gp[q]=10;
			else if(ex[q]>=24&&(ex[q]+in[q])>=80)
				gp[q]=9;
			else if(ex[q]>=24&&(ex[q]+in[q])>=70)
				gp[q]=8;
			else if(ex[q]>=24&&(ex[q]+in[q])>=60)
				gp[q]=7;
			else if(ex[q]>=24&&(ex[q]+in[q])>=50)
				gp[q]=6;
			else if(ex[q]>=24&&(ex[q]+in[q])>=40)
				gp[q]=5;
			else
				gp[q]=0;
		}
			sgpa=0.0;
		double tn=0.0,td=0.0;
		for(int r=0;r<9;r++){
			tn+=(double)(credits[r]*gp[r]);
			td+=(double)credits[r];
			sgpa=tn/td;
		}
                        }
                        catch(Exception e5){}
                        pof=true;
		
		for(int k=0;k<6;k++){ 
			if(ex[k]==-2||ex[k]==-1||(ex[k]<24||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		for(int k=6;k<9;k++){
			if(ex[k]==-2||ex[k]==-1||(ex[k]<30||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		
		for(int n=0;n<9;n++)
			if(pass[n]==1){
				
			}
			else{
				pof=false;
				
			}
			
			if(pof){
				spdate=semdate;
			}
			else
				spdate="-";
			for(int j=0;j<9;j++){
				if(ex[j]<0)
					ex[j]=0;
				if(in[j]<0)
					in[j]=0;
				tot[j]=ex[j]+in[j];
				tc+=credits[j];
				tgp+=gp[j];
			}
			
			if(elig){
                            try {
                                 String query="insert into "+ semtable+"(regdno, name, fname, e1, e2, e3, e4, e5, e6, e7, e8, e9, i1, i2, i3, i4, i5, i6, i7, i8, i9, t1, t2, t3, t4, t5, t6, t7, t8, t9, pd1, pd2, pd3, pd4, pd5, pd6, pd7, pd8, pd9, cr1, cr2, cr3, cr4, cr5, cr6, cr7, cr8, cr9, gp1, gp2, gp3, gp4, gp5, gp6, gp7, gp8, gp9, spdate, tc, tg, sgpa, acy, attemptdate)  values('"+regno+"','"+name+"','"+fname+"','"+exs[0]+"','"+exs[1]+"','"+exs[2]+"','"+exs[3]+"','"+exs[4]+"','"+exs[5]+"','"+exs[6]+"','"+exs[7]+"','"+exs[8]+"','"+ins[0]+"','"+ins[1]+"','"+ins[2]+"','"+ins[3]+"','"+ins[4]+"','"+ins[5]+"','"+ins[6]+"','"+ins[7]+"','"+ins[8]+"','"+Integer.toString(tot[0])+"','"+Integer.toString(tot[1])+"','"+Integer.toString(tot[2])+"','"+Integer.toString(tot[3])+"','"+Integer.toString(tot[4])+"','"+Integer.toString(tot[5])+"','"+Integer.toString(tot[6])+"','"+Integer.toString(tot[7])+"','"+Integer.toString(tot[8])+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+pdate[6]+"','"+pdate[7]+"','"+pdate[8]+"','"+Integer.toString(credits[0])+"','"+Integer.toString(credits[1])+"','"+Integer.toString(credits[2])+"','"+Integer.toString(credits[3])+"','"+Integer.toString(credits[4])+"','"+Integer.toString(credits[5])+"','"+Integer.toString(credits[6])+"','"+Integer.toString(credits[7])+"','"+Integer.toString(credits[8])+"','"+Integer.toString(gp[0])+"','"+Integer.toString(gp[1])+"','"+Integer.toString(gp[2])+"','"+Integer.toString(gp[3])+"','"+Integer.toString(gp[4])+"','"+Integer.toString(gp[5])+"','"+Integer.toString(gp[6])+"','"+Integer.toString(gp[7])+"','"+Integer.toString(gp[8])+"','"+spdate+"','"+Integer.toString(tc)+"','"+Integer.toString(tgp)+"','"+Double.toString(sgpa)+"','"+acy+"','"+semdate+"')";
		
                            stmt1.executeUpdate(query);
                            } catch (Exception ex) {
                                Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
			elig=false;
			tc=0;
			tgp=0;
		}
		}
                out.println("<html><head></head><body><h3><center>");
                out.println(" your data has been successfully inserted");
                out.println("</center></h3></body></html>");
               
    }	
}
