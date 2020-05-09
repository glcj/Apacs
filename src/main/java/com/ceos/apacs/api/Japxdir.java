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
public class Japxdir implements Library  {

    //Directory Services Management Routines

    /**
     * This function performs all of the communication initialization required
     * for Directory Services. This function must be called before any other
     * Directory Services functions are executed. Should the function
     * fail, Directory Services are not available and should not be used.
     * In the UNIX versions, this function utilizes the SIGIO signal. If this
     * signal is blocked, this function will not operate correctly.
     *
     * @param ResourceName
     * @return ER_Success ER_InsufficientMemory ER_Failure
     */
    public static native short InitializeDirectoryServices(String ResourceName);

    /**
     * This function cleans up after Directory Services is complete. Once this
     * function has been executed, no further Directory Services functions
     * should be executed. In the UNIX versions, this function utilizes the
     * SIGIO signal. If this signal is blocked, this function will not operate
     * correctly.
     *
     * @return None
     */
    public static native void ShutDownDirectoryServices();

    //Directory Management Routines
    
    /**
     * Gathers information on all resources within the scope of the current
     * system. The M-BUS modules in the list are always sorted by physical
     * address, node/rack/slot. After this function is called,
     * ResetMBusModules( ) should be called. In the UNIX versions, this
     * function utilizes the SIGIO signal. If this signal is blocked, this
     * function will not operate correctly.
     * 
     * @return: ER_Success ER_InsufficientMemory ER_Failure ER_TransmitError
     */
    public static native short CollectMBusModules();

    /**
     * This function is used to begin the process of gathering information on
     * all resources within the scope of the current system. This command only
     * begins the process and will not actually gather any information.
     * It is expected that the user will execute ContinueCollectMBusModules()
     * commands until the process is complete.
     * 
     * @return ER_InsufficientMemory ER_UnknownError ER_ProcessPending
     * ER_TransmitError ER_Failure
     */
    public static native short CollectMBusModulesAsync();

    /**
     * This function will continue the process of gathering information on all
     * resources within the scope of the current system. This command functions
     * similar to the CollectMBusModules command except that a ProcessPending
     * can be returned if the responses for all of the modules have not yet
     * arrived. The user should continue executing this routine until an
     * ER_ProcessPending is NOT returned. In between calls, the user should
     * execute the DoPolling() command to allow the system to process the
     * communication messages that may have been received by the API.
     * 
     * @return ER_UnknownError ER_ProcessPending ER_Success ER_Failure
     */
    public static native short ContinueCollectMBusModules();

    /**
     * This function resets the list of M-BUS modules to the beginning of the list.
     * 
     * @return ER_Success ER_Failure
     */
    public static native short ResetMBusModules();

    /**
     * This function advances the current module to be the next module in the list.
     *
     * @return Handle Next module entry found<br>
     * NULL Next module entry does not exist
     */
    public static native Pointer NextMBusModule();

    /**
     * This function provides random access to the module entries in the
     * resource list. ResourceName represents which module you wish to go to.
     * If ResourceName does not include a module specifier and the resource is
     * part of a redundant pair, then one of the modules is retrieved.
     * 
     * @return Handle Resource name found<br>
     * NULL Resource name is not found
     */
    public static native Pointer GoToMBusModuleName(String ResourceName);
    
    /**
     * This function gathers information on all I/O modules which are on the}
     * IOBUS of the current M-BUS module. The I/O modules in the list are always
     * sorted by physical address, node/rack/slot. After this function is
     * called, ResetIOBusModules( ) should be called. In the UNIX versions,
     * this function utilizes the SIGIO signal. If this signal is blocked,
     * this function will not operate correctly.
     * 
     * @return ER_Success ER_InsufficientMemory ER_PathNotFound ER_Failure
     * ER_TransmitError ER_UnknownError
     */
    public static native short CollectIOBusModules();

