package com.weblogism.jebiwave.extras.core.junit;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.junit.Assert;

import com.weblogism.jebiwave.extras.annotations.StoryPath;
import com.weblogism.jebiwave.extras.core.io.SimplePathResolver;

/**
 * @author Sébastien Le Callonnec
 *
 */
public class StoryTestCase extends JUnitStory {
    @Override
    public Configuration configuration() {
        StoryPath annotation = this.getClass().getAnnotation(StoryPath.class);
        Assert.assertNotNull("You must provide a @StoryPath annotation.", annotation);
        
        return new MostUsefulConfiguration()
                .useStoryPathResolver(new SimplePathResolver(annotation.value()))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                .withFailureTrace(true)
                .withDefaultFormats()
                .withFormats(CONSOLE, TXT, HTML, XML));
    }
}
