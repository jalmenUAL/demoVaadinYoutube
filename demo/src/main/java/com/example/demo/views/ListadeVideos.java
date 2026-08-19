package com.example.demo.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseListView;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("ListadeVideos")
public class ListadeVideos extends BaseListView<Video> {

    public Vector<ListadeVideos_item> _item = new Vector<>();
    protected ViewFactoryProvider viewFactory;

    public ListadeVideos(Set<Video> videos, ViewFactoryProvider factory) {
        super(videos);
        this.viewFactory = factory;
         initView();
    }

    @Override
    protected void buildContainer() {

        setWidthFull();
        setSpacing(true);

    }

    

    @Override
    protected void bindEvents() {
        // La lista no tiene eventos propios.
    }

    @Override
    protected void buildItems() {
        int columnas = 2;

        int index = 0;

        List<Video> listaVideos = new ArrayList<>(elements);

        while (index < listaVideos.size()) {

            HorizontalLayout fila = new HorizontalLayout();

            fila.setWidthFull();
            fila.setSpacing(true);

            fila.getStyle()
                    .set("justify-content", "space-between");

            for (int c = 0; c < columnas && index < listaVideos.size(); c++) {

                Video video = listaVideos.get(index);

                ListadeVideos_item item = new ListadeVideos_item(video, viewFactory);

                item.setWidth("48%");

                _item.add(item);

                fila.add(item);

                index++;
            }

            add(fila);

        }
    }

}