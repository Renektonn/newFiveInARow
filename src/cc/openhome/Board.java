//author : JIN HAO LU
//date : 2022/12/17

package cc.openhome;

import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;



public class Board extends JPanel implements MouseListener{

	private static final int BLACK = 1;
	private static final int WHITE = -1;

	private int [][] chess;
	private int turn;//black chess first
	private int width;
	private boolean isGame;

	public Board(){

		initBoard();
	}

	private void initBoard(){

		initGame();
		addMouseListener(this);
	}

	private void initGame(){

		turn = BLACK;
		width = 40;
		isGame = true;
		chess = new int[19][19];

		setPreferredSize(new Dimension(1600, 900));       
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		doDrawing(g);
	}

	private void doDrawing(Graphics g) {
		
		for(int i=1 ; i<=19 ; i++){
			g.drawLine(width , width * i , width * 19 , width * i);	
			g.drawLine(width * i , width , width * i , width * 19);	


		}
		
		Graphics2D g2d = (Graphics2D)g;
		for(int i=0 ; i<3 ; i++){
			for(int j=0 ; j<3 ; j++){
				drawCircle(g2d , width * 4 + width * 6 * i , width * 4 + width * 6 * j , 10);
			}
		}
		
		
		for(int i=0 ; i<19 ; i++){
			for(int j=0 ; j<19 ; j++){
				if(chess[i][j] == BLACK){
					g2d.setColor(Color.black);
					drawCircle(g2d , width + width * j , width + width * i , 35);
				}
				else
					if(chess[i][j] == WHITE){
						g2d.setColor(Color.white);
						drawCircle(g2d, width + width * j , width + width * i , 35);
					}
			}
		}
						
	}

	private void drawCircle(Graphics2D g2d , double x , double y , double diameter){

		Ellipse2D.Double circle = new Ellipse2D.Double(x-diameter/2,y-diameter/2,diameter,diameter);
		//construct a ellipse will create from up-left corner, so it's postition must minut diamter/2
		g2d.fill(circle);
	}
	
	@Override
	public void mouseClicked(MouseEvent e){

		Point postition = e.getLocationOnScreen();
		getAbsolutePosition(postition.x,postition.y - 20);//I don't know why, but is works.
	}

	@Override
	public void mousePressed(MouseEvent e){}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

	private void getAbsolutePosition(int x , int y){
		if(!isGame) return;
		x = (x - width / 2) / width;
		y = (y - width / 2) / width;
		addChess(x,y);
	}

	private void addChess(int x , int y){

		if(!inField(x,y)) return;
		if(chess[y][x]!=0) return;
		chess[y][x]=turn;	
		repaint();
		judgeGame(x,y);
		turn*=-1;

	}

	private void judgeGame(int x , int y){

		//up-left
		while(inField(x - 1 , y - 1) && sameColor(x - 1 , y - 1)){
			x--;
			y--;
		}			
		for(int i=0 ; i<5 ; i++){
			if(!sameColor(x+i , y+i)){
				break;
			}
			if(i==4){
				isGame=false;
				return;
			}
		}
		
		//up-right
		while(inField(x + 1 , y - 1) && sameColor(x + 1 , y - 1)){
			x++;
			y--;
		}			
		for(int i=0 ; i<5 ; i++){
			if(!sameColor(x-i , y+i)){
				break;
			}
			if(i==4){
				isGame=false;
				return;
			}
		}
		
		//left
		while(inField(x - 1 , y) && sameColor(x - 1 , y)){
			x-=1;
		}			
		for(int i=0 ; i<5 ; i++){
			if(!sameColor(x+i , y)){
				break;
			}
			if(i==4){
				isGame=false;
				return;
			}
		}

		//up
		while(inField(x , y - 1) && sameColor(x , y - 1)){
			y-=1;
		}			
		for(int i=0 ; i<5 ; i++){
			if(!sameColor(x , y+i)){
				break;
			}
			if(i==4){
				isGame=false;
				return;
			}
		}
	

	}

	private boolean sameColor(int x , int y){

		if(!inField(x,y)) return false;
		if(chess[y][x]!=turn) return false;
		return true;

	}

	private boolean inField(int x , int y){

		if(x<0 || y<0) return false;
		if(x>18 || y>18) return false;
		return true;

	}

}