    /**
     * This function is used to begin the process of gathering information on
     * all I/O modules which are on the IOBUS of the current M-BUS module. This
     * command only begins the process and will not actually gather any
     * information. It is expected that the user will execute
     * ContinueCollectIOBusModules() commands until the process is complete.
     * 
     * @return ER_InsufficientMemory ER_UnknownError ER_ProcessPending
     * ER_PathNotFound ER_TransmitError ER_Failure
     */
    public static native short CollectIOBusModulesAsync();

    /**
     * This function will continue the process of gathering information on all
     * I/O modules which are on the IOBUS of the current M-BUS module. This
     * command functions similar to the CollectIOBusModules command except that
     * a ProcessPending can be returned if the responses for all of the modules
     * have not yet arrived. The user should continue executing this routine
     * until an ER_ProcessPending is NOT  * returned. In between calls, the
     * user should execute the DoPolling() command to allow the system to
     * process the communication messages that may have been received
     * by the API.
     *
     * @return ER_UnknownError ER_ProcessPending ER_Success ER_Failure
     */
    public static native short ContinueCollectIOBusModules();

    /**
     * This function resets the list of IOBUS modules to the beginning of the
     * list of the current M-BUS module.
     * 
     * @return ER_Success ER_Failure
     */
    public static native short ResetIOBusModules();

    /**
     * This function advances the current IOBUS module to the next IOBUS module
     * in the list for the current M-BUS module.
     * 
     * @return Handle Next IOBUS module found<br>
     * NULL Next IOBUS module does not exist
     */
    public static native Pointer NextIOBusModule();

    //Module Information Access Routines

    /**
     * This function will return the node number of the specified module.
     *
     * @param DirectoryEntry
     * @return Node number
     */
    public static native short GetNodeNumber(Pointer DirectoryEntry);

    /**
     * This function will return the rack number of the specified module.
     *
     * @param DirectoryEntry
     * @return Rack number
     */
    public static native short GetRackNumber(Pointer DirectoryEntry);

    /**
     * This function will return the slot number of the specified module.
     *
     * @param DirectoryEntry
     * @return Slot number
     */
    public static native short GetSlotNumber(Pointer DirectoryEntry);

   /**
    * This function will return the length of the resource name (not including
    * the null termination character).
    *
    * @param DirectoryEntry
    * @return Length of resource name
    */
    public static native short GetResourceNameLength(Pointer DirectoryEntry);

   /**
    * This function will copy at most BufferSize characters, including the null
    * terminator, from the resource name of the passed DIRECTORY_ENTRY into
    * Buffer. Buffer will always be null terminated. If DirectoryEntry is
    * an I/O module, then Buffer will be set to null.
    *
    * @param DirectoryEntry
    * @param Buffer
    * @param BufferSize
    * @return The number of characters copied to Buffer,
    * not including the null character
    */
    public static native short GetResourceName(Pointer DirectoryEntry, byte[] Buffer,
            short BufferSize);

    /**
     * This function will copy at most BufferSize characters, including the
     * null terminator, from the Hardware Revision of the passed
     * DIRECTORY_ENTRY into Buffer. This information is also available as a
     * STRING value through DataServices by accessing the Imbedded Global
     * Variable named #HardwareRevision.
     *
     * @param DirectoryEntry
     * @param Buffer
     * @param BufferSize
     * @return The number of characters copied to Buffer,
     * including a null terminator
     */
    public static native short GetHardwareRevision(Pointer DirectoryEntry, byte[] Buffer,
            short BufferSize);

    /**
     * This function will copy at most BufferSize characters, including the
     * null terminator, from the Operating System Revision of the passed
     * DIRECTORY_ENTRY into Buffer. This information is also available as
     * a STRING value through DataServices by accessing the Imbedded Global
     * Variable named #OSRevision.
     *
     * @param DirectoryEntry
     * @param Buffer
     * @param BufferSize
     * @return The number of characters copied to Buffer,
     * including a null terminator
     */
    public static native short GetOSRevision(Pointer DirectoryEntry, byte[] Buffer,
            short BufferSize);

