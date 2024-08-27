package Movie;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
class DatabaseBackend {    
    private static Connection connection;
    public static void connect() throws SQLException {
    	connection = DriverManager.getConnection("jdbc:mysql://localhost/movie","root","" );
    }    
    public static void insertData(String MovieName,String ShowTime,String SeatType,String Noofseats, String Date) throws SQLException {
        String query = "INSERT INTO ticket (MovieName,ShowTime,SeatType,Noofseats,Date) " + "VALUES (?, ?, ?, ?, ?)";        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, MovieName);
            preparedStatement.setString(2, ShowTime);
            preparedStatement.setString(3, SeatType);
            preparedStatement.setString(4, Noofseats);
            preparedStatement.setString(5, Date);
            preparedStatement.executeUpdate();
        } 
    }
    public static void InsertUser(String Username,String Password) throws SQLException {
        String query = "INSERT INTO userlogin (Username,Password) " + "VALUES (?, ?)";        
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(query))
        {
            preparedStatement1.setString(1, Username);
            preparedStatement1.setString(2, Password);
            preparedStatement1.executeUpdate();
        } 
    }
    public static void closeConnection() throws SQLException
    {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
public class Movieticketbooking {
    static void Login(){
        JFrame f1=new JFrame("Login ");
        JTextField t1,t2;
        f1 = new JFrame();
        JLabel l1 = new JLabel("Username");
        t1 = new JTextField("");
        JLabel l2 = new JLabel("Password");
        t2 = new JTextField("");
        l1.setBounds(50,100,200,30);
        l2.setBounds(50,150,200,30);
        t1.setBounds(125,100,200,30);
        t2.setBounds(125,150,200,30);
        JButton b1 = new JButton("login");
        b1.setBounds(125,250,100,30);
        JButton b2 = new JButton("Cancel");
        b2.setBounds(225,250,100,30);
        b1.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent e){frame();
        Component f = null;
		try {
            DatabaseBackend.connect();
            DatabaseBackend.InsertUser(t1.getText(),t2.getText());

            JOptionPane.showMessageDialog(f, "Logined succefully");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, "Error occurred while loging in. Please try again.");
        } finally {
            try {
                DatabaseBackend.closeConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        }});
        b2.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent e){cancle();}});
        f1.add(t1);
        f1.add(t2);
        f1.add(l1);
        f1.add(l2);
        f1.add(b1);
        f1.add(b2);
        f1.getContentPane().setBackground(Color.white);
        f1.setSize(400,400);
        f1.setLayout(null);
        f1.setVisible(true);
        }
        static void frame(){
        String movie[]={"","Annebella","Nun","Avengers","Batman","Fast&Furious","Mission Impossible","Friends"};
        String time[]={"","10:00am","01:00pm","04:00pm","09:00pm","01:00am"};
        String seat[]={"","Front","Middle","Back","Balcony"};
        JFrame f;
       	JTextField t1,t6;
        f = new JFrame("Movie Ticket Booking");
	JLabel l1 = new JLabel("MovieName");
	JLabel l2 = new JLabel("ShowTime");
	JLabel l3 = new JLabel("SeatType ");
        JLabel l4 = new JLabel("Noofseats ");
        JLabel l7 = new JLabel("Date");
        t1 =new JTextField();
        t6 =new JTextField();
        t1.setBounds(150,300,200,30);
        t6.setBounds(150,250,200,30);
        JButton b1 = new JButton("Book");
	b1.setBounds(150,350,100,30);
        JButton b2 = new JButton("Cancle");
	b2.setBounds(300,350,100,30);
	f.getContentPane().setBackground(Color.white);
       	l1.setBounds(50,100,200,30);
	l2.setBounds(50,150,200,30);
	l3.setBounds(50,200,200,30);
        l4.setBounds(50,250,200,30);
        l7.setBounds(50,300,200,30);
        JComboBox cb1 = new JComboBox(movie);
        cb1.setEditable(false); 
        cb1.setBounds(150,100,200,30);
        f.add(cb1);
        JComboBox cb2 = new JComboBox(time);
        cb2.setBounds(150,150,200,30);
        cb2.setEditable(false);
        f.add(cb2);
        JComboBox cb3 = new JComboBox(seat); 
        cb3.setBounds(150,200,200,30);
        cb3.setEditable(false);       
        b1.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent e){booked();
         try {
                 DatabaseBackend.connect();
                 DatabaseBackend.insertData(cb1.getSelectedItem().toString(), cb2.getSelectedItem().toString(), cb3.getSelectedItem().toString(),t6.getText(), t1.getText());

                 JOptionPane.showMessageDialog(f, "Booking Successful");
             } catch (SQLException ex) {
                 ex.printStackTrace();
                 JOptionPane.showMessageDialog(f, "Error occurred while booking. Please try again.");
             } finally {
                 try {
                     DatabaseBackend.closeConnection();
                 } catch (SQLException ex) {
                     ex.printStackTrace();
                 }
             }
        
        }});
        b2.addActionListener(new ActionListener(){@Override
        public void actionPerformed(ActionEvent e){cancle();}});
       
        f.add(l7);
        f.add(t1);
        f.add(l4);
        f.add(l1);
	f.add(l2);
	f.add(l3);
	f.add(b1);
	f.add(t6);
        f.add(b2);
        f.add(l7);
        f.add(t1);
        f.add(l4);
        f.add(l1);
	f.add(l2);
	f.add(l3);
	f.add(b1);
        f.add(b2);
        f.add(cb3);
        f.getContentPane().setBackground(Color.white);
        f.setSize(600,600);
        f.setLayout(null);
        f.setVisible(true);
        }
        static void booked(){
        JFrame f2=new JFrame();
        JOptionPane.showMessageDialog(f2,"Confirm your booking","Alert",JOptionPane.NO_OPTION);
        }
        static void cancle(){
        JFrame f3=new JFrame();
        JOptionPane.showMessageDialog(f3,"Sorry for the inconvinence.","Alert",JOptionPane.WARNING_MESSAGE);
        }
    
        public static void main(String[] args) {
            Login();
        }
       

}
