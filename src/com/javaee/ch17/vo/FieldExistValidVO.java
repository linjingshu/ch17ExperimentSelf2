package com.javaee.ch17.vo;

public class FieldExistValidVO { // 为便于判断指定字段是否已存在。

	private String fieldValue, fieldType;

	private int id; // 对于更新操作来说，此条记录对应的主键id号
	
	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/*
	public FieldExistValidVO(String fieldValue, String fieldType) {
		this.fieldValue = fieldValue;
		this.fieldType = fieldType;
	}

	public FieldExistValidVO() {
		super();
	}
	*/
	
}
