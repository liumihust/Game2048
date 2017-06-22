package com.mygame;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;

import java.util.Random;
//import java.net.URL;
//import java.net.URI;

//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
//import java.awt.SpringLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//import javax.swing.SwingConstants;
import javax.swing.*;




public class LoginFrame extends JFrame {
	private JButton login;
	private JButton last;
	private JButton history;
	private JButton Exit;

	/**
	 * Create the frame
	 */
	public LoginFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//getContentPane().setLayout(null);
		//setTitle("2048游戏登陆");
		setUndecorated(true);
		MyMouseAdapter adapter = new MyMouseAdapter();
		addMouseMotionListener(adapter);
		Container pane = getContentPane();
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		setBounds(200,50,900,600);


		login=new MyButton();//登录按钮
		login.addActionListener(new LoginAction(1));
		ImageIcon loginIcon=CreatecdIcon.add("login1.png");//创建图标方法
		ImageIcon loginIcon1=CreatecdIcon.add("login_1.png");//创建图标方法
		login.setIcon(loginIcon);
		login.setRolloverIcon(loginIcon1);
		pane.add(login);
		layout.putConstraint(SpringLayout.WEST, login , 185,SpringLayout.WEST,  pane);
		layout.putConstraint(SpringLayout.NORTH, login , 77,SpringLayout.NORTH, pane);
		
		last = new MyButton();//按钮
		last.addActionListener(new LoginAction(2));
		ImageIcon resetIcon=CreatecdIcon.add("login2.png");//创建图标方法
		ImageIcon resetIcon1=CreatecdIcon.add("login_2.png");//创建图标方法
		last.setIcon(resetIcon);
		last.setRolloverIcon(resetIcon1);
		pane.add(last);
		layout.putConstraint(SpringLayout.WEST, last , 246,SpringLayout.WEST,  pane);
		layout.putConstraint(SpringLayout.NORTH, last , 125,SpringLayout.NORTH, pane);
		
		history = new MyButton();//按钮
		history.addActionListener(new LoginAction(3));
		ImageIcon historyIcon=CreatecdIcon.add("login3.png");//创建图标方法
		ImageIcon historyIcon1=CreatecdIcon.add("login_3.png");//创建图标方法
		history.setIcon(historyIcon);
		history.setRolloverIcon(historyIcon1);
		pane.add(history);
		layout.putConstraint(SpringLayout.WEST, history , 353,SpringLayout.WEST,  pane);
		layout.putConstraint(SpringLayout.NORTH, history , 414,SpringLayout.NORTH, pane);
		
		
		
		Exit=new MyButton();
		ImageIcon Exit_icon=CreatecdIcon.add("exit.png");//创建图标方法	
		ImageIcon Exit_icon1=CreatecdIcon.add("exit1.png");//创建图标方法
		Exit.setIcon(Exit_icon);
		Exit.setRolloverIcon(Exit_icon1);
		Exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		pane.add(Exit);
		layout.putConstraint(SpringLayout.WEST, Exit , 296,SpringLayout.WEST,  pane);
		layout.putConstraint(SpringLayout.NORTH, Exit , 475,SpringLayout.NORTH, pane);
		

		final JLabel tupianLabel= new JLabel();
		ImageIcon loginIco=CreatecdIcon.add("back.jpg");//背景
		tupianLabel.setIcon(loginIco);
		tupianLabel.setOpaque(true);
		tupianLabel.setBackground(Color.GREEN);
		tupianLabel.setPreferredSize(new Dimension(900,600));
		pane.add(tupianLabel);
		layout.putConstraint(SpringLayout.WEST, tupianLabel , 0,SpringLayout.WEST,  pane);
		layout.putConstraint(SpringLayout.NORTH, tupianLabel , 0,SpringLayout.NORTH, pane);

		setVisible(true);
		setResizable(false);
		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

	}
	
	
	class LoginAction implements ActionListener {
		int a;
		public LoginAction(int a){
			this.a = a;	
		}
		public void actionPerformed(final ActionEvent e) {
			try {
				new Game2048(a);
				setVisible(false);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	
	/*界面任意拖动事件
	 * 
	 */
	class MyMouseAdapter extends MouseAdapter {
		private int offsetX, offsetY;
		public void mouseDragged(MouseEvent e) {
			SwingUtilities.getRoot((Component) e.getSource()).setLocation(e.getXOnScreen()
					- offsetX, e.getYOnScreen()- offsetY);
		}
		public void mousePressed(MouseEvent e) {
			offsetX = e.getX();
			offsetY = e.getY();
		}
	 }
 }
	
	
	