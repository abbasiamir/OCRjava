package test1;

import java.awt.image.BufferedImage;


    public class pattern{
        public pattern(BufferedImage b,Setting sett){
            setting=sett;
            bufi=b;
            make();
        }
        void make(){
            pixels=new pixelcolor[bufi.getWidth()][bufi.getHeight()];
           
            makecolorset();
            bound=makerect(); 
        }
        String Name="";
        Setting setting=new Setting();
        pixelcolor[][] pixels;
        rectXY bound =new rectXY();
       
        boolean[][] bPattern;
        void MakebPattern(){
           rectXY from=makerect();
           bPattern=new boolean[from.x2-from.x1+1][from.y2-from.y1+1];
           for (int i = from.x1; i <= from.x2; i++) {
              for (int j = from.y1; j <= from.y2; j++) {
                  if(!(pixels[i][j].R>setting.ptolerance&&pixels[i][j].G>setting.ptolerance&&pixels[i][j].B>setting.ptolerance)){
                      bPattern[i-from.x1][j-from.y1]=true;
                  }
                   else
                    bPattern[i-from.x1][j-from.y1]=false; 
                }
            }

        }
        BufferedImage bufi;
        void makecolorset(){
            int x=0;
            int y=0;
            //colorset colorsetmain=new colorset(new rectWH(0,0, bufi.getWidth(), bufi.getHeight()));
            int[] ar=new int[bufi.getWidth()*bufi.getHeight()*3];
            int[] colorsettemp=bufi.getData().getPixels(0, 0, bufi.getWidth(), bufi.getHeight(), ar);
            int lenght=colorsettemp.length;
            for(int i=0;i<lenght;) {
                pixels[x][y]=new pixelcolor();
                pixels[x][y].R=colorsettemp[i++];
                pixels[x][y].G=colorsettemp[i++];
                pixels[x][y].B=colorsettemp[i++];
                x++;
                if(x==bufi.getWidth()){
                    x=0;
                    y++;
                }
            }
            
        }
        rectXY makerect(){
            int beginx=0;int endx=0;int beginy=0;int endy=0;
            String Mod="black";
            boolean all=true;
            int I=bufi.getWidth();
            int J=bufi.getHeight();
            endx=I-1;
            endy=J-1;
            boolean first=true;
            for (int i = 0; i < I; i++) {
                all=true;
                for (int j = 0; j < J; j++) {
                    if(Mod=="black"&&!(pixels[i][j].R>setting.ptolerance&&
                        pixels[i][j].G>setting.ptolerance&&
                        pixels[i][j].B>setting.ptolerance))
                    {
                        if(first){
                            beginx=i;
                            first=false;
                        }
                        Mod="white";
                    }
                    if(Mod=="white"&&!(pixels[i][j].R>setting.ptolerance&&
                        pixels[i][j].G>setting.ptolerance&&
                        pixels[i][j].B>setting.ptolerance)) {
                    all=false;
                    }
                    
                }
                if(Mod=="white"&&all){
                   endx=i-1;
                  Mod="black";
                    
                }

            } 
            J=bufi.getHeight();
            Mod="black";
            first=true;
            for (int j = 0; j < J; j++) {
                all=true;
                for (int i = beginx; i <= endx; i++) {
                    if(Mod=="black"&&!(pixels[i][j].R>setting.ptolerance&&
                        pixels[i][j].G>setting.ptolerance&&
                        pixels[i][j].B>setting.ptolerance))
                    {
                        if(first){
                            beginy=j;
                            first=false;
                        }
                        Mod="white";
                    }
                    if(Mod=="white"&&!(pixels[i][j].R>setting.ptolerance&&
                        pixels[i][j].G>setting.ptolerance&&
                        pixels[i][j].B>setting.ptolerance)) {
                    all=false;
                    }
                    
                }
                if(Mod=="white"&&all){
                   endy=j-1;
                    Mod="black";
                    
                }
                if(Mod=="white"&&j==J-1)
                    endy=J-1;
            }
            
            return (new rectXY(beginx,beginy,endx,endy));
        }
    }
    
