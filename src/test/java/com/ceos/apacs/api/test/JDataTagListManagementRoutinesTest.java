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
import java.util.HashMap;
import com.sun.jna.NativeLong;
import junit.framework.TestCase;
import com.sun.jna.Pointer;

/**
 *
 * @author cgarcia
 */
public class JDataTagListManagementRoutinesTest extends TestCase{

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
        EC = Japxapi.AddNimName("IEM02");
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
     * Test para crear y borrar una serie de tags dummy en el driver.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     */
    public void testCrearYBorrarTagListTags()  {
        short EC = 0;
        Pointer DTL;
        NativeLong MIL = new NativeLong();

        DTL = Japxapi.CreateDataTagList();
        assertNotNull("DTL = Japxapi.CreateDataTagList(): "+DTL+" ",DTL);

        Japxapi.DeleteDataTagList(DTL);

    }

    /**
     * Test para crear y borrar una serie de tags dummy en el driver.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     */
    public void testCrearYBorrarAsyncTagListTags()  {
        short EC = 0;
        Pointer DTL;
        NativeLong MIL = new NativeLong();

        DTL = Japxapi.CreateAsyncDataTagList();
        assertNotNull("DTL = Japxapi.CreateAsyncDataTagList(): "+DTL+" ",DTL);

        Japxapi.DeleteDataTagList(DTL);

    }

    /**
     * Test para crear y borrar una serie de tags dummy en el driver.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     */
    public void testCrearYBorrarServerTagListTags()  {
        short EC = 0;
        Pointer DTL;
        NativeLong MIL = new NativeLong();

        DTL = Japxapi.CreateServerDataTagList();
        assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);

