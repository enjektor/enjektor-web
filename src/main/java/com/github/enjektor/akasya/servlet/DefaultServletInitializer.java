package com.github.enjektor.akasya.servlet;

import com.github.enjektor.akasya.enums.HttpMethod;
import com.github.enjektor.akasya.state.MethodState;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DefaultServletInitializer implements ServletInitializer {

    private final ServletMethodInitializer servletMethodInitializer;

    public DefaultServletInitializer() {
        this.servletMethodInitializer = new DefaultServletMethodInitializer();
    }

    @Override
    public TByteObjectMap<MethodState> initialize(Class<?> router) {
        final TByteObjectMap<MethodState> states = new TByteObjectHashMap<>(1);
        states.put(HttpMethod.GET.getMethodHex(), servletMethodInitializer.initializeGet(router));
//        methodArray[HTTP_METHOD_GET] = servletMethodInitializer.initializeGet(router);
//        methodArray[HTTP_METHOD_POST] = servletMethodInitializer.initializePost(router);
//        methodArray[HTTP_METHOD_DELETE] = servletMethodInitializer.initializeDelete(router);
//        methodArray[HTTP_METHOD_PUT] = servletMethodInitializer.initializePut(router);

        return states;
    }

}
