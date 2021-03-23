package org.fog.entities;

//Java program to implement LRU cache
//using LinkedHashSet
import java.util.*;

import org.fog.examples.DataPlacement;
import org.fog.placement.ModuleMapping;
import org.fog.entities.*;
import org.cloudbus.cloudsim.Log;
import org.fog.utils.Logger;

public class FogCache {

	Set<String> cache;
	private long capacity;//quantité de donnée en MO, GO
	private int size;//Nombre de données dans le cache ex : 10
	private String type;//SSD, HDD, NVME
	public int nodeId = -1;
	public Map<String, Tuple > cachedata= new HashMap<String, Tuple >();
	
	public FogCache(int size, double d, String type)
	{
		this.cache = new LinkedHashSet<String>(size);
		this.size = size;

	}

	/* Refers key x with in the LRU cache */
	public void retreive(String tupleType, int nodeId)
	{	 
		//System.out.println(ModuleMapping.getFogDevNameById(nodeId)+"Retriving Tuple...");
		//Log.writeInLogFile(ModuleMapping.getFogDevNameById(nodeId),"Retriving Tuple...");
		if (this.cachedata.containsKey(tupleType)==true) {//donnée dans cache
			//System.out.println("data in cache");
			Tuple tuple = this.cachedata.get(tupleType); //send to consommateur with events
			if (cache.contains(tupleType) == false)
			{
				System.out.println("erreur");
			}
			//change order of data
			cache.remove(tupleType);
			DataPlacement.fogBroker.processCacheRemove(nodeId, tupleType);
			cache.add(tupleType);
			DataPlacement.fogBroker.processCacheAdd(nodeId, tupleType);
			
		}
		else {//data not in cache
			System.out.println("donnée non existante");
		}
		
	}

	public void store(Tuple tuple, int nodeId)
	{	 
		//Log.writeInLogFile(ModuleMapping.getFogDevNameById(nodeId),"cache storing");
		if (this.cachedata.containsKey(tuple.getActualTupleId())==true) {//donnée dans cache
			System.out.println("donnée existante");
			cache.remove(tuple.getActualTupleId());
			DataPlacement.fogBroker.processCacheRemove(nodeId, tuple.getTupleType());
		}
		else {//data not in cache
			this.cachedata.put(tuple.getTupleType(), tuple);
		}
		//change order of data
		
		cache.add(tuple.getTupleType());
		DataPlacement.fogBroker.processCacheAdd(nodeId, tuple.getTupleType());

	}
	
	// displays contents of cache in Reverse Order
	public void display()
	{
	LinkedList<String> list = new LinkedList<>(cache);
	
	// The descendingIterator() method of java.util.LinkedList
	// class is used to return an iterator over the elements
	// in this LinkedList in reverse sequential order
	Iterator<String> itr = list.descendingIterator(); 
	
	while (itr.hasNext())
			System.out.print(itr.next() + " ");
	}
	
	public void put(String key)
	{
		
	if (cache.size() == size) {
			String firstKey = cache.iterator().next();
			cache.remove(firstKey);
			DataPlacement.fogBroker.processCacheRemove(this.nodeId, this.type);

		}

		cache.add(key);
		DataPlacement.fogBroker.processCacheAdd(this.nodeId, this.type);
		

		
	}
	
}
