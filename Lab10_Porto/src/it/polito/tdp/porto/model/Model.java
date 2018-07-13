package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private PortoDAO pDAO;
	private List<Author> autori;
	
	private SimpleGraph<Author, DefaultEdge> grafo;
	
	

	public Model() {
	
		pDAO = new PortoDAO();
		autori = new ArrayList<>();
		autori = pDAO.getAllAutori();
	}



	public List<Author> getAllAutori() {
	
		return autori;
	}



	public void creaGrafo() {
		
		List <Author> coautori =  new ArrayList<>();
		
		grafo = new SimpleGraph<>(DefaultEdge.class);

		Graphs.addAllVertices(grafo, autori);
		
		for(Author orig : autori) {
			
			coautori = pDAO.getCoautori(orig);

			
			for(Author dest : coautori) 
		
			
			grafo.addEdge(orig, dest);
			
		}	
			
			System.out.println(grafo.vertexSet().size());
			System.out.println("Archi: " + grafo.edgeSet().size());
	}

}
