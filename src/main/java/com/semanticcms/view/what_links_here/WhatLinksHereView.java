/*
 * semanticcms-view-what-links-here - SemanticCMS view of which pages and elements link to the current page.
 * Copyright (C) 2016, 2017, 2018, 2020, 2021  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of semanticcms-view-what-links-here.
 *
 * semanticcms-view-what-links-here is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * semanticcms-view-what-links-here is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with semanticcms-view-what-links-here.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.semanticcms.view.what_links_here;

import com.aoindustries.html.FlowContent;
import com.semanticcms.core.controller.CapturePage;
import com.semanticcms.core.controller.SemanticCMS;
import com.semanticcms.core.model.BookRef;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.model.PageRef;
import com.semanticcms.core.pages.CaptureLevel;
import com.semanticcms.core.renderer.html.HtmlRenderer;
import com.semanticcms.core.renderer.html.NavigationTreeRenderer;
import com.semanticcms.core.renderer.html.View;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WhatLinksHereView extends View {

	public static final String NAME = "what-links-here";

	@WebListener("Registers the \"" + NAME + "\" view in HtmlRenderer.")
	public static class Initializer implements ServletContextListener {
		@Override
		public void contextInitialized(ServletContextEvent event) {
			HtmlRenderer.getInstance(event.getServletContext()).addView(new WhatLinksHereView());
		}
		@Override
		public void contextDestroyed(ServletContextEvent event) {
			// Do nothing
		}
	}

	private WhatLinksHereView() {}

	@Override
	public Group getGroup() {
		return Group.FIXED;
	}

	@Override
	public String getDisplay() {
		return "What Links Here";
	}

	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * Does not apply to global navigation since "here" is not intuitive.
	 */
	@Override
	public boolean getAppliesGlobally() {
		return false;
	}

	/**
	 * TODO: Is there a computationally inexpensive way to see if anything links here (without full page tree traversal?)
	 */
	@Override
	public boolean isApplicable(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, Page page) throws ServletException, IOException {
		return true;
	}

	@Override
	public String getDescription(Page page) {
		return null;
	}

	@Override
	public String getKeywords(Page page) {
		return null;
	}

	/**
	 * Not sure if this would be a benefit to search engines, but we'll be on the safe side
	 * and focus on search engines seeing the original content.
	 */
	@Override
	public boolean getAllowRobots(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, Page page) {
		return false;
	}

	@Override
	public <__ extends FlowContent<__>> void doView(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, __ flow, Page page) throws ServletException, IOException {
		PageRef pageRef = page.getPageRef();
		BookRef bookRef = pageRef.getBookRef();
		Page contentRoot = CapturePage.capturePage(
			servletContext,
			request,
			response,
			SemanticCMS.getInstance(servletContext).getRootBook().getContentRoot(),
			CaptureLevel.PAGE
		);
		flow.h1__(h1 -> h1
			.text("What Links to ").text(page.getTitle())
		);
		NavigationTreeRenderer.writeNavigationTree(
			servletContext,
			request,
			response,
			flow,
			contentRoot,
			false, // skipRoot
			false, // yuiConfig
			true, // includeElements
			null, // target
			bookRef.getDomain(), // thisDomain
			bookRef.getPath(), // thisBook
			pageRef.getPath().toString(), // thisPage
			bookRef.getDomain(), // linksToDomain
			bookRef.getPath(), // linksToBook
			pageRef.getPath().toString(), // linksToPage
			0 // maxDepth
		);
	}
}
