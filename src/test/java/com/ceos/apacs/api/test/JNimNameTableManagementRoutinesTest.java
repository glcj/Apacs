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
import com.sun.jna.Native;
import junit.framework.TestCase;

/**
 * Este conjunto de pruebas validan la funcionalidad de las rutinas para el
 * manejo de las NIM en la red Ethernet.
 * Estas rutinas son utililes cuando se utilizan las NIM32 para las
 * comunicaciones con los APACS+.
 * Basicamente funcionan como el manejador de una lista.
 * @author cgarcia
 */
public class JNimNameTableManagementRoutinesTest  extends TestCase  {

    public JNimNameTableManagementRoutinesTest () {
    }

    /**
     * Aqui incializaremos las variables globales para las pruebas.
     * Tambien se podran incializar las rutinas en función de que
     * ya hayan superado las pruebas.
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        short res = 0;
        // Siempre se deben inicializar las API
        res = Japxapi.InitializeAPI();
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
    }


    /**
     * Aqui se liberan las variables globales para las pruebas.
     * Tambien se podran finalizar las rutinas en función de que
     * ya hayan superado las pruebas.
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        short res = 0;
        // Finalizando las API
        res = Japxapi.ShutDownAPI();
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
    }

    /**
     * Prueba la inicialización de las rutinas requeridas para el manejo de los
     * dispositivos de red NIM.
     */
    public void testInicializarLasRutinas()  {
        short res = 0;

        // Inicializa la tabla de dispositivos de red NIM
        res = Japxapi.InitializeNimNameTable();
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);

    }


    /**
     * Prueba la inicialización de las rutinas requeridas para el manejo de los
     * dispositivos de red NIM.
     */
    public void testManejoDeLaNimTable()  {
        short res = 0;
        byte[] buffer = new byte[255];
        short BL = 0;
        short BS = 255;
        String s = new String();

        // Inicializa la tabla de dispositivos de red NIM
        res = Japxapi.InitializeNimNameTable();
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);

        //Agrega 10 NIM"
        res = Japxapi.AddNimName("NIM01");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM02");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM03");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM04");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM05");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM06");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM07");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM08");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM09");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);
        res = Japxapi.AddNimName("NIM10");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);

        //Agrego una NIM repetida.
        //Solo debe reemplazar el valor anterior
        res = Japxapi.AddNimName("NIM05");
        assertEquals(Japx.getErrorString(res)+" ",Japx.ER_Success, res);

        //Tomo el primer elemento de la Nim
        BL = Japxapi.GetNextNimName(buffer, BS);
        s = Native.toString(buffer);
        //Primer elemento de la lista
        assertEquals("Elemento de red "+s+" ","NIM01",s);
        
          //Tomo el segundo elemento de la Nim
        BL = Japxapi.GetNextNimName(buffer, BS);
        s = Native.toString(buffer);
        //Primer elemento de la lista
        assertEquals("Elemento de red "+s+" ","NIM02",s);

        //Tomo el tercer elemento de la Nim
        BL = Japxapi.GetNextNimName(buffer, BS);
        s = Native.toString(buffer);
        //Primer elemento de la lista
        assertEquals("Elemento de red "+s+" ","NIM03",s);

        //Reseteo la lista
        res = Japxapi.ResetNimNameTable();
        assertEquals("Japxapi.ResetNimNameTable(): "+Japx.getErrorString(res)+" ",Japx.ER_Success, res);

        //Tomo el primer elemento de la Nim
        BL = Japxapi.GetNextNimName(buffer, BS);
        s = Native.toString(buffer);
        //Primer elemento de la lista
        assertEquals("Elemento de red "+s+" ","NIM01",s);

        //Borro el NIM02 para que el proximo elemento sea el NIM03
        res = Japxapi.DeleteNimName("NIM02");
        assertEquals("Japxapi.DeleteNimName('NIM02'): "+Japx.getErrorString(res)+" ",Japx.ER_Success, res);

        //Tomo el primer elemento de la Nim
        BL = Japxapi.GetNextNimName(buffer, BS);
        s = Native.toString(buffer);
        //Primer elemento de la lista
        assertEquals("Elemento de red "+s+" ","NIM03",s);

        //Al tumar la API desaparece la lista.

    }



}
