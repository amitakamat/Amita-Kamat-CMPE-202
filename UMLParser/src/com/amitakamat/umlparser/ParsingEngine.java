/**
 * @author Amita Vasudev Kamat
 * 
 * CMPE 202 - Personal Project
 * 
 * Spring 2017
 *
 */

package com.amitakamat.umlparser;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;

import net.sourceforge.plantuml.SourceStringReader;

public class ParsingEngine {

	public static void parseCode(String fileContent){
		CompilationUnit compileUnit = JavaParser.parse(fileContent);
		NodeList<TypeDeclaration<?>> types = compileUnit.getTypes();
		
		ArrayList<String> classNames = getClassNames(types);
		System.out.println(classNames.toString());
		ArrayList<String> interfaceNames = getInterfaceNames(types);
		ArrayList<String> classInterfaceNames = new ArrayList<String>();
		for (String className : classNames){
			classInterfaceNames.add(new String(className));
		}
		for (String interfaceName : interfaceNames){
			classInterfaceNames.add(new String(interfaceName));
		}
		//classInterfaceNames.addAll(interfaceNames);
		
		HashMap<String, ClassInterfaceInfo> ClassInterfaceDetails = getClassOrInterfaceInfo(types, classInterfaceNames);
		//System.out.println("\n Types are : " + types.size());
		
		System.out.println("\n Class Names : " + classInterfaceNames.toString());
		System.out.println("\n Class Details: ");
		for(int i=0;i<classInterfaceNames.size(); i++){
			System.out.println(ClassInterfaceDetails.get(classInterfaceNames.get(i)));
		}
		
		String outputFile = "/home/amita/GitHub 202/Amita-Kamat-CMPE-202/Output-Diagrams/OutputClassDiagram1.png";
		String grammar = GrammarEngine.generateGrammar(ClassInterfaceDetails, classNames, interfaceNames);
		//String grammar = "@startuml\nObject <|-- ArrayList\nObject : equals()\nArrayList : Object[] elementData\nArrayList : size()\n@enduml";
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
			ClassInterfaceInfo classOrInterfaceInfo = new ClassInterfaceInfo(attributeInfo, methodsInfo, getImplementsInfo(node), getExtendsInfo(node), getUsesInfo(attributeInfo, methodsInfo, classOrInterfaceNames));
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
	
	private static ArrayList<String> getUsesInfo(ArrayList<ClassInterfaceAttributeInfo> attributeInfo, ArrayList<ClassInterfaceMethodInfo> methodInfo, ArrayList<String> classInterfaceNames){
		ArrayList<String> usesInfo = new ArrayList<String>();
		if(attributeInfo.size() > 0) {
			for(ClassInterfaceAttributeInfo eachAttribute : attributeInfo){
				String type = eachAttribute.getDataType();
				if(classInterfaceNames.contains(type)){
					if(!usesInfo.contains(type)){
						usesInfo.add(type);
					}
				}
			}
		}
		
		if(methodInfo.size() > 0) {
			for(ClassInterfaceMethodInfo eachMethod : methodInfo){
				ArrayList<ArrayList<String>> parameters = eachMethod.getParameters();
				String returnType = eachMethod.getReturnType();
				if(classInterfaceNames.contains(returnType)){
					if(!usesInfo.contains(returnType)){
						usesInfo.add(returnType);
					}
				}
				
				for(ArrayList<String> eachParameter: parameters){
					String type = eachParameter.get(1);
					if(classInterfaceNames.contains(type)){
						if(!usesInfo.contains(type)){
							usesInfo.add(type);
						}
					}
				}
			}
		}
		
		return usesInfo;
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
		List<BodyDeclaration<?>> body = node.getMembers();
		for(BodyDeclaration<?> eachMember: body){
			if(eachMember instanceof ConstructorDeclaration)
			{
				//String name = node.getNameAsString();
				ConstructorDeclaration constructor = (ConstructorDeclaration) eachMember;
				ArrayList<ArrayList<String>> parameters = new ArrayList<ArrayList<String>>();
				for( int i=0; i < constructor.getParameters().size(); i++){
					//System.out.println(method.getParameters());
					Parameter parameter = constructor.getParameter(i);
					ArrayList<String> eachParameter = new ArrayList<String>();
					eachParameter.add(parameter.getNameAsString());						
					eachParameter.add(parameter.getType().toString());
					parameters.add(eachParameter);
				}
				
				methodList.add(new ClassInterfaceMethodInfo(constructor.getNameAsString(),
						parameters,
						"void"));
			}
			
		}
		
		
		if(methods.size() != 0){
			for(MethodDeclaration method : methods){
				boolean isGetterOrSetterMethod = false;
				if(method.isPublic()){
					String name = method.getNameAsString();
					for(int j=0;j<attributeInfo.size(); j++){
						String accessModifier = attributeInfo.get(j).getAccessModifier();
						
						// With a previous getter or setter method the attribute would be set to public.
						// Hence, we need to compare public too.
						if(accessModifier == "Private" || accessModifier == "Public"){
							String attributeName = attributeInfo.get(j).getName();
							String getter = "get" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
							System.out.println(getter);
							String setter = "set" + attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
							System.out.println(setter);
							
							if(name.equals(getter) || name.equals(setter)){
								attributeInfo.get(j).setAccessModifier("Public");
								isGetterOrSetterMethod = true;
							}
						}
					}
					
					if(!isGetterOrSetterMethod)
					{
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
		}
		
		return methodList;
	}
	
}
