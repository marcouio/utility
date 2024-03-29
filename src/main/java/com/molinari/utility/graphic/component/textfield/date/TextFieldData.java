package com.molinari.utility.graphic.component.textfield.date;

import java.awt.Container;

import com.molinari.utility.graphic.component.base.IComponenteBase;
import com.molinari.utility.graphic.component.style.StyleBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;

/**
 * TextFieldData è una specializzazione di JTextField per contenere e gestire
 * date
 * 
 */
public class TextFieldData extends TextFieldBase {

	private static final long serialVersionUID = 1L;
	private String format;

	public TextFieldData(final String format, final Container componentePadre) {
		super(format, componentePadre);
		setFormatter(new FormatterData(format));
		this.setFormat(format);
	}

	@Override
	public void applicaStile(StyleBase styleBase, IComponenteBase comp) {
		StyleBase style = styleBase;
		if (style == null) {
			style = new StyleBase("StyleTFData");
		}
		super.applicaStile(style, comp);
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
