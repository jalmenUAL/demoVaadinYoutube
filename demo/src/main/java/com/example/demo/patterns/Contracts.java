package com.example.demo.patterns;

import java.util.Collection;

import com.example.demo.factories.ViewFactoryProvider;

public class Contracts {
    public interface HasService<S> {
        S getServicio();
    }

    public interface HasModel<T> {
        T getModel();
    }

    public interface HasElements<T> {
        Collection<T> getElements();
    }

    public interface HasFactory {
        ViewFactoryProvider getViewFactory();
    }

}