    /**
     * This function will copy at most BufferSize characters, including the null
     * terminator, from the ROM Revision of the passed DIRECTORY_ENTRY into
     * Buffer. This information is also available as a STRING value through
     * DataServices by accessing the Imbedded Global Variable named
     * #ROMRevision.
     *
     * @param DirectoryEntry
     * @param Buffer
     * @param BufferSize
     * @return The number of characters copied to Buffer,
     * including a null terminator
     */
    public static native short GetROMRevision(Pointer DirectoryEntry, byte[] Buffer,
            short BufferSize);

    /**
     * This function will store the redundancy specifier for the passed
     * resource, DirectoryEntry, in the string pointed to by Buffer.
     * Buffer must be large enough to hold the module specified
     * (MAX_MODULE_SPECIFIER_SIZE). This information is also available as a
     * STRING value through DataServices by accessing the Imbedded Global
     * Variable named #ModuleSpecifier.
     *
     * @param DirectoryEntry
     * @param Buffer
     * @param BufferSize
     * @return The number of characters copied to Buffer,
     * not including the null character
     */
     public static native short GetModuleSpecifier(Pointer DirectoryEntry, byte[] Buffer,
            short BufferSize);

    /**
     * This function will return the module type code of the passed module,
     * DirectoryEntry (see Appendix H for a listing of module type codes).
     * This information is also available as a STRING value through
     * DataServices by accessing the Imbedded Global Variable named
     * #ModuleType.
     *
     * @param DirectoryEntry
     * @return ModuleTypeCode value
     */
     public static native short GetModuleTypeCode(Pointer DirectoryEntry);

    /**
     * This function will return the product type code of the passed module,
     * DirectoryEntry. There are three product type codes available - APACS,
     * QUADLOG, and UNKNOWN.
     *
     * @param DirectoryEntry
     * @return PT_APACS PT_QUADLOG PT_UNKNOWN
     */
     public static native short GetProductTypeCode(Pointer DirectoryEntry);

    /**
     * This function returns an indication of whether or not the specified
     * module, DirectoryEntry, has been value through DataServices by accessing
     * the Imbedded Global Variable named #SystemLoaded.
     *
     * @param DirectoryEntry
     * @return TRUE <br>
     * FALSE Software has been downloaded to module
     */
    public static native boolean IsSystemLoaded(Pointer DirectoryEntry);

    /**
     * This function returns an indication of whether or not the specified
     * module, DirectoryEntry, has had a DataServices by accessing the Imbedded
     * Global Variable named #Configured.
     *
     * @param DirectoryEntry
     * @return TRUE <br>
     * FALSE  A configuration has been downloaded to the module.
     */
    public static native boolean IsConfigured(Pointer DirectoryEntry);

    /**
     * This function returns an indication of whether or not the specified
     * module, DirectoryEntry, is physically Imbedded Global Variable named
     * #Installed.
     *
     * @param DirectoryEntry
     * @return TRUE FALSE The module is physically present
     */
    public static native boolean IsInstalled(Pointer DirectoryEntry);

    /**
     * This function returns an indication of whether or not there are any
     * errors on the specified module, the Imbedded Global Variable named
     * #LocalOK.
     *
     * @param DirectoryEntry
     * @return TRUE FALSE The module has errors present
     */
    public static native boolean IsLocalOK(Pointer DirectoryEntry);

    /**
     * This function returns an indication of whether or not there are any
     * errors on the specified module, temperature above its specified
     * maximum allowable temperature. This information is also available as a
     * BOOL value through DataServices by accessing the Imbedded Global
     * Variable named #OverTemp.
     *
     * @param DirectoryEntry
     * @return TRUE The module is running at too high a temperature
     * FALSE The module is running within the specified temperature
     */
    public static native boolean IsSystemOK(Pointer DirectoryEntry);

