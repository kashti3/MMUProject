package com.hit.memoryunits;


public class Page<T> implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Object pageId;
	private T content;
	
	public Page(Object id, T content){
		this.setPageId(id);
		this.content = content;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public Object getPageId() {
		return pageId;
	}

	public void setPageId(Object pageId) {
		this.pageId = pageId;
	}
	
	@Override
	public int hashCode(){
		long pid = (long)pageId; //unboxing
		return (int)pid;
	}
	
	@Override 
	public boolean equals(Object objId){
		if(this.hashCode() == objId.hashCode())
			return true;
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Page Id: " + this.pageId + "Content: " + this.content;
	}
	

	
}
