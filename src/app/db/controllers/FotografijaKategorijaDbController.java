package app.db.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.db.entities.FotografijaKategorija;
import app.db.entities.FotografijaKategorijaReport;

public class FotografijaKategorijaDbController extends AbstractDatabaseController<FotografijaKategorija> {

	public FotografijaKategorijaDbController() {
		super(FotografijaKategorija.class);
	}

	public List<FotografijaKategorijaReport> getCategoriesReport() {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			String sql = "SELECT fk.Naziv, SUM(i.Id), AVG(kat.Cena), t.Rezolucija " 
					+ " FROM fotografija f "
					+ " JOIN fotografijakatalog kat ON f.Id = kat.FotografijaId "
					+ " left JOIN istorijaKupovine i on i.FotografijaKatalogId = kat.Id "
					+ " right JOIN fotografijakategorija fk on fk.Id = f.FotografijaKategorijaId "
					+ " JOIN (SELECT fk.Id as Id, fk.naziv as Naziv, kat.rezolucija as Rezolucija "
							+ " FROM fotografija f " 
							+ " JOIN fotografijakatalog kat ON f.Id = kat.FotografijaId "
							+ " right JOIN fotografijakategorija fk on fk.Id = f.FotografijaKategorijaId "
							+ " GROUP BY kat.rezolucija " + " ORDER BY kat.rezolucija DESC) t on t.Id = fk.Id "
					+ " GROUP BY fk.Naziv";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<FotografijaKategorijaReport> list = new ArrayList<FotografijaKategorijaReport>();
			while (rs.next()) {
				list.add(readFromResultSetForFotografijaKategorijaReport(rs));
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

	protected FotografijaKategorijaReport readFromResultSetForFotografijaKategorijaReport(ResultSet rs) {
		if (rs == null) {
			return null;
		}

		try {
			FotografijaKategorijaReport object = new FotografijaKategorijaReport();
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

	public boolean update(FotografijaKategorija object) {
		if (object == null) {
			return false;
		}
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		String strQuery = "UPDATE FotografijaKategorija SET Naziv = '" + object.getNaziv() + "' where id = "
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
	
	@Override
	public List<FotografijaKategorija> getAll() {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement(String.format("select * from fotografijakategorija where id != 0"));
			ResultSet rs = st.executeQuery();
			List<FotografijaKategorija> list = new ArrayList<FotografijaKategorija>();
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
}
