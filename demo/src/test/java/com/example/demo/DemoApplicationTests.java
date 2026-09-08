package com.example.demo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;

import com.example.demo.facade.BDPrincipal;
import com.example.demo.patterns.BaseView;
import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaCall;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaConstructorCall;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaFieldAccess;
import com.tngtech.archunit.core.domain.JavaParameterizedType;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;

public class DemoApplicationTests extends VisualParadigmModel {

        /* Tipos básicos autorizados */

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

        private static final String PAQUETE_PATRONES = "com.example.demo.patterns";
        private static final String PREFIJO_EXTERNAL = "com.example.demo.views.external.";

        /*
         * -------------------------------------------------------------------------
         * COMPROBACIÓN DE MÉTODOS
         * -------------------------------------------------------------------------
         */

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

        /*
         * -------------------------------------------------------------------------
         * COMPROBACIÓN DE ATRIBUTOS
         * -------------------------------------------------------------------------
         */

        @Test
        void comprobarAtributosUML() {
                for (Class<?> clase : ATRIBUTOS_UML.keySet()) {
                        comprobarAtributos(clase, ATRIBUTOS_UML.get(clase));
                }
        }

        private void comprobarAtributos(Class<?> clase, Set<String> atributosObligatorios) {
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

        /*
         * -------------------------------------------------------------------------
         * COMPROBACIÓN DE CLASES Y HERENCIA UML
         * -------------------------------------------------------------------------
         */

        @Test
        void comprobarClasesUML() {
                ClassPathScanningCandidateComponentProvider provider = crearEscaneadorSinFiltros();
                String paqueteBase = "com.example.demo.views";

                Set<String> clasesEncontradas = provider.findCandidateComponents(paqueteBase)
                                .stream()
                                .map(beanDef -> {
                                        String fullClassName = beanDef.getBeanClassName();
                                        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
                                })
                                .collect(Collectors.toSet());

                for (String nombreClase : CLASES_UML) {
                        if (!clasesEncontradas.contains(nombreClase)) {
                                fail("Falta la clase " + nombreClase + " en " + paqueteBase + " (o sus subcarpetas)");
                        }
                }
        }

        @Test
        void comprobarHerenciaUML() throws Exception {
                Map<String, String> mapaClasesCompletas = obtenerMapaClasesViews();

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

        /*
         * -------------------------------------------------------------------------
         * COMPROBACIÓN DE CONSTRUCTORES
         * -------------------------------------------------------------------------
         */

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

                        if (!BaseView.class.isAssignableFrom(clase)) {
                                continue;
                        }

                        if (fullClassName.contains(".views.common.") || fullClassName.contains(".views.external.")) {
                                continue;
                        }

                        Constructor<?>[] constructores = clase.getDeclaredConstructors();
                        boolean tieneConstructorValido = false;

                        for (Constructor<?> constructor : constructores) {
                                Class<?>[] parametros = constructor.getParameterTypes();

                                if (parametros.length == 0) {
                                        continue;
                                }

                                boolean todosParametrosValidos = true;
                                for (int i = 0; i < parametros.length; i++) {
                                        if (!esParametroValido(parametros[i], i, constructor)) {
                                                todosParametrosValidos = false;
                                                break;
                                        }
                                }

                                if (todosParametrosValidos) {
                                        tieneConstructorValido = true;
                                        break;
                                }
                        }

                        if (!tieneConstructorValido) {
                                fail("La clase " + nombreClase
                                                + " debe declarar al menos un constructor con parámetros válidos (Servicios, Factories, Tables, Auth, o Tipos Básicos).");
                        }
                }
        }

        private boolean esParametroValido(Class<?> parametro, int indiceParametro, Constructor<?> constructor) {
                String paqueteParam = parametro.getPackageName();

                if (TIPOS_BASICOS.contains(parametro)) {
                        return true;
                }

                if (parametro.isInterface() && paqueteParam.equals("com.example.demo.services.interfaces")) {
                        return true;
                }

                if (paqueteParam.equals("com.example.demo.factories")) {
                        return true;
                }

                if (paqueteParam.equals("com.example.demo.tables")) {
                        return true;
                }

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

                if (parametro.getName().equals("org.springframework.security.authentication.AuthenticationManager")
                                || paqueteParam.startsWith("org.springframework")) {
                        return true;
                }

                return false;
        }

