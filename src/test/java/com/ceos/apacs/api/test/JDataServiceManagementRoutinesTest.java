/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package com.ceos.apacs.api.test;

import com.ceos.apacs.api.Japxapi;
import com.ceos.apacs.api.Japx;
import com.sun.jna.NativeLong;
import junit.framework.TestCase;

/**
 *
 * @author cgarcia
 */
public class JDataServiceManagementRoutinesTest extends TestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        short EC = 0;
        // Siempre se deben inicializar las API
        EC = Japxapi.InitializeAPI();
        assertEquals("Japxapi.InitializeAPI(): "+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        //En este caso en particular se deben inicializar las NIM a P500
        //que es la maquina de pruebas a ser utilizada.
        //No debe ser necesario con la tarjeta MBUS.
        EC = Japxapi.InitializeNimNameTable();
        assertEquals("Japxapi.InitializeNimNameTable(): "+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        //En este caso en particular se deben inicializar las NIM a P500
        //que es la maquina de pruebas a ser utilizada.
        EC = Japxapi.AddNimName("IEM01");
        assertEquals("Japxapi.InitializeNimNameTable(): "+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        short res = 0;
        // Finalizando las API
        res = Japxapi.ShutDownAPI();
        assertEquals("Japxapi.ShutDownAPI(): "+Japx.getErrorString(res)+" ",Japx.ER_Success, res);
    }

    /**
     * Inicializa las rutinas de acceso a datos.
     * Antes de usar cualquier rutina de acceso a datos se debe inicializar.
     */
    /**
     * Prueba la inicialización de las rutinas requeridas para el manejo de los
     * dispositivos de red NIM.
     */
    public void testInicializarServiciosDeDatos()  {
        short EC = 0;
        NativeLong MIL = new NativeLong();

        // Inicializa los servicios de datos.
        EC = Japxapi.InitializeDataServices("CEOS");
        assertEquals("Japxapi.InitializeDataServices('CEOS')"+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);


        //Espero algo de tiempo
        MIL.setValue(500);
        EC = Japxapi.WaitForResponse(MIL);
        assertEquals("Japxapi.WaitForResponse(MIL)"+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        //Tumbo los servicios
        Japxapi.ShutDownDataServices();

        //Espero algo de tiempo
        MIL.setValue(500);
        EC = Japxapi.WaitForResponse(MIL);
        assertEquals("Japxapi.WaitForResponse(MIL)"+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        // Inicializa los servicios de datos.
        EC = Japxapi.InitializeDataServices("CEOS");
        assertEquals("Japxapi.InitializeDataServices('CEOS')"+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        //Espero algo de tiempo
        //Se debe ver inicializado el servicio en el NIM Monitor.
        MIL.setValue(2000);
        EC = Japxapi.WaitForResponse(MIL);
        assertEquals("Japxapi.WaitForResponse(MIL)"+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        // Inicializa los servicios de datos.
        EC = Japxapi.InitializeDataServices("CEOS");
        assertEquals("Japxapi.InitializeDataServices('CEOS')"+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        //Espero algo de tiempo
        MIL.setValue(500);
        EC = Japxapi.WaitForResponse(MIL);
        assertEquals("Japxapi.WaitForResponse(MIL)"+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        //Tumbo los servicios
        Japxapi.ShutDownDataServices();

    }



}
