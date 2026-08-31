package com.example.demo.services.mocks;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.demo.services.interfaces.iRegistrado;


 
@Service
@Profile("mock")

public class RegistradoMock extends InicioMock implements iRegistrado {

    public RegistradoMock(DatosMock datos) {
        super(datos);
       
    }

}
