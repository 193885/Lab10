package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> getAllAutori() {
		
		List <Author> autori= new ArrayList<>();
	
			final String sql =  "SELECT * FROM author";

			try {
				
				Connection conn = DBConnect.getConnection() ;	
				PreparedStatement st = conn.prepareStatement(sql) ;
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					
					autori.add(new Author(res.getInt("id"), res.getString("lastname"), res.getString("firstname"))) ;
				}
				
				conn.close();
				return autori ;
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				throw new RuntimeException("Errore Db");
	}
		}

	public List<Author> getCoautori(Author a) {
		
		List <Author> coautori= new ArrayList<>();
		
		final String sql =	"select * from author where author.id in (select distinct c1.authorid " + 
							"from creator as c1,creator as c " + 
							"where c1.eprintid=c.eprintid " + 
							"and c.authorid =? " + 
							"and c1.authorid<> c.authorid)" ;
			
		try {
			
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, a.getId());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				coautori.add(new Author(res.getInt("id"), res.getString("lastname"), res.getString("firstname"))) ;
			}
			
			conn.close();
			return coautori ;
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
}
	}

	public List<Paper> getArticolo(Author astart, Author aterminal) {
		
		List <Paper> articoli= new ArrayList<>();
		
		final String sql =	"select * " + 
							"from paper,creator as c, creator as c1 " + 
							"where paper.eprintid=  c.eprintid " + 
							"and c.eprintid = c1.eprintid " + 
							"and c.authorid = ? " + 
							"and c1.authorid = ? ";
						
			
		try {
			
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, astart.getId());
			st.setInt(2, aterminal.getId());
			
			ResultSet rs = st.executeQuery() ;
			
			Paper p = null;
			
			while(rs.next()) {
				
				p = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
							 rs.getString("publication"), rs.getString("type"), rs.getString("types")) ;
				
				articoli.add(p);
			}
			
			conn.close();
			return articoli ;
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}