/*
 * semanticcms-view-what-links-here - SemanticCMS view of which pages and elements link to the current page.
 * Copyright (C) 2021, 2022, 2023  AO Industries, Inc.
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
module com.semanticcms.view.whatlinkshere {
  exports com.semanticcms.view.whatlinkshere;
  // Direct
  requires com.aoapps.html.servlet; // <groupId>com.aoapps</groupId><artifactId>ao-fluent-html-servlet</artifactId>
  requires javax.servlet.api; // <groupId>javax.servlet</groupId><artifactId>javax.servlet-api</artifactId>
  requires com.semanticcms.core.model; // <groupId>com.semanticcms</groupId><artifactId>semanticcms-core-model</artifactId>
  requires com.semanticcms.core.servlet; // <groupId>com.semanticcms</groupId><artifactId>semanticcms-core-servlet</artifactId>
  // Transitive
  requires com.aoapps.lang; // <groupId>com.aoapps</groupId><artifactId>ao-lang</artifactId>
}
