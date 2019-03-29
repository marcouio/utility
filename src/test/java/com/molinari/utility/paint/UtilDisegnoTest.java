package com.molinari.utility.paint;

import java.awt.Point;
import java.awt.Rectangle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.molinari.utility.paint.objects.poligoni.Rettangolo;

public class UtilDisegnoTest {

	private Rettangolo rettangolo = new Rettangolo();
	
	@Before
	public void init() {
		rettangolo.setSize(100, 100);
		rettangolo.setLocation(0, 0);
	}
	
	@Test
	public final void testIsInRegion() {
		final Rectangle rect = new Rectangle(rettangolo.getX(), rettangolo.getY(), rettangolo.getWidth(), rettangolo.getHeight());
		boolean inRectangle = UtilDisegno.isInRegion(new Point(40, 40), rect);
		Assert.assertEquals(true, inRectangle);
		
		inRectangle = UtilDisegno.isInRegion(new Point(200, 290), rect);
		Assert.assertEquals(false, inRectangle);
	}

	@Test
	public final void testIsMouseNearPoint() {
		boolean mouseNearPoint = UtilDisegno.isMouseNearPoint(new Point(40, 40), new Point(40, 50));
		Assert.assertEquals(false, mouseNearPoint);

		mouseNearPoint = UtilDisegno.isMouseNearPoint(new Point(40, 40), new Point(50, 40));
		Assert.assertEquals(false, mouseNearPoint);

		mouseNearPoint = UtilDisegno.isMouseNearPoint(new Point(40, 40), new Point(40, 40));
		Assert.assertEquals(true, mouseNearPoint);
		
		mouseNearPoint = UtilDisegno.isMouseNearPoint(new Point(40, 40), new Point(43, 37));
		Assert.assertEquals(true, mouseNearPoint);
	}

	@Test
	public final void testMakePointByAngle() {
		Point makePointByAngle = UtilDisegno.makePointByAngle(new Point(1, 100), Math.toRadians(0), 1);
		Assert.assertEquals(new Point(2, 100), makePointByAngle);

		makePointByAngle = UtilDisegno.makePointByAngle(new Point(1, 100), Math.toRadians(180), 1);
		Assert.assertEquals(new Point(0, 100), makePointByAngle);

		makePointByAngle = UtilDisegno.makePointByAngle(new Point(1, 100), Math.toRadians(90), 1);
		Assert.assertEquals(new Point(1, 101), makePointByAngle);

		makePointByAngle = UtilDisegno.makePointByAngle(new Point(1, 100), Math.toRadians(270), 1);
		Assert.assertEquals(new Point(1, 99), makePointByAngle);
	}

	@Test
	public final void testMakeLengthByAngle() {
		int makeLengthByAngle = UtilDisegno.makeLengthByAngle(new Point(0, 0), new Point(100, 0));
		Assert.assertEquals(100, makeLengthByAngle);
	}

	@Test
	public final void testMakeHeightByAngle() {
		int makeLengthByAngle = UtilDisegno.makeHeightByAngle(new Point(0, 100), new Point(0, 0));
		Assert.assertEquals(100, makeLengthByAngle);
	}

}
