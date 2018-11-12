package com.bjxg.tools.codegenerator.impl.nck;

import java.io.File;
import java.util.List;

import com.bjxg.tools.codegenerator.ServiceImplGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckServiceImplFileUtils;

public class NckServiceImplGeneratorImpl implements ServiceImplGenerator {
	public void generate(Project project) {
		try {
			NckFileUtils.isFiles(project.getServiceImplPath(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createService(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param project
	 * @throws Exception
	 * 
	 */
	public void createService(Project project) throws Exception {

		System.out.println("Nck service  path " + project.getServiceImplPath());
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
					NckFileUtils.createFile(project.getServiceImplPath() + calssName + "ServiceImpl.java", isFile);
					String tableName = fileId.name.toLowerCase();
					writeFile = new File(project.getServiceImplPath() + calssName + "ServiceImpl.java");
					File classFile = new File(project.getNckAutoPath() + "service/" + "serviceImplFragment.txt");
					NckServiceImplFileUtils.readNckToFile(classFile, writeFile, tableName, calssName, fileId.describe,
							"", "", "", isEmpty, project.getNckAutoPath() + "service/", project.getNckBasePackage(),
							list);
				}
			}
			System.out.println(AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName
					+ " service class success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create Service class success！" + "----");
	}

}
