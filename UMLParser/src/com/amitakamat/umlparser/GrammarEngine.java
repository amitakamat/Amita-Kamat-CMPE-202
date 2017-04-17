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

public class GrammarEngine {
	
	public static String generateGrammar(HashMap<String, ClassInterfaceInfo> classInterfaceInfo, ArrayList<String> classNames, ArrayList<String> interfaceNames){
		String grammar = Constants.startGrammar + "\n";
		boolean blockStarted = false;
		
		System.out.println(classNames.toString());
		System.out.println("\n\n\n" + interfaceNames.toString());
		for(Entry<String, ClassInterfaceInfo> e: classInterfaceInfo.entrySet()){
			String name = e.getKey();
			blockStarted = false;
			
			if(interfaceNames.contains(name)){
				grammar += Constants.interfaceLabel + name + " {\n";
				blockStarted = true;
			}
			
			if(classNames.contains(name)){
				grammar += Constants.classLabel + name + " {\n";
				blockStarted = true;
				
				ArrayList<ClassInterfaceAttributeInfo> attributes = e.getValue().getAttributes();
				for(int i=0 ;i<attributes.size(); i++){
					ClassInterfaceAttributeInfo eachAttribute = attributes.get(i);
					
					//Since we consider only private and public attributes.
					if(eachAttribute.getAccessModifier() == "Private")
						grammar += Constants.privateAccess;
					else
						grammar += Constants.publicAccess;
					
					grammar += eachAttribute.getName() + " : " + eachAttribute.getDataType() + "\n";
				}
			}
			
			ArrayList<ClassInterfaceMethodInfo> methods = e.getValue().getMethods();
			for(int i=0; i<methods.size(); i++)
			{
				ClassInterfaceMethodInfo eachMethod = methods.get(i);
				grammar += Constants.publicAccess + " " + eachMethod.getName() + "(";
				
				for(int j=0; j<eachMethod.getParameters().size(); j++){
					ArrayList<String> eachParameter = eachMethod.getParameters().get(j);
					grammar += eachParameter.get(0) + ":" + eachParameter.get(1);
					if(j != eachMethod.getParameters().size()-1){
						grammar += ",";
					}
				}
				grammar += "):" + eachMethod.getReturnType() + "\n";
			}
			
			if(blockStarted){
				grammar += "}\n";
			}
		}
			
		for(Entry<String, ClassInterfaceInfo> e: classInterfaceInfo.entrySet()){
			String name = e.getKey();			
			ArrayList<String> implementsInfo = e.getValue().getImplemensInfo();
			if(implementsInfo.size() != 0){
				for(int i=0; i<implementsInfo.size(); i++){
					if(classNames.contains(name)){
						grammar += implementsInfo.get(i) + Constants.implementsSign + name + "\n";
					}
				}
			}
			
			ArrayList<String> extendsInfo = e.getValue().getExtendsInfo();
			if(extendsInfo.size() != 0){
				for(int i=0; i<extendsInfo.size(); i++){
					if(classNames.contains(name)){
						grammar += extendsInfo.get(i) + Constants.extendsSign + name + "\n";
					}
				}
			}
			
			ArrayList<String> usesInfo = e.getValue().getUsesInfo();
			if(usesInfo.size() != 0){
				for(int i=0; i<usesInfo.size(); i++){
					if(classNames.contains(name)){
						grammar += usesInfo.get(i) + Constants.usesSign + name + " : < uses \n";
					}
				}
			}
			
			ArrayList<ClassInterfaceAttributeInfo> attributes = e.getValue().getAttributes();
			for(int i=0 ;i<attributes.size(); i++){
				ClassInterfaceAttributeInfo eachAttribute = attributes.get(i);
				
				if(eachAttribute.getOneToOne() != "")
					//grammar += String.format("%s -- %s\n", name, eachAttribute.getOneToOne());
					grammar += name + " -- " +  eachAttribute.getOneToOne() + "\n";
				
				if(eachAttribute.getOneToMany() != "")
					//grammar += String.format("%s --\"*\" %s\n", name, eachAttribute.getOneToMany());
					grammar += name + " --\"*\" " +  eachAttribute.getOneToMany() + "\n";
			}
		}
		
		grammar += Constants.endGrammar;
		
		System.out.println(grammar);
		return grammar;
		//return "@startuml\nObject <|-- ArrayList\nObject : equals()\nArrayList : Object[] elementData\nArrayList : size()\n@enduml";
	}
}
