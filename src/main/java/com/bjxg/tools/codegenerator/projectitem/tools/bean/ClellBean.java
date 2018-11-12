package com.bjxg.tools.codegenerator.projectitem.tools.bean;

public class ClellBean {
	/*** 包路径 **/
	public String basePackage = "";
	/*** 行数 **/
	public String row = "";
	/*** 属性名称 **/
	public String name = "";
	/*** 属性数据库字段名称 **/
	public String dbname = "";
	/*** 类型 **/
	public String type = "";
	/*** 数据库字段类型 **/
	public String dbtype = "";
	/*** 数据库字段长度 **/
	public String dblength = "";
	/*** 长度 **/
	public String length = "";
	/*** 字段介绍 **/
	public String describe = "";
	/*** 模糊查询 **/
	public String vague = "";
	/*** 是否为空 **/
	public String isNull = "n";
	/*** 备注 **/
	public String annotate = "";
	/*** 是否允许为空 **/
	public String isList = "n";
	/*** 网页输入框宽度 **/
	public String width = "120px";
	/*** 是否是图片 **/
	public String isImg = "n";
	/*** 图片类型 **/
	public String fileType = "";
	/*** 是否是必填 **/
	public String isVereist = "n";
	/*** 是否是下拉列表 **/
	public String isSelect = "n";
	/*** 下拉列表实体类名 **/
	public String entityClass = "";
	/*** 下拉列表中保存字段名称 **/
	public String disVal = "";
	/*** 下拉列表中显示字段名称 **/
	public String disDesc = "";
	/*** 下拉列表中获取下拉列表的值 **/
	public String filterVal = "";
	/*** 是否为隐藏框 **/
	public String isHidden = "n";
	/*** 下拉狂list页面显示关了字端 **/
	public String relevanceField = "";

	public int getLength() {
		if (length != null && length.trim().length() > 0) {
			return Integer.parseInt(length);
		} else {
			return 0;
		}
	}
}
