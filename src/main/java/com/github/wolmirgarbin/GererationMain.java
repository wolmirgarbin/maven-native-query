package com.github.wolmirgarbin;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mojo(name = "native-query-generation", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GererationMain extends AbstractMojo {

    private static final String BASE_GENERATION = "target/generated-sources";
    private static final String BASE_PACKAGE = "templates";

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "scope")
    private String scope;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        //List<Dependency> dependencies = project.getDependencies();
        //long numDependencies = dependencies.stream().count();
        getLog().info("Generated Sources");

        for (Object test : project.getResources()) {
            getLog().info("Resource: "+ ((Resource)test).getTargetPath());
        }

        Map<String, String> files = new HashMap<>();
        files.put("test-base.sql", "SELECT DATE()");
        files.put("client-query-report.sql", "SELECT DATE()");
        files.put("client-insert.sql", "SELECT DATE()");
        files.put("client-update.sql", "SELECT DATE()");

        TemplateConverter templateConverter = new TemplateFreemarkerConvert();
        templateConverter.convertToClass(project.getGroupId(), files);
    }

}
