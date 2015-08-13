package ch.crossart.multitree;

public class SearchTool {

	private Root root;
	
	
	public SearchTool(){
		
	}
	
	public SearchTool(Root root){
		this.root = root;
	}
	
	public SearchTool setRoot(Root root){
		this.root = root;
		return this;
	}
}
