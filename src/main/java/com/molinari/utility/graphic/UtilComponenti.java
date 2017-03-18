package com.molinari.utility.graphic;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.FrameBase;

import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UtilComponenti {

	public static void main(final String[] args) {

	}

	/**
	 * Fornisce i settaggi default per il funzionamento di un frame
	 * 
	 * @param lm
	 * @param controllore
	 * @return
	 */
	public static FrameBase initContenitoreFrameApplicazione(final LayoutManager lm, final ControlloreBase controllore) {
		FrameBase frame = new FrameBase();
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(lm);
		frame.setSize(400, 400);
//		frame.setVisible(true);
		if (controllore != null) {
			ControlloreBase.setApplicationframe(frame);
			ControlloreBase.setApplicationGraphics2d((Graphics2D) frame.getGraphics());
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}

	/**
	 * Fornisce i settaggi default per il funzionamento di un frame
	 * 
	 * @param lm
	 * @return
	 */
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
