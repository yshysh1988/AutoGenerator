package com.bjxg.tools.codegenerator.impl.nck;

import java.io.File;
import java.util.List;

import com.bjxg.tools.codegenerator.XmlGenerator;
import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.DateConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.constants.RowConstants;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.AutoUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckFileUtils;
import com.bjxg.tools.codegenerator.projectitem.tools.utils.nck.NckXmlFileUtils;

public class NckXmlGeneratorImpl implements XmlGenerator {
	public void generate(Project project) {

		try {
			NckFileUtils.isFiles(project.getXmlPath(), true);
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
		System.out.println("Nck xml  path " + project.getXmlPath());
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
					NckFileUtils.createFile(project.getXmlPath() + fileId.name + "Dao.xml", isFile);
					String tableName = fileId.name.toLowerCase();
					calssName = AutoUtils.getUpperCase(fileId.name);
					writeFile = new File(project.getXmlPath() + calssName + "Dao.xml");
					File classFile = new File(project.getNckAutoPath() + "xml/" + "xmlFragment.txt");
					NckXmlFileUtils.readNckToFile(classFile, writeFile, tableName, calssName, fileId.describe, AutoUtils.getPrimarykey(list,"主键"), 
							isEmpty, project.getNckAutoPath() + "xml/", project.getNckBasePackage(), list);
				}
			}
			System.out.println(
					AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + " Create " + calssName + "Dao.xml xml success！");
		}
		System.out.println("--------- " + AutoUtils.getNowDate(DateConstants.DATE_FORMAT1) + "-----"
				+ " Create  xml success！" + "----");
	}

}
