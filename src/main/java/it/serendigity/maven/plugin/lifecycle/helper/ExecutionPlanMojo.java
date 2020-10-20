
package it.serendigity.maven.plugin.lifecycle.helper;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import it.serendigity.maven.plugin.lifecycle.helper.output.TxtTable;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionAttribute;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionPlanInfo;

/**
 * List execution plan for the current project.
 */
@Mojo(name = "execution-plan", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true, requiresProject = true)
public class ExecutionPlanMojo extends AbstractLifecycleMojo {

	/**
	 * If enabled allows you to group execution plan result by the orderBy param,
	 * any value of {@linkplain MavenExecutionAttribute}. It's valid only for table
	 * text output result. Order by {@linkplain MavenExecutionAttribute#PLAN_ORDER}
	 * is not grouped by.
	 **/
	@Parameter(property = "lifecycle-helper.groupby", defaultValue = "false")
	private boolean paramGroupBy;

	/**
	 * Allows you to group the execution plan result by any value of
	 * {@linkplain MavenExecutionAttribute} .
	 **/
	@Parameter(property = "lifecycle-helper.orderby", defaultValue = "PLAN_ORDER")
	private MavenExecutionAttribute paramOrderBy;

	//
	private MavenExecutionAttribute orderByToElaborate;

	public void execute() throws MojoExecutionException, MojoFailureException {

		if ( isParamSkip() ) {
			getLog().info( "Skipping the execution as per configuration" );
			return;
		}

		setOrderByToElaborate( getParamOrderBy() );

		MavenExecutionPlanInfo executionPlanInfo = calculateExecutionPlan( true );

		executionPlanInfo.sort( getOrderByToElaborate() );

		TxtTable textTable = new TxtTable( executionPlanInfo, getOrderByToElaborate(), getParamGroupBy() );

		String table = textTable.createTable();

		handleOutput( table );
	}

	@Override
	protected String headerParametersString() {
		String header = super.headerParametersString();
		header = header + "\nOrder by: " + getOrderByToElaborate();
		header = header + "\nGroup by: " + getParamGroupBy();

		return header;
	}

	protected MavenExecutionAttribute getParamOrderBy() {

		return paramOrderBy;
	}

	protected boolean getParamGroupBy() {

		return paramGroupBy;
	}

	protected void setOrderByToElaborate( MavenExecutionAttribute orderByToElaborate ) {
		this.orderByToElaborate = orderByToElaborate;
	}

	protected MavenExecutionAttribute getOrderByToElaborate() {
		return orderByToElaborate;
	}

}
