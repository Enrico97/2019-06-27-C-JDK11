package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	EventsDao dao = new EventsDao();
	Graph<String, DefaultWeightedEdge> grafo;
	
	public List<String> listaReati() {
		return dao.listaReati();
	}
	
	public List<Integer> listaGiorni() {
		return dao.listaGiorni();
	}
	
	public Graph<String, DefaultWeightedEdge> creaGrafo(String categoria, int giorno) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.vertici(categoria, giorno));
		for(Adiacenza a : dao.archi(categoria, giorno)) {
			Graphs.addEdge(grafo, a.getS1(), a.getS2(), a.getPeso());
		}
		return grafo;
	}
	
	public List<Adiacenza> stampare(String categoria, int giorno) {
		List<Adiacenza> stampare = new ArrayList<>();
		int max=0;
		int min=1000000;
		double mediano=0;
		for(DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)>max) {
				max=(int) grafo.getEdgeWeight(e);
			}
			if(grafo.getEdgeWeight(e)<min) {
				min=(int) grafo.getEdgeWeight(e);
			}
		}
		mediano=(max+min)/2;
		for(Adiacenza a : dao.archi(categoria, giorno)) {
			if(a.getPeso()<mediano)
				stampare.add(a);
		}
		return stampare;
	}
	
}
