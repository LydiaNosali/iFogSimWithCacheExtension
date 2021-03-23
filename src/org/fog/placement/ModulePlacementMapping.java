package org.fog.placement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.entities.FogDevice;

public class ModulePlacementMapping extends ModulePlacement{

	private ModuleMapping moduleMapping;
	
	
	public ModulePlacementMapping(List<FogDevice> fogDevices, Application application, ModuleMapping moduleMapping){
		this.setFogDevices(fogDevices);
		this.setApplication(application);
		this.setModuleMapping(moduleMapping);
		this.setModuleToDeviceMap(new HashMap<String, List<Integer>>());
		this.setDeviceToModuleMap(new HashMap<Integer, List<AppModule>>());
		this.setModuleInstanceCountMap(new HashMap<Integer, Map<String, Integer>>());
		for(FogDevice device : getFogDevices())
			getModuleInstanceCountMap().put(device.getId(), new HashMap<String, Integer>());
		mapModules();
	}
	
	@Override
	protected void mapModules() {
		Map<String, Map<String, Integer>> mapping = moduleMapping.getModuleMapping();
		//System.out.println("mapping"+mapping);
		for(String deviceName : mapping.keySet()){
			FogDevice device = getDeviceByName(deviceName);
			for(String moduleName : mapping.get(deviceName).keySet()){
				//System.out.println("moduleName "+ moduleName);
				AppModule module = getApplication().getModuleByName(moduleName);
				createModuleInstanceOnDevice(module, device);
				getModuleInstanceCountMap().get(device.getId()).put(moduleName, mapping.get(deviceName).get(moduleName));
			}
		}
	}

	
	public ModuleMapping getModuleMapping() {
		return moduleMapping;
	}
	public void setModuleMapping(ModuleMapping moduleMapping) {
		this.moduleMapping = moduleMapping;
	}

	
}