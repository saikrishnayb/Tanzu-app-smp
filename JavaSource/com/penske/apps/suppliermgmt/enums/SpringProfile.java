package com.penske.apps.suppliermgmt.enums;


public enum SpringProfile {

    DEV("dev"),
    QA("qa"),
    QA2("qa2"),
    PROD("prod");

    private String xmlName;

    private SpringProfile(String xmlName) {
        this.xmlName = xmlName;
    }

    public static SpringProfile findByXmlName(String xmlName) {

        for (SpringProfile springProfile : values()) {

            if(springProfile.getXmlName().equals(xmlName))
                return springProfile;

        }

        return null;
    }

    public String getXmlName() {
        return xmlName;
    }
}
