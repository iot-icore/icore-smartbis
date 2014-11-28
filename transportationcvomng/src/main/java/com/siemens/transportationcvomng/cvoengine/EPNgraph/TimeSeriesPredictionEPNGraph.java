package com.siemens.transportationcvomng.cvoengine.EPNgraph;

import org.kohsuke.graphviz.Graph;
import org.kohsuke.graphviz.Node;

public class TimeSeriesPredictionEPNGraph {

	private String packageID;
	private Node containerTemperatureSensorVO1;
	private Node containerTemperatureSensorVO2;
	private Node containerTemperatureSensorVO3;
	private Node humiditySensorVO;
	private Node hvacActuatorVO;
	private Node currentClampSensorVO;
	private Node externalContainerTemperatureSensorVO;
	private Node parcelTemperatureSensorVO;

	public TimeSeriesPredictionEPNGraph(String packageID,
			Node containerTemperatureSensorVO1,
			Node containerTemperatureSensorVO2,
			Node containerTemperatureSensorVO3, Node humiditySensorVO,
			Node hvacActuatorVO, Node currentClampSensorVO,
			Node externalContainerTemperatureSensorVO,
			Node parcelTemperatureSensorVO) {
		super();
		this.packageID = packageID;
		this.containerTemperatureSensorVO1 = containerTemperatureSensorVO1;
		this.containerTemperatureSensorVO2 = containerTemperatureSensorVO2;
		this.containerTemperatureSensorVO3 = containerTemperatureSensorVO3;
		this.humiditySensorVO = humiditySensorVO;
		this.hvacActuatorVO = hvacActuatorVO;
		this.currentClampSensorVO = currentClampSensorVO;
		this.externalContainerTemperatureSensorVO = externalContainerTemperatureSensorVO;
		this.parcelTemperatureSensorVO = parcelTemperatureSensorVO;
	}

