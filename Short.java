import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.sql.*;


/*<applet code="Short" width="1350" height="650"></applet>*/
public class Short extends Applet implements MouseListener,ActionListener
{
TextField t1,t2,t3,t4,t5;
Button b1,b2,adjust,del;
int mov_index=-1;
int a[][]=new int[30][30];
int x[]=new int[30];
int y[]=new int[30];
int n=0,find=0;
int sol[]=new int[30];
int dist[]=new int[30];
int route[]=new int[30];
int select[]=new int[30];
int solnodes=0,adj=0;
public void init()
{
setLayout(null);
setFont(new Font("Arial",Font.PLAIN,20));
t1=new TextField();
t2=new TextField();
t3=new TextField();
t4=new TextField();
t5=new TextField();
b1=new Button("ENTER");
b2=new Button("FIND");
adjust=new Button("Add");
del=new Button("Clear");
add(t1);
add(t2);
add(t3);
add(b1);
add(t4);
add(t5);
add(b2);
add(adjust);
add(del);
addMouseListener(this);
adjust.setBounds(10+100,5,100,30);
del.setBounds(1200,5,100,30);
b1.setBounds(680,615,100,30);
b2.setBounds(1230,615,100,30);
b1.addActionListener(this);
b2.addActionListener(this);
adjust.addActionListener(this);
del.addActionListener(this);
t1.setBounds(80,615,80,30);
t2.setBounds(280,615,80,30);
t3.setBounds(560,615,80,30);
t4.setBounds(890,615,80,30);
t5.setBounds(1090,615,80,30);


//initialise begin
for(int i=0;i<30;i++)
{
x[i]=0;
y[i]=0;
dist[i]=0;
route[i]=0;
sol[i]=0;
select[i]=0;
for(int j=0;j<30;j++)
{
a[i][j]=0;
}
}
//initialise ends
}//init method ends
public void actionPerformed(ActionEvent ae)
{
String s=ae.getActionCommand();
if(s.equals("ENTER"))
{
int n1=Integer.parseInt(t1.getText());
int n2=Integer.parseInt(t2.getText());
int n3=Integer.parseInt(t3.getText());
a[n1][n2]=n3;
a[n2][n1]=n3;
repaint();
}
else if(s.equals("Clear"))
{

for(int r=0;r<n;r++)
for(int c=0;c<n;c++)
a[r][c]=0;
n=0;
repaint();
}
else if(s.equals("Fixed"))
{
adjust.setLabel("Add");
adj=0;
}
else if(s.equals("Adjust"))
{
adj=2;
adjust.setLabel("Fixed");
}
else if(s.equals("Add"))
{
adj=3;
adjust.setLabel("Delete");
}
else if(s.equals("Delete"))
{
adj=1;
adjust.setLabel("Adjust");
mov_index=-1;
}
else if(s.equals("FIND"))
{
find=1;
int src=Integer.parseInt(t4.getText());
int dest=Integer.parseInt(t5.getText());
for(int i=0;i<30;i++)
{
dist[i]=0;
route[i]=-1;
select[i]=0;
sol[i]=-1;
}
if(src>=0 && src<30 && dest>=0 && dest<30)
compute(src,dest);
repaint();
}
}
public void mouseClicked(MouseEvent me)
{

if(adj==0){
x[n]=me.getX();
y[n]=me.getY();
 n++;
}
else if(adj==1)
{
int fnd=0;
int tx=me.getX();
int ty=me.getY();
int temp,iter;
for( iter=0;iter<n;iter++)
{
temp=(int)Math.sqrt((x[iter]-tx)*(x[iter]-tx)+(y[iter]-ty)*(y[iter]-ty));
if(temp<=25){
fnd=1;
mov_index=iter;
break;
}
}
if(iter==n && mov_index!=-1)
{
fnd=0;
x[mov_index]=me.getX();
y[mov_index]=me.getY();
}
}
else if(adj==3)
{
//delete
int fnd=0;
int dnode=-1;
int tx=me.getX();
int ty=me.getY();
int temp,iter;
for( iter=0;iter<n;iter++)
{
temp=(int)Math.sqrt((x[iter]-tx)*(x[iter]-tx)+(y[iter]-ty)*(y[iter]-ty));
if(temp<=25){
fnd=1;
dnode=iter;
break;
}
}
if(dnode!=-1)
{
for(int row=0;row<n;row++){
a[dnode][row]=0;
}
for(int row=dnode;row<n-1;row++)
for(int colum=0;colum<n;colum++)
a[row][colum]=a[row+1][colum];

for(int row=0;row<n;row++){
a[row][dnode]=0;
}
for(int colum=dnode;colum<n-1;colum++)
for(int row=0;row<n;row++)
a[row][colum]=a[row][colum+1];

for(int colum=dnode;colum<n-1;colum++)
{
x[colum]=x[colum+1];
y[colum]=y[colum+1];
}
n=n-1;
}
//adj=3 ends
}
 repaint();  
}
public void mouseExited(MouseEvent me){}
public void mouseEntered(MouseEvent me){}
public void mousePressed(MouseEvent me){}
public void mouseReleased(MouseEvent me){}

void compute(int s,int d)
{

String res="";
int di=1,m=-1,min=s;
select[s]=di;
dist[s]=0;
for(int i=0;i<n;i++)
{
dist[i]=a[s][i];
route[i]=s;
if((m>dist[i] && dist[i]!=0)||(m==-1 && dist[i]!=0))
{
m=dist[i];
min=i;
}
}//init over
di++;

while(di<n)
{
select[min]=di;
for(int i=0;i<n;i++)
{
if( ((m+a[min][i])<dist[i] && a[min][i]!=0)||(a[min][i]!=0 && dist[i]==0 && select[i]==0))
{
dist[i]=m+a[min][i];
route[i]=min;
}
}
//find min and m
m=-1;
for(int i=0;i<n;i++)
{
if(m==-1 && select[i]==0 && dist[i]!=0)
{
m=dist[i];
min=i;
}
else if(m>dist[i] && select[i]==0 && dist[i]!=0)
{
m=dist[i];
min=i;
}
}//for loop ends
di++;
}//while ends
solnodes=0;
if(dist[d]!=0){
solnodes=0;
sol[solnodes]=d;
solnodes++;
while(d!=s)
{
sol[solnodes]=route[d];
res=res+""+sol[solnodes];
d=route[d];
solnodes++;
}

}


}//compute ends

public void paint(Graphics g)
{
//static part
g.setColor(Color.darkGray);
g.fillRect(0,0,1400,40);
g.fillRect(0,610,1400,40);
g.setColor(Color.white);
g.setFont(new Font("Arial",Font.BOLD,20));
g.drawString("NODE1",10,635);
g.drawString("MODE  :",10,25);
g.drawString("Dijkstra's Single Source Shortestpath Algorithm Simulation",400,25);
g.drawString("NODE2",200,635);
g.drawString("EDGE -WEIGHT",400,635);
g.drawString("SRC",840,635);
g.drawString("DEST",1030,635);
//static part ends
g.setFont(new Font("Arial",Font.BOLD,25));
g.setColor(Color.black);
for(int i=0;i<n;i++)
{
g.setColor(Color.black);
g.drawOval(x[i]-15,y[i]-15,50,50);
g.setColor(Color.blue);
g.drawString(""+i,x[i]-10,y[i]+25);
}

for(int i=0;i<n;i++)
for(int j=0;j<n;j++)
{
if(a[i][j]!=0){
g.setColor(Color.green);
g.drawLine(x[i],y[i],x[j],y[j]);
g.drawLine(x[i]+1,y[i]+1,x[j]+1,y[j]+1);
g.setColor(Color.black);
g.drawString(""+a[i][j],(x[i]+x[j])/2,(y[i]+y[j])/2);
}
}
showStatus("Single Source Shortest Path Algorithm");
if(find==1)
{
find=0;
if(solnodes>0){
g.setColor(Color.red);

for(int i=0;i<n;i++)
g.drawString(""+dist[i],x[i]+20,y[i]-15);

for(int i=solnodes-1;i>0;i--)
{

//g.drawLine(x[sol[i]],y[sol[i]],x[sol[i-1]],y[sol[i-1]]);
//g.drawLine(x[sol[i]]+1,y[sol[i]]+1,x[sol[i-1]]+1,y[sol[i-1]]+1);
int d=(int)Math.sqrt((float)((x[sol[i-1]]-x[sol[i]])*(x[sol[i-1]]-x[sol[i]])+(y[sol[i-1]]-y[sol[i]])*(y[sol[i-1]]-y[sol[i]])));

double theta=Math.atan2((double)(y[sol[i-1]]-y[sol[i]]),(x[sol[i-1]]-x[sol[i]]))*180/Math.PI; 
showStatus("Single Source Shortest Path Algorithm "); 
   for(int j=0;j<d;j=j+5)
   {
      g.drawLine(x[sol[i]],y[sol[i]],x[sol[i]]+(int)(j*Math.cos(Math.PI*theta/180)),y[sol[i]]+(int)(j*Math.sin(Math.PI*theta/180)));
      g.drawLine(x[sol[i]]+1,y[sol[i]]+1,x[sol[i]]+1+(int)(j*Math.cos(Math.PI*theta/180)),y[sol[i]]+1+(int)(j*Math.sin(Math.PI*theta/180)));
    try{Thread.sleep(5);}catch(InterruptedException e){}
   }
}
//showStatus("Single Source Shortest Path Algorithm ");
}
else{

showStatus("No Path Exists");
JOptionPane.showMessageDialog(null, "No path Exists");

}
}

}
}

