package it.serendigity.maven.plugin.lifecycle.helper.output;

import java.util.EnumSet;
import java.util.Set;

import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionAttribute;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionInfo;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionPlanInfo;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionSummary;

public abstract class TxtOutput {

	private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
	private Set<MavenExecutionAttribute> columns;
	private String rowFormat;
	private MavenExecutionPlanInfo executionPlanInfo;

	protected TxtOutput( MavenExecutionPlanInfo executionPlanInfo ) {

		this.executionPlanInfo = executionPlanInfo;

	}

	/**
	 * Init tables variables: columns, rowformat, ...
	 *
	 * @see #createColumns()
	 * @see #createRowFormat()
	 */
	protected void initTable() {
		this.columns = createColumns();

		this.rowFormat = createRowFormat();
	}

	/**
	 * Override this method and call super to initialize the table
	 *
	 * @return
	 */
	protected String createTable() {
		initTable();
		return "";
	}

	protected abstract String createRowFormat();

	protected String getHeaderTitle( MavenExecutionAttribute mavenExecutionAttribute ) {
		return mavenExecutionAttribute.getShortDescription().toUpperCase();
	}

	protected MavenExecutionSummary getSummary() {
		return executionPlanInfo.getSummary();
	}

	protected Set<MavenExecutionAttribute> createColumns() {

		return EnumSet.allOf( MavenExecutionAttribute.class );
	}

	protected String headerTitle() {

		Object[] columnTitle = new String[getColumns().size()];

		int count = 0;
		for ( MavenExecutionAttribute mavenExecutionAttribute : getColumns() ) {
			columnTitle[count] = getHeaderTitle( mavenExecutionAttribute );
			count++;
		}

		return String.format( getRowFormat(), columnTitle );
	}

	protected String tableRow( MavenExecutionInfo info ) {

		Object[] rowValues = new Object[getColumns().size()];

		int count = 0;
		for ( MavenExecutionAttribute mavenExecutionAttribute : getColumns() ) {
			rowValues[count] = info.getValueOrEmpty( mavenExecutionAttribute );
			count++;
		}

		return String.format( getRowFormat(), rowValues );

	}

	public Set<MavenExecutionAttribute> getColumns() {
		return columns;
	}

	public static String newLineSeparator() {
		return LINE_SEPARATOR;
	}

	public String getRowFormat() {
		return rowFormat;
	}

	protected MavenExecutionPlanInfo getExecutionPlanInfo() {
		return executionPlanInfo;
	}

}
