package com.incarcloud.tensoranalyzor.jsonexpr;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.*;

public class FieldsExpr{
    private static final Logger s_logger = LoggerFactory.getLogger(FieldsExpr.class);
    private  static final Pattern s_rgxDS = Pattern.compile("^\\$\\{(\\w+)\\}");

    private final HashMap<String, Object> mapDS = new HashMap<>();
    private final HashMap<String, FieldRef> mapFields = new HashMap<>();

    /**
     * 验证json是否符合schema
     */
    public static boolean Validate(String json){
        try(InputStream schemaStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("./schema-fields.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(json));
        }
        catch(IOException | ValidationException ex) {
            s_logger.warn("Validate json failed\n\t{}", ex.toString());
            return false;
        }
        return true;
    }

    public FieldsExpr(){}

    public FieldsExpr(String json){
        JSONObject joRoot = new JSONObject(json);

        JSONObject joDS = joRoot.getJSONObject("dataSources");
        loadDataSources(joDS);

        JSONObject joFields = joRoot.getJSONObject("fields");
        loadFields(joFields);
    }

    public Map<String, Object> getDataSources(){ return mapDS; }
    public Map<String, FieldRef> getFields(){ return mapFields; }

    private void loadDataSources(JSONObject joDS){
        for(String key : joDS.keySet()){
            Object objDS = joDS.get(key);
            mapDS.put(key, objDS);
        }
    }

    private void loadFields(JSONObject joFields){
        for(String key : joFields.keySet()){
            if(mapFields.containsKey(key)){
                s_logger.warn("Duplicate field: {}, ignore subsequent", key);
                continue;
            }

            JSONObject joField = joFields.getJSONObject(key);
            String data = joField.getString("data");
            Object objDS = findDS(data);
            if(objDS == null){
                s_logger.warn("Missing data source: {} : {}, ignore", key, data);
                continue;
            }
            String dataPath = findDataPath(data);

            String desc = null, description = null;
            if(joField.has("desc")) desc = joField.getString("desc");
            if(joField.has("description")) description = joField.getString("description");

            mapFields.put(key, new FieldRef(objDS, dataPath, desc, description));
        }
    }

    private Object findDS(String data){
        Matcher m = s_rgxDS.matcher(data);
        if(m.find()){
            String ds = m.group(1);
            return mapDS.get(ds);
        }
        return null;
    }

    private String findDataPath(String data){
        int pos = data.indexOf("/");
        return data.substring(pos+1);
    }
}