# HippoWeblog

**This repository is outdated and will not be maintained**

## Introduction
This is a demo project for Hippo CMS 7.6 how you could set up a blogging engine with comments. The frontend can be fully customised using
Freemarker templates and CSS stored inside the Hippo Repository. 

This used to be the source code and design behind [jasha.eu](http://www.jasha.eu).

## Build the project

The project will contain at least 4 subprojects:

    cms - a pre-configured content repository and CMS
    demoblogcontent - sample content including documents and pictures for the sample site
    content - document types, minimal folder structure and configuration that lives in the repository
    site - the front-end website. Uses either Freemarker or JSP for the rendering.

Now build your project using the following command in the root folder of your project:

    mvn install

This will produce two WAR files:

    cms/target/cms.war
    site/target/site.war

## Run the weblog demo
See "[Run and develop with Cargo](https://wiki.onehippo.com/display/CMS7/Run+and+develop+with+Cargo)".

## How do I?
See the [Wiki](https://github.com/jashaj/HippoWeblog/wiki). The documentation is also included in the demo content of this project.
