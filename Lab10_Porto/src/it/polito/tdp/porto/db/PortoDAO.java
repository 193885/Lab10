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
	
			final String sql = "SELECT * FROM author";

			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);

				ResultSet rs = st.executeQuery();

				while (rs.next()) {

					Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
					
					autori.add(autore);

				}

				return autori;
				
			} catch (SQLException e) {
				// e.printStackTrace();
				throw new RuntimeException("Errore Db");
			}
		}

	public List<Author> getCoautori(Author a) {
		
		List <Author> coautori= new ArrayList<>();
		
		final String sql = "select * from author where author.id in (select distinct c1.authorid " + 
							"from creator as c1,creator as c " + 
							"where c1.eprintid=c.eprintid " + 
							"and c.authorid =? " + 
							"and c1.authorid<> c.authorid) ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, a.getId());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Author c = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				
				coautori.add(c);

			}

			return coautori;
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}