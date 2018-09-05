package oops_assignment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import java.sql.PreparedStatement;

public class DelPaper {
	JFrame jframe = LoginPage.f;
	JPanel ques1 = new JPanel();
	JPanel j = new JPanel();
	JScrollPane sc1= new JScrollPane (ques1);
	JButton delete,cancel;
	String subject,type;
	int noofquestions;

	public DelPaper(String sub,String t)
	{
		try {
			LoginPage.con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			PreparedStatement p = LoginPage.con.prepareStatement("SELECT COUNT (*) FROM "+sub+" WHERE type = ?");
			p.setString(1,t);
			noofquestions = p.executeQuery().getInt(1);
		}
		catch(SQLException se) {
			se.printStackTrace();
		}

		subject = sub;
		type = t;
		j.setLayout(null);
		j.setBounds(0, 0, 400, 400);

		delete=new JButton("Delete");//creating instance of JButton  
		delete.setBounds(25,265,100,40);
		ques1.setBounds(25,25,300,230);
		sc1.setBounds(0,0,300,260);

		cancel=new JButton("cancel");//creating instance of JButton  
		cancel.setBounds(175,265,100,40);//x axis, y axis, width, height
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jframe.remove(j);
				Panel p = new Panel();
				p.loadGUI();
			}
		});
	}
	public void loadGUI() {
		JCheckBox[] arr = new JCheckBox[noofquestions];
		int[] arr2 = new int[noofquestions];
		ques1.setLayout(new BoxLayout(ques1, BoxLayout.Y_AXIS));
		sc1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		int k=0;
		try {
			
			PreparedStatement p=LoginPage.con.prepareStatement("SELECT * FROM "+subject+" WHERE type= ?");
					p.setString(1, type);
					
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				arr[k] = new JCheckBox(rs.getString("QUESTION"));
				arr2[k] = Integer.parseInt(rs.getString("Id"));
				ques1.add(arr[k++]);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		j.add(ques1);
		j.add(delete);
		j.add(cancel);
		j.add(sc1); 
		jframe.add(j);

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int it;
				for(it=0;it<noofquestions;it++) {
					if(arr[it].isSelected()) {
						try {
							PreparedStatement p=LoginPage.con.prepareStatement("DELETE FROM "+subject+" WHERE Id= ?");
						    p.setString(1,""+arr2[it]);
						    p.executeUpdate();
						    
						}
						catch(SQLException sql) {
							sql.printStackTrace();
						}
					}
				}
				jframe.remove(j);
				Panel p = new Panel();
				p.loadGUI();
			}
		});

		SwingUtilities.updateComponentTreeUI(jframe);
	}
}
