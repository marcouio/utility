package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

public class OggettoDeleteBase extends OggettoSQL{

	public OggettoDeleteBase(final Connection cn) {
		super(cn);
	}

	public boolean delete(final String comandoSql){
		return false;
	}
	public boolean delete(final String tabella, final HashMap<String, Object> campi){
		return false;
	}


	private boolean gestioneIstruzioneDelete(final String tabella, final HashMap<String, String> clausole, final boolean ok,
			final StringBuffer sql, final String command) throws SQLException {
		sql.append(command).append(" FROM ").append(tabella);
		if (!clausole.isEmpty()) {
			sql.append(" WHERE 1=1");
			final Iterator<String> where = clausole.keySet().iterator();
			while (where.hasNext()) {
				sql.append(" AND ");

				final String prossimo = where.next();
				sql.append(prossimo).append(" = ");

				try {
					sql.append(Integer.parseInt(clausole.get(prossimo)));
				} catch (final NumberFormatException e) {
					sql.append("'" + clausole.get(prossimo) + "'");
				}
			}
			if (where.hasNext()) {
				sql.append(", ");
			}
			//			final Connection cn = DBUtil.getConnection();
			//			final Statement st = cn.createStatement();
			//			if (st.executeUpdate(sql.toString()) != 0) {
			//				ok = true;
			//			}
			cn.close();
		}
		return ok;
	}

}
