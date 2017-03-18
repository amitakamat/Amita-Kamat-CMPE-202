package com.amitakamat.umlparser;

import java.util.ArrayList;

public class ClassInterfaceInfo {
	private ArrayList<ClassInterfaceAttributeInfo> attributes;
	private ArrayList<ClassInterfaceMethodInfo> methods;
	private ArrayList<String> implementsInfo;
	private ArrayList<String> extendsInfo;
	
	public ArrayList<ClassInterfaceAttributeInfo> getAttributes(){
		return this.attributes;
	}
	
	public ArrayList<ClassInterfaceMethodInfo> getMethods(){
		return this.methods;
	}
	
	public ArrayList<String> getImplemensInfo(){
		return this.implementsInfo;
	}
	
	public ArrayList<String> getExtendsInfo(){
		return this.extendsInfo;
	}
	
	public ClassInterfaceInfo(ArrayList<ClassInterfaceAttributeInfo> attributes, ArrayList<ClassInterfaceMethodInfo> methods,
							ArrayList<String> implementsInfo, ArrayList<String> extendsInfo) {
		this.attributes = attributes;
		this.methods = methods;
		this.implementsInfo = implementsInfo;
		this.extendsInfo = extendsInfo;
	}
}
