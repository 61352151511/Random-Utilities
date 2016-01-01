package com.sixonethree.randomutilities.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveFile  {
	public File file;
	public String name;
	public String path;

	public ArrayList<String> data = new ArrayList<String>();

	public SaveFile(String name, String path) {
		this.name = name;
		this.path = path;
		this.file = new File(path + name);
	}
	
	protected SaveFile() {}

	public void createFile() {
		if (this.file.exists()) { return; }
		File pathFile = new File(this.path);
		pathFile.mkdirs();

		try {
			this.file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		this.createFile();
		try {
			PrintWriter out = new PrintWriter(this.file);
			for (String line : this.data) {
				for (int i = 0; i < line.length(); i++) {
					char character = line.charAt(i);
					out.print(character);
				}
				out.println();
				out.flush();
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		this.createFile();
		this.clear();
		try {
			Scanner scan = new Scanner(this.file);
			while (scan.hasNext()) {
				this.data.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		this.data.clear();
	}

	public boolean exists() {
		return this.file.exists();
	}

	public String getSingleData(String name) {
		for (String aData : this.data) {
			if (aData.contains(name)) { return aData; }
		}
		return null;
	}

	public boolean isBoolean(String name) {
		return (name.contains("true") || name.contains("false")) ? true : false;
	}

	public boolean getBoolean(String name){
		String aData = this.getSingleData(name);
		String[] split = null;
		if (this.isBoolean(aData)) {
			split = aData.split("=");
		}
		return Boolean.parseBoolean(split[1]);
	}
	
	public String getString(String name) {
		String aData = this.getSingleData(name);
		String[] split = aData.split("=");
		return split[1];
	}
}