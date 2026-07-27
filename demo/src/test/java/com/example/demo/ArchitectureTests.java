package com.example.demo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.views.Administrador;
import com.example.demo.views.BaseView;
import com.example.demo.views.Buscar;
import com.example.demo.views.GaleradeVideos_item;
import com.example.demo.views.Inicio;
import com.example.demo.views.ListadeVideos_item;
import com.example.demo.views.NoLogueado;
import com.example.demo.views.Perfil;
import com.example.demo.views.PerfilAjenodeAdministrador;
import com.example.demo.views.PerfilAjenodeYoutuber;
import com.example.demo.views.PerfilPropio;
import com.example.demo.views.Registrado;
import com.example.demo.views.Registrar;
import com.example.demo.views.ServidordeCorreo;
import com.example.demo.views.VerComentarios_item;
import com.example.demo.views.VerComentariosdeAdministrador_item;
import com.example.demo.views.VerComentariosdeYoutuber;
import com.example.demo.views.VerVideo;
import com.example.demo.views.VerVideodeAdministrador;
import com.example.demo.views.VerVideodeYoutuber;
import com.example.demo.views.Videosrelacionados_item;
import com.example.demo.views.Youtuber;
import com.example.demo.views.Youtubersseguidos_item;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

public class ArchitectureTests {

    private static final Set<Class<?>> TIPOS_BASICOS = Set.of(
        String.class,
        int.class,
        Integer.class,
        long.class,
        Long.class,
        double.class,
        Double.class,
        float.class,
        Float.class,
        boolean.class,
        Boolean.class,
        char.class,
        Character.class
);

    private static final Set<String> METODOS_PATRON = Set.of(
            "configure",
            "build",
            "bindEvents",
            "configureNavigation",
            "onAttach",
            "beforeEnter",
            "afterNavigation",
            "setParameter");

    private static final Set<String> CLASES_UML = Set.of(

            "Administrador",
            "Buscar",
            "Comentar",
            "Configuracion",
            "GaleradeVideos",
            "GaleradeVideos_item",
            "Inicio",
            "ListadeVideos",
            "ListadeVideos_item",
            "Login",
            "NoLogueado",
            "Perfil",
            "PerfilAjeno",
            "PerfilAjenodeAdministrador",
            "PerfilAjenodeYoutuber",
            "PerfilPropio",
            "PublicarVideo",
            "Registrado",
            "Registrar",
            "ResultadodeBusqueda",
            "ServidordeCorreo",
            "UltimosVideos",
            "UltimosVideos_item",
            "UltimosVideosdeAdministrador",
            "UltimosVideosdeAdministrador_item",
            "UltimosVideosdeYoutuber",
            "UltimosVideosdeYoutuber_item",
            "Usuariosdenunciados",
            "Usuariosdenunciados_item",
            "VerComentarios",
            "VerComentarios_item",
            "VerComentariosdeAdministrador",
            "VerComentariosdeAdministrador_item",
            "VerComentariosdeYoutuber",
            "VerComentariosdeYoutuber_item",
            "VerVideo",
            "VerVideodeAdministrador",
            "VerVideodeYoutuber",
            "Videosgustados",
            "Videosgustados_item",
            "Videospublicados",
            "Videospublicados_item",
            "Videosrelacionados",
            "Videosrelacionados_item",
            "Youtuber",
            "Youtubersseguidos",
            "Youtubersseguidos_item"

    );

    private static final Map<String, String> HERENCIA_UML = Map.ofEntries(

            Map.entry("Registrado", "Inicio"),
            Map.entry("NoLogueado", "Inicio"),

            Map.entry("Administrador", "Registrado"),
            Map.entry("Youtuber", "Registrado"),

            Map.entry("PerfilPropio", "Perfil"),
            Map.entry("PerfilAjeno", "Perfil"),

            Map.entry("PerfilAjenodeAdministrador", "PerfilAjeno"),
            Map.entry("PerfilAjenodeYoutuber", "PerfilAjeno"),

            Map.entry("UltimosVideos", "GaleradeVideos"),
            Map.entry("ResultadodeBusqueda", "GaleradeVideos"),

            Map.entry("UltimosVideos_item", "GaleradeVideos_item"),
            Map.entry("ResultadodeBusqueda_item", "GaleradeVideos_item"),

            Map.entry("Videosgustados", "ListadeVideos"),
            Map.entry("Videospublicados", "ListadeVideos"),

            Map.entry("Videosgustados_item", "ListadeVideos_item"),
            Map.entry("Videospublicados_item", "ListadeVideos_item"),

            Map.entry("UltimosVideosdeAdministrador", "UltimosVideos"),
            Map.entry("UltimosVideosdeYoutuber", "UltimosVideos"),

            Map.entry("UltimosVideosdeAdministrador_item",
                    "UltimosVideos_item"),
            Map.entry("UltimosVideosdeYoutuber_item",
                    "UltimosVideos_item"),

            Map.entry("VerComentariosdeAdministrador",
                    "VerComentarios"),
            Map.entry("VerComentariosdeYoutuber",
                    "VerComentarios"),

            Map.entry("VerComentariosdeAdministrador_item",
                    "VerComentarios_item"),
            Map.entry("VerComentariosdeYoutuber_item",
                    "VerComentarios_item"),

            Map.entry("VerVideodeAdministrador", "VerVideo"),
            Map.entry("VerVideodeYoutuber", "VerVideo")

    );

