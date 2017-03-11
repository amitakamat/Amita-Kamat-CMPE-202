package com.amitakamat.umlparser;

import java.util.ArrayList;

public class ClassInterfaceInfo {
	private ArrayList<ClassInterfaceAttributeInfo> attributes;
	private ArrayList<ClassInterfaceMethodInfo> methods;
	private ArrayList<String> implementsInfo;
	private ArrayList<String> extendsInfo;
	
	public ClassInterfaceInfo(ArrayList<ClassInterfaceAttributeInfo> attributes, ArrayList<ClassInterfaceMethodInfo> methods,
							ArrayList<String> implementsInfo, ArrayList<String> extendsInfo) {
		this.attributes = attributes;
		this.methods = methods;
		this.implementsInfo = implementsInfo;
		this.extendsInfo = extendsInfo;
	}
}
