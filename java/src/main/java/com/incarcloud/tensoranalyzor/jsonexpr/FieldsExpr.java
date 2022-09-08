package com.incarcloud.tensoranalyzor.jsonexpr;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.*;

public class FieldsExpr{
    private static final Logger s_logger = LoggerFactory.getLogger(FieldsExpr.class);

    /**
     * 验证json是否符合schema
     */
    public boolean Validate(String json){
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
}