package models;

import java.lang.Thread.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import Dao.DBConnect;

public class UserModel extends DBConnect{
	private int cid;
	private int tid;
	private String note;
	
	DBConnect  conn = null;
	java.sql.Statement stmt = null;
	
	public UserModel() {
		conn = new DBConnect();
	}
	
	/* getter and setter*/

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
	
	public void insertRecord(int cid, String note1) {

		try {
			setCid(cid);
			// Execute a query
			System.out.println("Inserting record into the table...");
			stmt = conn.getConnection().createStatement();
			String sql = null;

			// Include data to the database table

			sql = " insert into fengli_notes(cid, notes) values('" + cid + "', '" + note1 + "')";

			stmt.executeUpdate(sql);

			System.out.println("Notes inserted" + note1 + " for CliendtID " + cid);

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	public List<UserModel> getNotes(int cid){
		List<UserModel> notes = new ArrayList<>();
		String query = "SELECT tid, notes FROM fengli_notes WHERE cid = ?;";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, cid);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserModel notes1 = new UserModel();
				// grab record data by table field name into UserModel account object
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
	}
	

