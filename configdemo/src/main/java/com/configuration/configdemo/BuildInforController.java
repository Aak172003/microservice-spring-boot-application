package com.configuration.configdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildInforController {
//    This annotation doing to job is injecting yaml value to here variables

    @Value("${build.id}")
    private String buildId;

    @Value("${build.version}")
    private String buildVersion;

    @Value("${build.name}")
    private String buildName;

    @GetMapping("/build-info")
    public String getBuildInfo(){
        String buildInfo = "Build Id: " + buildId + " Build Version: " + buildVersion + " Build Name: " + buildName;
        return buildInfo;
    }
}
