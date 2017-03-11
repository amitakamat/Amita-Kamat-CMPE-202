package com.amitakamat.umlparser;

public class ClassInterfaceAttributeInfo {
		private String name; 
		private String accessModifier;
		private String dataType;
		private String oneToOneRelationship;
		private String oneToManyRelationship;
		
		public String getName(){
			return this.name;
		}
		
		public void setName(String name){
			this.name = name;
		}
		
		public String getAccessModifier(){
			return this.accessModifier;
		}
		
		public void setAccessModifier(String accessModifier){
			this.accessModifier = accessModifier;
		}
		
		public String getDataType(){
			return this.dataType;
		}
		
		public void setDataType(String dataType){
			this.dataType = dataType;
		}
		
		public String getOneToOne(){
			return this.oneToOneRelationship;
		}
		
		public void setOneToOne(String oneToOne){
			this.oneToOneRelationship = oneToOne;
		}
		
		public String getOneToMany(){
			return this.oneToManyRelationship;
		}
		
		public void setOneToMany(String oneToMany){
			this.oneToManyRelationship = oneToMany;
		}
		
		public ClassInterfaceAttributeInfo(String name, String accessModifier, String dataType, String oneToOne, String oneToMany){
			this.name = name;
			this.accessModifier = accessModifier;
			this.dataType = dataType;
			this.oneToOneRelationship = oneToOne;
			this.oneToManyRelationship = oneToMany;
		}
}
