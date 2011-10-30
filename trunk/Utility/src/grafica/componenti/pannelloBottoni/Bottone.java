package grafica.componenti.pannelloBottoni;

import grafica.componenti.button.ToggleBtn;

import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Bottone è un pannello che contiene all'interno un AbstractButton o un bottone
 * che lo estende. La classe non è completamente affidabile e nemmeno abbastanza
 * generale, va ripensata
 * 
 */
public class Bottone extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JButton bottone;
	private final ToggleBtn toggleBottone;
	private GridLayout grid;
	private JPanel contentPane = new JPanel();

	private GridLayout grid2;

	public Bottone(final JButton bottone) {
		initLayout();
		this.setBorder(null);
		this.bottone = bottone;
		toggleBottone = null;
		// bottone.settaggioBottoneStandard();
		this.add(bottone);
		contentPane.setLayout(grid);

		this.setLayout(grid2);
	}

	public Bottone(final ToggleBtn bottone) {
		this.bottone = null;
		this.setBorder(null);
		this.toggleBottone = bottone;
		bottone.settaggioBottoneStandard();
		this.add(bottone);
		initLayout();
		contentPane.setLayout(grid);
		this.setLayout(grid2);
	}

	private void initLayout() {
		grid2 = new GridLayout(2, 1);
		grid2.setVgap(0);
		grid2.setHgap(0);
		grid = new GridLayout(1, 0);
		grid.setVgap(0);
		grid.setHgap(0);

	}

	public ToggleBtn getTBottone() {
		return toggleBottone;
	}

	public JPanel getContent() {
		return contentPane;
	}

	public void setContent(final JPanel content) {
		this.contentPane = content;
		this.add(contentPane);
		contentPane.setVisible(false);
	}

	public JButton getJBottone() {
		return this.bottone;
	}

	public AbstractButton getBottone() {
		if (bottone != null) {
			return bottone;
		} else if (toggleBottone != null) {
			return toggleBottone;
		} else {
			return new ToggleBtn("");
		}
	}

}