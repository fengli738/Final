package models;

import java.net.ConnectException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;

import Dao.DBConnect;

public class AdminModel extends DBConnect{
	private int cid;
	private int tid;
	private String note;
	
	DBConnect conn = null;
	java.sql.Statement stmt = null;
	
	public AdminModel() {
		conn = new DBConnect();
	}
	
	/*Getter and Setter*/
	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public List<AdminModel> getNotes(){
		List<AdminModel> notes = new ArrayList<>();
		String query = "SELECT cid, tid, notes FROM fengli_notes;";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				AdminModel notes1 = new AdminModel();
				// grab record data by table field name into AdminModel account object
				notes1.setCid(resultSet.getInt("cid"));
				notes1.setTid(resultSet.getInt("tid"));
				notes1.setNote(resultSet.getString("notes"));
				notes.add(notes1); // add account data to arraylist	
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error fetching Accounts: " + e);
		}
		return notes;
	}
	public void deleteNote(int tid) {
		String sql = null;
		// delete data from the table
		sql = "DELETE FROM fengli_notes WHERE tid = ?;";
		
		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, tid);
			int rowCount = statement.executeUpdate();
			System.out.println("delete success " + rowCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateNote(int tid, String note) {
		String sql = "UPDATE fengli_notes SET notes = ? WHERE tid = ?;";
		
		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, note);
			statement.setInt(2, tid);
			int rowCount = statement.executeUpdate();
			System.out.println("update succsess " + rowCount);
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
}
