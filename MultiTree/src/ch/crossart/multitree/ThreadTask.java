package ch.crossart.multitree;

import java.util.ArrayList;
import java.util.LinkedList;

public class ThreadTask {

	public EventTaskEnum state;
	public String both;
	public String id;
	public String data;
	@SuppressWarnings("rawtypes")
	public TreeElement node;
	public boolean first;
	@SuppressWarnings("rawtypes")
	public ArrayList<TreeElement> nodes;
	public boolean single;
	public EventRunnable runnable;
	public LinkedList<ThreadTask> subTasks;
	public ArrayList<String> buffer;
	public String path;
	public int sequence;
	
	@SuppressWarnings("rawtypes")
	public ThreadTask(){
		this.state = null;
		this. node = null;
		this.both = null;
		this.id = null;
		this.data = null;
		this.single = false;
		this.runnable = null;
		this.subTasks = new LinkedList<ThreadTask>();
		this.nodes = new ArrayList<TreeElement>();
		this.buffer = new ArrayList<String>();
		this.path = null;
		this.first = false;
		this.sequence = 0;
	}
	
	public ThreadTask parseTree(String path){
		this.state = EventTaskEnum.PARSE;
		this.path = path;
		return this;
	}
	
	public ThreadTask buildTree(ArrayList<String> buffer, int sequence){
		this.state = EventTaskEnum.BUILD;
		this.buffer.addAll(buffer);
		this.sequence = sequence;
		return this;
	}
	
	public ThreadTask linkLevelZero(){
		this.state = EventTaskEnum.ZERO;
		this.single = true;
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public ThreadTask levelTree(TreeElement node){
		this.state = EventTaskEnum.LEVEL;
		this.node = node;
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public ThreadTask searchTree(String both, String id, String data, TreeElement node, boolean first){
		this.state = EventTaskEnum.SEARCH;
		this.node = node;
		this.nodes.add(node);
		this.both = both;
		this.id = id;
		this.data = data;
		this.first = first;
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public ThreadTask searchTrees(String both, String id, String data, ArrayList<TreeElement> nodes, boolean first){
		this.state = EventTaskEnum.SEARCH;
		this.single = true;
		this.nodes = nodes;
		this.both = both;
		this.id = id;
		this.data = data;
		this.first = first;
		return this;
	}
	
	public ThreadTask runSingle(EventRunnable runnable){
		this.state = EventTaskEnum.SINGLE;
		this.single = true;
		this.runnable = runnable;
		return this;
	}
	
	public ThreadTask runSingleSub(){
		this.state = EventTaskEnum.SINGLE;
		this.single = true;
		return this;
	}
	
	public ThreadTask createSubTask(ThreadTask subTask){
		subTasks.add(subTask);
		return this;
	}
	
	public ThreadTask isSingle(){
		this.single = true;
		return this;
	}
	
	public enum EventTaskEnum {
		LEVEL, SEARCH, SINGLE, PARSE, BUILD, ANALYSE, ZERO
	}
	
	public interface EventRunnable {
		void startTask();
	}
}
