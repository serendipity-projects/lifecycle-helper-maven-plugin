package it.serendigity.maven.plugin.lifecycle.helper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionInfo;

class AbstractLifecycleMojoTest {

	@Test
	void testValidatePlugin_novalid() throws Exception {
		String filterPlugin = "PluginNotFound";
		List<String> f = new ArrayList<String>();

		f.add( filterPlugin );

		String infoPLugin =  "other" ;


		MavenExecutionInfo info = MavenExecutionInfo.builder().withExecutionOrder( 1 )
				.withLifecycle( "l1" ).withPhase( "fase1" ).withPluginArtifactId(infoPLugin).build();

		boolean validatePlugin = AbstractLifecycleMojo.validatePlugin( info, f );

		assertThat( validatePlugin ).isFalse();
	}

	@Test
	void testValidatePlugin_valid() throws Exception {
		String filterPlugin = "a.valid.plugin";
		List<String> f = new ArrayList<String>();

		String infoPLugin = filterPlugin;

		f.add( filterPlugin );

		MavenExecutionInfo info = MavenExecutionInfo.builder().withExecutionOrder( 1 ).withLifecycle( "l1" ).withPhase( "fase1" )
				.withPluginArtifactId( infoPLugin ).build();

		boolean validatePlugin = AbstractLifecycleMojo.validatePlugin( info, f );

		assertThat( validatePlugin ).isTrue();
	}

	@Test
	void testValidatePlugin_multipleValue_oneValid() throws Exception {
		String filterPlugin = "a.valid.plugin";
		String filterPlugin2 = "other.plugin";

		String infoPLugin = filterPlugin;
		List<String> f = new ArrayList<String>();

		f.add( filterPlugin );
		f.add( filterPlugin2 );

		MavenExecutionInfo info = MavenExecutionInfo.builder().withExecutionOrder( 1 ).withLifecycle( "l1" ).withPhase( "fase1" )
				.withPluginArtifactId( infoPLugin ).build();

		boolean validatePlugin = AbstractLifecycleMojo.validatePlugin( info, f );

		assertThat( validatePlugin ).isTrue();
	}

	@Test
	void testValidatePlugin_multipleValue_allNoValid() throws Exception {
		String filterPlugin = "a.other.plugin";
		String filterPlugin2 = "other.plugin";
		List<String> f = new ArrayList<String>();

		f.add( filterPlugin );
		f.add( filterPlugin2 );

		String infoPLugin = "invalid.plugin";

		MavenExecutionInfo info = MavenExecutionInfo.builder().withExecutionOrder( 1 ).withLifecycle( "l1" ).withPhase( "fase1" )
				.withPluginArtifactId( infoPLugin  ).build();

		boolean validatePlugin = AbstractLifecycleMojo.validatePlugin( info, f );

		assertThat( validatePlugin ).isFalse();
	}

}
