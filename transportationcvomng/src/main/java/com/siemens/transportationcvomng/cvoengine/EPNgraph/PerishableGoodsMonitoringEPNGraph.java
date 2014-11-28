package com.siemens.transportationcvomng.cvoengine.EPNgraph;

import org.kohsuke.graphviz.Graph;
import org.kohsuke.graphviz.Node;

public class PerishableGoodsMonitoringEPNGraph {

	private String packageID;
	private Node humidityVO;
	private Node temperatureVO;

	public PerishableGoodsMonitoringEPNGraph(String packageID,
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

		Node humidityAlarmNode = new Node();
		humidityAlarmNode.id("HAL_" + packageID);
		humidityAlarmNode.attr("color", "green");
		graphToBeExtended.node(humidityAlarmNode);

		Node pgstceewProcesingNode = new Node();
		pgstceewProcesingNode.id("PGSTCEEW_" + packageID);
		graphToBeExtended.node(pgstceewProcesingNode);

		Node pgstceedProcesingNode = new Node();
		pgstceedProcesingNode.id("PGSTCEED_" + packageID);
		graphToBeExtended.node(pgstceedProcesingNode);

		Node pgstcneedProcesingNode = new Node();
		pgstcneedProcesingNode.id("PGSTCNEED_" + packageID);
		graphToBeExtended.node(pgstcneedProcesingNode);

		Node dpgstceewProcesingNode = new Node();
		dpgstceewProcesingNode.id("D_PGSTCEEW_" + packageID);
		graphToBeExtended.node(dpgstceewProcesingNode);

		Node pgstceeProcesingNode = new Node();
		pgstceeProcesingNode.id("PGSTCEE_" + packageID);
		graphToBeExtended.node(pgstceedProcesingNode);

		Node softtemperatureAlarmNode = new Node();
		softtemperatureAlarmNode.id("S_TAL_" + packageID);
		softtemperatureAlarmNode.attr("color", "green");
		graphToBeExtended.node(softtemperatureAlarmNode);
		

		graphToBeExtended.edge(temperatureVO, temperatureProcesingNode);
		graphToBeExtended.edge(humidityVO, humidityProcesingNode);
		graphToBeExtended.edge(temperatureProcesingNode,
				temperatureAlarmNode);
		graphToBeExtended.edge(humidityProcesingNode, humidityAlarmNode);

		graphToBeExtended.edge(temperatureVO, pgstceedProcesingNode);
		graphToBeExtended.edge(temperatureVO, pgstcneedProcesingNode);
		graphToBeExtended.edge(pgstceedProcesingNode, pgstceewProcesingNode);
		graphToBeExtended.edge(pgstcneedProcesingNode, dpgstceewProcesingNode);
		graphToBeExtended.edge(dpgstceewProcesingNode, pgstceewProcesingNode);
		graphToBeExtended.edge(pgstceewProcesingNode, pgstceeProcesingNode);
		graphToBeExtended.edge(pgstceeProcesingNode, softtemperatureAlarmNode);
		
		
		
		return graphToBeExtended;
	}

}
