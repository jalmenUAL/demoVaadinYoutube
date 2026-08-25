package com.example.demo.views;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iYoutuber;
import com.example.demo.tables.Youtuber;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("PerfilAjenodeYoutuber")
public class PerfilAjenodeYoutuber extends PerfilAjeno {

    /*
     * Esta vista representa el perfil de otro Youtuber cuando quien está
     * navegando es un Youtuber autenticado.
     *
     * Hereda toda la estructura visual de Perfil y PerfilAjeno y añade
     * las acciones específicas que puede realizar un Youtuber:
     *
     * - Seguir / dejar de seguir.
     * - Denunciar / quitar denuncia.
     */
    private final iYoutuber iYoutuber;

    private Button btnSeguir;
    private Button btnDenunciar;

    public PerfilAjenodeYoutuber(
            iYoutuber iYoutuber,
            ViewFactoryProvider viewFactory) {

        super(iYoutuber, viewFactory);
        this.iYoutuber = iYoutuber;
    }

    /*
     * build() recibe el parámetro de la URL.
     *
     * Primero llamamos a super.build(parameter), que construye el perfil
     * completo y obtiene el Youtuber que estamos visualizando.
     *
     * Después añadimos los botones específicos de esta vista.
     */
    @Override
    protected void build(String parameter) {

        super.build(parameter);

        btnSeguir = new Button();
        btnDenunciar = new Button();

        btnSeguir.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        btnDenunciar.addThemeVariants(
                ButtonVariant.LUMO_PRIMARY);

        /*
         * topLayout ha sido creado en Perfil, por lo que podemos reutilizarlo
         * y añadir nuestros botones al mismo layout.
         */
        topLayout.add(
                btnSeguir,
                btnDenunciar);
    }

    /*
     * Los eventos también se construyen mediante herencia.
     *
     * Primero se registran los eventos definidos por Perfil y después
     * añadimos los eventos propios de esta clase.
     */
    @Override
    protected void bindEvents() {

        super.bindEvents();

        btnSeguir.addClickListener(e -> Seguir());
        btnDenunciar.addClickListener(e -> Denunciar());
    }

    /*
     * setParameter() es llamado por Vaadin cuando la navegación contiene
     * un parámetro.
     *
     * Por ejemplo:
     *
     *     /PerfilAjenodeYoutuber/pepe
     *
     * En ese caso "pepe" llega como parameter.
     *
     * Es importante llamar primero a super.setParameter(), porque la clase
     * Perfil necesita ese parámetro para cargar _usuario.
     */
    @Override
    public void setParameter(
            BeforeEvent event,
            String parameter) {

        super.setParameter(event, parameter);

        /*
         * Obtenemos el usuario que actualmente ha iniciado sesión.
         *
         * El usuario autenticado está almacenado en Spring Security.
         */
        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        /*
         * En esta vista sabemos que el usuario autenticado es un Youtuber,
         * por lo que podemos obtener nuestra entidad desde getPrincipal().
         *
         * Esto funciona porque CustomAuthProvider creó el
         * UsernamePasswordAuthenticationToken utilizando la entidad
         * Youtuber como principal.
         */
        Youtuber youtuber =
                (Youtuber) auth.getPrincipal();

        // -------------------------------------------------
        // Estado del botón "Seguir"
        // -------------------------------------------------

        /*
         * _usuario es el Youtuber cuyo perfil estamos viendo.
         *
         * seguido_por contiene los Youtubers que siguen a _usuario.
         *
         * Si el usuario autenticado está dentro de esa colección,
         * significa que ya lo está siguiendo.
         */
        if (_usuario.getSeguido_por().contains(youtuber)) {

            btnSeguir.setText("Dejar de seguir");

        } else {

            btnSeguir.setText("Seguir");
        }

        // -------------------------------------------------
        // Estado del botón "Denunciar"
        // -------------------------------------------------

        /*
         * denunciado_por contiene los usuarios que han denunciado
         * a _usuario.
         *
         * Por tanto, comprobamos si el usuario autenticado ya ha
         * denunciado al usuario cuyo perfil estamos viendo.
         */
        if (_usuario.getDenunciado_por().contains(youtuber)) {

            btnDenunciar.setText("Quitar denuncia");

        } else {

            btnDenunciar.setText("Denunciar");
        }

        // -------------------------------------------------
        // No permitir acciones sobre uno mismo
        // -------------------------------------------------

        /*
         * Un usuario no puede seguirse ni denunciarse a sí mismo.
         *
         * Comparamos los login porque login es el identificador
         * del Youtuber y es un String, por lo que se debe utilizar
         * equals() y no ==.
         */
        if (_usuario.getLogin().equals(youtuber.getLogin())) {

            btnSeguir.setVisible(false);
            btnDenunciar.setVisible(false);
        }
    }

    /*
     * Ejecuta la acción de seguir o dejar de seguir.
     *
     * El usuario que aparece en el perfil (_usuario) es el usuario
     * que queremos seguir.
     *
     * El usuario autenticado (seguidor) es quien realiza la acción.
     */
    public void Seguir() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        Youtuber seguidor =
                (Youtuber) auth.getPrincipal();

        /*
         * El texto del botón representa el estado actual.
         *
         * Si pone "Seguir", todavía no lo seguimos.
         */
        if (btnSeguir.getText().equals("Seguir")) {

            iYoutuber.seguirUsuario(
                    _usuario.getLogin(),
                    seguidor.getLogin());

        } else {

            /*
             * Si el botón pone "Dejar de seguir", significa que ya
             * existe la relación y queremos eliminarla.
             */
            iYoutuber.dejardeseguirUsuario(
                    _usuario.getLogin(),
                    seguidor.getLogin());
        }

        /*
         * Recargamos la vista para volver a consultar los datos
         * actualizados de la base de datos y actualizar el estado
         * de los botones.
         */
        UI.getCurrent()
                .getPage()
                .reload();
    }

    /*
     * Ejecuta la acción de denunciar o quitar una denuncia.
     *
     * Igual que en Seguir():
     *
     * _usuario -> usuario que estamos viendo.
     * seguidor -> usuario autenticado que realiza la acción.
     */
    public void Denunciar() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        Youtuber denunciante =
                (Youtuber) auth.getPrincipal();

        /*
         * Si el botón muestra "Denunciar", creamos la relación.
         */
        if (btnDenunciar.getText().equals("Denunciar")) {

            iYoutuber.denunciarUsuario(
                    _usuario.getLogin(),
                    denunciante.getLogin());

        } else {

            /*
             * Si ya existe la denuncia, la eliminamos.
             */
            iYoutuber.quitardenunciaUsuario(
                    _usuario.getLogin(),
                    denunciante.getLogin());
        }

        /*
         * Volvemos a cargar la vista para mostrar el nuevo estado.
         */
        UI.getCurrent()
                .getPage()
                .reload();
    }
}