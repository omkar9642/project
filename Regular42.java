
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Regular42 extends HttpServlet {
        public static int[] credits={0,0,0,0,0,0};
	public static double sgpa=0.0;
	public static int[] gp={0,0,0,0,0,0};
	public static String regno,en,semdate="",acy="",spdate="",name="",fname="";
	public static int ex[]={0,0,0,0,0,0};
	public static int tot[]={0,0,0,0,0,0};
	public static String exs[]={"","","","","",""};
	public static String ins[]={"","","","","",""};
	public static int in[]={0,0,0,0,0,0};
	public static int slno;
	public static String semtable="s",batch="",creditstable="cr";
	public static int sem=0;
	public static boolean pof=false;
	public static String branch[]={"BT","CE","CH","CS","EC","EE","EI","IT","ME"};
	public static String pdate[]={"","","","","",""};
	public static int pass[]={0,0,0,0,0,0};
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
			con1= DriverManager.getConnection("Jdbc:Odbc:db","","");
                        stmt1 = con1.createStatement();
                
            }catch(Exception e7){}
         	for(String b:branch)  
		for(int y=8;y<=13;y++)
		for(int i=401;i<=650;i++){
			regno="Y"+y+"A"+b+""+i;
			//regno="Y10ABT413";
                        
                        try{
			rs=stmt.executeQuery("select * from [Sheet1$] where REGDNO='"+regno+"'");
		while(rs.next()){
			elig=true;
			name=rs.getString(2);
			
			for(int j=3;j<=8;j++)
				exs[j-3]=rs.getString(j);
			slno=rs.getInt("SLNO");
			slno+=1;
			for(int j=0;j<=5;j++){
				if(exs[j].equals("A"))
					ex[j]=-1;
				else if(exs[j].equals("_")||exs[j].equals("-"))
					ex[j]=-2;
				else
					ex[j]=new Integer(exs[j]);
			}
			rs=stmt.executeQuery("select * from [Sheet1$] where SLNO="+slno);
			while(rs.next()){
				fname=rs.getString(2);
				for(int j=3;j<=8;j++)
					ins[j-3]=rs.getString(j);
				for(int j=0;j<=5;j++){
					if(ins[j].equals("A"))
						in[j]=-1;
					else
						in[j]=new Integer(ins[j]);
				}
			}
		}
                
               /* rs=stmt.executeQuery("select * from [regrv$] where REGDNO='"+regno+"'");
		while(rs.next()){
			String tcode="",acode=b+" "+42,acode1=b+""+42;
			tcode=rs.getString("CODE");
			for(int j=0;j<4;j++){
				if(tcode.equals(acode+(j+1))||tcode.equals("42"+(j+1))||tcode.equals(acode1+(j+1))){
					String tex,tin;
					tex=rs.getString("External");
					//tin=rs.getString("Internal");
					if(!(tex.equals("-")||tex.equals(" ")||tex.equals(""))){
						exs[j]=tex;
						//ins[j]=tin;
						//System.out.println(exs[j]+""+ins[j]);
						ex[j]=Integer.parseInt(exs[j]);
						//in[i]=Integer.parseInt(ins[j]);
					}
				}
			}
		}*/
                
                
                rs=stmt1.executeQuery("select * from cr"+sem+" where BRANCH='"+b+"'");
		while(rs.next()){
			for(int l=0;l<6;l++){
				credits[l]=Integer.parseInt(rs.getString(l+2));
                                
			}		
		}
		rs.close();
                for(int m=0;m<6;m++){
			if(ex[m]>=24&&(ex[m]+in[m])>=90&&(ex[m]+in[m])<=100)
				gp[m]=10;
			else if(ex[m]>=24&&(ex[m]+in[m])>=80)
				gp[m]=9;
			else if(ex[m]>=24&&(ex[m]+in[m])>=70)
				gp[m]=8;
			else if(ex[m]>=24&&(ex[m]+in[m])>=60)
				gp[m]=7;
			else if(ex[m]>=24&&(ex[m]+in[m])>=50)
				gp[m]=6;
			else if(ex[m]>=24&&(ex[m]+in[m])>=40)
				gp[m]=5;
			else 
				gp[m]=0;
		}	
		if(ex[4]>=50&&(ex[4]+in[4])>=135&&(ex[4]+in[4])<=150)
			gp[4]=10;
		else if(ex[4]>=50&&(ex[4]+in[4])>=120)
			gp[4]=9;
		else if(ex[4]>=50&&(ex[4]+in[4])>=105)
			gp[4]=8;
		else if(ex[4]>=50&&(ex[4]+in[4])>=90)
			gp[4]=7;
		else if(ex[4]>=50&&(ex[4]+in[4])>=75)
			gp[4]=6;
		else if(ex[4]>=50&&(ex[4]+in[4])>=60)
			gp[4]=5;
		else 
			gp[4]=0;
                sgpa=0.0;
		double tn=0.0,td=0.0;
		for(int n=0;n<6;n++){
			tn+=(double)(credits[n]*gp[n]);
			td+=(double)credits[n];
			sgpa=tn/td;
		}
                        }catch(Exception e12){}
			
		pof=false;
		
		for(int k=0;k<4;k++){ 
			if(ex[k]==-2||ex[k]==-1||(ex[k]<24||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		for(int k=4;k<5;k++){
			if(ex[k]==-2||ex[k]==-1||(ex[k]<50||(ex[k]+in[k])<60)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		for(int k=5;k<6;k++){
			if(ex[k]==-2||ex[k]==-1||(ex[k]<30||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		
		for(int o=0;o<6;o++)
			if(pass[o]==1){
				pof=true;
			}
			else{
				pof=false;
				
			}

                if(pof){
				spdate=semdate;
			}
			else
				spdate="-";
			for(int j=0;j<6;j++){
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
				String query="insert into "+ semtable+"(regdno, name, fname, e1, e2, e3, e4, e5, e6, i1, i2, i3, i4, i5, i6, t1, t2, t3, t4, t5, t6, pd1, pd2, pd3, pd4, pd5, pd6, cr1, cr2, cr3, cr4, cr5, cr6, gp1, gp2, gp3, gp4, gp5, gp6, spdate, tc, tg, sgpa, acy, attemptdate)  values('"+regno+"','"+name+"','"+fname+"','"+exs[0]+"','"+exs[1]+"','"+exs[2]+"','"+exs[3]+"','"+exs[4]+"','"+exs[5]+"','"+ins[0]+"','"+ins[1]+"','"+ins[2]+"','"+ins[3]+"','"+ins[4]+"','"+ins[5]+"','"+Integer.toString(tot[0])+"','"+Integer.toString(tot[1])+"','"+Integer.toString(tot[2])+"','"+Integer.toString(tot[3])+"','"+Integer.toString(tot[4])+"','"+Integer.toString(tot[5])+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+Integer.toString(credits[0])+"','"+Integer.toString(credits[1])+"','"+Integer.toString(credits[2])+"','"+Integer.toString(credits[3])+"','"+Integer.toString(credits[4])+"','"+Integer.toString(credits[5])+"','"+Integer.toString(gp[0])+"','"+Integer.toString(gp[1])+"','"+Integer.toString(gp[2])+"','"+Integer.toString(gp[3])+"','"+Integer.toString(gp[4])+"','"+Integer.toString(gp[5])+"','"+spdate+"','"+Integer.toString(tc)+"','"+Integer.toString(tgp)+"','"+Double.toString(sgpa)+"','"+acy+"','"+semdate+"')";
		
		stmt1.executeUpdate(query);
		
		//stmt1.executeUpdate("insert into s"+sem+" values('"+regno+"','"+name+"','"+fname+"','"+ex[0]+"','"+ex[1]+"','"+ex[2]+"','"+ex[3]+"','"+ex[4]+"','"+ex[5]+"','"+ex[6]+"','"+ex[7]+"','"+ex[8]+"','"+in[0]+"','"+in[1]+"','"+in[2]+"','"+in[3]+"','"+in[4]+"','"+in[5]+"','"+in[6]+"','"+in[7]+"','"+in[8]+"','"+tot[0]+"','"+tot[1]+"','"+tot[2]+"','"+tot[3]+"','"+"','"+tot[4]+"','"+tot[5]+"','"+tot[6]+"','"+tot[7]+"','"+tot[8]+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+pdate[6]+"','"+pdate[7]+"','"+pdate[8]+"','"+credits[0]+"','"+credits[1]+"','"+credits[2]+"','"+credits[3]+"','"+credits[4]+"','"+credits[5]+"','"+credits[6]+"','"+credits[7]+"','"+credits[8]+"','"+gp[0]+"','"+gp[1]+"','"+gp[2]+"','"+gp[3]+"','"+gp[4]+"','"+gp[5]+"','"+gp[6]+"','"+gp[7]+"','"+gp[8]+"','"+spdate+"','"+tc+"','"+tgp+"','"+sgpa+"','"+acy+"','"+semdate+"')");
	
			}catch(Exception e11){}
			}
			elig=false;
			tc=0;
			tgp=0;
		}
		for(String b:branch)  
		for(int y=8;y<=14;y++)
		for(int i=401;i<=650;i++){
			regno="L"+y+"A"+b+""+i;
			//regno="Y10ABT413";
                        try{
			rs=stmt.executeQuery("select * from [Sheet1$] where REGDNO='"+regno+"'");
		while(rs.next()){
			elig=true;
			name=rs.getString(2);
			System.out.println(regno);
			for(int j=3;j<=8;j++)
				exs[j-3]=rs.getString(j);
			slno=rs.getInt("SLNO");
			slno+=1;
			for(int j=0;j<=5;j++){
				if(exs[j].equals("A"))
					ex[j]=-1;
				else if(exs[j].equals("_")||exs[j].equals("-"))
					ex[j]=-2;
				else
					ex[j]=new Integer(exs[j]);
			}
			rs=stmt.executeQuery("select * from [Sheet1$] where SLNO="+slno);
			while(rs.next()){
				fname=rs.getString(2);
				for(int j=3;j<=8;j++)
					ins[j-3]=rs.getString(j);
				for(int j=0;j<=5;j++){
					if(ins[j].equals("A"))
						in[j]=-1;
					else
						in[j]=new Integer(ins[j]);
				}
			}
		}
                /*
                rs=stmt.executeQuery("select * from [regrv$] where REGDNO='"+regno+"'");
		while(rs.next()){
			String tcode="",acode=b+" "+42,acode1=b+""+42;
			tcode=rs.getString("CODE");
			for(int j=0;j<4;j++){
				if(tcode.equals(acode+(j+1))||tcode.equals("42"+(j+1))||tcode.equals(acode1+(j+1))){
					String tex,tin;
					tex=rs.getString("External");
					//tin=rs.getString("Internal");
					if(!(tex.equals("-")||tex.equals(" ")||tex.equals(""))){
						exs[j]=tex;
						//ins[j]=tin;
						System.out.println(exs[j]+""+ins[j]);
						ex[j]=Integer.parseInt(exs[j]);
						//in[i]=Integer.parseInt(ins[j]);
					}
				}
			}
		}*/
                rs=stmt1.executeQuery("select * from cr"+sem+" where BRANCH='"+b+"'");
		while(rs.next()){
			for(int l=0;l<6;l++){
				credits[l]=Integer.parseInt(rs.getString(l+2));
			}		
		}
		rs.close();
                for(int m=0;m<6;m++){
			if(ex[m]>=24&&(ex[m]+in[m])>=90&&(ex[m]+in[m])<=100)
				gp[m]=10;
			else if(ex[m]>=24&&(ex[m]+in[m])>=80)
				gp[m]=9;
			else if(ex[m]>=24&&(ex[m]+in[m])>=70)
				gp[m]=8;
			else if(ex[m]>=24&&(ex[m]+in[m])>=60)
				gp[m]=7;
			else if(ex[m]>=24&&(ex[m]+in[m])>=50)
				gp[m]=6;
			else if(ex[m]>=24&&(ex[m]+in[m])>=40)
				gp[m]=5;
			else 
				gp[m]=0;
		}	
		if(ex[4]>=50&&(ex[4]+in[4])>=135&&(ex[4]+in[4])<=150)
			gp[4]=10;
		else if(ex[4]>=50&&(ex[4]+in[4])>=120)
			gp[4]=9;
		else if(ex[4]>=50&&(ex[4]+in[4])>=105)
			gp[4]=8;
		else if(ex[4]>=50&&(ex[4]+in[4])>=90)
			gp[4]=7;
		else if(ex[4]>=50&&(ex[4]+in[4])>=75)
			gp[4]=6;
		else if(ex[4]>=50&&(ex[4]+in[4])>=60)
			gp[4]=5;
		else 
			gp[4]=0;
                sgpa=0.0;
		double tn=0.0,td=0.0;
		for(int n=0;n<6;n++){
			tn+=(double)(credits[n]*gp[n]);
			td+=(double)credits[n];
			sgpa=tn/td;
		}
                }catch(Exception e13){}
		pof=false;
		
		for(int k=0;k<4;k++){ 
			if(ex[k]==-2||ex[k]==-1||(ex[k]<24||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		for(int k=4;k<5;k++){
			if(ex[k]==-2||ex[k]==-1||(ex[k]<50||(ex[k]+in[k])<60)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		for(int k=5;k<6;k++){
			if(ex[k]==-2||ex[k]==-1||(ex[k]<30||(ex[k]+in[k])<40)){
				pass[k]=0;
				pdate[k]="-";
			}
			else{
				pass[k]=1;
				pdate[k]=semdate;
			}
		}
		
		for(int o=0;o<6;o++)
			if(pass[o]==1){
				pof=true;
			}
			else{
				pof=false;
				
			}
			
			if(pof){
				spdate=semdate;
			}
			else
				spdate="-";
			for(int j=0;j<6;j++){
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
				String query="insert into "+ semtable+"(regdno, name, fname, e1, e2, e3, e4, e5, e6, i1, i2, i3, i4, i5, i6, t1, t2, t3, t4, t5, t6, pd1, pd2, pd3, pd4, pd5, pd6, cr1, cr2, cr3, cr4, cr5, cr6, gp1, gp2, gp3, gp4, gp5, gp6, spdate, tc, tg, sgpa, acy, attemptdate)  values('"+regno+"','"+name+"','"+fname+"','"+exs[0]+"','"+exs[1]+"','"+exs[2]+"','"+exs[3]+"','"+exs[4]+"','"+exs[5]+"','"+ins[0]+"','"+ins[1]+"','"+ins[2]+"','"+ins[3]+"','"+ins[4]+"','"+ins[5]+"','"+Integer.toString(tot[0])+"','"+Integer.toString(tot[1])+"','"+Integer.toString(tot[2])+"','"+Integer.toString(tot[3])+"','"+Integer.toString(tot[4])+"','"+Integer.toString(tot[5])+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+Integer.toString(credits[0])+"','"+Integer.toString(credits[1])+"','"+Integer.toString(credits[2])+"','"+Integer.toString(credits[3])+"','"+Integer.toString(credits[4])+"','"+Integer.toString(credits[5])+"','"+Integer.toString(gp[0])+"','"+Integer.toString(gp[1])+"','"+Integer.toString(gp[2])+"','"+Integer.toString(gp[3])+"','"+Integer.toString(gp[4])+"','"+Integer.toString(gp[5])+"','"+spdate+"','"+Integer.toString(tc)+"','"+Integer.toString(tgp)+"','"+Double.toString(sgpa)+"','"+acy+"','"+semdate+"')";
		
		stmt1.executeUpdate(query);
		
		//stmt1.executeUpdate("insert into s"+sem+" values('"+regno+"','"+name+"','"+fname+"','"+ex[0]+"','"+ex[1]+"','"+ex[2]+"','"+ex[3]+"','"+ex[4]+"','"+ex[5]+"','"+ex[6]+"','"+ex[7]+"','"+ex[8]+"','"+in[0]+"','"+in[1]+"','"+in[2]+"','"+in[3]+"','"+in[4]+"','"+in[5]+"','"+in[6]+"','"+in[7]+"','"+in[8]+"','"+tot[0]+"','"+tot[1]+"','"+tot[2]+"','"+tot[3]+"','"+"','"+tot[4]+"','"+tot[5]+"','"+tot[6]+"','"+tot[7]+"','"+tot[8]+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+pdate[6]+"','"+pdate[7]+"','"+pdate[8]+"','"+credits[0]+"','"+credits[1]+"','"+credits[2]+"','"+credits[3]+"','"+credits[4]+"','"+credits[5]+"','"+credits[6]+"','"+credits[7]+"','"+credits[8]+"','"+gp[0]+"','"+gp[1]+"','"+gp[2]+"','"+gp[3]+"','"+gp[4]+"','"+gp[5]+"','"+gp[6]+"','"+gp[7]+"','"+gp[8]+"','"+spdate+"','"+tc+"','"+tgp+"','"+sgpa+"','"+acy+"','"+semdate+"')");
	
			}catch(Exception e11){}
                        }
			elig=false;
			tc=0;
			tgp=0;
		}
out.println("<html><head></head><body><h3><center>");
                out.println(" your data has been successfully inserted");
                out.println("</center></h3></body></html>");	
    }
    
	}
	
       


