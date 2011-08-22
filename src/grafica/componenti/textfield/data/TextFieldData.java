package grafica.componenti.textfield.data;

import grafica.componenti.textfield.TextFieldBase;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * TextFieldData è una specializzazione di JTextField per contenere e gestire
 * date
 * 
 */
public class TextFieldData extends TextFieldBase {

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JFrame inst = new JFrame();
				final TextFieldData textFieldData = new TextFieldData("dd-MM-yyyy");
				System.out.println(textFieldData.getBackground());
				textFieldData.setSize(25, 50);
				inst.getContentPane().add(textFieldData);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.pack();
			}
		});
	}

	private static final long serialVersionUID = 1L;

	public TextFieldData(final String format) {
		super(format);
		this.formatter = new FormatterData(format);
		this.style = new StyleTFData();
	}

	public class StyleTFData extends StyleBaseTF {

	}
}
