package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private List<Author> autori;
	
	private SimpleGraph<Author, DefaultEdge> grafo;
	
	private PortoDAO pDAO;
	
	public Model() {
	
		 pDAO = new PortoDAO() ;
	
		 autori = pDAO.getAllAutori();
	}
	
	public List<Author> getAllAutori() {
		
		return autori ;
	}
	
	public List<Author> findCoautori(Author value) {
		
		if(grafo==null)
			creaGrafo() ;

		return Graphs.neighborListOf(this.grafo, value) ; 
	}

	public void creaGrafo() {
		
		List <Author> coautori = new ArrayList<>();
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);

		Graphs.addAllVertices(this.grafo, autori);
		
		for(Author orig : autori) {
			
			coautori = pDAO.getCoautori(orig);
		
			for(Author dest : coautori) 
				
					grafo.addEdge(orig, dest);
		}	

			System.out.println("Archi: " + grafo.edgeSet().size());
	}

	public List<Paper> findSequenza(Author a1, Author a2) {
		
		List <Paper> articoliResult= new ArrayList<>();
		
		List<DefaultEdge> edges = new ArrayList<>();
	
		ShortestPathAlgorithm<Author, DefaultEdge> dfe = new DijkstraShortestPath<>(grafo);
	
		GraphPath<Author, DefaultEdge> gp = dfe.getPath(a1, a2);

		edges = gp.getEdgeList();

		System.out.println("Archi: " + edges.size());

		for (DefaultEdge arco : edges) {
			
			Author astart = grafo.getEdgeSource(arco) ;
			Author aterminal = grafo.getEdgeTarget(arco) ;

			articoliResult =pDAO.getArticolo(astart,aterminal) ;
		
		}
		
		return articoliResult;	
	}
}
