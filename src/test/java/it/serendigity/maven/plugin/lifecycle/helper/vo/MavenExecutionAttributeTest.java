package it.serendigity.maven.plugin.lifecycle.helper.vo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

 class MavenExecutionAttributeTest {

	@Test
	void testFromCode() throws Exception {
		MavenExecutionAttribute[] values = MavenExecutionAttribute.values();

		for ( MavenExecutionAttribute mavenExecutionAttribute : values ) {
			MavenExecutionAttribute result = MavenExecutionAttribute.fromCode( mavenExecutionAttribute.getCode() );

			assertThat( result ).isEqualTo( mavenExecutionAttribute );
		}
	}

	@Test
	void testComplementOf() throws Exception {

		MavenExecutionAttribute[] values = MavenExecutionAttribute.values();

		for ( MavenExecutionAttribute exclude : values ) {
			Set<MavenExecutionAttribute> complementOf = MavenExecutionAttribute.complementOf( exclude );
			assertThat( complementOf.contains( exclude ) ).isFalse();
		}

	}

	@Test
	void testComplementOf_empty() throws Exception {

		EnumSet<MavenExecutionAttribute> allOf = EnumSet.allOf( MavenExecutionAttribute.class );

		Set<MavenExecutionAttribute> complementOf = MavenExecutionAttribute.complementOf();
		assertThat( complementOf ).containsAll( allOf );

	}

	@Test
	void testRetrieveGroupByEnabled() throws Exception {
		Set<MavenExecutionAttribute> retrieveGroupByEnabled = MavenExecutionAttribute.retrieveGroupByEnabled();

		for ( MavenExecutionAttribute v : retrieveGroupByEnabled ) {

			assertThat( v.isGroupByEnabled() ).isTrue();
		}
	}

	@Test
	void testRetrieveGroupByEnabledCode() throws Exception {
		List<String> codes = MavenExecutionAttribute.retrieveGroupByEnabledCode();

		for ( String code : codes ) {
			MavenExecutionAttribute fromCode = MavenExecutionAttribute.fromCode( code );
			assertThat(fromCode).isNotNull();
			assertThat( fromCode.isGroupByEnabled() ).isTrue();
		}
	}

}
