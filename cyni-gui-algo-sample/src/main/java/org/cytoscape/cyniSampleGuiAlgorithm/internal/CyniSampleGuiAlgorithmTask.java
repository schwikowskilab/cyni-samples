/*
 * #%L
 * Cyni Sample Gui implementaion
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2006 - 2013 The Cytoscape Consortium
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package fr.systemsbiology.cyniSampleGuiAlgorithm.internal;




import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyRow;
import fr.systemsbiology.cyni.*;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.view.vizmap.VisualMappingManager;





/**
 * The CyniSampleAlgorithmTask provides a simple example on how to create a cyni task
 */
public class CyniSampleGuiAlgorithmTask extends AbstractCyniTask {
	
	private final CyTable mytable;
	private final List<String> attributeArray;
	private CyLayoutAlgorithmManager layoutManager;
	private CyniNetworkUtils netUtils;
	private double param1;
	private double param2;
	

	/**
	 * Creates a new object.
	 */
	public CyniSampleGuiAlgorithmTask(final String name, final CyniSampleGuiAlgorithmContext context, CyNetworkFactory networkFactory, CyNetworkViewFactory networkViewFactory,
			CyNetworkManager networkManager,CyNetworkTableManager netTableMgr, CyRootNetworkManager rootNetMgr, VisualMappingManager vmMgr,
			CyNetworkViewManager networkViewManager,CyLayoutAlgorithmManager layoutManager, 
			CyCyniMetricsManager metricsManager, CyTable selectedTable)
	{
		super(name, context,networkFactory,networkViewFactory,networkManager, networkViewManager,netTableMgr,rootNetMgr, vmMgr);
		//Get Context data
		this.param1 = context.getParam1();
		this.param2 = context.getParam2();
		this.mytable = selectedTable;
		this.attributeArray = null;
		this.layoutManager = layoutManager;
		this.netUtils = new CyniNetworkUtils(networkViewFactory,networkManager,networkViewManager,netTableMgr,rootNetMgr,vmMgr);
		
	}

	/**
	 *  Perform actualtask.
	 */
	@Override
	final protected void doCyniTask(final TaskMonitor taskMonitor) {
		
		Double progress = 0.0d;
		CyNetwork networkSelected = null;
		String networkName;
		CyLayoutAlgorithm layout;
		CyNetworkView newNetworkView ;
   
        //step = 1.0 /  attributeArray.size();
        
        taskMonitor.setStatusMessage("Algorithm running ...");
		taskMonitor.setProgress(progress);
		
		//Create new network
		CyNetwork newNetwork = netFactory.createNetwork();
		
		
		//Check if a network is associated to the selected table
		networkSelected = netUtils.getNetworkAssociatedToTable(mytable);
		
		// Create the CyniTable
		//CyniTable data = new CyniTable(mytable,attributeArray.toArray(new String[0]), false, false, selectedOnly);
		
		
		//Set the name of the network, another name could be chosen
		networkName = "Induction " + newNetwork.getSUID();
		if (newNetwork != null && networkName != null) {
			CyRow netRow = newNetwork.getRow(newNetwork);
			netRow.set(CyNetwork.NAME, networkName);
		}
		
		
		/*****************************************************/
		//
		// Add the different nodes and edges according to the table data
	    //
		//
		/*****************************************************/
		
		//Display the new network
		if (!cancelled)
		{
			newNetworkView = netUtils.displayNewNetwork(newNetwork, networkSelected,false);
			taskMonitor.setProgress(1.0d);
			layout = layoutManager.getDefaultLayout();
			Object context = layout.getDefaultLayoutContext();
			insertTasksAfterCurrentTask(layout.createTaskIterator(newNetworkView, context, CyLayoutAlgorithm.ALL_NODE_VIEWS,""));
		}
		
		taskMonitor.setProgress(1.0d);
	}
	
	
	
	
}
