package bank.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import bank.JWT.JwtManager;
import bank.dao.BranchDAOImpl;
import bank.model.Branch;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureException;

@WebServlet("/get_branches")
public class ViewBranchesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	private Gson gson = new Gson();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authTokenHeader = request.getHeader("Authorization");
		if(authTokenHeader != null && !authTokenHeader.isEmpty()) {
			try {
				Jws<Claims> parsedToken = JwtManager.parseToken(authTokenHeader);
				System.out.println(parsedToken);
				BranchDAOImpl branchImpl = new BranchDAOImpl();
				List<Branch> branches = branchImpl.getAllBranches();
				ArrayList<String> output = new ArrayList<String>();
				for(Branch branch:branches) {
					// convert branch objects to JSON format
					output.add(this.gson.toJson(branch));
				}
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				out.print(output);
				out.flush();
			}catch (Exception e){
				e.printStackTrace();
				response.getWriter().append("Invalid Token, Please login");
			}
			
		}else {
			response.getWriter().append("No Token provided, Please login!!");
		}
		
		
	}


}
