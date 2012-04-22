## Introduction
This is a demo project how you could set up a blogging engine with comments. The frontend can be fully customised using
Freemarker templates and CSS stored inside the Hippo Repository.

This is also the source code and design behind [jasha.eu](http://www.jasha.eu)

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
See the [documentation](http://www.jasha.eu/documentation). This is also available in the demo content for this project.