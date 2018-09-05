package oops_assignment;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import java.awt.event.ActionListener;
import javax.swing.*;

public class Panel {
	
	JFrame jframe;
	Choice c=new Choice();
	Choice sub = new Choice();
	
	protected JPanel j = new JPanel(new GridLayout(7,1));
	
	JButton m=new JButton("Modify");
	
	
	JButton i=new JButton("Insert");
	JButton d=new JButton("Delete");
	JButton g=new JButton("Generate");
	public void loadGUI() {
		
		sub.add("Maths");
		sub.add("OOPS");
		sub.add("Logic");
		
		c.add("MCQ");
		c.add("True/False");
		c.add("Fill in the blanks");
		
		m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			jframe.remove(j);
			ModPaper mod=new ModPaper(sub.getSelectedItem(),c.getSelectedItem());
			
			mod.loadGUI();
			
			}
		});
		
		i.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jframe.remove(j);
				InQuest in = new InQuest();
				in.loadGUI(c.getSelectedIndex(),sub.getSelectedItem());
			}
		});
		d.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jframe.remove(j);
				DelPaper del=new DelPaper(sub.getSelectedItem(),c.getSelectedItem());
				del.loadGUI();
				
			}
		});
		g.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jframe.remove(j);
				Generate gen = new Generate(sub.getSelectedItem(),c.getSelectedItem());
			}
		});
		j.setBounds(150, 240, 100, 200);
		j.add(sub);
		j.add(d);
		j.add(c);
		j.add(i);
		j.add(m);
		j.add(d);
		j.add(g);
	
		
		
		jframe = LoginPage.f;
		jframe.add(j);
		//jframe.add(m);
		//jframe.add(d);
		//jframe.add(g);
		
		SwingUtilities.updateComponentTreeUI(jframe);
	}
}
