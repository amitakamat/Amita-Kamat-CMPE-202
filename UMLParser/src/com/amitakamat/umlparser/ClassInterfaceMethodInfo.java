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

/**
 * Class to store the information of the methods of classes and interfaces
 */
public class ClassInterfaceMethodInfo {
	private String name; 
	private ArrayList<ArrayList<String>> parameters;
	private String returnType;
	
	/*
	 * get set properties to get name of the method
	 */
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	/*
	 * get set properties to get parameters of the method
	 */
	public ArrayList<ArrayList<String>> getParameters(){
		return this.parameters;
	}
	
	
	public void setParameters(ArrayList<ArrayList<String>> parameters){
		this.parameters = parameters;
	}
	
	/*
	 * get set properties to get return type of the method
	 */
	public String getReturnType(){
		return this.returnType;
	}
	
	public void setReturnType(String returnType){
		this.returnType = returnType;
	}
	
	public ClassInterfaceMethodInfo(String name, ArrayList<ArrayList<String>> parameters, String returnType){
		this.name = name;
		this.parameters = parameters;
		this.returnType = returnType;
	}
}
