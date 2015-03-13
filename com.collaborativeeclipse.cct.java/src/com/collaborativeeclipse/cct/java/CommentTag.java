package com.collaborativeeclipse.cct.java;

import com.collaborativeeclipse.cct.ITag;

public class CommentTag extends Tag{

	private Comment comment;
	
	public CommentTag(){
		this(null);
	}
	public CommentTag(ITag tag){
		comment = tag == null?
				new Comment()
				:new Comment(tag.getData());
	}
	
	@Override
	public String getData() {
		return comment.toString();
	}
	
	public String getComment(){
		return comment.getComment();
	}
	public String getKind(){
		return comment.getKind();
	}
	public void setComment(String comment){
		this.comment.setComment(comment);
	}
	public void setKind(String kind){
		this.comment.setKind(kind);
	}
}

class Comment{
	static String DEM = "_:_";
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	private String comment;
	private String kind;
	
	public Comment(String data){
		comment = data.split(DEM)[0];
		kind = data.split(DEM)[1];
	}
	public Comment(){
		comment = "";
		kind ="";
	}
	
	public String toString(){
		return comment+DEM+kind;
	}
}