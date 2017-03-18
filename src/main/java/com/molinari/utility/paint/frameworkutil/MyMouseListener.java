package com.molinari.utility.paint.frameworkutil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.molinari.utility.controller.ControlloreDisegno;

public class MyMouseListener {

	MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(final MouseEvent e) {
			ControlloreDisegno.mousePressed(e);
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			ControlloreDisegno.mouseReleased(e);
		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			ControlloreDisegno.mouseClicked(e);
		}
	};

	MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
		@Override
		public void mouseMoved(final MouseEvent e) {
			ControlloreDisegno.mouseMoved(e);
		}

		@Override
		public void mouseDragged(final MouseEvent e) {
			ControlloreDisegno.mouseDragged(e);
		}

	};

	public MouseAdapter getMouseAdapter() {
		return mouseAdapter;
	}

	public MouseMotionAdapter getMouseMotionAdapter() {
		return mouseMotionAdapter;
	}
}
