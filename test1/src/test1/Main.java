package test1;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Main {
    Setting setting=new Setting();
    public Main(File[] f,Setting sett,boolean checked) {
      files=f;//فایلهای خوانده شده از قسمت فریم را در آرایه تعریف شده قرار میدهیم
      setting=sett;//برای انتقال کلاس settingبه کلاسpatternrecognition
      allinone=checked;//برای ذخیره فایلها در یک فایل دلخواه
    }
    File[] files;//یک آرایه از فایلها تعریف میکنیم تا فایلهای خوانده شده را در آن قرار دهیم
    boolean allinone;
    void processimages(JTextArea list) {
        boolean failed=false;
        int page=1;
        for(int i=0;i<files.length;i++) {
            String filetext;
            String path=files[i].getPath();
            String filename=files[i].getName();
            path=path.substring(0,path.lastIndexOf("\\")+1);
            filename=filename.substring(0,filename.indexOf( "."))+".txt";
            ArrayList<String> arr=new ArrayList<String>();
            PatternRecognition pattrecog =new PatternRecognition(setting);
            //این خط نمونه ای از کلاس patternrecognitionمیسازد و شیئ settingرا به آن پاس میدهد
            BufferedImage buffer=null;
            try
            {
                
            
                 buffer=ImageIO.read(files[i]);//فایل را خوانده و در یک buffer قرار میدهیم
                //int type=buffer.getType();
               
            }
            catch(Exception ex){
               
               System.out.println(ex.getMessage());
            }
               
            arr=pattrecog.MakeText(buffer,list);
            //این خط تابع اصلی maketextرا از شیئ کلاسpatternrecognitionدا میزند که این تابع فرا خواننده
            //بقیه تابع ها است
            if(!allinone){
                for (int x=0;x<arr.size();x++) {
                    filetext=(String)arr.get(x);
                    File file=new File(path+Integer.toString(page++)+"_"+filename);
                    try{
                       // if(!file.exists()){
                            //file.renameTo(new File(file.getName()))
                            file.createNewFile();
                        //}
                    }
                    catch(Exception ex){
                        System.out.println(ex.getMessage());
                    }
                    FileWriter fw;
                    try{
                        fw=new FileWriter(file);
                        if(file.canWrite())
                            fw.write(filetext);
                        fw.close();
                    }
                    catch(Exception ex){
                        System.out.println();
                    }
                    
                }
            }
            else{
               JFileChooser jf=new JFileChooser();
                jf.setDialogType(1);
                jf.setDialogTitle("Please input file name");
                FileFilter filter=new FileNameExtensionFilter("Text File(*.txt)","txt");
                jf.addChoosableFileFilter(filter);  
                FileFilter filter2=new FileNameExtensionFilter("Text File(*.text)","text");
                jf.addChoosableFileFilter(filter2);
               if(jf.showSaveDialog(null )==JFileChooser.APPROVE_OPTION){
                    String filestext="";
                    File savefilename=jf.getSelectedFile();
                    for (int x=0;x<arr.size();x++) 
                        filestext+=(String)arr.get(x)+"\r\n\r\n\r\n";
                    try{
                        savefilename.createNewFile();
                        FileWriter fw=new FileWriter(savefilename);
                        fw.write(filestext);
                        fw.close();
                    }
                    catch(Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
                        
            }
            
        }
    }
    
}
