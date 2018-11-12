package com.bjxg.tools.project.impl;

import com.bjxg.tools.codegenerator.AddGenerator;
import com.bjxg.tools.codegenerator.AutoConstants;
import com.bjxg.tools.codegenerator.ControllerGenerator;
import com.bjxg.tools.codegenerator.DaoGenerator;
import com.bjxg.tools.codegenerator.EntityGenerator;
import com.bjxg.tools.codegenerator.ListGenerator;
import com.bjxg.tools.codegenerator.ServiceGenerator;
import com.bjxg.tools.codegenerator.ServiceImplGenerator;
import com.bjxg.tools.codegenerator.VoGenerator;
import com.bjxg.tools.codegenerator.XmlGenerator;
import com.bjxg.tools.codegenerator.impl.nck.NckAddGeneratorImpl;
import com.bjxg.tools.codegenerator.impl.nck.NckControllerGeneratorImpl;
import com.bjxg.tools.codegenerator.impl.nck.NckDaoGeneratorImpl;
import com.bjxg.tools.codegenerator.impl.nck.NckEntityGeneratorImpl;
import com.bjxg.tools.codegenerator.impl.nck.NckListGeneratorImpl;
import com.bjxg.tools.codegenerator.impl.nck.NckServiceGeneratorImpl;
import com.bjxg.tools.codegenerator.impl.nck.NckServiceImplGeneratorImpl;
import com.bjxg.tools.codegenerator.impl.nck.NckVoGeneratorImpl;
import com.bjxg.tools.codegenerator.impl.nck.NckXmlGeneratorImpl;
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
				+ (project.getNckBasePackage() + ".entity").replace(".", "/") + "/");

		project.setDaoPath(
				project.getNckOutputPath() + "/java/" + (project.getNckBasePackage() + ".dao").replace(".", "/") + "/");

		project.setServicePath(project.getNckOutputPath() + "/java/"
				+ (project.getNckBasePackage() + ".service").replace(".", "/") + "/");

		project.setServiceImplPath(project.getNckOutputPath() + "/java/"
				+ (project.getNckBasePackage() + ".service.impl").replace(".", "/") + "/");

		project.setControllerPath(project.getNckOutputPath() + "/java/"
				+ (project.getNckBasePackage() + ".controller").replace(".", "/") + "/");

		project.setVoPath(
				project.getNckOutputPath() + "/java/" + (project.getNckBasePackage() + ".vo").replace(".", "/") + "/");

		project.setAddPath(project.getNckOutputPath() + "/webapp/WEB-INF/jsp/");

		project.setListPath(project.getNckOutputPath() + "/webapp/WEB-INF/jsp/");

		project.setXmlPath(project.getNckOutputPath() + "/resources/"
				+ (project.getNckBasePackage() + ".dao.impl").replace(".", "/") + "/");

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
