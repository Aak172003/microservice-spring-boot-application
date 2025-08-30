package com.configuration.configdemo;

import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController
//public class BuildInforController {
////    Value annotation in spring boot is straightforward used to inject value to the variable from external sources such as application.properties or application.yml
//
////-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//
////    But configuration properties allow grouping of related properties to a single class.
////    This makes application much more organized and easier to manage an application
//
//
////    This is how we can set default kind of value
////    @Value("${build.id:default}")
////    This default value if we provide is it throws error like no active profile set.'
//
////    @Value("${build.id}")
////    @Value("${OS:default}")
////    If we provide default value and also we enable default profile active ,
////    so this will show values from application.yml file because i activate default profile
//    @Value("${build.id:default}")
//    private String buildId;
//
////    @Value("${USERPROFILE:default}")
//
//    @Value("${build.version:default}")
//    private String buildVersion;
//
////    @Value("${JAVA_HOME:default}")
//    @Value("${build.name:default}")
//    private String buildName;
//
//    @GetMapping("/build-info")
//    public String getBuildInfo(){
//        String buildInfo = "Build Id: " + buildId + " Build Version: " + buildVersion + " Build Name: " + buildName;
//        return buildInfo;
//    }
//}


@RestController
@AllArgsConstructor
public class BuildInforController {

    private final BuildInfo buildInfo;

    @GetMapping("/build-info")
    public String getBuildInfo() {
        return "Build Id: " + buildInfo.getId()
                + " Build Version: " + buildInfo.getVersion()
                + " Build Name: " + buildInfo.getName();
    }
}
