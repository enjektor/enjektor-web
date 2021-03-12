package com.github.enjektor.web.invocation;

import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Method;

public interface HttpInvocation {
    TByteObjectMap<Method>[] invoke(Class<?> routerClass);
}
