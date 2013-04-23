package org.cytoscape.cyniSampleGuiAlgorithm.internal;

import java.io.IOException;
import java.util.List;

import org.cytoscape.cyni.*;
import org.cytoscape.work.util.*;
import javax.swing.JPanel;

import org.cytoscape.work.Tunable;
import org.cytoscape.work.TunableValidator;

public class CyniSampleGuiAlgorithmContext extends CyniAlgorithmContext  {
	
	private CyniSampleGui gui;

	public CyniSampleGuiAlgorithmContext( ) {
		super(true);
		
		gui = new CyniSampleGui();
	}
	public boolean contextHasOwnSwingComponent()
	{
		return true;
	}

	public JPanel getContextSwingPanel()
	{
		return gui;
	}
}
