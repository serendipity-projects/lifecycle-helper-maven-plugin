package it.serendigity.maven.plugin.lifecycle.helper.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Maven execution plan attribute
 */
public enum MavenExecutionAttribute {

	/** Execution plan order **/
	PLAN_ORDER("execution-plan-order-id", "Plan order", "Execution plan order id", false),
	/** Maven lifecycle **/
	LIFECYCLE("lifecycle", "Lifecycle"),
	/** Maven lifecycle phase **/
	PHASE("phase", "Lifecycle phase"),
	/** Maven plugin **/
	PLUGIN("plugin", "Plugin Artifact Id"),
	/** Maven plugin version **/
	PLUGIN_VERSION("plugin-version", "Plugin Version"),
	/** Maven plugin execution id **/
	PLUGIN_EXECUTION_ID("plugin-execution", "Plugin Execution Id"),
	/** Maven plugin goal **/
	PLUGIN_GOAL("goal", "Plugin goal"),;

	private final String code;
	private final String shortDescription;
	private final String description;
	private final boolean groupByEnabled;

	MavenExecutionAttribute( String code, String shortDescription, String description, boolean groupByEnabled ) {
		this.code = code;
		this.description = description;
		this.shortDescription = shortDescription;
		this.groupByEnabled = groupByEnabled;

	}

	private MavenExecutionAttribute( String code, String description, boolean groupByEnabled ) {
		this( code, description, description, groupByEnabled );

	}

	private MavenExecutionAttribute( String code, String description ) {
		this( code, description, true );
	}

	public static MavenExecutionAttribute fromCode( String name ) {
		String ignoreCase = name.toLowerCase();

		MavenExecutionAttribute[] values = values();

		for ( MavenExecutionAttribute mavenExecutionAttribute : values ) {
			if ( mavenExecutionAttribute.getCode().equals( ignoreCase ) ) {
				return mavenExecutionAttribute;
			}
		}
		return null;
	}

	public static Set<MavenExecutionAttribute> complementOf( MavenExecutionAttribute... excludes ) {
		EnumSet<MavenExecutionAttribute> excludesEnum = EnumSet.noneOf( MavenExecutionAttribute.class );

		if ( excludes != null ) {
			excludesEnum.addAll( Arrays.asList( excludes ) );
		}

		return EnumSet.complementOf( excludesEnum );
	}

	public static Set<MavenExecutionAttribute> retrieveGroupByEnabled() {
		EnumSet<MavenExecutionAttribute> result = EnumSet.noneOf( MavenExecutionAttribute.class );

		MavenExecutionAttribute[] values = MavenExecutionAttribute.values();

		for ( MavenExecutionAttribute v : values ) {
			if ( v.isGroupByEnabled() ) {
				result.add( v );
			}
		}

		return result;

	}

	public static List<String> retrieveGroupByEnabledCode() {
		List<String> result = new ArrayList<>();
		Set<MavenExecutionAttribute> retrieveGroupByEnabled = retrieveGroupByEnabled();
		retrieveGroupByEnabled.stream().forEach( p -> result.add( p.getCode() ) );

		return result;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public boolean isGroupByEnabled() {
		return groupByEnabled;
	}

}
