package com.penske.apps.adminconsole.model;

import java.util.List;
/**
 * 
 * @author 600144005
 * This class is a model object to hold information about templates 
 */
public class TemplatePoCategorySubCategory  {
	private int templateId;
	private SubCategory subCategory;
	private PoCategory poCategory;
	private List<TemplateComponents> templateComponents;
	
	// Getters
	public int getTemplateId() {
		return templateId;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public PoCategory getPoCategory() {
		return poCategory;
	}

	public List<TemplateComponents> getTemplateComponents() {
		return templateComponents;
	}

	// Setters
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public void setPoCategory(PoCategory poCategory) {
		this.poCategory = poCategory;
	}

	public void setTemplateComponents(List<TemplateComponents> templateComponents) {
		this.templateComponents = templateComponents;
	}

	@Override
	public String toString() {
		return "TemplatePoCategorySubCategory [templateId=" + templateId
				+ ", subCategory=" + subCategory + ", poCategory=" + poCategory
				+ ", templateComponents=" + templateComponents + "]";
	}
	
	
	
}
