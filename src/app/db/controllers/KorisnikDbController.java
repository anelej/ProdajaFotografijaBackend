package app.db.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import app.api.dto.LoginResponseDto;
import app.db.entities.Korisnik;
import app.db.entities.KorisnikReport;

public class KorisnikDbController extends AbstractDatabaseController<Korisnik> {

	public KorisnikDbController() {
		super(Korisnik.class);
	}

	public LoginResponseDto Login(String username, String password) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}

		try {
			LoginResponseDto response = new LoginResponseDto();
			// TODO add activation of user
			String sql = "select Id, Uloga, PromenaPassworda from Korisnik where username='" + username
					+ "' and password='" + password + "' and Aktivan = 1 and Deaktiviran = 0";

			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			response.setId(-1);
			if (rs.next()) {
				response.setId((int) rs.getObject("Id"));
				response.setUloga((int) rs.getObject("Uloga"));
				response.setPromenaPassworda((boolean) rs.getObject("PromenaPassworda"));
				closeStat(st);
				closeResultSet(rs);

				String sqlGetPurchasedPhotos = "select f.Id from IstorijaKupovine i "
						+ " join FotografijaKatalog k on i.fotografijaKatalogId = k.Id "
						+ "join Fotografija f on f.Id = k.FotografijaId " + "where KorisnikId = " + response.getId();
				PreparedStatement stGetPurchasedPhotos = conn.prepareStatement(sqlGetPurchasedPhotos);
				ResultSet rsGetPurchasedPhotos = stGetPurchasedPhotos.executeQuery();
				while (rsGetPurchasedPhotos.next()) {
					response.getListPurchasePhoto().add((int) rsGetPurchasedPhotos.getObject("Id"));
				}
				closeStat(stGetPurchasedPhotos);
				closeResultSet(rsGetPurchasedPhotos);
				return response;
			} else {
				closeStat(st);
				closeResultSet(rs);
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	public boolean checkForUsername(Korisnik korisnik) {
		if (korisnik == null) {
			return false;
		}
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		String sqlCheckUsername = "select * from korisnik where username = '" + korisnik.getUsername()
				+ "' and Deaktiviran = 0";
		PreparedStatement stCheckUsername;
		try {
			stCheckUsername = conn.prepareStatement(sqlCheckUsername);
			ResultSet rs = stCheckUsername.executeQuery();
			if (rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			closeConnection(conn);
		}
	}

	public boolean activateUserById(String username) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		String activateUserSql = "UPDATE Korisnik SET Aktivan= 1 WHERE Username='" + username + "'";
		PreparedStatement stActivate;
		try {
			stActivate = conn.prepareStatement(activateUserSql);
			int result = stActivate.executeUpdate();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(conn);
		}
	}

	public List<Korisnik> getSellers() {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn
					.prepareStatement("select * from Korisnik where Uloga in (1, 2, 3) and Deaktiviran = 0");
			ResultSet rs = st.executeQuery();
			List<Korisnik> list = new ArrayList<Korisnik>();
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

	public List<Korisnik> getUsersForTest() {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement("SELECT Id, Username FROM korisnik k "
					+ "WHERE (SELECT COUNT(Id) FROM Fotografija WHERE ProdavacId = k.Id) > 9 and k.Deaktiviran = 0");
			ResultSet rs = st.executeQuery();
			List<Korisnik> list = new ArrayList<Korisnik>();
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

	public boolean userBecomeSeller(int id) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		String sqlCheckUsername = "UPDATE Korisnik SET Uloga= 1 WHERE Id=" + id;
		PreparedStatement stCheckUsername;
		try {
			stCheckUsername = conn.prepareStatement(sqlCheckUsername);
			int result = stCheckUsername.executeUpdate();
			if (result < 1) {
				return false;
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(conn);
		}
	}

	public List<Integer> getSellersOfBougthPhotos(int id) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement(
					"select f.ProdavacId from Korisnik k " + "join IstorijaKupovine i on i.KorisnikId = k.Id "
							+ "join FotografijaKatalog fk on fk.Id = i.FotografijaKatalogId "
							+ "join Fotografija f on f.Id = fk.FotografijaId where k.Id = " + id);
			ResultSet rs = st.executeQuery();
			List<Integer> list = new ArrayList<Integer>();
			while (rs.next()) {
				list.add((int) rs.getObject("f.ProdavacId"));
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

	public List<KorisnikReport> getReportForUser(int id) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			String sql = "select f.Naziv, fk.Rezolucija, fk.Cena, i.DatumKupovine " + "from Korisnik k"
					+ " left join istorijakupovine i on i.KorisnikId = k.Id"
					+ " left join fotografijakatalog fk on fk.Id = i.FotografijaKatalogId"
					+ " left join fotografija f on f.Id = fk.FotografijaId" + " where k.Id = " + id
					+ " and k.Deaktiviran = 0";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<KorisnikReport> list = new ArrayList<KorisnikReport>();
			while (rs.next()) {
				list.add(readFromResultSetForKorisnikReport(rs));
			}
			closeStat(st);
			closeResultSet(rs);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			for (KorisnikReport korisnikReport : list) {
				if (korisnikReport.getDatumKupovine() != null) {
					String dateString = df.format(korisnikReport.getDatumKupovine());
					korisnikReport.setDatumKupovineString(dateString);
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	protected KorisnikReport readFromResultSetForKorisnikReport(ResultSet rs) {
		if (rs == null) {
			return null;
		}

		try {
			KorisnikReport object = new KorisnikReport();
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

	public List<Korisnik> getUsersBasedOnRole(int role) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}

		String sqlCheckUsername = "Select * from Korisnik where Uloga <= " + role + " and deaktiviran = 0";
		PreparedStatement stCheckUsername;
		List<Korisnik> list = new ArrayList<Korisnik>();
		try {
			stCheckUsername = conn.prepareStatement(sqlCheckUsername);
			ResultSet rs = stCheckUsername.executeQuery();
			while (rs.next()) {
				list.add(readFromResultSet(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection(conn);
		}
	}

	public boolean deactivate(int id) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		String sqlCheckUsername = "update Korisnik set deaktiviran = 1 where id = " + id;
		PreparedStatement stCheckUsername;
		try {
			stCheckUsername = conn.prepareStatement(sqlCheckUsername);
			int result = stCheckUsername.executeUpdate();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(conn);
		}
	}

	public boolean changePassword(Korisnik korisnik) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		String sqlCheckUsername = "update Korisnik set password = '" + korisnik.getPassword()
				+ "' , promenapassworda= 0 where id = " + korisnik.getId();
		PreparedStatement stCheckUsername;
		try {
			stCheckUsername = conn.prepareStatement(sqlCheckUsername);
			int result = stCheckUsername.executeUpdate();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection(conn);
		}
	}

	public String resetPassword(String username) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}

		String sqlCheckUsername = "update Korisnik set password = '" + username + "' where username = '" + username
				+ "'";
		PreparedStatement stCheckUsername;
		try {
			stCheckUsername = conn.prepareStatement(sqlCheckUsername);
			int result = stCheckUsername.executeUpdate();
			if (result > 0) {
				String sqlEmail = "select email from korisnik where username = '" + username + "'";
				PreparedStatement st = conn.prepareStatement(sqlEmail);
				ResultSet rs = st.executeQuery();

				String email = "";
				if (rs.next()) {
					email = rs.getString("email");
				}
				closeStat(st);
				closeResultSet(rs);
				return email;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeConnection(conn);
		}
	}
}
