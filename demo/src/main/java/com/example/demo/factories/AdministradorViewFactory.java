package com.example.demo.factories;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.demo.services.iAdministrador;
import com.example.demo.tables.Comentario;
import com.example.demo.views.PerfilAjeno;
import com.example.demo.views.PerfilAjenodeAdministrador;
import com.example.demo.views.UltimosVideos_item;
import com.example.demo.views.UltimosVideosdeAdministrador_item;
import com.example.demo.views.VerComentarios;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerComentariosdeAdministrador;
import com.example.demo.views.VerComentariosdeAdministrador_item;
import com.example.demo.views.VerVideo;
import com.example.demo.views.VerVideodeAdministrador;

 @Component
 /**
  * Factoría de vistas para el usuario Administrador.
  *
  * <p>
  * Su función es decidir qué implementación concreta de cada
  * vista debe utilizar un Administrador.
  *
  * <p>
  * De esta forma, el resto de la aplicación no necesita conocer
  * directamente clases como:
  *
  *     VerVideodeAdministrador
  *     PerfilAjenodeAdministrador
  *     VerComentariosdeAdministrador
  *
  * sino que solicita la vista a través de la interfaz ViewFactory.
  *
  * <p>
  * Este mecanismo permite tener diferentes factorías para los
  * diferentes tipos de usuario.
  */
public class AdministradorViewFactory implements ViewFactory {

    /**
     * Interfaz que proporciona las operaciones disponibles
     * para un Administrador.
     *
     * La factoría la recibe mediante inyección de dependencias
     * en el constructor.
     */
    private iAdministrador _iAdministrador;


    /**
     * Constructor de la factoría.
     *
     * @param iAdministrador interfaz con las operaciones
     *                       disponibles para el Administrador
     */
    public AdministradorViewFactory(iAdministrador iAdministrador) {
        this._iAdministrador = iAdministrador;
    }


    /**
     * Indica qué vista de vídeo debe utilizar un Administrador.
     *
     * <p>
     * Se devuelve la clase, no se crea todavía el objeto.
     *
     * Esto permite que la navegación de Vaadin utilice esa clase
     * como destino de navegación.
     */
    @Override
    public Class<? extends VerVideo> createVideo() {

        return VerVideodeAdministrador.class;
    }


    /**
     * Indica qué implementación de PerfilAjeno corresponde
     * a un Administrador.
     *
     * <p>
     * Al igual que en createVideo(), se devuelve la clase concreta
     * que debe utilizarse.
     */
    @Override
    public Class<? extends PerfilAjeno> createPerfilAjeno() {

        return PerfilAjenodeAdministrador.class;
    }


    /**
     * Crea una vista para mostrar los comentarios.
     *
     * <p>
     * En este caso no se devuelve una Class porque la vista
     * necesita recibir datos en su constructor:
     *
     *     - los comentarios
     *     - el ID del vídeo
     *     - la factoría de vistas
     *
     * Por eso aquí sí se crea directamente el objeto con new.
     */
    @Override
    public VerComentarios createVerComentarios(
            Set<Comentario> comentarios,
            int idvideo,
            ViewFactoryProvider viewFactory) {

        return new VerComentariosdeAdministrador(
                _iAdministrador,
                comentarios,
                idvideo,
                viewFactory);
    }


    /**
     * Indica qué tipo de elemento individual debe utilizarse
     * para representar un vídeo en la galería de un Administrador.
     *
     * <p>
     * Se devuelve la clase concreta que implementa el item.
     */
    @Override
    public Class<? extends UltimosVideos_item> createGaleriaItem() {

        return UltimosVideosdeAdministrador_item.class;
    }


    /**
     * Crea el elemento individual utilizado para mostrar
     * un comentario.
     *
     * <p>
     * Este método recibe el comentario concreto que se quiere
     * mostrar y la factoría de vistas.
     *
     * Por eso, al igual que createVerComentarios(), necesita
     * crear directamente una instancia mediante new.
     */
    @Override
    public VerComentarios_item createVerComentariosItem(
            Comentario comentario,
            ViewFactoryProvider viewFactory) {

        return new VerComentariosdeAdministrador_item(
                _iAdministrador,
                comentario,
                viewFactory);
    }
}