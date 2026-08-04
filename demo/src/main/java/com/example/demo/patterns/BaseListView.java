package com.example.demo.patterns;

import java.util.Collection;

public abstract class BaseListView<T> extends BaseView {

    protected final Collection<T> elements;

    public BaseListView(Collection<T> elements) {

        this.elements = elements;

        initView();
    }

    @Override
    protected void build() {

        buildContainer();

        buildItems();

    }

    protected abstract void buildContainer();

    protected abstract void buildItems();

}