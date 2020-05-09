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
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cgarcia
 */
public class JDataTagWriterTest {

    public JDataTagWriterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
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
        EC = Japxapi.InitializeDataServices("TEST APXAPI");        
    }

    @After
    public void tearDown() {
        short EC = 0;

        //Primero paramos los servicios de datos.
        Japxapi.ShutDownDataServices();

        // Finalizando las API
        EC = Japxapi.ShutDownAPI();
        assertEquals("Japxapi.ShutDownAPI(): "+Japx.getErrorString(EC)+" ",Japx.ER_Success, EC);        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void WriteValue(){
        String strHello = "HOLA MUNDO!";
        int intValor = 1000;
        
        short EC = 0;
        Pointer DT = null;
        Memory PV = new Memory(255);
        
        PV.setFloat(0, 99);
        DT = Japxapi.CreateDataTag("INCINERATOR.SINGLE_LOOPS.B28TIC_5110ADIR.AUTO_MANUAL.[COUT]");        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(JDataTagWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Japxapi.DoPolling();
        
        EC = Japxapi.ReadTag(DT);
        if (EC != Japx.ER_Success) {  
            System.out.println("Error JJapxapi.ReadTag(DT): "+Japx.getErrorString(EC));                   
        }      
        
        EC = Japxapi.PutData(DT, Japx.OUTPUT_SEGMENT, PV, Japx.TYPE_F32IEEE);
        
        if (EC == Japx.ER_Success) {
            
            EC = Japxapi.WriteTagAsync(DT);
            
            while (EC == Japx.ER_ProcessPending){
                Japxapi.DoPolling();
                try {
                    Thread.sleep(50);
                    EC = Japxapi.ContinueWriteTag(DT);
                    
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }                
            }
            
            if ( EC != Japx.ER_Success){
                System.out.println("Error Japxapi.WriteTagAsync(DT): "+Japx.getErrorString(EC));
            }
            
        } else {
        System.out.println("Error Japxapi.PutData(DT, Japx.OUTPUT_SEGMENT, PV, Japx.TYPE_F32IEEE);: "+Japx.getErrorString(EC));       
        }        

    }

}