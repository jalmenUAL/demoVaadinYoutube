package com.example.demo.views;

import java.util.Set;
import java.util.Vector;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseListParameterizedView;
import com.example.demo.patterns.BaseListView;
import com.example.demo.patterns.BaseParameterizedView;
import com.example.demo.services.iInicio;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("Youtuberseguidos")
@RolesAllowed({ "ROLE_YOUTUBER", "ROLE_ADMINISTRADOR" })

public class Youtubersseguidos   extends BaseListParameterizedView<String> {
    public Perfil _perfil;
    public Vector<Youtubersseguidos_item> _item = new Vector<Youtubersseguidos_item>();

    Set<com.example.demo.tables.Youtuber> youtubers;
     FlexLayout gridContainer = new FlexLayout();

    iInicio _iInicio;
    protected ViewFactoryProvider viewFactory;

    public Youtubersseguidos(iInicio iInicio, ViewFactoryProvider viewFactory) {
        super();
        this._iInicio = iInicio;
        this.viewFactory = viewFactory;
        
    }

    @Override
    protected void bindEvents() {
         
    }


    @Override
    protected void buildList(String parameter) {
         com.example.demo.tables.Youtuber _youtuber = _iInicio.findYoutuberById(String.valueOf(parameter));
          Set<com.example.demo.tables.Youtuber> youtubers = _youtuber.getSeguidor_de();         
        
       if (youtubers == null) {

        } else  
       for (com.example.demo.tables.Youtuber youtuber : youtubers) {

                Youtubersseguidos_item youtuberItem = new Youtubersseguidos_item(youtuber, viewFactory);

                this._item.add(youtuberItem);

                gridContainer.add(youtuberItem);
            }

            add(gridContainer);
    }








 
}
