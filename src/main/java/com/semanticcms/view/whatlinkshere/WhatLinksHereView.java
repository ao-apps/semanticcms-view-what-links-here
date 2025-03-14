/*
 * semanticcms-view-what-links-here - SemanticCMS view of which pages and elements link to the current page.
 * Copyright (C) 2016, 2017, 2018, 2020, 2021, 2022, 2024  AO Industries, Inc.
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
 * along with semanticcms-view-what-links-here.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.semanticcms.view.whatlinkshere;

import com.aoapps.html.servlet.FlowContent;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.model.PageRef;
import com.semanticcms.core.servlet.CaptureLevel;
import com.semanticcms.core.servlet.CapturePage;
import com.semanticcms.core.servlet.SemanticCMS;
import com.semanticcms.core.servlet.View;
import com.semanticcms.core.servlet.impl.NavigationTreeImpl;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SemanticCMS view of which pages and elements link to the current page.
 */
public final class WhatLinksHereView extends View {

  public static final String NAME = "what-links-here";

  /**
   * Registers the "{@link #NAME}" view in {@link SemanticCMS}.
   */
  @WebListener("Registers the \"" + NAME + "\" view in SemanticCMS.")
  public static class Initializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
      SemanticCMS.getInstance(event.getServletContext()).addView(new WhatLinksHereView());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
      // Do nothing
    }
  }

  private WhatLinksHereView() {
    // Do nothing
  }

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
   * {@inheritDoc}
   *
   * <p>TODO: Is there a computationally inexpensive way to see if anything links here (without full page tree traversal?)</p>
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
    NavigationTreeImpl.writeNavigationTreeImpl(
        servletContext,
        request,
        response,
        flow,
        contentRoot,
        false, // skipRoot
        false, // yuiConfig
        true, // includeElements
        null, // target
        pageRef.getBookName(), // thisBook
        pageRef.getPath(), // thisPage
        pageRef.getBookName(), // linksToBook
        pageRef.getPath(), // linksToPage
        0 // maxDepth
    );
  }
}
