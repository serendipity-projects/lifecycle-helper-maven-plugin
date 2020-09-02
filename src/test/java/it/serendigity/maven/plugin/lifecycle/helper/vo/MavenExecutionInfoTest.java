package it.serendigity.maven.plugin.lifecycle.helper.vo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.base.Strings;

import nl.jqno.equalsverifier.EqualsVerifier;

public class MavenExecutionInfoTest {

	@Test
	void testSetValue_GetValue() throws Exception {
		MavenExecutionInfo info = new MavenExecutionInfo();
		// Set value
		MavenExecutionAttribute[] values = MavenExecutionAttribute.values();
		int count = 0;
		for ( MavenExecutionAttribute mavenExecutionAttribute : values ) {
			info.setValue( mavenExecutionAttribute, String.valueOf( count++ ) );
		}

		// Get value
		count = 0;
		for ( MavenExecutionAttribute mavenExecutionAttribute : values ) {
			assertThat( String.valueOf( info.getValue( mavenExecutionAttribute ) ) ).isEqualTo( String.valueOf( count++ ) );
		}

	}

	@Test
	void testGetStringLength() throws Exception {
		MavenExecutionInfo info = new MavenExecutionInfo();
		// Set value
		MavenExecutionAttribute[] values = MavenExecutionAttribute.values();
		int count = 1;
		for ( MavenExecutionAttribute mavenExecutionAttribute : values ) {
			info.setValue( mavenExecutionAttribute, String.valueOf( Strings.repeat( "1", count++ ) ) );
		}

		// Get string length
		count = 1;
		for ( MavenExecutionAttribute mavenExecutionAttribute : values ) {
			assertThat( info.getStringLength( mavenExecutionAttribute ) ).isEqualTo( count++ );
		}

	}

	@Test
	void testEquals() throws Exception {
		EqualsVerifier.simple().forClass( MavenExecutionInfo.class ).verify();
	}

	@Test
	void testGetValueOrEmpty() throws Exception {
		MavenExecutionInfo info = new MavenExecutionInfo();
		// Set value (exclude numeric value)
		 Set<MavenExecutionAttribute> complementOf = MavenExecutionAttribute.complementOf( MavenExecutionAttribute.PLAN_ORDER);

		for ( MavenExecutionAttribute mavenExecutionAttribute : complementOf ) {
			info.setValue( mavenExecutionAttribute, null );
		}

		for ( MavenExecutionAttribute mavenExecutionAttribute : complementOf ) {
			assertThat( String.valueOf( info.getValueOrEmpty( mavenExecutionAttribute ) ) ).isEmpty();
		}
	}

}
