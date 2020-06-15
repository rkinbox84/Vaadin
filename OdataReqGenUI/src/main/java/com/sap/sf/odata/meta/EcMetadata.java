package com.sap.sf.odata.meta;

import java.util.ArrayList;
import java.util.List;

public class EcMetadata {
	
	List<EntityMetadata> lstEntityMetadata;
	
	public EcMetadata(){
		lstEntityMetadata = new ArrayList<EntityMetadata>();
	}

	public List<EntityMetadata> getLstEntityMetadata() {
		return lstEntityMetadata;
	}

	public void setLstEntityMetadata(List<EntityMetadata> lstEntityMetadata) {
		if (lstEntityMetadata != null && lstEntityMetadata.size() >0)
			this.lstEntityMetadata = lstEntityMetadata;
	}
	
	public void addEntityMetadata(EntityMetadata entityMetadata){
		this.lstEntityMetadata.add(entityMetadata);
	}
	
	public void removeEntityMetadata(EntityMetadata entityMetadata){
		this.lstEntityMetadata.remove(entityMetadata);
	}
	
	
}
