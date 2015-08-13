package ch.crossart.multitree;

public class TreeElement {
	
	private final int id;
	
	private Object key;
	private Object data;
	
	private int level;
	
	private TreeElement parent;
	private TreeElement child;
	private TreeElement back;
	private TreeElement next;
	
	public TreeElement(int id){
		this.id = id;
		this.key = null;
		this.data = null;
		
		this.level = -1;
		
		this.parent = null;
		this.child = null;
		this.back = null;
		this.next = null;
	}
	
	public TreeElement(){
		this(0);
	}
	
	public int getLevel() {
		return level;
	}
	
	public TreeElement setLevel(int level){
		this.level = level;
		return this;
	}

	public TreeElement getParent() {
		return parent;
	}

	public TreeElement getChild() {
		return child;
	}

	public TreeElement getBack() {
		return back;
	}

	public TreeElement getNext() {
		return next;
	}

	public TreeElement setParent(TreeElement parent) {
		this.parent = parent;
		return this;
	}

	public TreeElement setChild(TreeElement child) {
		this.child = child;
		return this;
	}

	public TreeElement setBack(TreeElement back) {
		this.back = back;
		return this;
	}

	public TreeElement setNext(TreeElement next) {
		this.next = next;
		return this;
	}
	
	public Object getKey(){
		return key;
	}
	
	public Object getData(){
		return data;
	}
	
	public int getId(){
		return id;
	}
}