        /*
         * -------------------------------------------------------------------------
         * COMPROBACIÓN DE PATRONES DE ARQUITECTURA (JERARQUÍA PATTERNS)
         * -------------------------------------------------------------------------
         */

        @Test
        @DisplayName("Auditar que todas las vistas hereden de una clase base del paquete de patrones")
        void comprobarPatronEnViews() throws Exception {
                Map<String, String> mapaClasesViews = obtenerMapaClasesViews();

                for (Map.Entry<String, String> entry : mapaClasesViews.entrySet()) {
                        String nombreSimple = entry.getKey();
                        String nombreCompleto = entry.getValue();

                        if (nombreCompleto.startsWith(PREFIJO_EXTERNAL)) {
                                continue;
                        }

                        Class<?> claseView = Class.forName(nombreCompleto);

                        if (claseView.isInterface()) {
                                continue;
                        }

                        boolean heredaDePatron = tienePatronEnJerarquia(claseView, PAQUETE_PATRONES);

                        if (!heredaDePatron) {
                                fail(String.format(
                                                "ERROR DE ARQUITECTURA:\n" +
                                                                "-> La clase '%s' (%s) NO hereda de ninguna clase del paquete '%s'.\n"
                                                                +
                                                                "-> Revisa la jerarquía de herencia de '%s'.",
                                                nombreSimple, nombreCompleto, PAQUETE_PATRONES, nombreSimple));
                        }
                }
        }

        /**
         * Recorre de forma limpia y directa la cadena de superclases hacia arriba
         * usando el nombre completo de la clase para evitar fallos de resolución de
         * paquetes en genéricos.
         */
        private boolean tienePatronEnJerarquia(Class<?> clase, String paquetePatrones) {
                Class<?> actual = clase;

                while (actual != null && !actual.equals(Object.class)) {
                        // Comprobación mediante la cadena de nombre completo (robusto en cualquier
                        // ClassLoader)
                        if (actual.getName().startsWith(paquetePatrones + ".")) {
                                return true;
                        }

                        actual = actual.getSuperclass();
                }

                return false;
        }

        /*
         * -------------------------------------------------------------------------
         * COMPROBACIÓN DE CAPA DE DATOS Y REGLAS DE ARQUITECTURA ARCHUNIT
         * -------------------------------------------------------------------------
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
                                .that().resideInAPackage("..views..")
                                .should().dependOnClassesThat().resideInAPackage("..components..");

                ArchRule rule2 = noClasses()
                                .that().resideInAPackage("..views..")
                                .should().dependOnClassesThat().resideInAPackage("..repositories..");

                ArchRule rule3 = noClasses()
                                .that().resideInAPackage("..views..")
                                .should().dependOnClassesThat().resideInAPackage("..facade..");

                ArchRule rule4 = noClasses()
                                .that().resideInAPackage("..facade..")
                                .should().dependOnClassesThat().resideInAPackage("..repositories..");

                ArchRule rule5 = noClasses()
                                .that().resideInAPackage("..components..")
                                .should().dependOnClassesThat().resideInAPackage("..services..");

                rule.check(importedClasses);
                rule2.check(importedClasses);
                rule3.check(importedClasses);
                rule4.check(importedClasses);
                rule5.check(importedClasses);
        }

        /*
         * -------------------------------------------------------------------------
         * MÉTODOS AUXILIARES Y ESCANEO DE CLASES
         * -------------------------------------------------------------------------
         */

        private Map<String, String> obtenerMapaClasesViews() {
                ClassPathScanningCandidateComponentProvider provider = crearEscaneadorSinFiltros();
                String paqueteBase = "com.example.demo.views";
                Map<String, String> mapaClasesCompletas = new HashMap<>();

                for (var component : provider.findCandidateComponents(paqueteBase)) {
                        String fullClassName = component.getBeanClassName();
                        String simpleName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
                        mapaClasesCompletas.put(simpleName, fullClassName);
                }

                return mapaClasesCompletas;
        }