    /**
     * This function returns the number of seconds since 01/01/1970 00:00:00
     * of the last change to the configuration of the specified resource,
     * DirectoryEntry. This information is also available as a DATE
     * value through DataServices by accessing the Imbedded Global Variable
     * named #ConfigTime.
     *
     * @param DirectoryEntry
     * @return The number of seconds since 12:00:00 AM on 1-1-1970
     */
     public static native long GetConfigTime(Pointer DirectoryEntry);


     //Module Error Information Routines

    /**
     * This function returns the number of Class 1 errors associated with the
     * specified module, DirectoryEntry. This information is also available as
     * a DINT value through DataServices if an ERRSTAT function block
     * is configured in the control module by accessing the CLASS1 output.
     * The desired system (control module and I/O) or individual module
     * address is specified by the NRS_AD input. The system total
     * NumClass1Errors value is also available as an Imbedded Global Variable
     * named #SysTotErrCount1. The module NumClass1Errors value is also
     * available as an Imbedded Global Variable named #ErrorCount1.
     *
     * @param DirectoryEntry
     * @return The number of class 1 errors
     */
     public static native long GetNumClass1Errors(Pointer DirectoryEntry);

    /**
     * This function returns the number of Class 2 errors associated with the
     * specified module, DirectoryEntry. This information is also available as
     * a DINT value through DataServices if an ERRSTAT function block
     * is configured in the control module by accessing the CLASS2 output.
     * The desired system (control module and I/O) or individual module
     * address is specified by the NRS_AD input. The system total
     * NumClass2Errors value is also available as an Imbedded Global Variable
     * named #SysTotErrCount2. The module NumClass2Errors value is also
     * available as an Imbedded Global Variable named #ErrorCount2.
     *
     * @param DirectoryEntry
     * @return The number of class 1 errors
     */
     public static native long GetNumClass2Errors(Pointer DirectoryEntry);

    /**
     * This function returns the number of Class 3 errors associated with the
     * specified module, DirectoryEntry. This information is also available as
     * a DINT value through DataServices if an ERRSTAT function block
     * is configured in the control module by accessing the CLASS3 output.
     * The desired system (control module and I/O) or individual module address
     * is specified by the NRS_AD input. The system total NumClass3Errors value
     * is also available as an Imbedded Global Variable named #SysTotErrCount3.
     * The module NumClass3Errors value is also available as an Imbedded Global
     * Variable named #ErrorCount3.
     *
     * @param DirectoryEntry
     * @return The number of class 3 errors
     */
     public static native long GetNumClass3Errors(Pointer DirectoryEntry);

    /**
     * This function returns the number of Class 4 errors associated with the
     * specified module, DirectoryEntry. This information is also available as
     * a DINT value through DataServices if an ERRSTAT function block
     * is configured in the control module by accessing the CLASS4 output.
     * The desired system (control module and I/O) or individual module address
     * is specified by the NRS_AD input. The system total NumClass4Errors value
     * is also available as an Imbedded Global Variable named #SysTotErrCount4.
     * The module NumClass1Errors value is also available as an Imbedded Global
     * Variable named #ErrorCount4.
     *
     * @param DirectoryEntry
     * @return The number of class 3 errors
     */
     public static native long GetNumClass4Errors(Pointer DirectoryEntry);

    /**
     * This function returns the error summary handle associated with the
     * highest priority error local to the specified module, DirectoryEntry.
     * The ErrorSummary information is determined by the ErrorSummary
     * parsing routines.
     *
     * @param DirectoryEntry
     * @return Handle Highest priority ErrorSummary handle
     * NULL No errors are associated with this module
     */
     public static native Pointer GetFirstError(Pointer DirectoryEntry);

    /**
     * This function returns the error summary handle associated with the
     * second highest priority error local to the specified module,
     * DirectoryEntry. The ErrorSummary information is determined by the
     * ErrorSummary parsing routines.
     *
     * @param DirectoryEntry
     * @return Handle Second highest priority ErrorSummary handle
     * NULL One or less errors are associated with this module
     */
     public static native Pointer GetSecondError(Pointer DirectoryEntry);


     //Module Time Synchronization Routines