    private static final Map<Class<?>, Set<String>> METODOS_UML = Map.ofEntries(

            Map.entry(
                    Administrador.class,
                    Set.of("Usuariosdenunciados")),

            Map.entry(
                    Buscar.class,
                    Set.of("ResultadodeBusqueda")),

            Map.entry(
                    GaleradeVideos_item.class,
                    Set.of("VerVideo")),

            Map.entry(
                    Inicio.class,
                    Set.of("Buscar",
                            "UltimosVideos")),

            Map.entry(
                    ListadeVideos_item.class,
                    Set.of("VerVideo")),

            Map.entry(
                    NoLogueado.class,
                    Set.of("Login",
                            "Registrar")),

            Map.entry(
                    Perfil.class,
                    Set.of("Videosgustados",
                            "Videospublicados",
                            "Youtubersseguidos")),

            Map.entry(
                    PerfilAjenodeAdministrador.class,
                    Set.of("Bloquear")),

            Map.entry(
                    PerfilAjenodeYoutuber.class,
                    Set.of("Denunciar",
                            "Seguir")),

            Map.entry(
                    PerfilPropio.class,
                    Set.of("PublicarVideo",
                            "Configuracion")),

            Map.entry(
                    Registrado.class,
                    Set.of("Logout")),

            Map.entry(
                    Registrar.class,
                    Set.of("EnviarCorreo")),

            Map.entry(
                    ServidordeCorreo.class,
                    Set.of("EnviarCorreo")),

            Map.entry(
                    VerComentarios_item.class,
                    Set.of("PerfilAjeno")),

            Map.entry(
                    VerComentariosdeAdministrador_item.class,
                    Set.of("eliminar")),

            Map.entry(
                    VerComentariosdeYoutuber.class,
                    Set.of("Comentar")),

            Map.entry(
                    VerVideo.class,
                    Set.of("Videosrelacionados",
                            "VerComentarios",
                            "PerfilAjeno")),

            Map.entry(
                    VerVideodeAdministrador.class,
                    Set.of("borrar")),

            Map.entry(
                    VerVideodeYoutuber.class,
                    Set.of("like")),

            Map.entry(
                    Videosrelacionados_item.class,
                    Set.of("VerVideo")),

            Map.entry(
                    Youtuber.class,
                    Set.of("PerfilPropio")),

            Map.entry(
                    Youtubersseguidos_item.class,
                    Set.of("PerfilAjeno"))

    );

    private static final Map<Class<?>, Set<String>> ATRIBUTOS_UML = Map.ofEntries(

            Map.entry(
                    Inicio.class,
                    Set.of("_buscar",
                            "_ultimosVideos")),

            Map.entry(
                    Administrador.class,
                    Set.of("_usuariosdenunciados")),

            Map.entry(
                    NoLogueado.class,
                    Set.of("_login",
                            "_registrar")),

            Map.entry(
                    Perfil.class,
                    Set.of("_videosgustados",
                            "_videospublicados")),

            Map.entry(
                    PerfilPropio.class,
                    Set.of("_configuracion",
                            "_publicarVideo")),

            Map.entry(
                    Buscar.class,
                    Set.of("_resultadodeBusqueda")),

            Map.entry(
                    Registrar.class,
                    Set.of("_servidordeCorreo")),

            Map.entry(
                    VerComentarios_item.class,
                    Set.of("_perfilAjeno")),

            Map.entry(
                    VerVideo.class,
                    Set.of("_verComentarios",
                            "_perfilAjeno",
                            "_videosrelacionados")),

            Map.entry(
                    Youtuber.class,
                    Set.of("_PerfilPropio"))

    );

    @Test
    void comprobarArquitecturaDeLasVistas() {

        for (Class<?> clase : METODOS_UML.keySet()) {

            comprobarMetodosPermitidos(
                    clase);

        }
    }

    private void comprobarMetodosPermitidos(Class<?> clase) {

        Set<String> permitidos = obtenerMetodosPermitidos(clase);

        for (Method method : clase.getDeclaredMethods()) {

            if (method.isSynthetic() || method.isBridge()) {
                continue;
            }

            if (!permitidos.contains(method.getName())) {
                fail("El método '" + method.getName()
                        + "' no está permitido en "
                        + clase.getSimpleName());
            }
        }
    }

