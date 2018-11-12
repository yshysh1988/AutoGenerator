package com.bjxg.tools.codegenerator.projectitem.tools.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;

public class FileUtils {

	public static boolean createFile(String filePath) throws Exception {
		if (filePath == null) {
			return false;
		}
		String[] arrStr = filePath.split("/");
		String starPath = arrStr[0];
		for (int i = 1; i < arrStr.length - 1; i++) {
			if (arrStr[i].length() > 0) {
				starPath = starPath + "/" + arrStr[i];
				File files = new File(starPath);
				// 如果文件夹不存在则创建
				if (!files.exists() && !files.isDirectory()) {
					files.mkdir();
				}
			}
		}
		File fileName = new File(filePath);
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/** 判断文件夹是否存在，不存在，则创建文件夹 **/
	public static boolean isFiles(String filePath) throws Exception {
		if (filePath == null) {
			return false;
		}
		String[] arrStr = filePath.replace("\\", "/").replace(".", "/").split("/");
		String starPath = arrStr[0];
		for (int i = 1; i < arrStr.length; i++) {
			if (arrStr[i].length() > 0) {
				starPath = starPath + "/" + arrStr[i];
				File files = new File(starPath);
				// 如果文件夹不存在则创建
				if (!files.exists() && !files.isDirectory()) {
					files.mkdir();
				} else if (i == (arrStr.length - 1)) {
					emptyFile(files);
				}
			}
		}
		return true;
	}

	/** 判断文件夹是否存在，不存在，则创建文件夹 **/
	public static boolean isFiles(String filePath, boolean isEmptyFile) throws Exception {
		if (filePath == null) {
			return false;
		}
		String[] arrStr = filePath.replace("\\", "/").replace(".", "/").split("/");
		String starPath = arrStr[0];
		for (int i = 1; i < arrStr.length; i++) {
			if (arrStr[i].length() > 0) {
				starPath = starPath + "/" + arrStr[i];
				File files = new File(starPath);
				// 如果文件夹不存在则创建
				if (!files.exists() && !files.isDirectory()) {
					files.mkdir();
				} else if (i == (arrStr.length - 1)) {
					if (isEmptyFile) {
						emptyFile(files);
					}
				}
			}
		}
		return true;
	}

	/** 删除文件夹中的文件 ***/
	private final static void emptyFile(File dir) throws Exception {
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				try {
					emptyFile(fs[i]);
				} catch (Exception e) {
				}
				File file = new File(fs[i].getAbsolutePath());
				if (file.exists() && file.isDirectory()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param
	 * @return
	 */
	public static boolean createFile(String filePath, boolean isEmpty) throws Exception {
		File fileName = new File(filePath);
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			} else {
				writeToFile("", fileName, isEmpty);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean writeToFile(String content, File fileName, boolean fillBoolean) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName, fillBoolean);
			o.write(content.getBytes("UTF-8"));
			o.close();
			/*
			 * mm=new RandomAccessFile(fileName,"rw"); mm.writeBytes(content);
			 */
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}

	public static void contentToTxt(String filePath, String content) {
		String str = new String();
		String s1 = new String();
		try {
			File f = new File(filePath);
			if (f.exists()) {
				System.out.print("文件存在！");
			} else {
				System.out.print("文件不存在！");
				f.createNewFile();
			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			System.out.println(s1);
			input.close();
			s1 += content;

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
