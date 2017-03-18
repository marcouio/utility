package com.molinari.utility.graphic.print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public abstract class DefaultPrint implements IPrint, Pageable {

	private Image offscreen;
	private PageFormat pageFormat;
	private int numebersOfPage;
	private Printable printable;

	public DefaultPrint(final Image offscreen) {
		super();
		this.offscreen = offscreen;
	}

	@Override
	public void print() {
		// richiamato servizio di stampa nativo
		final PrinterJob job = PrinterJob.getPrinterJob();

		// pageble: definisce il numero di pagine per la stampare con
		// getNumberOfPages()), cosa stampare con print() e come stampare
		// con getPageFormat()
		// assegno il pageable al servizio di stampa
		job.setPageable(this);

		if (job.printDialog()) {
			try {
				job.print();
			} catch (final PrinterException exc) {
				System.out.println(exc);
				exc.printStackTrace();
			}
		}

	}

	// //restituisce il numero di pagine da stampare
	@Override
	public int getNumberOfPages() {
		numebersOfPage = 1;
		return numebersOfPage;
	}

	// TODO se possibile evitare che compaia questa dialog e concentrare tutte
	// le personalizzazioni nella
	// //dialog di scelta della stampante
	// //apre una dialog per customizzare il layout
	@Override
	public PageFormat getPageFormat(final int pageIndex) throws IndexOutOfBoundsException {
		final PrinterJob job = PrinterJob.getPrinterJob();
		pageFormat = job.pageDialog(job.defaultPage());
		return pageFormat;
	}

	@Override
	public Printable getPrintable(final int pageIndex) throws IndexOutOfBoundsException {
		printable = new Printable() {

			@Override
			public int print(final Graphics g, final PageFormat pageFormat, final int pageIndex) throws PrinterException {
				if (pageIndex > 0) {
					return NO_SUCH_PAGE;
				} else {
					final Graphics2D g2 = (Graphics2D) g;
					// il rendering dell'immagine � spostato per farlo rientrare
					// nello spazio stampabile della pagina
					g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

					// calcola una costante per scalare la grandezza
					// dell'immagine
					double costante = (pageFormat.getImageableWidth() - pageFormat.getImageableX()) / offscreen.getWidth(null);
					if (offscreen.getHeight(null) * costante > pageFormat.getImageableHeight()) {
						costante = (pageFormat.getImageableHeight() - pageFormat.getImageableY()) / offscreen.getHeight(null);
					}
					// il graphics viene scalato per far rientrare l'immagine
					// nella pagina
					g2.scale(costante, costante);
					g2.drawImage(offscreen, (int) (pageFormat.getImageableX()), (int) (pageFormat.getImageableY()), null);
					return PAGE_EXISTS;
				}
			}
		};
		return printable;
	}

	public void setOffscreen(final Image offscreen) {
		this.offscreen = offscreen;
	}

	public void setPrintable(final Printable printable) {
		this.printable = printable;
	}

	public void setPageFormat(final PageFormat pageFormat) {
		this.pageFormat = pageFormat;
	}

	public void setNumebersOfPage(final int numebersOfPage) {
		this.numebersOfPage = numebersOfPage;
	}

}