	public Graph addEPNNodesToGraph(Graph graphToBeExtended) {

		// generate truck sensor joined event

		Node temperatureProcesingNodeC1 = new Node();
		temperatureProcesingNodeC1.id("TEC1_" + packageID);
		graphToBeExtended.node(temperatureProcesingNodeC1);

		Node temperatureProcesingNodeC2 = new Node();
		temperatureProcesingNodeC2.id("TEC2_" + packageID);
		graphToBeExtended.node(temperatureProcesingNodeC2);

		Node temperatureProcesingNodeC3 = new Node();
		temperatureProcesingNodeC3.id("TEC3_" + packageID);
		graphToBeExtended.node(temperatureProcesingNodeC3);

		Node temperatureProcesingNodeExt = new Node();
		temperatureProcesingNodeExt.id("TECEXT_" + packageID);
		graphToBeExtended.node(temperatureProcesingNodeExt);

		Node temperatureProcesingNodePack = new Node();
		temperatureProcesingNodePack.id("TEPACK_" + packageID);
		graphToBeExtended.node(temperatureProcesingNodePack);

		Node acUnitProcesingNode = new Node();
		acUnitProcesingNode.id("ACUEC_" + packageID);
		graphToBeExtended.node(acUnitProcesingNode);

		Node currentClampProcesingNode = new Node();
		currentClampProcesingNode.id("CCLEC_" + packageID);
		graphToBeExtended.node(currentClampProcesingNode);

		Node truckSensorJointProcesingNode = new Node();
		truckSensorJointProcesingNode.id("TSJE_" + packageID);
		graphToBeExtended.node(truckSensorJointProcesingNode);

		Node truckSensorJointEventVONode = new Node();
		truckSensorJointEventVONode.id("TSJES_" + packageID);
		truckSensorJointEventVONode.attr("color", "green");
		graphToBeExtended.node(truckSensorJointEventVONode);

		graphToBeExtended.edge(containerTemperatureSensorVO1,
				temperatureProcesingNodeC1);
		graphToBeExtended.edge(containerTemperatureSensorVO2,
				temperatureProcesingNodeC2);
		graphToBeExtended.edge(containerTemperatureSensorVO3,
				temperatureProcesingNodeC3);
		graphToBeExtended.edge(externalContainerTemperatureSensorVO,
				temperatureProcesingNodeExt);
		graphToBeExtended.edge(parcelTemperatureSensorVO,
				temperatureProcesingNodePack);
		graphToBeExtended.edge(hvacActuatorVO, acUnitProcesingNode);
		graphToBeExtended.edge(currentClampSensorVO, currentClampProcesingNode);
		graphToBeExtended.edge(temperatureProcesingNodeC1,
				truckSensorJointProcesingNode);
		graphToBeExtended.edge(temperatureProcesingNodeC2,
				truckSensorJointProcesingNode);
		graphToBeExtended.edge(temperatureProcesingNodeC3,
				truckSensorJointProcesingNode);
		graphToBeExtended.edge(temperatureProcesingNodeExt,
				truckSensorJointProcesingNode);
		graphToBeExtended.edge(temperatureProcesingNodePack,
				truckSensorJointProcesingNode);
		graphToBeExtended.edge(acUnitProcesingNode,
				truckSensorJointProcesingNode);
		graphToBeExtended.edge(currentClampProcesingNode,
				truckSensorJointProcesingNode);
		graphToBeExtended.edge(truckSensorJointProcesingNode,
				truckSensorJointEventVONode);

		// generate predictions

		Node predictionsNode = new Node();
		predictionsNode.id("PD_" + packageID);
		graphToBeExtended.node(predictionsNode);

		Node predictionsVONode = new Node();
		predictionsVONode.id("PS_" + packageID);
		predictionsVONode.attr("color", "green");
		graphToBeExtended.node(predictionsVONode);

		graphToBeExtended.edge(truckSensorJointProcesingNode, predictionsNode);
		graphToBeExtended.edge(predictionsNode, predictionsVONode);

		// generate validation nodes

		Node predictionsWindowNode = new Node();
		predictionsWindowNode.id("PW_" + packageID);
		graphToBeExtended.node(predictionsWindowNode);

		// 10min
		Node acusc10Node = new Node();
		acusc10Node.id("ACUSC10_" + packageID);
		graphToBeExtended.node(acusc10Node);

		Node tpv10Node = new Node();
		tpv10Node.id("TPV10_" + packageID);
		graphToBeExtended.node(tpv10Node);

		Node tpv10VONode = new Node();
		tpv10VONode.id("TPV10VO_" + packageID);
		tpv10VONode.attr("color", "green");
		graphToBeExtended.node(tpv10VONode);


		graphToBeExtended.edge(predictionsWindowNode, tpv10Node);
		graphToBeExtended.edge(temperatureProcesingNodePack, tpv10Node);
		graphToBeExtended.edge(hvacActuatorVO, acusc10Node);
		graphToBeExtended.edge(acusc10Node, tpv10Node);
		graphToBeExtended.edge(tpv10Node, tpv10VONode);

		// 60min
		Node acusc60Node = new Node();
		acusc60Node.id("ACUSC60_" + packageID);
		graphToBeExtended.node(acusc60Node);

		Node tpv60Node = new Node();
		tpv60Node.id("TPV60_" + packageID);
		graphToBeExtended.node(tpv60Node);

		Node tpv60VONode = new Node();
		tpv60VONode.id("TPV60VO_" + packageID);
		tpv60VONode.attr("color", "green");
		graphToBeExtended.node(tpv60VONode);


		graphToBeExtended.edge(predictionsWindowNode, tpv60Node);
		graphToBeExtended.edge(temperatureProcesingNodePack, tpv60Node);
		graphToBeExtended.edge(hvacActuatorVO, acusc60Node);
		graphToBeExtended.edge(acusc60Node, tpv60Node);
		graphToBeExtended.edge(tpv60Node, tpv60VONode);

		// 120min
		Node acusc120Node = new Node();
		acusc120Node.id("ACUSC120_" + packageID);
		graphToBeExtended.node(acusc120Node);

		Node tpv120Node = new Node();
		tpv120Node.id("TPV120_" + packageID);
		graphToBeExtended.node(tpv120Node);

		Node tpv120VONode = new Node();
		tpv120VONode.id("TPV120VO_" + packageID);
		tpv120VONode.attr("color", "green");
		graphToBeExtended.node(tpv120VONode);

		graphToBeExtended.edge(predictionsNode, predictionsWindowNode);
		graphToBeExtended.edge(predictionsWindowNode, tpv120Node);
		graphToBeExtended.edge(temperatureProcesingNodePack, tpv120Node);
		graphToBeExtended.edge(hvacActuatorVO, acusc120Node);
		graphToBeExtended.edge(acusc120Node, tpv120Node);
		graphToBeExtended.edge(tpv120Node, tpv120VONode);

		return graphToBeExtended;

	}

}
