package it.serendigity.maven.plugin.lifecycle.helper.vo;

import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.maven.lifecycle.DefaultLifecycles;
import org.apache.maven.plugin.MojoExecution;

import it.serendigity.maven.plugin.lifecycle.helper.utils.MavenUtils;

public final class MavenExecutionInfo {

	private int executionOrder;
	private String lifecycle;
	private String phase;

	private String pluginArtifactId;
	private String pluginVersion;
	private String pluginExecutionId;
	private String pluginGoal;

	public MavenExecutionInfo() {
		super();
	}

	public int getExecutionOrder() {
		return executionOrder;
	}

	public String getLifecycle() {
		return lifecycle;
	}

	public String getPhase() {
		return phase;
	}

	public String getPluginArtifactId() {
		return pluginArtifactId;
	}

	public String getPluginVersion() {
		return pluginVersion;
	}

	public String getPluginExecutionId() {
		return pluginExecutionId;
	}

	public String getPluginGoal() {
		return pluginGoal;
	}

	public static MavenExecutionInfo from( int order, MojoExecution mojoExecution, DefaultLifecycles defaultLifecycles ) {

		return MavenExecutionInfo.builder()
				.withExecutionOrder( order )
				.withLifecycle( MavenUtils.retrieveLifecycle( mojoExecution, defaultLifecycles ) )
				.withPhase( mojoExecution.getLifecyclePhase() )
				.withPluginArtifactId( mojoExecution.getArtifactId() )
				.withPluginVersion( mojoExecution.getVersion() )
				.withPluginExecutionId( mojoExecution.getExecutionId() )
				.withPluginGoal( mojoExecution.getGoal() )

				.build();

	}

	@Generated("SparkTools")
	private MavenExecutionInfo( Builder builder ) {
		this.lifecycle = builder.lifecycle;
		this.phase = builder.phase;
		this.pluginArtifactId = builder.pluginArtifactId;
		this.pluginVersion = builder.pluginVersion;
		this.pluginExecutionId = builder.pluginExecutionId;
		this.pluginGoal = builder.pluginGoal;
		this.executionOrder = builder.executionOrder;

	}

	/**
	 * Creates builder to build {@link MavenExecutionInfo}.
	 *
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link MavenExecutionInfo}.
	 */
	@Generated("SparkTools")
	public static final class Builder {

		private int executionOrder;
		private String lifecycle;
		private String phase;
		private String pluginArtifactId;
		private String pluginVersion;
		private String pluginExecutionId;
		private String pluginGoal;

		private Builder() {
		}

		public Builder withExecutionOrder( int executionOrder ) {
			this.executionOrder = executionOrder;
			return this;
		}

		public Builder withLifecycle( String lifecycle ) {
			this.lifecycle = lifecycle;
			return this;
		}

		public Builder withPhase( String phase ) {
			this.phase = phase;
			return this;
		}

		public Builder withPluginArtifactId( String pluginArtifactId ) {
			this.pluginArtifactId = pluginArtifactId;
			return this;
		}

		public Builder withPluginVersion( String pluginVersion ) {
			this.pluginVersion = pluginVersion;
			return this;
		}

		public Builder withPluginExecutionId( String pluginExecutionId ) {
			this.pluginExecutionId = pluginExecutionId;
			return this;
		}

		public Builder withPluginGoal( String pluginGoal ) {
			this.pluginGoal = pluginGoal;
			return this;
		}

		public MavenExecutionInfo build() {
			return new MavenExecutionInfo( this );
		}
	}

	/**
	 * Retrieve value of the attribute {@code mavenExecutionAttribute}.
	 * The null value are converted in empty value
	 *
	 * @param mavenExecutionAttribute the attribute
	 * @return the value of the attribute or empty string otherwise
	 */
	public Object getValueOrEmpty( MavenExecutionAttribute mavenExecutionAttribute ) {
		return Objects.toString( getValue( mavenExecutionAttribute ), "" );
	}

