//author : JIN HAO LU
//date 2022/12/17

package cc.openhome;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Chess extends JFrame {

	public Chess () {

		initUI();
	}

	private void initUI() {

		add(new Board());
		setUndecorated(true);
		pack();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

				Chess ex = new Chess ();
				ex.setVisible(true);
				});
	}
}
