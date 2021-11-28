

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Y14Supply extends HttpServlet {

        public static int[] credits={0,0,0,0,0,0,0,0,0};
	public static double sgpa=0.0;
	public static int[] gp={0,0,0,0,0,0,0,0,0};
	public static String regno,en,semdate="",acy="",spdate="",name="",fname="";
	public static int ex[]={0,0,0,0,0,0,0,0,0};
	public static int tot[]={0,0,0,0,0,0,0,0,0};
	public static String exs[]={"","","","","","","","",""};
	public static String ins[]={"","","","","","","","",""};
	public static int in[]={0,0,0,0,0,0,0,0,0};
	public static int attempt;
	public static String semtable="s",batch="",creditstable="cr";
	public static int sem=0;
	public static boolean pof=false;
	public static String branch[]={"CE","CH","CS","EC","EE","EI","IT","ME"};
	public static String pdate[]={"","","","","","","","",""};
	public static int pass[]={0,0,0,0,0,0,0,0,0};
	public static Connection con,con1;
	public static ResultSet rs;
	public static Statement stmt,stmt1;
	public static int tc=0,tgp=0;
	public static boolean elig=false;
        public String fileName;
    

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
                response.setContentType("text/html");
		PrintWriter out=response.getWriter();
        attempt=Integer.parseInt(request.getParameter("attempt"));
         sem=Integer.parseInt(request.getParameter("sem"));
         semdate=request.getParameter("semdate");
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
		for(int y=12;y<=14;y++)
		for(int i=401;i<=650;i++){
			regno="Y"+y+"A"+b+""+i;
			//regno="Y14AME501";
                        try{
			boolean elig1=false;
		rs=stmt1.executeQuery("select * from "+semtable+" where REGDNO='"+regno+"'");
		while(rs.next()){
			elig1=true;
			for(int j=0;j<9;j++){
				exs[j]=rs.getString("e"+(j+1));
			}
			for(int j=0;j<9;j++){
				ins[j]=rs.getString("i"+(j+1));
				if(!(ins[j].equals("-")||ins[j].equals("_")||ins[j].equals("A")))
					in[j]=Integer.parseInt(ins[j]);
			}
			for(int j=0;j<9;j++){
				pdate[j]=rs.getString("pd"+(j+1));
			}
		}
		if(elig1){
			rs=stmt.executeQuery("select * from [sup"+attempt+"$] where REGDNO='"+regno+"'");
			while(rs.next()){
				elig=true;
				String tex;
				System.out.println(regno);
				for(int j=3;j<=11;j++){
					tex=rs.getString(j);
					if(!(tex.equals("-")||tex.equals("_")))
						exs[j-3]=tex;
				}
				for(int j=0;j<=8;j++){
					if(exs[j].equals("A"))
						ex[j]=-1;
					else if(exs[j].equals("_")||exs[j].equals("-"))
						ex[j]=-2;
					else
						ex[j]=new Integer(exs[j]);
				}
			}
		}
			//reval("ME",sem,attempt);
			//readcredits("ME");
			if(elig){
			rs=stmt.executeQuery("select * from [rv"+sem+"$] where REGDNO='"+regno+"'");
			while(rs.next()){
				String tcode="",acode2=""+sem,acode=b+" "+sem,acode1=b+""+sem;
				tcode=rs.getString("CODE");
				for(int o=0;o<6;o++){
					if(tcode.equals(acode2+(o+1))||tcode.equals(acode+(o+1))||tcode.equals(sem+""+(o+1))||tcode.equals(acode1+(o+1))){
						String tex,tin;
						tex=rs.getString("External");
						if(!(tex.equals("-")||tex.equals(" ")||tex.equals(""))){
							exs[o]=tex;
							System.out.println(exs[o]+""+ins[o]);
							ex[o]=Integer.parseInt(exs[o]);
						}
					}
				}
			}
		}
			rs=stmt1.executeQuery("select * from y14cr"+sem+" where BRANCH='"+b+"'");
		while(rs.next()){
			for(int p=0;p<9;p++){
				credits[p]=Integer.parseInt(rs.getString(p+2));
			}		
		}
		rs.close();
			for(int r=0;r<9;r++){
			if(ex[r]>=24&&(ex[r]+in[r])>=90)
				gp[r]=10;
			else if(ex[r]>=24&&(ex[r]+in[r])>=80)
				gp[r]=9;
			else if(ex[r]>=24&&(ex[r]+in[r])>=70)
				gp[r]=8;
			else if(ex[r]>=24&&(ex[r]+in[r])>=60)
				gp[r]=7;
			else if(ex[r]>=24&&(ex[r]+in[r])>=50)
				gp[r]=6;
			else if(ex[r]>=24&&(ex[r]+in[r])>=40)
				gp[r]=5;
			else
				gp[r]=0;
		}
			sgpa=0.0;
		double tn=0.0,td=0.0;
		for(int u=0;u<9;u++){
			tn+=(double)(credits[u]*gp[u]);
			td+=(double)credits[u];
			sgpa=tn/td;
		}
                        }catch(Exception e3){}
                        
                        pof=true;
		
		for(int k=0;k<6;k++){ 
			if(ex[k]==-2||ex[k]==-1||ex[k]<24||(ex[k]+in[k])<40){
				pass[k]=0;
				pdate[k]="-";
			}
			else if(pdate[k].equals("-")){
				pass[k]=1;
				pdate[k]=semdate;
			}
			else
				pass[k]=1;
		}
		for(int k=6;k<9;k++){
			if(ex[k]==-2||ex[k]==-1||(ex[k]<30||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else if(pdate[k].equals("-")){
				pass[k]=1;
				pdate[k]=semdate;
			}
			else
				pass[k]=1;
		}
		
		for(int n=0;n<9;n++){
			if(pass[n]==1){
				pof=true;
			}
			else{
				pof=false;
				
			}
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
                            try{
				String query="update "+ semtable+" set e1='"+exs[0]+"',e2='"+exs[1]+"',e3='"+exs[2]+"',e4='"+exs[3]+"',e5='"+exs[4]+"',e6='"+exs[5]+"',e7='"+exs[6]+"',e8='"+exs[7]+"',e9='"+exs[8]+"',i1='"+ins[0]+"',i2='"+ins[1]+"',i3='"+ins[2]+"',i4='"+ins[3]+"',i5='"+ins[4]+"',i6='"+ins[5]+"',i7='"+ins[6]+"',i8='"+ins[7]+"',i9='"+ins[8]+"',t1='"+Integer.toString(tot[0])+"',t2='"+Integer.toString(tot[1])+"',t3='"+Integer.toString(tot[2])+"',t4='"+Integer.toString(tot[3])+"',t5='"+Integer.toString(tot[4])+"',t6='"+Integer.toString(tot[5])+"',t7='"+Integer.toString(tot[6])+"',t8='"+Integer.toString(tot[7])+"',t9='"+Integer.toString(tot[8])+"',pd1='"+pdate[0]+"',pd2='"+pdate[1]+"',pd3='"+pdate[2]+"',pd4='"+pdate[3]+"',pd5='"+pdate[4]+"',pd6='"+pdate[5]+"',pd7='"+pdate[6]+"',pd8='"+pdate[7]+"',pd9='"+pdate[8]+"',gp1='"+Integer.toString(gp[0])+"',gp2='"+Integer.toString(gp[1])+"',gp3='"+Integer.toString(gp[2])+"',gp4='"+Integer.toString(gp[3])+"',gp5='"+Integer.toString(gp[4])+"',gp6='"+Integer.toString(gp[5])+"',gp7='"+Integer.toString(gp[6])+"',gp8='"+Integer.toString(gp[7])+"',gp9='"+Integer.toString(gp[8])+"',spdate='"+spdate+"',tg='"+Integer.toString(tgp)+"',sgpa='"+Double.toString(sgpa)+"' where regdno='"+regno+"'";
		
		stmt1.executeUpdate(query);
		
		//stmt1.executeUpdate("insert into s"+sem+" values('"+regno+"','"+name+"','"+fname+"','"+ex[0]+"','"+ex[1]+"','"+ex[2]+"','"+ex[3]+"','"+ex[4]+"','"+ex[5]+"','"+ex[6]+"','"+ex[7]+"','"+ex[8]+"','"+in[0]+"','"+in[1]+"','"+in[2]+"','"+in[3]+"','"+in[4]+"','"+in[5]+"','"+in[6]+"','"+in[7]+"','"+in[8]+"','"+tot[0]+"','"+tot[1]+"','"+tot[2]+"','"+tot[3]+"','"+"','"+tot[4]+"','"+tot[5]+"','"+tot[6]+"','"+tot[7]+"','"+tot[8]+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+pdate[6]+"','"+pdate[7]+"','"+pdate[8]+"','"+credits[0]+"','"+credits[1]+"','"+credits[2]+"','"+credits[3]+"','"+credits[4]+"','"+credits[5]+"','"+credits[6]+"','"+credits[7]+"','"+credits[8]+"','"+gp[0]+"','"+gp[1]+"','"+gp[2]+"','"+gp[3]+"','"+gp[4]+"','"+gp[5]+"','"+gp[6]+"','"+gp[7]+"','"+gp[8]+"','"+spdate+"','"+tc+"','"+tgp+"','"+sgpa+"','"+acy+"','"+semdate+"')");
	
                            }catch(Exception e5){}
			}
			elig=false;
			tc=0;
			tgp=0;
		}
		if(sem!=11&&sem!=12&&sem!=21){
		for(String b:branch)  
		for(int y=13;y<=15;y++)
		for(int i=401;i<=650;i++){
			regno="L"+y+"A"+b+""+i;
			//regno="Y10ABT413";
                        try{
			boolean elig1=false;
		rs=stmt1.executeQuery("select * from "+semtable+" where REGDNO='"+regno+"'");
		while(rs.next()){
			elig1=true;
			for(int j=0;j<9;j++){
				exs[j]=rs.getString("e"+(j+1));
			}
			for(int j=0;j<9;j++){
				ins[j]=rs.getString("i"+(j+1));
				if(!(ins[j].equals("-")||ins[j].equals("_")||ins[j].equals("A")))
					in[j]=Integer.parseInt(ins[j]);
			}
			for(int j=0;j<9;j++){
				pdate[j]=rs.getString("pd"+(j+1));
			}
		}
		if(elig1){
			rs=stmt.executeQuery("select * from [sup"+attempt+"$] where REGDNO='"+regno+"'");
			while(rs.next()){
				elig=true;
				String tex;
				
				for(int j=3;j<=11;j++){
					tex=rs.getString(j);
					if(!(tex.equals("-")||tex.equals("_")))
						exs[j-3]=tex;
				}
				for(int j=0;j<=8;j++){
					if(exs[j].equals("A"))
						ex[j]=-1;
					else if(exs[j].equals("_")||exs[j].equals("-"))
						ex[j]=-2;
					else
						ex[j]=new Integer(exs[j]);
				}
			}
		}
			if(elig){
			rs=stmt.executeQuery("select * from [rv"+sem+"$] where REGDNO='"+regno+"'");
			while(rs.next()){
				String tcode="",acode2=""+sem,acode=b+" "+sem,acode1=b+""+sem;
				tcode=rs.getString("CODE");
				for(int o=0;o<6;o++){
					if(tcode.equals(acode2+(o+1))||tcode.equals(acode+(o+1))||tcode.equals(sem+""+(o+1))||tcode.equals(acode1+(o+1))){
						String tex,tin;
						tex=rs.getString("External");
						if(!(tex.equals("-")||tex.equals(" ")||tex.equals(""))){
							exs[o]=tex;
							System.out.println(exs[o]+""+ins[o]);
							ex[o]=Integer.parseInt(exs[o]);
						}
					}
				}
			}
		}
			rs=stmt1.executeQuery("select * from y14cr"+sem+" where BRANCH='"+b+"'");
		while(rs.next()){
			for(int p=0;p<9;p++){
				credits[p]=Integer.parseInt(rs.getString(p+2));
			}		
		}
		rs.close();
			for(int r=0;r<9;r++){
			if(ex[r]>=24&&(ex[r]+in[r])>=90)
				gp[r]=10;
			else if(ex[r]>=24&&(ex[r]+in[r])>=80)
				gp[r]=9;
			else if(ex[r]>=24&&(ex[r]+in[r])>=70)
				gp[r]=8;
			else if(ex[r]>=24&&(ex[r]+in[r])>=60)
				gp[r]=7;
			else if(ex[r]>=24&&(ex[r]+in[r])>=50)
				gp[r]=6;
			else if(ex[r]>=24&&(ex[r]+in[r])>=40)
				gp[r]=5;
			else
				gp[r]=0;
		}
			sgpa=0.0;
		double tn=0.0,td=0.0;
		for(int u=0;u<9;u++){
			tn+=(double)(credits[u]*gp[u]);
			td+=(double)credits[u];
			sgpa=tn/td;
		}
                        }catch(Exception e4){}
                        
                        pof=true;
		
		for(int k=0;k<6;k++){ 
			if(ex[k]==-2||ex[k]==-1||ex[k]<24||(ex[k]+in[k])<40){
				pass[k]=0;
				pdate[k]="-";
			}
			else if(pdate[k].equals("-")){
				pass[k]=1;
				pdate[k]=semdate;
			}
			else
				pass[k]=1;
		}
		for(int k=6;k<9;k++){
			if(ex[k]==-2||ex[k]==-1||(ex[k]<30||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else if(pdate[k].equals("-")){
				pass[k]=1;
				pdate[k]=semdate;
			}
			else
				pass[k]=1;
		}
		
		for(int n=0;n<9;n++){
			if(pass[n]==1){
				pof=true;
			}
			else{
				pof=false;
				
			}
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
                            try{
				String query="update "+ semtable+" set e1='"+exs[0]+"',e2='"+exs[1]+"',e3='"+exs[2]+"',e4='"+exs[3]+"',e5='"+exs[4]+"',e6='"+exs[5]+"',e7='"+exs[6]+"',e8='"+exs[7]+"',e9='"+exs[8]+"',i1='"+ins[0]+"',i2='"+ins[1]+"',i3='"+ins[2]+"',i4='"+ins[3]+"',i5='"+ins[4]+"',i6='"+ins[5]+"',i7='"+ins[6]+"',i8='"+ins[7]+"',i9='"+ins[8]+"',t1='"+Integer.toString(tot[0])+"',t2='"+Integer.toString(tot[1])+"',t3='"+Integer.toString(tot[2])+"',t4='"+Integer.toString(tot[3])+"',t5='"+Integer.toString(tot[4])+"',t6='"+Integer.toString(tot[5])+"',t7='"+Integer.toString(tot[6])+"',t8='"+Integer.toString(tot[7])+"',t9='"+Integer.toString(tot[8])+"',pd1='"+pdate[0]+"',pd2='"+pdate[1]+"',pd3='"+pdate[2]+"',pd4='"+pdate[3]+"',pd5='"+pdate[4]+"',pd6='"+pdate[5]+"',pd7='"+pdate[6]+"',pd8='"+pdate[7]+"',pd9='"+pdate[8]+"',gp1='"+Integer.toString(gp[0])+"',gp2='"+Integer.toString(gp[1])+"',gp3='"+Integer.toString(gp[2])+"',gp4='"+Integer.toString(gp[3])+"',gp5='"+Integer.toString(gp[4])+"',gp6='"+Integer.toString(gp[5])+"',gp7='"+Integer.toString(gp[6])+"',gp8='"+Integer.toString(gp[7])+"',gp9='"+Integer.toString(gp[8])+"',spdate='"+spdate+"',tg='"+Integer.toString(tgp)+"',sgpa='"+Double.toString(sgpa)+"' where regdno='"+regno+"'";
		
		stmt1.executeUpdate(query);
		
		//stmt1.executeUpdate("insert into s"+sem+" values('"+regno+"','"+name+"','"+fname+"','"+ex[0]+"','"+ex[1]+"','"+ex[2]+"','"+ex[3]+"','"+ex[4]+"','"+ex[5]+"','"+ex[6]+"','"+ex[7]+"','"+ex[8]+"','"+in[0]+"','"+in[1]+"','"+in[2]+"','"+in[3]+"','"+in[4]+"','"+in[5]+"','"+in[6]+"','"+in[7]+"','"+in[8]+"','"+tot[0]+"','"+tot[1]+"','"+tot[2]+"','"+tot[3]+"','"+"','"+tot[4]+"','"+tot[5]+"','"+tot[6]+"','"+tot[7]+"','"+tot[8]+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+pdate[6]+"','"+pdate[7]+"','"+pdate[8]+"','"+credits[0]+"','"+credits[1]+"','"+credits[2]+"','"+credits[3]+"','"+credits[4]+"','"+credits[5]+"','"+credits[6]+"','"+credits[7]+"','"+credits[8]+"','"+gp[0]+"','"+gp[1]+"','"+gp[2]+"','"+gp[3]+"','"+gp[4]+"','"+gp[5]+"','"+gp[6]+"','"+gp[7]+"','"+gp[8]+"','"+spdate+"','"+tc+"','"+tgp+"','"+sgpa+"','"+acy+"','"+semdate+"')");
	
                            }catch(Exception e5){}
			}
			elig=false;
			tc=0;
			tgp=0;
		}
		}
         
         
                 out.println("<html><head></head><body><h3><center>");
                out.println(" your data has been successfully updated");
                out.println("</center></h3></body></html>");
         
         
         
    }
}
