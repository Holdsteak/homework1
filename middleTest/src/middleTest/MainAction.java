package middleTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainAction {
	static String[] data_inFile;
	static IEmpDAO dao = new EmpDAO();

	public static void main(String[] args) {
		inFile();
		outFile();
	}

	private static void outFile() {
		try {
			dao.getConnection();
			List<EmpBean> emps = dao.getAll();
			List<String> label = dao.rsMetaData();

			FileWriter fos = new FileWriter("res/school.json");
			BufferedWriter bw = new BufferedWriter(fos);

			String jsons = "";

			for (EmpBean emp : emps) {
				System.out.print(emp.getSeq() + ", ");
				System.out.print(emp.getDateListed() + ", ");
				System.out.print(emp.getYear() + ", ");
				System.out.print(emp.getCategory() + ", ");
				System.out.print(emp.getDuration() + ", ");
				System.out.print(emp.getSchoolPopulation() + ", ");
				System.out.print(emp.getTransferPopulation() + ", ");
				System.out.print(emp.getTransferRates() + "\n");

				String json = "{" + "\"" + label.get(0) + "\"" + ":" + "\"" + emp.getSeq() + "\"" + "," 
								  + "\"" + label.get(1) + "\"" + ":" + "\"" + emp.getDateListed() + "\"" + "," 
								  + "\"" + label.get(2) + "\"" + ":" + "\"" + emp.getYear() + "\"" + "," 
								  + "\"" + label.get(3) + "\"" + ":" + "\"" + emp.getCategory()+ "\"" + "," 
								  + "\"" + label.get(4) + "\"" + ":" + "\"" + emp.getDuration() + "\"" + "," 
								  + "\"" + label.get(5) + "\"" + ":" + "\"" + emp.getSchoolPopulation() + "\"" + "," 
								  + "\"" + label.get(6) + "\"" + ":" + "\"" + emp.getTransferPopulation() + "\"" + "," 
								  + "\"" + label.get(7) + "\"" + ":" + "\"" + emp.getTransferRates() + "\"" 
							+ "},";
				
				jsons += json;
			}

			jsons = "[" + jsons.substring(0, jsons.length() - 1) + "]";
			System.out.println(jsons);

			bw.write(jsons);

			bw.close();
			fos.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dao.closeConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void insertSql() {
		try {
			dao.getConnection();
			EmpBean emp1 = new EmpBean();
			emp1.setSeq(Integer.valueOf(data_inFile[0]));
			emp1.setYear(data_inFile[1]);
			emp1.setDateListed(data_inFile[2]);
			emp1.setCategory(data_inFile[3]);
			emp1.setDuration(data_inFile[4]);
			emp1.setSchoolPopulation(Double.valueOf(data_inFile[5]));
			emp1.setTransferPopulation(Double.valueOf(data_inFile[6]));
			emp1.setTransferRates(Double.valueOf(data_inFile[7]));
			int count1 = dao.insert(emp1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dao.closeConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void inFile() {
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader("res/school.csv");
			br = new BufferedReader(fr);
			String data;

			while ((data = br.readLine()) != null) {
				data_inFile = data.trim().replaceAll("\"", "").split(",");

				insertSql();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}