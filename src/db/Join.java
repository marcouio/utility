package db;

	public class Join {
		private String alias1;
		private String campo1;
		private String alias2;
		private String campo2;
		
		public Join(final String alias1, final String campo1, final String alias2, final String campo2) {
			this.alias1 = alias1;
			this.alias2 = alias2;
			this.campo1 = campo1;
			this.campo2 = campo2;
		}
		
		public String toString() {
			final String campoAlias1 = getCampoAlias(campo1, alias1);
			final String campoAlias2 = getCampoAlias(campo2, alias2);
			return campoAlias1 + " = " + campoAlias2;
		}
		
		private String getCampoAlias(final String campo, final String alias){
			return alias + "." + campo;
		}
	
}
