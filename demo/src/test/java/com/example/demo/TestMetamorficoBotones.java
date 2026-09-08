package com.example.demo;

import static com.github.mvysny.kaributesting.v10.LocatorJ._click;
import static com.github.mvysny.kaributesting.v10.LocatorJ._find;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.factories.ViewFactoryProvider;
import com.example.demo.services.interfaces.iInicio;
import com.example.demo.views.inicio.Perfil;
import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

public class TestMetamorficoBotones {

    @BeforeEach
    public void setup() {
        // Inicializar rutas y mock de Vaadin en memoria
        Routes routes = new Routes().autoDiscoverViews("com.example.demo.views");
        MockVaadin.setup(routes);
    }

    @AfterEach
    public void tearDown() {
        MockVaadin.tearDown();
    }

 @Test
void verificarQueTodosLosBotonesDePerfilTienenEfecto() throws IllegalAccessException {
    // 1. Mockear dependencias y modelo de datos
    iInicio servicioInicioMock = org.mockito.Mockito.mock(iInicio.class);
    ViewFactoryProvider viewFactoryMock = org.mockito.Mockito.mock(ViewFactoryProvider.class);
    
    com.example.demo.tables.Youtuber usuarioMock = org.mockito.Mockito.mock(com.example.demo.tables.Youtuber.class);
    org.mockito.Mockito.when(servicioInicioMock.findYoutuberById("user123")).thenReturn(usuarioMock);

    // 2. Instanciar la vista
    Perfil vista = new Perfil(servicioInicioMock, viewFactoryMock);

    // 3. OBLIGATORIO: Forzar la ejecución de build() y bindEvents() de BaseParameterizedView
    // (Simula lo que hace Vaadin cuando navegas con parámetro)
    vista.setParameter(null, "user123"); // O ejecuta manualmente: vista.build("user123"); vista.bindEvents();
    
    // Añadir la vista construida a Karibu
    UI.getCurrent().add(vista);

    // 4. PASO A: Verificar que los campos Button de la clase NO estén a null tras la construcción
    java.lang.reflect.Field[] campos = Perfil.class.getDeclaredFields();
    for (java.lang.reflect.Field campo : campos) {
        if (Button.class.isAssignableFrom(campo.getType())) {
            campo.setAccessible(true);
            Object valorCampo = campo.get(vista);

            assertNotNull(
                valorCampo, 
                String.format("El botón '%s' en Perfil es null (¿se olvidó de instanciar en build()?).", campo.getName())
            );

            Button boton = (Button) valorCampo;
            assertTrue(
                boton.getParent().isPresent(),
                String.format("El botón '%s' se creó pero no se añadió con add(...) al layout.", campo.getName())
            );
        }
    }

    // 5. PASO B: Recorrer los botones en pantalla y ejecutar la Relación Metamórfica
    List<Button> botonesEnPantalla = _find(Button.class);
    assertFalse(botonesEnPantalla.isEmpty(), "No se encontró ningún botón en el árbol DOM de Perfil.");

    for (Button boton : botonesEnPantalla) {
        String estadoInicialS1 = capturarSnapshotVista(vista);

        // Simulamos el clic en memoria con Karibu
        _click(boton);

        String estadoFinalS2 = capturarSnapshotVista(vista);

        String nombreBoton = (boton.getText() != null && !boton.getText().isEmpty()) 
                ? boton.getText() 
                : boton.getId().orElse(boton.getClass().getSimpleName());

        // Si el listener está comentado, S1 == S2 y el test FALLARÁ AQUÍ
        assertNotEquals(
            estadoInicialS1, 
            estadoFinalS2, 
            String.format("Violación Metamórfica: El botón '%s' no produce ningún cambio ni evento al hacer clic.", nombreBoton)
        );

        // Reiniciar la vista
        UI.getCurrent().removeAll();
        vista = new Perfil(servicioInicioMock, viewFactoryMock);
        vista.setParameter(null, "user123");
        UI.getCurrent().add(vista);
    }
}

    /**
     * Genera una huella digital (Snapshot) de los elementos visibles del DOM virtual de Vaadin.
     * Si un clic modifica una etiqueta, habilita/deshabilita un componente o añade elementos,
     * la firma generada cambiará.
     */
    private String capturarSnapshotVista(Component raiz) {
        StringBuilder snapshot = new StringBuilder();
        recorrerArbol(raiz, snapshot);
        return snapshot.toString();
    }

    private void recorrerArbol(Component componente, StringBuilder snapshot) {
        // Guardamos el tipo de componente, su visibilidad y si está habilitado
        snapshot.append(componente.getClass().getSimpleName())
                .append("[vis:").append(componente.isVisible())
                    .append(",hab:").append(componente instanceof com.vaadin.flow.component.HasEnabled
                            && ((com.vaadin.flow.component.HasEnabled) componente).isEnabled()).append("];");

        // Si el componente expone texto (ej. Span, Label, Paragraph, Notification)
        String texto = componente.getElement().getText();
        if (texto != null && !texto.isEmpty()) {
            snapshot.append("txt:").append(texto).append(";");
        }

        // Recorrer recursivamente los hijos del componente
        componente.getChildren().forEach(hijo -> recorrerArbol(hijo, snapshot));
    }
}
