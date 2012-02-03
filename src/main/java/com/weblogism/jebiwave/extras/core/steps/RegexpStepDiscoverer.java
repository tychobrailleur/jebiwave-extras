package com.weblogism.jebiwave.extras.core.steps;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.AbstractStepsFactory;

import com.google.classpath.ClassPath;
import com.google.classpath.ClassPathFactory;
import com.google.classpath.ResourceFilter;

public class RegexpStepDiscoverer extends AbstractStepsFactory {
    private final Map<Class<?>,Object> stepsInstances = new HashMap<Class<?>, Object>();
    
    private final static String DEFAULT_REGEXP = ".*Step";
    private final static String CLASS_EXTENSION = "\\.class";
    private String pattern = DEFAULT_REGEXP;
    
    public RegexpStepDiscoverer(Configuration configuration) {
        super(configuration);
    }
    
    public RegexpStepDiscoverer(Configuration configuration, String pattern) {
        this(configuration);
        setPattern(pattern);
    }
    
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Object createInstanceOfType(Class<?> type) {
        try {
            if (stepsInstances.get(type) == null) {
                Object instance = type.newInstance();
                stepsInstances.put(type, instance);
                return instance;
            } else {
                return stepsInstances.get(type);
            }
        } catch (Exception e) {
            fail(type + " cannot be instantiated.");
        }
        
        return null;
    }

    @Override
    protected List<Class<?>> stepsTypes() {
        String[] findResources = findMatchingSteps();
        
        final List<Class<?>> stepClasses = new ArrayList<Class<?>>();
        for (String step : findResources) {
            try {
                String javaClassName = step.replaceAll("/", ".").replaceAll(CLASS_EXTENSION, "");
                stepClasses.add(Class.forName(javaClassName));
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
        
        if (stepClasses.isEmpty()) {
            fail("No candidate step found, please check your regular expression: " + pattern);
        }
        
        return stepClasses;
    }

    private String[] findMatchingSteps() {
        final ClassPathFactory factory = new ClassPathFactory();
        final ClassPath classPath = factory.createFromJVM();
        
        String[] findResources = classPath.findResources("", new ResourceFilter() {
            
            @Override
            public boolean match(String dir, String fileName) {
                File resource = new File(dir, fileName);
                return Pattern.matches(pattern + CLASS_EXTENSION, resource.getPath());
            }
        });
        return findResources;
    }
}
