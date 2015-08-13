package ch.crossart.multitree;

import java.util.ArrayList;

public class AnalysingTool {

	   //string strreg = @"[a-zA-Z!-/:-@[-`{-~]+";
       //string intreg = @"(?<!([.][0-9]*))([0-9]+)(?!([0-9]*[.]))";
      // string dblreg = @"-?[0-9]*\.[0-9]+";
       
	public static ArrayList<Tool> toolBuilder(boolean BooleanTool, boolean wholeNumberTool, boolean floatNumberTool, boolean charTool){
		ArrayList<Tool> tools = new ArrayList<Tool>();
		if(BooleanTool){
			tools.add(new BooleanTool());
		}else if(wholeNumberTool){
			tools.add(new wholeNumberTool());
		}else if(floatNumberTool){
			tools.add(new floatNumberTool());
		}else if(charTool){
			tools.add(new charTool());
		}
		return tools;
	}
	
	public static AnalysingResult stringParser(ArrayList<Tool> tools, String input, AnalysingResult result){
		input.trim();	
		if(!tools.isEmpty()){
			for(Tool t : tools){
				if(t.parseInput(input, result)){
					break;
				}		
			}	
		}else{
			result.stringResult = input;
			result.type = String.class;
		}	
		return result; 
	}
	
	private static class BooleanTool implements Tool{

		@Override
		public boolean parseInput(String input, AnalysingResult result) {
			if(input.matches("true")){			
				result.boolResult = true;
				result.type = boolean.class;
				return true;
			}else if(input.matches("false")){
				result.boolResult = false;
				result.type = boolean.class;
				return true;
			}
			return false;
		}
		
	}
	
	private static class wholeNumberTool implements Tool{

		@Override
		public boolean parseInput(String input, AnalysingResult result) {
			if(input.matches("^\\d*$")){
				
			}
			
			return false;
		}
		
	}
	
	private static class floatNumberTool implements Tool{

		@Override
		public boolean parseInput(String input, AnalysingResult result) {
			// TODO Auto-generated method stub
			
			return false;
		}
		
	}
	
	private static class charTool implements Tool{

		@Override
		public boolean parseInput(String input, AnalysingResult result) {
			// TODO Auto-generated method stub
			
			return false;
		}
		
	}

	private interface Tool{
		public boolean parseInput(String input, AnalysingResult result);
	}
}
