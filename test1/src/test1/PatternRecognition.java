package test1;

import java.awt.Graphics2D;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.nio.charset.Charset;

import java.security.Key;

import test1.Patterns;
import java.util.ArrayList;

import javax.swing.JTextArea;

import sun.java2d.Disposer;

public class PatternRecognition {
   Setting setting=new Setting();
    public PatternRecognition(Setting sett){
       //imagebuf =image;
        setting=sett;
    patt=new Patterns(setting);
       patt.Create();
       
    }
   
   Patterns patt;
    //BufferedImage imagebuf;
    /*public class rectXY{
        public rectXY(){
        
        }
        public  rectXY(int X1,int Y1,int X2,int Y2){
           x1=X1;y1=Y1;x2=X2;y2=Y2;
        }
        int x1,y1,x2,y2;
    }*/
    rectXY bound=new rectXY();
    //ArrayList<boolean[][]> pattern=new ArrayList<boolean[][]>();
    
    ArrayList<String> MakeText(BufferedImage img,JTextArea list){
      picture pic=new picture(img,setting,false);
      /*double linescount=0;
      double sizeall=0;
      for (int i = 0; i < pic1.pages.size(); i++) {
          picture.page pg0=(picture.page)pic1.pages.get(i);
         for (int j = 0; j <pg0.Lines.size() ; j++) {
               linescount++;
             picture.line line0=(picture.line)pg0.Lines.get(j);
             sizeall+=(line0.bound.y2-line0.bound.y1+1);
            }

        }
        double avrage=sizeall/linescount;
        double scale0=3;
      
        
        BufferedImage img0=Scale0(img,scale0);
        pic=new picture(img0,setting);*/
        ArrayList<String> outtext=new ArrayList<String>();
      String pagetext="";
      String linetext="";
      double scaletemp=0;
      for (int p=pic.pages.size()-1;p>=0;p--) {
          picture.page pg=(picture.page)pic.pages.get(p);
          String wrdtext="";
            for (int l=0;l<pg.Lines.size();l++) {
                picture.line line=(picture.line)pg.Lines.get(l);
                double scale =findSingles(p,l,line,pic,img);
                scale=1/scale;
            
                BufferedImage lineimg=img.getSubimage(line.bound.x1,line.bound.y1,line.bound.x2-line.bound.x1+1,line.bound.y2-line.bound.y1+1);
                rectXY b=new rectXY(0,0,lineimg.getWidth()-1,lineimg.getHeight()-1);
                BufferedImage lineimgs=Scale(lineimg,scale,b);
                b=new rectXY(0,0,lineimgs.getWidth()-1,lineimgs.getHeight()-1);
                picture linepic=new picture(lineimgs,setting,true);
                picture.page linepage=(picture.page)linepic.pages.get(0);
                line=(picture.line)linepage.Lines.get(0);
                
                
                
                /*picture pic2=new picture(scaledimage); 
                picture.page pg2=(picture.page)pic2.pages.get(p);*/
                
                linetext="";
                if(line.bound.y2-line.bound.y1>3){
                    for (int w=0;w<line.words.size();w++) {
                        picture.word wrd=(picture.word)line.words.get(w);
                        wrdtext="";
                        wrdtext= getWrdText(linepic,wrd);
                        linetext+=wrdtext+" ";
                        
                    }
                    linetext+="\r\n";
                    list.append(linetext);
                    list.update(list.getGraphics());
                    pagetext+=linetext;
                }
            }
            outtext.add(pagetext);
        }
       
        return outtext;
    }
    double getratio(pattern p){
        double h=(p.bound.y2-p.bound.y1+1);
        double w=(p.bound.x2-p.bound.x1+1);
        return (h/w);
    }
    double findSingles(int pagenum,int linenum,picture.line line,picture imag,BufferedImage buff){
        //picture imag2;
        double finallscale=1;
        double comptemp=100000;
       for (int i = 0; i < patt.pattern_s.size(); i++) {
           
            if((i==81||i==27||i==35)){
             int a;
             a = 1;
             a++;
            }
               if(patt.Names.get(i).indexOf('1')!=-1){
                  
                   for (int j=0;j<line.words.size();j++) {
                       pattern p=patt.pattern_s.get(i);
                       picture.word w=(picture.word)line.words.get(j);
                       if((w.bounds.x2-w.bounds.x1>3)&&(w.bounds.y2-w.bounds.y1>3)){
                           double h=(w.bounds.y2-w.bounds.y1+1);
                           double wid=(w.bounds.x2-w.bounds.x1+1);
                           double wratio=h/wid;
                           double pratio=getratio(p);
                           if(Math.abs(pratio-wratio)<.1){
                               rectXY rect=new rectXY();
                               rect.x1=w.bounds.x1;rect.x2=w.bounds.x2;
                               rect.y1=w.bounds.y1;rect.y2=w.bounds.y2;                    
                                    double pheight=(p.bound.y2-p.bound.y1+1);
                                    double rectheight=(rect.y2-rect.y1+1);
                                   double scale=rectheight/pheight;
                                   BufferedImage scbuff=Scale(p.bufi,scale,p.bound);
                                   pattern temp=new pattern(scbuff,setting);
                                   temp.MakebPattern();
                                    /*imag2=new picture(scImage);
                                   picture.page pagesc=(picture.page)imag2.pages.get(pagenum);
                                   picture.line linesc=(picture.line)pagesc.Lines.get(linenum);
                                   picture.word wordsc=(picture.word)linesc.words.get(j);*/
                                   boolean[][] tempboolean=getboolean(new rectXY(w.bounds.x1,w.bounds.y1,
                                        w.bounds.x2,w.bounds.y2),imag);
                                   double comp=compare2(tempboolean,temp.bPattern);
                                   if(comp<comptemp){
                                        finallscale=scale;
                                        comptemp=comp;   
                                    }
                                   
                                      
                           }
                       }
                   }
               }
            
        }
        return finallscale;
    }

