package com.example.demo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.patterns.BaseView;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

public class DemoApplicationTests extends VisualParadigmModel {

        /* Tipos básicos */

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

        /* Métodos permitidos por los patrones */

        private static final Set<String> METODOS_PATRON = Set.of(

                        "build",
                        "bindEvents",
                        "setOnResultado",
                        "buildList",
                        "buildContainer",
                        "buildItems");

        /* Comprobación de los métodos de UML y de los patrones */

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

        /* Comprobación de los atributos de UML */

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

        void comprobarClasesUML() {
                // Escaneador personalizado que NO ignora interfaces ni clases abstractas
                ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                                false) {
                        @Override
                        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                                return true;
                        }

                        @Override
                        protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
                                return true;
                        }
                };

                // Añadir filtro para incluir cualquier tipo de clase
                provider.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);

                String paqueteBase = "com.example.demo.views";

                // Buscar en el paquete y en TODOS sus subpaquetes
                Set<String> clasesEncontradas = provider.findCandidateComponents(paqueteBase)
                                .stream()
                                .map(beanDef -> {
                                        String fullClassName = beanDef.getBeanClassName();
                                        // Extrae solo el nombre simple: de
                                        // "com.example.demo.views.registrado.Registrado" a "Registrado"
                                        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
                                })
                                .collect(Collectors.toSet());

                // Comprobar presencia
                for (String nombreClase : CLASES_UML) {
                        if (!clasesEncontradas.contains(nombreClase)) {
                                fail("Falta la clase " + nombreClase + " en " + paqueteBase + " (o sus subcarpetas)");
                        }
                }
        }

        @Test
        void comprobarHerenciaUML() throws Exception {
                // 1. Crear el escaneador que acepte cualquier clase (concretas, abstractas,
                // etc.)
                ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                                false) {
                        @Override
                        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                                return true;
                        }

                        @Override
                        protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
                                return true;
                        }
                };

                provider.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);

                String paqueteBase = "com.example.demo.views";

                // 2. Mapear NombreSimple -> NombreCualificadoCompleto
                // Ejemplo: "Registrado" -> "com.example.demo.views.registrado.Registrado"
                Map<String, String> mapaClasesCompletas = new HashMap<>();

                for (var component : provider.findCandidateComponents(paqueteBase)) {
                        String fullClassName = component.getBeanClassName();
                        String simpleName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
                        mapaClasesCompletas.put(simpleName, fullClassName);
                }

                // 3. Comprobar la herencia buscando los nombres completos en el mapa
                for (Map.Entry<String, String> entry : HERENCIA_UML.entrySet()) {
                        String hija = entry.getKey();
                        String padre = entry.getValue();

                        String fullHija = mapaClasesCompletas.get(hija);
                        String fullPadre = mapaClasesCompletas.get(padre);

                        if (fullHija == null) {
                                fail("Falta la clase hija: " + hija);
                        }
                        if (fullPadre == null) {
                                fail("Falta la clase padre: " + padre);
                        }

                        Class<?> claseHija = Class.forName(fullHija);
                        Class<?> clasePadre = Class.forName(fullPadre);

                        if (claseHija.getSuperclass() == null || !claseHija.getSuperclass().equals(clasePadre)) {
                                fail("La clase " + hija + " debe heredar de " + padre);
                        }
                }
        }

        // Método auxiliar para buscar clases recursivamente en subcarpetas de views
        private Map<String, String> obtenerMapaClasesViews() {
                ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                                false) {
                        @Override
                        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                                return true;
                        }

                        @Override
                        protected boolean isCandidateComponent(MetadataReader metadataReader)
                                        throws IOException {
                                return true;
                        }
                };

                provider.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);

                String paqueteBase = "com.example.demo.views";
                Map<String, String> mapaClasesCompletas = new HashMap<>();

                for (var component : provider.findCandidateComponents(paqueteBase)) {
                        String fullClassName = component.getBeanClassName();
                        String simpleName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
                        mapaClasesCompletas.put(simpleName, fullClassName);
                }

                return mapaClasesCompletas;

        }

        @Test
        void comprobarConstructoresUML() throws Exception {
                Map<String, String> mapaClasesViews = obtenerMapaClasesViews();

                for (String nombreClase : CLASES_UML) {
                        String fullClassName = mapaClasesViews.get(nombreClase);

                        if (fullClassName == null) {
                                fail("Falta la clase " + nombreClase + " en com.example.demo.views o sus subcarpetas");
                                continue;
                        }

                        Class<?> clase = Class.forName(fullClassName);

                        // Solo validamos clases que extiendan de BaseView
                        if (!BaseView.class.isAssignableFrom(clase)) {
                                continue;
                        }

                        Constructor<?>[] constructores = clase.getDeclaredConstructors();

                        if (constructores.length != 1) {
                                fail("La clase " + nombreClase
                                                + " debe declarar exactamente un constructor explícito.");
                        }

                        Constructor<?> constructor = constructores[0];

                        Class<?>[] parametros = constructor.getParameterTypes();

                        // 1. Si no recibe parámetros (vistas genéricas/estáticas en common), es válido
                        if (parametros.length == 0) {
                                continue;
                        }

                        // Validar que CADA parámetro recibido corresponda a un tipo permitido en la
                        // arquitectura
                        for (int i = 0; i < parametros.length; i++) {
                                boolean parametroValido = esParametroValido(parametros[i], i, constructor);

                                if (!parametroValido) {
                                        fail("El parámetro '" + parametros[i].getSimpleName() + "' (posición " + (i + 1)
                                                        + ") en el constructor de "
                                                        + nombreClase
                                                        + " no cumple con las dependencias permitidas por la arquitectura.");
                                }
                        }
                }
        }

         
        /**
         * Evalúa si un parámetro cumple con alguno de los roles arquitectónicos
         * válidos.
         */
        private boolean esParametroValido(Class<?> parametro, int indiceParametro, Constructor<?> constructor) {
                String paqueteParam = parametro.getPackageName();

                // 0. Tipos básicos, primitivos, wrappers, String e InputStream
                if (TIPOS_BASICOS.contains(parametro)) {
                        return true;
                }

                // 1. Interfaz de Servicio (Lógica de negocio / BD)
                if (parametro.isInterface() && paqueteParam.equals("com.example.demo.services.interfaces")) {
                        return true;
                }

                // 2. Provider / Factory (Variantes por rol/permisos)
                if (paqueteParam.equals("com.example.demo.factories")) {
                        return true;
                }

                // 3. Item único de modelo (Vista detalle/edición)
                if (paqueteParam.equals("com.example.demo.tables")) {
                        return true;
                }

                // 4. Colecciones (Set, List, Collection) de items del modelo
                if (Collection.class.isAssignableFrom(parametro)) {
                        Type tipo = constructor.getGenericParameterTypes()[indiceParametro];
                        if (tipo instanceof ParameterizedType parameterizedType) {
                                Type tipoGenerico = parameterizedType.getActualTypeArguments()[0];
                                if (tipoGenerico instanceof Class<?> claseGenerica
                                                && claseGenerica.getPackageName().equals("com.example.demo.tables")) {
                                        return true;
                                }
                        }
                }

                // 5. Infraestructura y Seguridad (Spring Security / Framework)
                if (parametro.getName().equals("org.springframework.security.authentication.AuthenticationManager")
                                || paqueteParam.startsWith("org.springframework")) {
                        return true;
                }

                return false;
        }

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

        @Test
        void comprobarPatronEnViews() throws Exception {
                // 1. Escanear recursivamente todas las clases dentro del paquete views
                Map<String, String> mapaClasesViews = obtenerMapaClasesViews();

                String paquetePatrones = "com.example.demo.patterns";

                for (Map.Entry<String, String> entry : mapaClasesViews.entrySet()) {
                        String nombreSimple = entry.getKey();
                        String nombreCompleto = entry.getValue();

                        Class<?> claseView = Class.forName(nombreCompleto);

                        // Si es una interfaz o clase abstracta dentro de views, la ignoramos si solo
                        // deseas validar componentes concretos.
                        // Si deseas validar absolutamente TODO lo que esté en views, retira estas dos
                        // líneas:
                        if (claseView.isInterface()) {
                                continue;
                        }

                        boolean heredaDePatron = tienePatronEnJerarquia(claseView, paquetePatrones);

                        if (!heredaDePatron) {
                                fail("La clase " + nombreSimple + " (" + nombreCompleto
                                                + ") debe heredar de una clase o implementar una interfaz del paquete '"
                                                + paquetePatrones + "'.");
                        }
                }
        }

        /**
         * Recorre recursivamente las superclases e interfaces implementadas
         * para verificar si alguna pertenece al paquete de patrones.
         */
        private boolean tienePatronEnJerarquia(Class<?> clase, String paquetePatrones) {
                if (clase == null || clase.equals(Object.class)) {
                        return false;
                }

                // 1. Comprobar la superclase directa
                Class<?> superClase = clase.getSuperclass();
                if (superClase != null && superClase.getPackageName().equals(paquetePatrones)) {
                        return true;
                }

                // 2. Comprobar las interfaces implementadas directamente por esta clase
                for (Class<?> interfaz : clase.getInterfaces()) {
                        if (interfaz.getPackageName().equals(paquetePatrones)) {
                                return true;
                        }
                        // Comprobación recursiva en interfaces padre
                        if (tienePatronEnJerarquia(interfaz, paquetePatrones)) {
                                return true;
                        }
                }

                // 3. Subir de forma recursiva por la jerarquía de clases padre
                return tienePatronEnJerarquia(superClase, paquetePatrones);
        }
}