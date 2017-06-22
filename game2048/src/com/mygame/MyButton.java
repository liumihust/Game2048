package com.mygame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class MyButton extends JButton{
	private int value;
	private Font font= new Font("DialogInput",Font.BOLD,60);
	private Font font1= new Font("DialogInput",Font.BOLD,50);
	private Font font2= new Font("DialogInput",Font.BOLD,45);
	//HSSFWorkbook workbook = new HSSFWorkbook();
	//HSSFPalette my = workbook.getCustomPlalette();
	Color c0=new Color(193,255,193);
	Color c2=new Color(84,255,159);
	Color c4=new Color(135,206,250);
	Color c8=new Color(127,255,0);
	Color c16=new Color(255,182,193);
	Color c32=new Color(0,191,255);
	Color c64=new Color(106,90,205);
	Color c128=new Color(240,128,128);
	Color c256=new Color(255,174,185);
	Color c512=new Color(238,92,66);
	Color c1024=new Color(238,130,238);
	Color c2048=new Color(255,64,64);
	Color cFont=new Color(255,255,240);
	
	public void setValue(){
		value = 2*value;	
	}
	
	public void shiftValue(int a){
		value = a;
		
	}
	public void clearValue(){
		value = 0;
		
	}
	public int getValue(){
		return value;
		
	}
	
	public MyButton(int a){
		super();
		setMargin(new java.awt.Insets(0, 0 ,0 , 0));
		setFocusPainted(false);
		setPreferredSize(new Dimension(175,135));
		//setPreferredSize(new Dimension(192,135));
		setForeground(cFont);
		setFont(font);
		value = a;
		setView();
	}
	
	public MyButton(){
		super();
		setMargin(new java.awt.Insets(0, 0 ,0 , 0));
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		//setFont(font);
		setHideActionText(true);
	}
	
	public void setView(){
		if(value != 0){
			super.setText(String.valueOf(value));
			
		}
		else{
			super.setText(" ");
		}
		switch(value){
		case 0:
			super.setBackground(c0);
			//super.setFont(font);
			
			break;
		case 2:
			super.setBackground(c2);
			//super.setFont(font);
			break;	
		case 4:
			super.setBackground(c4);
			//super.setFont(font);
			break;	
			
		case 8:
			super.setBackground(c8);
			//super.setFont(font);
			break;	
			
		case 16:
			super.setBackground(c16);
			break;	
			
		case 32:
			super.setBackground(c32);
			break;	
			
		case 64:
			super.setBackground(c64);
			break;	
			
		case 128:
			super.setBackground(c128);
			//super.setFont(font1);
			break;	
			
		case 256:
			super.setBackground(c256);
			//super.setFont(font1);
			break;	
			
		case 512:
			super.setBackground(c512);
			//super.setFont(font1);
			break;	
			
		case 1024:
			super.setBackground(c1024);
			//super.setFont(font2);
			break;	
			
		case 2048:
			super.setBackground(c2048);
			//super.setFont(font2);
			
			break;
		default:
			super.setBackground(c2048);
			//super.setFont(font2);
			break;
			
		}
	}
	

}
