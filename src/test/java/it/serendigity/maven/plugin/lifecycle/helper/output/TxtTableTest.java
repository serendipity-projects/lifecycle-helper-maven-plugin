package it.serendigity.maven.plugin.lifecycle.helper.output;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionAttribute;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionInfo;
import it.serendigity.maven.plugin.lifecycle.helper.vo.MavenExecutionPlanInfo;

class TxtTableTest {

	@Test
	void testCreateTable_withGroupBy() throws Exception {
		List<String> lifecycles = Arrays.asList( "Lifecycle A", "Lifecycle B", "Lifecycle C large" );

		MavenExecutionAttribute orderAttr = MavenExecutionAttribute.LIFECYCLE;
		MavenExecutionPlanInfo planInfo = createPlanInfo( lifecycles, orderAttr );

		TxtTable table = new TxtTable( planInfo, orderAttr, true );
		String text = table.createTable();
		Set<MavenExecutionAttribute> columns = table.getColumns();
		assertThat( columns.contains( orderAttr ) ).isFalse();
		assertThat( text ).contains( lifecycles ).containsIgnoringCase( orderAttr.getShortDescription() + ":" );

	}

	@Test
	void testCreateTable_withoutGroupBy() throws Exception {
		List<String> lifecycles = Arrays.asList( "Lifecycle A", "Lifecycle B", "Lifecycle C large" );

		MavenExecutionAttribute orderAttr = MavenExecutionAttribute.PLAN_ORDER;
		MavenExecutionPlanInfo planInfo = createPlanInfo( lifecycles, orderAttr );

		TxtTable table = new TxtTable( planInfo, orderAttr, false );
		String text = table.createTable();
		assertThat( text ).contains( lifecycles );

	}

	private MavenExecutionPlanInfo createPlanInfo( List<String> lifecycles, MavenExecutionAttribute orderAttr ) {
		MavenExecutionPlanInfo planInfo = new MavenExecutionPlanInfo( true );

		MavenExecutionInfo info = MavenExecutionInfo.builder().withExecutionOrder( 1 ).withLifecycle( lifecycles.get( 0 ) )
				.withPhase( "cphase 1" ).withPluginArtifactId( "plugin artifact 1" )
				.withPluginExecutionId( "execution id 1 " ).withPluginGoal( "plugin goal 1" ).build();

		MavenExecutionInfo info2 = MavenExecutionInfo.builder().withExecutionOrder( 2 ).withLifecycle( lifecycles.get( 1 ) )
				.withPhase( "blarge phase xxxxxxxx" ).withPluginArtifactId( "plugin artifact 1" )
				.withPluginExecutionId( "execution id 1 " ).withPluginGoal( "large plugin goal 1" ).build();

		MavenExecutionInfo info3 = MavenExecutionInfo.builder().withExecutionOrder( 3 ).withLifecycle( lifecycles.get( 2 ) )
				.withPhase( "alarge phase 2" ).withPluginArtifactId( "plugin artifact 1" )
				.withPluginExecutionId( "large execution id 2 " ).withPluginGoal( "large plugin goal 2" ).build();

		MavenExecutionInfo info4 = MavenExecutionInfo.builder().withExecutionOrder( 3 ).withLifecycle( lifecycles.get( 2 ) )
				.withPhase( "alarge phase 4" ).withPluginArtifactId( "plugin artifact 4" )
				.withPluginExecutionId( "large execution id 4 " ).withPluginGoal( "large plugin goal 4" ).build();

		planInfo.addMavenExecutionInfo( info );
		planInfo.addMavenExecutionInfo( info3 );
		planInfo.addMavenExecutionInfo( info2 );
		planInfo.addMavenExecutionInfo( info4 );

		planInfo.sort( orderAttr );
		return planInfo;
	}

}
