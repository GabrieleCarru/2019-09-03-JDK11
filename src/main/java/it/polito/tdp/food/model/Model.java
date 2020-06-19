package it.polito.tdp.food.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge> graph;
	private List<Adiacenza> adiacenze;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(Double calories) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		adiacenze = dao.getTipiEPeso(calories);
		
		Graphs.addAllVertices(this.graph, dao.getTipiPorzione(calories));
		
		for(Adiacenza a : adiacenze) {
//			if(!graph.containsVertex(a.getT1())) {
//				graph.addVertex(a.getT1());
//			}
//			
//			if(!graph.containsVertex(a.getT2())) {
//				graph.addVertex(a.getT2());
//			}
			
			if(graph.vertexSet().contains(a.getT1()) && graph.vertexSet().contains(a.getT2())) {
				Graphs.addEdge(this.graph, a.getT1(), a.getT2(), a.getPeso());
			}
		}
	}
	
	public int getNumberVertex() {
		return this.graph.vertexSet().size();
	}
	
	public int getNumberEdge() {
		return this.graph.edgeSet().size();
	}
	
}
