package com.example.demo.patterns;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BaseListParameterizedView<T>
        extends BaseParameterizedView<T> {

    protected VerticalLayout body;

    public BaseListParameterizedView() {
        super();
    }

    @Override
    protected void build(T parameter) {
        body = new VerticalLayout();
        body.setWidthFull();

        add(body);

        buildList(parameter);
    }

    protected abstract void buildList(T parameter);
}