package app.db.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.api.dto.FotografijaRateDto;
import app.api.dto.FotografijaSearchDto;
import app.db.entities.Fotografija;
import app.db.entities.FotografijaDetails;
import app.db.entities.FotografijaKatalog;
import app.db.entities.FotografijaKeyword;

public class FotografijaDbController extends AbstractDatabaseController<Fotografija> {

	public FotografijaDbController() {
		super(Fotografija.class);
	}

	public boolean updateUserRoleToBuyer(long id) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		try {
			String sql = "update User set Uloga = 1 where Id = " + id;
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// public boolean checkIfBuyerCanBecomeSeller(FotografijaRateDto photo) {
	// Connection conn = createConnection();
	// if (conn == null) {
	// return false;
	// }
	//
	// try {
	// String sql = "select Ocena from FotografijaOcena where FotografijaId in "
	// + "(select Id from Fotografija where ProdavacId = " + photo.getProdavacId() +
	// ")";
	// PreparedStatement st = conn.prepareStatement(sql);
	// ResultSet rs = st.executeQuery();
	// double sum = 0;
	// int numberOfRows = 0;
	// while (rs.next()) {
	// numberOfRows++;
	// }
	// sum = sum / numberOfRows;
	// return sum > 8;
	// } catch (SQLException e) {
	// e.printStackTrace();
	// return false;
	// }
	// }

	private List<FotografijaKeyword> getListFotografijaKeywordForFotografijaId(long fotografijaId) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		List<FotografijaKeyword> fotografijaKeywordList = new ArrayList<FotografijaKeyword>();
		try {
			String sql = "select * from FotografijaKeyword where FotografijaId=" + fotografijaId;
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			FotografijaKeywordDbController fotografijaKeywordDbController = new FotografijaKeywordDbController();
			while (rs.next()) {
				FotografijaKeyword fotografijaKeyword = new FotografijaKeyword();
				fotografijaKeyword = fotografijaKeywordDbController.readFromResultSet(rs);
				fotografijaKeywordList.add(fotografijaKeyword);
			}
			closeStat(st);
			closeResultSet(rs);
			return fotografijaKeywordList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	private List<FotografijaKatalog> getListFotografijaKatalogForFotografijaId(long fotografijaId) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		List<FotografijaKatalog> fotografijaKatalogList = new ArrayList<FotografijaKatalog>();
		try {
			String sql = "select * from FotografijaKatalog where FotografijaId=" + fotografijaId;
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			FotografijaKatalogDbController fotografijaKatalogDbController = new FotografijaKatalogDbController();
			while (rs.next()) {
				FotografijaKatalog fotografijaKatalog = new FotografijaKatalog();
				fotografijaKatalog = fotografijaKatalogDbController.readFromResultSet(rs);
				fotografijaKatalogList.add(fotografijaKatalog);
			}
			closeStat(st);
			closeResultSet(rs);
			return fotografijaKatalogList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	// public FotografijaDto getFotografijaById(long id) {
	// Fotografija foto = getById(id);
	//
	// if (foto != null) {
	// foto.setFotografijaKatalogList(getListFotografijaKatalogForFotografijaId(id));
	// foto.setFotografijaKeywordList(getListFotografijaKeywordForFotografijaId(id));
	// FotografijaDto result = new FotografijaDto(foto);
	// result.setOcena(getPhotoGrade(foto.getId()));
	// return result;
	// }
	// return null;
	// }

	private double getPhotoGrade(long id) {
		Connection conn = createConnection();
		if (conn == null) {
			return -1;
		}

		try {
			String sql = "select Ocena from FotografijaOcena where FotografijaId = " + id;
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			double sum = 0;
			int numberOfRows = 0;
			while (rs.next()) {
				sum = +(int) rs.getObject("Ocena");
				numberOfRows++;
			}
			sum = sum / numberOfRows;
			return sum;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<FotografijaDetails> searchPhoto(FotografijaSearchDto fotografijaSearch) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		List<FotografijaDetails> fotografijaList = new ArrayList<FotografijaDetails>();
		try {
			String sql = "select f.*, k.Username as 'Username', kat.Naziv from Fotografija f join Korisnik k on f.ProdavacId = k.Id"
					+ " left join fotografijaKategorija kat on f.fotografijaKategorijaId = kat.Id ";

			if (fotografijaSearch != null) {
				if (fotografijaSearch.isKupljeneFotografije()) {
					sql += " join FotografijaKatalog katalog on katalog.FotografijaId "
							+ " join IstorijaKupovine i on (i.FotografijaKatalogId = katalog.Id and i.KorisnikId = " + fotografijaSearch.getKupacId() +") ";
				}
				if (fotografijaSearch.getKeyword() != null && !fotografijaSearch.getKeyword().isEmpty()) {
					sql += " left join FotografijaKeyword fk on f.Id = fk.FotografijaId ";
				}
				int odobrena = fotografijaSearch.isOdobrena() ? 1 : 0;
				sql += " where f.Odobrena = " + odobrena;
				sql += " and k.Uloga > 0 ";
				if (fotografijaSearch.getKeyword() != null && !fotografijaSearch.getKeyword().isEmpty()) {
					sql += " and UPPER(fk.Keyword) LIKE UPPER('%" + fotografijaSearch.getKeyword() + "%')";
				}
				if (fotografijaSearch.getFotografijaKategorijaId() != 0) {
					sql += " and f.fotografijaKategorijaId = " + fotografijaSearch.getFotografijaKategorijaId();
				}
				if (fotografijaSearch.getNaziv() != null && !fotografijaSearch.getNaziv().isEmpty()) {
					sql += " and UPPER(f.Naziv) LIKE UPPER('%" + fotografijaSearch.getNaziv() + "%')";
				}
				if (fotografijaSearch.getProdavacId() != 0) {
					sql += " and f.ProdavacId = " + fotografijaSearch.getProdavacId();
				}
				if (fotografijaSearch.isKupljeneFotografije()) {
					sql += " group by i.KorisnikId ";
				}
				if (fotografijaSearch.getSortOrder() != null && !fotografijaSearch.getSortOrder().isEmpty()) {
					sql += " order by " + fotografijaSearch.getSortOrder();

					if (fotografijaSearch.getSortDirection() != null && !fotografijaSearch.getSortDirection().isEmpty()) {
						sql += " " + fotografijaSearch.getSortDirection();
					} else {
						sql += " asc";
					}
				}
			} else {
				sql += " order by f.naziv acs";
			}
			int page = fotografijaSearch.getPage() != 0 ? fotografijaSearch.getPage() - 1 : 0;
			int pageSize = fotografijaSearch.getPageSize() != 0 ? fotografijaSearch.getPageSize() : 1000000;
			sql += " limit " + page * pageSize + " , " + pageSize;

			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				FotografijaDetails fotografija = new FotografijaDetails();
				fotografija = readFromResultSetFotografijaDetails(rs);
				fotografijaList.add(fotografija);
			}
			closeStat(st);
			closeResultSet(rs);
			return fotografijaList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	public FotografijaDetails readFromResultSetFotografijaDetails(ResultSet rs) {
		if (rs == null) {
			return null;
		}

		try {
			FotografijaDetails object = new FotografijaDetails();
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

	public FotografijaDetails getFotografijaDetails(int id, int korisnikId) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}

		String sql = "select f.*, k.username, kat.Naziv from fotografija f "
				+ "left join fotografijakategorija kat on f.fotografijaKategorijaId = kat.Id "
				+ "left join korisnik k on f.ProdavacId = k.Id where f.id = " + id;
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery(sql);

			FotografijaDetails object = null;
			if (rs.next()) {
				object = readFromResultSetFotografijaDetails(rs);
			}
			closeStat(st);
			closeResultSet(rs);

			List<FotografijaKeyword> fotografijakeyword = getListFotografijaKeywordForFotografijaId(id);
			if (fotografijakeyword != null) {
				object.setFotografijaKeywordList(fotografijakeyword);
			}
			List<FotografijaKatalog> fotografijaKatalog = getListFotografijaKatalogForFotografijaId(id);
			if (fotografijaKatalog != null) {
				object.setFotografijaKatalogList(fotografijaKatalog);
			}
			double averageGrade = getPhotoGrade(id);
			if (averageGrade != -1) {
				object.setOcena(averageGrade);
			}
			int userGrade = (int) getPhotoGradeForUser(id, korisnikId);
			if (userGrade != -1) {
				object.setFotografijaOcena(userGrade);
			}
			return object;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	public boolean ratePhoto(FotografijaRateDto photo) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}
		try {
			String sql = "INSERT INTO FotografijaOcena (FotografijaId, Ocena, KorisnikId) " + "VALUES ("
					+ photo.getFotografijaId() + ", " + photo.getOcena() + ", " + photo.getKorisnikId() + ")";
			PreparedStatement st = conn.prepareStatement(sql);
			int id = st.executeUpdate();
			if (id > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int checkIfRateExists(FotografijaRateDto photo) {
		Connection conn = createConnection();
		if (conn == null) {
			return -2;
		}

		try {
			String sql = "select Id from FotografijaOcena where FotografijaId = " + photo.getFotografijaId()
					+ " and KorisnikId = " + photo.getKorisnikId();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return (int) rs.getObject("Id");
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		}
	}

	public boolean updateRatePhoto(FotografijaRateDto photo, int id) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		try {
			String sql = "update FotografijaOcena set Ocena = " + photo.getOcena() + " where Id = " + id;
			PreparedStatement st = conn.prepareStatement(sql);
			int update = st.executeUpdate();
			if (update > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean approvePhoto(int id) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		try {
			String sql = "update Fotografija set Odobrena = 1 where Id = " + id;
			PreparedStatement st = conn.prepareStatement(sql);
			int update = st.executeUpdate();
			if (update > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<FotografijaDetails> getPhotosFromBuyer(int id) {
		List<FotografijaDetails> fotografijaList = new ArrayList<FotografijaDetails>();
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}

		try {
			String sql = "select * from Fotografija f join Korisnik k on f.ProdavacId = k.Id "
					+ "where f.Odobrena = 0 and k.Uloga = 0 and k.Id = " + id;
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				FotografijaDetails fotografija = new FotografijaDetails();
				fotografija = readFromResultSetFotografijaDetails(rs);
				fotografijaList.add(fotografija);
			}
			closeStat(st);
			closeResultSet(rs);
			return fotografijaList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	public boolean removePhotos(List<Integer> photoIds) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		try {
			String sql = "delete from fotografija where Id in (";
			for (Integer id : photoIds) {
				sql += id + ", ";
			}
			sql = sql.substring(0, sql.length() - 2);
			sql += ")";
			PreparedStatement st = conn.prepareStatement(sql);
			int result = st.executeUpdate();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return false;
	}

	private double getPhotoGradeForUser(long photoId, int userId) {
		Connection conn = createConnection();
		if (conn == null) {
			return -1;
		}

		try {
			String sql = "select Ocena from FotografijaOcena where FotografijaId = " + photoId + " and KorisnikId = "
					+ userId;
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return (int) rs.getObject("Ocena");
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public long getNumberOfPhotosAddedTodayForUser(int userId) {
		Connection conn = createConnection();
		if (conn == null) {
			return -1;
		}

		try {
			String sql = "select count(Id) from Fotografija where ProdavacId = " + userId + " and DatumPostavljanja = CURDATE()";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return (long) rs.getObject("count(Id)");
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public long getNumberOfPhotosAddedThisWeekForUser(int userId) {
		Connection conn = createConnection();
		if (conn == null) {
			return -1;
		}

		try {
			String sql = "select count(Id) from Fotografija where ProdavacId = " + userId 
					+ " and YEARWEEK(DatumPostavljanja, 1) = YEARWEEK(CURDATE(), 1)";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return (long) rs.getObject("count(Id)");
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
