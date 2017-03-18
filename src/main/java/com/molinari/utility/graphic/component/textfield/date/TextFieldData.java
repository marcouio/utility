package main.java.com.molinari.utility.graphic.component.textfield.date;

import java.awt.Container;

import main.java.com.molinari.utility.graphic.component.base.IComponenteBase;
import main.java.com.molinari.utility.graphic.component.style.StyleBase;
import main.java.com.molinari.utility.graphic.component.textfield.TextFieldBase;

/**
 * TextFieldData è una specializzazione di JTextField per contenere e gestire
 * date
 * 
 */
public class TextFieldData extends TextFieldBase {

	private static final long serialVersionUID = 1L;

	public TextFieldData(final String format, final Container componentePadre) {
		super(format, componentePadre);
		formatter = new FormatterData(format);
	}

	@Override
	public void applicaStile(StyleBase styleBase, IComponenteBase comp) {
		StyleBase style = styleBase;
		if (style == null) {
			style = new StyleBase("StyleTFData");
		}
		super.applicaStile(style, comp);
	}
}
