package oops_assignment;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.sql.PreparedStatement;

public class Generate {
	JFrame jframe = LoginPage.f;
	JPanel panel = new JPanel(new GridLayout(3,1));
	JTextField numq = new JTextField("Enter number of questions here");
	JButton ok = new JButton("OK");
	JButton cancel = new JButton("Cancel");
	int noofquestions;
	public Generate(String subject, String type) {
		panel.setBounds(25,25,150,150);

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int num;
				try {
					num = Integer.parseInt(numq.getText());
					int[] ids = new int[noofquestions];
					int count=0;
					try{
						PreparedStatement prep = LoginPage.con.prepareStatement("SELECT * FROM "+subject+" WHERE type = ?");
						prep.setString(1,type);
						ResultSet xtra = prep.executeQuery();
						while(xtra.next()){
							ids[count++]=xtra.getInt("Id");
						}
					}
					catch(SQLException se){
						System.out.println("Not this one");
						se.printStackTrace();
					}
					count=0;
					Random random = new Random();
					ResultSet rs;
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");
					LocalDateTime now = LocalDateTime.now();
					String time = dtf.format(now);
					try(FileWriter fw = new FileWriter("C:\\Users\\Rashi\\Desktop\\QuestionBank\\"+subject+"_"+type+"_"+time+".txt", true);
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter out = new PrintWriter(bw);
							FileWriter fw2 = new FileWriter("C:\\Users\\Rashi\\Desktop\\QuestionBank\\"+subject+"_"+type+"_"+time+"solutions.txt", true);
							BufferedWriter bw2 = new BufferedWriter(fw2);
							PrintWriter out2 = new PrintWriter(bw2)){
						while(count<num){
							int idno = java.lang.Math.abs(((random.nextInt())%(noofquestions)));
							if(ids[idno]!=0){
								if(type.equals("MCQ")){
									PreparedStatement p=LoginPage.con.prepareStatement("SELECT * FROM "+subject+" WHERE Id = ?");
									p.setString(1,""+ids[idno]);
									rs=p.executeQuery();
									
									out.println((count+1)+")\t"+rs.getString("QUESTION")+"\n\n\ta)"+rs.getString("Option1")+"\n\tb)"+rs.getString("Option2")+"\n\tc)"
											+rs.getString("Option3")+"\n\td)"+rs.getString("Option4")+"\n");
									out2.println((count+1)+")\t"+rs.getString("QUESTION")+"\n\n\tAnswer: "+rs.getString("ANSWER")+"\n");
								}
								else{
									PreparedStatement p=LoginPage.con.prepareStatement("SELECT QUESTION, ANSWER FROM "+subject+" WHERE Id = ?");
									p.setString(1,""+ids[idno]);
									rs=p.executeQuery();
									
									out.println((count+1)+")\t"+rs.getString("QUESTION")+"\n");
									out2.println((count+1)+")\t"+rs.getString("QUESTION")+"\n\n\tAnswer: "+rs.getString("ANSWER")+"\n");                                            
								}
								ids[idno]=0;
								count++;
							}
							else{
								continue;
							}
						}
					}
					catch(Exception io){
						io.printStackTrace();
					}
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,"File Created");
					if(Desktop.isDesktopSupported()){
						Desktop.getDesktop().open(new File("C:\\Users\\Rashi\\Desktop\\QuestionBank\\"+subject+"_"+type+"_"+time+".txt"));
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}

		});


		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jframe.remove(panel);
				Panel p = new Panel();
				p.loadGUI();

			}

		});
		panel.add(numq);
		panel.add(ok);;
		panel.add(cancel);
		jframe.add(panel);
		SwingUtilities.updateComponentTreeUI(jframe);

		SwingUtilities.updateComponentTreeUI(jframe);
		try {
					LoginPage.con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
					PreparedStatement p = LoginPage.con.prepareStatement("SELECT COUNT (*) FROM "+subject+" WHERE type = ?");
					p.setString(1,type);
					noofquestions = p.executeQuery().getInt(1);
		}
		catch(SQLException se) {
			System.out.println("This one");
			se.printStackTrace();
		}

	}
}
