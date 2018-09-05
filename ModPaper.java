package oops_assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class ModPaper {
	JFrame jframe = LoginPage.f;
	JPanel j = new JPanel();
	JPanel ques = new JPanel();
	JButton modifyb1,cancel;
	JScrollPane sc= new JScrollPane (ques);
	int noofquestions;
	String subject;
	String type;
	public static int selectedID;

	public ModPaper(String sub,String t)
	{
		type=t;
		subject=sub;
		j.setLayout(null);
		j.setBounds(0, 0, 400, 400);
		ques.setBounds(25,25,300,230);
		sc.setBounds(0,0,350,260);
		modifyb1=new JButton("Edit");//creating instance of JButton  
		modifyb1.setBounds(25,265,100,40);

		cancel=new JButton("cancel");//creating instance of JButton  
		cancel.setBounds(175,265,100,40);//x axis, y axis, width, height
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jframe.remove(j);
				Panel p = new Panel();
				p.loadGUI();

				try {
					
					LoginPage.con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
					PreparedStatement pr = LoginPage.con.prepareStatement("SELECT COUNT (*) FROM "+subject+" WHERE type = ?");
					pr.setString(1,type);
					noofquestions = pr.executeQuery().getInt(1);
				
	
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
			}
		});
		modifyb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InQuest in = new InQuest();
				try {
					
					
					PreparedStatement pr=LoginPage.con.prepareStatement("SELECT * FROM "+subject+" WHERE Id = ?");
					pr.setString(1,""+selectedID);
					ResultSet rs=pr.executeQuery();
					String[] array = new String[] {rs.getString("QUESTION"),rs.getString("Option1"),rs.getString("Option2"),rs.getString("Option3"),rs.getString("Option4"),rs.getString("ANSWER")};
					if(type.equals("MCQ")) in.modQuestion(array[0],array[1],array[2],array[3],array[4],array[5]);
					if(type.equals("True/False")) in.modQuestion(array[0],array[5].equals("True")?true:false);
					if(type.equals("Fill in the blanks"))in.modQuestion(array[0],array[5]);
					
				}
				catch(SQLException sql) {
					
					sql.printStackTrace();
				}
				int choice=-1;
				if(type.equals("MCQ")) choice = 0;
				if(type.equals("True/False")) choice =1;
				if(type.equals("Fill in the blanks")) choice=2;
				jframe.remove(j);
				in.loadGUI(choice, subject);

			}
		});
		j.add(ques);
		j.add(modifyb1);
		j.add(cancel);
		j.add(sc); 
		jframe.add(j);
	}		
	public void loadGUI() {
		ButtonGroup buttongrp = new ButtonGroup();
		ques.setLayout(new BoxLayout(ques, BoxLayout.Y_AXIS));
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		try {
			
		
			PreparedStatement p=LoginPage.con.prepareStatement("SELECT * FROM "+subject+" WHERE type= ?");
			p.setString(1, type);
			ResultSet rs = p.executeQuery();
			
			while(rs.next()) {
				JRadioButton j = new JRadioButton(rs.getString("QUESTION"));
				j.addActionListener(new ActionListener() {
					int id = rs.getInt("Id");
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							if(j.isSelected())	selectedID=id;
							System.out.println(""+selectedID);
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					
				});
				buttongrp.add(j);
				ques.add(j);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(jframe);
	}
}

