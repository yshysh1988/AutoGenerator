package com.bjxg.tools;

import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.impl.SimpleProject;
import com.bjxg.tools.project.ProjectGenerator;
import com.bjxg.tools.project.impl.ProjectGeneratorImplInfo;
import com.bjxg.tools.project.impl.ProjectGeneratorImplNck;

public class AutoGeneratorUtils {
	public static void generateSystemNck(String docPath, String basePackage, String pages, String nckOutPutPath) {
		Project project = new SimpleProject();
		project.setDocPath(docPath);
		project.setNckOutputPath(nckOutPutPath);
		project.setNckBasePackage(basePackage);
		ProjectGenerator projectGeneratorImpl = new ProjectGeneratorImplNck();
		projectGeneratorImpl.generate(project);
	}

	public static void generateSystemInfo(String docPath, String basePackage, String pages, String nckOutPutPath) {
		Project project = new SimpleProject();
		project.setDocPath(docPath);
		project.setNckOutputPath(nckOutPutPath);
		project.setNckBasePackage(basePackage);
		ProjectGenerator projectGeneratorImpl = new ProjectGeneratorImplInfo();
		projectGeneratorImpl.generate(project);
	}
}
