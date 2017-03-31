package com.amitakamat.umlparser;

/*
 * Class to store the information of the atrributes of classes and interfaces
 */
public class ClassInterfaceAttributeInfo {
		private String name; 
		private String accessModifier;
		private String dataType;
		private String oneToOneRelationship;
		private String oneToManyRelationship;
		
		/*
		 * get set properties to get name of the attribute
		 */
		
		public String getName(){
			return this.name;
		}
		
		public void setName(String name){
			this.name = name;
		}
		
		/*
		 * get set properties to get access modifier of the attribute
		 */
		public String getAccessModifier(){
			return this.accessModifier;
		}
		
		public void setAccessModifier(String accessModifier){
			this.accessModifier = accessModifier;
		}
		
		
		/*
		 * get set properties to get data type of the attribute
		 */
		
		public String getDataType(){
			return this.dataType;
		}
		
		public void setDataType(String dataType){
			this.dataType = dataType;
		}
		
		/*
		 * get set properties to get one to one relationships of the attribute
		 */
		
		public String getOneToOne(){
			return this.oneToOneRelationship;
		}
		
		public void setOneToOne(String oneToOne){
			this.oneToOneRelationship = oneToOne;
		}
		
		/*
		 * get set properties to get one to many relationships of the attribute
		 */
		
		public String getOneToMany(){
			return this.oneToManyRelationship;
		}
		
		public void setOneToMany(String oneToMany){
			this.oneToManyRelationship = oneToMany;
		}
		
		/*
		 * Parameterized constructor to create an object with the given values
		 */
		public ClassInterfaceAttributeInfo(String name, String accessModifier, String dataType, String oneToOne, String oneToMany){
			this.name = name;
			this.accessModifier = accessModifier;
			this.dataType = dataType;
			this.oneToOneRelationship = oneToOne;
			this.oneToManyRelationship = oneToMany;
		}
}
