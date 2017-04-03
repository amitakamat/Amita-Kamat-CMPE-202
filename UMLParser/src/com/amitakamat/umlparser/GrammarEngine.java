package com.amitakamat.umlparser;

public class GrammarEngine {
	
	public static String generateGrammar(){
		return "@startuml\nObject <|-- ArrayList\nObject : equals()\nArrayList : Object[] elementData\nArrayList : size()\n@enduml";
	}
}
