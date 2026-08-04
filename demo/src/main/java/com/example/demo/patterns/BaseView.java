package com.example.demo.patterns;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BaseView extends VerticalLayout {

    protected final void initView() {
        build();
        bindEvents();
    }

    protected abstract void build();

    protected void bindEvents() {
    }
}