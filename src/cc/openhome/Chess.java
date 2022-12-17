//author : JIN HAO LU
//date 2022/12/17

package cc.openhome;

import java.awt.*;
import javax.swing.*;


public class Chess extends JFrame {

	public Chess () {

		initUI();
	}

	private void initUI() {


		setTitle("五子棋");
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,d.width,d.height);

		setExtendedState(this.MAXIMIZED_BOTH);//full screen by default

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);
		add(new Board());
		setUndecorated(false);
		//pack();
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

				Chess ex = new Chess ();
				ex.setVisible(true);
				});
	}
}
