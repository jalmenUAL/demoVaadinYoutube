package com.example.demo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;

import com.example.demo.patterns.BaseView;
import com.tngtech.archunit.core.domain.JavaCall;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaConstructor;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.EvaluationResult;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public class ReporteCalidadVistasTest {

    private static JavaClasses clases;
    private static Map<String, String> mapaViews;

    // Colecciones para registrar estadísticas
    private static final List<String> clasesInaccesibles = new ArrayList<>();
    private static final List<String> clasesVacias = new ArrayList<>();
    private static final List<String> llamadasRemoveAll = new ArrayList<>();
    private static final List<String> excesosCampos = new ArrayList<>();
    private static final List<String> excesosParametros = new ArrayList<>();
    private static final List<String> llamadasAddEnConstructor = new ArrayList<>();
    private static final List<String> dependenciasARepositorio = new ArrayList<>();
    private static final List<String> nombresAtributosSospechosos = new ArrayList<>();
    private static final List<String> sobreescriturasSinUso = new ArrayList<>();

     private static final Set<String> METODOS_PATRON = Set.of(
                        "build",
                        "bindEvents",
                        "setOnResultado",
                        "buildList",
                        "buildContainer",
                        "buildItems");

    @BeforeAll
    static void setUp() throws Exception {
        clases = new ClassFileImporter().importPackages("com.example.demo");
        // Asegúrate de que este método sea static en esta o la clase utilitaria
        mapaViews = obtenerMapaClasesViews(); 
    }


     private static ClassPathScanningCandidateComponentProvider crearEscaneadorSinFiltros() {
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

    private static Map<String, String> obtenerMapaClasesViews() {
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

    @Test
    void auditarClasesInaccesibles() {
        ArchRule regla = classes()
                .that().resideInAPackage("..views..")
                .and().areNotInterfaces()
                .should(haveAtLeastOneDependant());

        EvaluationResult result = regla.evaluate(clases);
        for (String event : result.getFailureReport().getDetails()) {
            clasesInaccesibles.add(event);
        }
    }

    private ArchCondition<JavaClass> haveAtLeastOneDependant() {
        return new ArchCondition<>("ser usada o referenciada al menos una vez") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                // Comprueba si hay dependencias que apunten a esta clase desde fuera de sí misma
                boolean esReferenciada = !javaClass.getDirectDependenciesToSelf().isEmpty();
                if (!esReferenciada) {
                    events.add(SimpleConditionEvent.violated(javaClass, javaClass.getSimpleName()));
                }
            }
        };
    }

    @Test
    void auditarClasesVacias() throws Exception {
        for (String nombreCompleto : mapaViews.values()) {
            Class<?> clase = Class.forName(nombreCompleto);
            if (clase.isInterface()) continue;

            long metodos = Arrays.stream(clase.getDeclaredMethods())
                    .filter(m -> !m.isSynthetic() && !m.isBridge())
                    .count();
            long campos = Arrays.stream(clase.getDeclaredFields())
                    .filter(f -> !f.isSynthetic())
                    .count();

            if (metodos == 0 && campos == 0) {
                clasesVacias.add(clase.getSimpleName());
            }
        }
    }

    @Test
    void auditarSobreescrituraSinSuper() throws Exception {
        for (String nombreCompleto : mapaViews.values()) {
            Class<?> clase = Class.forName(nombreCompleto);

            if (BaseView.class.isAssignableFrom(clase) && !clase.equals(BaseView.class)) {
                for (Method m : clase.getDeclaredMethods()) {
                    if (!m.isSynthetic() && METODOS_PATRON.contains(m.getName()) && m.getParameterCount() == 0) {
                        sobreescriturasSinUso.add(clase.getSimpleName() + "." + m.getName() + "()");
                    }
                }
            }
        }
    }

    @Test
void auditarUsoDeRemoveAll() {
    ArchRule reglaRemoveAll = classes()
            .that().resideInAPackage("..views..")
            .should(noLlamarRemoveAll());

    EvaluationResult result = reglaRemoveAll.evaluate(clases);
    for (String event : result.getFailureReport().getDetails()) {
        llamadasRemoveAll.add(event);
    }
}

private ArchCondition<JavaClass> noLlamarRemoveAll() {
    return new ArchCondition<>("no invocar el método removeAll() de Vaadin") {
        @Override
        public void check(JavaClass javaClass, ConditionEvents events) {
            // Recorremos todas las llamadas a métodos que salen de esta clase
            for (JavaCall<?> call : javaClass.getMethodCallsFromSelf()) {
                if (call.getTarget().getName().equals("removeAll")) {
                    String detalle = String.format(
                        "%s (línea %d) llama a removeAll()",
                        javaClass.getSimpleName(),
                        call.getLineNumber()
                    );
                    events.add(SimpleConditionEvent.violated(javaClass, detalle));
                }
            }
        }
    };
}

    @Test
    void auditarTamanoYComplejidadVistas() throws Exception {
        for (String nombreCompleto : mapaViews.values()) {
            Class<?> clase = Class.forName(nombreCompleto);
            if (clase.isInterface()) continue;

            long camposReales = Arrays.stream(clase.getDeclaredFields())
                    .filter(f -> !f.isSynthetic())
                    .count();

            if (camposReales > 12) {
                excesosCampos.add(clase.getSimpleName() + " (" + camposReales + " campos)");
            }

            for (Method m : clase.getDeclaredMethods()) {
                if (m.isSynthetic()) continue;
                if (m.getParameterCount() > 4) {
                    excesosParametros.add(clase.getSimpleName() + "." + m.getName() + " (" + m.getParameterCount() + " params)");
                }
            }
        }
    }

    @Test
    void auditarConstruccionEnConstructor() {
        ArchRule noAddEnConstructor = classes()
                .that().resideInAPackage("..views..")
                .should(noLlamarAddEnConstructores());

        EvaluationResult result = noAddEnConstructor.evaluate(clases);
        result.getFailureReport().getDetails().forEach(llamadasAddEnConstructor::add);
    }

    private ArchCondition<JavaClass> noLlamarAddEnConstructores() {
        return new ArchCondition<>("no invocar add(...) dentro de constructores") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                for (JavaConstructor constructor : javaClass.getConstructors()) {
                    for (JavaCall<?> call : constructor.getMethodCallsFromSelf()) {
                        if (call.getTarget().getName().equals("add")) {
                            events.add(SimpleConditionEvent.violated(
                                    javaClass, javaClass.getSimpleName() + " -> " + call.getTarget().getName()
                            ));
                        }
                    }
                }
            }
        };
    }

    @Test
    void auditarLogicaEnListeners() {
        ArchRule listenersLimpios = noClasses()
                .that().resideInAPackage("..views..")
                .should().dependOnClassesThat().resideInAPackage("..repositories..");

        EvaluationResult result = listenersLimpios.evaluate(clases);
        result.getFailureReport().getDetails().forEach(dependenciasARepositorio::add);
    }

    @Test
    void auditarNombresAtributos() throws Exception {
        for (String nombreCompleto : mapaViews.values()) {
            Class<?> clase = Class.forName(nombreCompleto);

            for (Field f : clase.getDeclaredFields()) {
                if (f.isSynthetic()) continue;

                String name = f.getName();
                if (name.matches(".*\\d+$") || name.equals("temp") || name.equals("data")) {
                    nombresAtributosSospechosos.add(clase.getSimpleName() + "." + name);
                }
            }
        }
    }

    @AfterAll
    static void imprimirReporteEstadistico() {
        System.out.println("\n=======================================================");
        System.out.println("   ESTADÍSTICAS DE CALIDAD Y AUDITORÍA DE CÓDIGO (IA)   ");
        System.out.println("=======================================================");
        
        imprimirSeccion("Clases Inaccesibles / Código Muerto", clasesInaccesibles);
        imprimirSeccion("Clases Vacías (Sin atributos ni métodos)", clasesVacias);
        imprimirSeccion("Uso de removeAll() en Vistas", llamadasRemoveAll);
        imprimirSeccion("Construcción dentro de Constructores (add())", llamadasAddEnConstructor);
        imprimirSeccion("Ataque Directo a Repositorios desde Vistas", dependenciasARepositorio);
        imprimirSeccion("Vistas Complejas (>12 Campos)", excesosCampos);
        imprimirSeccion("Métodos con Demasiados Parámetros (>4)", excesosParametros);
        imprimirSeccion("Nombres de Atributos Genericos/Sospechosos", nombresAtributosSospechosos);
        imprimirSeccion("Redefiniciones de Métodos del Ciclo de Vida", sobreescriturasSinUso);

        System.out.println("=======================================================\n");
    }

    private static void imprimirSeccion(String titulo, List<String> items) {
        System.out.printf("%n[ %s: %d ]%n", titulo.toUpperCase(), items.size());
        if (items.isEmpty()) {
            System.out.println("   ✓ Ningún caso detectado.");
        } else {
            items.forEach(item -> System.out.println("   - " + item));
        }
    }
}
