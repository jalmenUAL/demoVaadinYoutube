package com.example.demo.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.example.demo.tables.Video;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("ListadeVideos")
public class ListadeVideos extends VerticalLayout {
    public Vector<ListadeVideos_item> _item = new Vector<ListadeVideos_item>();

    public ListadeVideos(Set<Video> videos) {
        int columnas = 2;
        int index = 0;
        List<Video> listavideos = new ArrayList<>(videos);
        while (index < listavideos.size()) {
            HorizontalLayout fila = new HorizontalLayout();
            fila.setWidthFull();
            fila.setSpacing(true);
            fila.getStyle().set("justify-content", "space-between");

            for (int c = 0; c < columnas && index < listavideos.size(); c++) {
                Video video = listavideos.get(index);
                ListadeVideos_item item = new ListadeVideos_item(video);
                item.setWidth("48%"); // Para que entren dos en una fila con espacio
                _item.add(item);
                fila.add(item);
                index++;
            }

            add(fila);
        }
    }
}
