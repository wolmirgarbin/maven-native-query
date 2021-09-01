package com.github.wolmirgarbin;

import java.util.Map;

public interface TemplateConverter {

    void convertToClass(String projectPackage, Map<String, String> files);

}
