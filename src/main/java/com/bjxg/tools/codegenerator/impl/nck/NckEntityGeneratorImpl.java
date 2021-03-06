package com.bjxg.tools.codegenerator.impl.nck;

import java.io.File;
import java.util.List;

import com.bjxg.tools.codegenerator.EntityGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckEntityFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;

public class NckEntityGeneratorImpl implements EntityGenerator {
	
	@Override
	public void generate(Project project) {
		try {
			NckFileUtils.isFiles(project.getBeanPath(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createClazz(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param project
	 * @throws Exception
	 * 
	 */
	public void createClazz(Project project) throws Exception {

		System.out.println("nck entity  path " + project.getBeanPath());

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
//					System.out.println(calssName + " ：： "+ fileId.describe);
					NckFileUtils.createFile(project.getBeanPath() + calssName + ".java", isFile);
					String tableName = fileId.name.toLowerCase();
					writeFile = new File(project.getBeanPath() + calssName + ".java");
					File classFile = new File(project.getNckAutoPath() + "bean/" + "beanFragment.txt");
					NckEntityFileUtils.readNckToFile(classFile, writeFile, tableName, calssName, fileId.describe, "",
							"", "", isEmpty, project.getNckAutoPath() + "bean/", project.getNckBasePackage(), list,project);
				}
			}
			System.out.println(
					AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName + " bean class success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create bean class success！" + "----");
	}

}
