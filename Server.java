package chatting_application;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;//181
public class Server implements ActionListener{
    //Global variables
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Server(){
        
        //Header panel 
        
        f.setLayout(null);
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 500, 70); 
        p1.setLayout(null);
        f.add(p1);
        
        //Back button image
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back=new JLabel (i3);
        back.setBounds(8,20,25,25);
        p1.add(back);
        
        //To close window when we click back button symbol
        
        back.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent ae){//ae is a variable
               System.exit(0);
           }
    });
        
        //Profile photo
        
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile =new JLabel (i6);
        profile.setBounds(50,10,50,50);
        p1.add(profile);
        
         //Video call image
        
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video =new JLabel (i9);
        video.setBounds(340,20,30,30);
        p1.add(video);
        
        //Video call image
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone =new JLabel (i12);
        phone.setBounds(400,20,35,30);
        p1.add(phone);
        
         //three dots image
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert =new JLabel (i15);
        morevert.setBounds(460,20,10,25);
        p1.add(morevert);
        
        //Name 
        
        JLabel name = new JLabel("Manas");
        name.setBounds(112 , 15, 100, 22);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        //Status
        
        JLabel status = new JLabel("Last Active at 4:00 PM");
        status.setBounds(112 , 35, 150, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);
        
        //Footer panel
        
        a1 = new JPanel();
        a1.setBounds(5, 75, 489, 570);
        f.add(a1);
        
        //Text field
        
        text = new JTextField();
        text.setBounds(5,648,350,50);
        text.setFont(new Font("SAN_SERIF",  Font.PLAIN, 20));
        f.add(text);
        
        //Send button
        
        JButton send = new JButton("Send");
        send.setBounds(360, 648, 135, 49);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN, 20));
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);//On click function
        f.add(send);
        
        f.setSize(500, 700);
        f.setUndecorated(true);//to remove( -, X, full sceen) options
        f.getContentPane().setBackground(Color.WHITE);
        f.setLocation(200, 50);
        f.setVisible(true);//Default will be invisible
    }
    
    public void actionPerformed(ActionEvent ae){
        try{
            String out = text.getText();//Saves text entered in text field in variable out
            //System.out.println(out);
        
            JLabel output = new JLabel(out);
        
            JPanel p2 = formatLabel(out);
        
            a1.setLayout(new BorderLayout());
        
            //sent messages display
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));//Space between two message boxes vertically
        
            a1.add(vertical, BorderLayout.PAGE_START);
        
            dout.writeUTF(out);
        
            text.setText(" ");//When we click send button the text area should become empty
        
            f.repaint();
            f.invalidate();
            f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 18));
        output.setBackground(new Color(77, 77, 77));
        output.setOpaque(true);
        output.setForeground(Color.WHITE);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel; 
    }
    
    public static void main(String[] args){
        new Server();
        
        try{
            ServerSocket skt = new ServerSocket(6001);//Creating server (6001 port no
            while(true){//to accept infinite msgs
                Socket s = skt.accept();// storing from where the msg was accepted
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
