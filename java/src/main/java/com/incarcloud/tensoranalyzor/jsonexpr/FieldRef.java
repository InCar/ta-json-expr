package com.incarcloud.tensoranalyzor.jsonexpr;

import org.json.JSONObject;

public class FieldRef {
    private Object dataDS;
    private String dataPath;
    private String desc;
    private String description;

    public FieldRef(){}

    public FieldRef(Object dataDS, String dataPath, String desc, String description){
        this.dataDS = dataDS;
        this.dataPath = dataPath;
        this.desc = desc;
        this.description = description;
    }

    public Object getDataDS(){ return dataDS; }
    public String getDataPath(){ return dataPath; }
    public String getDesc(){ return desc; }
    public String getDescription(){ return description;}
}
