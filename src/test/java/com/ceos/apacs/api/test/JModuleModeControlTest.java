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
import com.sun.jna.Native;
import junit.framework.TestCase;

/**
 * Se prueban las funciones relacionadas al estado de los modulos Apacs.
 * Se debe disponer de por lo menos un dispositivo denominado modulo,
 * en caso contrario, se debera definir el nombre al inicio de la ejecución
 * @author cgarcia
 */
public class JModuleModeControlTest extends TestCase {

    public JModuleModeControlTest(){

    }

    public void testCreacionDeUnTag() throws Exception {

        short res = 0;
        short[] mode = new short[1];

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
        res = Japxapi.InitializeAPI();
        assertEquals(Japx.ER_Success,res);

        /*
         * 3. Inicializar servico NIM
         */
        res = Japxapi.InitializeNimNameTable();
        assertEquals(Japx.ER_Success,res);

        /*
         * 3. Inicializar servico NIM
         */
        res = Japxapi.AddNimName("P500");
        assertEquals(Japx.ER_Success,res);

        /*
         * 4. Inicializa el servicio de datos.
         */
        res = Japxapi.InitializeDataServices("SIEMENS");
        assertEquals(Japx.ER_Success,res);

        /*
         * 5. Se Solicita parar el modulo.
         */
        Thread.sleep(500);

        //Coloca el dispositivo en el estado de STOP
        Japxapi.DoPolling();
        int i = 0;
        for (i=0; i<5; i++) {
            res = Japxapi.StopModule(modulo);
            Thread.sleep(1000);
            res = Japxapi.GetModuleMode(modulo, mode);
            Japxapi.DoPolling();
        }
        /*

        //Valido que el dispositivo paso a STOP
        assertEquals(Japx.STOP_MODE,mode[0]);

        Thread.sleep(1000);
        Japxapi.DoPolling();

        /*
         * 5. Se Solicita arrancar el modulo.
         */
        res = Japxapi.StartModule(modulo);
        assertEquals(Japx.ER_Success,res);


        res = Japxapi.GetModuleMode(modulo, mode);
        assertEquals(Japx.ER_Success,res);
        assertEquals(Japx.RUN_MODE,mode[0]);
        Thread.sleep(1000);
        
        /*
         * 6. Se Solicita parar el modulo.
         */

        res = Japxapi.StopModule(modulo);
        assertEquals(Japx.ER_Success,res);

        res = Japxapi.GetModuleMode(modulo, mode);
        assertEquals(Japx.ER_Success,res);
        assertEquals(Japx.STOP_MODE,mode[0]);

        /*
         * 7. Se Solicita arrancar el modulo.
         */
        res = Japxapi.StartModule(modulo);
        assertEquals(Japx.ER_Success,res);
        Thread.sleep(1000);

        res = Japxapi.GetModuleMode(modulo, mode);
        assertEquals(Japx.ER_Success,res);
        assertEquals(Japx.RUN_MODE,mode[0]);

        /*
         * 8. Se Solicita arrancar el modulo.
         */
        res = Japxapi.StepModule(modulo);
        assertEquals(Japx.ER_Success,res);
        Thread.sleep(1000);

        res = Japxapi.GetModuleMode(modulo, mode);
        assertEquals(Japx.ER_Success,res);
        assertEquals(Japx.STOP_MODE,mode[0]);

        /*
         * 8. Se Solicita arrancar el modulo.
         */
        res = Japxapi.StartModule(modulo);
        assertEquals(Japx.ER_Success,res);
        Thread.sleep(1000);

        res = Japxapi.GetModuleMode(modulo, mode);
        assertEquals(Japx.ER_Success,res);
        assertEquals(Japx.RUN_MODE,mode[0]);

        /*
         * . Tumbo los servicios de datos.
         */
        Japxapi.ShutDownDataServices();

        /*
         * . Tumbando las API.
         */
        Japxapi.ShutDownAPI();

    }

}
