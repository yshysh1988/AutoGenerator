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

        project.setBeanPackage("com.netcai.sdk.admin.entity");
        project.setDaoPackage("com.netcai.sdk.admin.dao");
        project.setVoPackage("com.netcai.sdk.admin.vo");
        project.setServicePackage("com.netcai.sdk.admin.service");
        project.setServiceImplPackage("com.netcai.sdk.admin.service.impl");
        project.setControllerPackage("com.netcai.admin.controller");
        project.setXmlPackage("com.netcai.sdk.admin.dao.impl");
        project.setPackageUtil("com.netcai.common");

        ProjectGenerator projectGeneratorImpl = new ProjectGeneratorImplNck();
        projectGeneratorImpl.generate(project);
    }

    public static void generateSystemInfo(String docPath, String basePackage, String pages, String nckOutPutPath) {
        Project project = new SimpleProject();
        project.setDocPath(docPath);
        project.setNckOutputPath(nckOutPutPath);
        project.setNckBasePackage(basePackage);

        project.setBeanPackage(basePackage + ".entity");
        project.setDaoPackage(basePackage + ".dao");
        project.setVoPackage(basePackage + ".vo");
        project.setServicePackage(basePackage + ".service");
        project.setServiceImplPackage(basePackage + ".service.impl");
        project.setControllerPackage(basePackage + ".controller");
        project.setXmlPackage(basePackage + ".dao.impl");
        project.setPackageUtil("com.info.admin.utils");

        ProjectGenerator projectGeneratorImpl = new ProjectGeneratorImplInfo();
        projectGeneratorImpl.generate(project);
    }
}
