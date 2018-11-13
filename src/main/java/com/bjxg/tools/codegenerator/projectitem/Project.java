package com.bjxg.tools.codegenerator.projectitem;

import java.util.List;

import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;

public interface Project {
	public String getDocPath();

	public void setDocPath(String docPath);

	public String getOutputPath();

	public void setOutputPath(String outputPath);

	public String getBasePackage();

	public void setBasePackage(String basePackage);

	public String getPages();

	public void setPages(String pages);

	public String getNutzBasePackage();

	public void setNutzBasePackage(String nutzBasePackage);

	public String getNckBasePackage();

	public void setNckBasePackage(String nckBasePackage);

	public String getNutzOutputPath();

	public void setNutzOutputPath(String nutzOutputPath);

	public String getNckOutputPath();

	public void setNckOutputPath(String nckOutputPath);

	public String getNutzPages();

	public void setNutzPages(String nutzPages);

	public String getNckPages();

	public void setNckPages(String nckPages);

	public String getNutzAutoPath();

	public void setNutzAutoPath(String nutzAutoPath);

	public String getNckAutoPath();

	public void setNckAutoPath(String nckAutoPath);

	public String getAutoPath();

	public void setAutoPath(String path);

	public List<List<ClellBean>> getDocList();

	public void setDocList(List<List<ClellBean>> docList);

	public List<List<ClellBean>> getIsNulls();

	public void setIsNulls(List<List<ClellBean>> isNulls);

	public String getBeanPackage();

	public void setBeanPackage(String beanPackage);

	public String getDaoPackage();

	public void setDaoPackage(String daoPackage);

	public String getServicePackage();

	public void setServicePackage(String servicePackage);

	public String getServiceImplPackage();

	public void setServiceImplPackage(String serviceImplPackage) ;

	public String getControllerPackage();

	public void setControllerPackage(String controllerPackage);

	public String getAddPackage();

	public void setAddPackage(String addPackage);

	public String getListPackage();

	public void setListPackage(String listPackage);

	public String getXmlPackage();

	public void setXmlPackage(String xmlPackage);

	public String getVoPackage();

	public void setVoPackage(String voPackage);

	public String getExamplePackage() ;

	void setExamplePackage(String examplePackage) ;

	public String getBeanPath();

	public void setBeanPath(String beanPath);

	public String getDaoPath();

	public void setDaoPath(String daoPath);

	public String getServicePath();

	public void setServicePath(String servicePath);

	public String getServiceImplPath();

	public void setServiceImplPath(String serviceImplPath);

	public String getControllerPath();

	public void setControllerPath(String controllerPath);

	public String getAddPath();

	public void setAddPath(String addPath);

	public String getListPath();

	public void setListPath(String listPath);

	public String getXmlPath();

	public void setXmlPath(String xmlPath);

	public String getVoPath();

	public void setVoPath(String voPath);
	
	public String getExamplePath();
	
	public void setExamplePath(String examplePath);

	public String getPackageUtil();

	public void setPackageUtil(String packageUtil);


}
