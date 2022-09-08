package com.incarcloud.tensoranalyzor.jsonexpr;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FieldExprTest {
    @Test
    void TestValidationForSample() throws IOException{
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

        // validate
        FieldsExpr target = new FieldsExpr();
        assertTrue(target.Validate(sbJson.toString()));
    }
}
