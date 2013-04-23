package org.cytoscape.cyniSampleGuiAlgorithm.internal;

import org.cytoscape.application.swing.CySwingApplication;



import org.cytoscape.application.swing.CyAction;
import org.cytoscape.cyni.*;

import org.osgi.framework.BundleContext;

import org.cytoscape.service.util.AbstractCyActivator;

import java.util.Properties;


public class CyActivator extends AbstractCyActivator {
	public CyActivator() {
		super();
	}


	public void start(BundleContext bc) {

		//Define new Cyni Algorithm
		CyniSampleGuiAlgorithm test = new CyniSampleGuiAlgorithm();
		//Register new Cyni Algorithm
		registerService(bc,test,CyCyniAlgorithm.class, new Properties());

		

	}
}

