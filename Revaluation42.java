
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Revaluation42 extends HttpServlet {

    static int[] credits={0,0,0,0,0,0};
	static double sgpa=0.0;
	static int[] gp={0,0,0,0,0,0};
	static String regno,en,semdate="",acy="",spdate="",name="",fname="";
	static int ex[]={0,0,0,0,0,0};
	static int tot[]={0,0,0,0,0,0};
	static String exs[]={"","","","","",""};
	static String ins[]={"","","","","",""};
	static int in[]={0,0,0,0,0,0};
	static int attempt;
	static String semtable="s",batch="",creditstable="cr";
	static int sem=0;
	static boolean pof=false;
	static String branch[]={"BT","CE","CH","CS","EC","EE","EI","IT","ME"};
	static String pdate[]={"","","","","",""};
	static int pass[]={0,0,0,0,0,0};
	static Connection con,con1;
	static ResultSet rs;
	static Statement stmt,stmt1;
	static int tc=0,tgp=0;
	static boolean elig=false;
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
			String url = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+fileName;
			System.out.println(url);
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection(url,"","");
		}
		catch(Exception e)
        {
            System.out.println("The  Exception is"+e);
			
        }
        try{
            stmt=con.createStatement();
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con1=DriverManager.getConnection("Jdbc:Odbc:db","","");
		stmt1=con1.createStatement();
        }catch(Exception e3){}
        for(String b:branch)  
		for(int y=8;y<=14;y++)
		for(int i=401;i<=650;i++){
			regno="Y"+y+"A"+b+""+i;
			//regno="Y10ABT413";
                        try{
			boolean elig1=false;
		rs=stmt1.executeQuery("select * from "+semtable+" where REGDNO='"+regno+"'");
		while(rs.next()){
			elig1=true;
			for(int j=0;j<6;j++){
				exs[j]=rs.getString("e"+(j+1));
				if(!(exs[j].equals("-")||exs[j].equals("_")||exs[j].equals("A")))
					ex[j]=Integer.parseInt(exs[j]);
			}
			for(int j=0;j<6;j++){
				ins[j]=rs.getString("i"+(j+1));
				if(!(ins[j].equals("-")||ins[j].equals("_")||ins[j].equals("A")))
					in[j]=Integer.parseInt(ins[j]);
			}
			for(int j=0;j<6;j++){
				pdate[j]=rs.getString("pd"+(j+1));
			}
		}
		if(elig1){
			rs=stmt.executeQuery("select * from [rv"+attempt+"$] where REGDNO='"+regno+"'");
		while(rs.next()){
			elig=true;
			String tcode="",acode=b+" "+sem,acode1=b+""+sem;
			tcode=rs.getString("CODE");
			for(int q=0;q<4;q++){
				if(tcode.equals(acode+(q+1))||tcode.equals(sem+""+(q+1))||tcode.equals(acode1+(q+1))){
					String tex,tin;
					tex=rs.getString("External");
					if(!(tex.equals("-")||tex.equals(" ")||tex.equals(""))){
						exs[q]=tex;
						System.out.println(exs[q]+""+ins[q]);
						ex[q]=Integer.parseInt(exs[i]);
					}
				}
			}
		}
		}
			//reval(b,sem,attempt);
			rs=stmt1.executeQuery("select * from cr"+sem+" where BRANCH='"+b+"'");
		while(rs.next()){
			for(int r=0;r<6;r++){
				credits[r]=Integer.parseInt(rs.getString(r+2));
			}		
		}
		rs.close();
			for(int p=0;p<6;p++){
			if(ex[p]>=24&&(ex[p]+in[p])>=90&&(ex[p]+in[p])<=100)
				gp[p]=10;
			else if(ex[p]>=24&&(ex[p]+in[p])>=80)
				gp[p]=9;
			else if(ex[p]>=24&&(ex[p]+in[p])>=70)
				gp[p]=8;
			else if(ex[p]>=24&&(ex[p]+in[p])>=60)
				gp[p]=7;
			else if(ex[p]>=24&&(ex[p]+in[p])>=50)
				gp[p]=6;
			else if(ex[p]>=24&&(ex[p]+in[p])>=40)
				gp[p]=5;
			else 
				gp[p]=0;
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
		for(int t=0;t<6;t++){
			tn+=(double)(credits[t]*gp[t]);
			td+=(double)credits[t];
			sgpa=tn/td;
		}
                        }catch(Exception e4){}
                        
                        pof=false;
		
		for(int k=0;k<4;k++){ 
			if(ex[k]==-2||ex[k]==-1||(ex[k]<24||(ex[k]+in[k])<40)){
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
		for(int k=4;k<5;k++){ 
			if(ex[k]==-2||ex[k]==-1||(ex[k]<50||(ex[k]+in[k])<60)){
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
		for(int k=5;k<6;k++){
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
		
		for(int n=0;n<6;n++)
			if(pass[n]==1){
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
				String query="update "+ semtable+" set e1='"+exs[0]+"',e2='"+exs[1]+"',e3='"+exs[2]+"',e4='"+exs[3]+"',e5='"+exs[4]+"',e6='"+exs[5]+"',i1='"+ins[0]+"',i2='"+ins[1]+"',i3='"+ins[2]+"',i4='"+ins[3]+"',i5='"+ins[4]+"',i6='"+ins[5]+"',t1='"+Integer.toString(tot[0])+"',t2='"+Integer.toString(tot[1])+"',t3='"+Integer.toString(tot[2])+"',t4='"+Integer.toString(tot[3])+"',t5='"+Integer.toString(tot[4])+"',t6='"+Integer.toString(tot[5])+"',pd1='"+pdate[0]+"',pd2='"+pdate[1]+"',pd3='"+pdate[2]+"',pd4='"+pdate[3]+"',pd5='"+pdate[4]+"',pd6='"+pdate[5]+"',gp1='"+Integer.toString(gp[0])+"',gp2='"+Integer.toString(gp[1])+"',gp3='"+Integer.toString(gp[2])+"',gp4='"+Integer.toString(gp[3])+"',gp5='"+Integer.toString(gp[4])+"',gp6='"+Integer.toString(gp[5])+"',spdate='"+spdate+"',tg='"+Integer.toString(tgp)+"',sgpa='"+Double.toString(sgpa)+"' where regdno='"+regno+"'";
		
		stmt1.executeUpdate(query);
		
		//stmt1.executeUpdate("insert into s"+sem+" values('"+regno+"','"+name+"','"+fname+"','"+ex[0]+"','"+ex[1]+"','"+ex[2]+"','"+ex[3]+"','"+ex[4]+"','"+ex[5]+"','"+ex[6]+"','"+ex[7]+"','"+ex[8]+"','"+in[0]+"','"+in[1]+"','"+in[2]+"','"+in[3]+"','"+in[4]+"','"+in[5]+"','"+in[6]+"','"+in[7]+"','"+in[8]+"','"+tot[0]+"','"+tot[1]+"','"+tot[2]+"','"+tot[3]+"','"+"','"+tot[4]+"','"+tot[5]+"','"+tot[6]+"','"+tot[7]+"','"+tot[8]+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+pdate[6]+"','"+pdate[7]+"','"+pdate[8]+"','"+credits[0]+"','"+credits[1]+"','"+credits[2]+"','"+credits[3]+"','"+credits[4]+"','"+credits[5]+"','"+credits[6]+"','"+credits[7]+"','"+credits[8]+"','"+gp[0]+"','"+gp[1]+"','"+gp[2]+"','"+gp[3]+"','"+gp[4]+"','"+gp[5]+"','"+gp[6]+"','"+gp[7]+"','"+gp[8]+"','"+spdate+"','"+tc+"','"+tgp+"','"+sgpa+"','"+acy+"','"+semdate+"')");
                            }catch(Exception e6){}
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
			boolean elig1=false;
		rs=stmt1.executeQuery("select * from "+semtable+" where REGDNO='"+regno+"'");
		while(rs.next()){
			elig1=true;
			for(int j=0;j<6;j++){
				exs[j]=rs.getString("e"+(j+1));
				if(!(exs[j].equals("-")||exs[j].equals("_")||exs[j].equals("A")))
					ex[j]=Integer.parseInt(exs[j]);
			}
			for(int j=0;j<6;j++){
				ins[j]=rs.getString("i"+(j+1));
				if(!(ins[j].equals("-")||ins[j].equals("_")||ins[j].equals("A")))
					in[j]=Integer.parseInt(ins[j]);
			}
			for(int j=0;j<6;j++){
				pdate[j]=rs.getString("pd"+(j+1));
			}
		}
		if(elig1){
			rs=stmt.executeQuery("select * from [rv"+attempt+"$] where REGDNO='"+regno+"'");
		while(rs.next()){
			elig=true;
			String tcode="",acode=b+" "+sem,acode1=b+""+sem;
			tcode=rs.getString("CODE");
			for(int q=0;q<4;q++){
				if(tcode.equals(acode+(q+1))||tcode.equals(sem+""+(q+1))||tcode.equals(acode1+(q+1))){
					String tex,tin;
					tex=rs.getString("External");
					if(!(tex.equals("-")||tex.equals(" ")||tex.equals(""))){
						exs[q]=tex;
						System.out.println(exs[q]+""+ins[q]);
						ex[q]=Integer.parseInt(exs[i]);
					}
				}
			}
		}
		}
			//reval(b,sem,attempt);
			rs=stmt1.executeQuery("select * from cr"+sem+" where BRANCH='"+b+"'");
		while(rs.next()){
			for(int r=0;r<6;r++){
				credits[r]=Integer.parseInt(rs.getString(r+2));
			}		
		}
		rs.close();
			for(int p=0;p<6;p++){
			if(ex[p]>=24&&(ex[p]+in[p])>=90&&(ex[p]+in[p])<=100)
				gp[p]=10;
			else if(ex[p]>=24&&(ex[p]+in[p])>=80)
				gp[p]=9;
			else if(ex[p]>=24&&(ex[p]+in[p])>=70)
				gp[p]=8;
			else if(ex[p]>=24&&(ex[p]+in[p])>=60)
				gp[p]=7;
			else if(ex[p]>=24&&(ex[p]+in[p])>=50)
				gp[p]=6;
			else if(ex[p]>=24&&(ex[p]+in[p])>=40)
				gp[p]=5;
			else 
				gp[p]=0;
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
		for(int t=0;t<6;t++){
			tn+=(double)(credits[t]*gp[t]);
			td+=(double)credits[t];
			sgpa=tn/td;
		}
                        }catch(Exception e5){}
                        
                        pof=false;
		
		for(int k=0;k<4;k++){ 
			if(ex[k]==-2||ex[k]==-1||(ex[k]<24||(ex[k]+in[k])<40)){
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
		for(int k=4;k<5;k++){ 
			if(ex[k]==-2||ex[k]==-1||(ex[k]<50||(ex[k]+in[k])<60)){
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
		for(int k=5;k<6;k++){
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
		
		for(int n=0;n<6;n++)
			if(pass[n]==1){
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
				String query="update "+ semtable+" set e1='"+exs[0]+"',e2='"+exs[1]+"',e3='"+exs[2]+"',e4='"+exs[3]+"',e5='"+exs[4]+"',e6='"+exs[5]+"',i1='"+ins[0]+"',i2='"+ins[1]+"',i3='"+ins[2]+"',i4='"+ins[3]+"',i5='"+ins[4]+"',i6='"+ins[5]+"',t1='"+Integer.toString(tot[0])+"',t2='"+Integer.toString(tot[1])+"',t3='"+Integer.toString(tot[2])+"',t4='"+Integer.toString(tot[3])+"',t5='"+Integer.toString(tot[4])+"',t6='"+Integer.toString(tot[5])+"',pd1='"+pdate[0]+"',pd2='"+pdate[1]+"',pd3='"+pdate[2]+"',pd4='"+pdate[3]+"',pd5='"+pdate[4]+"',pd6='"+pdate[5]+"',gp1='"+Integer.toString(gp[0])+"',gp2='"+Integer.toString(gp[1])+"',gp3='"+Integer.toString(gp[2])+"',gp4='"+Integer.toString(gp[3])+"',gp5='"+Integer.toString(gp[4])+"',gp6='"+Integer.toString(gp[5])+"',spdate='"+spdate+"',tg='"+Integer.toString(tgp)+"',sgpa='"+Double.toString(sgpa)+"' where regdno='"+regno+"'";
		
		stmt1.executeUpdate(query);
		
		//stmt1.executeUpdate("insert into s"+sem+" values('"+regno+"','"+name+"','"+fname+"','"+ex[0]+"','"+ex[1]+"','"+ex[2]+"','"+ex[3]+"','"+ex[4]+"','"+ex[5]+"','"+ex[6]+"','"+ex[7]+"','"+ex[8]+"','"+in[0]+"','"+in[1]+"','"+in[2]+"','"+in[3]+"','"+in[4]+"','"+in[5]+"','"+in[6]+"','"+in[7]+"','"+in[8]+"','"+tot[0]+"','"+tot[1]+"','"+tot[2]+"','"+tot[3]+"','"+"','"+tot[4]+"','"+tot[5]+"','"+tot[6]+"','"+tot[7]+"','"+tot[8]+"','"+pdate[0]+"','"+pdate[1]+"','"+pdate[2]+"','"+pdate[3]+"','"+pdate[4]+"','"+pdate[5]+"','"+pdate[6]+"','"+pdate[7]+"','"+pdate[8]+"','"+credits[0]+"','"+credits[1]+"','"+credits[2]+"','"+credits[3]+"','"+credits[4]+"','"+credits[5]+"','"+credits[6]+"','"+credits[7]+"','"+credits[8]+"','"+gp[0]+"','"+gp[1]+"','"+gp[2]+"','"+gp[3]+"','"+gp[4]+"','"+gp[5]+"','"+gp[6]+"','"+gp[7]+"','"+gp[8]+"','"+spdate+"','"+tc+"','"+tgp+"','"+sgpa+"','"+acy+"','"+semdate+"')");
                            }catch(Exception e6){}
			}
			elig=false;
			tc=0;
			tgp=0;
		}
        
        out.println("<html><head></head><body><h3><center>");
                out.println(" your data has been successfully updated");
                out.println("</center></h3></body></html>");
        
        
    }
}
