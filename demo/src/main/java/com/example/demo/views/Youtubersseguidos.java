package com.example.demo.views;

import java.util.Set;
import java.util.Vector;

import com.example.demo.patterns.BaseListView;
import com.example.demo.services.iInicio;
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

public class Youtubersseguidos extends BaseListView<com.example.demo.tables.Youtuber> implements HasUrlParameter<String> {
    public Perfil _perfil;
    public Vector<Youtubersseguidos_item> _item = new Vector<Youtubersseguidos_item>();

    Set<com.example.demo.tables.Youtuber> youtubers;
     FlexLayout gridContainer = new FlexLayout();

    iInicio _iInicio;

    public Youtubersseguidos(iInicio iInicio,Set<com.example.demo.tables.Youtuber> youtubers) {
        super(youtubers);
        this._iInicio = iInicio;
    }


       

    

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

        com.example.demo.tables.Youtuber usuario = _iInicio.findYoutuberById(parameter);
        

        

        if (youtubers == null) {

        } else {

            FlexLayout gridContainer = new FlexLayout();

            gridContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);

            gridContainer.getStyle().set("gap", "1em");

            for (com.example.demo.tables.Youtuber youtuber : youtubers) {

                Youtubersseguidos_item youtuberItem = new Youtubersseguidos_item(youtuber);

                this._item.add(youtuberItem);

                gridContainer.add(youtuberItem);
            }

            add(gridContainer);

            setHorizontalComponentAlignment(Alignment.CENTER, gridContainer);
        }
    }

    @Override
    protected void buildContainer() {
         setPadding(true);
        setSpacing(true);

        H1 titulo = new H1("Youtubers Seguidos");
        titulo.getStyle().set("text-align", "center");
        add(titulo);
         if (youtubers == null) {

        } else {

           

            gridContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);

            gridContainer.getStyle().set("gap", "1em");

            

            add(gridContainer);

            setHorizontalComponentAlignment(Alignment.CENTER, gridContainer);
        }
    }

    

    @Override
    protected void buildItems() {
       for (com.example.demo.tables.Youtuber youtuber : youtubers) {

                Youtubersseguidos_item youtuberItem = new Youtubersseguidos_item(youtuber);

                this._item.add(youtuberItem);

                gridContainer.add(youtuberItem);
            }
    }
}
