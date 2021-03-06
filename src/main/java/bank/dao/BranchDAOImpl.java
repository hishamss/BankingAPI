package bank.dao;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import bank.model.Branch;
import bank.model.User;
import bank.utilities.*;

public class BranchDAOImpl implements BranchDAO {

	PreparedStatement stmt = null; // to help protect against SQL injection

	@Override
	public List<Branch> getAllBranches() {
		// TODO Auto-generated method stub
		List<Branch> branches = new ArrayList<>();

		try (Connection connection = DAOUtilities.getConnection()) {

			String sql = "select * from branches";
			stmt = connection.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				Branch branch = new Branch();
				branch.setBranchName(result.getString("branchname"));
				branch.setBranchCity(result.getString("branchcity"));
				branches.add(branch);
			}
			result.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return the list of Book objects populated by the DB.
		return branches;

	}

	@Override
	public List<User> getAllUsersByBranch(int branchID) {
		List<User> users = new ArrayList<>();
		try (Connection connection = DAOUtilities.getConnection()) {
			String sql = "select * from users where branchid = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, branchID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("userid"));
				user.setBranchID(rs.getInt("branchid"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setPassword("NA");
				user.setDOB(rs.getDate("dob").toLocalDate());
				user.setRole(rs.getString("role"));
				users.add(user);
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// return the list of users.
		return users;
	}

	@Override
	public boolean addBranch(Branch branch) {
		// TODO Auto-generated method stub
		try (Connection connection = DAOUtilities.getConnection()) {
			String sql = "insert into branches (BranchName, BranchCity) values (? , ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, branch.getBranchName());
			stmt.setString(2, branch.getBranchCity());
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

}
