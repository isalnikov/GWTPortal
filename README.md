# GWTPortal
GWT 2.7.0 spring  javaconfig

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