
package it.serendigity.maven.plugin.lifecycle.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.lifecycle.DefaultLifecycles;
import org.apache.maven.lifecycle.LifecycleExecutor;
import org.apache.maven.lifecycle.LifecycleNotFoundException;
import org.apache.maven.lifecycle.LifecyclePhaseNotFoundException;
import org.apache.maven.lifecycle.MavenExecutionPlan;
import org.apache.maven.lifecycle.internal.GoalTask;
import org.apache.maven.lifecycle.internal.LifecycleTask;
import org.apache.maven.lifecycle.internal.LifecycleTaskSegmentCalculator;
import org.apache.maven.lifecycle.internal.TaskSegment;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.InvalidPluginDescriptorException;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.MojoNotFoundException;
import org.apache.maven.plugin.PluginDescriptorParsingException;
import org.apache.maven.plugin.PluginNotFoundException;
import org.apache.maven.plugin.PluginResolutionException;
import org.apache.maven.plugin.prefix.NoPluginFoundForPrefixException;
import org.apache.maven.plugin.version.PluginVersionResolutionException;
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

	@Component
	private LifecycleTaskSegmentCalculator lifecycleTaskSegmentCalculator;

	/**
	 * The Maven default built-in lifecycles.
	 */
	@Component
	protected DefaultLifecycles defaultLifecycles;

	/**
	 * Allows you to specify which tasks (lifecycle phases or plugin/goal) will be used to calculate the execution
	 * plan.
	 * If not specified the run tasks are the phases: post-clean, deploy, site-deploy.
	 * If you set {@link #paramForceTasksFromSession} to true this parameter is ignored!
	 */
	@Parameter(property = "lifecycle-helper.tasks", defaultValue = "post-clean,deploy,site-deploy")
	private String[] paramLifecycleTasks;

	/** Allows you to filter the specified plugins (plugin-artifact-id). */
	@Parameter(property = "lifecycle-helper.filter.plugins")
	private String[] paramFilterPlugins;

	/**
	 * If enabled the tasks are taken from maven session and the property {@link #paramLifecycleTasks} is ignored
	 * Helpful when you include the plugin in your pom in order to list the execution-plan of the current tasks build.
	 **/
	@Parameter(property = "lifecycle-helper.forceTasksFromSession", defaultValue = "false")
	private boolean paramForceTasksFromSession;

	/**
	 * Skip the execution.
	 *
	 * @since 0.5.0
	 */
	@Parameter(property = "lifecycle-helper.skip", defaultValue = "false")
	private boolean paramSkip;

	protected boolean isParamForceTasksFromSession() {
		return paramForceTasksFromSession;
	}

	private List<String> pluginToElaborate;
	private String[] tasksToElaborate;

	protected MavenExecutionPlanInfo calculateExecutionPlan( boolean calculateSummary ) throws MojoFailureException {

		setPluginToElaborate( normalizeParamFilterPlugin() );

		MavenExecutionPlanInfo planInfo = new MavenExecutionPlanInfo( calculateSummary );

		try {

			String[] tasks = calculateTasksToElaborate();
			setTasksToElaborate( tasks );

			MavenExecutionPlan calculateExecutionPlan = lifecycleExecutor.calculateExecutionPlan( session,
					tasks );
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

	protected String[] calculateTasksToElaborate() throws MojoFailureException {
		String[] result = null;

		if ( isParamForceTasksFromSession() ) {
			List<String> tasksFromSession = retrieveTasksFromSession();

			if ( !tasksFromSession.isEmpty() ) {
				result = tasksFromSession.toArray( new String[tasksFromSession.size()] );
			}
		}
		else {
			result = paramLifecycleTasks;
		}
		return result;
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

		header = header + "\nForce tasks from Session: " + isParamForceTasksFromSession();

		if ( isParamForceTasksFromSession() ) {
			header = header + "\nExecution plan for Session tasks: " + Arrays.asList( getTasksToElaborate() );
		}
		else {
			header = header + "\nExecution plan for run tasks: " + Arrays.asList( paramLifecycleTasks );
		}

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

	protected List<String> retrieveTasksFromSession() throws MojoFailureException {

		List<String> sessionTasksResult = new ArrayList<>();

		List<TaskSegment> taskSegments;
		try {
			taskSegments = lifecycleTaskSegmentCalculator.calculateTaskSegments( session );

			if ( taskSegments != null ) {

				for ( TaskSegment taskSegmentList : taskSegments ) {

					for ( Object task : taskSegmentList.getTasks() ) {

						if ( task instanceof LifecycleTask ) {
							sessionTasksResult.add( ( (LifecycleTask) task ).getLifecyclePhase() );
						}
						else if ( task instanceof GoalTask ) {
							sessionTasksResult.add( ( (GoalTask) task ).toString() );
						}
						else {
							getLog().warn( "Task is not recognized. Task is ignored --> " + task + " " + task.getClass() );
						}

					}

				}
			}
		}
		catch (PluginNotFoundException | PluginResolutionException | PluginDescriptorParsingException | MojoNotFoundException | NoPluginFoundForPrefixException
				| InvalidPluginDescriptorException | PluginVersionResolutionException | LifecyclePhaseNotFoundException | LifecycleNotFoundException e) {
			throw new MojoFailureException( "Error retrieving task from session", e );
		}

		return sessionTasksResult;

	}

	protected String[] getTasksToElaborate() {
		return tasksToElaborate;
	}

	protected void setTasksToElaborate( String[] tasksToElaborate ) {
		this.tasksToElaborate = tasksToElaborate;
	}

	/**
	 * Check if the execution should be skipped
	 *
	 * @return true to skip
	 */
	protected boolean isParamSkip() {
		return paramSkip;
	}

}
