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
import com.sun.jna.NativeLong;
import junit.framework.TestCase;
import com.sun.jna.Pointer;

/**
 *
 * @author cgarcia
 */
public class JIndividualDataTagManagementRoutinesTest extends TestCase{

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
        EC = Japxapi.InitializeDataServices("TEST APXAPI");

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
    public void testCrearYBorrarDummyTags()  {
        short EC = 0;
        Pointer DT1,DT2,DT3;
        NativeLong MIL = new NativeLong();

        // Crea un tag nulo.
        DT1 = Japxapi.CreateDataTag("");
        assertNull("DT1 = Japxapi.CreateDataTag(''): "+DT1+" ",DT1);

        //Destruye el puntero creado
        Japxapi.DeleteDataTag(DT1);

        //Cra un tag con el formato estandar
        DT1 = Japxapi.CreateDataTag("INCINERATOR.SINGLE_LOOPS.B28TIC_5110AIND.SETPOINT.[TARGET]");
        assertNotNull("DT2 = Japxapi.CreateDataTag(''): "+DT1+" ",DT1);

        //Cra un tag con el formato estandar
        DT2 = Japxapi.CreateDataTag("INCINERATOR.SINGLE_LOOPS.B28TIC_5110AIND.SETPOINT.TARGET");
        assertNotNull("DT2 = Japxapi.CreateDataTag(''): "+DT2+" ",DT2);

        //Cra un tag con el formato estandar
        DT3 = Japxapi.CreateDataTag("RESOURCENAME3.PROGRAMNAME.DERIVEDNAME.TC1102.PV");
        assertNotNull("DT2 = Japxapi.CreateDataTag(''): "+DT3+" ",DT3);

        //Dado que los tags utilizados son Dummy al forzar la lectura
        // se debe obtener un error de dispositivo no encontrado.
        EC = Japxapi.ReadTag(DT1);
        assertEquals("Japxapi.ReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);
        EC = Japxapi.ReadTag(DT2);
        assertEquals("Japxapi.ReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);
        EC = Japxapi.ReadTag(DT3);
        assertEquals("Japxapi.ReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);

        EC = Japxapi.ReadTagAsync(DT1);
        assertEquals("Japxapi.ReadTagAsync(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ProcessPending, EC);
        //Espero algo de tiempo
        EC = Japxapi.ReadTagAsync(DT2);
        assertEquals("Japxapi.ReadTagAsync(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ProcessPending, EC);
        //Espero algo de tiempo
        EC = Japxapi.ReadTagAsync(DT3);
        assertEquals("Japxapi.ReadTagAsync(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ProcessPending, EC);
        //Espero algo de tiempo

        //Importante el dispositivo nunca responde
        //No existe
        //En algún momento arrojo una excepción en la máquina virtual.
        MIL.setValue(500);
        EC = Japxapi.WaitForResponse(MIL);
        assertEquals("Japxapi.WaitForResponse(MIL): "+Japx.getErrorString(EC)+" ",Japx.ER_TimeOutError, EC);

        EC = Japxapi.ContinueReadTag(DT1);
        assertEquals("Japxapi.ContinueReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);
        EC = Japxapi.ContinueReadTag(DT2);
        assertEquals("Japxapi.ContinueReadTag(DT2): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);
        EC = Japxapi.ContinueReadTag(DT3);
        assertEquals("Japxapi.ContinueReadTag(DT3): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);

        //Destruye el puntero creado
        Japxapi.DeleteDataTag(DT1);

        MIL.setValue(500);
        EC = Japxapi.WaitForResponse(MIL);
        assertEquals("Japxapi.WaitForResponse(MIL): "+Japx.getErrorString(EC)+" ",Japx.ER_TimeOutError, EC);
        EC = Japxapi.ContinueReadTag(DT1);
        assertEquals("Japxapi.ContinueReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_InvalidRequest, EC);

        //Destruye el puntero creado
        Japxapi.DeleteDataTag(DT2);

        //Destruye el puntero creado
        Japxapi.DeleteDataTag(DT3);

    }

   /**
     * Test para crear y borrar una serie de tags dummy en el driver.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     */
    public void testCrearYBorrarTagsReales()  {
        short EC = 0;
        Pointer DT1,DT2,DT3;
        NativeLong MIL = new NativeLong();

        // Crea un tag nulo.
        DT1 = Japxapi.CreateDataTag("");
        assertNull("DT1 = Japxapi.CreateDataTag(''): "+DT1+" ",DT1);

        //Destruye el puntero creado
        Japxapi.DeleteDataTag(DT1);

        //Cra un tag con el formato estandar
        //Este tag debe existir en el recurso señalado.

        DT1 = Japxapi.CreateDataTag("RESPALDO_IP.PROGRAMNAME.DERIVEDNAME.TC1100.PV");
        assertNotNull("DT2 = Japxapi.CreateDataTag(''): "+DT1+" ",DT1);

        //Cra un tag con el formato estandar
        DT2 = Japxapi.CreateDataTag("RESPALDO_IP.PROGRAMNAME.DERIVEDNAME.TC1101.PV");
        assertNotNull("DT2 = Japxapi.CreateDataTag(''): "+DT2+" ",DT2);

        //Cra un tag con el formato estandar
        DT3 = Japxapi.CreateDataTag("RESPALDO_IP.PROGRAMNAME.DERIVEDNAME.TC1102.PV");
        assertNotNull("DT2 = Japxapi.CreateDataTag(''): "+DT3+" ",DT3);

        //Dado que los tags utilizados son Dummy al forzar la lectura
        // se debe obtener un error de dispositivo no encontrado.
        EC = Japxapi.ReadTag(DT1);
        assertEquals("Japxapi.ReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);
        EC = Japxapi.ReadTag(DT2);
        assertEquals("Japxapi.ReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);
        EC = Japxapi.ReadTag(DT3);
        assertEquals("Japxapi.ReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);

        EC = Japxapi.ReadTagAsync(DT1);
        assertEquals("Japxapi.ReadTagAsync(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ProcessPending, EC);
        //Espero algo de tiempo
        EC = Japxapi.ReadTagAsync(DT2);
        assertEquals("Japxapi.ReadTagAsync(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ProcessPending, EC);
        //Espero algo de tiempo
        EC = Japxapi.ReadTagAsync(DT3);
        assertEquals("Japxapi.ReadTagAsync(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ProcessPending, EC);
        //Espero algo de tiempo

        //Importante el dispositivo nunca responde
        //No existe
        //En algún momento arrojo una excepción en la máquina virtual.
        MIL.setValue(500);
        EC = Japxapi.WaitForResponse(MIL);
        assertEquals("Japxapi.WaitForResponse(MIL): "+Japx.getErrorString(EC)+" ",Japx.ER_TimeOutError, EC);

        EC = Japxapi.ContinueReadTag(DT1);
        assertEquals("Japxapi.ContinueReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);
        EC = Japxapi.ContinueReadTag(DT2);
        assertEquals("Japxapi.ContinueReadTag(DT2): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);
        EC = Japxapi.ContinueReadTag(DT3);
        assertEquals("Japxapi.ContinueReadTag(DT3): "+Japx.getErrorString(EC)+" ",Japx.ER_ModuleNotFound, EC);

        //Destruye el puntero creado
        Japxapi.DeleteDataTag(DT1);

        MIL.setValue(500);
        EC = Japxapi.WaitForResponse(MIL);
        assertEquals("Japxapi.WaitForResponse(MIL): "+Japx.getErrorString(EC)+" ",Japx.ER_TimeOutError, EC);
        EC = Japxapi.ContinueReadTag(DT1);
        assertEquals("Japxapi.ContinueReadTag(DT1): "+Japx.getErrorString(EC)+" ",Japx.ER_InvalidRequest, EC);

        //Destruye el puntero creado
        Japxapi.DeleteDataTag(DT2);

        //Destruye el puntero creado
        Japxapi.DeleteDataTag(DT3);

    }

}
