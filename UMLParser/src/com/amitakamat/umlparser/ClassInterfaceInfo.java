package com.amitakamat.umlparser;

import java.util.ArrayList;

/**
 * 
 * @author Amita Vasudev Kamat
 * Class to store all the information of a class or an interface
 *
 */
public class ClassInterfaceInfo {
	private ArrayList<ClassInterfaceAttributeInfo> attributes;
	private ArrayList<ClassInterfaceMethodInfo> methods;
	private ArrayList<String> implementsInfo;
	private ArrayList<String> extendsInfo;
	private ArrayList<String> usesInfo;
	
	/*
	 * Gets the attributes of a class or interface
	 */
	public ArrayList<ClassInterfaceAttributeInfo> getAttributes(){
		return this.attributes;
	}
	
	/*
	 * Gets the methods of a class or an interface
	 */
	public ArrayList<ClassInterfaceMethodInfo> getMethods(){
		return this.methods;
	}
	
	/*
	 * Gets the implements interface information of a class
	 */
	public ArrayList<String> getImplemensInfo(){
		return this.implementsInfo;
	}
	
	/*
	 * Gets the extends class information of a class
	 */
	public ArrayList<String> getExtendsInfo(){
		return this.extendsInfo;
	}
	
	public ArrayList<String> getUsesInfo(){
		return this.usesInfo;
	}
	
	/*
	 * Parameterized constructor for ClassInterfaceInfo class 
	 */
	public ClassInterfaceInfo(ArrayList<ClassInterfaceAttributeInfo> attributes, ArrayList<ClassInterfaceMethodInfo> methods,
			ArrayList<String> implementsInfo, ArrayList<String> extendsInfo, ArrayList<String> usesInfo) {
		this.attributes = attributes;
		this.methods = methods;
		this.implementsInfo = implementsInfo;
		this.extendsInfo = extendsInfo;
		this.usesInfo = usesInfo;
}
}
