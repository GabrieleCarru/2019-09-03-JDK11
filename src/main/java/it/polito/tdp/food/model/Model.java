package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge> graph;
	private List<String> tipiPorzione;
	
	// Variabili ricorsive
	private List<String> camminoMax;
	private Integer pesoMax;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(Double calories) {
		graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		tipiPorzione = dao.getAllTipiPorzione(calories);
		
		// Aggiungo i vertici 
		Graphs.addAllVertices(this.graph, tipiPorzione);
		
		// Aggiungo gli archi
		List<InfoArco> archi = dao.getAllArchi();
		for(InfoArco arco : archi) {
			if(graph.vertexSet().contains(arco.getP1()) && graph.vertexSet().contains(arco.getP2()) 
						&& !graph.edgeSet().contains(graph.getEdge(arco.getP1(), arco.getP2()))) {
				Graphs.addEdge(this.graph, arco.getP1(), arco.getP2(), arco.getPeso());
			}
		}
	}
	
	public List<InfoArco> getVicini(String partenza) {
		
		List<InfoArco> result = new ArrayList<>();
		
		for(String s : Graphs.neighborListOf(this.graph, partenza)) {
			result.add(new InfoArco(partenza, s, 
					(int) this.graph.getEdgeWeight(this.graph.getEdge(partenza, s))));
		}
		
		return result;
	}
	
	public void trovaCammino(String partenza, int N) {
		pesoMax = 0;
		camminoMax = null;
		
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		ricorsivo(parziale, 0, N);
	}
	
	public void ricorsivo(List<String> parziale, int livello, int N) {
		
		// Caso terminale:
		if(livello == N) {
			if(getPesoCammino(parziale) > pesoMax) {
				pesoMax = getPesoCammino(parziale);
				camminoMax = new ArrayList<>(parziale);
			}
			return;
		}
		
		// Caso normale:
		List<String> vicini = Graphs.neighborListOf(this.graph, parziale.get(livello));
		for(String s : vicini) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				ricorsivo(parziale, livello + 1, N);
				parziale.remove(s);
			}
		}
	}
	
	private Integer getPesoCammino(List<String> parziale) {
		
		int peso = 0;
		
		for(int i = 1; i < parziale.size(); i++) {
			peso += this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i), parziale.get(i-1)));
		}
		
		return peso;
	}

	public List<String> getAllTipiPorzione(Double calories) {
		return dao.getAllTipiPorzione(calories);
	}
	
	public int getNumberVertex() {
		return this.graph.vertexSet().size();
	}
	
	public int getNumberEdges() {
		return this.graph.edgeSet().size();
	}

	public List<String> getCamminoMax() {
		return camminoMax;
	}

	public Integer getPesoMax() {
		return pesoMax;
	}
	
	
	
}
