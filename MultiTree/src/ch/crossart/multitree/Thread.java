package ch.crossart.multitree;


public class Thread {
	
	private boolean finished;
	private boolean running;
	private boolean single;
	private int time;

	private boolean search;
	private boolean parse;
	
	private ParsingTool parseTool;
	private SearchTool searchTool;
	private TreeTool treeTool;
	
	
	public Thread(){
		time = 100;
		this.finished = false;
		this.single = false;
		this.search = false;
		this.parse = false;
		//this.first = old;
	}
	
	@Override
	public void run() {
		System.out.println((System.currentTimeMillis() - History.time) + ": Thread started!");
		if(single){
			waitForSingleTask();
		}else{
			waitForTask();
		}	
	}
	
	public boolean isRunning(){
		return running;
	}
	
	@SuppressWarnings("rawtypes")
	public void waitForSingleTask(){
		while(!finished){
			try {
				synchronized(this){
					//System.out.println(Thread.currentThread().getName());
					wait(time);
					EventTask task = History.events.startTask(this);
													
					if(task != null){
						System.out.println((System.currentTimeMillis() - History.time) + ": Task started!");
						if(!task.subTasks.isEmpty()){
							History.events.addSubTaskList(task.subTasks);
						}
												
						switch(task.state){
						case SEARCH:
							History.events.resetNodes();
							search = true;
							try {
								if(task.nodes.isEmpty()){
									History.events.addSubTask(new EventTask().searchTree(task.both, task.id, task.data, DataHandler.Tree, task.first));
								}else{
									for(TreeElement node: task.nodes){
										History.events.addSubTask(new EventTask().searchTree(task.both, task.id, task.data, node, task.first));
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case LEVEL:
							treeTool = new TreeTool();
							try {
								treeTool.levelTree(task.node);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case SINGLE:
							if(task.runnable != null){
								runSingle(task.runnable);
							}				
							break;
						case BUILD:
							break;
						case PARSE:
							parseTool = new ParseTool();
							parse = true;
							try {
								parseTool.parseFile(task.path);
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						case ZERO:
							try {
								parseTool = new ParseTool();
								parseTool.linkLevelZero();
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						default:
							break;
						}
					}		
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(parse && History.events.recording){
			History.events.firstRecording = false;
		}
		
		if(search && !History.events.recording){
			History.search.searchFinished();
		}
		if(parse && !History.events.recording){
			Display.getDefault().asyncExec(new Runnable() {
			    public void run() {
			    	History.populateMenu();
			    }
			});	
		}
		System.out.println((System.currentTimeMillis() - History.time) + ": Single thread shutting down!");
	}
	
	
	public synchronized void waitForTask(){
		while(!finished){
			try {
					EventTask task = History.events.startTask(this);
					//wait(1);
					if(task == null){
						//System.out.println(Thread.currentThread().getName() + " Time:"+time);
						running = false;
						wait(time);			
						time = time +100;
					}else{
						time = 100;
						switch(task.state){
						case SEARCH:
							searchTool = new SearchTool();
							try {
								running = true;
								searchTool.searchTree(task.both, task.id, task.data, task.node, task.first);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case LEVEL:
							treeTool = new TreeTool();
							try {
								running = true;
								treeTool.levelTree(task.node);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case SINGLE:			
							break;
						case BUILD:
							parseTool = new ParseTool();
							running = true;
							parseTool.buildTree(task.buffer, task.sequence);
							break;
						case PARSE:
							break;
						case ZERO:
							break;
						default:
							break;
						}
					}		
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println((System.currentTimeMillis() - History.time) + ": " + Thread.currentThread().getName() + " shutting down!");
	}
	
	public void setFinished(){
		finished = true;
	}
		
	public EventThread setSingle(boolean single){
		this.single = single;
		return this;
	}
	
	public boolean isSingle(){
		return single;
	}
	
	public void runSingle(EventRunnable runnable){
		runnable.startTask();
		System.out.println((System.currentTimeMillis() - History.time) + ": Task finished!");
	}
}
