package app.db.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.db.entities.Komentar;
import app.db.entities.KomentarDetails;

public class KomentarDbController extends AbstractDatabaseController<Komentar>{

	public KomentarDbController() {
		super(Komentar.class);
	}
	
	public List<KomentarDetails> getCommentsForSeller(int sellerId) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			String sql = "select *, ko.Username from Komentar k join Korisnik ko on k.KupacId = ko.Id where k.ProdavacId = " + sellerId;
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<KomentarDetails> list = new ArrayList<KomentarDetails>();
			while (rs.next()) {
				list.add(readFromResultSetKomentarDetails(rs));
			}
			closeStat(st);
			closeResultSet(rs);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}
	
	protected KomentarDetails readFromResultSetKomentarDetails(ResultSet rs) {
		if (rs == null) {
			return null;
		}

		try {
			KomentarDetails object = new KomentarDetails();
			for (String columnName : object.getColumnsName()) {
				try {
					if (rs.getObject(columnName) != null) {
						object.setValueForColumnName(columnName, rs.getObject(columnName));
					}
				} catch (SQLException e) {
					continue;
				}
			}

			return object;
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
