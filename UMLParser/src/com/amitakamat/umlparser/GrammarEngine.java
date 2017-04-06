/**
 * @author Amita Vasudev Kamat
 * 
 * CMPE 202 - Personal Project
 * 
 * Spring 2017
 *
 */

package com.amitakamat.umlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class GrammarEngine {
	
	public static String generateGrammar(HashMap<String, ClassInterfaceInfo> classInterfaceInfo, ArrayList<String> classNames, ArrayList<String> interfaceNames){
		String grammar = "@startuml\n";
		boolean blockStarted = false;
		
		System.out.println(classNames.toString());
		System.out.println("\n\n\n" + interfaceNames.toString());
		for(Entry<String, ClassInterfaceInfo> e: (Set<Entry<String, ClassInterfaceInfo>>)classInterfaceInfo.entrySet()){
			String name = e.getKey();
			blockStarted = false;
			
			if(interfaceNames.contains(name)){
				grammar += "interface " + name + " {\n";
				blockStarted = true;
			}
			
			if(classNames.contains(name)){
				grammar += "class " + name + " {\n";
				blockStarted = true;
				
				ArrayList<ClassInterfaceAttributeInfo> attributes = e.getValue().getAttributes();
				for(int i=0 ;i<attributes.size(); i++){
					ClassInterfaceAttributeInfo eachAttribute = attributes.get(i);
					if(eachAttribute.getAccessModifier() == "Private")
						grammar += "-";
					else
						grammar += "+";
					
					grammar += eachAttribute.getName() + " : " + eachAttribute.getDataType() + "\n";
				}
			}
			
			if(blockStarted){
				grammar += "}\n";
			}
			
		}
		
		grammar += "@enduml";
		
		System.out.println(grammar);
		return grammar;
		//return "@startuml\nObject <|-- ArrayList\nObject : equals()\nArrayList : Object[] elementData\nArrayList : size()\n@enduml";
	}
}
