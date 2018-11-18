package com.bjxg.tools.codegenerator.projectitem.impl;

import java.util.List;

import com.bjxg.tools.codegenerator.projectitem.Project;
import com.bjxg.tools.codegenerator.projectitem.tools.bean.ClellBean;

public class SimpleProject implements Project {
	private String docPath;
	private String outputPath;
	private String basePackage;
	private String pages;
	private String nutzPages;
	private String nckPages;
	private String autoPath;
	private String nutzBasePackage;
	private String nutzOutputPath;
	private String nckOutputPath;
	private String nutzAutoPath;
	private String nckAutoPath;
	private String nckBasePackage;

	private String beanPackage;
	private String daoPackage;
	private String servicePackage;
	private String serviceImplPackage;
	private String controllerPackage;
	private String addPackage;
	private String listPackage;
	private String xmlPackage;
	private String voPackage;
	private String examplePackage;
	private String desktopPackage;

	private String beanPath;
	private String daoPath;
	private String servicePath;
	private String serviceImplPath;
	private String controllerPath;
	private String addPath;
	private String listPath;
	private String xmlPath;
	private String voPath;
	private String examplePath;
	private String desktopPath;

	private String packageUtil;

	private List<List<ClellBean>> isNulls;
	private List<List<ClellBean>> docList;

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public List<List<ClellBean>> getDocList() {
		return docList;
	}

	public void setDocList(List<List<ClellBean>> docList) {
		this.docList = docList;
	}

	public List<List<ClellBean>> getIsNulls() {
		return isNulls;
	}

	public void setIsNulls(List<List<ClellBean>> isNulls) {
		this.isNulls = isNulls;
	}

	public String getAutoPath() {
		return autoPath;
	}

	public void setAutoPath(String autoPath) {
		this.autoPath = autoPath;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getNutzBasePackage() {
		return nutzBasePackage;
	}

	public void setNutzBasePackage(String nutzBasePackage) {
		this.nutzBasePackage = nutzBasePackage;
	}

	public String getNutzOutputPath() {
		return nutzOutputPath;
	}

	public void setNutzOutputPath(String nutzOutputPath) {
		this.nutzOutputPath = nutzOutputPath;
	}

	public String getNutzAutoPath() {
		return nutzAutoPath;
	}

	public void setNutzAutoPath(String nutzAutoPath) {
		this.nutzAutoPath = nutzAutoPath;
	}

	public String getNutzPages() {
		return nutzPages;
	}

	public void setNutzPages(String nutzPages) {
		this.nutzPages = nutzPages;
	}

	public String getNckAutoPath() {
		return nckAutoPath;
	}

	public void setNckAutoPath(String nckAutoPath) {
		this.nckAutoPath = nckAutoPath;
	}

	public String getNckBasePackage() {
		return nckBasePackage;
	}

	public void setNckBasePackage(String nckBasePackage) {
		this.nckBasePackage = nckBasePackage;
	}

	public String getNckOutputPath() {
		return nckOutputPath;
	}

	public void setNckOutputPath(String nckOutputPath) {
		this.nckOutputPath = nckOutputPath;
	}

	public String getNckPages() {
		return nckPages;
	}

	public void setNckPages(String nckPages) {
		this.nckPages = nckPages;
	}

	public String getBeanPath() {
		return beanPath;
	}

	public void setBeanPath(String beanPath) {
		this.beanPath = beanPath;
	}

	public String getDaoPath() {
		return daoPath;
	}

	public void setDaoPath(String daoPath) {
		this.daoPath = daoPath;
	}

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	public String getServiceImplPath() {
		return serviceImplPath;
	}

	public void setServiceImplPath(String serviceImplPath) {
		this.serviceImplPath = serviceImplPath;
	}

	public String getControllerPath() {
		return controllerPath;
	}

	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}

	public String getAddPath() {
		return addPath;
	}

	public void setAddPath(String addPath) {
		this.addPath = addPath;
	}

	public String getListPath() {
		return listPath;
	}

	public void setListPath(String listPath) {
		this.listPath = listPath;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String getVoPath() {
		return voPath;
	}

	public void setVoPath(String voPath) {
		this.voPath = voPath;
	}

	public String getExamplePath() {
		return examplePath;
	}

	public void setExamplePath(String examplePath) {
		this.examplePath = examplePath;
	}

	public String getBeanPackage() {
		return beanPackage;
	}

	public void setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getServiceImplPackage() {
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}

	public String getControllerPackage() {
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public String getAddPackage() {
		return addPackage;
	}

	public void setAddPackage(String addPackage) {
		this.addPackage = addPackage;
	}

	public String getListPackage() {
		return listPackage;
	}

	public void setListPackage(String listPackage) {
		this.listPackage = listPackage;
	}

	public String getXmlPackage() {
		return xmlPackage;
	}

	public void setXmlPackage(String xmlPackage) {
		this.xmlPackage = xmlPackage;
	}

	public String getVoPackage() {
		return voPackage;
	}

	public void setVoPackage(String voPackage) {
		this.voPackage = voPackage;
	}

	public String getExamplePackage() {
		return examplePackage;
	}

	public void setExamplePackage(String examplePackage) {
		this.examplePackage = examplePackage;
	}

	@Override
	public String getPackageUtil() {
		return packageUtil;
	}

	@Override
	public void setPackageUtil(String packageUtil) {
		this.packageUtil = packageUtil;
	}

	public String getDesktopPackage() {
		return desktopPackage;
	}

	public void setDesktopPackage(String desktopPackage) {
		this.desktopPackage = desktopPackage;
	}

	public String getDesktopPath() {
		return desktopPath;
	}

	public void setDesktopPath(String desktopPath) {
		this.desktopPath = desktopPath;
	}
}
