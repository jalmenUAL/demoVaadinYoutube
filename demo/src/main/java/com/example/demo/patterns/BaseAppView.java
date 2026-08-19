package com.example.demo.patterns;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BaseAppView extends AppLayout {

    protected HorizontalLayout header = new HorizontalLayout();
    protected VerticalLayout body;

    public BaseAppView() {
        buildLayout();
        build();
        bindEvents();
    }

    private void buildLayout() {
        header = new HorizontalLayout();
        header.setWidthFull();
        header.setAlignItems(Alignment.CENTER);

        addToNavbar(header);

        body = new VerticalLayout();
        body.setSizeFull();

        setContent(body);
    }

    protected abstract void build();

    protected void bindEvents() {
    }
}
