/**
 * @author Amita Vasudev Kamat
 * 
 * CMPE 202 - Personal Project
 * 
 * Spring 2017
 *
 */

package com.amitakamat.umlparser;

import java.io.*;
import java.util.ArrayList;

public class UmlParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Check for input arguments
		//if(args.length == 0)
			//System.out.println("Input Invalid. Please provide source code folder path.");

		//else if(args.length > 2)
			//System.out.println("Input Invalid. Too many input arguments.");
		
		//else
		//{
			try{
				//String sourceFolder = args[0];
				String sourceFolder = "/home/amita/GitHub 202/Amita-Kamat-CMPE-202/Test-Cases/UmlParser-1/";
				ArrayList<String> sourceCodeFiles = getJavaSourceFiles(sourceFolder);
				if(sourceCodeFiles.size() == 0)
				{
					System.out.println("No java files found in the source folder.");
				}
				else
				{
					System.out.println("Folder found");
					readSourceCode(sourceCodeFiles, sourceFolder);
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		//}
		
	}
	
	
	/**
	 * Method to retrieve all the java files from the source folder
	 * @param sourceFolder
	 * @return List of the names of java files in the source folder
	 */
	private static ArrayList<String> getJavaSourceFiles(String sourceFolder){
		ArrayList<String> javaFiles = new ArrayList<String>();
		
		try
		{
			//Create a log file to record all the operations carried out.
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("../LogFile.txt")));
			
			writer.write("Logs\n\n");
			writer.write("--------Extracting java files from source Folder-----------------\n");
			
			//Create directory to store source files if it does not already exist
			//if(!newDirectory.exists())
			//	newDirectory.mkdirs();
		
			File sourceDirectory = new File(sourceFolder);
			
			//Check if source folder provided in input exists t the given path
			if(sourceDirectory.isDirectory())
			{
		
			  for (File file : sourceDirectory.listFiles()) 
			  {
				  if (file.getName().endsWith((".java"))) 
				  {
			    	writer.write(file.getName() + " extracted \n");
			    	javaFiles.add(file.getName());
				  }
			  }
			}
			else
			{
				writer.write("Error : Source Directory not found.\n\n");
				System.out.println("Source Directory not found. Please ensure the path is correct and try again.");
			}
			
			writer.close();
		}
		catch(Exception e)
		{
		  System.out.println(e.getMessage());
		}
		return javaFiles;

	}

	/**
	 * Method to read the source files 
	 * @param javaSourceFiles list of java source files
	 * @param sourceFolderPath path of directory which contains the source files
	 */
	private static void readSourceCode(ArrayList<String> javaSourceFiles, String sourceFolderPath){
		StringBuilder fileContents = new StringBuilder();
		for(int i=0; i<javaSourceFiles.size(); i++){
			try
			{
				InputStream inputStream = new FileInputStream(sourceFolderPath + "/" + javaSourceFiles.get(i));
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
				String eachLineContent = bufferReader.readLine();
				
				while(eachLineContent!=null)
				{
					fileContents.append(eachLineContent + "\n");
					eachLineContent = bufferReader.readLine();
				}
				
				bufferReader.close();
				
				//System.out.println("\n Contents of file " + javaSourceFiles.get(i) + " :\n\n");
				//System.out.println(fileContents.toString());

			}
			catch(Exception e)
			{
				System.out.println("\n" + e.getMessage());
			}
		}
		String parseContent = fileContents.toString().replace("import", "//import");
		ParsingEngine.parseCode(parseContent);
	}
}
