package oops_assignment;
import javax.swing.*;  
import java.sql.*;       //.* because we want all the interfaces and classes
import  java.awt.event.*;
public class LoginPage { 
	public static Connection con;
	
	protected static JFrame f;

	public LoginPage() {
		try {
			con=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Rashi\\Desktop\\QuestionBank\\question_bank.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f=new JFrame("Login page");//creating instance of JFrame  
		f.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		{
			JTextField t1,t2;  
			t1=new JTextField();  
			t1.setBounds(20,40,100,20); 

			t2=new JPasswordField();  
			t2.setBounds(30,60,60,20);  
			f.add(t1); f.add(t2);  
			f.setSize(300,300);  

			JButton b=new JButton("click");//creating instance of JButton  
			b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					if(t1.getText().equals("rashi") && t2.getText().equals("sharma")) {
						f.remove(t1);
						f.remove(t2);
						f.remove(b);
					
					
						Panel p = new Panel();
						p.loadGUI();
									
					}
				}
			});
			f.getRootPane().setDefaultButton(b);
			
			f.add(b);//adding button in JFrame  
			f.setSize(400,500);//400 width and 500 height  
			f.setLayout(null);//using no layout managers    
		}
	}
	private void loadGUI() {
		f.setVisible(true);
	}	
	public static void main(String args[])
	{
		LoginPage l=new LoginPage();
		l.loadGUI();
	}
}