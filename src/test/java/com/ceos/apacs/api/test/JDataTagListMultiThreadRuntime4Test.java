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
import java.util.Scanner;
import com.sun.jna.NativeLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import com.sun.jna.Pointer;
import com.sun.jna.Memory;

/**
 *
 * @author cgarcia
 */
public class JDataTagListMultiThreadRuntime4Test extends TestCase {

    public static Pointer DTL1,DTL2;
    public static Pointer DTL1DT1, DTL1DT2, DTL1DT3 ,DTL1DT4;
    public static Pointer DTL2DT1, DTL2DT2, DTL2DT3 ,DTL2DT4, DTL2DT5 ;

    
    /**
     *
     */
    private static class TagPool implements Runnable {

        public void run() {
            short EC = 0;
            int DTC = 0;
            Pointer DTL; //Puntero a la lista de tags;
            Pointer DT1,DT2,DT3,DT4;
            NativeLong MIL = new NativeLong();

            System.out.println("Arrancando TagPool1");

            //Forzo una lectura del driver
            try {
                //Duermo un rato el programa
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(JDataTagListManagementRoutinesTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            MIL.setValue(50000);
            EC = Japxapi.WaitForResponse(MIL);
            //assertEquals("Japxapi.WaitForResponse(MIL): "+Japx.getErrorString(EC),Japx.ER_Success,EC);

            int i =0;

            
            do {
                if (!Japxapi.IsDTLReadInProgress(DTL1)) {
                    EC = Japxapi.ReadTagList(DTL1);

                //assertEquals("Lectura de lista de tags: "+Japx.getErrorString(EC),Japx.ER_Success,EC);
                // EC = Japxapi.WaitForResponse(MIL);
                //assertEquals("Japxapi.WaitForResponse(MIL): "+Japx.getErrorString(EC),Japx.ER_Success,EC);
                    System.out.println("Ejecutando DTL1: "+i+" EC: "+Japx.getErrorString(EC));
                    Japxapi.DoPolling();
                }
                    System.out.println("DTL1: "+i);
                i += 1;
                if (EC != Japx.ER_Success) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(JDataTagListMultiThreadRuntime4Test.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (EC != Japx.ER_Success);


            //Forzo una lectura del driver


            //EC = Japxapi.ReadTagList(DTL1);
            assertEquals("Lectura de lista de tags: "+Japx.getErrorString(EC),Japx.ER_Success,EC);

            //Forzo una lectura del driver
            //Japxapi.DoPolling();


            //Aqui puedo probar la validación del tipo
            DTC = Japxapi.GetDataType(DTL1DT1);
            assertEquals("Tipo tiempo: ",Japx.DTYPE_TIME,DTC);

            DTC = Japxapi.GetDataType(DTL1DT2);
            assertEquals("Tipo real: ",Japx.DTYPE_REAL,DTC);

            DTC = Japxapi.GetDataType(DTL1DT3);
            assertEquals("Tipo boolean: ",Japx.DTYPE_BOOL,DTC);

        System.out.println("Salidendo de la tarea 1");
        }


    }

    /**
     *
     */
    private static class TagPool2 implements Runnable {

        public void run() {
            try {
                short EC = 0;
                int DTC = 0;
                Pointer DTL; //Puntero a la lista de tags;
                Pointer DT1;
                Pointer DT2;
                Pointer DT3;
                Pointer DT4;
                NativeLong MIL = new NativeLong();
                System.out.println("Arrancando TagPool2");

                try {
                    //Duermo un rato el programa
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JDataTagListManagementRoutinesTest.class.getName()).log(Level.SEVERE, null, ex);
                }

                MIL.setValue(50000);
                EC = Japxapi.WaitForResponse(MIL);
                //assertEquals("Japxapi.WaitForResponse(MIL): "+Japx.getErrorString(EC),Japx.ER_Success,EC);
                int i = 0;

                do {
                    if (!Japxapi.IsDTLReadInProgress(DTL2)) {
                        EC = Japxapi.ReadTagList(DTL2);
                        
                        //assertEquals("Lectura de lista de tags: "+Japx.getErrorString(EC),Japx.ER_Success,EC);
                        // EC = Japxapi.WaitForResponse(MIL);
                        //assertEquals("Japxapi.WaitForResponse(MIL): "+Japx.getErrorString(EC),Japx.ER_Success,EC);
                        System.out.println("Ejecutando DTL2: "+i+" EC: "+Japx.getErrorString(EC));
                        Japxapi.DoPolling();
                    }
                    System.out.println("DTL2: "+i);
                if (EC != Japx.ER_Success) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(JDataTagListMultiThreadRuntime4Test.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    i += 1;
                } while (EC != Japx.ER_Success);


                //Forzo una lectura del driver
                //EC = Japxapi.ReadTagList(DTL2);
                assertEquals("Lectura de lista de tags: " + Japx.getErrorString(EC), Japx.ER_Success, EC);


                //Forzo una lectura del driver
                //Japxapi.DoPolling();
                //Aqui puedo probar la validación del tipo
                DTC = Japxapi.GetDataType(DTL2DT1);
                assertEquals("Tipo tiempo: ", Japx.DTYPE_UINT, DTC);

                DTC = Japxapi.GetDataType(DTL2DT2);
                assertEquals("Tipo real: ", Japx.DTYPE_REAL, DTC);

                DTC = Japxapi.GetDataType(DTL2DT3);
                assertEquals("Tipo boolean: ", Japx.DTYPE_REAL, DTC);

                //Aqui agrgamos las rutinas de letura
                Memory mem = new Memory(256);

                EC = Japxapi.GetData(DTL2DT1, Japx.INPUT_SEGMENT, mem, Japx.TYPE_S16, (short) 255, null);
                assertEquals("GetData: ", Japx.ER_Success, EC);
                System.out.println("Valor leido TYPE_S16: "+mem.getShort(0));

                EC = Japxapi.GetData(DTL2DT2, Japx.INPUT_SEGMENT, mem, Japx.TYPE_F32IEEE, (short) 255, null);
                assertEquals("GetData: ", Japx.ER_Success, EC);
                System.out.println("Valor leido TYPE_F32IEEE: "+mem.getFloat(0));
                
                EC = Japxapi.GetData(DTL2DT3, Japx.INPUT_SEGMENT, mem, Japx.TYPE_F32IEEE, (short) 255, null);
                assertEquals("GetData: ", Japx.ER_Success, EC);
                System.out.println("Valor leido TYPE_F32IEEE: "+mem.getFloat(0));

                EC = Japxapi.GetData(DTL2DT4, Japx.INPUT_SEGMENT, mem, Japx.TYPE_LOGICAL, (short) 255, null);
                assertEquals("GetData: ", Japx.ER_Success, EC);
                System.out.println("Valor leido TYPE_LOGICAL: "+mem.getShort(0));

                EC = Japxapi.GetData(DTL2DT5, Japx.INPUT_SEGMENT, mem, Japx.TYPE_LOGICAL, (short) 255, null);
                assertEquals("GetData: ", Japx.ER_Success, EC);
                System.out.println("Valor leido TYPE_LOGICAL: "+mem.getShort(0));


                System.out.println("Salidendo de la tarea 2");

                
            } catch (Exception ex) {
                Logger.getLogger(JDataTagListMultiThreadRuntime4Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }


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
     * Crea los grupos y variables para los Apacs conectados a la maquina.
     * El formato general de un tag es IEC:
     * <i>ResourceName.ProgramName.DerivedName.<...>TC1100.PV</i>
     */
    public void testPruebaDeTareas()  {

        short EC = 0;

        //Creo la primera lista de Tags
        DTL1 = Pointer.NULL;
        DTL1 = Japxapi.CreateDataTagList();
        System.out.println("DTL1: "+DTL1);

        System.out.println("Creado los tags EN DTL1....");
        DTL1DT1 = Japxapi.AddDataTag(DTL1, "INCINERATOR.RESOURCE_BLOCKS.RESOURCE_STATUS.PROG_T");
        assertNotNull("No se pudo crear RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PROG_T",DTL1DT1);
        DTL1DT2 = Japxapi.AddDataTag(DTL1, "RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PCTPRG");
        assertNotNull("No se pudo crear RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.PCTPRG",DTL1DT2);
        DTL1DT3 = Japxapi.AddDataTag(DTL1, "RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.WFLAG");
        assertNotNull("No se pudo crear RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.WFLAG",DTL1DT3);


        //Creación y asignación de las lista
        DTL2 = Pointer.NULL;
        DTL2 = Japxapi.CreateDataTagList();
        System.out.println("DTL: " + DTL2);
        System.out.println("Creando los tags....");
        DTL2DT1 = Japxapi.AddDataTag(DTL2, "RESPALDO_IP.RESOURCE_BLOCKS.ERROR_LOG.NUM_ER");
        assertNotNull("No se pudo crear RESPALDO_IP.RESOURCE_BLOCKS.ERROR_LOG.NUM_ER", DTL2DT1);

        DTL2DT2 = Japxapi.AddDataTag(DTL2, "RESPALDO_IP.RESOURCE_BLOCKS.LEANDRO.OUT1");
        assertNotNull("No se pudo crear RESPALDO_IP.RESOURCE_BLOCKS.LEANDRO.OUT1", DTL2DT2);
        
        DTL2DT3 = Japxapi.AddDataTag(DTL2, "RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.MAXPRG");
        assertNotNull("No se pudo crear RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.MAXPRG", DTL2DT3);

        DTL2DT4 = Japxapi.AddDataTag(DTL2, "RESPALDO_IP.RESOURCE_BLOCKS.ERROR_LOG.NEW_ER");
        assertNotNull("No se pudo crear RESPALDO_IP.RESOURCE_BLOCKS.ERROR_LOG.NEW_ER",DTL2DT4);

        DTL2DT5 = Japxapi.AddDataTag(DTL2, "RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.CFLAG");
        assertNotNull("No se pudo crear RESPALDO_IP.RESOURCE_BLOCKS.RESOURCE_STATUS.CFLAG",DTL2DT5);
        

        Thread t1 = new Thread(new TagPool());
        Thread t2 = new Thread(new TagPool2());
        Thread t3 = new Thread(new TagPool());
        Thread t4 = new Thread(new TagPool2());

        System.out.println("Tarea 1");
        t1.start();

        System.out.println("Tarea 2");
        t2.start();

        System.out.println("Tarea 3");
        t3.start();

        System.out.println("Tarea 4");
        t4.start();

        Scanner in = new Scanner(System.in);
        int b = 0;
        
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ex) {
            Logger.getLogger(JDataTagListMultiThreadRuntime4Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        //*******************************
        //Borrado de datos en la lista #1
        //*******************************

        Japxapi.ResetList(DTL1);
        DTL1DT1 = Japxapi.NextTag(DTL1);

        assertNotNull(" DT = Japxapi.NextTag(DTL): " + DTL1DT1 + " ", DTL1DT1);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(JDataTagListMultiThreadRuntime4Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Procedo a borrar los tags LISTA 1 ");

        do {
            do {
                EC = Japxapi.RemoveDataTag(DTL1, DTL1DT1);
                if ((EC != Japx.ER_TagNotRemoved) && (EC != Japx.ER_Success)) {
                   System.out.println("EC = Japxapi.RemoveDataTag(DTL, DT): " + Japx.getErrorString(EC));
                   fail();
                 }
               } while (EC != Japx.ER_Success);
                DTL1DT1 = Japxapi.NextTag(DTL1);
               //assertNotNull(" DT = Japxapi.NextTag(DTL): "+DT+" ",DT);
            } while (DTL1DT1 != null);
        Japxapi.DeleteDataTagList(DTL1);

        //*******************************
        //Borrado de datos en la lista #2
        //*******************************
        Japxapi.ResetList(DTL2);
        DTL2DT1 = Japxapi.NextTag(DTL2);

        assertNotNull(" DT = Japxapi.NextTag(DTL): " + DTL2DT1 + " ", DTL2DT1);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(JDataTagListMultiThreadRuntime4Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Procedo a borrar los tags LISTA 2 ");

        do {
            do {
                EC = Japxapi.RemoveDataTag(DTL2, DTL2DT1);
                if ((EC != Japx.ER_TagNotRemoved) && (EC != Japx.ER_Success)) {
                   System.out.println("EC = Japxapi.RemoveDataTag(DTL2, DT): " + Japx.getErrorString(EC));
                   fail();
                 }
               } while (EC != Japx.ER_Success);
                DTL2DT1 = Japxapi.NextTag(DTL2);
               //assertNotNull(" DT = Japxapi.NextTag(DTL): "+DT+" ",DT);
            } while (DTL2DT1 != null);
        Japxapi.DeleteDataTagList(DTL2);


       }

 }
