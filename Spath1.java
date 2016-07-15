import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
/*<applet code="Spath1" width=1300 height=650></applet>*/
public class Spath1 extends Applet implements MouseListener,ActionListener{
int x[]=new int[40];
int y[]=new int[40];
int post,s,d,n=0;
int aa[][]=new int[40][40];
String pxpy="",gg="";
   int finalpath[]=new int[40];

TextField jtf=new TextField(8);
TextField jtf1=new TextField(8);
TextField jtf2=new TextField(8);
TextField j1=new TextField(8);
TextField j2=new TextField(8);
Button b1=new Button("Submit");
Button b2=new Button("FindShortestPath");
public void init()
{
for(int i=0;i<40;i++)
for(int j=0;j<40;j++)
{
aa[i][j]=0;
}

jtf.setBounds(1100,100,50,40);
jtf1.setBounds(950,100,50,40);
jtf2.setBounds(1025,100,50,40);
j1.setBounds(950,200,50,40);
j2.setBounds(1025,200,50,40);
b1.setBounds(1200,100,100,50);
b2.setBounds(1100,200,150,50);


add(jtf);
add(jtf1);
add(jtf2);
add(j1);
add(j2);
add(b1);
add(b2);
b1.addActionListener(this);
b2.addActionListener(this);
setLayout(null);
addMouseListener(this);

}
public void actionPerformed(ActionEvent ae)
{
String str=ae.getActionCommand();
if(str.equals("Submit")){
int p=Integer.parseInt(jtf.getText());
int q=Integer.parseInt(jtf1.getText());
int r=Integer.parseInt(jtf2.getText());
aa[q][r]=p;
aa[r][q]=p;
}
if(str.equals("FindShortestPath")){
   s=Integer.parseInt(j1.getText());
   d=Integer.parseInt(j2.getText());
int spew[][]=new int[40][40];
int spsn[][]=new int[40][40];
int select[]=new int[40];
int c=0,boxew=0,boxsn=0;   
for(int i=0;i<40;i++)
for(int j=0;j<40;j++)
{
select[j]=0;
spsn[i][j]=0;
spew[i][j]=0;
finalpath[j]=0;
}
select[s]=1;
spsn[c][0]=s;
for(int i=1;i<=n;i++)
{
spsn[c][i]=s;
spew[c][i]=aa[s][i-1];
}
c++;
while(c<n)
{
   boxew=0;
		boxsn=0;
		for(int i=1;i<=n;i++){
		if(select[i-1]==0 && boxew==0){
		spsn[c][0]=i-1;
		spew[c][0]=0;
		boxew=spew[c-1][i];
		boxsn=spsn[c-1][i];
		}
		else if(select[i-1]==0 && spew[c-1][i]<=boxew && spew[c-1][i]!=0){
		spsn[c][0]=i-1;
		spew[c][0]=0;
		boxew=spew[c-1][i];
		boxsn=spsn[c-1][i];
		}
		}
	  
		select[spsn[c][0]]=1;
		for(int i=1;i<=n;i++){
		if(select[i-1]!=0 || aa[spsn[c][0]][i-1]==0){
			spew[c][i]=spew[c-1][i];
				spsn[c][i]=spsn[c-1][i];
		}
		else if(spew[c-1][i]<=boxew+aa[spsn[c][0]][i-1] && spew[c-1][i]!=0){
		spsn[c][i]=spsn[c-1][i];
		spew[c][i]=spew[c-1][i];
		}
		else{
		spsn[c][i]=spsn[c][0];
		spew[c][i]=boxew+aa[spsn[c][0]][i-1];
		}
		}
		c++;
		
	


}//while c<n ends

	//cout<<"path\n";
	//cout<<n<<endl;
	finalpath[0]=d;
	int temp=d,temp1;
	post=0;
	gg="path "; 

	while(temp!=s)
	{       if(post!=0)
		finalpath[post]=temp;
		//cout<<finalpath[post]<<" ";
		gg=gg+finalpath[post]+" ";
		temp1=spsn[n-1][temp+1];
		temp=temp1;
		post++;
	}
		finalpath[post]=temp;
		//cout<<finalpath[post]<<" ";
		gg=gg+finalpath[post];


}//findshortpathif loop ends

repaint();

}
public void mouseClicked(MouseEvent me)
{
 x[n]=me.getX();
 y[n]=me.getY();
 n++;
 repaint();  
}
public void mouseExited(MouseEvent me){}
public void mouseEntered(MouseEvent me){}
public void mousePressed(MouseEvent me){}
public void mouseReleased(MouseEvent me){}
public void paint(Graphics g)
{
 g.setFont(new Font("Times_New_Roman",Font.PLAIN,20));
//showStatus("c value : "+n);  

for(int i=0;i<n;i++)
for(int j=0;j<n;j++)
pxpy=pxpy+" "+aa[i][j];
pxpy=pxpy+" |"+n;
showStatus(pxpy+" "+gg);
pxpy="";
gg="";

for(int i=0;i<n;i++)
  {
	g.setColor(Color.black);
	g.drawOval(x[i]-30,y[i]-30,60,60);
	g.setColor(Color.blue);
	g.drawString(""+i,x[i]-15,y[i]+15);
	
   }
	  for(int i=0;i<=n;i++){
	  for(int j=0;j<=n;j++){
        	if(aa[i][j]!=0 && i!=j){
		g.setColor(Color.green);
		g.drawLine(x[i],y[i],x[j],y[j]);
		g.drawLine(x[i],y[i],x[j],y[j]);
		g.setColor(Color.black);
		g.drawString(""+aa[i][j],(x[i]+x[j])/2,(y[i]+y[j])/2);
                }           
          }
          }
	  g.setColor(Color.red);
	  for(int i=0;i<post;i++)
	  {
		g.drawLine(x[finalpath[i]],y[finalpath[i]],x[finalpath[i+1]],y[finalpath[i+1]]);
		g.drawLine(x[finalpath[i]],y[finalpath[i]],x[finalpath[i+1]],y[finalpath[i+1]]);
	  }
       

}
}