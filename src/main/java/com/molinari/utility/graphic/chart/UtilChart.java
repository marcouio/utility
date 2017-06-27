package com.molinari.utility.graphic.chart;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;

import com.molinari.utility.controller.ControlloreBase;

public class UtilChart {

	private UtilChart() {
		//do nothing
	}
	
	public static byte[] exportFromChart(Dataset dataset, String name, String categoryAxisLabel, String valueAxisLabel, int width, int height) {
		try {
			JFreeChart chart = null;
			if(dataset instanceof PieDataset){
				chart = ChartFactory.createPieChart(name, (PieDataset) dataset, true, true, true);
			}else if(dataset instanceof CategoryDataset){
				chart = ChartFactory.createLineChart(name, categoryAxisLabel, valueAxisLabel, (CategoryDataset)dataset, PlotOrientation.VERTICAL, true, true, true);
				final CategoryPlot plot = chart.getCategoryPlot();
				final LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);
				renderer.setSeriesShapesFilled(0, true);
				plot.setRenderer(renderer);
			}

			if(chart != null){
				BufferedImage bufImage = chart.createBufferedImage(width, height);
				
				return ChartUtilities.encodeAsPNG(bufImage);
			}
		} catch (Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		
		return new byte[]{};
	}
	
	public static boolean saveChart(String path, String prefixName, String categoryAxisLabel, String valueAxisLabel, Dataset dataset, int width, int height) {
		final GregorianCalendar data = new GregorianCalendar();
		final String dataMinuti = Integer.toString(data.get(Calendar.HOUR_OF_DAY)) + data.get(Calendar.MINUTE);
		JFreeChart chart = null;
		if(dataset instanceof PieDataset){
			chart = ChartFactory.createPieChart(prefixName, (PieDataset) dataset, true, true, true);
		}else if(dataset instanceof CategoryDataset){
			chart = ChartFactory.createLineChart(prefixName, categoryAxisLabel, valueAxisLabel, (CategoryDataset)dataset, PlotOrientation.VERTICAL, true, true, true);
		}
		try {
			File pathFile = new File(path);
			boolean exists = pathFile.exists();
			if(!exists){
				pathFile.mkdir();
			}
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(pathFile.getAbsolutePath());
			stringBuilder.append(File.pathSeparator);
			stringBuilder.append(prefixName);
			stringBuilder.append(dataMinuti);
			stringBuilder.append(".png");
			if(chart != null){
				ChartUtilities.saveChartAsPNG(new java.io.File(stringBuilder.toString()), chart, width, height);
			}
			return true;
		} catch (IOException e1) {
			ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
			return false;
		}

	}
}
