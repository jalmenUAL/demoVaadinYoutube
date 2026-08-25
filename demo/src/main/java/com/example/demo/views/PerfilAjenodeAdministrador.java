package com.example.demo.views;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.iAdministrador;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;

@Route("PerfilAjenodeAdministrador")
public class PerfilAjenodeAdministrador extends PerfilAjeno {

    /*
     * ============================================================
     * HERENCIA
     * ============================================================
     *
     * Esta clase hereda de PerfilAjeno, que a su vez hereda de
     * Perfil.
     *
     * Por tanto, reutilizamos toda la interfaz y funcionalidad
     * del perfil ajeno y solamente añadimos lo específico del
     * administrador.
     *
     * La jerarquía es:
     *
     * BaseParameterizedView<String>
     *             ↑
     *           Perfil
     *             ↑
     *        PerfilAjeno
     *             ↑
     * PerfilAjenodeAdministrador
     */


    /*
     * Servicio específico del administrador.
     *
     * Se utiliza para poder bloquear y desbloquear usuarios.
     */
    private final iAdministrador iAdministrador;

    /*
     * Botón que permitirá:
     *
     * - bloquear al usuario
     * - desbloquearlo si ya está bloqueado
     */
    private Button btnBloquear;


    public PerfilAjenodeAdministrador(
            iAdministrador iAdministrador,
            ViewFactoryProvider viewFactory) {

        /*
         * iAdministrador hereda de iRegistrado e iInicio, por lo
         * que también puede utilizarse allí donde se espera
         * un iInicio.
         *
         * Por eso podemos pasarlo al constructor de PerfilAjeno.
         */
        super(iAdministrador, viewFactory);

        this.iAdministrador = iAdministrador;
    }


    /*
     * ============================================================
     * CONSTRUCCIÓN
     * ============================================================
     */

    @Override
    protected void build(String parameter) {

        /*
         * Primero construimos TODO el perfil heredado:
         *
         * - banner
         * - avatar
         * - nombre
         * - vídeos publicados
         * - vídeos gustados
         * - botón de Youtubers seguidos
         *
         * Después añadimos únicamente el botón propio del
         * administrador.
         */
        super.build(parameter);


        /*
         * Creamos el botón.
         *
         * El texto se establecerá posteriormente en setParameter(),
         * cuando ya sepamos si el usuario está bloqueado o no.
         */
        btnBloquear = new Button();

        /*
         * LUMO_ERROR utiliza el estilo visual de una acción
         * potencialmente peligrosa.
         */
        btnBloquear.addThemeVariants(
                ButtonVariant.LUMO_ERROR);


        /*
         * topLayout pertenece a Perfil.
         *
         * Al ser protected podemos acceder a él desde la clase
         * hija y añadir nuestro botón junto al resto de controles.
         */
        topLayout.add(btnBloquear);
    }


    /*
     * ============================================================
     * EVENTOS
     * ============================================================
     */

    @Override
    protected void bindEvents() {

        /*
         * Perfil ya puede tener sus propios eventos.
         *
         * Los conservamos llamando a super.bindEvents().
         */
        super.bindEvents();

        /*
         * Añadimos el evento específico del administrador.
         */
        btnBloquear.addClickListener(
                e -> Bloquear());
    }


    /*
     * ============================================================
     * PARÁMETRO DE LA URL
     * ============================================================
     *
     * Sobrescribimos setParameter() porque necesitamos realizar
     * una acción adicional después de que Perfil haya recibido
     * el parámetro.
     */
    @Override
    public void setParameter(
            BeforeEvent event,
            String parameter) {

        /*
         * MUY IMPORTANTE:
         *
         * Primero dejamos que BaseParameterizedView/Perfil
         * procese el parámetro.
         *
         * Como consecuencia de ello, Perfil obtiene el usuario:
         *
         *     _usuario = findYoutuberById(parameter)
         *
         * Por tanto, después de super.setParameter() podemos
         * consultar _usuario.
         */
        super.setParameter(
                event,
                parameter);


        /*
         * Una vez que sabemos qué usuario estamos mostrando,
         * configuramos el texto del botón según su estado.
         */
        if (_usuario.getBloqueado()) {

            btnBloquear.setText(
                    "Quitar bloqueo");

        } else {

            btnBloquear.setText(
                    "Bloquear");
        }
    }


    /*
     * ============================================================
     * BLOQUEAR / DESBLOQUEAR
     * ============================================================
     */

    private void Bloquear() {

        /*
         * El mismo botón sirve para las dos operaciones.
         *
         * Si actualmente muestra "Bloquear", bloqueamos.
         */
        if (btnBloquear.getText()
                .equals("Bloquear")) {

            iAdministrador.bloquearUsuario(
                    _usuario.getLogin());

        } else {

            /*
             * Si muestra "Quitar bloqueo", significa que el usuario
             * ya estaba bloqueado, así que lo desbloqueamos.
             */
            iAdministrador.desbloquearUsuario(
                    _usuario.getLogin());
        }


        /*
         * Después de modificar el usuario en la base de datos
         * recargamos la página.
         *
         * Así se vuelve a construir la vista y el botón se
         * actualiza con el nuevo estado.
         */
        UI.getCurrent()
                .getPage()
                .reload();
    }
}