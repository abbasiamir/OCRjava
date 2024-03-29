package test1;

import java.awt.image.BufferedImage;

import java.io.File;

import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Patterns {
    Setting setting=new Setting();
    public Patterns(Setting sett) {
        super();
       setting=sett;
    }
    /*public Patterns(){
        //buffers=bs;
        //Image image = buffers[0].getScaledInstance(10, 10, 0);
        
    }*/
    ArrayList<String> Names=new ArrayList<String>();
    ArrayList<BufferedImage> buffers=new ArrayList<BufferedImage>();
   
    /*public class rectWH{
        public rectWH(int X,int Y,int W,int H){
            x=X;y=Y;w=W;h=H;         
        }
        public rectWH(){
            
        }
        int x,y,w,h;
    }
    public class rectXY{
        public rectXY(){
        
        }
        public  rectXY(int X1,int Y1,int X2,int Y2){
           x1=X1;y1=Y1;x2=X2;y2=Y2;
        }
        int x1,y1,x2,y2;
    }
    public class pixelcolor{
        int R=0;
        int G=0;
        int B=0;
    }
    */
    public class colorset{
        
        public colorset(rectWH b){
           pixels=new pixelcolor[b.w][b.h]; 
           bound=b;
        }
        rectWH bound=new rectWH();
        pixelcolor[][] pixels;

        
    }
    void Create(){
        buffers=load();
        makepatterns();
    }
    public ArrayList<BufferedImage> load(){
       
       ArrayList<BufferedImage> buffers=new ArrayList<BufferedImage>();
       String dir=System.getProperty("user.dir");
       File file=new File(dir+"/Patterns");
        File[] file_2 = file.listFiles();
        for (int i=0; i<file_2.length; i++) {
            try{
            BufferedImage img;
            img=ImageIO.read(file_2[i]);
                buffers.add(img);
                Names.add(file_2[i].getName());
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return buffers;
    }
    void makepatterns(){
        for (BufferedImage buffer : buffers) {
            pattern p=new pattern(buffer,setting);
            
            p.MakebPattern();
            pattern_s.add(p);
        }
    }
    ArrayList<pattern> pattern_s=new ArrayList<pattern>();

}
   /* public class pattern{
        public pattern(BufferedImage b){
            bufi=b;
            make();
        }
        void make(){
            pixels=new pixelcolor[bufi.getWidth()][bufi.getHeight()];
           
            makecolorset();
            bound=makerect(); 
        }
        String Name="";
        pixelcolor[][] pixels;
        rectXY bound =new rectXY();
       
        boolean[][] bPattern;
        void MakebPattern(){
           rectXY from=makerect();
           bPattern=new boolean[from.x2-from.x1+1][from.y2-from.y1+1];
           for (int i = from.x1; i <= from.x2; i++) {
              for (int j = from.y1; j <= from.y2; j++) {
                  if(pixels[i][j].R<150&&pixels[i][j].G<150&&pixels[i][j].B<150){
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
            for (int i = 0; i < I; i++) {
                all=true;
                for (int j = 0; j < J; j++) {
                    if(Mod=="black"&&!(pixels[i][j].R>180&&
                        pixels[i][j].G>180&&
                        pixels[i][j].B>180))
                    {
                        beginx=i;
                        Mod="white";
                    }
                    if(Mod=="white"&&!(pixels[i][j].R>180&&
                        pixels[i][j].G>180&&
                        pixels[i][j].B>180)) {
                    all=false;
                    }
                    
                }
                if(Mod=="white"&&all){
                   endx=i-1;
                   break;
                    
                }

            } 
            J=bufi.getHeight();
            Mod="black";
            for (int j = 0; j < J; j++) {
                all=true;
                for (int i = beginx; i <= endx; i++) {
                    if(Mod=="black"&&!(pixels[i][j].R>150&&
                        pixels[i][j].G>150&&
                        pixels[i][j].B>150))
                    {
                        beginy=j;
                        Mod="white";
                    }
                    if(Mod=="white"&&!(pixels[i][j].R>150&&
                        pixels[i][j].G>150&&
                        pixels[i][j].B>150)) {
                    all=false;
                    }
                    
                }
                if(Mod=="white"&&!all){
                   endy=j-1;
                   
                    
                }

            }
            return (new rectXY(beginx,beginy,endx,endy));
        }
    }*/
    