package com.weblogism.jebiwave.extras.core.io;

import org.jbehave.core.Embeddable;
import org.jbehave.core.io.StoryPathResolver;

/**
 * Simple {@link StoryPathResolver} implementation that uses
 * a relative path to a story file in the classpath.
 * 
 * <p>Example of use:</p>
 * 
 * <pre>
 *  @Override
 *   public Configuration configuration() {
 *       return new MostUsefulConfiguration()
 *               .useStoryPathResolver(new SimplePathResolver("test_folder/test_story.feature"));
 *   }
 * </pre>
 * 
 * <p>Here <code>test_folder</code> must be on the classpath, <i>e.g.</i> under
 * <code>src/test/resources</code> if using Maven.</p>
 * 
 * @author Sébastien Le Callonnec
 */
public class SimplePathResolver implements StoryPathResolver {

    private String relativePath;
    
    /**
     * builds the {@link StoryPathResolver} instance with the relative
     * path to the story file in the classpath.
     * 
     * @param relativePath - Path to resolve in the classpath.
     */
    public SimplePathResolver(String relativePath) {
        this.relativePath = relativePath;
    }
    
    /**
     * @see org.jbehave.core.io.StoryPathResolver#resolve(java.lang.Class)
     */
    @Override
    public String resolve(Class<? extends Embeddable> embeddableClass) {
        return relativePath;
    }
}
