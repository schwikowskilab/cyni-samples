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


import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.beans.*; 


public class CyniSampleGui extends JPanel implements ActionListener,ChangeListener,PropertyChangeListener{
	
	
	JFormattedTextField textField;
    JComboBox optionsChooser;
    JSlider slider;
    LimitsRangeModel sliderModel;
    CyniSampleGuiAlgorithmContext controller;
    Option[] options;
    String title;
    NumberFormat numberFormat;
 
    final  boolean MULTICOLORED = false;
    final static  int MAX = 10000;
 
    CyniSampleGui( String myTitle,
                    Option[] myOptions,
                    LimitsRangeModel myModel) {
        if (MULTICOLORED) {
            setOpaque(true);
            setBackground(new Color(0, 255, 255));
        }
        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(myTitle),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
 
        //Save arguments in instance variables.
        options = myOptions;
        title = myTitle;
        sliderModel = myModel;
 
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        
        textField = new JFormattedTextField(formatter);
        textField.setColumns(10);
        textField.setValue(new Double(sliderModel.getDoubleValue()));
        textField.addPropertyChangeListener(this);
 
        //Add the combo box.
        optionsChooser = new JComboBox();
        for (int i = 0; i < options.length; i++) { //Populate it.
        	optionsChooser.addItem(options[i]);
        }
        optionsChooser.setSelectedIndex(0);
        sliderModel.setLimit(options[0].limit);
        optionsChooser.addActionListener(this);
 
        //Add the slider.
        slider = new JSlider(sliderModel);
        sliderModel.addChangeListener(this);
 
        JPanel unitGroup = new JPanel() {
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
            public Dimension getPreferredSize() {
                return new Dimension(150,
                                     super.getPreferredSize().height);
            }
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        unitGroup.setLayout(new BoxLayout(unitGroup,
                                          BoxLayout.PAGE_AXIS));
        if (MULTICOLORED) {
            unitGroup.setOpaque(true);
            unitGroup.setBackground(new Color(0, 0, 255));
        }
        unitGroup.setBorder(BorderFactory.createEmptyBorder(
                                                0,0,0,5));
        unitGroup.add(textField);
        unitGroup.add(slider);
 
        //Create a subpanel so the combo box isn't too tall
        //and is sufficiently wide.
        JPanel chooserPanel = new JPanel();
        chooserPanel.setLayout(new BoxLayout(chooserPanel,
                                             BoxLayout.PAGE_AXIS));
        if (MULTICOLORED) {
            chooserPanel.setOpaque(true);
            chooserPanel.setBackground(new Color(255, 0, 255));
        }
        chooserPanel.add(optionsChooser);
        chooserPanel.add(Box.createHorizontalStrut(100));
 
        //Put everything together.
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(unitGroup);
        add(chooserPanel);
        unitGroup.setAlignmentY(TOP_ALIGNMENT);
        chooserPanel.setAlignmentY(TOP_ALIGNMENT);
    }
 
    //Don't allow this panel to get taller than its preferred size.
    //BoxLayout pays attention to maximum size, though most layout
    //managers don't.
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE,
                             getPreferredSize().height);
    }
 
    
    public double getLimit() {
        return sliderModel.getLimit();
    }
 
    public double getValue() {
        return sliderModel.getDoubleValue();
    }
 
    /** Updates the text field when the main data model is updated. */
    public void stateChanged(ChangeEvent e) {
        int min = sliderModel.getMinimum();
        int max = sliderModel.getMaximum();
        double value = sliderModel.getDoubleValue();
        NumberFormatter formatter = (NumberFormatter)textField.getFormatter();
 
        formatter.setMinimum(new Double(min));
        formatter.setMaximum(new Double(max));
        textField.setValue(new Double(value));
    }
 
    
    public void actionPerformed(ActionEvent e) {
        //Combo box event. 
        int i = optionsChooser.getSelectedIndex();
        sliderModel.setLimit(options[i].limit);
        if(i==0){
        	sliderModel.setMinimum(0);
        	sliderModel.setMaximum(10);
        }
        if(i==1){
        	sliderModel.setMinimum(-10);
        	sliderModel.setMaximum(0);
        }
        if(i==2){
        	sliderModel.setMinimum(-10);
        	sliderModel.setMaximum(10);
        }
        sliderModel.setDoubleValue(options[i].limit);
    }
 
    /**
     * Detects when the value of the text field (not necessarily the same
     * number as you'd get from getText) changes.
     */
    public void propertyChange(PropertyChangeEvent e) {
        if ("value".equals(e.getPropertyName())) {
            Number value = (Number)e.getNewValue();
            sliderModel.setDoubleValue(value.doubleValue());
        }
    }
	
}
