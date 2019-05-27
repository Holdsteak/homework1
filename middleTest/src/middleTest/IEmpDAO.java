package middleTest;

import java.sql.SQLException;
import java.util.List;

public interface IEmpDAO {
	public void getConnection() throws SQLException;
	public int insert(EmpBean emp) throws SQLException;
	public List<String> rsMetaData() throws SQLException;
	public List<EmpBean> getAll() throws SQLException;
	public void closeConn() throws SQLException;
}
