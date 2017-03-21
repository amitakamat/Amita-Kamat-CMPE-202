package com.amitakamat.umlparser;
/**
 * 
 */

/**

 * @author Amita Vasudev Kamat
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import net.sourceforge.plantuml.*;
import net.sourceforge.plantuml.SourceStringReader;
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
				String sourceFolder = "UmlParser-1/src";
				ArrayList<String> sourceCodeFiles = getJavaSourceFiles(sourceFolder);
				if(sourceCodeFiles.size() == 0)
				{
					System.out.println("No java files found in the source folder.");
				}
				else
				{
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
		parseCode(fileContents.toString());
	}
	
	private static void parseCode(String fileContent){
		CompilationUnit compileUnit = JavaParser.parse(fileContent);
		NodeList<TypeDeclaration<?>> types = compileUnit.getTypes();
		
		ArrayList<String> classNames = getClassNames(types);
		ArrayList<String> interfaceNames = getInterfaceNames(types);
		ArrayList<String> classInterfaceNames = classNames;
		classInterfaceNames.addAll(interfaceNames);
		
		HashMap<String, ClassInterfaceInfo> ClassInterfaceDetails = getClassOrInterfaceInfo(types, classInterfaceNames);
		//System.out.println("\n Types are : " + types.size());
		
		System.out.println("\n Class Names : " + classInterfaceNames.toString());
		System.out.println("\n Class Details: ");
		for(int i=0;i<classInterfaceNames.size(); i++){
			System.out.println(ClassInterfaceDetails.get(classInterfaceNames.get(i)));
		}
		
		String outputFile = "Output-Diagrams/OutputClassDiagram.png";
		String grammar = "@startuml\nObject <|-- ArrayList\nObject : equals()\nArrayList : Object[] elementData\nArrayList : size()\n@enduml";
		try{
			SourceStringReader grammarReader = new SourceStringReader(grammar);
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			grammarReader.generateImage(outputStream);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	private static ArrayList<String> getClassNames(NodeList<TypeDeclaration<?>> types){
		ArrayList<String> classNames = new ArrayList<String>();
		for(int i=0; i< types.size(); i++){
			TypeDeclaration<?> node = types.get(i);
			if(node instanceof ClassOrInterfaceDeclaration && !((ClassOrInterfaceDeclaration) node).isInterface()){
				classNames.add(node.getNameAsString());
			}
		}
		
		return classNames;
	}
	
	private static ArrayList<String> getInterfaceNames(NodeList<TypeDeclaration<?>> types){
		ArrayList<String> interfaceNames = new ArrayList<String>();
		for(int i=0; i< types.size(); i++){
			TypeDeclaration<?> node = types.get(i);
			if(types.get(i) instanceof ClassOrInterfaceDeclaration && ((ClassOrInterfaceDeclaration) types.get(i)).isInterface()){
				interfaceNames.add(node.getNameAsString());
			}
		}
		
		return interfaceNames;
	}
	
	private static HashMap<String, ClassInterfaceInfo> getClassOrInterfaceInfo(NodeList<TypeDeclaration<?>> types, ArrayList<String> classOrInterfaceNames){
		HashMap<String, ClassInterfaceInfo> sourceClassesOrInterfaces = new HashMap<>();
		
		for(int i=0; i< types.size(); i++){
			TypeDeclaration<?> node = types.get(i);
			ArrayList<ClassInterfaceAttributeInfo> attributeInfo = getAttributes(node, classOrInterfaceNames);
			ArrayList<ClassInterfaceMethodInfo> methodsInfo = getMethodsDetails(node, attributeInfo);
			ClassInterfaceInfo classOrInterfaceInfo = new ClassInterfaceInfo(attributeInfo, methodsInfo, getImplementsInfo(node), getExtendsInfo(node));
			sourceClassesOrInterfaces.put(node.getNameAsString(), classOrInterfaceInfo);
		}
	
		return sourceClassesOrInterfaces;
	}
	
	private static ArrayList<String> getImplementsInfo(TypeDeclaration<?> node){
		ArrayList<String> implementsInfo = new ArrayList<String>();
		if(node instanceof ClassOrInterfaceDeclaration){
			NodeList<ClassOrInterfaceType> implementsList = ((ClassOrInterfaceDeclaration) node).getImplementedTypes();
			for(ClassOrInterfaceType implement : implementsList){
				implementsInfo.add(implement.getNameAsString());
			}
		}
		
		return implementsInfo;
	}
	
	private static ArrayList<String> getExtendsInfo(TypeDeclaration<?> node){
		ArrayList<String> extendsInfo = new ArrayList<String>();
		if(node instanceof ClassOrInterfaceDeclaration){
			NodeList<ClassOrInterfaceType> extendsList = ((ClassOrInterfaceDeclaration) node).getExtendedTypes();
			for(ClassOrInterfaceType extend : extendsList){
				extendsInfo.add(extend.getNameAsString());
			}
		}
		
		return extendsInfo;
	}
	
	private static ArrayList<ClassInterfaceAttributeInfo> getAttributes(TypeDeclaration<?> node, ArrayList<String> classOrInterfaceNames){
		List<FieldDeclaration> members = node.getFields();
		String accessModifier = null;
		ArrayList<ClassInterfaceAttributeInfo> attributes = new ArrayList<ClassInterfaceAttributeInfo>();
		if(members.size() != 0){
			for(BodyDeclaration<?> member : members){
				FieldDeclaration field = (FieldDeclaration) member;
				String oneToOne = "";
				String oneToMany = "";
				boolean isPrivateOrPublic = false;
				
				if(field.isPrivate()){
					accessModifier = "Private";
					isPrivateOrPublic = true;
				}
				
				else if(field.isPublic()) {
					accessModifier = "Public";
					isPrivateOrPublic = true;
				}
				
				if(isPrivateOrPublic) {
					Type dataType = field.getVariables().get(0).getType();
					String dataTypeStrValue = dataType.toString();
					
					if(classOrInterfaceNames.contains(dataTypeStrValue)){
						oneToOne = dataTypeStrValue;
					}
					else{
						if(dataType instanceof ReferenceType){
							ReferenceType<?> refType = (ReferenceType<?>)dataType;
							if(refType instanceof  ClassOrInterfaceType)
							{
								Optional<NodeList<Type>> typeArguments = ((ClassOrInterfaceType) refType).getTypeArguments();
								if(typeArguments.isPresent()){
									NodeList<Type> arguments = ((ClassOrInterfaceType) refType).getTypeArguments().get();
									if(arguments.size() != 0)
									{
										String relatedToClass = arguments.get(0).toString();
										if(classOrInterfaceNames.contains(relatedToClass)){
											oneToMany = relatedToClass;
										}
									}
								}
							}
						}
					}
				
				
					attributes.add(new ClassInterfaceAttributeInfo(field.getVariables().get(0).getNameAsString(), 
							accessModifier, dataType.toString(), oneToOne, oneToMany));
								
				}
			}
		}
		return attributes;
	}
	
	private static ArrayList<ClassInterfaceMethodInfo> getMethodsDetails(TypeDeclaration<?> node, ArrayList<ClassInterfaceAttributeInfo> attributeInfo){
		ArrayList<ClassInterfaceMethodInfo> methodList = new ArrayList<ClassInterfaceMethodInfo>();
		List<MethodDeclaration> methods = node.getMethods();
		if(methods.size() != 0){
			for(MethodDeclaration method : methods){
				if(method.isPublic()){
					for(int j=0;j<attributeInfo.size(); j++){
						if(attributeInfo.get(j).getAccessModifier() == "Private"){
							String attributeName = attributeInfo.get(j).getName();
							String getter = "get" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
							System.out.println(getter);
							String setter = "set" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
							System.out.println(setter);
						}
					}
					
					ArrayList<ArrayList<String>> parameters = new ArrayList<ArrayList<String>>();
					for( int i=0; i < method.getParameters().size(); i++){
						//System.out.println(method.getParameters());
						Parameter parameter = method.getParameter(i);
						ArrayList<String> eachParameter = new ArrayList<String>();
						eachParameter.add(parameter.getNameAsString());
						eachParameter.add(parameter.getType().toString());
						parameters.add(eachParameter);
					}
					
					methodList.add(new ClassInterfaceMethodInfo(method.getNameAsString(),
							parameters,
							method.getType().toString()));
				}
			}
		}
		
		return methodList;
	}
}
