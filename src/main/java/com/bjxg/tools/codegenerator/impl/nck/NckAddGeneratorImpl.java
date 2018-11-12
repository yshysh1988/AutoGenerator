package com.bjxg.tools.codegenerator.impl.nck;

import java.io.File;
import java.util.List;

import com.bjxg.tools.codegenerator.AddGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckAddFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;

public class NckAddGeneratorImpl implements AddGenerator {

	@Override
	public void generate(Project project) {
		try {
			NckFileUtils.isFiles(project.getAddPath(), true);
			createAdd(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param project
	 * @throws Exception
	 * 
	 */
	public void createAdd(Project project) throws Exception {
		System.out.println("Nck add  path " + project.getAddPath());
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

					NckFileUtils.isFiles(project.getAddPath() + clzss + "/", false);
					NckFileUtils.createFile(project.getAddPath() + clzss + "/" + "add" + calssName + ".jsp", isFile);
					writeFile = new File(project.getAddPath() + clzss + "/" + "add" + calssName + ".jsp");

					String tableName = fileId.name.toLowerCase();
					File classFile = new File(project.getNckAutoPath() + "add/" + "addFragment.txt");
					// FileUtils.writeToFile(basePackage, writeFile, isEmpty);
					NckAddFileUtils.readNckToFile(classFile, writeFile, tableName, calssName, fileId.describe, AutoUtils.getPrimarykey(list,"主键"),
							isEmpty, project.getNckAutoPath() + "add/", project.getNckBasePackage(), list);
					continue;
				}
			}
			System.out.println(
					AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName + "Add add jsp success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create add.jsp success！" + "----");
	}

}
