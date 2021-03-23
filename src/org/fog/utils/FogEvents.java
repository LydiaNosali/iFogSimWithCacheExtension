package org.fog.utils;

public class FogEvents {
	private static final int BASE = 50;
	public static final int TUPLE_ARRIVAL = BASE + 1;
	public static final int LAUNCH_MODULE = BASE + 2;
	public static final int RELEASE_OPERATOR = BASE + 3;
	public static final int SENSOR_JOINED = BASE + 4;
	public static final int TUPLE_ACK = BASE + 5;
	public static final int APP_SUBMIT = BASE + 6;
	public static final int CALCULATE_INPUT_RATE = BASE + 7;
	public static final int CALCULATE_UTIL = BASE + 8;
	public static final int UPDATE_RESOURCE_USAGE = BASE + 9;
	//public static final int UPDATE_TUPLE_QUEUE = BASE + 10;
	public static final int TUPLE_FINISHED = BASE + 11;
	public static final int ACTIVE_APP_UPDATE = BASE+12;
	public static final int CONTROLLER_RESOURCE_MANAGE = BASE+13;
	public static final int ADAPTIVE_OPERATOR_REPLACEMENT = BASE+14;
	public static final int GET_RESOURCE_USAGE = BASE+15;
	public static final int RESOURCE_USAGE = BASE+16;
	public static final int CONTROL_MSG_ARRIVAL = BASE + 17;
	public static final int UPDATE_NORTH_TUPLE_QUEUE = BASE+18;
	public static final int UPDATE_SOUTH_TUPLE_QUEUE = BASE+19;
	public static final int ACTUATOR_JOINED = BASE+20;
	public static final int STOP_SIMULATION = BASE + 21;
	public static final int SEND_PERIODIC_TUPLE = BASE+22;
	public static final int LAUNCH_MODULE_INSTANCE = BASE+23;
	public static final int RESOURCE_MGMT = BASE+24;
	public static final int INITIALIZE_SENSOR = BASE+24;
	public static final int EMIT_TUPLE = BASE+25;
	
	public static final int TUPLE_STORAGE = BASE + 26;
	public static final int TUPLE_PROCESS = BASE + 27;
	
	//Evenements relatifs au cache : changements dans les evenements relatifs au fog device 
	//lorsque le cache est activé au niveau du fogdevice
	public static final int TUPLE_REQUEST = BASE + 28;
	public static final int CACHE_REMOVE = BASE + 29;
	public static final int CACHE_ADD = BASE + 30;
	//public static final int SEND_PERIODIC_TUPLE_REQUEST = BASE+29;
	public static final int INITIALIZE_DATA_RETRIEVE = BASE + 31;
	public static final int SEND_DATA_TO_CONSUMER = BASE + 32;
	public static final int DATA_RETREIVED = BASE + 33;
	public static final int STORE_DATA_CACHE = BASE + 34;

	
	
}
