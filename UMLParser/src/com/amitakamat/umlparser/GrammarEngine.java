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

public class GrammarEngine {
	
	public static String generateGrammar(HashMap<String, ClassInterfaceInfo> classInterfaceInfo, ArrayList<String> classNames, ArrayList<String> interfaceNames){
		String grammar = "@startuml\n";
		grammar += "interface TestInterface {\n";
		grammar += "}\n";
		grammar += "class TestClass {\n";
		grammar += "}\n";
		grammar += "TestClass ..|> TestInterface\n";
		grammar += "@enduml";
		
		System.out.println(grammar);
		return grammar;
		//return "@startuml\nObject <|-- ArrayList\nObject : equals()\nArrayList : Object[] elementData\nArrayList : size()\n@enduml";
	}
}
