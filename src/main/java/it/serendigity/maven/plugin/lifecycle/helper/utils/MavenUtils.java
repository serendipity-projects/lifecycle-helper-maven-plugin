package it.serendigity.maven.plugin.lifecycle.helper.utils;

import org.apache.maven.lifecycle.DefaultLifecycles;
import org.apache.maven.lifecycle.Lifecycle;
import org.apache.maven.plugin.MojoExecution;

public class MavenUtils {

	private MavenUtils() {

	}

	public static String retrieveLifecycle( String phase, DefaultLifecycles defaultLifecycles ) {
		String result = null;
		Lifecycle lifecycle = defaultLifecycles.getPhaseToLifecycleMap().get( phase );

		if ( lifecycle != null ) {
			result = lifecycle.getId();
		}

		return result;
	}

	public static String retrieveLifecycle( MojoExecution mojoExecution, DefaultLifecycles defaultLifecycles ) {
		return retrieveLifecycle( mojoExecution.getLifecyclePhase(), defaultLifecycles );
	}

}
