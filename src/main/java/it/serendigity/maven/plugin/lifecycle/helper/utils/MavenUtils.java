package it.serendigity.maven.plugin.lifecycle.helper.utils;

import org.apache.maven.lifecycle.DefaultLifecycles;
import org.apache.maven.plugin.MojoExecution;

public class MavenUtils {

	private MavenUtils() {

	}

	public static String retrieveLifecycle( String phase, DefaultLifecycles defaultLifecycles ) {
		return defaultLifecycles.getPhaseToLifecycleMap().get( phase ).getId();
	}

	public static String retrieveLifecycle( MojoExecution mojoExecution, DefaultLifecycles defaultLifecycles ) {
		return retrieveLifecycle( mojoExecution.getLifecyclePhase(), defaultLifecycles );
	}
}
