package com.sheldon.basic.innerclass;

public class OuterClassDemo {
	
	private String outerDesc;
	
	public class InnerClass{
		
		private String innerDesc;

		public String getDescription() {
			return innerDesc;
		}

		public void setDescription(String description) {
			this.innerDesc = description;
		}
		
		public InnerClass(String desc) {
			
			outerDesc = desc;
			System.out.println(outerDesc);
			
			OuterClassDemo outter = new OuterClassDemo();
			System.out.println(outter.outerDesc);
			System.out.println("----------------------Cut Off--------------------------");
		}
		
		public void printOutterDescription() {
			System.out.println(this.innerDesc);
			System.out.println(outerDesc);
			System.out.println("----------------------Cut Off--------------------------");
		}
		
	}
	
	public static void main(String[] args) {
		OuterClassDemo outterClass = new OuterClassDemo();
		
		OuterClassDemo.InnerClass outerWithInner = outterClass.new InnerClass("A");
		
		//you have to construct the outer class in order to construct inner class.
		InnerClass innerClass = outterClass.new InnerClass("InnerClass");
		
		innerClass.printOutterDescription();
		
		innerClass.setDescription("Hello");
		
		innerClass.printOutterDescription();
		
	}

}
