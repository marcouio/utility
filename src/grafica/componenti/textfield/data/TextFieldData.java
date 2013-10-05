package grafica.componenti.textfield.data;

import grafica.componenti.UtilComponenti;
import grafica.componenti.componenteBase.IComponenteBase;
import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.style.StyleBase;
import grafica.componenti.textfield.TextFieldBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.Container;

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
				final FrameBase frame = UtilComponenti.initContenitoreFrameApplicazione(null, null);
				final TextFieldData textFieldData = new TextFieldData("dd-MM-yyyy", frame.getContentPane());
				final TextFieldTesto textFieldTesto = new TextFieldTesto("testo", frame.getContentPane());
				textFieldTesto.posizionaSottoA(textFieldData, 0, 0);
			}
		});
	}

	private static final long serialVersionUID = 1L;

	public TextFieldData(final String format, final Container componentePadre) {
		super(format, componentePadre);
		formatter = new FormatterData(format);
	}

	@Override
	public void applicaStile(StyleBase styleBase, IComponenteBase comp) {
		if (styleBase == null) {
			styleBase = new StyleBase("StyleTFData");
		}
		super.applicaStile(styleBase, comp);
	}
}
