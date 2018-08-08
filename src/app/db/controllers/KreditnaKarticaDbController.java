package app.db.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.db.entities.KreditnaKartica;

public class KreditnaKarticaDbController extends AbstractDatabaseController<KreditnaKartica>{

	public KreditnaKarticaDbController() {
		super(KreditnaKartica.class);
	}
	
	public List<KreditnaKartica> getForUser(int id) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement("select * from KreditnaKartica where KorisnikId = " + id);
			ResultSet rs = st.executeQuery();
			List<KreditnaKartica> list = new ArrayList<KreditnaKartica>();
			while (rs.next()) {
				list.add(readFromResultSet(rs));
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
	
	public boolean update(KreditnaKartica object) {
		if (object == null) {
			return false;
		}
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		String strQuery = "UPDATE KreditnaKartica SET BrojKartice = '" + object.getBrojKartice() + "' where id = "
				+ object.getId();

		try {
			PreparedStatement st = conn.prepareStatement(strQuery);
			int result = st.executeUpdate();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return false;
	}
}
