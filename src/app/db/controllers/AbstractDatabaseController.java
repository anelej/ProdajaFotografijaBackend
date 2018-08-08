package app.db.controllers;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import app.db.entities.BasicEntity;

public abstract class AbstractDatabaseController<T extends BasicEntity> {

	private String USERNAME = "root";
	private String PASSWORD = "";
	private Class<T> clazz;

	public AbstractDatabaseController(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected Connection createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			return DriverManager.getConnection("jdbc:mysql://localhost/webprogramiranje", USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	protected void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void closeStat(PreparedStatement stat) {
		if (stat == null) {
			return;
		}

		try {
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void closeResultSet(ResultSet rs) {
		if (rs == null) {
			return;
		}

		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected T readFromResultSet(ResultSet rs) {
		if (rs == null) {
			return null;
		}

		try {
			T object = this.clazz.getConstructor().newInstance();
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
		} catch (java.lang.InstantiationException | java.lang.IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long add(T object) {
		if (object == null) {
			return -1;
		}
		Connection conn = createConnection();
		if (conn == null) {
			return -1;
		}

		String columnsName = "";
		String questionMarks = "";
		for (String columnName : object.getColumnsName()) {
			if (columnName.equals("Id"))
				continue;
			columnsName = columnsName == "" ? columnName : String.format("%s, %s", columnsName, columnName);
			questionMarks = questionMarks == "" ? "?" : String.format("%s, ?", questionMarks);
		}

		String strQuery = String.format("INSERT INTO %s (%s) VALUES (%s)", this.clazz.getSimpleName(), columnsName,
				questionMarks);

		try {
			PreparedStatement st = conn.prepareStatement(strQuery, Statement.RETURN_GENERATED_KEYS);
			int parameterIndex = 1;
			for (String columnName : object.getColumnsName()) {
				if (columnName.equals("Id"))
					continue;
				st.setObject(parameterIndex++, object.getValueForColumnName(columnName));
			}
			st.executeUpdate();
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			}else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return -1;
	}

	public T getById(long id) {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}

		try {
			String sql = "select * from " + this.clazz.getSimpleName() + " where Id=" + id;
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			T object = null;
			if (rs.next()) {
				object = readFromResultSet(rs);
			}
			closeStat(st);
			closeResultSet(rs);
			return object;
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

	public boolean removeById(long id) {
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		try {
			T objectForIdName = clazz.getConstructor().newInstance();
			PreparedStatement st = conn.prepareStatement(
					String.format("delete from %s where Id=?", this.clazz.getSimpleName(), objectForIdName.getId()));
			st.setObject(1, id);
			int result = st.executeUpdate();
			if (result > 0) {
				return true;
			} 
			return false;
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return false;
	}

	public boolean update(T object) {
		if (object == null) {
			return false;
		}
		Connection conn = createConnection();
		if (conn == null) {
			return false;
		}

		String columnsName = "";
		String questionMarks = "";
		for (String columnName : object.getColumnsName()) {
			if (columnName.equals("Id"))
				continue;
			columnsName = columnsName == "" ? columnName : String.format("%s, %s", columnsName, columnName);
			questionMarks = questionMarks == "" ? "?" : String.format("%s, ?", questionMarks);
		}

		String strQuery = String.format("UPDATE %s SET ", this.clazz.getSimpleName());
		for (String columnName : object.getColumnsName()) {
			strQuery += strQuery + columnName + "= ?";
		}

		try {
			PreparedStatement st = conn.prepareStatement(strQuery);
			int parameterIndex = 1;
			for (String columnName : object.getColumnsName()) {
				if (columnName.equals("Id"))
					continue;
				st.setObject(parameterIndex++, object.getValueForColumnName(columnName));
			}
			return st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

	public List<T> getAll() {
		Connection conn = createConnection();
		if (conn == null) {
			return null;
		}
		try {
			PreparedStatement st = conn.prepareStatement(String.format("select * from %s", this.clazz.getSimpleName()));
			ResultSet rs = st.executeQuery();
			List<T> list = new ArrayList<T>();
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
