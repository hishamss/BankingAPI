package bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bank.model.User;
import bank.utilities.DAOUtilities;

public class UserDAOImpl implements UserDAO{
	
	Connection connection = null;	// Intialize the connection with null
	PreparedStatement stmt = null;	// to help protect against SQL injection

	@Override
	public List<User> getAllusers() {
		List<User> users = new ArrayList<User>();
		try {
			connection = DAOUtilities.getConnection();
			String sql = "select * from users";
			stmt = connection.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();
			
			while(result.next()) {
				User user = new User();
				user.setBranchID(result.getInt("branchid"));
				user.setFirstName(result.getString("firstname"));
				user.setLastName(result.getString("lastname"));
				user.setEmail(result.getString("email"));
				user.setPassword(result.getString("pass"));
				user.setDOB(result.getDate("dob").toLocalDate());
				
				users.add(user);
			}
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			closeResources();
		}
		
		return users;
	}

	@Override
	public boolean addUser(User add) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(User delete) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}