	/**
	 * Retrieve value of the attribute {@code mavenExecutionAttribute}
	 *
	 * @param mavenExecutionAttribute the attribute
	 * @return the value of the attribute or empty string otherwise
	 */
	public Object getValue( MavenExecutionAttribute mavenExecutionAttribute ) {

		Object result = null;

		switch ( mavenExecutionAttribute ) {
			case PLAN_ORDER:
				result = getExecutionOrder();
				break;
			case LIFECYCLE:
				result = getLifecycle();
				break;

			case PHASE:
				result = getPhase();
				break;
			case PLUGIN:
				result = getPluginArtifactId();
				break;

			case PLUGIN_VERSION:
				result = getPluginVersion();
				break;

			case PLUGIN_EXECUTION_ID:
				result = getPluginExecutionId();
				break;

			case PLUGIN_GOAL:
				result = getPluginGoal();
				break;
			default:
				break;
		}

		return result;
	}

	public int getStringLength( MavenExecutionAttribute mavenExecutionAttribute ) {

		Object value = getValue( mavenExecutionAttribute );

		int result = 0;
		if ( value != null ) {
			result = String.valueOf( value ).length();
		}

		return result;
	}

	public void setValue( MavenExecutionAttribute mavenExecutionAttribute, Object value ) {
		if ( value != null ) {
			String s = String.valueOf( value );
			switch ( mavenExecutionAttribute ) {
				case PLAN_ORDER:
					setExecutionOrder( Integer.parseInt( s ) );
					break;

				case LIFECYCLE:
					setLifecycle( s );
					break;

				case PHASE:
					setPhase( s );
					break;
				case PLUGIN:
					setPluginArtifactId( s );
					break;

				case PLUGIN_VERSION:
					setPluginVersion( s );
					break;

				case PLUGIN_EXECUTION_ID:
					setPluginExecutionId( s );
					break;

				case PLUGIN_GOAL:
					setPluginGoal( s );
					break;

				default:
					break;
			}
		}
	}

	public void setExecutionOrder( int executionOrder ) {
		this.executionOrder = executionOrder;
	}

	public void setLifecycle( String lifecycle ) {
		this.lifecycle = lifecycle;
	}

	public void setPhase( String phase ) {
		this.phase = phase;
	}

	public void setPluginArtifactId( String pluginArtifactId ) {
		this.pluginArtifactId = pluginArtifactId;
	}

	public void setPluginVersion( String pluginVersion ) {
		this.pluginVersion = pluginVersion;
	}

	public void setPluginExecutionId( String pluginExecutionId ) {
		this.pluginExecutionId = pluginExecutionId;
	}

	public void setPluginGoal( String pluginGoal ) {
		this.pluginGoal = pluginGoal;
	}

	@Override
	public String toString() {
		return "MavenExecutionInfo [executionOrder=" + executionOrder + ", lifecycle=" + lifecycle + ", phase=" + phase
				+ ", pluginArtifactId=" + pluginArtifactId + ", pluginVersion=" + pluginVersion + ", pluginExecutionId="
				+ pluginExecutionId + ", pluginGoal=" + pluginGoal + "]";
	}

	@Override
	public boolean equals( Object o ) {

		if ( !( o instanceof MavenExecutionInfo ) ) {
			return false;
		}

		MavenExecutionInfo other = (MavenExecutionInfo) o;

		return new EqualsBuilder()
				.append( getLifecycle(), other.getLifecycle() )
				.append( getExecutionOrder(), other.getExecutionOrder() )
				.append( getPhase(), other.getPhase() )
				.append( getPluginArtifactId(), other.getPluginArtifactId() )
				.append( getPluginVersion(), other.getPluginVersion() )
				.append( getPluginExecutionId(), other.getPluginExecutionId() )
				.append( getPluginGoal(), other.getPluginGoal() )
				.isEquals();

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append( getLifecycle() )
				.append( getExecutionOrder() )
				.append( getPhase() )
				.append( getPluginArtifactId() )
				.append( getPluginVersion() )
				.append( getPluginExecutionId() )
				.append( getPluginGoal() )
				.toHashCode()

		;
	}

}
