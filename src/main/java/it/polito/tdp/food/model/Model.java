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
	private List<Adiacenza> adiacenze;
	
	// variabili per la ricorsione
	private List<String> camminoMax;
	private double pesoMax;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(Double calories) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		adiacenze = dao.getTipiEPeso();
		
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
	
	public void trovaCammino(String partenza, int N) {
		pesoMax = 0.0;
		camminoMax = null;
		
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		trovaRicorsivo(parziale, 0, N);
	}
	
	private void trovaRicorsivo(List<String> parziale, int livello, int N) {
		
		// caso terminale: controllo che sia la soluzione migliore
		if(livello == N) {
			double peso = calcolaPeso(parziale, N);
			if(peso > pesoMax) {
				this.pesoMax = peso;
				this.camminoMax = new ArrayList<>(parziale);
			}
			return;
		}
		
		// caso normale
		List<String> vicini = Graphs.neighborListOf(this.graph, parziale.get(livello));
		for(String v : vicini) {
			if(!parziale.contains(v)) {
				parziale.add(v);
				trovaRicorsivo(parziale, livello+1, N);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	private double calcolaPeso(List<String> parziale, int N) {
		double result = 0.0;
		
		for(int i = 0; i < N+1; i++) {
			result += this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1), parziale.get(i)));
		}
		
		return result;
	}

	public int getNumberVertex() {
		return this.graph.vertexSet().size();
	}
	
	public int getNumberEdge() {
		return this.graph.edgeSet().size();
	}
	
	public List<String> getTipiPorzione(Double calories) {
		return this.dao.getTipiPorzione(calories);
	}

	public List<Correlato> getVicini(String porzione) {
		List<String> vicini = Graphs.neighborListOf(this.graph, porzione);
		List<Correlato> result = new ArrayList<>();
		
		for(String s : vicini) {
			result.add(new Correlato(s, this.graph.getEdgeWeight(this.graph.getEdge(porzione, s))));
		}
		
		return result;
	}

	public List<String> getCamminoMax() {
		return camminoMax;
	}

	public double getPesoMax() {
		return pesoMax;
	}
	
	
	
}
