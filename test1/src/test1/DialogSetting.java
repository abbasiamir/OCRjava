package test1;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class DialogSetting extends JDialog {
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();
    private JTextField jTextField1 = new JTextField();
    private JTextField jTextField2 = new JTextField();
    private JTextField jTextField3 = new JTextField();
    private JTextField jTextField4 = new JTextField();
    private JTextField jTextField5 = new JTextField();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    public Setting sett;
    private JButton jButton4 = new JButton();

    public DialogSetting(Setting set) {
        this(null, "Frame", false);
        sett=set;
    }

    public DialogSetting(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(449, 300));
        this.getContentPane().setLayout( null );
        this.setModal(true);
        this.setLocationRelativeTo(jLabel1);
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {
                    this_windowOpened(e);
                }
            });
        jButton1.setText("Close");
        jButton1.setBounds(new Rectangle(350, 230, 75, 21));
        jButton1.setForeground(Color.red);
        jButton1.setBackground(new Color(237, 63, 231));
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jButton2.setText("Save");
        jButton2.setBounds(new Rectangle(140, 230, 75, 20));
        jButton2.setForeground(new Color(0, 185, 0));
        jButton2.setBackground(new Color(237, 231, 71));
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        jButton3.setText("Default");
        jButton3.setBounds(new Rectangle(45, 230, 75, 20));
        jButton3.setDoubleBuffered(true);
        jButton3.setForeground(Color.blue);
        jButton3.setBackground(new Color(237, 230, 188));
        jButton3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton3_actionPerformed(e);
                }
            });
        jTextField1.setBounds(new Rectangle(380, 20, 40, 25));
        jTextField2.setBounds(new Rectangle(380, 50, 40, 25));
        jTextField3.setBounds(new Rectangle(380, 85, 40, 25));
        jTextField4.setBounds(new Rectangle(380, 120, 40, 25));
        jTextField5.setBounds(new Rectangle(380, 155, 40, 25));
        jLabel1.setText("the contrast forpage whiter background larger number(0-255)");
        jLabel1.setBounds(new Rectangle(5, 10, 375, 40));
        jLabel1.setForeground(new Color(0, 66, 198));
        jLabel1.setFont(new Font("Times New Roman", 0, 16));
        jLabel1.setInheritsPopupMenu(false);
        jLabel2.setText("�?��� �����?? ��?�� ����?�");
        jLabel2.setBounds(new Rectangle(155, -65, 215, 40));
        jLabel2.setForeground(new Color(0, 66, 198));
        jLabel2.setFont(new Font("Simplified Arabic", 0, 16));
        jLabel4.setText("same above but for patterns");
        jLabel4.setBounds(new Rectangle(145, 45, 215, 40));
        jLabel4.setForeground(new Color(71, 198, 194));
        jLabel4.setFont(new Font("Arial", 0, 16));
        jLabel5.setText("maximum tolerance for bitwise compare");
        jLabel5.setBounds(new Rectangle(65, 75, 280, 40));
        jLabel5.setForeground(new Color(198, 15, 198));
        jLabel5.setFont(new Font("Arial", 0, 16));
        jLabel6.setText("maximum tolerance for compare histograme above letter");
        jLabel6.setBounds(new Rectangle(65, 115, 305, 40));
        jLabel6.setForeground(new Color(198, 34, 96));
        jLabel6.setFont(new Font("Arial", 0, 16));
        jLabel7.setText("minimum width of pages size");
        jLabel7.setBounds(new Rectangle(145, 150, 205, 40));
        jLabel7.setForeground(new Color(198, 186, 26));
        jLabel7.setFont(new Font("Arial", 0, 16));
        jButton4.setText("Load");
        jButton4.setBounds(new Rectangle(245, 230, 75, 21));
        jButton4.setForeground(new Color(210, 210, 0));
        jButton4.setFont(new Font("Tahoma", 1, 13));
        jButton4.setBackground(new Color(105, 237, 232));
        jButton4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton4_actionPerformed(e);
                }
            });
        this.getContentPane().add(jButton4, null);
        this.getContentPane().add(jLabel7, null);
        this.getContentPane().add(jLabel6, null);
        this.getContentPane().add(jLabel5, null);
        this.getContentPane().add(jLabel4, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(jTextField5, null);
        this.getContentPane().add(jTextField4, null);
        this.getContentPane().add(jTextField3, null);
        this.getContentPane().add(jTextField2, null);
        this.getContentPane().add(jTextField1, null);
        this.getContentPane().add(jButton3, null);
        this.getContentPane().add(jButton2, null);
        this.getContentPane().add(jButton1, null);
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    
    private void setdefault(){
        jTextField1.setText("175");
        jTextField2.setText("175");
        jTextField3.setText("16");
        jTextField4.setText("1.2");
        jTextField5.setText("10");
    }
    private void jButton3_actionPerformed(ActionEvent e) {
        setdefault();
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        
        try{
            File file=new File(System.getProperty("user.dir")+"//setting.txt");
           
            FileWriter fs=new FileWriter(file);
            fs.write(jTextField1.getText()+"\r\n");
            fs.write(jTextField2.getText()+"\r\n");
            fs.write(jTextField3.getText()+"\r\n");
            fs.write(jTextField4.getText()+"\r\n");
            fs.write(jTextField5.getText()+"\r\n");
            fs.close();
        }
        catch(Exception ex){
                 JOptionPane.showMessageDialog(null,ex.getMessage());
                 };
        sett.RGB=Integer.parseInt( jTextField1.getText());
        sett.ptolerance=Double.parseDouble( jTextField2.getText());
        sett.telorance=Double.parseDouble( jTextField3.getText());
        sett.histogram=Double.parseDouble(jTextField4.getText());
        sett.Minpagesize=Double.parseDouble( jTextField5.getText());
    }

    private void this_windowOpened(WindowEvent e) {
        
                jTextField1.setText(String.valueOf(sett.RGB));
                 jTextField2.setText(String.valueOf(sett.ptolerance));
                 jTextField3.setText(String.valueOf(sett.telorance));
                 jTextField4.setText(String.valueOf(sett.histogram));
                 jTextField5.setText(String.valueOf(sett.Minpagesize));
                 return;
            }
          
        
    
    private void setting2dlg(Setting s) {
        jTextField1.setText(String.valueOf(s.RGB));
         jTextField2.setText(String.valueOf(s.ptolerance));
         jTextField3.setText(String.valueOf(s.telorance));
         jTextField4.setText(String.valueOf(s.histogram));
         jTextField5.setText(String.valueOf(s.Minpagesize));
    }


    private void jButton4_actionPerformed(ActionEvent e) {
       char[] all=new char[500];
        try{
            //File file=new File(System.getProperty("user.dir")+"//setting.txt");
        
           
            FileReader fr=new FileReader(System.getProperty("user.dir")+"//setting.txt");
            fr.read(all);
           
            fr.close();
           
              
          
        }
        catch(Exception ex){
        Dialog dlg=new Dialog(this,"��?� ?��� ���");
         dlg.setVisible(true);
         dlg.setModal(true);
         
           JOptionPane.showMessageDialog(null,ex.getMessage());
    }
        String[] ones={"","","","",""};
   for (int i = 0; i < all.length; i++) {
       int snum=0;
       if(all[i]!='\n'&&all[i]!='\r')
           ones[snum]+=all[i];
       else
           if(all[i]=='\n'&&snum<=3)
            snum+=3;
        }


        jTextField1.setText(ones[0]);
        //jTextField2.setText(ones[0]);
    jTextField2.setText(ones[1]);
    jTextField3.setText(ones[2]);
    jTextField4.setText(ones[3]);
    jTextField5.setText(ones[4]);
    } 
}
