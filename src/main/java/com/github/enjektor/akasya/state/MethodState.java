package com.github.enjektor.akasya.state;

import gnu.trove.map.TByteObjectMap;

import java.lang.reflect.Method;
import java.util.List;

public class MethodState {
    private final TByteObjectMap<Method> methods;
    private final List<PathParameterState> states;

    public static class Builder {
        private TByteObjectMap<Method> methods;
        private List<PathParameterState> states;

        public Builder methods(TByteObjectMap<Method> methods) {
            this.methods = methods;
            return this;
        }

        public Builder states(List<PathParameterState> states) {
            this.states = states;
            return this;
        }

        public MethodState build() {
            return new MethodState(this);
        }
    }

    private MethodState(Builder builder) {
        this.methods = builder.methods;
        this.states = builder.states;
    }

    public TByteObjectMap<Method> getMethods() {
        return methods;
    }

    public List<PathParameterState> getStates() {
        return states;
    }
}
