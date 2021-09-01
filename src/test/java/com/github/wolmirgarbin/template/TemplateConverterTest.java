package com.github.wolmirgarbin.template;

import com.github.wolmirgarbin.TemplateConverter;
import com.github.wolmirgarbin.TemplateFreemarkerConvert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TemplateConverterTest {

    @Test
    public void testEmpty() {
        Map<String, String> files = new HashMap<>();

        TemplateConverter converter = new TemplateFreemarkerConvert();
        converter.convertToClass("com.github.wolmirgarbin", files);
    }

    @Test
    public void testOne() {
        Map<String, String> files = new HashMap<>();
        files.put("test-base.sql", "SELECT DATE()");

        TemplateConverter converter = new TemplateFreemarkerConvert();
        converter.convertToClass("com.github.wolmirgarbin", files);
    }

    @Test
    public void testMore() {
        Map<String, String> files = new HashMap<>();
        files.put("test-base.sql", "SELECT DATE()");
        files.put("client-query-report.sql", "SELECT DATE()");
        files.put("client-insert.sql", "SELECT DATE()");
        files.put("client-update.sql", "SELECT DATE()");

        TemplateConverter converter = new TemplateFreemarkerConvert();
        converter.convertToClass("com.github.wolmirgarbin", files);
    }

}
