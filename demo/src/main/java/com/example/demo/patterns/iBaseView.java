package com.example.demo.patterns;

import com.example.demo.factories.ViewFactoryProvider;

public interface iBaseView<S> {
    
    /**
     * Inicializa completamente la vista.
     */
    void initView();

    /**
     * Obtiene el proveedor de factorías.
     */
    ViewFactoryProvider getViewFactory();

    /**
     * Obtiene el servicio de negocio asociado.
     */
    S getServicio();
}