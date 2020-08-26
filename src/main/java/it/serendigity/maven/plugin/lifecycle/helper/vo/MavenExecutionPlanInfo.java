package it.serendigity.maven.plugin.lifecycle.helper.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MavenExecutionPlanInfo {

	private List<MavenExecutionInfo> mavenExecutionsInfo;
	private MavenExecutionSummary summary;
	private boolean calculateSummary;


	public MavenExecutionPlanInfo(boolean calculateSummary) {
		this.calculateSummary = calculateSummary;
		
	}

	public List<MavenExecutionInfo> getMavenExecutionsInfo() {
		return mavenExecutionsInfo;
	}

	public void addMavenExecutionInfo(MavenExecutionInfo executionInfo) {
		if (mavenExecutionsInfo == null) {
			mavenExecutionsInfo = new ArrayList<>();

		}

		mavenExecutionsInfo.add(executionInfo);

		if (calculateSummary) {
			if (summary == null) {
				summary = new MavenExecutionSummary();
			}

			summary.updateSummary(executionInfo);

		}

	}

	public MavenExecutionSummary getSummary() {
		return summary;
	}

	public void setSummary(MavenExecutionSummary summary) {
		this.summary = summary;
	}

	

	public void sort(MavenExecutionAttribute orderByAttribute) {

		if (orderByAttribute != null && getMavenExecutionsInfo() != null) {
			Collections.sort(getMavenExecutionsInfo(), new MavenExecutionComparator(orderByAttribute));
		}

	}

}
