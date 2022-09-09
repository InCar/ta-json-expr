package com.incarcloud.tensoranalyzor.jsonexpr;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FieldExprTest {
    @Test
    void ValidationForSample(){
        String json = loadSample();
        assertTrue(FieldsExpr.Validate(json));
    }

    @Test
    void ParseSample(){
        String json = loadSample();
        FieldsExpr target = new FieldsExpr(json);

        assertFalse(target.getDataSources().isEmpty());
        assertFalse(target.getFields().isEmpty());

        // 测试解析内容是否正确
        FieldRef frTM = target.getFields().get("tm");
        assertTrue(target.getDataSources().containsValue(frTM.getDataDS()));
        assertEquals("collectTime", frTM.getDataPath());
        assertEquals("采集时间", frTM.getDesc());
        assertEquals("数据的采样时间", frTM.getDescription());

        // 测试可空字段
        FieldRef frVin = target.getFields().get("vin");
        assertNull(frVin.getDesc());
        assertNull(frVin.getDescription());
    }

    private String loadSample(){
        StringBuilder sbJson = new StringBuilder();
        // reading sample
        try(InputStream isJson = new FileInputStream("../sample-fields.json")){
            InputStreamReader reader = new InputStreamReader(isJson, StandardCharsets.UTF_8);
            final int nSize = 4096;
            char[] buf = new char[nSize];
            int nRead = 0;
            do {
                nRead = reader.read(buf);
                sbJson.append(buf, 0, nRead);
            } while (nRead == nSize);

        }
        catch(IOException ex){
            System.err.println(ex.toString());
        }
        return sbJson.toString();
    }
}
