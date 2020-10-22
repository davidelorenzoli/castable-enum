![Java CI with Maven](https://github.com/davidelorenzoli/castable-enum/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

# castable-enum
This project allows to create a Java enum which inherits enum item value automatic conversion functionality.

## The Problem
Imagine you have an enum to hold your application JVM arguments. To get the actual JVM argument value as a URL object you would normally do:

```Java
String webServerKey = JVMArgument.WEB_SERVER.getKey();
String webServer = System.getProperty(webServerKey);
try {
    URL webServerUrl = new URL(webServer);
} catch(MalformedUrlException e) {
    // error handling
}
```

This is clearly too much work to get the job done!

## The Solution
The solution is to have your enum implementing `CastableEnum` interface and define your custom value function. The value function defines how to fetch the value corresponding to an enum element. In the example the value is fetched from System properties.

```java
public enum JVMArgument implements CastableEnum {
    SOURCE_ID("sourceId"),
    WEB_SERVER("webServer"),
    FEATURES("features");

    private final String key;

    JVMArgument(String key) {
        this.key = key;
    }

    @Override
    public Function<String, String> getValueFunction() {
        return key -> System.getProperty(key);
    }

    @Override
    public String getKey() {
        return key;
    }
}
```

By implementing the `CastableEnum` you will be able to access cast functionality

```java
System.setProperty(JVMArgument.SOURCE_ID.getKey(), "123");
System.setProperty(JVMArgument.WEB_SERVER.getKey(), "http://webserver.com");
System.setProperty(JVMArgument.FEATURES.getKey(), "logDebug;disCache;sso");

int sourceId = JVMArgument.SOURCE_ID.asInt();
URL url = JVMArgument.WEB_SERVER.asURL();
List<String> list = JVMArgument.FEATURES.asList(";", String.class);
```
