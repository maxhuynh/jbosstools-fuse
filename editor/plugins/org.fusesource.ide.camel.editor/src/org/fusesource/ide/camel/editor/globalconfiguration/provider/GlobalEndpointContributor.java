/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 

package org.fusesource.ide.camel.editor.globalconfiguration.provider;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.ide.camel.editor.globalconfiguration.wizards.AddGlobalEndpointWizard;
import org.fusesource.ide.camel.editor.provider.ext.GlobalConfigElementType;
import org.fusesource.ide.camel.editor.provider.ext.GlobalConfigurationTypeWizard;
import org.fusesource.ide.camel.editor.provider.ext.ICustomGlobalConfigElementContribution;
import org.fusesource.ide.camel.model.service.core.catalog.CamelModel;
import org.fusesource.ide.camel.model.service.core.catalog.CamelModelFactory;
import org.fusesource.ide.camel.model.service.core.catalog.Dependency;
import org.fusesource.ide.camel.model.service.core.catalog.components.Component;
import org.fusesource.ide.camel.model.service.core.catalog.components.ComponentModel;
import org.fusesource.ide.camel.model.service.core.model.CamelFile;
import org.fusesource.ide.foundation.core.util.CamelUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author lhein
 */
public class GlobalEndpointContributor implements ICustomGlobalConfigElementContribution {

	private static final String ENDPOINT = "endpoint";
	private AddGlobalEndpointWizard wizard = null;

	/* (non-Javadoc)
	 * @see org.fusesource.ide.camel.editor.provider.ext.ICustomGlobalConfigElementContribution#createGlobalElement(org.w3c.dom.Document)
	 */
	@Override
	public GlobalConfigurationTypeWizard createGlobalElement(CamelFile camelFile) {
		final CamelModel camelModel = CamelModelFactory.getModelForVersion(CamelModelFactory.getCamelVersion(camelFile.getResource().getProject()));
		final ComponentModel componentModel = camelModel.getComponentModel();
		wizard = new AddGlobalEndpointWizard(camelFile, componentModel);
		return wizard;
	}

	/* (non-Javadoc)
	 * @see org.fusesource.ide.camel.editor.provider.ext.ICustomGlobalConfigElementContribution#modifyGlobalElement(org.w3c.dom.Document)
	 */
	@Override
	public GlobalConfigurationTypeWizard modifyGlobalElement(Document document) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.ide.camel.editor.provider.ext.ICustomGlobalConfigElementContribution#getElementDependencies()
	 */
	@Override
	public List<Dependency> getElementDependencies() {
		if (wizard != null) {
			final Component component = wizard.getComponent();
			if (component != null) {
				return component.getDependencies();
			}
		}
		return new ArrayList<Dependency>();
	}

	/* (non-Javadoc)
	 * @see org.fusesource.ide.camel.editor.provider.ext.ICustomGlobalConfigElementContribution#onGlobalElementDeleted(org.w3c.dom.Node)
	 */
	@Override
	public void onGlobalElementDeleted(Node node) {
		// possible actions if one of my nodes got deleted from the context
	}

	/* (non-Javadoc)
	 * @see org.fusesource.ide.camel.editor.provider.ext.ICustomGlobalConfigElementContribution#canHandle(org.w3c.dom.Node)
	 */
	@Override
	public boolean canHandle(Node nodeToHandle) {
		return CamelUtils.getTranslatedNodeName(nodeToHandle).equalsIgnoreCase(ENDPOINT);
	}

	/* (non-Javadoc)
	 * @see org.fusesource.ide.camel.editor.provider.ext.ICustomGlobalConfigElementContribution#getGlobalConfigElementType()
	 */
	@Override
	public GlobalConfigElementType getGlobalConfigElementType() {
		return GlobalConfigElementType.CONTEXT_ENDPOINT;
	}
}
