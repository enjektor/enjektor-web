package com.github.enjektor.web.servlet;

import com.github.enjektor.web.annotations.*;
import com.github.enjektor.web.invocation.state.MethodStateInvocation;
import com.github.enjektor.web.invocation.state.PrimitiveMethodStateInvocation;
import com.github.enjektor.web.servlet.endpoint.hash.ByteHashProvider;
import com.github.enjektor.web.servlet.endpoint.hash.HashProvider;
import com.github.enjektor.web.state.MethodState;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultServletMethodInitializer implements ServletMethodInitializer {

    private final HashProvider hashProvider;

    public DefaultServletMethodInitializer() {
        this.hashProvider = ByteHashProvider.getInstance();
    }

    @Override
    public MethodState initializeGet(Class<?> routerClass) {
        final TByteObjectMap<Method> map = new TByteObjectHashMap<>();
        final List<String> patterns = new ArrayList<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Get.class)) {
                final Get getAnnotation = method.getAnnotation(Get.class);
                final String endpointValue = routerEndpoint + getAnnotation.value();
                final byte hashValue = hashProvider.provide(endpointValue);


                map.put(hashValue, methodState);
            }
        }

        return new MethodState.Builder()
                .methods(map)
                .patterns(patterns)
                .build();
    }

    @Override
    public TByteObjectMap<MethodState> initializePost(Class<?> routerClass) {
        final TByteObjectMap<MethodState> map = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        Arrays.stream(declaredMethods)
                .filter(m -> m.isAnnotationPresent(Post.class))
                .forEach(method -> {
                    final Post postAnnotation = method.getAnnotation(Post.class);
                    final String endpointValue = routerEndpoint + postAnnotation.value();

                    final MethodStateInvocation methodStateInvocation = new PrimitiveMethodStateInvocation(endpointValue);
                    final byte hashValue = hashProvider.provide(methodStateInvocation.hash());

                    final MethodState methodState = new MethodState.Builder()
                            .method(method)
                            .pattern(methodStateInvocation.pattern())
                            .build();

                    map.put(hashValue, methodState);
                });

        return map;
    }

    @Override
    public TByteObjectMap<MethodState> initializeDelete(Class<?> routerClass) {
        final TByteObjectMap<MethodState> map = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        Arrays.stream(declaredMethods)
                .filter(m -> m.isAnnotationPresent(Delete.class))
                .forEach(method -> {
                    final Delete deleteAnnotation = method.getAnnotation(Delete.class);
                    final String endpointValue = routerEndpoint + deleteAnnotation.value();

                    final MethodStateInvocation methodStateInvocation = new PrimitiveMethodStateInvocation(endpointValue);
                    final byte hashValue = hashProvider.provide(methodStateInvocation.hash());

                    final MethodState methodState = new MethodState.Builder()
                            .method(method)
                            .pattern(methodStateInvocation.pattern())
                            .build();

                    map.put(hashValue, methodState);
                });

        return map;
    }

    @Override
    public TByteObjectMap<MethodState> initializePut(Class<?> routerClass) {
        final TByteObjectMap<MethodState> map = new TByteObjectHashMap<>();
        final Method[] declaredMethods = routerClass.getDeclaredMethods();
        final String routerEndpoint = routerEndpoint(routerClass);

        Arrays.stream(declaredMethods)
                .filter(m -> m.isAnnotationPresent(Put.class))
                .forEach(method -> {
                    final Put putAnnotation = method.getAnnotation(Put.class);
                    final String endpointValue = routerEndpoint + putAnnotation.value();

                    final MethodStateInvocation methodStateInvocation = new PrimitiveMethodStateInvocation(endpointValue);
                    final byte hashValue = hashProvider.provide(methodStateInvocation.hash());

                    final MethodState methodState = new MethodState.Builder()
                            .method(method)
                            .pattern(methodStateInvocation.pattern())
                            .build();

                    map.put(hashValue, methodState);
                });

        return map;
    }

    private String routerEndpoint(Class<?> routerClass) {
        final Router router = routerClass.getAnnotation(Router.class);
        return router.value().intern();
    }
}
