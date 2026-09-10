package com.example.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventBus;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuditoriaMasivaVistasTest {

    @Test
    @DisplayName("Auditar automáticamente todos los botones de todas las vistas del proyecto")
    void auditarTodasLasVistasDelProyecto() {
        // 1. Escanear el paquete raíz donde residen todas las vistas
        Reflections reflections = new Reflections("com.example.demo.views");
        
        // Obtener todas las clases anotadas con @Route (tus vistas de Vaadin)
        Set<Class<?>> clasesVistas = reflections.getTypesAnnotatedWith(Route.class);

        List<String> erroresGlobales = new ArrayList<>();

        for (Class<?> claseVista : clasesVistas) {
            try {
                // 2. Crear una instancia de la vista inyectando Mocks dinámicos
                Object instanciaVista = instanciarVistaConMocks(claseVista);

                // 3. Ejecutar lifecycle si la vista es parametrizada/configurada
                ejecutarCicloDeVida(instanciaVista);

                // 4. Auditar los botones de esta vista concreta
                auditarBotonesDeVista(claseVista, instanciaVista, erroresGlobales);

            } catch (Exception e) {
                erroresGlobales.add(String.format("[%s] No se pudo instanciar o procesar la vista: %s", 
                        claseVista.getSimpleName(), e.getMessage()));
            }
        }

        // 5. Un único informe de errores consolidado
        assertTrue(
            erroresGlobales.isEmpty(),
            "\n❌ SE HAN DETECTADO BOTONES HUÉRFANOS O INVALIDOS EN EL PROYECTO:\n - " + 
            String.join("\n - ", erroresGlobales)
        );
    }

    /**
     * Inspecciona los constructores de la clase y crea Mocks automáticamente 
     * para CUALQUIER servicio o dependencia que exija el constructor.
     */
    private Object instanciarVistaConMocks(Class<?> claseVista) throws Exception {
        Constructor<?> constructor = claseVista.getConstructors()[0];
        Class<?>[] tiposParametros = constructor.getParameterTypes();
        Object[] mocks = new Object[tiposParametros.length];

        for (int i = 0; i < tiposParametros.length; i++) {
            mocks[i] = Mockito.mock(tiposParametros[i], Mockito.RETURNS_DEEP_STUBS);
        }

        return constructor.newInstance(mocks);
    }

    /**
     * Invoca automáticamente métodos como setParameter(), build(), bindEvents() 
     * o initView() usando reflexión si existen en la clase.
     */
    private void ejecutarCicloDeVida(Object vista) {
        for (Method metodo : vista.getClass().getMethods()) {
            try {
                // Si la vista implementa HasUrlParameter / BaseParameterizedView
                if (metodo.getName().equals("setParameter") && metodo.getParameterCount() == 2) {
                    metodo.invoke(vista, null, "123"); 
                    return;
                }
                // Si usas metodos directos de construcción
                if (metodo.getName().equals("initView") && metodo.getParameterCount() == 0) {
                    metodo.invoke(vista);
                    return;
                }
            } catch (Exception ignored) {
                // Si el parametro por defecto falla, se ignora y continúa con la inspección del estado actual
            }
        }
    }

    /**
     * Audita todos los atributos Button declarados dentro de la clase vista.
     */
    private void auditarBotonesDeVista(Class<?> claseVista, Object vista, List<String> errores) throws IllegalAccessException {
        Field[] campos = claseVista.getDeclaredFields();

        for (Field campo : campos) {
            if (Button.class.isAssignableFrom(campo.getType())) {
                campo.setAccessible(true);
                Button boton = (Button) campo.get(vista);
                String idBoton = claseVista.getSimpleName() + "." + campo.getName();

                if (boton == null) {
                    errores.add(String.format("[%s] Es NULL (declarado pero no instanciado)", idBoton));
                    continue;
                }

                if (boton.getParent().isEmpty()) {
                    errores.add(String.format("[%s] Instanciado pero NO añadido a ningún Layout con add()", idBoton));
                }

                if (!tieneListenerRegistrado(boton)) {
                    errores.add(String.format("[%s] Añadido a la UI pero SIN ClickListener registrado", idBoton));
                }
            }
        }
    }

    /**
     * Revisa el EventBus privado de Vaadin para verificar suscripciones.
     */
    private boolean tieneListenerRegistrado(Button boton) {
        try {
            Field eventBusField = Component.class.getDeclaredField("eventBus");
            eventBusField.setAccessible(true);
            ComponentEventBus eventBus = (ComponentEventBus) eventBusField.get(boton);

            if (eventBus == null) return false;

            Field componentEventDataField = ComponentEventBus.class.getDeclaredField("componentEventData");
            componentEventDataField.setAccessible(true);
            Map<?, ?> mapaListeners = (Map<?, ?>) componentEventDataField.get(eventBus);

            return mapaListeners != null && !mapaListeners.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}