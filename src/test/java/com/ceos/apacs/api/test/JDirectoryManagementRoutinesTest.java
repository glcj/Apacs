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

import com.ceos.apacs.api.Japx;
import com.ceos.apacs.api.Japxapi;
import com.ceos.apacs.api.Japxdir;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.ShortByReference;
import com.sun.jna.ptr.NativeLongByReference;

import junit.framework.TestCase;
import com.sun.jna.Pointer;

/**
 *
 * @author cgarcia
 */
public class JDirectoryManagementRoutinesTest extends TestCase{

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
        short EC , BL, L1= 0;
        Pointer DTL1,DTL2;
        Pointer DT1,DT2,DT3,DT4,DT5,DT6,DT7,DT8;
        NativeLong ID = new NativeLong();
        ShortByReference BS = new ShortByReference();
        NativeLongByReference IDRef = new NativeLongByReference();

        byte[] buff = new byte[255];
        String strTag,strRet, strDevice;

        //Inicializar servicios de directorios
        EC = Japxdir.InitializeDirectoryServices("CEOS");
        assertEquals("EC = Japxapi.InitializeDataServices('CEOS'): "+Japx.getErrorString(EC),Japx.ER_Success,EC);

        //Recolecta los modulos en la red
        EC = Japxdir.CollectMBusModules();
        assertEquals("EC = Japxdir.CollectMBusModules(): "+Japx.getErrorString(EC),Japx.ER_Success,EC);

        //Grantiza que el puntero va al incio de la lista
        EC = Japxdir.ResetMBusModules();
        assertEquals("EC = Japxdir.ResetMBusModules(): "+Japx.getErrorString(EC),Japx.ER_Success,EC);

        //Tomo el primer MBUS en la lista
        Pointer DIRECTORY_ENTRY;
        do {
            DIRECTORY_ENTRY = Japxdir.NextMBusModule();
            if (DIRECTORY_ENTRY != null) {
                L1 = Japxdir.GetResourceNameLength(DIRECTORY_ENTRY);
                BL = Japxdir.GetResourceName(DIRECTORY_ENTRY, buff, (short) 255);
                assertEquals("Comparación de longitudes: ",L1,BL);

                strDevice = Native.toString(buff);
                System.out.println("Device: "+strDevice);
            }
        } while (DIRECTORY_ENTRY != null);

        //Finaliza el servicio de directorios
        Japxdir.ShutDownDirectoryServices();




    }




}
