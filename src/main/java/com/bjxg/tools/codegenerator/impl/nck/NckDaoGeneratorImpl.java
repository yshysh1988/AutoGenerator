package com.bjxg.tools.codegenerator.impl.nck;

import java.io.File;
import java.util.List;

import com.bjxg.tools.codegenerator.DaoGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckDaoFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;

public class NckDaoGeneratorImpl implements DaoGenerator {
	
	@Override
	public void generate(Project project) {
		try {
			NckFileUtils.isFiles(project.getDaoPath(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createDao(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param project
	 * @throws Exception
	 * 
	 */
	public void createDao(Project project) throws Exception {

		System.out.println("Nck dao  path " + project.getDaoPath());
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
					NckFileUtils.createFile(project.getDaoPath() + calssName + "Dao.java", isFile);
					String tableName = fileId.name.toLowerCase();
					
					writeFile = new File(project.getDaoPath() + calssName + "Dao.java");
					File classFile = new File(project.getNckAutoPath() + "dao/" + "daoFragment.txt");
					NckDaoFileUtils.readNckToFile(classFile, writeFile, tableName, calssName, fileId.describe, "", "",
							"", isEmpty, project.getNckAutoPath() + "dao/", project.getNckBasePackage(), list);
					continue;
				}
			}
			System.out.println(AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName
					+ "Dao dao class success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create dao class success！" + "----");
	}

}
