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

package com.ceos.apacs.api;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 *
 * @author cgarcia
 */
public class NewMain {

    
    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
            Native.loadLibrary("c:\\windows\\system32\\APXAPIWX.DLL",CLibrary.class);

        short InitializeAPI();
        short ShutDownAPI();
        short InitializeNimNameTable();
        short AddNimName(String nim);

        short InitializeDataServices(String resource);
        void ShutDownDataServices();

        short StartModule(String module);
        short StopModule(String module);
        short  StepModule(String module);

        Pointer CreateDataTag(String str);

        short ValidateTagName(String str);
        short GetTagLength(Pointer p);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s = new String("0123456789012345678901234567890");
        byte[] buf = new byte[1024];

        // TODO code application logic here
       // System.out.println(Japxapi.InitializeAPI());
        System.out.println(Japxapi.GetVersionLabel(buf,(short) 20));
        System.out.println("Lib: " + buf[0]+buf[1]+buf[2]);
    }

}
