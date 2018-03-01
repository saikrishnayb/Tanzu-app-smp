package com.penske.apps.adminconsole.model;

import java.util.List;

public class TemplateComponent {

    private int templateId;
    private List<ComponentSequence> components;

    public TemplateComponent() {}
    public TemplateComponent(int templateId) {
        this.templateId = templateId;
    }
    public int getTemplateId() {
        return templateId;
    }
    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    public List<ComponentSequence> getComponents() {
        return components;
    }
    public void setComponents(List<ComponentSequence> components) {
        this.components = components;
    }

}
