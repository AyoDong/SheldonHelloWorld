package com.sheldon.basic.externalizable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class User implements Externalizable {

	private String user;
	private int age;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public User() {
		
	}
	
	public User(String user, int age) {
		super();
		this.user = user;
		this.age = age;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("writeExternal");
		out.writeObject(user);
		out.writeInt(age);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		System.out.println("readExternal");
		user = (String) in.readObject();
		age = in.readInt();
	}

}
