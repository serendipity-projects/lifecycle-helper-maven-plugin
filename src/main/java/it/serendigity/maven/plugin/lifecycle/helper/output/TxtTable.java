package it.serendigity.maven.plugin.lifecycle.helper.output;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.apache.maven.shared.utils.logging.MessageUtils;

import com.google.common.base.Strings;

import it.serendigity.maven.plugin.lifecycle.helper.utils.TextUtils;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionAttribute;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionInfo;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionPlanInfo;

public class TxtTable extends TxtOutput {

	private boolean groupByAttribute;
	private MavenExecutionAttribute orderByColumn;

	private int rowLength;

	private static final String COLUMN_SEPARATOR = " | ";
	private static final int COLUMN_SEPARATOR_LENGTH = COLUMN_SEPARATOR.length();
	private static final int DEFAULT_COLUMN_WIDTH = 50;

	public TxtTable( MavenExecutionPlanInfo executionPlanInfo, MavenExecutionAttribute orderByColumn, boolean groupByAttribute ) {
		super( executionPlanInfo );

		this.orderByColumn = orderByColumn;
		this.groupByAttribute = groupByAttribute;
	}

	@Override
	protected String createRowFormat() {
		StringBuilder builder = new StringBuilder();

		int width = 0;

		for ( MavenExecutionAttribute mavenExecutionAttribute : getColumns() ) {

			builder.append( COLUMN_SEPARATOR );

			int maxStringLength = DEFAULT_COLUMN_WIDTH;
			if ( getSummary() != null ) {
				maxStringLength = Math.max( getSummary().getMaxStringLength( mavenExecutionAttribute ),
						getHeaderTitle( mavenExecutionAttribute ).length() );

			}
			builder.append( TextUtils.justifyFormat( -maxStringLength ) );

			width = width + maxStringLength + COLUMN_SEPARATOR_LENGTH;

		}
		builder.append( COLUMN_SEPARATOR );
		width = width + COLUMN_SEPARATOR_LENGTH;

		setRowLength( width );

		return builder.toString();
	}

	protected boolean isGroupByAttribute() {
		return groupByAttribute;
	}

	@Override
	public String createTable()

	{
		super.createTable();
		StringBuilder builder = new StringBuilder();

		MavenExecutionPlanInfo info = getExecutionPlanInfo();

		if ( info.getMavenExecutionsInfo() != null && !info.getMavenExecutionsInfo().isEmpty() ) {

			builder.append( headerRows() );

			String oldGroup = "";
			boolean columnGroupByEnabled = getOrderByColumn().isGroupByEnabled();

			Collection<MavenExecutionInfo> mavenExecutionsInfo = info.getMavenExecutionsInfo();
			for ( MavenExecutionInfo mavenExecutionInfo : mavenExecutionsInfo ) {

				String currentGroup = isGroupByColumnSelected()
						? String.valueOf( mavenExecutionInfo.getValueOrEmpty( getOrderByColumn() ) )
						: "";

				if ( columnGroupByEnabled && !currentGroup.equals( oldGroup ) ) {
					builder.append( newLineSeparator() );

					builder.append( newLineSeparator() )
							.append( titleLine( getOrderByColumn().getDescription() + ": " + currentGroup ) );

				}

				builder.append( newLineSeparator() );
				builder.append( tableRow( mavenExecutionInfo ) );

				oldGroup = currentGroup;
			}
		}

		return builder.toString();

	}

	@Override
	protected Set<MavenExecutionAttribute> createColumns() {
		Set<MavenExecutionAttribute> result = null;

		if ( isGroupByColumnSelected() ) {
			result = MavenExecutionAttribute.complementOf( getOrderByColumn() );
		}
		else {

			result = EnumSet.allOf( MavenExecutionAttribute.class );

		}
		return result;
	}

	public MavenExecutionAttribute getOrderByColumn() {

		return orderByColumn;
	}

	public boolean isGroupByColumnSelected() {

		return isGroupByAttribute() && getOrderByColumn() != null && getOrderByColumn().isGroupByEnabled();
	}

	protected String headerRows() {
		StringBuilder output = new StringBuilder().append( headerRowSeparator() )

				.append( newLineSeparator() ).append( headerTitle() )

				.append( headerRowSeparator() );

		return output.toString();
	}

	@Override
	protected String headerTitle() {

		Object[] columnTitle = new String[getColumns().size()];

		int count = 0;
		for ( MavenExecutionAttribute mavenExecutionAttribute : getColumns() ) {
			columnTitle[count] = getHeaderTitle( mavenExecutionAttribute );
			count++;
		}

		return String.format( getRowFormat(), columnTitle );
	}

	private String headerRowSeparator() {
		StringBuilder output = new StringBuilder().append( newLineSeparator() ).append( headerSeparator() );

		return output.toString();
	}

	private String headerSeparator() {
		return Strings.repeat( "-", getRowLength() );
	}

	private void setRowLength( int rowLength ) {
		this.rowLength = rowLength;
	}

	public int getRowLength() {
		return rowLength;
	}

	protected String titleLine( String key ) {
		return MessageUtils.buffer().strong( key ) + " " + Strings.repeat( ".", getRowLength() - key.length() );
	}
}
