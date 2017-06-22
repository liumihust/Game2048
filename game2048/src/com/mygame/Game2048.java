package com.mygame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class Game2048 extends JFrame implements KeyListener{
	JPanel gamePanel;
	JPanel gamePanel2;
	JPanel controlPanel = new JPanel();
	JPanel tupianPanel = new JPanel();
	JPanel dataPanel = new JPanel();
	JLabel sumLabel = new JLabel();
	JLabel maxLabel = new JLabel();
	JLabel tupianLabel = new JLabel();
	
	MyButton[][] mybutton = new MyButton[4][4];
	MyButton[] controlButton = new MyButton[5];
	ImageIcon[] myImg = new ImageIcon[5];
	ImageIcon[] myImg2 = new ImageIcon[5];
	SpringLayout spring = new SpringLayout(); 
	GridBagLayout grid = new GridBagLayout();
	GridBagConstraints c;
	Color color_controlPanel=new Color(0,191,225);
	Color color_dataPanel=new Color(0,191,225);
	Color color_gamePanel=new Color(175,238,238);
	Font font= new Font("Serif",Font.BOLD,30);
	Random ra = new Random();
	
	static int count = 0;
	static int r_i = 0;
	static int r_j = 0;
	static int a = 0;
	static boolean combined =false;//判断单行单列有无结合
	static boolean changed =false;//判断整个过程有无变动
	final static int UP = 0;
	final static int DOWN = 1;
	final static int LEFT = 2;
	final static int RIGHT = 3;
	
	int[][] array1 = new int[4][4];
	int[][] array2 = new int[4][4];
	static int maxNum;
	static int sum;
	Thread t1;
	
	public Game2048(int fresh) throws FileNotFoundException{
		super("2048游戏");
		setBounds(0,0,1000,650);
		//setBounds(100,50,1050,650);
		setLayout(new BorderLayout());
		Container container = this.getContentPane();
		gamePanel = new JPanel();
		gamePanel.setSize(700,650);
		gamePanel.setLayout(grid);
		gamePanel.setBackground(color_gamePanel);
		gamePanel.addKeyListener(this);
		gamePanel2 = new JPanel();
		//gamePanel2.setBackground(color_gamePanel2);
		gamePanel2.setSize(300,650);
		gamePanel2.setLayout(new BorderLayout());
		
	    controlPanel.setSize(300,300);
	    controlPanel.setBackground(color_controlPanel);
	    controlPanel.setLayout(new BorderLayout());
	    tupianPanel.setSize(300,150);
	    tupianPanel.setLayout(spring);
	    dataPanel.setSize(300,100);
	    dataPanel.setBackground(color_dataPanel);
	    dataPanel.setLayout(new BorderLayout());
	    sumLabel.setFont(font);
	    maxLabel.setFont(font);
	    
		c = new GridBagConstraints();
		c.weightx = 25;
		c.weighty = 25;
        c.fill = (GridBagConstraints.BOTH);
		c.insets = new Insets(8,8,8,8);
		
		a = ra.nextInt(7);
        if(fresh == 1){//新的一局，初始化随机
        	for(int i = 0;i<4; i++){
    			for(int j = 0;j<4;j++){
    				c.gridx = i;
    				c.gridy = j;
                    if(i+j == a){
                    	mybutton[i][j] = new MyButton((ra.nextInt(2)+1)*2);
                    }
                    else{
                    	mybutton[i][j] = new MyButton(0);
                    }
    				gamePanel.add(mybutton[i][j],c);
    			}
    		}
        	
        }else if(fresh == 2){//接着上一次玩
        	try {
				getData("array.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	for(int i = 0;i<4; i++){
     			for(int j = 0;j<4;j++){
     				c.gridx = i;
     				c.gridy = j;
                     mybutton[i][j] = new MyButton(array2[i][j]);
     				gamePanel.add(mybutton[i][j],c);
     			}
     		}   	
        }else{//查看历史最好成绩
        	try {
				getData("history.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	for(int i = 0;i<4; i++){
     			for(int j = 0;j<4;j++){
     				c.gridx = i;
     				c.gridy = j;
                     mybutton[i][j] = new MyButton(array2[i][j]);
     				gamePanel.add(mybutton[i][j],c);
     			}
     		}   
        }
        
		for(int j = 0;j<5; j++){
			myImg[j]=CreatecdIcon.add("direct"+j+".png");//创建图标方法
			myImg2[j]=CreatecdIcon.add("direct_"+j+".png");//创建图标方法
		}
	
		for(int j = 0;j<5; j++){
			controlButton[j] = new MyButton();
			controlButton[j].setIcon(myImg[j]);
			controlButton[j].setRolloverIcon(myImg2[j]);
			controlButton[j].addActionListener(new MyAction(j));
			//controlPanel.add(controlButton[j]);
		}
		controlPanel.add(controlButton[0],BorderLayout.NORTH);
		controlPanel.add(controlButton[1],BorderLayout.SOUTH);
		controlPanel.add(controlButton[2],BorderLayout.WEST);
		controlPanel.add(controlButton[3],BorderLayout.EAST);
		controlPanel.add(controlButton[4],BorderLayout.CENTER);
		
		
		/*controlPanel.add(controlButton[0]);
		spring.putConstraint(SpringLayout.EAST, controlButton[0] , 10,SpringLayout.EAST, controlPanel);
		spring.putConstraint(SpringLayout.NORTH, controlButton[0] , 10,SpringLayout.NORTH, controlPanel);
		controlPanel.add(controlButton[1]);
		spring.putConstraint(SpringLayout.EAST, controlButton[1] , 10,SpringLayout.EAST, controlPanel);
		spring.putConstraint(SpringLayout.NORTH, controlButton[1] , 10,SpringLayout.NORTH, controlPanel);
		controlPanel.add(controlButton[2]);
		spring.putConstraint(SpringLayout.WEST, controlButton[2] , 10,SpringLayout.EAST, controlPanel);
		spring.putConstraint(SpringLayout.NORTH, controlButton[2] , 10,SpringLayout.NORTH, controlPanel);
		controlPanel.add(controlButton[3]);
		spring.putConstraint(SpringLayout.EAST, controlButton[3] , 10,SpringLayout.EAST, controlPanel);
		spring.putConstraint(SpringLayout.NORTH, controlButton[3] , 10,SpringLayout.NORTH, controlPanel);
		controlPanel.add(controlButton[4]);
		spring.putConstraint(SpringLayout.EAST, controlButton[4] , 10,SpringLayout.WEST, controlPanel);
		spring.putConstraint(SpringLayout.NORTH, controlButton[4] , 10,SpringLayout.NORTH, controlPanel);
		
		/*controlButton[0].addActionListener(new MyAction(UP));
		controlButton[1].addActionListener(new MyAction(1));
		controlButton[2].addActionListener(new MyAction(2));
		controlButton[3].addActionListener(new MyAction(3));
		controlButton[4].addActionListener(new MyAction(4));*/
		
		ImageIcon tupianIcon=CreatecdIcon.add("back2.jpg");
		tupianLabel.setIcon(tupianIcon);
		tupianLabel.setOpaque(true);
		tupianLabel.setPreferredSize(new Dimension(200,350));
		tupianPanel.add(tupianLabel);
		spring.putConstraint(SpringLayout.WEST, tupianLabel , 0,SpringLayout.WEST, tupianPanel);
		spring.putConstraint(SpringLayout.NORTH, tupianLabel , 0,SpringLayout.NORTH, tupianPanel);
	
		dataPanel.add(maxLabel,BorderLayout.NORTH);
		dataPanel.add(sumLabel,BorderLayout.SOUTH);
		gamePanel2.add(controlPanel,BorderLayout.NORTH);
		gamePanel2.add(tupianPanel,BorderLayout.CENTER);
		gamePanel2.add(dataPanel,BorderLayout.SOUTH);
		
		container.add(gamePanel,BorderLayout.CENTER);
		container.add(gamePanel2,BorderLayout.EAST);
		
		sumThread sumthread = new sumThread();
		t1 = new Thread(sumthread);
		t1.start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		gamePanel.requestFocus();
		
		
	}
	
	private class MyAction implements ActionListener {
		int a;
		public MyAction(int a){
			this.a = a;	
		}
		public void actionPerformed(final ActionEvent e){
			combine(a);
			if(changed){
				new Thread(){
		            public void run(){
		            	try {
							Thread.sleep(200);//为了视觉效果，有意延迟0.2秒，时间是自定义
							Create();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}   
		             } 
		         }.start();
				 changed = false;
				 
			}else{
				CreateSound.play(new File("music/wrong.wav"));
			}
			
			gamePanel.requestFocus();
			
		}
		
	}
	
	
	private class sumThread implements Runnable {

		public void run() {
			while(true) {
				for(int i = 0;i<4; i++){
	     			for(int j = 0;j<4;j++){
	     				array1[i][j]= mybutton[i][j].getValue();
	     			}
				}
				sum = sumOfArray(array1);
				maxNum = maxOfArray(array1);
				sumLabel.setText("SUM:"+String.valueOf(sum));	
				maxLabel.setText("MAX:"+String.valueOf(maxNum));
				try {
					Thread.sleep(500);//0.5秒更新一次
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void combine(int a){
		switch(a){
		case UP :
			for(int i = 0;i<4; i++){
				for(int j = 0;j<3;j++){
					if(mybutton[i][j].getValue()==0){
						for(int k = j;k<3;k++){
							mybutton[i][k].shiftValue(mybutton[i][k+1].getValue());
							mybutton[i][k].setView();
							if(mybutton[i][k+1].getValue()!=0){
								changed = true;
							}
							
						}
						mybutton[i][3].shiftValue(0);
						mybutton[i][3].setView();
					}
					
					if(mybutton[i][j].getValue()==0){
						for(int k = j;k<3;k++){
							mybutton[i][k].shiftValue(mybutton[i][k+1].getValue());
							mybutton[i][k].setView();	
							if(mybutton[i][k+1].getValue()!=0){
								changed = true;
							}
						}
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = j;k<3;k++){
							mybutton[i][k].shiftValue(mybutton[i][k+1].getValue());
							mybutton[i][k].setView();
							if(mybutton[i][k+1].getValue()!=0){
								changed = true;
							}
						}
					}
					if((!combined)&&(j!=0)){
						if(compare(mybutton[i][j],mybutton[i][j-1])){
							mybutton[i][j-1].setValue();
							mybutton[i][j-1].setView();
							mybutton[i][j].clearValue();
							mybutton[i][j].setView();
							combined = true;
							changed = true;
						}
						
					}
						
					if(compare(mybutton[i][j],mybutton[i][j+1])){
						mybutton[i][j].setValue();
						mybutton[i][j].setView();
						mybutton[i][j+1].clearValue();
						mybutton[i][j+1].setView();
						combined = true;
						changed = true;
						
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = j;k<3;k++){
							mybutton[i][k].shiftValue(mybutton[i][k+1].getValue());
							mybutton[i][k].setView();
							if(mybutton[i][k+1].getValue()!=0){
								changed = true;
							}
						}
					}
						
				}
				combined = false;
			}
		break;
			
			
		case DOWN :
			for(int i = 0;i<4; i++){
				for(int j = 3;j>0;j--){
					if(mybutton[i][j].getValue()==0){
						for(int k = j;k>0;k--){
							mybutton[i][k].shiftValue(mybutton[i][k-1].getValue());
							mybutton[i][k].setView();
							if(mybutton[i][k-1].getValue()!=0){
								changed = true;
							}
						}
						mybutton[i][0].shiftValue(0);
						mybutton[i][0].setView();
					}
					
					if(mybutton[i][j].getValue()==0){
						for(int k = j;k>0;k--){
							mybutton[i][k].shiftValue(mybutton[i][k-1].getValue());
							mybutton[i][k].setView();
							if(mybutton[i][k-1].getValue()!=0){
								changed = true;
							}
						}
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = j;k>0;k--){
							mybutton[i][k].shiftValue(mybutton[i][k-1].getValue());
							mybutton[i][k].setView();
							if(mybutton[i][k-1].getValue()!=0){
								changed = true;
							}
						}
					}
					if((!combined)&&(j!=3)){
						if(compare(mybutton[i][j],mybutton[i][j+1])){
							mybutton[i][j+1].setValue();
							mybutton[i][j+1].setView();
							mybutton[i][j].clearValue();
							mybutton[i][j].setView();
							combined = true;
							changed = true;
						}
					}
						
					if(compare(mybutton[i][j],mybutton[i][j-1])){
						mybutton[i][j].setValue();
						mybutton[i][j].setView();
						mybutton[i][j-1].clearValue();
						mybutton[i][j-1].setView();
						combined = true;
						changed = true;
						
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = j;k>0;k--){
							mybutton[i][k].shiftValue(mybutton[i][k-1].getValue());
							mybutton[i][k].setView();	
							//changed = true;
							if(mybutton[i][k-1].getValue()!=0){
								changed = true;
							}
						}
					}
						
				}
				combined = false;
			}
			break;
			
			
		case LEFT :
			for(int j = 0;j<4; j++){
				for(int i = 0;i<3;i++){
					if(mybutton[i][j].getValue()==0){
						for(int k = i;k<3;k++){
							mybutton[k][j].shiftValue(mybutton[k+1][j].getValue());
							mybutton[k][j].setView();
							if(mybutton[k+1][j].getValue()!=0){
								changed = true;
							}
						}
						mybutton[3][j].shiftValue(0);
						mybutton[3][j].setView();
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = i;k<3;k++){
							mybutton[k][j].shiftValue(mybutton[k+1][j].getValue());
							mybutton[k][j].setView();
							if(mybutton[k+1][j].getValue()!=0){
								changed = true;
							}
						}
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = i;k<3;k++){
							mybutton[k][j].shiftValue(mybutton[k+1][j].getValue());
							mybutton[k][j].setView();
							if(mybutton[k+1][j].getValue()!=0){
								changed = true;
							}
						}
					}
					if((!combined)&&(i!=0)){
						if(compare(mybutton[i][j],mybutton[i-1][j])){
							mybutton[i-1][j].setValue();
							mybutton[i-1][j].setView();
							mybutton[i][j].clearValue();
							mybutton[i][j].setView();
							combined = true;
							changed = true;
						}
					}
						
					if(compare(mybutton[i][j],mybutton[i+1][j])){
						mybutton[i][j].setValue();
						mybutton[i][j].setView();
						mybutton[i+1][j].clearValue();
						mybutton[i+1][j].setView();
						combined = true;
						changed = true;
						
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = i;k<3;k++){
							mybutton[k][j].shiftValue(mybutton[k+1][j].getValue());
							mybutton[k][j].setView();
							if(mybutton[k+1][j].getValue()!=0){
								changed = true;
							}
						}
					}
						
				}
				combined = false;
			}
			break;
			
			
		case RIGHT :
			for(int j = 0;j<4; j++){
				for(int i = 3;i>0;i--){
					if(mybutton[i][j].getValue()==0){
						for(int k = i;k>0;k--){
							mybutton[k][j].shiftValue(mybutton[k-1][j].getValue());
							mybutton[k][j].setView();
							if(mybutton[k-1][j].getValue()!=0){
								changed = true;
							}
						}
						mybutton[0][j].shiftValue(0);
						mybutton[0][j].setView();
					}
					
					if(mybutton[i][j].getValue()==0){
						for(int k = i;k>0;k--){
							mybutton[k][j].shiftValue(mybutton[k-1][j].getValue());
							mybutton[k][j].setView();
							if(mybutton[k-1][j].getValue()!=0){
								changed = true;
							}
						}
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = i;k>0;k--){
							mybutton[k][j].shiftValue(mybutton[k-1][j].getValue());
							mybutton[k][j].setView();	
							if(mybutton[k-1][j].getValue()!=0){
								changed = true;
							}
						}
					}
					if((!combined)&&(i!=3)){
						if(compare(mybutton[i][j],mybutton[i+1][j])){
							mybutton[i+1][j].setValue();
							mybutton[i+1][j].setView();
							mybutton[i][j].clearValue();
							mybutton[i][j].setView();
							combined = true;
							changed = true;
						}
					}
						
					if(compare(mybutton[i][j],mybutton[i-1][j])){
						mybutton[i][j].setValue();
						mybutton[i][j].setView();
						mybutton[i-1][j].clearValue();
						mybutton[i-1][j].setView();
						combined = true;
						changed = true;
						
					}
					if(mybutton[i][j].getValue()==0){
						for(int k = i;k>0;k--){
							mybutton[k][j].shiftValue(mybutton[k-1][j].getValue());
							mybutton[k][j].setView();	
							if(mybutton[k-1][j].getValue()!=0){
								changed = true;
							}
						}
					}
				}
				combined = false;
			}
			break;
			
		case 4: 
			//JOptionPane.showConfirmDialog(this, "你确定要退出游戏？", JOptionPane.YES_NO_CANCEL_OPTION);
			int n = JOptionPane.showConfirmDialog(this, "你确定要退出游戏？", "提醒",JOptionPane.YES_NO_OPTION);
			if(n==JOptionPane.YES_OPTION){
				try {
					saveData("array.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					getData("history.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(better_array(array1,array2)){
					try {
						saveData("history.txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				new LoginFrame();
				setVisible(false);
				//System.exit(0);
			}
			break;
		}

	}
	
	
	public boolean compare(MyButton mybutton1,MyButton mybutton2){
		    if((mybutton1.getValue()==mybutton2.getValue())&&(mybutton1.getValue()!=0)){
		    	 return true;
		    }
		    else{
		    	return false;
		    }	  
	}
	
	
	public void Create(){//随机在空值位产生那个一个2或者4
		//int r_i = 0;
		//int r_j = 0;
		int count2 = 0;
		for(int i = 0;i<4; i++){
			for(int j = 0;j<4;j++){
				if(mybutton[i][j].getValue() == 0){
					count++;
				}
			}
		}
		count2 = ra.nextInt(count)+1;
		for(int i = 0;i<4; i++){
			for(int j = 0;j<4;j++){
				if(mybutton[i][j].getValue() == 0){
					r_i = i;
					r_j = j;
					count2--;
				}
				if(count2 == 0){
					break;
				}
			}
			if(count2 == 0){
				break;
				
			}
		}
		count2 =count;
		count =0;
		mybutton[r_i][r_j].shiftValue((ra.nextInt(2)+1)*2);
		mybutton[r_i][r_j].setView();
		if((count2==1)&&gameOver()){
			
			try {
				saveData("array.txt");//同时放到array1当中
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				getData("history.txt");//存放在array2当中
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(better_array(array1,array2)){
				try {
					saveData("history.txt");
					JOptionPane.showMessageDialog(this,"游戏已经结束了,但你的成绩已经是历史最好了！！");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(this, "游戏结束，再接再厉哦！");
			}
			//System.exit(0);
	         setVisible(false);
	         new LoginFrame();
		}
		//return count2;
	}
					
	
	public boolean gameOver(){
		boolean over = true;
		for(int i = 0;i<4; i++){
			for(int j = 0;j<3;j++){
				if(compare(mybutton[i][j],mybutton[i][j+1])){
					over = false;
				}	
			}
		}
		for(int j = 0;j<4; j++){
			for(int i = 0;i<3;i++){
				if(compare(mybutton[i][j],mybutton[i+1][j])){
					over = false;
				}
			}
		}
		return over;
	}
	
	
	public void saveData(String str) throws IOException{//默认是将游戏结果放到指定的文本
		 //File file = new File("array.txt"); //存放数组数据的文件 
		BufferedWriter out = new BufferedWriter(new FileWriter(str)); //文件写入流  
	     for(int i=0;i<4;i++){ 
	            for(int j=0;j<4;j++){ 
	                   out.write(mybutton[i][j].getValue()+"\t");
					   array1[i][j]=mybutton[i][j].getValue();
	            } 
	            out.write("\r\n"); 
	     } 
	     out.close(); 	
	}
	
	public void getData(String str) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(str)); //一行数据 
        int row=0; //逐行读取，并将每个数组放入到数组中
        String line;
        try {
			while((line = in.readLine()) != null){ 
			        String[] temp = line.split("\t"); 
			        for(int j=0;j<temp.length;j++){ 
			               array2[row][j] = (int) Double.parseDouble(temp[j]); 
			        } 
			        row++; 
			 }
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
	}
	
	
	public boolean better_array(int[][] a,int[][] b){
		if(maxOfArray(a)>maxOfArray(b)){
			return true;
		}
		else if((maxOfArray(a)==maxOfArray(b))&&(sumOfArray(a)>sumOfArray(b))){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public int sumOfArray(int[][] a ){
	    int sum = 0;
		for(int i=0;i<4;i++){ 
            for(int j=0;j<4;j++){
            	sum +=a[i][j];	
            }
		}
		return sum;	
	}
	
	public int maxOfArray(int[][] a){
		int max = a[0][0];
		for(int i=0;i<4;i++){ 
            for(int j=0;j<4;j++){
            	if(a[i][j]>=max)
            		max = a[i][j];
            }
		}
		return max;
	}
	
	
	public static void main(String[] args) {
          new LoginFrame();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		//e. = false;
		switch(keycode){
		case KeyEvent.VK_UP:
			controlButton[0].doClick();
			break;
		case KeyEvent.VK_DOWN:
			controlButton[1].doClick();
			break;
        case KeyEvent.VK_LEFT:
        	controlButton[2].doClick();
			break;
		case KeyEvent.VK_RIGHT:	
			controlButton[3].doClick();
			break;
		default:
			CreateSound.play(new File("music/wrong.wav"));//如果按错了键
			break;
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

}