        private ClassPathScanningCandidateComponentProvider crearEscaneadorSinFiltros() {
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
                return provider;
        }

        private static final List<String> botonesSinEventos = new ArrayList<>();

        @Test
        void auditarEfectosEnBotones() {
                // 1. Cargar las clases del proyecto localmente
                JavaClasses clases = new ClassFileImporter().importPackages("com.example.demo");

                ArchRule reglaBotones = classes()
                                .that().resideInAPackage("..views..")
                                .should(verificarListenersEnBotones());

                EvaluationResult result = reglaBotones.evaluate(clases);

                result.getFailureReport().getDetails().stream()
                                .map(Object::toString)
                                .forEach(botonesSinEventos::add);
        }

        private ArchCondition<JavaClass> verificarListenersEnBotones() {
                return new ArchCondition<>("registrar al menos un listener o efecto por cada Button declarado") {
                        @Override
                        public void check(JavaClass javaClass, ConditionEvents events) {
                                // 1. Obtener los campos que son explícitamente de tipo Button en esta clase
                                List<JavaField> camposBotones = javaClass.getFields().stream()
                                                .filter(field -> field.getRawType().isAssignableTo(
                                                                "com.vaadin.flow.component.button.Button"))
                                                .collect(Collectors.toList());

                                if (camposBotones.isEmpty())
                                        return;

                                Set<String> botonesValidados = new HashSet<>();

                                // 2. CASO A: Buscar llamadas directas a .addClickListener(), .add...() sobre un
                                // campo específico
                                for (JavaFieldAccess access : javaClass.getFieldAccessesFromSelf()) {
                                        if (access.getTarget().getRawType()
                                                        .isAssignableTo("com.vaadin.flow.component.button.Button")) {
                                                String nombreCampo = access.getTarget().getName();

                                                // Verificamos si en la misma línea o método de este acceso existe una
                                                // llamada a un listener
                                                boolean tieneMetodoListener = access.getOrigin()
                                                                .getMethodCallsFromSelf().stream()
                                                                .filter(call -> call.getLineNumber() == access
                                                                                .getLineNumber())
                                                                .anyMatch(call -> {
                                                                        String methodName = call.getTarget().getName();
                                                                        return call.getTargetOwner().isAssignableTo(
                                                                                        "com.vaadin.flow.component.button.Button")
                                                                                        && (methodName.startsWith("add")
                                                                                                        || methodName.contains(
                                                                                                                        "Listener")
                                                                                                        || methodName.equals(
                                                                                                                        "setClickShortcut"));
                                                                });

                                                if (tieneMetodoListener) {
                                                        botonesValidados.add(nombreCampo);
                                                }
                                        }
                                }

                                // 3. CASO B: Buscar asignaciones especificas donde el constructor de Button
                                // recibe un Listener en la MISMA LÍNEA
                                for (JavaConstructorCall callConstructor : javaClass.getConstructorCallsFromSelf()) {
                                        if (callConstructor.getTargetOwner()
                                                        .isAssignableTo("com.vaadin.flow.component.button.Button")) {

                                                // Comprobar si esta llamada específica al constructor recibe un
                                                // Listener como parámetro
                                                boolean constructorTieneListener = callConstructor.getTarget()
                                                                .getRawParameterTypes().stream()
                                                                .anyMatch(param -> param.isAssignableTo(
                                                                                "com.vaadin.flow.component.ComponentEventListener")
                                                                                || param.getName().contains(
                                                                                                "EventListener")
                                                                                || param.getName().contains(
                                                                                                "ClickListener"));

                                                if (constructorTieneListener) {
                                                        // Vinculamos ÚNICAMENTE la variable asignada exactamente en el
                                                        // número de línea de este constructor
                                                        javaClass.getFieldAccessesFromSelf().stream()
                                                                        .filter(access -> access
                                                                                        .getLineNumber() == callConstructor
                                                                                                        .getLineNumber())
                                                                        .forEach(access -> botonesValidados.add(
                                                                                        access.getTarget().getName()));
                                                }
                                        }
                                }

                                // 4. Reportar de forma aislada cada campo Button que no esté en la lista de
                                // validados
                                for (JavaField campoBoton : camposBotones) {
                                        if (!botonesValidados.contains(campoBoton.getName())) {
                                                String detalle = fail(String.format(
                                                                "El botón '%s' en la clase '%s' no tiene registrado ningún listener o efecto.",
                                                                campoBoton.getName(),
                                                                javaClass.getSimpleName()));
                                                events.add(SimpleConditionEvent.violated(javaClass, detalle));
                                        }
                                }
                        }
                };
        }

