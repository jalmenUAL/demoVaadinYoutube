package com.example.demo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.demo.facade.BDPrincipal;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

public class DemoApplicationTests extends VisualParadigmModel {

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
                        Character.class, 
						InputStream.class);

        private static final Set<String> METODOS_PATRON = Set.of(
                         
                        "build",
                        "bindEvents",
                        "setOnResultado", 
                        "buildList",
                        "buildContainer",
                        "buildItems"
                        );

       

        @Test
        void comprobarMetodosUML() {

                for (Class<?> clase : METODOS_UML.keySet()) {

                        comprobarMetodosPermitidos(clase);

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

        @Test
        void comprobarAtributosUML() {

                for (Class<?> clase : ATRIBUTOS_UML.keySet()) {

                        comprobarAtributos(
                                        clase,
                                        ATRIBUTOS_UML.get(clase));

                }
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

       /*  @Test
        void comprobarClasesUML() {

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

        }*/

					/* 
        @Test
        void comprobarHerenciaUML() throws Exception {

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
				*/

			/* 
        @Test
        void comprobarConstructoresUML() throws Exception {

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
				*/

        @Test
        void comprobarDependenciasBDPrincipal() {

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
        void ComprobarDependenciasComponentesUML() {

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