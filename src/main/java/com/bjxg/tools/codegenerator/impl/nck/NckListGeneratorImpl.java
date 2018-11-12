package com.bjxg.tools.codegenerator.impl.nck;

import java.io.File;
import java.util.List;

import com.bjxg.tools.codegenerator.ListGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckListFileUtils;

public class NckListGeneratorImpl implements ListGenerator {
	@Override
	public void generate(Project project) {
		try {
			NckFileUtils.isFiles(project.getListPath(), false);
			createList(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param project
	 * @throws Exception
	 * 
	 */
	public void createList(Project project) throws Exception {
		System.out.println("Nck add  path " + project.getListPath());
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
					String clzss = calssName.toLowerCase();
					NckFileUtils.isFiles(project.getListPath() + clzss + "/", false);
					NckFileUtils.createFile(project.getListPath() + clzss + "/list" + calssName + ".jsp", isFile);
					writeFile = new File(project.getListPath() + clzss + "/list" + calssName + ".jsp");
					
					String tableName = fileId.name.toLowerCase();
					File classFile = new File(project.getNckAutoPath() + "list/" + "listFragment.txt");
					NckListFileUtils.readToFileList(classFile, writeFile, tableName, calssName, fileId.describe,
							isEmpty, project.getNckAutoPath() + "list/", list, project.getNckBasePackage());
					continue;
				}
			}
			System.out.println(AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName
					+ "list list class success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create list class success！" + "----");
	}

}