    /**
     * This function is used to send the current time to either one or all
     * control modules connected to the system, allowing you to synchronize the
     * time. If the DIRECTORY_ENTRY is NULL, the function will access
     * the current time and write it to each control module which had responded
     * to the last CollectMBusModules request. Otherwise, the time will be
     * written to the control module corresponding to the DIRECTORY_ENTRY.
     * If the communication succeeds, the response will be ER_Success. If
     * there were any problems, an ER_Failure will be returned. In the UNIX
     * versions, this function utilizes the SIGIO signal. If this signal is
     * blocked, this function will not operate correctly.
     *
     * This function block is used for control modules not configured with
     * TIMESYNC function blocks. Control modules that are configured with
     * TIMESYNC function blocks ignore this function block.
     *
     * @param DirectoryEntry
     * @return ER_Success or ER_Failure
     */
     public static native short SyncSystemTime(Pointer DirectoryEntry);

    /**
     * Using this function, the user promises to send the subsequent
     * SendAPITimeSet/Sync messages to the control modules with TIMESYNC
     * function blocks at this frequency. Once this period is set, an error
     * will be posted by the TIMESYNC function blocks if the user fails to
     * send either APITimeSet/Sync messages at this frequency. Note that the
     * time period is in milliseconds. If the user does not send this command
     * before calling either APITimeSend/Set command, this value defaults to
     * 3000 milliseconds.
     *
     * @param MilliSeconds
     * @return Always returns ER_Success
     */
     public static native short SetAPITimeSyncPeriod(long MilliSeconds);

    /**
     * This function sets the tolerance limits for the TIMESYNC function blocks.
     * If the time in the control modules drifts beyond this tolerance, an error
     * will be posted by that control module’s TIMESYNC function block.
     * The tolerance is set in microseconds. If the user does not send this
     * command before calling either SendAPITimeSet/Sync command, this value
     * defaults to 30000 microseconds.
     *
     * @param MilliSeconds
     * @return Always returns ER_Success
     */
     public static native short SetAPITimeSyncTolerance(long MilliSeconds);

    /**
     * This function is used to set the clocks in all control modules with
     * TIMESYNC function blocks enabled to the time that is passed through
     * these function parameters. If all parameters are set to zero, time from
     * the API client station will be sent to the control modules. Note that
     * the seconds in the first parameter are seconds since 1970-01-01. If
     * this command succeeds, ER_Success will be returned. If there are any
     * problems sending this command, ER_ErrorSendingMessage will be returned.
     *
     * This function is normally used only to make large changes in setting the
     * time, such as when setting the control module clocks for the first time
     * or for making daylight savings time changes.
     *
     * @param Seconds
     * @param MilliSeconds
     * @param MicroSeconds
     * @return ER_Success ER_ErrorSendingMessage
     */
     public static native short SendAPITimeSetMessage(long Seconds,
             long MilliSeconds, long MicroSeconds);

    /**
     * This function is used to update the clocks in all control modules (with
     * TIMESYNC function blocks enabled) to the time that is passed through
     * these function parameters. If all parameters are set to zero,
     * the control modules will be updated to the API client station's time.
     * Note that the seconds in the first parameter are seconds since
     * 1970-01-01. If this command succeeds, ER_Success will be returned. If
     * there are any problems sending this command, ER_ErrorSendingMessage will
     * be returned.
     *
     * This function is normally used to keep the control module
     * clocks accurate. The time sent by this function is the target time to
     * which the TIMESYNC function blocks will track. Due to the maximum
     * tracking rate limit, this function should not be used for large time
     * changes; instead, the APITimeSet function should be used.
     *
     * If an APITimeSet/Sync command is not sent, the control modules will
     * remain synchronized, but will drift at the rate of the master controller
     * clock.
     *
     * @return ER_Success ER_ErrorSendingMessage
     */
     public static native short SendAPITimeSyncMessage(long Seconds,
             long MilliSeconds, long MicroSeconds);
     
    static {
       Native.register("c:\\windows\\system32\\APXAPIWX.DLL");
    }

}
