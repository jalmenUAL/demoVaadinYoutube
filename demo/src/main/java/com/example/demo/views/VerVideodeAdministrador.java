package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iAdministrador;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("VerVideodeAdministrador")

public class VerVideodeAdministrador extends VerVideo {

    /*
     * Servicio específico de administrador.
     *
     * Permite realizar operaciones que un usuario normal
     * no puede realizar, como borrar vídeos.
     */
    protected final iAdministrador iAdministrador;


    /*
     * Constructor.
     *
     * Se reutiliza la configuración común de VerVideo
     * mediante super().
     *
     * La vista especializada recibe iAdministrador,
     * ya que necesita funcionalidades adicionales.
     */
    public VerVideodeAdministrador(
            iAdministrador iAdministrador,
            ViewFactoryProvider viewFactory) {

        super(iAdministrador, viewFactory);

        this.iAdministrador = iAdministrador;
    }


    /*
     * Elimina el vídeo actualmente visualizado.
     *
     * La operación se delega en la interfaz iAdministrador.
     * La vista no accede directamente a la base de datos.
     */
    public void borrar() {

        iAdministrador.borrarVideo(
                video.getId());

        /*
         * Una vez eliminado el vídeo,
         * volvemos a la vista anterior.
         */
        UI.getCurrent()
                .getPage()
                .getHistory()
                .back();
    }


    /*
     * Se sobrescribe setParameter() para añadir
     * funcionalidad específica del administrador.
     *
     * Primero se ejecuta la implementación de VerVideo,
     * que carga el vídeo y construye la vista base.
     */
    @Override
    public void setParameter(
            BeforeEvent event,
            Integer parameter) {

        super.setParameter(
                event,
                parameter);


        /*
         * Botón exclusivo del administrador.
         */
        Button borrarButton =
                new Button(
                        "🗑️ Borrar video",
                        event2 -> borrar());


        /*
         * Estilo visual del botón de borrado.
         */
        borrarButton.getStyle()
                .set("background-color", "#dc3545")
                .set("color", "white")
                .set("border-radius", "8px")
                .set("padding", "10px 20px")
                .set("font-weight", "bold");


        /*
         * Añadimos el botón a la zona principal
         * de la vista del vídeo.
         */
        frame_y_comentarios.add(
                borrarButton);
    }


    /*
     * Sobrescribimos la creación de comentarios.
     *
     * Un administrador utiliza una implementación especializada
     * de VerComentarios que puede incluir funcionalidades
     * adicionales, como eliminar comentarios.
     */
    @Override
    public void VerComentarios() {

        _verComentarios =
                new VerComentariosdeAdministrador(
                        iAdministrador,
                        video.getTiene_comentarios(),
                        video.getId(),
                        viewFactory);

        comentarios.add(
                _verComentarios);
    }
}