    String getWrdText(picture picall,picture.word word){
        String wordtext="";
        double compareresult=1000000;
        double uphisttoltemp=100000;
        double downhisttoltemp=100000;
        double lefthisttoltemp=1000000;
        int xtemp=word.bounds.x2;
        double comparetemp=0;
        int xbegin=word.bounds.x2;
        int endx=xbegin;
        int index=0;
        int shift=0;
        double tolerance=setting.telorance;
        boolean detected=true;
        boolean success=true;
        boolean over=false;
        int minshift=1;
        int d=0;
        int[] detectedx=new int[10];
        if((word.bounds.x2-word.bounds.x1)<=5&&(word.bounds.y2-word.bounds.y1)>10) 
            {
               char[] a=new char[1];
               a[0] ='\u0627';
               String b=new String(a);
                return(b);
        }
        if(Math.abs((word.bounds.x2-word.bounds.x1)-(word.bounds.y2-word.bounds.y1))<=1&&(word.bounds.y2-word.bounds.y1)<=5)
            return(".");
        while(xbegin>word.bounds.x1){
            compareresult=100000;
            detected=false;
            
            for (int i=0;i<patt.pattern_s.size();i++) {
                if(i==76){
                    int a=1;
                }
                pattern pattern=patt.pattern_s.get(i);
                /*if(scale!=1){
                    BufferedImage scbuf=Scale(pattern.bufi,scale,pattern.bound);
                    pattern=new pattern(scbuf,setting);
                    pattern.MakebPattern();
                }*/
                endx=xbegin-pattern.bPattern.length+1;
                if(endx>=word.bounds.x1&&xbegin>endx){
                   //if (endx>=word.bounds.x1)
                    //{
                        int ybegin=findybegin(picall,word,endx,xbegin);
                        int yend=findyend(picall,word,endx,xbegin);
                        boolean[][] brect=getboolean(new rectXY(endx,ybegin,xbegin,yend),picall);
                        if(Math.abs((yend-ybegin+1)-pattern.bPattern[0].length)<=2){
                           /* int yright=findyright(picall,word,xbegin);
                            int yleft=findyleft(picall,word,endx);
                            int yrightp=findyrightp(pattern);
                            int yleftp=findyleftp(pattern);
                            if(yright==yrightp&&yleft==yleftp){*/
                            double uphisttol= compareys(picall,word,xbegin,endx,ybegin,yend,pattern);
                            double downhisttol=compareysb(picall,word,xbegin,endx,ybegin,yend,pattern);
                            double lefthisttol=comparexsleft(picall,word,xbegin,endx,ybegin,yend,pattern);
                            /*double yAvrgp=findyAvrgp(pattern);
                            if(Math.abs(yAvrg-yAvrgp)<=3){
                                /*if(Math.abs(yright-yrightp)<12&&
                                   Math.abs(yleft-yleftp)<12){*/
                               //comparetemp= compare(new rectXY(endx,ybegin,xbegin,yend),picall,pattern);
                                    comparetemp=compare2(pattern.bPattern,brect);
                                    if(((compareresult>comparetemp)&&(comparetemp<tolerance))&&
                                       (uphisttol<uphisttoltemp&&uphisttol<setting.histogram&&
                                       downhisttol<downhisttoltemp&&downhisttol<setting.histogram&&
                                       lefthisttol<lefthisttoltemp&&lefthisttol<setting.histogram)){
                                       // boolean hasdetect=false;
                                        /*for (int j = 0; j <10; j++) {
                                            if(detectedx[j]!=0&&Math.abs(detectedx[j]-xbegin)<=1)
                                                hasdetect=true;
                                        }

                                    //if(!hasdetect){*/
                                            uphisttoltemp=uphisttol;
                                            downhisttoltemp=downhisttol;
                                            lefthisttoltemp=lefthisttol;
                                            compareresult=comparetemp;
                                            index=i;
                                            xtemp=endx;
                                            
                                            detected=true;
                                            /*success=true;
                                            over=false;
                                        }*/
                                    }
                           }
                    }
                //}
            }
           /* if(over){
                xbegin-=1;
            }
            else if(!detected&&!success){
                xbegin-=1;
                shift++;
                if(shift==minshift*2){
                   
                    if(minshift<=1){
                        shift=0;
                        xbegin=xbegin+minshift;
                        minshift++;
                        success=true;
                    }
                    else{
                        minshift=1;
                        over=true;
                        tolerance=setting.telorance;
                    }
                }
            }
            else if(!detected){
                xbegin+=minshift;
                success=false;
            }*/
            if(!detected){
                xbegin-=1;
            }
            else{
                
                wordtext+=patt.Names.get(index).subSequence(0,patt.Names.get(index).indexOf('_'));
                //detectedx[d++]=xbegin;
                xbegin=xtemp;
                tolerance=setting.telorance;
                compareresult=100000;
                compareresult=1000000;
                uphisttoltemp=100000;
                downhisttoltemp=100000;
                lefthidttoltemp=1000000;
               // minshift=1;
                
            }
        }
        return wordtext;
    }
    int findybegin(picture pic,picture.word wr,int end,int begin){
        for (int j = wr.bounds.y1; j <=wr.bounds.y2; j++) {
           for (int i = end; i <=begin; i++) {
                if(!(pic.colorsetmain.pixels[i][j][0]>setting.RGB&&
                    pic.colorsetmain.pixels[i][j][1]>setting.RGB&&
                    pic.colorsetmain.pixels[i][j][2]>setting.RGB))
                    return j;
            }
            
        }
        return wr.bounds.y1;
    }
    int[] findys(picture pic,picture.word wr,int end,int begin,int ybegin,int yend){
       int[] ys=new int[begin-end+1];
        int element=0;
        for ( int i = end; i <=begin; i++) {
           for (int j =ybegin; j <=yend; j++) {
                if(!(pic.colorsetmain.pixels[i][j][0]>setting.RGB&&
                    pic.colorsetmain.pixels[i][j][1]>setting.RGB&&
                     pic.colorsetmain.pixels[i][j][2]>setting.RGB)){
                        ys[element++]=((j-ybegin));
                        break;
                     }
            }
           
        }
        return ys;
    }
    int[] findxsleft(picture pic,picture.word wr,int end,int begin,int ybegin,int yend){
       int[] xs=new int[ybegin-yend+1];
        int element=0;
        for ( int i = yend; i <=ybegin; i++) {
           for (int j =end; j <=begin; j++) {
                if(!(pic.colorsetmain.pixels[j][i][0]>setting.RGB&&
                    pic.colorsetmain.pixels[j][i][1]>setting.RGB&&
                     pic.colorsetmain.pixels[j][i][2]>setting.RGB)){
                        xs[element++]=((j-end));
                        break;
                     }
            }
           
        }
        return xs;
    }
    int[] findysb(picture pic,picture.word wr,int end,int begin,int ybegin,int yend){
       int[] ys=new int[begin-end+1];
        int element=0;
        for ( int i = end; i <=begin; i++) {
           for (int j =yend; j >=ybegin; j--) {
                if(!(pic.colorsetmain.pixels[i][j][0]>setting.RGB&&
                    pic.colorsetmain.pixels[i][j][1]>setting.RGB&&
                     pic.colorsetmain.pixels[i][j][2]>setting.RGB)){
                        ys[element++]=(j);
                        break;
                     }
            }
           
        }
        return ys;
    }
    int[] findysp(pattern p){
       int[] ys=new int[p.bPattern.length];
       int element=0;
       for (int i = 0; i < p.bPattern.length; i++) {
            for (int j = 0; j <p.bPattern[0].length; j++) {
        
                if(p.bPattern[i][j]==true){
                    ys[element++]=(j);
                    break;
                }
            }
       }
    
        return ys;
    }
    int[] findxsleftp(pattern p){
       int[] xs=new int[p.bPattern[0].length];
       int element=0;
       for (int i = 0; i < p.bPattern[0].length; i++) {
            for (int j = 0; j <p.bPattern.length; j++) {
        
                if(p.bPattern[j][i]==true){
                    xs[element++]=(j);
                    break;
                }
            }
       }
    
        return xs;
    }
    int[] findyspb(pattern p){
       int[] ys=new int[p.bPattern.length];
       int element=0;
       for (int i = 0; i < p.bPattern.length; i++) {
            for (int j = p.bPattern[0].length; j >0; j--) {
        
                if(p.bPattern[i][j]==true){
                    ys[element++]=(j);
                    break;
                }
            }
       }
    
        return ys;
    }
    double compareys(picture pic,picture.word wrd,int begin,int end,int ybegin,int yend,pattern p){
        int[] ys=findys(pic,wrd,end,begin,ybegin,yend);
        int[] ysp=findysp(p);
        double wrong=0;
        for (int i = 0; i < ys.length; i++) {
            wrong+=Math.abs(ys[i]-ysp[i]);
              
        }
        return (wrong/ys.length);
           
    }
    double comparexsleft(picture pic,picture.word wrd,int begin,int end,int ybegin,int yend,pattern p){
        int[] xs=findxsleft(pic,wrd,end,begin,ybegin,yend);
        int[] xsp=findxsleftp(p);
        double wrong=0;
        int min=0;
        int max =0;
        if(xs.length>xsp.length) {
            min=xsp.length;
            max=xs.length;
        }
        else{
           max=xsp.length;
            min=xs.length;
        }
        for (int i = 0; i <min; i++) {
            wrong+=Math.abs(xs[i]-xsp[i]);
              
        }
        for (int i = min; i < max; i++) {
            if(xs.length>xsp.length){
                wrong+=xs[i];
            }
            else
                wrong+=xsp[i];
        }

        return (wrong/max);
           
    }
    double compareysb(picture pic,picture.word wrd,int begin,int end,int ybegin,int yend,pattern p){
        int[] ys=findysb(pic,wrd,end,begin,ybegin,yend);
        int[] ysp=findyspb(p);
        double wrong=0;
        for (int i = 0; i < ys.length; i++) {
            wrong+=Math.abs(ys[i]-ysp[i]);
              
        }
        return (wrong/ys.length);
           
    }
    int findyleft(picture pic,picture.word wr,int end){
        for (int j = wr.bounds.y1; j <=wr.bounds.y2; j++) {
          
                if(!(pic.colorsetmain.pixels[end][j][0]>setting.RGB&&
                    pic.colorsetmain.pixels[end][j][1]>setting.RGB&&
                    pic.colorsetmain.pixels[end][j][2]>setting.RGB))
                    return j-wr.bounds.y1;
            }
            
    
        return -1;
    }
    int findyright(picture pic,picture.word wr,int begin){
        for (int j = wr.bounds.y1; j <=wr.bounds.y2; j++) {
          
                if(!(pic.colorsetmain.pixels[begin][j][0]>setting.RGB&&
                    pic.colorsetmain.pixels[begin][j][1]>setting.RGB&&
                    pic.colorsetmain.pixels[begin][j][2]>setting.RGB))
                    return j-wr.bounds.y1;
            }
            
    
        return -1;
    }
   
