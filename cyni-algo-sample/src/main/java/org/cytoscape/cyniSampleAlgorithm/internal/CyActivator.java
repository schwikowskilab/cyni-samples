package fr.systemsbiology.cyniSampleAlgorithm.internal;

import org.cytoscape.application.swing.CySwingApplication;



import org.cytoscape.application.swing.CyAction;
import fr.systemsbiology.cyni.*;

import org.osgi.framework.BundleContext;

import org.cytoscape.service.util.AbstractCyActivator;

import java.util.Properties;


public class CyActivator extends AbstractCyActivator {
	public CyActivator() {
		super();
	}


	public void start(BundleContext bc) {

		//Define new Cyni Algorithm
		CyniSampleAlgorithm test = new CyniSampleAlgorithm();
		//Register new Cyni Algorithm
		registerService(bc,test,CyCyniAlgorithm.class, new Properties());

		

	}
}

