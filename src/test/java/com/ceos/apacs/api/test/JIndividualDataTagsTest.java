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
import com.sun.jna.Pointer;



/**
 *
 * @author cgarcia
 */
public class JIndividualDataTagsTest extends TestCase {



    public JIndividualDataTagsTest(){

    }

    public void testCreacionDeUnTag()  {
        short res = 0;
        short l = 0;
        String str = "INCINERATOR.ResourceStatus.STATISTICS.PCTPRG";

        /*
         * 1. Inicializar las API
         */
        res = Japxapi.InitializeAPI();
        System.out.println("res = Japxapi.InitializeAPI(): "+Japx.getErrorString(res));

        /*
         * 2. Las rutinas de acceso a las librerias Apacs se mantienen
         *    protegidas para evitar tumbar la JVM.
         */
        Native.setProtected(true);

        /*
         * 3. Inicializa el servicio de datos.
         */
        Japxapi.InitializeDataServices(str);

        /*
         * 4. Manejo de rutinas comunes.
         */
        res = Japxapi.ValidateTagName("");
        System.out.println("res = Japxapi.ValidateTagName(str): "+Japx.getErrorString(res));

        /*
         * 5. Creo un TAG de datos.
         */
        Pointer pointer = Japxapi.CreateDataTag(str);
        System.out.println("Japxapi.GetTagLength(pointer): "+Japxapi.GetTagLength(pointer));

        Japxapi.DoPolling();


        /*
         * 6. Leer Data Tag.
         */
        res = Japxapi.ReadTag(pointer);
        Japxapi.DoPolling();
        System.out.println("Japxapi.ReadTag(pointer): "+Japx.getErrorString(res));
        try {
                    Thread.sleep(500);
        } catch (Exception e) {
            
        }

        res = Japxapi.ReadTag(pointer);
        Japxapi.DoPolling();
        System.out.println("Japxapi.ReadTag(pointer): "+Japx.getErrorString(res));
       
        Japxapi.DeleteDataTag(pointer);

        /*
         * . Tumbo los servicios de datos.
         */
        Japxapi.ShutDownDataServices();

        /*
         * . Tumbo las api de Apacs.
         */
        Japxapi.ShutDownAPI();


    }

}
