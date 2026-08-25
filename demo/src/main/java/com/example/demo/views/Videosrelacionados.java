package com.example.demo.views;

import java.util.List;
import java.util.Vector;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.patterns.BaseListView;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.Route;

@Route("Videosrelacionados")

public class Videosrelacionados extends BaseListView<Video> {

    protected ViewFactoryProvider viewFactory;

    public Videosrelacionados(
            List<Video> videosrelacionados,
            ViewFactoryProvider viewFactory) {

        super(videosrelacionados);
        this.viewFactory = viewFactory;
        initView();
    }

    @Override
    protected void buildContainer() {

        setWidthFull();
        setPadding(true);
        setSpacing(false);

        H2 titulo = new H2("Videos Relacionados");

        add(titulo);
    }

    @Override
    protected void buildItems() {

        for (Video v : elements) {

            Videosrelacionados_item item =
                    new Videosrelacionados_item(
                            v,
                            viewFactory);

            item.getStyle()
                    .set("padding", "10px")
                    .set("background-color", "#f9f9f9")
                    .set("border-radius", "6px")
                    .set(
                            "box-shadow",
                            "0 1px 3px rgba(0,0,0,0.1)");

            add(item);
        }
    }

    @Override
    protected void bindEvents() {
        // Los eventos se gestionan en Videosrelacionados_item.
    }
}