package com.sheldon.basic.externalizable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ExternalizableObjectOperation {

	private static File file = new File("externalizable.txt");

	public static void main(String[] args) {
		try {
			wirteExternalizableObject();
			readExternalizableObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void wirteExternalizableObject() throws IOException {
		ObjectOutputStream oos = null;
		try {
			User user = new User("sheldon", 24);
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(user);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			oos.close();
		}
	}

	public static void readExternalizableObject() throws IOException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			User user = (User) ois.readObject();
			System.out.println("User Object: " + user);
			System.out.println("User : " + user.getUser() + ", " + user.getAge());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			ois.close();
		}
	}

}
