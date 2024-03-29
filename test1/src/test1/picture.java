package test1;

import com.sun.corba.se.spi.orb.ParserData;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.security.auth.callback.DialogCallbackHandler;




import java.awt.Font;
import java.awt.Graphics;
import java.awt.List;
import java.awt.TextField;
import java.awt.image.BufferedImage;

import java.io.File;


import java.text.ParseException;

import java.text.ParsePosition;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;



import sun.java2d.pipe.BufferedBufImgOps;

public class picture {
    public picture(BufferedImage b,Setting sett,boolean isline) {
        setting=sett;
        bufi=b;
        Isline=isline;
        makecolorset();
        MakePages();
        
    }
    boolean Isline;
    public class dot{
        public dot(int X,int Y){
          x=X;y=Y;  
        }
        int x;
        int y;
    }
    Setting setting=new Setting();

   /* public class rectXY{
        public rectXY(){
        
        }
        public  rectXY(int X1,int Y1,int X2,int Y2){
           x1=X1;y1=Y1;x2=X2;y2=Y2;
        }
        int x1,y1,x2,y2;
    }
    public class rectWH{
        public rectWH(int X,int Y,int W,int H){
            x=X;y=Y;w=W;h=H;         
        }
        public rectWH(){
            
        }
        int x,y,w,h;
    }*/
    public class page{
        ArrayList Lines=new ArrayList();
        //colorset pixels=new colorset();
        rectXY bound=new rectXY(); 
        public page(rectXY c){
            bound=c;
        }
    
        void findlines(){
            String Mode="black";
            int ybegin=0;
            int yend=0;
            boolean all=true;
            for (int j = bound.y1; j <= bound.y2; j++) {
                all=true;
               for (int i = bound.x1; i <= bound.x2; i++) {
                    if(Mode=="black"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                        colorsetmain.pixels[i][j][1]>setting.RGB&&
                        colorsetmain.pixels[i][j][2]>setting.RGB))
                    {
                        ybegin=j;
                        Mode="white";
                        all=false;
                        break;
                    }
                    if(Mode=="white"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                        colorsetmain.pixels[i][j][1]>setting.RGB&&
                        colorsetmain.pixels[i][j][2]>setting.RGB))
                    {
                        all=false;
                    }
                   
                }
                if(Mode=="white"&&all){
                    yend=j;
                    
                        Lines.add(new line(new rectXY(bound.x1,ybegin,bound.x2,yend)));
                        Mode="black";
                    
                 }
            }
           
