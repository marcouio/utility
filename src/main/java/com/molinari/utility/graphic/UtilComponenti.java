package com.molinari.utility.graphic;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.container.FrameBase;

public class UtilComponenti {

	public static void main(final String[] args) {

	}

	public static FrameBase initContenitoreFrameApplicazione(final LayoutManager lm, final ControlloreBase controllore, PercentageDimension dimension) {
		FrameBase frame = new FrameBase();
		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(lm);
		if(dimension != null) {
			if(dimension.isPercentage()) {
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int width = (int) (screenSize.getWidth() / 100 * dimension.getWidth());
				int heigth = (int) (screenSize.getHeight() / 100 * dimension.getHeight());
				frame.setSize(width, heigth);
			} else {
				frame.setSize(dimension.getWidth(), dimension.getHeight());
			}
		}
		frame.setLocation(0, 0);
		frame.setVisible(true);
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
