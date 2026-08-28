package com.example.demo.services.mocks;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.services.interfaces.iRegistrado;

@Service
 
public class RegistradoMock extends InicioMock implements iRegistrado {

    public RegistradoMock(DatosMock datos) {
        super(datos);
        //TODO Auto-generated constructor stub
    }

}
