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
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.ShortByReference;
import com.sun.jna.ptr.NativeLongByReference;

import junit.framework.TestCase;
import com.sun.jna.Pointer;

/**
 *
 * @author cgarcia
 */
public class JDataTagCommonRoutinesTest extends TestCase{

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
     * Prueba de las rutinas comunes.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     */
    public void testPruebaRutinasComunes()  {
        short EC , BL= 0;
        Pointer DTL;
        Pointer DT1,DT2,DT3,DT4;
        NativeLong ID = new NativeLong();
        ShortByReference BS = new ShortByReference();
        NativeLongByReference IDRef = new NativeLongByReference();


        byte[] buff = new byte[255];
        String strTag,strRet;

        //Prueba la validación del tag, en ralidad es un formalismo.
        //La verdadera validación se realiza al forzar la lectura
        //con un ReadTag().
        EC = Japxapi.ValidateTagName("");
        assertEquals(Japx.ER_UnknownError, EC);

        EC = Japxapi.ValidateTagName("RESPALDO_IP");
        assertEquals(Japx.ER_Success, EC);

        EC = Japxapi.ValidateTagName("RESPALDO_IP.RESOURCE_BLOCKS");
        assertEquals(Japx.ER_Success, EC);

        EC = Japxapi.ValidateTagName("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS");
        assertEquals(Japx.ER_Success, EC);

        EC = Japxapi.ValidateTagName("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PROG_T");
        assertEquals(Japx.ER_Success, EC);

        //Pruebo que valide la información de longitud de los tags.
                                   //012345678901234567890123456789012345678901234567890123456789
        DT1 = Japxapi.CreateDataTag("RESPALDO_IP");
        DT2 = Japxapi.CreateDataTag("RESPALDO_IP.RESOURCE_BLOCKS");
        DT3 = Japxapi.CreateDataTag("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS");
        DT4 = Japxapi.CreateDataTag("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PROG_T");

        BL = Japxapi.GetTagLength(DT1);
        assertEquals(0,BL);

        BL = Japxapi.GetTagLength(DT2);
        assertEquals(27,BL);

        BL = Japxapi.GetTagLength(DT3);
        assertEquals(43,BL);

        BL = Japxapi.GetTagLength(DT4);
        assertEquals(50,BL);

        BS.setValue((short) 255);

        //Recupero los string desde el API
        BL = Japxapi.GetTagText(DT1, buff, BS);
        strTag = Native.toString(buff);
        strRet = "";
        assertEquals("Retornado "+strTag,strRet,strTag);
        
        BL = Japxapi.GetTagText(DT2, buff, BS);
        strTag = Native.toString(buff);
        strRet = "RESPALDO_IP.RESOURCE_BLOCKS";
        assertEquals("Retornado "+strTag,strRet,strTag);

        BL = Japxapi.GetTagText(DT3, buff, BS);
        strTag = Native.toString(buff);
        assertEquals("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS",strTag);

        BL = Japxapi.GetTagText(DT4, buff, BS);
        strTag = Native.toString(buff);
        assertEquals("RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PROG_T",strTag);

        //Prueba la asignación del UserID

        ID.setValue(1000);
        IDRef.setValue(ID);
        Japxapi.PutUserID(DT1, IDRef);

        ID.setValue(2000);
        IDRef.setValue(ID);
        Japxapi.PutUserID(DT2, IDRef);

        ID.setValue(3000);
        IDRef.setValue(ID);
        Japxapi.PutUserID(DT3, IDRef);

        ID.setValue(4000);
        IDRef.setValue(ID);
        Japxapi.PutUserID(DT4, IDRef);

        //Este primer tag no puede ser creado
        ID = Japxapi.GetUserID(DT1);
        assertEquals(0,ID.longValue());

        ID = Japxapi.GetUserID(DT2);
        assertEquals(2000,ID.longValue());

        ID = Japxapi.GetUserID(DT3);
        assertEquals(3000,ID.longValue());

        ID = Japxapi.GetUserID(DT4);
        assertEquals(4000,ID.longValue());

        //Borro los punteros creados.
        Japxapi.DeleteDataTag(DT1);
        Japxapi.DeleteDataTag(DT2);
        Japxapi.DeleteDataTag(DT3);
        Japxapi.DeleteDataTag(DT4);

    }




}
