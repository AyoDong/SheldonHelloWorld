package com.sheldon.basic.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableObjectOperation {

	private static File file = new File("serializable.txt");

	public static void main(String[] args) {
		try {
			writeSerializableObject();
			readSerializableObejct();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void writeSerializableObject() throws IOException {
		ObjectOutputStream oos = null;
		try {
			Man man = new Man("SHELDON", "123456");
			Person person = new Person(man, "DONG", 40);
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject("String");
			oos.writeObject(person);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			oos.close();
		}
	}

	private static void readSerializableObejct() throws IOException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			String string = (String) ois.readObject();
			Person person = (Person) ois.readObject();

			System.out.println("String = " + string);
			System.out.println("Person Object = " + person);
			System.out.println("Person : " + person.getAge() + ", " + person.getUsername() + ", "
					+ person.getMan().getUsername() + ", " + person.getMan().getPassword());
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
