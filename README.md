# GWTPortal
GWT 2.7.0 spring  javaconfig

UPDATE 02/10/2015
DEBUG mode 

1) add to pom.xml jetty-maven-plugin webAppSourceDirectory - ${project.build.directory}/${project.build.finalName}

2) mvn jetty:stop jetty:run-exploded

3) add to pom.xml gwt-maven-plugin noserver true

4) mvn gwt:run 


1.  Добавил все настройку на spring java config
    http://habrahabr.ru/post/226791/

2.  Добавил GWT + GXT
    

3.  TODO  нужно понять как запускать и отлаживать код совместно :
    может ли gwt jetty  конфигурироваться через spring javaconfig и видеть все настройки;
    как проводить отладку клиента и сервера ?
    mvn jetty:run  не видит скомпилированные ресурсы gwt -  требуется стартовать через mvn jetty:run-war но при этом scanIntervalSeconds = 0


4.  RequestBuilder 
    http://stackoverflow.com/questions/15446161/gwt-request-builder-how-to-return-the-response-string

5.  gwt:run gwt:dubug 


TODO 

6. отказаться от web.xml - перейти на javaconfig
7. добавить GWT RPC совместно со spring javaconfig (отказаться от  RequestBuilder)


8. permutations http://docs-devel.sencha.com/gxt/3.x/gwt/Permutations.html

9. GWT XML module configuration http://docs-devel.sencha.com/gxt/3.x/getting_started/Getting_Started.html


10. spring xml + annotation javaconfig http://www.robinhowlett.com/blog/2013/02/13/spring-app-migration-from-xml-to-java-based-config/

11. https://gwt.googlesource.com/gwt/+/2.7.0/samples/json?autodive=0%2F%2F/

12. http://www.jworks.nl/2012/08/13/getting-started-with-maven-gxt-3-and-gwt-2-5/

13. example https://github.com/steinsag/gwt-maven-example

14. CRUD https://github.com/poseidonjm/basic-crud

15. http://vivagwt.blogspot.in/2012/06/sencha-gxt3-requestfactory-guice-jpa.html

16. http://www.mkyong.com/spring/spring-view-content-of-hsqldb-embedded-database/