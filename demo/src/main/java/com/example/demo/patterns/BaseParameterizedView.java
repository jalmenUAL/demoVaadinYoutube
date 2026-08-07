package com.example.demo.patterns;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;

public abstract class BaseParameterizedView<T>
        extends VerticalLayout
        implements HasUrlParameter<T> {

    @Override
    public void setParameter(BeforeEvent event, T parameter) {

      

        build(parameter);

        bindEvents();
    }

    protected void build(T parameter) {
        build();
    }

    protected abstract void build();

    protected abstract void bindEvents();

     
}