package com.example.demo.views;

import java.util.List;

import org.springframework.http.StreamingHttpOutputMessage.Body;

import com.example.demo.factories.ViewFactory;
import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.tables.Video;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.Route;

import aj.org.objectweb.asm.Label;

@Route("ResultadodeBusqueda")

public class ResultadodeBusqueda extends GaleradeVideos {

    public Buscar _buscar;

    public ResultadodeBusqueda(List<Video> resultados, ViewFactoryProvider viewFactory) {
        super(resultados, viewFactory);
         
      
    }

    @Override
    protected void build() {
       
        super.build();
       tituloGaleria.setText("Resultados de la búsqueda");
       

    }

    @Override
    protected void buildItems() {
        
             
            for (Video video : elements) {
                ResultadodeBusqueda_item item = new ResultadodeBusqueda_item(video, viewFactory);
                carrusel.add(item);
            }
        
    }
}