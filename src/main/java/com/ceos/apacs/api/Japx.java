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

/**
 *
 * @author cgarcia
 */
public class Japx {

/**
 *A successful operation has been performed. For the ReadTagList( ) function,
 * any errors that may have occurred for the individual tags will be stored
 * in the tags themselves.
 */
public final static int ER_Success = 0;

 /**
  * The module address file (MODULE.ADR) could not be found. No APACS communication should
be performed.
  */
public final static int ER_ModAddrFileNotFound = 1;

  /**
   * The APACS communication port is already in use. No APACS communication should be
performed.
   */
public final static int ER_PortAlreadyUsed = 2;

  /**
   * There are no communication ports available, normally due to insufficient memory. No APACS
communication should be performed.
   */
public final static int ER_NoAvailablePorts = 3;

  /**
   * The communication functions either could not be started or could not obtain a port. No APACS
communication should be performed.
   */
 public final static int ER_CommStartFailure = 4;

/**
  * The tagname is syntactically incorrect and the tagname should not be used to create a DataTag.
  */
public final static int ER_InvalidTagName = 5;

/**
 * The module address has not yet been resolved but has not exceeded the timeout period. The reasons
for this error could be the name is wrong, the module has been removed, or the communication port
is too busy. Applications should allow this error to occur, since it can happen for completely valid
tag names. This error can occur in subsequent function calls up to the timeout period. After the
timeout period, the error returned will be converted to ER_ModuleNotFound.
 */
public final static int ER_ModuleNotResolved = 6;

/**
 * The module address has not been resolved but has exceeded the timeout period. The reasons for this
error could be the name is wrong, the module has been removed, or the communication port is too
busy. Applications should allow this error to occur, since it can happen for completely valid tag
names. If the name is correct, this error will be returned until the module is present.
 */
public final static int ER_ModuleNotFound = 7;

/**
 * The function failed due to insufficient memory available to create a communication packet or to add
a resource to the resource table. This error indicates low memory problems in the communication
hardware due to high communication demands. The operation should not be attempted again until
memory is made available by lowering the communication demand.
 */
public final static int ER_InsufficientMemory = 8;

/**
 * A failure has occurred in transmitting the data value or command. It can occur due to problems with
the communication hardware or software and will be returned until the problems are resolved. If this
error persists, delete the DataTag, create a new DataTag, and retry the operation. If this error is
returned for all DataTags on all modules, the application should be restarted.
 */
public final static int ER_TransmitError = 9;

/**
 * This error code is no longer valid.
 */
public final static int ER_TemplateFailure = 10;

/**
 * The segment for the requested tag was not found in the control module. Either the control module
does not contain the tag or the tagname is incorrect. If the tagname is correct, then the error will be
returned until the tag is added to the control module’s configuration.
 */
public final static int ER_SegmentNotFound = 11;

/**
 * No modules responded to any of the DataTags in the DataTagList. It indicates communication
problems with either the modules or the local system. This error will be returned until the problems
are resolved. If this error is returned for all DataTagLists on all modules, the application should be
restarted.
 */
public final static int ER_NoModulesResponded = 12;

/**
 * No response message was received from the module. It indicates communication problems with
either that particular module or the local system. This error will be returned until the problems are
resolved. If this error occurs for all functions on all modules, the application should be restarted.
 */
public final static int ER_NoMessageReceived = 13;

/**
 * This error code is only valid when the DataTagList was created for synchronous processing. It
indicates the Database at the module changed once, tag re-resolution was begun, and the database
changed again. Instead of possibly being in an endless loop while the module keeps changing its
revision number, the user will have the opportunity to abort the processing.
 */
public final static int ER_DBChanged = 14;

/**
 * An invalid message has been requested or a DataType mismatch error has occurred. This error will
be returned until the problems are resolved. If this error persists, the application should delete the
DataTag, create a new DataTag, and again execute the function. If this error is returned for all
DataTags on all modules, the application should be restarted.
 */
public final static int ER_InvalidRequest = 15;

/**
 * This error code is no longer valid.
 */
public final static int ER_WriteError = 16;

/**
 * The data value could not be converted to the requested data type. This error can also occur if the
ValueTypeCode is invalid. The DataTypeCode-ValueTypeCode chart, given in Appendix F, should
be consulted if the error persists.
 */
public final static int ER_InvalidConversion = 17;

/**
 * This error code is no longer valid.
 */
public final static int ER_RequestMsgRequired = 18;

/**
 * This error code is no longer valid.
 */
public final static int ER_SegmentNotCurrent = 19;

/**
 * The reference count for the DataTag in the DataTagList is not zero (0). The DataTag was added to
the DataTagList more than once, creating a reference count. The RemoveDataTag( ) function
decremented the reference count, but did not remove the DataTag from the DataTagList.
 */
public final static int ER_TagNotRemoved = 20;

/**
 * The data segment could not be written to because it has been disabled.
 */
public final static int ER_SegmentDisabled = 21;

/**
 * The operation could not be performed because the control module has security enabled and this
function is not authorized.
 */
public final static int ER_SecurityViolation = 22;

/**
 * The operation could not be performed because the module could not be accessed.
 */
public final static int ER_ModuleAccessDenied = 23;

/**
 * The format of a DATE string is invalid.
 */
public final static int ER_InvalidDateFormat = 24;

/**
 * The format of a DATETIME string is invalid.
 */
public final static int ER_InvalidDateTimeFormat = 25;

/**
 * Initialization of the Token Bus Controller chip failed.
 */
public final static int ER_TBCInitFailure = 26;

/**
 * The specified NIM name is invalid.
 */
public final static int ER_InvalidNimName = 27;

/**
 * An error occurred while trying to read the HOSTS file.
 */
public final static int ER_HostTableError = 28;

/**
 * A failure occurred initializing the network sockets layer.
 */
public final static int ER_SocketInitFailure = 29;

/**
 * A failure occurred while binding with the network interface.
 */
public final static int ER_BindingFailure = 30;

/**
 * A failure occurred setting a network socket option.
 */
public final static int ER_SocketOptFailure = 31;

/**
 * This error code is no longer valid.
 */
public final static int ER_InvalidTagType = 32;

/**
 * The API has not been initialized.
 */
public final static int ER_APINotInitialized = 33;

/**
 * This error code is no longer valid.
 */
public final static int ER_InvalidPointer = 34;

/**
 * A data path could not be found.
 */
public final static int ER_PathNotFound = 35;

/**
 * This error code is no longer valid.
 */
public final static int ER_ReadError = 36;

/**
 * If a DataTagList is being used, one or more of the modules referencing it lost communications with
this client. In other words, at some time in the past there was communication with the module, but
this communication was lost during processing of the command. Any outstanding read requests for
those modules will be removed from the system. For single tag requests, this error code indicates the
module did not respond in the timeout period.
 */
public final static int ER_TimeOutError = 37;

/**
 * An error occurred while sending a message.
 */
public final static int ER_ErrorSendingMessage = 38;

/**
 * This error code is no longer valid.
 */
public final static int ER_ErrorReceivingMessage = 39;

/**
 * The DataTag handle trying to be added to the DataTagList already exists and, thus, was not added.
 */
public final static int ER_ItemAlreadyExists = 40;

/**
 * There will be a checksum stored with each group of handles to be sent to a module for a TagList
read. Before each group of handles is sent, the checksum will be recalculated and verified with the
saved value. This error code indicates at least one group of handles was not sent to the module
because the checksums did not agree. There is also the possibility that other read requests may have
completed successfully. If this error occurs, the DataTagList will attempt to fix it by resolving the
tags in the corrupted group. Therefore, this response is only to inform the user that there is the possibility that somewhere in the client station a function may be overwriting the handles.
 */
public final static int ER_CheckSumError = 41;

/**
 * This error code is no longer valid.
 */
public final static int ER_InvalidHandle = 42;

/**
 * An array function was performed on a DataTag that is not an array.
 */
public final static int ER_NotArray = 43;

/**
 * The bit specified is outside the possible range of bits.
 */
public final static int ER_InvalidBit = 44;

/**
 * This error code is no longer valid.
 */
public final static int ER_IncompatibleTemplate = 45;

/**
 * Asynchronous communication is occurring and is not yet complete. A continue type of command
should be executed until ER_ProcessPending is not returned.
 */
public final static int ER_ProcessPending = 46;

/**
 * One or more modules referenced by the DataTagList or the module referenced by the DataTag was
busy performing another task that could not be interrupted. If the function is tried again later, it
might work. For a Read of a DataTagList, this error code indicates the read is complete and there
may have been some good data returned.
 */
public final static int ER_ModuleBusy = 47;

/**
 * The type of data attempted to be written is not the same as the type of data stored in the module. Try
reading the tag again before executing the write.
 */
public final static int ER_DataTypeMismatch = 48;

/**
 * Before a read or write was started, the system determined there were no communications buffers
available. Thus, this communication cannot take place until some buffers are freed up. This will
occur when the user continues processing other outstanding communications. After continuing the
other communications, the user should attempt to start this read or write again.
 */
public final static int ER_NoCommBufferAvailable = 49;

/**
 * One or more of the parameters passed to the function are invalid.
 */
public final static int ER_InvalidParameter = 253;

/**
 * The operation failed for the following reasons:<br>
• <b>ContinueCollectMBusModules()</b> —This command either did not follow a
CollectMBusModulesAsync() command, or the CollectMBusModulesAsync() command failed
and the continue command should not have been attempted.<br>
• <b>ContinueCollectIOBusModules()</b> —This command either did not follow a
CollectIOBusModulesAsync() command, or the CollectIOBusModulesAsync() command failed
and the continue command should not have been attempted.<br>
• <b>InitializeAPI( )</b> — The Heap memory manager was already created or there is not enough
memory to create one.<br>
• <b>ShutDownAPI( )</b> — The Heap memory manager was already deleted.<br>
• <b>AddNimName( )</b> — No NimNameTable exists or the table is full.<br>
• <b>DeleteNimName( )</b> — No NimNameTable exists, the table is empty, or the specified NimName
is not in the table.<br>
• <b>ResetNimNameTable( )</b> — No NimNameTable exists.<br>
• <b>GetModuleMode( )</b> — The module was found but the operation failed.<br>
• <b>StartModule( )</b> — The module was found but the operation failed. This may be due to the
operation being invalid for the current state of the control module.<br>
• <b>StopModule( )</b> — The module was found but the operation failed. This may be due to the
operation being invalid for the current state of the control module.<br>
• <b>StepModule( )</b> — The module was found but the operation failed. This may be due to the
operation being invalid for the current state of the control module.<br>
• <b>RemoveDataTag( )</b> — The DataTag does not exist in the DataTagList.<br>
• <b>ValidateTagName( )</b> — The function failed due to insufficient local memory. The application
should free up local memory and retry the operation.<br>
• <b>PutDataQuality( )</b> — The function could not be performed due to no data object for the
DataTag.<br>
• <b>PutData( )</b> — The function could not be performed due to no data object for the DataTag.<br>
• <b>SetEnable( )</b> — The function could not be performed due to no data object for the DataTag.<br>
• <b>SetWriteProtect( )</b> — The function could not be performed due to no data object for the
DataTag.<br>
 */
public final static int ER_Failure = 254;

/**
 * An error is returned from a lower level subroutine and is not recognized by the API. This indicates
that the function has failed and that other functions may fail also. This error should never occur in
released software, but if it occurs, it should be reported to Moore Products Co.
 */
public final static int ER_UnknownError = 255;

//DATA STATUS CODES

/**
 * The data tag has never been read.
 */
public final static int DATA_UNINITIALIZED = 0;

/**
 * No communication error has occurred and generally indicates a good status condition.
 */
public final static int NO_IO_ERROR = 1;

/**
 * The specified resource could not be located in the APACS system.
 */
public final static int RESOURCE_NOT_FOUND = 2;

/**
 * The specified resource was located, but the specified data value within the resource was not found.
 */
public final static int TAG_NOT_RESOLVED = 3;

/**
 * An invalid data type has been defined for the tag.
 */
public final static int INVALID_DATA_TYPE = 4;

/**
 * The data value could not be read since the database has changed. Rereading the data should remove this
error.
 */
public final static int DB_CHANGE_ERROR = 5;

/**
 * The data value could not be transmitted.
 */
public final static int TRANSMIT_FAILURE = 6;

/**
 * The resource allocation function failed.
 */
public final static int RESOURCE_ALLOC_FAILURE = 7;

/**
 * A time out occurred waiting for a response from a resource.
 */
public final static int TIME_OUT_WAITING_FOR_RESP = 8;

/**
 * Resolution of the resource is still pending. Rereading the data will ultimately remove this error.
 */
public final static int RESOURCE_NOT_YET_RESOLVED = 9;

/**
 * The operation could not be performed because the control module has security enabled and the previous
function performed is not authorized.
 */
public final static int SECURITY_VIOLATION_DETECTED = 10;

/**
 * A read operation detected a different data type than was defined when the DataTag was originally
created.
 */
public final static int DATATYPE_MISMATCH = 11;

/**
 * This Status indicates that some memory relating to this tag has been corrupted.
 */
public final static int MEMORY_ERROR = 12;

/**
 * A complete array was not able to be returned by the ReadTag function.
 */
public final static int DATA_TRUNCATED = 13;

/**
 * An array DataTag is included in a DataTagList.
 */
public final static int DTYPE_NOT_SUPPORTED = 14;

/**
 * This Status indicates the module was busy executing some other task and could not respond to the
request for this tag.
 */
public final static int MODULE_IS_BUSY = 15;

/**
 * This status code indicates that the status code returned from a lower level communication code is
unknown.
 */
public final static int UNKNOWN_STATUS_CODE = 255;

//DATA QUALITY CODES

/**
 * Good data is present.
 */
public final static int DATA_GOOD = 0;

/**
 * Data was successfully read, but the data value was marked as Bad by the control module or server
application.
 */
public final static int DATA_BAD = 1;

/**
 * Data was successfully read, but the data value was marked as Questionable by the control module or
server application.
 */
public final static int DATA_QUESTIONABLE = 2;

/**
 * Data was successfully read, but the data value was marked as Unavailable by the control module or
server application.
 */
public final static int DATA_UNAVAILABLE = 3;

//DATA TYPE CODE VALUES

/**
 * Invalid<br>
 * Bytes: N/A
 */
public final static int DTYPE_NULL = 0;

/**
 * int Integer<br>
 * Bytes: 1
 */
public final static int DTYPE_SINT = 1;

/**
 * Unsigned int Integer<br>
 * Bytes: 1
 */
public final static int DTYPE_USINT = 2;

/**
 * Integer<br>
 * Bytes: 2
 */
public final static int DTYPE_INT = 3;

/**
 * unsigned Integer<br>
 * Bytes: 2
 */
public final static int DTYPE_UINT = 4;

/**
 * Double Integer<br>
 * Bytes: 4
 */
public final static int DTYPE_DINT = 5;

/**
 * unsigned Double Integer<br>
 * Bytes: 4
 */
public final static int DTYPE_UDINT = 6;

/**
 * Bit String of 8 bits. <b> No implemented.</b><br>
 * Bytes: 1
 */
public final static int DTYPE_BYTE = 7;

/**
 * Bit String of 16 bits.<br>
 * Bytes: 2
 */
public final static int DTYPE_WORD = 8;

/**
 * Bit String of 32 bits. <b> No implemented.</b><br>
 * Bytes: 4
 */
public final static int DTYPE_DWORD = 9;

/**
 * Time (Duration)<br>
 * Bytes: 4
 */
public final static int DTYPE_TIME = 10;

/**
 * Real<br>
 * Bytes: 4
 */
public final static int DTYPE_REAL = 11;

/**
 * Time Of Day<br>
 * Bytes: 4
 */
public final static int DTYPE_TIMEOFDAY = 12;

/**
 * Date<br>
 * Bytes: 4
 */
public final static int DTYPE_DATE = 13;

/**
 * Date and Time of day<br>
 * Bytes: 4
 */
public final static int DTYPE_DATETIME = 14;

/**
 * Error Summary<br>
 * Bytes: 4
 */
public final static int DTYPE_ERROR_SUMMARY = 18;

/**
 * Long Real.<b> No implemented.</b><br>
 * Bytes: 8
 */
public final static int DTYPE_LREAL = 20;

/**
 * String<br>
 * Bytes: Variable
 */
public final static int DTYPE_STRING = 21;

/**
 * Long integer. <b> No implemented.</b><br>
 * Bytes: 8
 */
public final static int DTYPE_LINT = 22;

/**
 * Unsigned Long integer. <b> No implemented.</b><br>
 * Bytes: 8
 */
public final static int DTYPE_ULINT = 23;

/**
 * Bit String of 64 bits. <b> No implemented.</b><br>
 * Bytes: 8
 */
public final static int DTYPE_LWORD = 24;

/**
 * Byte Array. <b> No implemented.</b><br>
 * Bytes: Variable
 */
public final static int DTYPE_BYTEARRAY = 25;

/**
 * Array of segments.<br>
 * Bytes: Variable
 */
public final static int DTYPE_ARRAY = 26;

/**
 * Boolean.<br>
 * Bytes: 1
 */
public final static int DTYPE_BOOL = 128;

//VALUE TYPE CODES

/**
 * Invalid.<br>
 * Bytes: N/A
 */
public final static short TYPE_NULL = 0;

/**
 * Signed 8-bit Integer.<br>
 * Bytes: 1
 */
public final static short TYPE_S8 = 1;

/**
 * Unsigned 8-bit Integer.<br>
 * Bytes: 1
 */
public final static short TYPE_U8 = 2;

/**
 * Signed 16-bit Integer.<br>
 * Bytes: 2
 */
public final static short TYPE_S16 = 3;

/**
 * Unsigned 16-bit Integer.<br>
 * Bytes: 2
 */
public final static short TYPE_U16 = 4;

/**
 * Signed 32-bit Integer.<br>
 * Bytes: 4
 */
public final static short TYPE_S32 = 5;

/**
 * Unsigned 32-bit Integer.<br>
 * Bytes: 4
 */
public final static short TYPE_U32 = 6;

/**
 * Signed 64-bit Integer.<br>
 * Bytes: 8
 */
public final static short TYPE_S64 = 7;

/**
 * Unsigned 64-bit Integer.<br>
 * Bytes: 8
 */
public final static short TYPE_U64 = 8;

/**
 * IEEE 32-bit Floating Point.<br>
 * Bytes: 4
 */
public final static short TYPE_F32IEEE = 9;

/**
 * IEEE 64-bit Floating Point.<br>
 * Bytes: 8
 */
public final static short TYPE_F64IEEE = 10;

/**
 * String.<br>
 * Bytes: Variable
 */
public final static short TYPE_STRING = 11;

/**
 * Boolean.<br>
 * Bytes: 2
 */
public final static short TYPE_LOGICAL = 12;

//MODULE TYPE CODES

/**
 * Advanced Control Module.
 */
public final static int MT_ACM = 0;

/**
 * MYCRO™ HART® Hi-Level Link Gateway.
 */
public final static int MT_MH_HLL_GW = 1;

/**
 * MYCRO HART Local Instrument Link Gateway
 */
public final static int MT_MH_LIL_GW = 2;

/**
 * MYCRO HART Fieldbus Multiplexer
 */
public final static int MT_MH_HFM = 3;

/**
 * APACS HART Fieldbus Multiplexer
 */
public final static int MT_APACS_HFM = 4;

/**
 * APACS - Hi-Level Link Gateway
 */
public final static int MT_APACS_HLL_GW = 5;

/**
 * APACS - Local Instrument Link Gateway
 */
public final static int MT_APACS_LIL_GW = 6;

/**
 * Standard Analog Module
 */
public final static int MT_SAM = 7;

/**
 * Standard Discrete Module
 */
public final static int MT_SDM = 8;

/**
 * Discrete Input Module
 */
public final static int MT_IDM = 9;

/**
 * Discrete Output Module
 */
public final static int MT_ODM = 10;

/**
 * Industrial Computer Module
 */
public final static int MT_ICM = 11;

/**
 * Computer Expansion Module
 */
public final static int MT_CEM = 12;

/**
 * Voltage Input Module
 */
public final static int MT_VIM = 13;

/**
 * Power Supply Module
 */
public final static int MT_PSM = 14;

/**
 * Module Bus Expander
 */
public final static int MT_MBX = 15;

/**
 * Network Interface Module
 */
public final static int MT_NIM = 16;

/**
 * IOBUS Expander Module
 */
public final static int MT_IOX = 17;

/**
 * Enhanced Discrete Module
 */
public final static int MT_EDM = 18;

/**
 * Enhanced Analog Module
 */
public final static int MT_EAM = 19;

/**
 * PC with Module Bus Interface (MBI) Card
 */
public final static int MT_UAI = 20;

/**
 * General Purpose Client Code
 */
public final static int MT_CLIENT = 21;

/**
 * Resistance Temperature Module
 */
public final static int MT_RTM = 22;

/**
 * Satellite Control Module
 */
public final static int MT_SCM = 23;

/**
 * Serial I/O Module
 */
public final static int MT_SIM = 24;

/**
 * Computer Products Expander Module
 */
public final static int MT_CPX = 25;

/**
 * Gage Interface Module
 */
public final static int MT_GIM = 26;

/**
 * Unknown Module Type code
 */
public final static int MT_UNKNOWN = 26;

//MODULE MODE

/**
 * Stop Mode
 */
public final static short STOP_MODE = 0;

/**
 * Run Mode
 */
public final static short RUN_MODE = 1;

//SEGMENT
/**
 * Input Segment
 */
public final static int INPUT_SEGMENT = 0;

/**
 * Output segment
 */
public final static int OUTPUT_SEGMENT = 1;


//Strings for errors.

/**
 * Return int error description.
 * @param err
 * @return String with the error description.
 */
public static String getErrorString(int err){
    String s = "";

    switch(err){
        case ER_Success  : s = "ER_Success";
                break;
        case ER_ModAddrFileNotFound  : s = "ER_ModAddrFileNotFound";
                break;
        case ER_PortAlreadyUsed  : s = "ER_PortAlreadyUsed";
                break;
        case ER_NoAvailablePorts : s = "ER_NoAvailablePorts";
                break;
        case ER_CommStartFailure : s = "ER_CommStartFailure";
                break;
        case ER_InvalidTagName: s = "ER_InvalidTagName";
                break;
        case ER_ModuleNotResolved: s = "ER_ModuleNotResolved";
                break;
        case ER_ModuleNotFound: s = "ER_ModuleNotFound";
                break;
        case ER_InsufficientMemory: s = "ER_InsufficientMemory";
                break;
        case ER_TransmitError: s = "ER_TransmitError";
                break;
        case ER_TemplateFailure: s = "ER_TemplateFailure";
                break;
        case ER_SegmentNotFound: s = "ER_SegmentNotFound";
                break;
        case ER_NoModulesResponded: s = "ER_NoModulesResponded";
                break;
        case ER_NoMessageReceived: s = "ER_NoMessageReceived";
                break;
        case ER_DBChanged: s = "ER_DBChanged";
                break;
        case ER_InvalidRequest: s = "ER_InvalidRequest";
                break;
        case ER_WriteError: s = "ER_WriteError";
                break;
        case ER_InvalidConversion: s = "ER_InvalidConversion";
                break;
        case ER_RequestMsgRequired: s = "ER_RequestMsgRequired";
                break;
        case ER_SegmentNotCurrent: s = "ER_SegmentNotCurrent";
                break;
        case ER_TagNotRemoved: s = "ER_TagNotRemoved";
                break;
        case ER_SegmentDisabled: s = "ER_SegmentDisabled";
                break;
        case ER_SecurityViolation: s = "ER_SecurityViolation";
                break;
        case ER_ModuleAccessDenied: s = "ER_ModuleAccessDenied";
                break;
        case ER_InvalidDateFormat: s = "ER_InvalidDateFormat";
                break;
        case ER_InvalidDateTimeFormat: s = "ER_InvalidDateTimeFormat";
                break;
        case ER_TBCInitFailure: s = "ER_TBCInitFailure";
                break;
        case ER_InvalidNimName: s = "ER_InvalidNimName";
                break;
        case ER_HostTableError: s = "ER_HostTableError";
                break;
        case ER_SocketInitFailure: s = "ER_SocketInitFailure";
                break;
        case ER_BindingFailure: s = "ER_BindingFailure";
                break;
        case ER_SocketOptFailure: s = "ER_SocketOptFailure";
                break;
        case ER_InvalidTagType: s = "ER_InvalidTagType";
                break;
        case ER_APINotInitialized: s = "ER_APINotInitialized";
                break;
        case ER_InvalidPointer: s = "ER_InvalidPointer";
                break;
        case ER_PathNotFound: s = "ER_PathNotFound";
                break;
        case ER_ReadError: s = "ER_ReadError";
                break;
        case ER_TimeOutError: s = "ER_TimeOutError";
                break;
        case ER_ErrorSendingMessage: s = "ER_ErrorSendingMessage";
                break;
        case ER_ErrorReceivingMessage: s = "ER_ErrorReceivingMessage";
                break;
        case ER_ItemAlreadyExists: s = "ER_ItemAlreadyExists";
                break;
        case ER_CheckSumError: s = "ER_CheckSumError";
                break;
        case ER_InvalidHandle: s = "ER_InvalidHandle";
                break;
        case ER_NotArray: s = "ER_NotArray";
                break;
        case ER_InvalidBit: s = "ER_InvalidBit";
                break;
        case ER_IncompatibleTemplate: s = "ER_IncompatibleTemplate";
                break;
        case ER_ProcessPending: s = "ER_ProcessPending";
                break;
        case ER_ModuleBusy: s = "ER_ModuleBusy";
                break;
        case ER_DataTypeMismatch: s = "ER_DataTypeMismatch";
                break;
        case ER_NoCommBufferAvailable: s = "ER_NoCommBufferAvailable";
                break;
        case ER_InvalidParameter: s = "ER_InvalidParameter";
                break;
        case ER_Failure: s = "ER_Failure";
                break;
        case ER_UnknownError: s = "ER_UnknownError";
                break;
        default: s = "ER_without_description";
                break;
    }
    return s;
}

/**
 * Return string containing the data status code that may return from some
 * functions during API operations.
 * @param dsc Code return from other function.
 * @return
 */
public static String getDataStatusCodeString(int dsc){
 String s = "";

     switch(dsc){
        case DATA_UNINITIALIZED : s = "DATA_UNINITIALIZED";
                break;
        case NO_IO_ERROR : s = "NO_IO_ERROR";
                break;
        case RESOURCE_NOT_FOUND: s = "RESOURCE_NOT_FOUND";
                break;
        case TAG_NOT_RESOLVED: s = "TAG_NOT_RESOLVED";
                break;
        case INVALID_DATA_TYPE: s = "INVALID_DATA_TYPE";
                break;
        case DB_CHANGE_ERROR: s = "DB_CHANGE_ERROR";
                break;
        case TRANSMIT_FAILURE: s = "TRANSMIT_FAILURE";
                break;
        case RESOURCE_ALLOC_FAILURE: s = "RESOURCE_ALLOC_FAILURE";
                break;
        case TIME_OUT_WAITING_FOR_RESP: s = "TIME_OUT_WAITING_FOR_RESP";
                break;
        case RESOURCE_NOT_YET_RESOLVED: s = "RESOURCE_NOT_YET_RESOLVED";
                break;
        case SECURITY_VIOLATION_DETECTED: s = "SECURITY_VIOLATION_DETECTED";
                break;
        case DATATYPE_MISMATCH: s = "DATATYPE_MISMATCH";
                break;
        case MEMORY_ERROR: s = "MEMORY_ERROR";
                break;
        case DATA_TRUNCATED: s = "DATA_TRUNCATED";
                break;
        case DTYPE_NOT_SUPPORTED: s = "DTYPE_NOT_SUPPORTED";
                break;
        case MODULE_IS_BUSY: s = "MODULE_IS_BUSY";
                break;
        case UNKNOWN_STATUS_CODE: s = "UNKNOWN_STATUS_CODE";
                break;
        default: s = "Without description!.";
                break;
    }

 return s;
}

/**
 * Return string containing the data quality code that may return from some
 * functions during API operations.
 * @param dsc Code return from other function.
 * @return
 */
public static String getDataQualityCodeString(int dqc){
 String s = "";

     switch(dqc){
        case DATA_GOOD: s = "DATA_GOOD";
                break;
        case DATA_BAD : s = "DATA_BAD";
                break;
        case DATA_QUESTIONABLE: s = "DATA_QUESTIONABLE";
                break;
        case DATA_UNAVAILABLE: s = "DATA_UNAVAILABLE";
                break;
        default: s = "Without description!.";
                break;
    }

 return s;
}

/**
 * Return string containing the module type code that may return from some
 * functions during API operations.
 * @param dsc Code return from other function.
 * @return
 */
public static String getmoduleTypeCodeString(int mtc){
 String s = "";

     switch(mtc){
        case DATA_GOOD: s = "DATA_GOOD";
                break;
        case DATA_BAD : s = "DATA_BAD";
                break;
        case DATA_QUESTIONABLE: s = "DATA_QUESTIONABLE";
                break;
        case DATA_UNAVAILABLE: s = "DATA_UNAVAILABLE";
                break;
        default: s = "Without description!.";
                break;
    }

 return s;
}

/**
 * Return string containing with the module mode code that may return from some
 * functions during API operations.
 * @param mtc Code return from other function.
 * @return
 */
public static String getModuleModeString(short mtc){
 String s = "";

     switch(mtc){
        case STOP_MODE: s = "STOP_MODE";
                break;
        case RUN_MODE : s = "RUN_MODE";
                break;
        default: s = "Without description!.";
                break;
    }

 return s;
}


}
