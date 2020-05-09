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
import com.sun.jna.NativeLong;
import junit.framework.TestCase;

/**
 * Se prueban las funciones relacionadas al estado de los modulos Apacs.
 * Se debe disponer de por lo menos un dispositivo denominado modulo,
 * en caso contrario, se debera definir el nombre al inicio de la ejecución
 * @author cgarcia
 */
public class JAPIManagementRoutinesTest extends TestCase {

    public JAPIManagementRoutinesTest(){

    }

    public void testInicializarAPI() throws Exception {

        short EC = 0;

        short BS = 255; //Longitud máxima del String
        short BL = 0;   //Número de caracteres devueltos por la función

        short[] mode = new short[1];
        byte[] VL = new byte[BS];

        String str = "CEOS";
        String modulo = "RESPALDO_IP";

        /*
         * 1. Las rutinas de acceso a las librerias Apacs se mantienen
         *    protegidas para evitar tumbar la JVM.
         */
        Native.setProtected(true);

        /*
         * 2. Inicializar las API
         */
        EC = Japxapi.InitializeAPI();
        assertEquals(Japx.ER_Success, EC);

        /*
         * 3. Si no genera una exscepción  se considera
         * inicializada las rutinas.
         */
         Japxapi.DoPolling();
         
        /*
         * 4. Esta función devuelve la versión de las librerias.
         */
         BL = Japxapi.GetVersionLabel(VL, BS);
         //Convertimos el cero String a String Java
         String s = Native.toString(VL);
         assertEquals("4.40 NET NTW",s);

        /*
         * 5. Comprueba la disponibilidad de memoria.
         * Las maquinas disponen de suficiente recursos.
         */
         Boolean flag = Japxapi.IsSystemMemoryLow();
         assertEquals((Boolean)false,flag);

        /*
         * 6. Espera por que el sistema responda.
         */
         NativeLong MIL = new NativeLong();
         MIL.setValue(2000);
         EC = Japxapi.WaitForResponse(MIL);
         assertEquals(Japx.ER_InvalidRequest, EC);

        /*
         * Esta función no devuelve ningún valo
         */
        EC = Japxapi.ShutDownAPI();
        assertEquals(Japx.ER_Success, EC);

    }

}
