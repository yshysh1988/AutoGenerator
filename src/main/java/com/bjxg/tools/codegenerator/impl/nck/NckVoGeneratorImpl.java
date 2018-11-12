package com.bjxg.tools.codegenerator.impl.nck;

import java.io.File;
import java.util.List;

import com.bjxg.tools.codegenerator.VoGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckEntityFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;

public class NckVoGeneratorImpl implements VoGenerator {
	public void generate(Project project) {
		try {
			NckFileUtils.isFiles(project.getVoPath(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			createVo(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param project
	 * @throws Exception
	 * 
	 */
	public void createVo(Project project) throws Exception {
		System.out.println("nck vo  path " + project.getVoPath());
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
					NckFileUtils.createFile(project.getVoPath() + calssName + "Vo.java", isFile);
					String tableName = fileId.name.toLowerCase();
					writeFile = new File(project.getVoPath() + calssName + "Vo.java");
					File classFile = new File(project.getNckAutoPath() + "vo/" + "voFragment.txt");
					NckEntityFileUtils.readNckToFile(classFile, writeFile, tableName, calssName, fileId.describe, "",
							"", "", isEmpty, project.getNckAutoPath() + "vo/", project.getNckBasePackage(), list);
				}
			}
			System.out.println(
					AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName + " vo class success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create vo class success！" + "----");
	}

}
