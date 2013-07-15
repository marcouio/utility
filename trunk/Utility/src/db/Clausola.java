package db;

public class Clausola {

		private String alias;
		private String campo;
		private String operatore;
		private String valore;
		
		public Clausola(final String alias, final String campo, final String operatore, final String valore) {
			this.alias = alias;
			this.campo = campo;
			this.operatore = operatore;
			this.valore = valore;
			
		}
		
		public String toString() {
			String campoAlias = getCampoAlias(campo, alias);
			if(campo.equals(alias)){
				campoAlias = campo;
			}
			if (valore == null) {
				valore = "null";
			}else{
				try{
				if(valore.contains(".")){
					Double.parseDouble(valore);
				}else{
					Integer.parseInt(valore);
				}
				}catch (final NumberFormatException e) {
					if(valore.contains("@@@")){
						valore = valore.replaceFirst("@@@", "");
					}
					valore = "'" + valore + "'";
				}
			}
			
			return campoAlias + " " + operatore + " " + valore;
		}
		
		private String getCampoAlias(final String campo, final String alias){
			return alias + "." + campo;
		}
		
		public String getAlias() {
			return alias;
		}

		public String getCampo() {
			return campo;
		}

		public String getOperatore() {
			return operatore;
		}

		public String getValore() {
			return valore;
		}
}