            if(Lines.size()==0)
                Lines.add(new line(bound));
        }
        void MakeLines(){
            findlines();
            for(int i=0;i<Lines.size();i++){
                line l=(line)Lines.get(i);
                l.findwords();
                
            }
            
        }
    }
    public class line{
        ArrayList words=new ArrayList();
        //colorset pixels=new colorset();
        rectXY bound=new rectXY();
        void findwords(){
            String Mode="black";
            int xbegin=0;
            int xend=0;
            int ybegin=0;
            int yend=0;
            boolean all=true;
            for (int i = bound.x2; i >= bound.x1; i--) {
                all=true;
                
               for (int j = bound.y1; j <= bound.y2; j++) {
                    if(Mode=="black"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                        colorsetmain.pixels[i][j][1]>setting.RGB&&
                        colorsetmain.pixels[i][j][2]>setting.RGB))
                    {
                        xbegin=i;
                        Mode="white";
                        all=false;
                        break;
                    }
                    if(Mode=="white"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                        colorsetmain.pixels[i][j][1]>setting.RGB&&
                        colorsetmain.pixels[i][j][2]>setting.RGB))
                    {
                        all=false;
                    }
                   
                }
                if(Mode=="white"&&all){
                    xend=i+1;
                    Mode="black";
                    boolean all2=true;
                    boolean first=true;
                    for (int k = bound.y1; k <= bound.y2; k++) {
                        all2=true;
                       for (int m = xend; m <= xbegin; m++) {
                            if(Mode=="black"&&!(colorsetmain.pixels[m][k][0]>setting.RGB&&
                                colorsetmain.pixels[m][k][1]>setting.RGB&&
                                colorsetmain.pixels[m][k][2]>setting.RGB))
                            {
                                if(first){
                                    ybegin=k;
                                    first=false;
                                }
                                Mode="white";
                                all2=false;
                                break;
                            }
                            if(Mode=="white"&&!(colorsetmain.pixels[m][k][0]>setting.RGB&&
                                colorsetmain.pixels[m][k][1]>setting.RGB&&
                                colorsetmain.pixels[m][k][2]>setting.RGB))
                            {
                                all2=false;
                            }
                        }
                        if(Mode=="white"&&all2)
                        {
                            yend=k-1;
                            Mode="black";
                        }
                        if(Mode=="white"&&k==bound.y2)
                            yend=k;
                    }

                    words.add(new word(new rectXY(xend,ybegin,xbegin,yend)));
                        Mode="black";
                    
                    //}
                 }
            }
            
        }
        public line(rectXY b){
            bound=b;
            
        }
        
    }
    public class word{
        rectXY bounds=new rectXY();
        String text;
        //colorset pixels=new colorset();
        public word(rectXY b){
            bounds=b;
        }
        String MakeText(){
            return null;
        }
        
    }
    /*public class pixelcolor{
        int R=0;
        int G=0;
        int B=0;
    }*/
    public class colorset{
        
        public colorset(rectWH b){
           pixels=new int[b.w][b.h][3]; 
           bound=b;
        }
        rectWH bound=new rectWH();
        int[][][] pixels;

        
    }
    public class pattern{
        rectWH bound=new rectWH(); 
    }
    public class bitwisepettern{
        
    }
    void findpages(){
        dot begin =new dot(0,0) ;
        dot end=new dot(0,0);
        String Mod="black";
        boolean all=true;
        int I=bufi.getWidth();
        int J=bufi.getHeight();
        if(Isline){
            pages.add(new page(new rectXY(0,0,I-1,J-1)));
            return;
        }
        for (int i = 0; i < I; i++) {
            all=true;
            for (int j = 0; j < J; j++) {
                if(Mod=="black"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                    colorsetmain.pixels[i][j][1]>setting.RGB&&
                    colorsetmain.pixels[i][j][2]>setting.RGB))
                {
                    begin=new dot(i,0);
                    Mod="white";
                }
                if(Mod=="white"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                    colorsetmain.pixels[i][j][1]>setting.RGB&&
                    colorsetmain.pixels[i][j][2]>setting.RGB)) {
                all=false;
                }
                
            }
            if(Mod=="white"&&all){
                if(i-begin.x>setting.Minpagesize){
                   end=new dot(i,0);
                   i=I;
                }
                else{
                    all=false;
                    Mod="black";
                }
            }

        } 
        J=bufi.getHeight();
        Mod="black";
        for (int j = 0; j < J; j++) {
            all=true;
            for (int i = 0; i < end.x; i++) {
                if(Mod=="black"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                    colorsetmain.pixels[i][j][1]>setting.RGB&&
                    colorsetmain.pixels[i][j][2]>setting.RGB))
                {
                    begin.y=j;
                    Mod="white";
                }
                if(Mod=="white"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                    colorsetmain.pixels[i][j][1]>setting.RGB&&
                    colorsetmain.pixels[i][j][2]>setting.RGB)) {
                all=false;
                }
                
            }
            if(Mod=="white"&&!all){
               end.y=j;
               
                
            }

        }
        
        pages.add(new page(new rectXY(begin.x-1,begin.y,end.x-1,end.y)));
        Mod="black";
        dot begin2=new dot(0,0);
        dot end2=new dot(0,0);
        I=bufi.getWidth();
        J=bufi.getHeight();
        for (int i = I-1; i > 0; i--) {
            all=true;
            for (int j = 0; j < J; j++) {
                if(Mod=="black"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                    colorsetmain.pixels[i][j][1]>setting.RGB&&
                    colorsetmain.pixels[i][j][2]>setting.RGB))
                {
                    begin2=new dot(i,0);
                    Mod="white";
                }
                if(Mod=="white"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                    colorsetmain.pixels[i][j][1]>setting.RGB&&
                    colorsetmain.pixels[i][j][2]>setting.RGB)) {
                all=false;
                }
                
            }
            if(Mod=="white"&&all){
                
                if(begin2.x-i>setting.Minpagesize){
                   end2=new dot(i,0);
                   break;
                }
                else{
                    all=false;
                    Mod="black";
                }
            }

        } 
        J=bufi.getHeight();
        I=bufi.getWidth();
        Mod="black";
        for (int j = 0; j < J; j++) {
            all=true;
            for (int i = I-1; i > end2.x; i--) {
                if(Mod=="black"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                    colorsetmain.pixels[i][j][1]>setting.RGB&&
                    colorsetmain.pixels[i][j][2]>setting.RGB))
                {
                    begin2.y=j;
                    Mod="white";
                }
                if(Mod=="white"&&!(colorsetmain.pixels[i][j][0]>setting.RGB&&
                    colorsetmain.pixels[i][j][1]>setting.RGB&&
                    colorsetmain.pixels[i][j][2]>setting.RGB)) {
                all=false;
                }
                
            }
            if(Mod=="white"&&!all){
               end2.y=j;
            
                
            }

        }
        if (begin2.x!= end.x-1) {
        
            pages.add(new page(new rectXY(end2.x,begin2.y,begin2.x,end2.y)));
        }
       
    }
    void makecolorset(){
        int x=0;
        int y=0;
        colorsetmain=new colorset(new rectWH(0,0, bufi.getWidth(), bufi.getHeight()));
        int[] ar=new int[bufi.getWidth()*bufi.getHeight()*3];
        int[] colorsettemp=bufi.getData().getPixels(0, 0, bufi.getWidth(), bufi.getHeight(), ar);
        int lenght=colorsettemp.length;
        for(int i=0;i<lenght;) {
            //colorsetmain.pixels[x][y]=new pixelcolor();
            colorsetmain.pixels[x][y][0]=colorsettemp[i++];
            colorsetmain.pixels[x][y][1]=colorsettemp[i++];
            colorsetmain.pixels[x][y][2]=colorsettemp[i++];
            x++;
            if(x==bufi.getWidth()){
                x=0;
                y++;
            }
        }
    }
    ArrayList pages=new ArrayList();
    colorset colorsetmain;
    BufferedImage bufi;
    
    
   /*   void getimage(JTextField tf) 
    {
        File file=new File(tf.getText());
          try{
             BufferedImage buftemp=ImageIO.read(file);
              bufi=new BufferedImage(buftemp.getWidth(),buftemp.getHeight(),buftemp.getType());
              bufi=buftemp;
          }
          catch(Exception ex){
             
              
          }
    }*/
      void MakePages(){
          ArrayList output=new ArrayList();
         findpages();
         for(int i=0;i<pages.size();i++){
            page p=(page)pages.get(i);
            p.MakeLines();
        }
         
         
      }
    
    
}
