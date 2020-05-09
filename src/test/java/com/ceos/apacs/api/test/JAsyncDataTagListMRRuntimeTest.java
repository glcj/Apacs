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
import com.sun.jna.ptr.ShortByReference;
import com.sun.jna.ptr.NativeLongByReference;

import junit.framework.TestCase;
import com.sun.jna.Pointer;

/**
 *
 * @author cgarcia
 */
public class JAsyncDataTagListMRRuntimeTest extends TestCase{

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
        EC = Japxapi.AddNimName("P500");
        assertEquals("Japxapi.InitializeNimNameTable(): "+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);

        //Inicializamos las rutinas de Datos
        EC = Japxapi.InitializeDataServices("CEOS");

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        short EC = 0;

        //Primero paramos los servicios de datos.
        Japxapi.ShutDownDataServices();

        // Finalizando las API
        EC = Japxapi.ShutDownAPI();
        assertEquals("Japxapi.ShutDownAPI(): "+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);
    }

    /**
     * Prueba de las rutinas comunes asincronas.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     */
    public void testPruebaRutinasAsincronas() throws InterruptedException  {
        short EC , BL= 0;
        Pointer DTL1,DTL2;
        Pointer DT1,DT2,DT3,DT4,DT5,DT6,DT7,DT8;
        NativeLong ID = new NativeLong();
        ShortByReference BS = new ShortByReference();
        NativeLongByReference IDRef = new NativeLongByReference();

        byte[] buff = new byte[255];
        String strTag,strRet;

        //Prueba la validación del tag, en ralidad es un formalismo.
        //La verdadera validación se realiza al forzar la lectura
        //con un ReadTag().
        EC = Japxapi.ValidateTagName("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PROG_T");
        assertEquals(Japx.ER_Success, EC);

        //Pruebo que valide la información de longitud de los tags.
                                   //012345678901234567890123456789012345678901234567890123456789
        DT1 = Japxapi.CreateDataTag("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PCTCOM");
        DT2 = Japxapi.CreateDataTag("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.MTOTAL");
        DT3 = Japxapi.CreateDataTag("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.M_FREE");
        DT4 = Japxapi.CreateDataTag("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PROG_T");

        //Segundo grupo de datos
        DT5 = Japxapi.CreateDataTag("RESPALDO_IP.B31_COMMS.B31TCP_COMMS.B31TCP_R1_DATA.[DATA][33]");
        DT6 = Japxapi.CreateDataTag("RESPALDO_IP.B31_COMMS.B31TCP_COMMS.B31TCP_R1_DATA.[DATA][65]");
        DT7 = Japxapi.CreateDataTag("RESPALDO_IP.%B31TE_31703A");
        DT8 = Japxapi.CreateDataTag("RESPALDO_IP.B30PIT_1022");

        //Validamos los tags.

        Japxapi.DoPolling();
        for (int i=0; i<10; i++) {
            System.out.println("Poll: "+i);
            EC = Japxapi.ReadTag(DT1);
            System.out.println("DT1 : "+Japx.getErrorString(EC));
            EC = Japxapi.ReadTag(DT2);
            System.out.println("DT2 : "+Japx.getErrorString(EC));
            EC = Japxapi.ReadTag(DT3);
            System.out.println("DT3 : "+Japx.getErrorString(EC));
            EC = Japxapi.ReadTag(DT4);
            System.out.println("DT4 : "+Japx.getErrorString(EC));
            EC = Japxapi.ReadTag(DT5);
            System.out.println("DT5 : "+Japx.getErrorString(EC));
            EC = Japxapi.ReadTag(DT6);
            System.out.println("DT6 : "+Japx.getErrorString(EC));
            EC = Japxapi.ReadTag(DT7);
            System.out.println("DT7 : "+Japx.getErrorString(EC));
            EC = Japxapi.ReadTag(DT8);
            System.out.println("DT8 : "+Japx.getErrorString(EC));
            Japxapi.DoPolling();
            Thread.sleep(1000);
        }
        
        //Borro los punteros creados.
        Japxapi.DeleteDataTag(DT1);
        Japxapi.DeleteDataTag(DT2);
        Japxapi.DeleteDataTag(DT3);
        Japxapi.DeleteDataTag(DT4);

        Japxapi.DeleteDataTag(DT5);
        Japxapi.DeleteDataTag(DT6);
        Japxapi.DeleteDataTag(DT7);
        Japxapi.DeleteDataTag(DT8);


    }




}
