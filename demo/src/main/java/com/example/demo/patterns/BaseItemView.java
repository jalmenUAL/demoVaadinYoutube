package com.example.demo.patterns;

public abstract class BaseItemView<T>
        extends BaseView {

    protected final T model;

    public BaseItemView(T model) {

        this.model = model;

         

    }

}