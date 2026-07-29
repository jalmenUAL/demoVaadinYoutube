package com.example.demo.patterns;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class BaseView extends VerticalLayout {

    public BaseView() {
        build();
        bindEvents();
        
    }
 
 
   
    protected abstract void build();

    protected abstract void bindEvents();

    
}