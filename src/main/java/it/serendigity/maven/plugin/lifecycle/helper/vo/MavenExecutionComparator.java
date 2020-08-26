package it.serendigity.maven.plugin.lifecycle.helper.vo;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * Order a {@link MavenExecutionInfo} by {@link MavenExecutionAttribute}
 *
 */
public class MavenExecutionComparator implements Comparator<MavenExecutionInfo> {

	private final MavenExecutionAttribute orderAttribute;

	public MavenExecutionComparator( MavenExecutionAttribute orderAttribute ) {
		this.orderAttribute = orderAttribute;
	}

	@Override
	public int compare( MavenExecutionInfo o1, MavenExecutionInfo o2 ) {

		MavenExecutionAttribute orderAttr = getOrderAttribute();

		CompareToBuilder builder = new CompareToBuilder();

		Set<MavenExecutionAttribute> excludes = EnumSet.noneOf( MavenExecutionAttribute.class );

		// Order by orderAttribute
		if ( orderAttr != null ) {
			builder.append( o1.getValue( orderAttr ), o2.getValue( orderAttr ) );
			excludes.add( orderAttr );
		}

		// Always second order with ExecutionOrder to preserve execution plan order
		if ( orderAttr == null || orderAttr != MavenExecutionAttribute.PLAN_ORDER ) {
			builder.append( o1.getExecutionOrder(), o2.getExecutionOrder() );
			excludes.add( MavenExecutionAttribute.PLAN_ORDER );
		}
		MavenExecutionAttribute[] excludesArray = excludes.toArray( new MavenExecutionAttribute[excludes.size()] );

		// Add other fields
		Set<MavenExecutionAttribute> complementOf = MavenExecutionAttribute.complementOf( excludesArray );

		if ( complementOf != null && !complementOf.isEmpty() ) {
			complementOf.stream().forEach( p -> builder.append( o1.getValue( p ), o2.getValue( p ) ) );

		}

		return builder.toComparison();

	}

	protected MavenExecutionAttribute getOrderAttribute() {
		return orderAttribute;
	}

}
