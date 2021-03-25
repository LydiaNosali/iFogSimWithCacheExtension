package org.fog.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.power.PowerDatacenterBroker;
import org.fog.application.AppEdge;
import org.fog.application.Application;
import org.fog.cplex.DataAllocation;
import org.fog.gui.lpFileConstuction.BasisDelayMatrix;
import org.fog.placement.ModuleMapping;
import org.fog.utils.FogEvents;

public class FogBroker extends PowerDatacenterBroker{
	public static Map<String, List<Integer>> cachePlacementMap = new HashMap<String, List<Integer>>();

	public FogBroker(String name) throws Exception {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startEntity() {
		// TODO Auto-generated method stub
		System.out.println(getName()+" is starting...");
		
		initializeTupleRetrieve();
	}

	private void initializeTupleRetrieve() {
		// TODO Auto-generated method stub
		
		for(AppEdge edge : Application.edges) {
			for(String consumer : edge.getDestination() ) {
				if(consumer.startsWith("DISP"))
					continue;
				
				String nodename = ModuleMapping.getDeviceHostModule(consumer);
				
				int nodeId = ModuleMapping.getFogDevIdByName(nodename);
				send(nodeId, 300, FogEvents.INITIALIZE_DATA_RETRIEVE, edge);
				Log.writeInLogFile(this.getName(), "send to consumer "+consumer+" located in FogDev "+nodename+" INITIALIZE_DATA_RETRIEVE");
			}
			
		}
		
	}

	@Override
	public void processEvent(SimEvent ev) {
		// TODO Auto-generated method stub
		//System.out.println("ForBroker' processEvent!");
		//fogdevice (consommateur) send event 
		//creer hashmap<iddonne, liste des emplacements des cache>
		//On reçoit un event 'cette donnée c'est moi qui l'est' on enregistre ça dans la liste des emplacement
		
		switch(ev.getTag()){
		//evenement de consommation 
			case FogEvents.TUPLE_REQUEST:
				processTupleRequest(ev);
				break;
			default:
				break;
		}
		
	}

	private void processTupleRequest(SimEvent ev) {
		// TODO Auto-generated method stub
		////*System.out.println("Tuple Storage:"+ev.toString());
		//Log.writeInLogFile(this.getName(), "Tuple Request:"+ev.toString());
		String tupleType = (String) ev.getData();
		
		int consumerNode = ev.getSource();
		
		if(DataAllocation.dataPlacementCacheMap.containsKey(tupleType)){
			List<Integer> listcache = DataAllocation.dataPlacementCacheMap.get(tupleType);
			
			float min = Float.MAX_VALUE;
			int cachemin = -1;
			for(int cache : listcache) {
				float latency = BasisDelayMatrix.getFatestLink(consumerNode, cache);
				if(latency < min) {
					min = latency;
					cachemin = cache;
				}
			}
			
			Object [] data = new Object [2];
			data[0] = consumerNode;
			data[1] = tupleType;
			
			send(cachemin, min, FogEvents.SEND_DATA_TO_CONSUMER, data);
			
		}else
		
		{
			int storageNode = DataAllocation.dataPlacementMap.get(tupleType);
			float latency = BasisDelayMatrix.getFatestLink(consumerNode, storageNode);
			Object [] data = new Object [2];
			data[0] = consumerNode;
			data[1] = tupleType;
			
			send(storageNode, latency, FogEvents.SEND_DATA_TO_CONSUMER, data);
		}
		
	}
	
	public void processCacheAdd(int sourceEdge, String tupleTyp) {
		// TODO Auto-generated method stub
		//Log.writeInLogFile(this.getName(), "Cache Add:"+tupleTyp);
		int src = sourceEdge;
		String tupletype = tupleTyp;
		List<Integer> cacheList;
		if (this.cachePlacementMap.containsKey(tupletype)) {
			cacheList = this.cachePlacementMap.get(tupletype);
			if (!cacheList.contains(src)) {
				cacheList.add(src);
			}
		}
		else {
			cacheList= new ArrayList<Integer>();
			cacheList.add(src);
			this.cachePlacementMap.put(tupletype, cacheList);
		}
		//Log.writeInLogFile(this.getName(), "CacheList ="+cachePlacementMap.get(tupletype));
		
	}
	
	public void processCacheRemove(int sourceEdge, String tupleTyp) {
		// TODO Auto-generated method stub
		//Log.writeInLogFile(this.getName(), "Cache Remove:"+tupleTyp);
		//System.out.println("processCacheRemove");
		int src = sourceEdge;
		String tupletype = tupleTyp;
		List<Integer> cacheList;
		if (!this.cachePlacementMap.containsKey(tupletype)) {
			//Log.writeInLogFile(this.getName(), "Erreur donnée non existante dans le cache ="+cachePlacementMap.get(tupletype));
			System.out.println("Erreur donnée non existante dans le cache ");
			System.exit(0);
		}
		cacheList = this.cachePlacementMap.get(tupletype);
		if (!cacheList.contains(src)) {
			//Log.writeInLogFile(this.getName(), "Erreur src non existant dans la liste de cache ="+cachePlacementMap.get(tupletype));
			System.exit(0);
		}
		//System.out.println("src "+src);
		cacheList.remove(new Integer(src));
		//Log.writeInLogFile(this.getName(), "CacheList ="+cachePlacementMap.get(tupletype));
		
	}
	
	@Override
	public void shutdownEntity() {
		// TODO Auto-generated method stub
		
	}

}
