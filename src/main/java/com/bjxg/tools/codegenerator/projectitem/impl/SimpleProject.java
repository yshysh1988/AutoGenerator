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

}
