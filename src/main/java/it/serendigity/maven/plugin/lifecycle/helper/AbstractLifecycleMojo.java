
package it.serendigity.maven.plugin.lifecycle.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.lifecycle.DefaultLifecycles;
import org.apache.maven.lifecycle.LifecycleExecutor;
import org.apache.maven.lifecycle.MavenExecutionPlan;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.utils.logging.MessageUtils;

import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionInfo;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionPlanInfo;

public abstract class AbstractLifecycleMojo extends AbstractMojo {

	@Parameter(defaultValue = "${session}", readonly = true)
	private MavenSession session;

	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;

	@Component
	private LifecycleExecutor lifecycleExecutor;

	/**
	 * The Maven default built-in lifecycles.
	 */
	@Component
	protected DefaultLifecycles defaultLifecycles;

	/**
	 * Allows you to specify which phases will be used to calculate the execution
	 * plan.
	 */
	@Parameter(property = "lifecycle-helper.phases", defaultValue = "post-clean,deploy,site-deploy")
	private String[] paramLifecyclePhases;

	/** Allows you to filter the specified plugins (plugin-artifact-id). */
	@Parameter(property = "lifecycle-helper.filter.plugins")
	private String[] paramFilterPlugins;

	private List<String> pluginToElaborate;

	protected MavenExecutionPlanInfo calculateExecutionPlan( boolean calculateSummary ) throws MojoFailureException {

		setPluginToElaborate( normalizeParamFilterPlugin() );

		MavenExecutionPlanInfo planInfo = new MavenExecutionPlanInfo( calculateSummary );

		try {

			MavenExecutionPlan calculateExecutionPlan = lifecycleExecutor.calculateExecutionPlan( session,
					paramLifecyclePhases );
			List<MojoExecution> mojoExecutions = calculateExecutionPlan.getMojoExecutions();

			int order = 0;

			for ( MojoExecution mojoExecution : mojoExecutions ) {

				MavenExecutionInfo info = MavenExecutionInfo.from( order++, mojoExecution, defaultLifecycles );
				if ( validateMavenExecution( info ) ) {
					planInfo.addMavenExecutionInfo( info );
				}
			}

			if ( planInfo.getMavenExecutionsInfo() == null || planInfo.getMavenExecutionsInfo().isEmpty() ) {
				getLog().info( "No execution plan item found for requested parameters." );
			}

		}
		catch (Exception e) {
			throw new MojoFailureException( "Cannot calculate Maven execution plan, caused by: " + e.getMessage(), e );
		}
		return planInfo;
	}

	protected boolean validateMavenExecution( MavenExecutionInfo info ) {
		boolean result = true;

		getLog().debug( "Validate maven execution ..." );
		result = validatePlugin( info, getPluginsToElaborate() );

		if ( getLog().isDebugEnabled() ) {

			getLog().debug( "Maven execution " + info + ". Validate: " + result );
		}

		return result;
	}

	/**
	 * Normalize filter plugin
	 *
	 * @return List of plugin to filter
	 */
	protected List<String> normalizeParamFilterPlugin() {

		List<String> result = null;

		for ( String f : getParamFilterPlugins() ) {

			if ( result == null ) {
				result = new ArrayList<>();
			}
			if ( f != null ) {
				result.add( f.trim() );
			}
		}

		return result;
	}

	/**
	 * @param info a {@link MavenExecutionInfo }
	 * @param filterPlugin a list of plugin String (artifact Id)
	 * @return true if filterPlugin is null or contains the plugin
	 */
	protected static boolean validatePlugin( MavenExecutionInfo info, List<String> filterPlugin ) {

		boolean result = true;

		// Filter plugin
		if ( filterPlugin != null ) {

			result = filterPlugin.contains( info.getPluginArtifactId() );

		}

		return result;
	}

	protected void handleOutput( final String output ) {
		String header = headerParametersString();

		String outputWithTitle = header + output;
		getLog().info( outputWithTitle );

	}

	/**
	 * Mojo parameters header
	 *
	 * @return String parameters
	 */
	protected String headerParametersString() {
		String header = MessageUtils.buffer().strong(
				"\nProject: " + project.getName() + " (" + project.getArtifactId() + ":" + project.getVersion() + ")" )
				+ ".";
		header = header + "\nExecution plan for run phases: " + Arrays.asList( paramLifecyclePhases );

		header = header + "\nFilter plugins: " + Objects.toString( getPluginsToElaborate(), "" );

		return header;
	}

	protected String[] getParamFilterPlugins() {
		return paramFilterPlugins;
	}

	protected List<String> getPluginsToElaborate() {
		return pluginToElaborate;
	}

	protected void setPluginToElaborate( List<String> pluginToElaborate ) {
		this.pluginToElaborate = pluginToElaborate;
	}

}
