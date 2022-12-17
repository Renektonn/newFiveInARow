//author : JIN HAO LU
//date : 2022/12/17

package cc.openhome;

import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;



class ChessInfo{
	int x;
	int y;
	int color;
	public ChessInfo(int x , int y , int color){
		this.x = x;
		this.y = y;
		this.color = color;
	}

}

public class Board extends JPanel implements MouseListener , ActionListener{

	private static final int BLACK = 1;
	private static final int WHITE = -1;

	private int [][] chess;
	private int turn;//black chess first
	private int width;
	private boolean isGame;

	private Stack <ChessInfo> order;

	public Board(){

		initBoard();

	}

	private void initBoard(){
		
		setLayout(null);
		JButton button = new JButton("悔棋");
		button.setFont( new Font(Font.DIALOG , Font.BOLD , 25) );
		button.setBounds(800 , 100 , 100 , 50);
		button.addActionListener(this);
		button.setActionCommand("regert");
		add(button);

		setVisible(true);

		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,d.width,d.height);
		

		addMouseListener(this);
		initGame();
	}

	private void initGame(){

		turn = BLACK;
		width = 35;
		isGame = true;
		chess = new int[19 + 1][19 + 1];
		order = new Stack <ChessInfo> ();

		repaint();
	}

	@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			doDrawing(g);
		}

	private void doDrawing(Graphics g) {

		for(int i = 1 ; i <= 19 ; i++){
			g.drawLine(width , width * i , width * 19 , width * i);	
			g.drawLine(width * i , width , width * i , width * 19);	


		}

		Graphics2D g2d = (Graphics2D)g;
		for(int i = 0 ; i < 3 ; i++){
			for(int j = 0 ; j < 3 ; j++){
				drawCircle(g2d , width * 4 + width * 6 * i , width * 4 + width * 6 * j , 10);
			}
		}


		for(int i = 1 ; i <= 19 ; i++){
			for(int j = 1 ; j <= 19 ; j++){
				if(chess[i][j] == BLACK){
					g2d.setColor(Color.black);
					drawCircle(g2d ,width * j , width * i , width - 5);
				}
				else
					if(chess[i][j] == WHITE){
						g2d.setColor(Color.white);
						drawCircle(g2d, width * j , width * i , width - 5);
					}
			}
		}

	}

	private void drawCircle(Graphics2D g2d , double x , double y , double diameter){

		Ellipse2D.Double circle = new Ellipse2D.Double(x - diameter / 2 , y - diameter / 2 , diameter , diameter);
		//construct a ellipse will create from up-left corner, so it's postition must minut diamter/2
		g2d.fill(circle);
	}

	@Override
		public void mouseClicked(MouseEvent e){

			Point position = e.getLocationOnScreen();
			getAbsolutePosition(position.x , position.y - 40);//get location on screen, but frame take 40, so I minus it to get more accuray.
			System.out.println(position.x + " " + (position.y - 40) );
		}

	@Override
		public void mousePressed(MouseEvent e){}

	@Override
		public void mouseReleased(MouseEvent e){}

	@Override
		public void mouseEntered(MouseEvent e){}

	@Override
		public void mouseExited(MouseEvent e){}

	@Override
		public void actionPerformed(ActionEvent e){
			String cmd = e.getActionCommand();	
			if(cmd == "regert"){
				if(order.empty()) return;
				isGame = true;
				ChessInfo c = order.pop();
				chess[c.y][c.x] = 0;
				turn = c.color;
				repaint();

			}
		}

	private void getAbsolutePosition(int x , int y){
		if(!isGame) return;
		x = (x + width / 2) / width;
		y = (y + width / 2) / width;
		addChess(x,y);
	}

	private void addChess(int x , int y){

		if(!inField(x,y)) return;
		if(chess[y][x] != 0) return;
		chess[y][x]=turn;	
		order.push(new ChessInfo(x , y , turn));
		repaint();
		judgeGame(x,y);
		turn *= -1;

	}

	private void judgeGame(int x , int y){

		//up-left
		while(inField(x - 1 , y - 1) && sameColor(x - 1 , y - 1)){
			x--;
			y--;
		}			
		for(int i = 0 ; i < 5 ; i++){
			if(!sameColor(x+i , y+i)){
				break;
			}
			if(i == 4){
				isGame=false;
				return;
			}
		}

		//up-right
		while(inField(x + 1 , y - 1) && sameColor(x + 1 , y - 1)){
			x++;
			y--;
		}			
		for(int i = 0 ; i < 5 ; i++){
			if(!sameColor(x - i , y + i)){
				break;
			}
			if(i == 4){
				isGame=false;
				return;
			}
		}

		//left
		while(inField(x - 1 , y) && sameColor(x - 1 , y)){
			x -= 1;
		}			
		for(int i = 0 ; i < 5 ; i++){
			if(!sameColor(x+i , y)){
				break;
			}
			if(i == 4){
				isGame = false;
				return;
			}
		}

		//up
		while(inField(x , y - 1) && sameColor(x , y - 1)){
			y -= 1;
		}			
		for(int i = 0 ; i < 5 ; i++){
			if(!sameColor(x , y+i)){
				break;
			}
			if(i == 4){
				isGame=false;
				return;
			}
		}


	}

	private boolean sameColor(int x , int y){

		if(!inField(x , y)) return false;
		if(chess[y][x] != turn) return false;
		return true;

	}

	private boolean inField(int x , int y){

		if(x < 1 || y < 1) return false;
		if(x > 19 || y > 19) return false;
		return true;

	}

}
