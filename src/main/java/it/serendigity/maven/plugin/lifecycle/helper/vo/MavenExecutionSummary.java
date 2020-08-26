package it.serendigity.maven.plugin.lifecycle.helper.vo;

import java.util.HashMap;
import java.util.Map;

public class MavenExecutionSummary {

	private Map<String, Integer> attributeMaxStringLength;

	public MavenExecutionSummary() {
		attributeMaxStringLength = new HashMap<>();
	}

	public void updateSummary( MavenExecutionInfo executionInfo ) {

		updateMaxStringLength( executionInfo );
	}

	protected void updateMaxStringLength( MavenExecutionInfo executionInfo ) {

		MavenExecutionAttribute[] values = MavenExecutionAttribute.values();

		for ( MavenExecutionAttribute mavenExecutionAttribute : values ) {

			String key = mavenExecutionAttribute.getCode();

			int newStringLength = executionInfo.getStringLength( mavenExecutionAttribute );
			Integer oldMaxStringLength = attributeMaxStringLength.get( key );

			if ( oldMaxStringLength == null || oldMaxStringLength.compareTo( newStringLength ) < 0 ) {
				attributeMaxStringLength.put( key, newStringLength );
			}

		}

	}

	public int getMaxStringLength( MavenExecutionAttribute attribute ) {
		

		return attributeMaxStringLength.get( attribute.getCode() );
	
	}
}
