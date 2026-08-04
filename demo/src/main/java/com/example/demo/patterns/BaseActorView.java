package com.example.demo.patterns;

public abstract class BaseActorView
        extends BaseView {

    @Override
    protected final void build() {

        buildHeader();

        buildActions();

        buildContent();

    }

    protected abstract void buildHeader();

    protected abstract void buildActions();

    protected abstract void buildContent();

}