    private Set<String> obtenerMetodosPermitidos(Class<?> clase) {

        Set<String> permitidos = new HashSet<>(METODOS_PATRON);

        Class<?> actual = clase;

        while (actual != null) {

            if (METODOS_UML.containsKey(actual)) {
                permitidos.addAll(METODOS_UML.get(actual));
            }

            actual = actual.getSuperclass();
        }

        return permitidos;
    }

    private void comprobarAtributos(
            Class<?> clase,
            Set<String> atributosObligatorios) {

        List<String> atributosDeclarados = Arrays.stream(clase.getDeclaredFields())
                .map(Field::getName)
                .toList();

        for (String atributo : atributosObligatorios) {

            if (!atributosDeclarados.contains(atributo)) {

                fail("Falta el atributo '" + atributo
                        + "' en "
                        + clase.getSimpleName());

            }
        }
    }

    @Test
    void comprobarAtributosUML() {

        for (Class<?> clase : ATRIBUTOS_UML.keySet()) {

            comprobarAtributos(
                    clase,
                    ATRIBUTOS_UML.get(clase));

        }
    }

    @Test
    void comprobarClasesDelModelo() {

        for (String nombreClase : CLASES_UML) {

            try {

                Class.forName(
                        "com.example.demo.views."
                                + nombreClase);

            } catch (ClassNotFoundException e) {

                fail("Falta la clase "
                        + nombreClase);

            }

        }

    }

    @Test
    void comprobarHerenciaDelModelo() throws Exception {

        for (Map.Entry<String, String> entry : HERENCIA_UML.entrySet()) {

            String hija = entry.getKey();
            String padre = entry.getValue();

            Class<?> claseHija = Class.forName("com.example.demo.views." + hija);

            Class<?> clasePadre = Class.forName("com.example.demo.views." + padre);

            if (!claseHija.getSuperclass().equals(clasePadre)) {

                fail("La clase " + hija
                        + " debe heredar de "
                        + padre);

            }
        }
    }

    @Test
    void comprobarConstructores() throws Exception {

        for (String nombreClase : CLASES_UML) {

            Class<?> clase = Class.forName("com.example.demo.views." + nombreClase);

            if (!BaseView.class.isAssignableFrom(clase)) {
                continue;
            }

            if (clase.getDeclaredConstructors().length != 1) {
                fail("La clase " + nombreClase
                        + " debe declarar exactamente un constructor.");
            }

            boolean constructorValido = false;

            Constructor<?> constructor = clase.getDeclaredConstructors()[0];

            // Debe tener exactamente un parámetro
            if (constructor.getParameterCount() != 1) {
                fail("El constructor de " + nombreClase
                        + " debe tener exactamente un parámetro.");
            }

            Class<?> parametro = constructor.getParameterTypes()[0];

            // Interfaz (servicio)
            if (parametro.isInterface()) {
                constructorValido = true;
            }

            // Objeto del dominio
            else if (parametro.getPackageName()
                    .equals("com.example.demo.domain")) {
                constructorValido = true;
            }

            // Set<T> donde T pertenece al dominio
            else if (Set.class.isAssignableFrom(parametro)) {

                Type tipo = constructor.getGenericParameterTypes()[0];

                if (tipo instanceof ParameterizedType parameterizedType) {

                    Type tipoGenerico = parameterizedType.getActualTypeArguments()[0];

                    if (tipoGenerico instanceof Class<?> claseGenerica
                            && claseGenerica.getPackageName()
                                    .equals("com.example.demo.domain")) {

                        constructorValido = true;
                    }
                }
            }

            if (!constructorValido) {
                fail("El constructor de " + nombreClase
                        + " debe recibir una interfaz, un objeto del dominio o un Set de objetos del dominio.");
            }
        }
    }


    @Test
void comprobarParametrosBDPrincipal() {

    for (Method metodo : BDPrincipal.class.getDeclaredMethods()) {

        for (Class<?> parametro : metodo.getParameterTypes()) {

            if (!TIPOS_BASICOS.contains(parametro)) {
                fail("El método '" + metodo.getName()
                        + "' tiene un parámetro no permitido: "
                        + parametro.getSimpleName());
            }
        }
    }
}

    @Test
    void lasVistasNoPuedenDependerDeLosComponents() {

        JavaClasses importedClasses = new ClassFileImporter()
                .importPackages("com.example.demo");

        ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..views..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..components..");

        ArchRule rule2 = noClasses()
                .that()
                .resideInAPackage("..views..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..repositories..");
          ArchRule rule3 = noClasses()
                .that()
                .resideInAPackage("..views..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..facade..");
        ArchRule rule4 = noClasses()
                .that()
                .resideInAPackage("..facade..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..repositories..");
        ArchRule rule5 = noClasses()
                .that()
                .resideInAPackage("..components..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..services..");


        rule.check(importedClasses);
        rule2.check(importedClasses);
        rule3.check(importedClasses);
        rule4.check(importedClasses);
        rule5.check(importedClasses);
    }
}