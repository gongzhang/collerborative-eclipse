package com.collaborativeeclipse.cct.java;

import com.collaborativeeclipse.cct.ITag;

public class RelationTag extends Tag {

	private Relation relation;
	
	public RelationTag(){
		this(null);
	}
	public RelationTag(ITag tag){
		relation = tag == null?
				new Relation()
				:new Relation(tag.getData());
	}
	
	@Override
	public String getData() {
		return relation.toString();
	}
	
	public String getRelativeTarget(){
		return relation.getRelativeTarget();
	}
	public String getKind(){
		return relation.getKind();
	}
	public void setRelativeTarget(String target){
		this.relation.setRelativeTarget(target);
	}
	public void setKind(String kind){
		this.relation.setKind(kind);
	}
}

class Relation{
	static String DEM = "_:_";
	public String getRelativeTarget() {
		return target;
	}
	public void setRelativeTarget(String target) {
		this.target = target;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	private String target;
	private String kind;
	
	public Relation(String data){
		target = data.split(DEM)[0];
		kind = data.split(DEM)[1];
	}
	public Relation(){
		target = "";
		kind ="";
	}
	
	public String toString(){
		return target+DEM+kind;
	}
}