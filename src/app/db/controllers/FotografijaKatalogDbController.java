package app.db.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.db.entities.FotografijaKatalog;

public class FotografijaKatalogDbController extends AbstractDatabaseController<FotografijaKatalog>{

	public FotografijaKatalogDbController() {
		super(FotografijaKatalog.class);
	}
	
	public String getPhotoResolutionForUser(int photoId, int userId) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement("select fk.rezolucija from Fotografija f "
					+ "join FotografijaKatalog fk on f.Id = fk.FotografijaId "
					+ "join IstorijaKupovine i on fk.Id = i.FotografijaKatalogId where i.KorisnikId = " + userId 
					+ " and f.Id = " + photoId);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				 return (String) rs.getObject("fk.rezolucija");
			}
			closeStat(st);
			closeResultSet(rs);
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}
}
