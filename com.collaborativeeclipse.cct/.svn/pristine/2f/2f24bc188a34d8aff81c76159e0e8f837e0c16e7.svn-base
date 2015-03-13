package com.collaborativeeclipse.cct.tagdata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


import com.collaborativeeclipse.cct.ITarget;
import com.collaborativeeclipse.cct.internal.TargetInternal;

public class TargetDAOMemoryImpl implements ITargetDAO {
	
	private static TargetDAOMemoryImpl targetdao = null;
	public static TargetDAOMemoryImpl getInstance(){
		if(targetdao == null)
			targetdao = new TargetDAOMemoryImpl();
		return targetdao;
	}
	private TargetDAOMemoryImpl(){
		
	}
	
	//private List<String> data = new ArrayList<String>();
	private Collection<String> data = new HashSet<String>();
		
	@Override
	public void addTarget(ITarget target) {
		data.add(target.getHandleIdentifier());
	}

	@Override
	public void removeByIndentifier(String handleIdentifier) {
		data.remove(handleIdentifier);
	}

	public boolean hasTarget(String handleIdentifier){
		return data.contains(handleIdentifier);
	}
}
