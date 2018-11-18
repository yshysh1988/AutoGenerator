package com.bjxg.tools.codegenerator.impl.nck;

import com.bjxg.tools.codegenerator.DesktopGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckDesktopFileUtils;

import java.io.File;
import java.util.List;

public class NckDesktopGeneratorImpl implements DesktopGenerator {
	@Override
	public void generate(Project project) {
		try {
			NckFileUtils.isFiles(project.getDesktopPath(), false);
			createDesktop(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param project
	 * @throws Exception
	 * 
	 */
	public void createDesktop(Project project) throws Exception {
		System.out.println("Nck desktop  path " + project.getDesktopPath());
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
					NckFileUtils.isFiles(project.getDesktopPath() + clzss + "/", false);
					NckFileUtils.createFile(project.getDesktopPath() + clzss + "/list" + calssName + "Desktop.jsp", isFile);
					writeFile = new File(project.getDesktopPath() + clzss + "/list" + calssName + "Desktop.jsp");
					
					String tableName = fileId.name.toLowerCase();
					File classFile = new File(project.getNckAutoPath() + "desktop/" + "desktopFragment.txt");
					NckDesktopFileUtils.readToFileList(classFile, writeFile, tableName, calssName, fileId.describe,
							isEmpty, project.getNckAutoPath() + "desktop/", list, project.getNckBasePackage());
					continue;
				}
			}
			System.out.println(AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName
					+ "desktop desktop class success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create desktop class success！" + "----");
	}

}
