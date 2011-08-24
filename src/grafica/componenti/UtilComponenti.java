package grafica.componenti;

import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UtilComponenti {

	public static void main(String[] args) {
		
	}
	
	public static JPanel initContenitoreFrame(LayoutManager lm){
		JFrame frame = new JFrame();
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(lm);
		frame.setVisible(true);
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return contentPane;
	}
}
