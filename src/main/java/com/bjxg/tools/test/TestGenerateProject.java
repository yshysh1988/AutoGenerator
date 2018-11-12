package com.bjxg.tools.test;

import com.bjxg.tools.AutoGeneratorUtils;

public class TestGenerateProject {
	
	public static void main(String[] args) {
		String docPath = System.getProperty("user.dir") + "/src/main/resources/库存.xls";
		
		String nckOutPutPath = System.getProperty("user.dir") + "/src/main/java/netcai";
		AutoGeneratorUtils.generateSystemNck(docPath, "com.netcai.admin", "src/main/webapp/pages/", nckOutPutPath);
		
		String infoOutPutPath = System.getProperty("user.dir") + "/src/main/java/info";
		AutoGeneratorUtils.generateSystemInfo(docPath, "com.info.admin", "src/main/webapp/pages/", infoOutPutPath);
	}

}
