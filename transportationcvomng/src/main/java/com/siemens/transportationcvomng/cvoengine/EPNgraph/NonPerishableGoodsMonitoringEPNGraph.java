package com.siemens.transportationcvomng.cvoengine.EPNgraph;

import org.kohsuke.graphviz.Graph;
import org.kohsuke.graphviz.Node;

public class NonPerishableGoodsMonitoringEPNGraph {

	private String packageID;
	private Node humidityVO;
	private Node temperatureVO;

	public NonPerishableGoodsMonitoringEPNGraph(String packageID,
			Node temperatureVO, Node humidityVO) {
		super();
		this.packageID = packageID;
		this.humidityVO = humidityVO;
		this.temperatureVO = temperatureVO;
	}

	public Graph addEPNNodesToGraph(Graph graphToBeExtended) {

		Node temperatureProcesingNode = new Node();
		temperatureProcesingNode.id("TPGM_" + packageID);
		graphToBeExtended.node(temperatureProcesingNode);

		Node humidityProcesingNode = new Node();
		humidityProcesingNode.id("HPGM_" + packageID);
		graphToBeExtended.node(humidityProcesingNode);

		Node temperatureAlarmNode = new Node();
		temperatureAlarmNode.id("TAL_" + packageID);
		temperatureAlarmNode.attr("color", "green");
		graphToBeExtended.node(temperatureAlarmNode);

		Node humidityAlarmNode = new Node();
		humidityAlarmNode.id("HAL_" + packageID);
		humidityAlarmNode.attr("color", "green");
		graphToBeExtended.node(humidityAlarmNode);

		graphToBeExtended.edge(temperatureVO, temperatureProcesingNode);
		graphToBeExtended.edge(humidityVO, humidityProcesingNode);
		graphToBeExtended.edge(temperatureProcesingNode, temperatureAlarmNode);
		graphToBeExtended.edge(humidityProcesingNode, humidityAlarmNode);

		return graphToBeExtended;
	}

}
