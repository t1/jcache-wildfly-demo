package com.github.t1.jcache.demo;

import javax.cache.annotation.CacheResult;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.LocalTime;

@Path("/hi")
@Singleton
public class HelloController {

    @GET
    @CacheResult
    public String sayHello() {
        return "hi, it's " + LocalTime.now();
    }
}
