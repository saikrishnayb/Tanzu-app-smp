package com.penske.apps.adminconsole.enums;

public enum UniformResource{
    
    COMMON_STATIC_URL("commonStaticUrl");
    
    private String value;
    
    private UniformResource(String value){
        this.setValue(value);
    }

    public String getValue(){
        return value;
    }

    private void setValue(String value){
        this.value = value;
    }
}
