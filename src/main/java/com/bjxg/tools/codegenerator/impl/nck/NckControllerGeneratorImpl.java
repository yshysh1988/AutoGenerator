package com.bjxg.tools.codegenerator.impl.nck;

import java.io.File;
import java.util.List;

import com.bjxg.tools.codegenerator.ControllerGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckControllerFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;

public class NckControllerGeneratorImpl implements ControllerGenerator {
	
	@Override
	public void generate(Project project) {
		try {
			NckFileUtils.isFiles(project.getControllerPath(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createController(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param project
	 * @throws Exception
	 * 
	 */
	public void createController(Project project) throws Exception {

		System.out.println("Nck controller  path " + project.getControllerPath());
		List<List<ClellBean>> docList = project.getDocList();
		if (docList == null || docList.size() == 0) {
			return;
		}

		boolean isEmpty = true;
		boolean isFile = false;
		File writeFile = null;
		String calssName = "";
		for (List<ClellBean> list : docList) {
			for (ClellBean fileId : list) {
				if (RowConstants.ROW_ZERO.equals(fileId.row)) {
					calssName = AutoUtils.getUpperCase(fileId.name);

					NckFileUtils.isFiles(project.getControllerPath() + fileId.name.toLowerCase() + "/", false);

					NckFileUtils.createFile(project.getControllerPath() + fileId.name.toLowerCase() + "/" + calssName
							+ "Controller.java", isFile);

					String tableName = fileId.name.toLowerCase();
					writeFile = new File(project.getControllerPath() + fileId.name.toLowerCase() + "/" + calssName
							+ "Controller.java");					
					File classFile = new File(project.getNckAutoPath() + "controller/" + "controllerFragment.txt");
					NckControllerFileUtils.readNckToFile(classFile, writeFile, tableName, calssName, fileId.describe,
							AutoUtils.getPrimarykey(list,"主键") ,  isEmpty, project.getNckAutoPath() + "controller/", project.getNckBasePackage(),
							list);
				}
			}
			System.out.println(AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName
					+ " controller class success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create Controller class success！" + "----");
	}

}