        Japxapi.DeleteDataTagList(DTL);

    }

    /**
     * Crea el mayor numero posible de datos en 100 tagroups creados.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     * El tope conseguido para tags es de 66*10000 + 9599 registros
     * o sea, 669599 variables. Luego hay que probrar si la
     * tarjeta lo soporta.
     * En la portatil se lograron 61*10000+9839 = 619.839
     * 
     */
    public void testMaxTagsInTagListTags()  {
        short EC = 0;
        short counter;
        int cont = 1;
        Pointer DTL;
        Pointer DT;
        HashMap DTLHmap;
        HashMap DTHmap;
        HashMap hmLocal;
        String s = new String();
        
        NativeLong MIL = new NativeLong();
        
        DTLHmap = new HashMap();
        DTHmap = new HashMap();

        //Creo las 100 listas de Tags
        System.out.println("Creando las listas.");
        for (int i=1; i<=60; i++) {
            DTL = Japxapi.CreateDataTagList();
            assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);
            DTLHmap.put(i, DTL);

            //Lo recupera de la lista y lo valida
            Pointer p = (Pointer) DTLHmap.get(i);
            assertSame("Los objetos no son iguales: ",DTL,p);
        }

        //Creo 10000 tags por cada grupo creado,
        //Ajustar este vaor en función de la disponibilidad de memoria.
        //Con ese valor se crean en teoria 1.000.000
        System.out.println("Creando los tags en la listas.");
        for (int i=1; i<=60; i++) {
            DTL = (Pointer) DTLHmap.get(i);
            assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);
            hmLocal = new HashMap();
            DTHmap.put(i, hmLocal);

            for (int j=1; j<=10000; j++){
                //Cra un tag con el formato estandar
                s = "RESOURCENAME"+i+".PROGRAMNAME.DERIVEDNAME.TC"+j+".PV";
                //System.out.println(s);
                DT = Japxapi.AddDataTag(DTL, s);
                assertNotNull(" DT = Japxapi.AddDataTag(DTL, s): "+j+" "+i,DT);
                hmLocal.put(j,DT);
            }
        }

        //Utilizar la función FindDataTag para validar que el puntero
        //fue creado
        System.out.println("Comprobando que fueron creados los tags.");
        for (int i=1; i<=60; i++) {
            DTL = (Pointer) DTLHmap.get(i);
            assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);
            hmLocal = (HashMap) DTHmap.get(i);
            for (int j=1; j<=10000; j++){
                //Cra un tag con el formato estandar
                s = "RESOURCENAME"+i+".PROGRAMNAME.DERIVEDNAME.TC"+j+".PV";
                DT = Japxapi.FindDataTagHandle(DTL, s);
                assertNotNull(" DT = Japxapi.AddDataTag(DTL, s): "+DT+" ",DT);
                Pointer tempP = (Pointer) hmLocal.get(j);
                assertEquals("Comparando los punteros: ", tempP, DT);

            }
        }

        System.out.println("Borrando el contenido de las listas.");
        //Destruyo las listas de tags que fueron creados
        //¿El recolector de basura deberia hacer esto?.
        for (int i=1; i<=60; i++) {
            DTL = (Pointer) DTLHmap.get(i);
            assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);

            //Paso 1 reseteo la lista de tags
            // colocando el puntero al inicio de la lista
            Japxapi.ResetList(DTL);

            DT = Japxapi.NextTag(DTL);
            assertNotNull(" DT = Japxapi.NextTag(DTL): "+DT+" ",DT);
            
            do {             

                do {
                    EC = Japxapi.RemoveDataTag(DTL, DT);
                    if ((EC != Japx.ER_TagNotRemoved) && (EC != Japx.ER_Success)) {
                        System.out.println("EC = Japxapi.RemoveDataTag(DTL, DT): "+Japx.getErrorString(EC));
                        fail();
                    }
                } while(EC != Japx.ER_Success);
                DT = Japxapi.NextTag(DTL);
                //assertNotNull(" DT = Japxapi.NextTag(DTL): "+DT+" ",DT);
            } while(DT != null) ;

           }

        System.out.println("Borrando las listas.");
        for (int i=1; i<=60; i++) {
            Japxapi.DeleteDataTagList((Pointer) DTLHmap.get(i));
        }
        System.out.println("Recolectando la basura.");
        System.gc();
        System.out.println("Saliendo de la recolección.");

    }

    /**
     * Prueba la creacion de 1000 grupos con 600 variables cada uno.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     * El objetivo es probar la capacidad de almacenamiento de los
     * grupos, en caso de ser requeridos un gran número de estos.
     * Se crearan un total de 1000 grupos con 600 tags, con un total
     * de 600.000 tags.
     */
    public void testMaxGroupsInTagListTags()  {
        short EC = 0;
        short counter;
        int cont = 1;
        Pointer DTL;
        Pointer DT;
        HashMap DTLHmap;
        HashMap DTHmap;
        HashMap hmLocal;
        String s = new String();

        NativeLong MIL = new NativeLong();

        DTLHmap = new HashMap();
        DTHmap = new HashMap();

        //Creo las 100 listas de Tags
        System.out.println("Creando las listas.");
        for (int i=1; i<=1000; i++) {
            DTL = Japxapi.CreateDataTagList();
            assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);
            DTLHmap.put(i, DTL);

            //Lo recupera de la lista y lo valida
            Pointer p = (Pointer) DTLHmap.get(i);
            assertSame("Los objetos no son iguales: ",DTL,p);
        }

        //Creo 10000 tags por cada grupo creado,
        //Ajustar este vaor en función de la disponibilidad de memoria.
        //Con ese valor se crean en teoria 1.000.000
        System.out.println("Creando los tags en la listas.");
        for (int i=1; i<=1000; i++) {
            DTL = (Pointer) DTLHmap.get(i);
            assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);
            hmLocal = new HashMap();
            DTHmap.put(i, hmLocal);

            for (int j=1; j<=600; j++){
                //Cra un tag con el formato estandar
                s = "RESOURCENAME"+i+".PROGRAMNAME.DERIVEDNAME.TC"+j+".PV";
                //System.out.println(s);
                DT = Japxapi.AddDataTag(DTL, s);
                assertNotNull(" DT = Japxapi.AddDataTag(DTL, s): "+j+" "+i,DT);
                hmLocal.put(j,DT);
            }
        }

        //Utilizar la función FindDataTag para validar que el puntero
        //fue creado
        System.out.println("Comprobando que fueron creados los tags.");
        for (int i=1; i<=1000; i++) {
            DTL = (Pointer) DTLHmap.get(i);
            assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);
            hmLocal = (HashMap) DTHmap.get(i);
            for (int j=1; j<=600; j++){
                //Cra un tag con el formato estandar
                s = "RESOURCENAME"+i+".PROGRAMNAME.DERIVEDNAME.TC"+j+".PV";
                DT = Japxapi.FindDataTagHandle(DTL, s);
                assertNotNull(" DT = Japxapi.AddDataTag(DTL, s): "+DT+" ",DT);
                Pointer tempP = (Pointer) hmLocal.get(j);
                assertEquals("Comparando los punteros: ", tempP, DT);

            }
        }

        System.out.println("Borrando el contenido de las listas.");
        //Destruyo las listas de tags que fueron creados
        //¿El recolector de basura deberia hacer esto?.
        for (int i=1; i<=1000; i++) {
            DTL = (Pointer) DTLHmap.get(i);
            assertNotNull("DTL = Japxapi.CreateServerDataTagList(): "+DTL+" ",DTL);

            //Paso 1 reseteo la lista de tags
            // colocando el puntero al inicio de la lista
            Japxapi.ResetList(DTL);

            DT = Japxapi.NextTag(DTL);
            assertNotNull(" DT = Japxapi.NextTag(DTL): "+DT+" ",DT);

            do {

                do {
                    EC = Japxapi.RemoveDataTag(DTL, DT);
                    if ((EC != Japx.ER_TagNotRemoved) && (EC != Japx.ER_Success)) {
                        System.out.println("EC = Japxapi.RemoveDataTag(DTL, DT): "+Japx.getErrorString(EC));
                        fail();
                    }
                } while(EC != Japx.ER_Success);
                DT = Japxapi.NextTag(DTL);
                //assertNotNull(" DT = Japxapi.NextTag(DTL): "+DT+" ",DT);
            } while(DT != null) ;

           }

        System.out.println("Borrando las listas.");
        for (int i=1; i<=1000; i++) {
            Japxapi.DeleteDataTagList((Pointer) DTLHmap.get(i));
        }
        System.out.println("Recolectando la basura.");
        System.gc();
        System.out.println("Saliendo de la recolección.");

    }



}
