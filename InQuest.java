package oops_assignment;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class InQuest {

	static boolean editMode=false;

	JFrame jframe = LoginPage.f;
	JPanel j = new JPanel();
	JTextField t3,t4,t5,t6,t7,t8;
	JButton b,cancel;
	JRadioButton r1,r2;
	public InQuest()
	{		

		j.setLayout(null);
		j.setBounds(0, 0, 400, 400);

		b=new JButton("OK");//creating instance of JButton  
		b.setBounds(25,265,100,40);

		cancel=new JButton("cancel");//creating instance of JButton  
		cancel.setBounds(275,265,100,40);//x axis, y axis, width, height
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				jframe.remove(j);
				Panel p = new Panel();
				p.loadGUI();
			}
		});

		r1=new JRadioButton("True");
		r2=new JRadioButton("False");   
		r1.setBounds(25,65,100,40);    
		r2.setBounds(200,65,100,40);    
		ButtonGroup bg=new ButtonGroup();    
		bg.add(r1);bg.add(r2); 

		t3=new JTextField();  
		t4=new JTextField();  
		t5=new JTextField();
		t6=new JTextField();
		t7=new JTextField();
		t8=new JTextField();
		t3.setBounds(25,25,350,30); 
		t4.setBounds(25,65,350,30);
		t5.setBounds(25,105,350,30);
		t6.setBounds(25,145,350,30);
		t7.setBounds(25,185,350,30);
		t8.setBounds(25,225,350,30);
	}
	protected void modQuestion(String question,String o1,String o2, String o3, String o4, String c) {

		editMode=true;
		t3.setText(question);
		t4.setText(o1);
		t5.setText(o2);
		t6.setText(o3);
		t7.setText(o4);
		t8.setText(c);
	}
	protected void modQuestion(String question,boolean c) {
		editMode=true;
		t3.setText(question);
		r2.setSelected(true);
		if(c)r1.setSelected(true);

	}
	protected void modQuestion(String question, String answer) {
		editMode=true;
		t3.setText(question);
		t4.setText(answer);
	}
	public void loadGUI(int choice,String sub) {
		switch(choice) {
		case 0:
			j.add(t3);
			j.add(t4);
			j.add(t5);
			j.add(t6);
			j.add(t7);
			j.add(t8);
			break;
		case 1:
			j.add(t3);
			j.add(r1);
			j.add(r2);
			break;
		case 2:
			j.add(t3);
			j.add(t4);
			break;
		}
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(editMode) { 
			try {
					System.out.println("It did run");
					//System.out.println(con);
					PreparedStatement p =LoginPage.con.prepareStatement("DELETE FROM "+sub+" WHERE Id= "+ModPaper.selectedID);
					p.executeUpdate();
					
					}
					catch(SQLException sql) {
						System.out.println("Error is from here");
						sql.printStackTrace();
					}
				}
				if(t3.getText().equals("") || (choice==0 && (t4.getText().equals("") || t5.getText().equals("") ||
						t6.getText().equals("") || t7.getText().equals("") || t8.getText().equals("")) ) || 
						(choice==0 && (!t8.getText().equals(t4.getText()) && !t8.getText().equals(t5.getText()) &&
								!t8.getText().equals(t6.getText()) && !t8.getText().equals(t7.getText())))
						|| (choice==2 && t4.getText().equals("") )) {
					JFrame abcd=new JFrame();
					JOptionPane.showMessageDialog(abcd, "You did something wrong");
				}
				else {
					if(choice==0) enterQuestion("MCQ",sub,t3.getText(),t4.getText(),t5.getText(),t6.getText(),t7.getText(),t8.getText());
					if(choice==1) enterQuestion("True/False",sub,t3.getText(),"True","False",r1.isSelected()?"True":"False");
					if(choice==2) enterQuestion("Fill in the blanks",sub,t3.getText(),t4.getText());
					jframe.remove(j);
					if(!editMode) {
						Panel p = new Panel();
						p.loadGUI();
					}
					else {
						String string="";
						if(choice==0) string = "MCQ";
						if(choice==1) string = "True/False";
						if(choice==2) string = "Fill in the blanks";
						ModPaper mod=new ModPaper(sub,string);
						mod.loadGUI();
					}
				}
			}
		});


		j.add(b);
		j.add(cancel);
		jframe.add(j);


		SwingUtilities.updateComponentTreeUI(jframe);
	}
	private void enterQuestion(String type,String sub,String q,String o1,String o2,String o3,String o4,String c) {
		try {
			
			PreparedStatement p=LoginPage.con.prepareStatement("INSERT INTO "+sub+"(type,QUESTION,Option1,Option2,Option3,Option4,ANSWER) VALUES(?,?,?,?,?,?,?)");
			p.setString(1,type);
			p.setString(2,q);
			p.setString(3,o1);
			p.setString(4,o2);
			p.setString(5,o3);
			p.setString(6,o4);
			p.setString(7,c);
			p.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}

	private void enterQuestion(String type,String sub,String q,String c) {
		try {
			
			PreparedStatement p=LoginPage.con.prepareStatement("INSERT INTO "+sub+"(type,QUESTION,ANSWER) VALUES(?,?,?)");
			p.setString(1,type);
			p.setString(2,q);
			p.setString(3,c);
			p.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}
	private void enterQuestion(String type,String sub,String q,String o1,String o2,String c) {
		try {
			PreparedStatement p=LoginPage.con.prepareStatement("INSERT INTO "+sub+"(type,QUESTION,Option1,Option2,ANSWER) VALUES(?,?,?,?,?)");
			p.setString(1,type);
			p.setString(2,q);
			p.setString(3,o1);
			p.setString(4,o2);
			p.setString(5,c);
			p.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}

