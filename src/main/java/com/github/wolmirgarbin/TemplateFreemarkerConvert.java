package com.github.wolmirgarbin;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TemplateFreemarkerConvert implements TemplateConverter {

    private static final String BASE_GENERATION = "target/generated-sources";
    private static final String BASE_PACKAGE = "templates";

    @Override
    public void convertToClass(String projectPackage, Map<String, String> files) {
        if (files == null) {
            return;
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(GererationMain.class, "/"+ BASE_PACKAGE);

        // Some other recommended settings:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        final String generateInPath = getAndCreateIfNotExistsDir(projectPackage);

        try {
            Template template = cfg.getTemplate("class.ftl");

            for (Map.Entry<String, String> file : files.entrySet()) {
                final String className = convertName(file.getKey());

                Map<String, Object> input = new HashMap<>();
                input.put("projectPackage", projectPackage + "." + BASE_PACKAGE);
                input.put("className", className);
                input.put("query", file.getValue());

                try (Writer consoleWriter = new OutputStreamWriter(System.out)) {
                    template.process(input, consoleWriter);
                } catch (TemplateException | IOException e) {
                    e.printStackTrace();
                }

                // For the sake of example, also write output into a file:
                try (Writer fileWriter = new FileWriter(getClassName(generateInPath, className))) {
                    template.process(input, fileWriter);
                } catch (IOException | TemplateException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAndCreateIfNotExistsDir(String projectPackage) {
        String inPackage = String.format("%s/%s/%s/", BASE_GENERATION, packageToDir(projectPackage), BASE_PACKAGE);

        File dir = new File(inPackage);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return inPackage;
    }

    private String getClassName(final String inPackage, final String className) {
        return String.format("%s%s.java", inPackage, className);
    }

    private String convertName(final String key) {
        String textByName = key.replace(".sql", "");

        StringBuilder sb = new StringBuilder();
        for (String word : textByName.split("-")) {
            sb.append(word.substring(0, 1).toUpperCase() + word.substring(1));
        }
        return sb.toString();
    }

    private String packageToDir(final String projectPackage) {
        return projectPackage.replaceAll("\\.", "/");
    }
}
