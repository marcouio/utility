package grafica.componenti;

import grafica.componenti.contenitori.FrameBase;

import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.ControlloreBase;

public class UtilComponenti {

	public static void main(final String[] args) {

	}

	public static FrameBase initContenitoreFrameApplicazione(final LayoutManager lm, final ControlloreBase controllore) {
		FrameBase frame = new FrameBase() {
			private static final long serialVersionUID = 1L;

			@Override
			protected StyleBase settaStileOverride() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(lm);
		frame.setVisible(true);
		frame.setSize(400, 400);
		controllore.setApplicationframe(frame);
		ControlloreBase.setApplicationGraphics2d((Graphics2D) frame.getGraphics());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}

	public static JPanel initContenitoreFrame(final LayoutManager lm) {
		JFrame frame = new JFrame();
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(lm);
		frame.setVisible(true);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return contentPane;
	}
}
