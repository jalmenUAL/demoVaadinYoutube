package com.example.demo.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BaseView extends VerticalLayout {

    public BaseView() {
        initView();
    }

     protected final void initView() {
        configure();
        build();  
        bindEvents();
        configureNavigation();
    }

    protected abstract void configure();

    protected abstract void build();

    protected abstract void bindEvents();

    protected abstract void configureNavigation();
}
