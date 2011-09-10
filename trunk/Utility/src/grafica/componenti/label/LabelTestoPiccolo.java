package grafica.componenti.label;

public class LabelTestoPiccolo extends LabelBase{

	private static final long serialVersionUID = 1L;

	public LabelTestoPiccolo() {
		super();
		this.style = new StyleBaseLTP();
	}

	public LabelTestoPiccolo(final String string) {
		super(string);
		this.style = new StyleBaseLTP();
	}

	public class StyleBaseLTP extends StyleBaseL {

	} 

}