        @Test
    void auditarDependenciasSinUsoEnVistas() {
        JavaClasses clases = new ClassFileImporter().importPackages("com.example.demo");

        ArchRule reglaDependencias = classes()
                .that().resideInAPackage("..views..")
                .should(noTenerImportsInutiles());

        reglaDependencias.check(clases);
    }

    private static ArchCondition<JavaClass> noTenerImportsInutiles() {
        return new ArchCondition<>("no declarar dependencias/imports no utilizados") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                
                for (Dependency dependency : javaClass.getDirectDependenciesFromSelf()) {
                    JavaClass dep = dependency.getTargetClass();

                    // 1. Ignorar paquetes base de Java, anotaciones y la propia clase
                    if (dep.getPackageName().startsWith("java.") 
                            || dep.getPackageName().startsWith("javax.") 
                            || dep.getPackageName().startsWith("org.springframework.")
                            || dep.isAnnotation() 
                            || dep.equals(javaClass)) {
                        continue;
                    }

                    // 2. Comprobar si la clase destino (ej. Video) conoce o registra que la vista la usa
                    // ArchUnit mantiene la tabla global de constantes de referencias
                    boolean esReferenciadaEnEstructura = 
                            javaClass.getFields().stream().anyMatch(f -> contieneClase(f.getType(), dep))
                            || javaClass.getMethods().stream().anyMatch(m -> contieneClase(m.getReturnType(), dep) 
                                    || m.getRawParameterTypes().stream().anyMatch(p -> contieneClase(p, dep)))
                            || javaClass.getConstructors().stream().anyMatch(c -> c.getRawParameterTypes().stream().anyMatch(p -> contieneClase(p, dep)))
                            || javaClass.getSuperclass().map(s -> contieneClase(s, dep)).orElse(false)
                            || javaClass.getInterfaces().stream().anyMatch(i -> contieneClase(i, dep))
                            || javaClass.getMethodCallsFromSelf().stream().anyMatch(call -> call.getTargetOwner().equals(dep))
                            || javaClass.getFieldAccessesFromSelf().stream().anyMatch(fa -> fa.getTargetOwner().equals(dep))
                            || javaClass.getConstructorCallsFromSelf().stream().anyMatch(cc -> cc.getTargetOwner().equals(dep));

                    if (esReferenciadaEnEstructura) {
                        continue;
                    }

                    // 3. Red de seguridad para variables locales o firmas inferidas:
                    // Si el bytecode de la vista contiene la cadena de la firma/paquete del tipo objetivo
                    String patronBytecode = dep.getName().replace('.', '/');
                    boolean existeEnConstantesDelClass = javaClass.getCodeUnits().stream()
                            .anyMatch(cu -> cu.getDescriptor().contains(patronBytecode));

                    if (existeEnConstantesDelClass) {
                        continue;
                    }

                    // Si pasa todos los filtros y no se encuentra ninguna huella de la clase
                    String mensaje = String.format(
                        "La vista '%s' importa o depende de '%s' pero no se encuentra uso directo.",
                        javaClass.getSimpleName(), dep.getSimpleName()
                    );
                    events.add(SimpleConditionEvent.violated(javaClass, mensaje));
                }
            }
        };
    }

    private static boolean contieneClase(JavaType tipoEvaluado, JavaClass tipoBuscado) {
        if (tipoEvaluado.equals(tipoBuscado)) {
            return true;
        }
        // Inspección recursiva de los argumentos de los tipos genéricos.
        if (tipoEvaluado instanceof JavaParameterizedType tipoParametrizado) {
            return tipoParametrizado.getActualTypeArguments().stream()
                    .anyMatch(tipo -> contieneClase(tipo, tipoBuscado));
        }

        return false;
    }
}