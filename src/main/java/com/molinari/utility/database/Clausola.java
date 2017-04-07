package com.molinari.utility.database;

import com.molinari.utility.math.UtilMath;

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
		
		@Override
		public String toString() {
			String campoAlias = getCampoAlias(campo, alias);
			if(campo.equals(alias) || alias == null){
				campoAlias = campo;
			}
			if (valore == null) {
				valore = "null";
			}else{
				boolean number = UtilMath.isNumber(valore);
				if(!number){
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
