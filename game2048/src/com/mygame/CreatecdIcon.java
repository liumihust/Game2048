package com.mygame;

import java.net.URL;

import javax.swing.ImageIcon;


public class CreatecdIcon {
	public static ImageIcon add(String ImageName){
		URL IconUrl = Game2048.class.getResource("/"+ImageName);
		ImageIcon icon=new ImageIcon(IconUrl);
		return icon;
	}
}
