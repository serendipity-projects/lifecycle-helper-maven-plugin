package it.serendigity.maven.plugin.lifecycle.helper.output;

import java.util.Collection;

import it.serendigity.maven.plugin.lifecycle.helper.utils.TextUtils;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionInfo;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionPlanInfo;

public class CSVTable extends TxtOutput {

	private static final String COLUMN_SEPARATOR = ";";

	public CSVTable( MavenExecutionPlanInfo executionPlanInfo ) {
		super( executionPlanInfo );

	}

	@Override
	public String createTable()
	{
		super.createTable();

		StringBuilder builder = new StringBuilder();
		MavenExecutionPlanInfo info = getExecutionPlanInfo();
		if ( info.getMavenExecutionsInfo() != null && !info.getMavenExecutionsInfo().isEmpty() ) {
			builder.append( headerRows() );

			Collection<MavenExecutionInfo> mavenExecutionsInfo = info.getMavenExecutionsInfo();
			for ( MavenExecutionInfo mavenExecutionInfo : mavenExecutionsInfo ) {

				builder.append( newLineSeparator() );
				builder.append( tableRow( mavenExecutionInfo ) );

			}
		}

		return builder.toString();

	}

	protected String headerRows() {
		StringBuilder output = new StringBuilder()

				.append( newLineSeparator() ).append( headerTitle() );

		return output.toString();
	}

	@Override
	protected String createRowFormat() {
		StringBuilder builder = new StringBuilder();
		int size = getColumns().size();

		for ( int i = 0; i < size; i++ ) {

			builder.append( TextUtils.STRING_PLACEHOLDER );
			builder.append( COLUMN_SEPARATOR );
		}

		return builder.toString();
	}

}
