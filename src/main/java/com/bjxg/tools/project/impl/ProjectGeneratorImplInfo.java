package com.bjxg.tools.project.impl;

import com.bjxg.tools.codegenerator.*;
import com.bjxg.tools.codegenerator.impl.nck.*;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.LoadExclUtils;
import com.bjxg.tools.project.ProjectGenerator;

public class ProjectGeneratorImplInfo implements ProjectGenerator {

    private EntityGenerator nckEntityGeneratorImpl = new NckEntityGeneratorImpl();
    private DaoGenerator nckDaoGeneratorImpl = new NckDaoGeneratorImpl();
    private ServiceGenerator nckServiceGeneratorImpl = new NckServiceGeneratorImpl();
    private ServiceImplGenerator nckServiceImplGeneratorImpl = new NckServiceImplGeneratorImpl();
    private ControllerGenerator nckControllerGeneratorImpl = new NckControllerGeneratorImpl();
    private VoGenerator nckVoGeneratorImpl = new NckVoGeneratorImpl();
    private XmlGenerator nckXmlGeneratorImpl = new NckXmlGeneratorImpl();
    private AddGenerator nckAddGeneratorImpl = new NckAddGeneratorImpl();
    private ListGenerator listGenerator = new NckListGeneratorImpl();

    @Override
    public void generate(Project project) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        project.setNckAutoPath((path + AutoConstants.PATH_TEMPLATES + "info/").replace("bin", "src"));
        project.setNckPages(path + project.getNckPages());
        LoadExclUtils.createDocList(project);

        project.setBeanPath(project.getNckOutputPath() + "/java/"
                + (project.getBeanPackage()).replace(".", "/") + "/");

        project.setDaoPath(
                project.getNckOutputPath() + "/java/" + (project.getDaoPackage()).replace(".", "/") + "/");

        project.setServicePath(project.getNckOutputPath() + "/java/"
                + (project.getServicePackage()).replace(".", "/") + "/");

        project.setServiceImplPath(project.getNckOutputPath() + "/java/"
                + (project.getServiceImplPackage()).replace(".", "/") + "/");

        project.setControllerPath(project.getNckOutputPath() + "/java/"
                + (project.getControllerPackage()).replace(".", "/") + "/");

        project.setVoPath(project.getNckOutputPath() + "/java/" + (project.getVoPackage()).replace(".", "/") + "/");

        project.setAddPath(project.getNckOutputPath() + "/webapp/WEB-INF/jsp/");

        project.setListPath(project.getNckOutputPath() + "/webapp/WEB-INF/jsp/");

        project.setXmlPath(project.getNckOutputPath() + "/resources/"
                + (project.getXmlPackage()).replace(".", "/") + "/");

        nckEntityGeneratorImpl.generate(project);
        nckDaoGeneratorImpl.generate(project);
        nckServiceGeneratorImpl.generate(project);
        nckServiceImplGeneratorImpl.generate(project);
        nckControllerGeneratorImpl.generate(project);
        nckVoGeneratorImpl.generate(project);
        nckXmlGeneratorImpl.generate(project);
        nckAddGeneratorImpl.generate(project);
        listGenerator.generate(project);
    }
}
