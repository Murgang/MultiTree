package ch.crossart.multitree;

import java.util.ArrayList;
import java.util.LinkedList;

public class ThreadManager {
	private LinkedList<EventTask> tasks;
	private LinkedList<EventTask> singleTasks;
	private ArrayList<EventThread> threads;
	private EventThread singleThread;
	private int numberThreads;
	public int nodes;
	public boolean recording;
	public boolean firstRecording;


	
	public ThreadManager(int numberThreads){
		this.numberThreads = numberThreads;
		tasks = new LinkedList<EventTask>();
		singleTasks = new LinkedList<EventTask>();
		recording = false;
		firstRecording = true;
		
		threads = new ArrayList<EventThread>();
		
		singleThread = new EventThread().setSingle(true);
		for(int i = 0; i < numberThreads; i++){
			threads.add(new EventThread());
		}
		
	}
	
	public synchronized void resetNodes(){
		nodes = 0;
	}
	
	public synchronized void addNode(){
		nodes++;
	}
	
	public synchronized void addSubTask(EventTask task){
		tasks.add(task);
		for(EventThread e : threads){
			if(!e.isAlive()){
				e.start();
			}
		}
	//	System.out.println(tasks.size());
	}
	
	public synchronized void addSubTaskList(LinkedList<EventTask> tasks){
		System.out.println((System.currentTimeMillis() - History.time) + ": Tasks added!");
		this.tasks.addAll(tasks);
	}
	
	public synchronized EventTask startTask(EventThread current){		
	
		if(current.isSingle()){
			
			if(singleTasks.isEmpty() && tasks.isEmpty()){
				for(EventThread e : threads){
					if(e.isRunning()){
						return null;
					}
				}
				for(EventThread e : threads){
					e.setFinished();
				}
				current.setFinished();
				//System.out.println(searchResults.size() + " counted: " + counter + " nodes!");
				
				return null;
			}else if(tasks.isEmpty()){
				
				if(!singleTasks.peek().subTasks.isEmpty()){
					for(EventThread e : threads){
						if(!e.isAlive()){
							e.start();
						}
					}
				}
				
				for(EventThread e : threads){
					if(e.isRunning()){
						return null;
					}
				}
					
				return singleTasks.pop();
			}else{
				return null;
			}	
			
		}else{
			if(tasks.isEmpty()){
				return null;			
			}else{
				return tasks.pop();
			}
		}
			
	}
	
	@SuppressWarnings("rawtypes")
	public synchronized void addSearchResult(TreeElement node){
		DataHandler.searchResults.add(node);
	}
	
	public synchronized void addTask(EventTask task){
		singleTasks.add(task);
		System.out.println((System.currentTimeMillis() - History.time) + ": Task " + task.state + " added!");
		if(!singleThread.isAlive()){
			threads.clear();
			for(int i = 0; i < numberThreads; i++){
				threads.add(new EventThread());
			}
			singleThread = new EventThread().setSingle(true);
			
			singleThread.start();
		}
	}
	
	public synchronized LinkedList<EventTask> getTasks() {
		return tasks;
	}
}
