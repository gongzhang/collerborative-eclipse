package com.collaborativeeclipse.cct.tagdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;
import com.collaborativeeclipse.cct.internal.TagInternal;

public class TagDAOMemoryImpl implements ITagDAO {

	private static TagDAOMemoryImpl tagdao = null;
	public static TagDAOMemoryImpl getInstance(){
		if(tagdao == null)
			tagdao = new TagDAOMemoryImpl();
		return tagdao;
	}
	private TagDAOMemoryImpl(){
		
	}

	private HashMap<String,String> data = new HashMap<String,String>();
	
	@Override
	public void addTag(ITag tag) {
		final Object O = new Object();
		if(hasTag(tag))
			return;
		synchronized(O) {
			data.put(tag.getData(), tag.getTarget().getHandleIdentifier());
		}
	}
	//key和value的意义，反没反？？ （对于两个remove）
	
	@Override
	public List<ITag> getTagsByTarget(ITarget target) {
		List<ITag> res = new ArrayList<ITag>();
		for (String key : data.keySet()){
			if(data.get(key).equals(target.getHandleIdentifier())){
				TagInternal tag = new TagInternal();
				tag.setData(key);
				tag.setTarget(target);
				res.add(tag);
			}
		}
		return res;
	}

	@Override
	public void remove(ITag tag) {
		if(data.get(tag.getData()).equals(tag.getTarget().getHandleIdentifier()))
			this.data.remove(tag.getData());

	}

	@Override
	public void removeByTarget(ITarget target) {
		for (String key : data.keySet()){
			if(data.get(key).equals(target.getHandleIdentifier())){
				data.remove(key);
			}
		}

	}

	@Override
	public boolean hasTag(ITag tag) {
		return data.containsKey(tag.getData());
	}

}