    int findyrightp(pattern p){
        for (int j = 0; j <p.bPattern[0].length; j++) {
        
                if(p.bPattern[p.bPattern.length-1][j]==true)
                    return j;
            }
            
    
        return 0;
    }
    int findyleftp(pattern p){
        for (int j = 0; j <p.bPattern[0].length; j++) {
        
                if(p.bPattern[0][j]==true)
                    return j;
            }
            
    
        return 0;
    }
    int findyend(picture pic,picture.word wr,int end,int begin){
        for (int j = wr.bounds.y2; j >=wr.bounds.y1; j--) {
           for (int i = end; i <=begin; i++) {
                if(!(pic.colorsetmain.pixels[i][j][0]>setting.RGB&&
                    pic.colorsetmain.pixels[i][j][1]>setting.RGB&&
                    pic.colorsetmain.pixels[i][j][2]>setting.RGB))
                    return j;
            }
            
        }
        return wr.bounds.y2;
    }
    double compare(rectXY rect,picture pic,pattern p){
        int x=0;int y=0;
        int width=rect.x2-rect.x1+1;
        int heigth=rect.y2-rect.y1+1;
        int correct=0;
        if(width>p.pixels.length)
            x=p.pixels.length;
        else
            x=width;
        if(heigth>p.pixels[0].length)
            y=p.pixels[0].length;
        else
            y=heigth;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
               if(Math.abs(p.pixels[i][j].R-pic.colorsetmain.pixels[i+rect.x1][j+rect.y1][0])<10&&
                   Math.abs(p.pixels[i][j].G-pic.colorsetmain.pixels[i+rect.x1][j+rect.y1][1])<10&&
                   Math.abs(p.pixels[i][j].B-pic.colorsetmain.pixels[i+rect.x1][j+rect.y1][2])<10)
                    correct++;
            }


        }
        return(correct/(x*y));
    }
    double compare2(boolean[][] b1,boolean[][] b2){
       int xmin=0;int ymin=0;double correctcount=0;
       int xmax=0;int ymax=0;                      
       if(b1.length>=b2.length){
           xmin=b2.length;
           xmax=b1.length;
        }
       else{
           xmin=b1.length;
           xmax=b2.length;
       }
       if(b1[0].length>=b2[0].length){
           ymin=b2[0].length;
           ymax=b1[0].length;
       }
       else{
           ymin=b1[0].length;
           ymax=b2[0].length;
       }
       for (int i = 0; i < xmin; i++) {
            for (int j = 0; j < ymin; j++) {
                if(b1[i][j]==b2[i][j])
                    correctcount++;
            }

        }
       double count=(xmax*ymax);
       double wrongcount=(count-correctcount);
        return ((wrongcount/count)*100);
    }
    double compare3(boolean[][] pb,boolean[][] prect){
        double correct=0;
        double wrong=0;
        for (int i = 0; i < pb.length; i++) {
           for (int j = 0; j < pb[0].length; j++) {
               if(pb[i][j]==true){
                    if(prect[i][j]==true)
                    {
                        correct++;
                    }
                    else
                        wrong++;
               }
            }

        }
        return((wrong/(correct+wrong))*100);
    }
    BufferedImage Scale(BufferedImage srcimage,double scale,rectXY bound){
        int width=(int)((bound.x2-bound.x1+1)*scale);
        int height=(int)((bound.y2-bound.y1+1)*scale);
        if (width==0)
            width=1;
        if (height==0)
            height=1;
        BufferedImage newbuff1=new BufferedImage(width,height
                                                ,BufferedImage.SCALE_SMOOTH);
         srcimage=srcimage.getSubimage( bound.x1,bound.y1,(bound.x2-bound.x1+1),(bound.y2-bound.y1+1));
        Graphics2D graphics=newbuff1.createGraphics();
    
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(srcimage,0,0,width,height,null);
        graphics.dispose();
        /*BufferedImage newbuff2=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2=newbuff2.createGraphics();
        graphics2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2.drawImage(newbuff1,0,0,width,height,null); 
        graphics2.dispose();*/
        return newbuff1;
    }
    BufferedImage Scale0(BufferedImage srcimage,double scale){
        int width=(int)(srcimage.getWidth()*scale);
        int height=(int)((srcimage.getHeight())*scale);
        if (width==0)
            width=1;
        if (height==0)
            height=1;
        BufferedImage newbuff=new BufferedImage(width,height
                                                ,BufferedImage.TYPE_INT_RGB); 
        Graphics2D graphics=newbuff.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(srcimage, 0,0,width,height,null);
        graphics.dispose();
        return newbuff;
    }
    boolean[][] getboolean(rectXY rect,picture pic){
        int w=rect.x2-rect.x1+1;
        int h=rect.y2-rect.y1+1;
        boolean[][] temp=new boolean[w][h];
        for (int i = 0; i <w ; i++) {
           for (int j = 0; j <h ; j++) {
               if(!(pic.colorsetmain.pixels[rect.x1+i][rect.y1+j][0]>setting.RGB&&
                   pic.colorsetmain.pixels[rect.x1+i][rect.y1+j][1]>setting.RGB&&
                   pic.colorsetmain.pixels[rect.x1+i][rect.y1+j][2]>setting.RGB)) 
                   temp[i][j]=true;
               else
                   temp[i][j]=false;
            }

        }
        return temp;

    }
}
