package ch.crossart.multitree;

public class Root {

	private TreeElement root;

	public Root(){
		this(new TreeElement());
	}
	
	public Root(TreeElement root){
		this.root = root;
	}
	
	public TreeElement getRoot(){
		return root;
	}
	
}
