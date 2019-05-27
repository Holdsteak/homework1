package middleTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDAO implements IEmpDAO {
	private static final String INSERT_STMT = "INSERT INTO school VALUES (?, ?, ?, ?, ?, ?, ? ,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM school ORDER BY Seq";
	Connection conn = null;

	public void getConnection() throws SQLException {
		String url = "jdbc:sqlserver://localhost:1433;databaseName=middleTest";
		conn = DriverManager.getConnection(url, "sa", "passw0rd");
	}

	@Override
	public int insert(EmpBean emp) throws SQLException {
		int updateCount = 0;
		PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT);
		pstmt.setInt(1, emp.getSeq());
		pstmt.setString(2, emp.getYear());
		pstmt.setString(3, emp.getDateListed());
		pstmt.setString(4, emp.getCategory());
		pstmt.setString(5, emp.getDuration());
		pstmt.setDouble(6, emp.getSchoolPopulation());
		pstmt.setDouble(7, emp.getTransferPopulation());
		pstmt.setDouble(8, emp.getTransferRates());
		updateCount = pstmt.executeUpdate();
		return updateCount;
	}

	@Override
	public List<EmpBean> getAll() throws SQLException {
		EmpBean emp = null;
		List<EmpBean> emps = new ArrayList<EmpBean>();
		PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			emp = new EmpBean();
			emp.setSeq(rs.getInt(1));
			emp.setYear(rs.getString(2));
			emp.setDateListed(rs.getString(3));
			emp.setCategory(rs.getString(4));
			emp.setDuration(rs.getString(5));
			emp.setSchoolPopulation(rs.getDouble(6));
			emp.setTransferPopulation(rs.getDouble(7));
			emp.setTransferRates(rs.getDouble(8));

			emps.add(emp);
		}
		return emps;
	}
	
	@Override
	public List<String> rsMetaData() throws SQLException {
		// TODO Auto-generated method stub
		List<String> empMD = new ArrayList<String>();
		PreparedStatement pstmt = conn.prepareStatement(GET_ALL_STMT);
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			empMD.add(rsmd.getColumnName(i)); 
		}
		return empMD;
	}
	
	@Override
	public void closeConn() throws SQLException {
		// TODO Auto-generated method stub
		if (conn != null)
			conn.close();
	}
}
