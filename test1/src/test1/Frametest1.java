package test1;


import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Frametest1 extends JFrame {
    private JTextField jTextField1 = new JTextField();
    private JButton jButton1 = new JButton();
    JScrollPane scroll=new JScrollPane();
    private JTextArea list1 = new JTextArea();
    private JLabel jLabel1 = new JLabel();
    private JCheckBox jCheckBox1 = new JCheckBox();
    private JButton jButton2 = new JButton();

    public Frametest1() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(800, 700));
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        jTextField1.setBounds(new Rectangle(35, 40, 175, 20));
        jButton1.setText("Open");
        jButton1.setBounds(new Rectangle(230, 40, 75, 21));
        jButton1.setActionCommand("Open");
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        list1.setBounds(new Rectangle(10, 115, 490, 130));
        scroll.setBounds(new Rectangle(10, 115, 490, 130));
        scroll.setSize(new Dimension(750, 500));
        list1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        list1.setFont(new Font("Arial", 0, 18));


        jLabel1.setText("Setting");
        jLabel1.setBounds(new Rectangle(575, 55, 60, 15));
        jLabel1.setSize(new Dimension(45, 15));
        jLabel1.setForeground(Color.blue);
        jLabel1.setFont(new Font("Arial", 0, 13));
        jLabel1.setMaximumSize(new Dimension(45, 15));
        jLabel1.setMinimumSize(new Dimension(45, 15));
        jLabel1.setPreferredSize(new Dimension(45, 15));
        jLabel1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jLabel1_mouseClicked(e);
                }
            });
        jCheckBox1.setText("All text in one file  ");
        jCheckBox1.setBounds(new Rectangle(575, 80, 170, 20));
        jCheckBox1.setActionCommand("All text in one file");
        jCheckBox1.setForeground(Color.red);
        jCheckBox1.setSelected(true);
        jCheckBox1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jCheckBox1_actionPerformed(e);
                }
            });
        jButton2.setText("Save");
        jButton2.setBounds(new Rectangle(355, 40, 75, 21));
        jButton2.setActionCommand("saveButton");
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        this.getContentPane().add(jButton2, null);
        this.getContentPane().add(jCheckBox1, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(scroll);
        this.getContentPane().add(list1, null);
        scroll.setViewportView(list1);
        //this.getContentPane().add(list1, null);
        this.getContentPane().add(jButton1, null);
        this.getContentPane().add(jTextField1, 0);
        
        
    }
    File[] files;
    Setting setting=new Setting();
   
    private void jButton1_actionPerformed(ActionEvent e) {
        
       open();
    }
    private void jLabel1_mouseClicked(MouseEvent e) { //label Setting Click
        DialogSetting dialog=new DialogSetting(setting);//باز نمودن پنجرهSetting
        dialog.setVisible(true);
    }

    private void this_windowOpened(WindowEvent e) {
        
           load();
        }
       

    private void open (){
       
        String defultpath="";
        list1.setText("");
        
         JFileChooser fdlg=new JFileChooser(System.getProperty("user.dir")+"//Images");
        //یک پنجره برای انتخاب فایل باز میکند که بصورتefoult
         //از پوشه Imagesدر مسیر جاری برنامه استفاده میکند
         fdlg.setMultiSelectionEnabled(true);//اجازه میدهد چند فایل همزمان انتخاب شوند
         int result=fdlg.showDialog(this, "Load");
         if(result==0)
             files=fdlg.getSelectedFiles();
         if(files!=null){
            
             jTextField1.setText("");
            
             
             for(int i=0;i<files.length;i++){
                 jTextField1.setText(jTextField1.getText()+files[i].getName()+";");
                 //نام فایلها را با تفکیک ; در TextFieldنشان میدهد
             }
             Main main=new Main(files,setting,jCheckBox1.isSelected());
             main.processimages(list1);    
             //فایلها را با متغیر های لازم به کلاس Main فرستاده تابعproccessimagesرا صدا میزند
         }
    }


 
    private void load(){
        char[] all=new char[500];
        try{
            File file=new File(System.getProperty("user.dir")+"//setting.txt");
            if (file.exists()){//فایل setting.txt را که حاوی تنظیمات است از مسیر جاری برنامه میخواند
            FileReader fr=new FileReader(file);
           fr.read(all);
            fr.close();}
            else{ //اگر فایل وجود نداشت از مقادیر پیش فرض استفاده میکند           
                    setting.RGB=175;
                    setting.ptolerance=175;
                    setting.telorance=16;
                    setting.histogram=1.2;
                    setting.Minpagesize=10;
                    return;
                }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        //چون اطلاعات بصورت رشته ای در فایل با "\r\n"جدا سازی شده اند
        //باید ازهم تفکیک گردند ودر آرایهones قرار گیرند
        String[] ones= {"","","","",""};
        int snum=0;
        for (int i = 0; i < all.length; i++) {
            if(all[i]!='\r'&&all[i]!='\n'){
               ones[snum]+=all[i]; 
            }
            else if(all[i]=='\n'&&snum<=3)
                snum++;
            else if(snum==4) break;
        }

        //مقادیر خوانده شده در کلاس ثبت میگردند
        setting.RGB=Integer.parseInt(ones[0].toString());
        setting.ptolerance=Double.parseDouble(ones[1]);
        setting.telorance=Double.parseDouble(ones[2]);
        setting.histogram=Double.parseDouble(ones[3]);
        setting.Minpagesize=Double.parseDouble(ones[4]);
    }

    private void jCheckBox1_actionPerformed(ActionEvent e) {
        
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        save();
    }
    private void save(){
        JFileChooser jf=new JFileChooser();
         jf.setDialogType(1);
         jf.setDialogTitle("Please input file name");
         FileFilter filter=new FileNameExtensionFilter("txt","txt");
         jf.addChoosableFileFilter(filter);  
         FileFilter filter2=new FileNameExtensionFilter("text","text file");
         jf.addChoosableFileFilter(filter2);
        //یک SaveDialogهمراه با فیلتر فایلها که پس از انتخاب فیلتر به پسوند فایل اضافه میشود
         if(jf.showSaveDialog(null )==JFileChooser.APPROVE_OPTION){
             String filestext=list1.getText();
             File savefilename=jf.getSelectedFile();
             File suffix=new File(savefilename.getAbsolutePath()+"."+jf.getFileFilter().getDescription());
             savefilename.renameTo(suffix);//اضافه کردن پسوند به فایل
             try{
                 savefilename.createNewFile();
                 FileWriter fw=new FileWriter(savefilename);
                //ساختن و نوشتن در فایل
                 fw.write(filestext);
                 fw.close();
             }
             catch(Exception ex){
                 JOptionPane.showMessageDialog(null, ex.getMessage());
             }
         }
                 
        }
    
}
