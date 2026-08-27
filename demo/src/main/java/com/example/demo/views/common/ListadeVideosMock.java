package com.example.demo.views.common;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.mocks.DatosMock;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("ListadeVideosMock")
@AnonymousAllowed

public class ListadeVideosMock extends ListadeVideos {

    public ListadeVideosMock(DatosMock datos, ViewFactoryProvider factory) {
        super(datos.videos, factory);
        //TODO Auto-generated constructor stub
    }

}
