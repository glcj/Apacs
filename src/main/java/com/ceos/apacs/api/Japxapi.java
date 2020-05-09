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
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ShortByReference;
import com.sun.jna.ptr.NativeLongByReference;

/**
 * Este objeto
 * 
 * @author cgarcia
 */
public class Japxapi implements Library {

   //API Management Routines

    /**
     * This function initializes the API. This function must be called
     * before any other functions are executed and must only be called
     * once. Should the function fail, no other functions should be used.
     *
     * @param
     * @return ER_Success, ER_Failure
     */
    public static native short InitializeAPI();

    /**
     * This function performs all clean-up operations required to
     * terminate API operations. This function should be used at the end
     * of the program, and once this function has been executed, no
     * further functions should be executed.
     *
     * @param
     * @return ER_Success, ER_Failure
     */
    public static native short ShutDownAPI();

    /**
     * This function must be performed on a periodic basis to process
     * communication messages that have been received by the API. The
     * frequency at which this function should be called is dependent
     * upon the number of messages being received and the amount of
     * buffer memory available. DoPolling( ) should be done during any
     * idle time or during any program loops, such as adding or removing
     * DataTags or during asynchronous communication. The suggested
     * minimum frequency is 2-4 times per second for client applications
     * and 10-20 times per second for server applications. In the UNIX
     * versions, this function utilizes the SIGIO signal. If this signal
     * is blocked, this function will not operate correctly.
     *
     * @param
     * @return
     */
    public static native synchronized  void DoPolling();

    /**
     * This function returns a text string containing the version of the
     * API library. A NULL-terminated string will be placed in Buffer,
     * whose size is specified by BufferSize. If the text string is
     * inter than BufferSize, the buffer will be NULL-filled. If the
     * text string is longer than BufferSize -1, it will be truncated.
     * The return value indicates the number of characters copied into
     * the buffer (the buffer length), not including the terminating
     * NULL. The maximum size of the buffer is 16 characters.
     *
     * Return: Number of characters in buffer
     */
    public static native short GetVersionLabel(byte[] buffer, short length);

    /**
     * This function returns an indication of the availability of system
     * memory. This function can be used prior to creating tags or adding
     * tags to a list in order to determine if sufficient memory exists.
     * If the function equals TRUE, tags should not be created or added
     * to a list. This is particularly useful in DOS applications. In
     * Windows applications, if system memory is low, other application
     * programs may be able to be closed to regain memory.
     *
     * @return TRUE System memory low, FALSE System memory not low
    */

    public static native boolean IsSystemMemoryLow();

    /**
     * This function will allow the caller to have the API wait for the
     * passed milliseconds amount of time without consuming CPU time.
     * If a signal arrives during this period, the routine will return
     * immediately. Otherwise, the routine will return at the end of the
     * timeout period. This function is not implemented on 16-bit Windows
     * applications (before Win95 and WinNT) since there is no lower level
     * routine that performs this function. However, on the other platforms,
     * the routine will call a lower level routine, which does this
     * processing without using CPU processing time.
     *
     * @param Milliseconds milliseconds amount of time without consuming CPU time.
     * @return ER_Success Signal arrived, ER_TimeOutError No signal arrived
     * ER_InvalidRequest Not allowed on this platform
     */
      public static native short WaitForResponse( NativeLong Milliseconds );



    //NimNameTable Management Routines

    /**
     * This function creates and initializes a table containing the
     * names of Rack-Mounted Network Interfaces (RNIs) or Network Interface
     * Modules (NIMs) to which a networked client application will attempt to
     * connect when DataServices are initialized. This function is not
     * required to communicate to APACS via the RNIs or NIMs, but it does
     * allow selective and/or user-defined names for the NIMs. If the
     * NimNameTable is not used, the default NIM names “NIM1” through “NIM10”
     * are searched in the HOSTS file and used when DataServices or
     * Directory Services are initialized. This function must be called
     * before any other NimNameTable functions are used and before calling the
     * InitializeDataServices( ) or InitializeDirectoryServices( ) functions.
     * This function should only be called once.
     *
     * The table allows up to 64 names to be added but, at present, only a
     * maximum of 10 should be used.
     *
     * Typically, these names and their associated IP addresses will exist
     * in the HOSTS file.
     *
     * @return ER_Success, ER_APINotInitialized
     */
    public static native short InitializeNimNameTable();

    /**
     * This function adds the NULL-terminated string pointed to by NimName to
     * the first available location in the NimNameTable. The NIM name can be
     * a maximum of 32 characters (excluding the NULLtermination).
     *
     * The function will return a failure indication if the NimNameTable does
     * not exist or if the table is full.
     *
     * @param NimName The NIM name can be a maximum of 32 characters
     * @return ER_Success, ER_Failure
     */
    public static native short AddNimName(String  NimName);

    /**
     * This function deletes the NULL-terminated string pointed to by NimName
     * from the NimNameTable. The NIM name can be a maximum of 32 characters
     * (excluding the NULL-termination). The function will return a failure
     * indication if the NimNameTable does not exist, if the specified NimName
     * is not in the table, or if the table is empty.
     *
     * @param NimName
     * @return ER_Success, ER_Failure
     */
    public static native short DeleteNimName(String  NimName);

    /**
     * This function resets the pointer to the top of the NimNameTable so that
     * the GetNextNimName( ) function returns the first name in the table.
     * A failure indication will be returned if the NimNameTable does not exist.
     *
     * @return ER_Success, ER_Failure
     */
    public static native short ResetNimNameTable();

    /**
     * This function returns a text string containing the next NIM name in the
     * NimNameTable and then moves the pointer to the next name in the table.
     * A NULL-terminated string will be placed in Buffer, whose size is
     * specified by BufferSize. If the text string is inter than BufferSize,
     * the buffer will be NULL-filled. If the text string is longer than
     * BufferSize -1, it will be truncated. The return value indicates the number
     * of characters copied into the buffer (the buffer length), not including
     * the terminating NULL. The maximum size of the buffer is 33 characters.
     *
     * @param Buffer
     * @param BufferSize
     * @return Number of characters in buffer
     */
    public static native short GetNextNimName(byte[] Buffer, short BufferSize);


    //ErrorSummary Parsing Routines

    /**
     * This function returns an indication of whether or not the specified
     * ErrorSummary is valid. If ErrorSummary represents a “no error” condition,
     * then FALSE is returned.
     *
     * @param ErrorSummary handle
     * @return TRUE ErrorSummary is valid,<br>
     * FALSE ErrorSummary is not valid
     */
    public static native boolean IsErrorValid(Pointer ErrorSummary);

    /**
     * This function returns the class code of the specified ErrorSummary.
     * The returned value represents the severity of the error that occurred.
     * The information for the highest error is also available as an INT value
     * through DataServices if an ERRSTAT function block is configured in the
     * control module by accessing the CLASS output. The desired system
     * (control module and I/O) or individual module address is
     * specified by the NRS_AD input.
     *
     * @param ErrorSummary handle
     * @return 1 Informational or warning error class<br>
     * 2 Non-Fatal less severe error class<br>
     * 3 Non-Fatal more severe error class<br>
     * 4 Fatal error class<br>
     */
     public static native short GetErrorClass(Pointer ErrorSummary);

    /**
     * This function returns the scope of the specified ErrorSummary.
     * The returned value identifies what portion of the system is affected
     * by the error condition. The information for the highest error is also
     * available as a string value through DataServices if an ERRSTAT function
     * block is configured in the control module by accessing the SCOPE output.
     * The desired system (control module and I/O) or individual module
     * address is specified by the NRS_AD input.
     *
     * @param ErrorSummary handle
     * @return 0 System error scope<br>
     * 1 Module error scope<br>
     * 2 Group error scope<br>
     * 3 Channel error scope<br>
     * 4 Invalid error scope<br>
     */
     public static native short GetErrorScope(Pointer ErrorSummary);

     /**
      * This function returns the system service code of the specified
      * ErrorSummary. The returned value identifies what piece of software
      * reported the error condition. The information for the highest error
      * is also available as an INT value through DataServices if an
      * ERRSTAT function block is configured in the control module by
      * accessing the SS_CD output. The desired system (control module and
      * I/O) or individual module address is specified by the NRS_AD input.
      *
      * @param ErrorSummary handle
      * @return System service code of the ErrorSummary
      */
     public static native short GetSystemServiceCode(Pointer ErrorSummary);

     /**
      * This function returns the error code associated with the specified
      * ErrorSummary. The returned value represents the specific error
      * condition that occurred. Each system service code has its own set
      * of error codes. Thus, error code 1 means something different for each
      * system service code. The information for the highest error is also
      * available as an INT value through DataServices if an ERRSTAT function
      * block is configured in the control module by accessing the ERR_CD
      * output. The desired system (control module and I/O) or individual
      * module address is specified by the NRS_AD input.
      *
      * @param ErrorSummary handle
      * @return Error code of the ErrorSummary
      */
     public static native short GetErrorCode(Pointer ErrorSummary);

     /**
      * This function returns the user action code associated with the
      * specified ErrorSummary. The returned value gives a general indication
      * of what corrective action is required by the user. The user action codes
      * are not affected by the system service code. The information for the
      * highest error is also available as a string value through DataServices
      * if an ERRSTAT function block is configured in the control module by
      * accessing the USER_A output. The desired system (control module and I/O)
      * or individual module address is specified by the NRS_AD input.
      *
      * @param ErrorSummary handle
      * @return User action code of the ErrorSummary
      */
     public static native short GetUserActionCode(Pointer ErrorSummary);

     /**
      * This function returns the channel number associated with the specified
      * ErrorSummary if the ErrorScope indicates “channel.” The information for
      * the highest error is also available as an INT value through
      * DataServices if an ERRSTAT function block is configured in the control
      * module by accessing the CHAN output. The desired system (control module
      * and I/O) or individual module address is specified by the
      * NRS_AD input.
      *
      * @param ErrorSummary handle
      * @return Channel number of the ErrorSummary
      */
     public static native short GetChannelNumber(Pointer ErrorSummary);

     /**
      * This function returns the group number of the specified ErrorSummary
      * if the ErrorScope indicates “group.” The information for the highest
      * error is also available as a string value through DataServices if
      * an ERRSTAT function block is configured in the control module by
      * accessing the GROUP output. The desired system (control module and I/O)
      * or individual module address is specified by the NRS_AD input.
      *
      * @param ErrorSummary handle
      * @return Group number of the ErrorSummary
      */
     public static native short GetGroupNumber(Pointer ErrorSummary);

     /**
      * This function returns the node number of the module on which the
      * associated ErrorSummary occurred. The information for the highest
      * error is also available as a string value through DataServices if an
      * ERRSTAT function block is configured in the control module by accessing
      * the ADDR output. The desired system (control module and I/O) or
      * individual module address is specified by the NRS_AD input.
      * The system ErrorNodeNumber value is also available as an embedded
      * global variable named #SystemErrorNode.
      *
      * @param ErrorSummary handle
      * @return Node number of the module reporting the ErrorSummary
      */
     public static native short GetErrorNodeNumber(Pointer ErrorSummary);

     /**
      * This function returns the rack number of the module on which the
      * associated ErrorSummary occurred. The information for the highest
      * error is also available as a string value through DataServices if an
      * ERRSTAT function block is configured in the control module by accessing
      * the ADDR output. The desired system (control module and I/O) or
      * individual module address is specified by the NRS_AD input.
      * The system ErrorRackNumber value is also available as an embedded
      * global variable named #SystemErrorRack.
      *
      * @param ErrorSummary handle
      * @return Rack number of the module reporting the ErrorSummary
      */
     public static native short GetErrorRackNumber(Pointer ErrorSummary);

     /**
      * This function returns the slot number of the module on which the
      * associated ErrorSummary occurred. The information for the highest
      * error is also available as a string value through DataServices if an
      * ERRSTAT function block is configured in the control module by accessing
      * the ADDR output. The desired system (control module and I/O) or
      * individual module address is specified by the NRS_AD input.
      * The system ErrorSlotNumber value is also available as an embedded
      * global variable named #SystemErrorSlot.
      *
      * @param ErrorSummary handle
      * @return Slot number of the module reporting the ErrorSummary
      */
      public static native short GetErrorSlotNumber(Pointer ErrorSummary);


     //DataServices Management Routines

     /**
      * This function performs all of the communication initialization required
      * for DataServices. This function must be called before any other
      * DataServices functions are executed. Should the function fail,
      * DataServices are not available and should not be used. In the UNIX
      * versions, this function utilizes the SIGIO signal. If this signal is
      * blocked, this function will not operate correctly.
      * The ResourceName is required for server applications and is strongly
      * suggested for client applications. The ResourceName will appear in
      * the Module Tree of 4-mation™ when the application is running.
      * If the NimNameTable is to be used, the NimNameTable must be initialized
      * (see section 4.1.2) prior to this function being called.
      * <b>IMPORTANT:</b> Executing DataServices functions before this call may
      * abort the program.
      *
      * @param ResourceName Name to be show in 4mmation tree
      * @return ER_Success ER_InsufficientMemory ER_ModAddrFileNotFound
      * ER_TBCInitFailure ER_PortAlreadyUsed ER_APINotInitialized
      * ER_NoAvailablePorts ER_UnknownError ER_CommStartFailure<br>
      * Additional network application error codes:<br>
      * ER_InvalidNimName ER_HostTableError ER_SocketInitFailure
      * ER_BindingFailure ER_SocketOptFailure
      */
      public static native short InitializeDataServices(String ResourceName);

     /**
      * This function cleans up after DataServices is completed. Once this
      * function has been executed, no further DataServices functions should be
      * executed. If it is desired to connect to other NIMs, this function
      * should be called to shut down the current DataServices, the
      * NimNameTable set up as desired, and the DataServices started back up
      * using the InitializeDataServices( ) function. In the UNIX versions,
      * this function utilizes the SIGIO signal. If this signal is blocked,
      * this function will not operate correctly.
      * <b>IMPORTANT:</b> Executing DataServices functions after this call
      * may abort the program.
      */

      public static native void ShutDownDataServices();


     //Module Mode Control Routines

     /**
      * This function returns the mode of the module ResourceName in ModuleMode.
      * In the UNIX versions, this function utilizes the SIGIO signal. If this
      * signal is blocked, this function will not operate correctly. Valid
      * values of ModuleMode are: 0 = STOP_MODE 1 = RUN_MODE
      *
      * @param ResourceName
      * @param ModuleMode 0 = STOP_MODE 1 = RUN_MODE
      * @return ER_Success ER_Failure ER_ModuleNotResolved ER_UnknownError
      * ER_ModuleNotFound ER_ModuleAccessDenied ER_InsufficientMemory
      * ER_InvalidTagName ER_InvalidParameter
      */
      public static native short GetModuleMode(String ResourceName, short[] ModuleMode );

     /**
      * This function is used to start the execution of the module of name
      * ResourceName. In the UNIX versions, this function utilizes the SIGIO
      * signal. If this signal is blocked, this function will not operate
      * correctly.
      *
      * @param ResourceName
      * @return ER_Success ER_Failure ER_ModuleNotResolved ER_UnknownError
      * ER_ModuleNotFound ER_ModuleAccessDenied ER_InsufficientMemory
      * ER_InvalidTagName
      */
      public static native short StartModule(String ResourceName);

     /**
      * This function is used to stop the execution of the module of name
      * ResourceName. In the UNIX versions, this function utilizes the SIGIO
      * signal. If this signal is blocked, this function will not operate
      * correctly.
      *
      * @param ResourceName
      * @return ER_Success ER_Failure ER_ModuleNotResolved ER_UnknownError
      * ER_ModuleNotFound ER_InvalidTagName ER_InsufficientMemory
      * ER_InvalidParameter
      */
      public static native short StopModule(String ResourceName);

     /**
      * This function is used to single-step the execution of the module of
      * name ResourceName. In the UNIX versions, this function utilizes the
      * SIGIO signal. If this signal is blocked, this function will not operate
      * correctly.
      *
      * @param ResourceName
      * @return ER_Success ER_Failure ER_ModuleNotResolved ER_UnknownError
      * ER_ModuleNotFound ER_ModuleAccessDenied ER_InsufficientMemory
      * ER_InvalidTagName ER_InvalidParameter
      */
      public static native short StepModule(String ResourceName);


     //Individual DataTag Management Routines

     /**
      * This function creates a DataTag based on the NULL-terminated string
      * pointed to by NewTag and returns a handle for the new DataTag.
      * If the return value is NULL, then a DataTag could not be created.
      * This error indicates that either there is insufficient memory or the
      * text specified is not valid. The text can be tested for validity using
      * the ValidateTagName( ) function (see section 5.9.6).
      *
      * @param NewTag
      * @return Handle Creation successful
      * NULL Creation unsuccessful
      */
      public static native Pointer CreateDataTag(String NewTag);

     /**
      * This function deletes the DataTag specified by TargetTag from the
      * system. All memory associated with the DataTag will be deallocated.
      * After calling this function, do not use the handle for any other
      * operations. DataTags must be deleted to recover allocated memory.
      *
      * A DataTag that was added to a DataTagList using the AddDataTag( )
      * function should be deleted using the DeleteDataTag( ) function only
      * if the DataTag has been removed from the DataTagList using the
      * RemoveDataTag( ) function. See the DataTagList functions
      * (section 5.9.4) for more detail.
      *
      * @param TargetTag
      * @return None
      */
      public static native void DeleteDataTag(Pointer TargetTag);

     /**
      * This function will attempt to read data for the specified TargetTag
      * DataTag. The return value will indicate whether or not the read
      * operation was successful. If the read is successful, the GetData( )
      * function is used to obtain the actual data. In the UNIX versions,
      * this function utilizes the SIGIO signal. If this signal is blocked,
      * this function will not operate correctly.
      *
      * @param TargetTag
      * @return ER_Success ER_UnknownError ER_ModuleNotFound ER_ModuleBusy
      * ER_InsufficientMemory ER_TransmitError ER_SegmentNotFound
      * ER_InvalidRequest ER_NoMessageReceived ER_NoCommBuffersAvailable
      * ER_InvalidParameter
      */
      public static native short ReadTag(Pointer TargetTag);

     /**
      * This function will begin the process of reading data for the specified
      * TargetTag DataTag. This command only begins the process and does not
      * actually perform any reading. It is expected that the user will
      * execute ContinueReadTag() commands until the read process is complete.
      *
      * @param TargetTag
      * @return ER_ProcessPending ER_InvalidParameter ER_NoCommBuffersAvailable
      */
      public static native short ReadTagAsync(Pointer TargetTag);

     /**
      * This function will continue the process of reading data for the
      * specified Target Tag Data Tag. This command functions similar to the
      * ReadTag command except that a ProcessPending can be returned if the
      * data has not yet arrived. The user should continue executing this
      * routine until an ER_ProcessPending is NOT returned. If the time between
      * calls will be greater than a second, the user should execute the
      * DoPolling() command to allow the system to process the communication
      * messages that may have been received by the API.
      *
      * @param TargetTag
      * @return ER_NoMessageReceived ER_TransmitError ER_ModuleBusy
      * ER_ModuleNotFound ER_InvalidParameter ER_SegmentNotFound
      * ER_UnknownError ER_InsufficientMemory ER_ProcessPending
      * ER_InvalidRequest ER_Success
      */
      public static native short ContinueReadTag(Pointer TargetTag);


      //DataTagList Management Routines

     /**
      * This function creates a DataTagList for use in client applications and
      * returns a handle to the new DataTagList. If the return value is NULL,
      * then a DataTagList could not be created. This error will usually
      * indicate insufficient memory. When the list is created, it has no
      * DataTags in it. DataTags are added to the DataTagList by calling the
      * AddDataTag( ) function. Functions using this list will be processed in
      * a synchronous manner (i.e. they will not return until they are
      * complete).
      *
      * @param None
      * @return Handle Creation successful
      * NULL Creation unsuccessful
      */
      public static native Pointer CreateDataTagList();

     /**
      * This function creates a DataTagList for use in client applications
      * when asynchronous processing is desired. It returns a handle to the
      * new DataTagList. If the return value is NULL, then a DataTagList
      * could not be created. This error will usually indicated insufficient
      * memory. When the list is created, it has no DataTags in it. DataTags
      * are added to the DataTagList by calling the AddDataTag() function.
      * When reading data from this DataTagList, the user must first execute
      * a ReadTagList() to begin the read process and then execute
      * ContinueReadTagList() commands until ER_ProcessPending is not returned.
      *
      * @param None
      * @return Handle Creation successful
      * NULL Creation unsuccessful
      */
      public static native Pointer CreateAsyncDataTagList();

     /**
      * This function creates a DataTagList for use in server applications and
      * returns a handle to the new DataTagList. If the return value is NULL,
      * then a DataTagList could not be created. This error will usually
      * indicate insufficient memory. When the list is created, it has no
      * DataTags in it. DataTags are added to the DataTagList by calling the
      * AddServerDataTag( ) function.
      *
      * @return Handle Creation successful
      * NULL Creation unsuccessful
      */
      public static native Pointer CreateServerDataTagList();

     /**
      * This function deletes the TargetList DataTagList from the system.
      * All memory associated with the DataTagList will be deallocated.
      * This routine will also delete any DataTags currently contained in the
      * list. Thus, the user should not use any DataTags contained in the list
      * after the list has been deleted.
      *
      * @return None
      */
      public static native void DeleteDataTagList(Pointer TargetList);

     /**
      * This function adds a DataTag to the specified TargetList client
      * DataTagList. The DataTag will be created in the list using the
      * specified NULL-terminated string pointed to by NewTag. The function
      * returns a handle for the new DataTag. This handle is used to access
      * the DataTag information, such as the data value. The added DataTag
      * will appear in the DataTagList in alphabetical order. If the specified
      * tag already exists in the list, the handle for the existing DataTag
      * will be returned. When this happens, an internal reference count is
      * maintained in the DataTag. Each time the same tag is added to the list,
      * the reference count will be incremented. Further, since there is only
      * a single copy of the tag in the list, there can be only one user ID
      * associated with the tag. In the UNIX versions, this function utilizes
      * the SIGIO signal. If this signal is blocked, this function will not
      * operate correctly.
      *
      * Note that on 16-bit PC platforms there is room only for 8192 tags.
      * Trying to add more than this will result in an error response.
      *
      * @param TargetList
      * @param NewTag
      * @return Handle Creation successful<br>
      * NULL Creation unsuccessful
      */
      public static native Pointer AddDataTag(Pointer TargetList, String NewTag);

     /**
      * This function adds a DataTag of APACS IEC data type NewType (Appendix D)
      * to the specified TargetList server DataTagList. The DataTag will be
      * created in the list using the specified NULLterminated string pointed
      * to by NewTag. Only valid APACS tagname characters can be used in NewTag.
      * The function returns a handle for the new DataTag. This handle is used
      * to access the DataTag information, such as the data value. The added
      * DataTag will appear in the DataTagList in alphabetical order. The
      * DataTag text contained in NewTag consists of all necessary fields,
      * excluding the ResourceName, to describe and/or differentiate the
      * DataTag since all DataTags must be unique across any server DataTagLists
      * in the server application.
      *
      * @param TargetList
      * @param NewTag
      * @param NewType
      * @return Handle Creation successful<br>
      * NULL Creation unsuccessful
      */
      public static native Pointer AddServerDataTag(Pointer TargetList, String NewTag, short NewType);

     /**
      * This function removes the specified TargetTag DataTag from the
      * specified TargetList DataTagList and moves the current pointer to the
      * next DataTag. In client applications, if the specified tag has been
      * added to the list multiple times, removal of the tag is effected by
      * decrementing the reference count.
      * When the reference count reaches zero (0), the tag can be deleted using
      * the DeleteDataTag( ) function. Once a tag has been deleted, all handles
      * to the tag are invalid and should not be used. The return value
      * indicates whether or not the specified tag has been removed.
      * If the tag is not removed, it can mean one of two things. Either the
      * reference count is not zero (0) or the tag does not exist in the list.
      * To determine if a given tag exists in the list, the FindDataTagHandle()
      * function can be used(see section 5.9.5).
      * A DataTag successfully removed from the DataTagList using the
      * RemoveDataTag( ) function should be deleted using the DeleteDataTag( )
      * function. If a DataTag remains in the DataTagList, it will be deleted
      * by the DeleteDataTagList( ) function.
      *
      * @param TargetList
      * @param TargetTag
      * @return ER_Success ER_TagNotRemoved ER_InvalidParameter ER_Failure
      */
      public static native short RemoveDataTag(Pointer TargetList, Pointer TargetTag);

     /**
      * If the DataTagList was created to use asynchronous processing, this
      * function will start the read process and then return immediately.
      * Otherwise, this function reads all of the DataTags in the specified
      * TargetList client DataTagList. The DataTag data values returned are
      * stored and the ChangedList is updated. The return value indicates
      * whether or not the overall read was successful and will indicate
      * success only if all DataTags read are successful. Each individual
      * DataTag status should be determined by using the GetIOStatus( )
      * function on the Input DataSegment. In the UNIX versions, this function
      * utilizes the SIGIO signal. If this signal is blocked, this function
      * will not operate correctly.
      * The first time data is read will take significantly longer then
      * subsequent reads. This is due to the need for the system to resolve all
      * of the tags into handles and create templates. Future reads will make
      * use of existing templates and handles and, thus, will be faster.
      * If DataTags are added to or deleted from the DataTagList, the next
      * read may also be slower.
      *
      *
      * @return ER_Success ER_TimeOutError ER_InsufficientMemory ER_ModuleBusy
      * ER_NoModulesResponded ER_DBChanged ER_NoMessageReceived ER_CheckSumError
      * ER_InvalidParameter ER_ProcessPending ER_UnknownError
      * ER_NoCommBuffersAvailable
      **/
      public static native synchronized short ReadTagList(Pointer TargetList);

     /**
      * This function will continue the process of reading DataTags in the
      * specified TargetList when the list was created to be asynchronous.
      * This command functions similar to the ReadTagList command except that a
      * ProcessPending will be returned if all of the data has not yet arrived.
      * The user should continue executing this routine until an
      * ER_ProcessPending is NOT returned. In between calls, the user should
      * execute the DoPolling() command to allow the system to process the
      * communication messages that may have been received by the API. Note
      * that during the processing to receive data from a TagList, multiple
      * errors may occur, but this command can only return one error for all
      * tags in the list. Thus, only the highest priority error will be returned
      * once the entire list has been attempted to be read. If ER_Success is
      * not returned, the user should use the GetIOStatus() function on the
      * Input Data Segment for each DataTag to determine the Status of each
      * DataTag.
      *
      * @param TargetList
      * @return ER_NoMessageReceived ER_ProcessPending ER_ModuleBusy
      * ER_TimeOutError ER_InvalidParameter ER_Success ER_UnknownError
      * ER_CheckSumError
      */
      public static native short ContinueReadTagList(Pointer TargetList);

     /**
      * This function returns an indication of whether a read of a DataTagList
      * is currently in progress. This function is only applicable when reading
      * DataTagLists that were created in the Asynchronous mode. If the last
      * read for TargetList returned a ProcessPending, this routine will
      * return a TRUE; otherwise, this routine will return a FALSE. Users who
      * create multiple DataTagLists can use this function to determine
      * which lists still need to be read.
      *
      * @param TargetList
      * @return TRUE There is a read in progress
      * FALSE There is NO read in progress
      */
      public static native boolean IsDTLReadInProgress(Pointer TargetList);


      //DataTagList and ChangedList Access Routines

     /**
      * This function resets the main pointer of the specified TargetList
      * DataTagList by moving the pointer to prior to the first DataTag in the
      * DataTagList. This function supports sequential access of the DataTags
      * contained in the list. This function is used in conjunction with the
      * NextTag( ) function to access the DataTags in alphabetical order.
      *
      * @param TargetList
      * @return None
      */
      public static native void ResetList(Pointer TargetList);

     /**
      * This function moves the main pointer to the next DataTag in the
      * specified TargetList DataTagList and returns its handle. This function
      * supports sequential access of the DataTags contained in the DataTagList.
      * If there is no next DataTag in the list, the function returns a NULL
      * handle.
      *
      * @param TargetList
      * @return Handle Next tag found<br>
      * NULL Next tag not found
      */
      public static native Pointer NextTag(Pointer TargetList);

     /**
      * This function returns the handle of the current DataTag in the specified
      * TargetList DataTagList pointed to by the main pointer. If there is no
      * current DataTag, the function returns a NULL handle. A NULL
      * handle means one of three things:
      *
      * 1. A call was made to ResetList( ) causing the current handle to be
      * before the first DataTag in the DataTagList.
      * 2. A call was made to NextTag( ) causing the current handle to be past
      * the last DataTag in the DataTagList.
      * 3. There are no DataTags in the DataTagList.
      *
      * @param TargetList
      * @return Handle Tag found
      * NULL Tag not found
      */
      public static native Pointer CurrentTag(Pointer TargetList);

     /**
      * This function resets the pointer of the ChangedList that belongs to the
      * specified TargetList DataTagList by moving the pointer prior to the
      * first DataTag in the DataTagList ChangedList. It supports sequential
      * access of the DataTags contained in the list whose data values have
      * changed since the previous read. This function is used in conjunction
      * with the NextChangedTag( ) function to access the DataTags.
      *
      * @param TargetList
      * @return None
      */
      public static native void ResetChangedList(Pointer TargetList);

     /**
      * This function moves the changed pointer to the next DataTag in the
      * specified TargetList DataTagList ChangedList and returns its handle.
      * If there is no next DataTag in the ChangedList, the function returns
      * a NULL handle.
      *
      * @param TargetList
      * @return Handle Next tag found
      * NULL Next tag not found
      */
      public static native Pointer NextChangedTag(Pointer TargetList);

     /**
      * This function returns the handle of the current DataTag in the specified
      * TargetList DataTagList ChangedList pointed to by the changed pointer.
      * If there is no current changed DataTag, the function returns a NULL
      * handle. A NULL handle means one of three things:
      *
      * 1. A call was made to ResetChangedList( ) causing the current changed
      * handle to be before the first DataTag in the DataTagList ChangedList.
      * 2. A call was made to NextChangedTag( ) causing the current changed
      * handle to be past the last DataTag in the DataTagList ChangedList.
      * 3. There are no DataTags in the DataTagList ChangedList.
      *
      * @param TargetList
      * @return Handle Next tag found
      * NULL Next tag not found
      */
      public static native Pointer CurrentChangedTag(Pointer TargetList);

     /**
      * This function removes the specified TargetTag DataTag from the specified
      * TargetList DataTagList ChangedList. Note that the DataTag is not removed
      * from the main DataTagList and is not deleted.
      *
      * @param TargetList
      * @param TargetTag
      * @return None
      */
      public static native void RemoveFromChangedList(Pointer TargetList, Pointer TargetTag);

     /**
      * This function will remove all DataTags from the specified TargetList
      * DataTagList ChangedList. Note that these DataTags are not removed from
      * the main DataTagList and are not deleted.
      *
      * @param TargetList
      * @return None
      */
      public static native void PurgeChangedList(Pointer TargetList);

     /**
      * This function will search the specified TargetList DataTagList for a
      * DataTag whose text matches that pointed to by TagText. The routine
      * searches for an exact, case-dependent match. If the specified
      * DataTag does not exist, a NULL handle will be returned.
      *
      * @param TargetList
      * @param TagText
      * @return Handle Data tag found<br>
      * NULL Data tag not found
      */
      public static native Pointer FindDataTagHandle(Pointer TargetList, String TagText);


     //DataTag Common Routines

     /**
      * This function returns an ErrorCode value indicating whether or not the
      * text pointed to by TagText is valid. DataTags in APACS have a specific
      * format as described in section 5.2 of this Guide. Note that this
      * validation text does not indicate that the specified tag exists in the
      * APACS system. Even if the tag text is valid, reads will fail if an
      * actual tag of that name does not exist. To determine whether or not a
      * specific tag exists in the system, a data read must be performed.
      *
      * @param TagText
      * @return ER_Success ER_UnknownError ER_InvalidTagName
      * ER_InsufficientMemory ER_InvalidParameter
      */
      public static native short ValidateTagName(String TagText);

      /**
       * This function returns the length of the TagName contained by the
       * DataTag specified by TargetTag. This length will include all characters
       * including delimiters, but will not include the terminating NULL.
       *
       * @param TargetTag
       * @return Tag length
       */
      public static native short GetTagLength(Pointer TargetTag);

      /**
       * This function returns the text string which represents the TagName
       * contained by TargetTag. A NULLterminated string will be placed in
       * Buffer, whose size is specified by BufferSize. If the text string is
       * inter than BufferSize, the buffer will be NULL-filled. If the text
       * string is longer than BufferSize -1, it will be truncated. The return
       * value indicates the number of characters copied into the buffer
       * (the buffer length), not including the terminating NULL.
       *
       * @param TargetTag
       * @param Buffer
       * @param BufferSize
       * @return Number of characters in buffer
       */
      public static native short GetTagText(Pointer TargetTag, byte[] Buffer, ShortByReference
 BufferSize);

      /**
       * This function allows the user to assign a UserID to the specified
       * TargetTag. A UserID is an identification value specified by the user
       * and pointed to by NewID. It is the size of a pointer on the target
       * platform. If the TargetTag exists multiple times in a DataTagList,
       * the last UserID written is the only user identification value
       * available. One potential use for the UserID is to contain the actual
       * address of the application program variable corresponding to the
       * DataTag. This allows the GetData( ) and PutData( ) functions to be more
       * generic in the application program when used with the NextTag( ) or
       * NextChangedTag( ) functions for DataTagLists.
       *
       * @param TargetTag
       * @param NewID
       * @return None
       */
      public static native void PutUserID(Pointer TargetTag, NativeLongByReference NewID);

      /**
       * This function returns the UserID value for the specified TargetTag.
       *
       * @param TargetTag
       * @return User identification value
       */
      public static native NativeLong GetUserID(Pointer TargetTag);


      //DataTag DataValue Get Routines

      /**
       * This function returns the DataStatusCode for the specified
       * TargetSegment DataSegment of the specified TargetTag DataTag from the
       * most recent read (or write) for client applications or the most recent
       * write (by a client) for server applications. The possible
       * DataStatusCode values are listed in Appendix B.
       *
       * Until a Read...( ) function or a PutData( ) function is used, the
       * specified TargetSegment DataSegment will return the DATA_UNINITIALIZED
       * DataStatusCode. The PutData( ) function automatically sets the
       * specified TargetSegment DataSegment to the NO_IO_ERROR DataStatusCode.
       *
       * @param TargetTag
       * @param TargetSegment
       * @return DataStatusCode value
       */
      public static native int GetIOStatus(Pointer TargetTag, int TargetSegment);

      /**
       * This function returns the DataQualityCode for the specified
       * TargetSegment DataSegment of the specified TargetTag DataTag and is
       * dependent upon the I/O status, which should be checked first. The
       * DataQualityCode values are listed in Appendix C.
       *
       * @param TargetTag
       * @param TargetSegment
       * @return DataQualityCode value
       */
      public static native int GetDataQuality(Pointer TargetTag, int TargetSegment);

      /**
       * This function returns the DataTypeCode of the specified TargetTag
       * DataTag. The DataTypeCode values associated with the APACS IEC data
       * types are listed in Appendix D.
       *
       * @param TargetTag
       * @return DataTypeCode value
       */
      public static native int GetDataType(Pointer TargetTag);

      /**
       * This function returns the data value from the specified TargetSegment
       * DataSegment of the specified TargetTag DataTag into the application
       * program variable Value whose data type is specified by ValueTypeCode.
       * The possible ValueTypeCode values are listed in Appendix E. This
       * function will work, in most cases, regardless of the DataType of the
       * TargetTag DataTag. The matrix of valid ValueTypeCodes versus
       * DataTypeCodes is given in Appendix F. If the actual DataType of the
       * DataTag does not match the requested type, a type conversion will be
       * performed. The function returns an ErrorCode indicating the success
       * or failure of the conversion.
       *
       * If Value is a String data type, a NULL-terminated string will be placed
       * in the variable, whose size is specified by BufferSize. If the text
       * string is inter than BufferSize, the variable will be NULL-filled.
       * If the resultant string is longer than BufferSize - 1, the string will
       * be truncated. The number of characters, not including the terminating
       * NULL, which have been placed in the variable will be returned in
       * BufferLength.
       *
       * This function can be used to return APACS DATE...- and TIME...-type
       * variables in either value or string form. If the string form is used,
       * the variable size should be large enough to ensure that all possible
       * DATE... and TIME... formats can be accommodated, including the
       * terminating NULL.
       *
       * In server applications, this function is used to obtain the data
       * (from the Input DataSegment) that has been written by client
       * applications using the WriteTag( ) function to change values in the
       * server. If the data is acceptable, the server application normally
       * uses the PutData( ) function to the Output DataSegment to update the
       * data to the system. This function can also be used by server
       * applications to verify the current output data value by performing
       * the GetData( ) function on the Output DataSegment.
       *
       * Appendix G contains a table listing various programming language data
       * types equivalent to the ValueTypeCode names.
       *
       * @param TargetTag
       * @param TargetSegment
       * @param Value
       * @param ValueTypeCode
       * @param BufferSize
       * @param BufferLength
       * @return ER_Success ER_InvalidConversion ER_SegmentNotFound
       * ER_InvalidParameter
       */
      public static native synchronized short GetData(Pointer TargetTag, int TargetSegment,
              Memory Value, short ValueTypeCode, short BufferSize, ShortByReference BufferLength);

      /**
       * This function returns an indication of whether or not the specified
       * TargetSegment DataSegment of the specified TargetTag DataTag is
       * enabled. Variables and Function Block Outputs can be disabled. When
       * disabled, a control module normally cannot or a server application
       * normally should not update the values as part of the normal program
       * execution, thereby allowing the values to be manually manipulated by a
       * client application using the PutData( ) and WriteTag( ) functions.
       *
       * In server applications, this function can be used (to the Input
       * DataSegment) to determine if a client has issued the EnableTag( ) or
       * DisableTag( ) functions.
       *
       * @param TargetTag
       * @param TargetSegment
       * @return TRUE Data value enabled
       * FALSE data value disabled
       */
      public static native boolean IsEnabled(Pointer TargetTag, int TargetSegment);

      /**
       * This function returns an indication of whether or not the specified
       * TargetTag DataTag is write protected. If it is write protected, its
       * value cannot be changed by a client application. Server applications
       * use the SetWriteProtect( ) function to set the appropriate state and
       * can use this function to verify the condition.
       *
       * @param TargetTag
       * @return TRUE Data value write protected
       * FALSE Data value not write protected
       */
      public static native boolean IsWriteProtected(Pointer TargetTag);

      /**
       * This function returns an indication of whether or not the specified
       * TargetTag DataTag is an array variable.
       *
       * @param TargetTag
       * @return TRUE Array variable
       * FALSE Not array variable
       */
      public static native boolean IsArray(Pointer TargetTag);

      /**
       * This function returns the number of dimensions of the specified
       * TargetTag DataTag, if it is an array variable.
       *
       * @param TargetTag
       * @return Number of dimensions
       */
      public static native short GetNumDimensions(Pointer TargetTag);

      /**
       * This function returns the size of the specified DimensionNumber,
       * relative of the specified TargetTag DataTag, if it is an array
       * variable.
       *
       * @param TargetTag
       * @param DimensionNumber
       * @return Size of dimension
       */
      public static native long GetSizeOfDimension(Pointer TargetTag, short DimensionNumber);

      /**
       * This function returns the lower bound of the specified DimensionNumber,
       * relative of the specified TargetTag DataTag, if it is an array variable.
       *
       * @param TargetTag
       * @param DimensionNumber
       * @return Dimension lower bound
       */
      public static native short GetDimLowerBound(Pointer TargetTag, short DimensionNumber);

      /**
       * This function returns the upper bound of the specified DimensionNumber,
       * relative of the specified TargetTag DataTag, if it is an array
       * variable.
       *
       * @param TargetTag
       * @param DimensionNumber
       * @return Dimension upper bound
       */
      public static native short GetDimUpperBound(Pointer TargetTag, short DimensionNumber);

      /**
       * One of the functions from this set is used to calculate an index into
       * the specified TargetTag DataTag, if it is an array variable, based on
       * the number of dimensions of TargetTag DataTag and the specified
       * ArrayElement1 through ArrayElement8 values, as appropriate. The
       * returned calculated index value is used by the array Get... or
       * Put... functions.
       *
       * Return: Calculated index
       */
      public static native long CalculateIndex1(Pointer TargetTag, short ArrayElement1);

      public static native long CalculateIndex2(Pointer TargetTag, short ArrayElement1,
              short ArrayElement2);

      public static native long CalculateIndex3(Pointer TargetTag, short ArrayElement1,
              short ArrayElement2, short ArrayElement3);

      public static native long CalculateIndex4(Pointer TargetTag, short ArrayElement1,
              short ArrayElement2, short ArrayElement3, short ArrayElement4);

      public static native long CalculateIndex5(Pointer TargetTag, short ArrayElement1,
              short ArrayElement2, short ArrayElement3, short ArrayElement4,
              short ArrayElement5);

      public static native long CalculateIndex6(Pointer TargetTag, short ArrayElement1,
              short ArrayElement2, short ArrayElement3, short ArrayElement4,
              short ArrayElement5, short ArrayElement6);

      public static native long CalculateIndex7(Pointer TargetTag, short ArrayElement1,
              short ArrayElement2, short ArrayElement3, short ArrayElement4,
              short ArrayElement5, short ArrayElement6, short ArrayElement7);

      public static native long CalculateIndex8(Pointer TargetTag, short ArrayElement1,
              short ArrayElement2, short ArrayElement3, short ArrayElement4,
              short ArrayElement5, short ArrayElement6, short ArrayElement7,
              short ArrayElement8);

      /**
       * This function returns the DataQualityCode for the specified
       * TargetSegment DataSegment of the element specified by the
       * CalculatedIndex of the specified TargetTag DataTag, if it is an array
       * variable, and is dependent upon the I/O status, which should be checked
       * first. The DataQualityCode values are listed in Appendix C.
       *
       * @param TargetTag
       * @param DimensionNumber
       * @param TargetSegment
       * @return DataQualityCode value
       */
      public static native int GetOneDataQuality(Pointer TargetTag, long CalculatedIndex,
              int TargetSegment);

      /**
       * This function returns the DataTypeCode of the elements of the specified
       * TargetTag array DataTag. The DataTypeCode values associated with the
       * APACS IEC data types are listed in Appendix D.
       *
       * @param TargetTag
       * @return DataTypeCode value
       */
      public static native int GetElementDataType(Pointer TargetTag);

      /**
       * This function returns the data value from the specified TargetSegment
       * DataSegment of the element specified by the CalculatedIndex of the
       * specified TargetTag DataTag, if it is an array variable, into the
       * application program variable Value whose data type is specified by
       * ValueTypeCode. The possible ValueTypeCode values are listed in
       * Appendix E. This function will work, in most cases, regardless of
       * the DataType of the TargetTag DataTag. The matrix of valid
       * ValueTypeCodes versus DataTypeCodes is given in Appendix F.
       * If the actual DataType of the DataTag does not match the requested
       * type, a type conversion will be performed. The function returns an
       * ErrorCode indicating the success or failure of the conversion.
       *
       * See the GetData( ) function (section 5.9.7) for additional information.
       *
       * @param TargetTag
       * @param CalculatedIndex
       * @param TargetSegment
       * @param Value
       * @param ValueTypeCode
       * @param BufferSize
       * @param BufferLength
       * @return ER_Success ER_InvalidConversion ER_SegmentNotFound
       * ER_InvalidParameter
       */
      public static native short GetOneValue(Pointer TargetTag, long CalculatedIndex,
              int TargetSegment,Pointer Value, short ValueTypeCode,
              short BufferSize, short BufferLength);

      /**
       * This function returns the data value from the specified TargetSegment
       * DataSegment of the starting at the element specified by
       * CalculatedIndex1 and ending with the element specified by
       * CalculatedIndex2 of the specified TargetTag DataTag, if it is an array
       * variable, into the application program variable ValueArray
       * whose data type is specified by ValueTypeCode. The possible
       * ValueTypeCode values are listed in Appendix E. This function will work,
       * in most cases, regardless of the DataType of the TargetTag
       * DataTag. The matrix of valid ValueTypeCodes vs DataTypeCodes is given
       * in Appendix F. If the actual DataType of the DataTag does not match
       * the requested type, a type conversion will be performed. The
       * function returns an ErrorCode indicating the success or failure of the
       * conversion.
       *
       * The ElementSize is required for String variables to indicate the
       * maximum length of the string per array element.
       *
       * See the GetData( ) function (section 5.9.7) for additional information.
       *
       * @param TargetTag
       * @param CalculatedIndex1
       * @param CalculatedIndex2
       * @param TargetSegment
       * @param ValueArray
       * @param ValueTypeCode
       * @param ElementSize
       * @return ER_Success ER_InvalidConversion ER_SegmentNotFound
       * ER_InvalidParameter
       */
      public static native short GetArrayValues(Pointer TargetTag, long CalculatedIndex1,
              long CalculatedIndex2, int TargetSegment,Pointer ValueArray, short ValueTypeCode,
              short ElementSize);


      //DataTag DataValue Put Routines

      /**
       * This function is used by server applications to set the DataQuality
       * for the specified TargetSegment DataSegment of the specified TargetTag
       * DataTag to the DataQualityCode contained in NewQuality based upon the
       * server application’s determination of the quality of the data.
       * The DataQualityCodes are listed in Appendix C.
       *
       * @param TargetTag
       * @param TargetSegment
       * @param NewQuality
       * @return ER_Success ER_InvalidParameter ER_Failure
       */
      public static native short PutDataQuality(Pointer TargetTag, int TargetSegment,
              int NewQuality);

      /**
       * This function allows the user to output data to the TargetSegment
       * DataSegment for the specified TargetTag DataTag. The data to be output
       * is contained in the variable pointed to by NewValue, whose
       * data type is specified by ValueTypeCode. The ErrorCode returned
       * indicates whether or not the output was successful.
       *
       * If the actual data type of the TargetTag is different than that
       * specified by ValueTypeCode, the function will attempt to convert
       * NewValue to that of the TargetTag. The matrix of valid ValueTypeCodes
       * versus DataTypeCodes is given in Appendix F.
       *
       * If the data type is String, it allows the data to be output in the
       * form of a NULL-terminated string. Based upon the TargetTag’s actual
       * data type, the string will be parsed into a value. The string form
       * can also be used to write to APACS DATE...- and TIME...-type variables.
       *
       * A table listing various programming language data types equivalent to
       * the ValueTypeCode names is given in Appendix G.
       *
       * In client applications, the PutData( ) function (specifying the Output
       * DataSegment) and the WriteTag() function must be used to actually
       * write the data to the variable.
       *
       * In server applications, once the PutData( ) function (specifying the
       * Output DataSegment) has been called, the data is then available to the
       * system. Server applications should normally use the PutData( ) function
       * twice, once specifying the Input DataSegment and again specifying the
       * Output DataSegment. This sets the Input DataSegment data value to be
       * the same as that of the Output DataSegment data value and allows the
       * server application to properly detect and process a changed value
       * from client applications.
       *
       * @param TargetTag
       * @param TargetSegment
       * @param NewValue
       * @param ValueTypeCode
       * @return ER_Success ER_Failure ER_InsufficientMemory ER_UnknownError
       * ER_SegmentNotFound ER_InvalidConversion ER_SegmentDisabled
       * ER_InvalidDataFormat ER_InvalidParameter ER_InvalidDataTimeFormat
       */
      public static native short PutData(Pointer TargetTag, int TargetSegment,
              Memory NewValue, int ValueTypeCode);

      /**
       * This function is used by server applications to set the Enable flag of
       * the TargetSegment DataSegment (normally the Output DataSegment) to the
       * specified state. This function is needed by server applications
       * in response to a client application wishing to force a normally
       * server-generated data value. In the UNIX versions, this function
       * utilizes the SIGIO signal. If this signal is blocked, this function
       * will not operate correctly.
       *
       * An Enabled DataTag (State = TRUE) normally means that the server
       * application can update the data automatically based on its calculated
       * value. A Disabled DataTag (State = FALSE) is normally forced by
       * the client application to some value, which the server application
       * still must output for the value to be seen by the system. Once the
       * DataTag has been disabled and the server application has detected the
       * changed value, it must first use the SetEnable( ) function
       * (specifying TRUE) to enable the DataTag. This allows the server to
       * update the value to the system. Then, the PutData( ) function
       * (specifying the Output DataSegment) is used to actually update the
       * value. Finally, the SetEnable( ) function is used again (specifying
       * FALSE) to return the DataTag to the disabled state.
       *
       * @param TargetTag
       * @param State
       * @param TargetSegment
       * @return ER_Success ER_InvalidParameter ER_Failure
       */
      public static native short SetEnable(Pointer TargetTag, boolean State,
              int TargetSegment);

      /**
       * This function is used by server applications to set the WriteProtect
       * flag of the specified TargetTag DataTag to the specified State.
       * A DataTag whose WriteProtect State is TRUE prevents client
       * applications from writing data values to the DataTag. In the UNIX
       * versions, this function utilizes the SIGIO signal. If this signal
       * is blocked, this function will not operate correctly.
       *
       * @param TargetTag
       * @param State
       * @return ER_Success ER_InvalidParameter ER_Failure
       */
      public static native short SetWriteProtect(Pointer TargetTag, boolean State);

      /**
       * This function is used by server applications to set the DataQuality
       * for the specified TargetSegment DataSegment of the element specified
       * by the CalculatedIndex of the specified TargetTag DataTag to the
       * DataQualityCode contained in NewQuality based upon the server
       * application’s determination of the quality of the data. The
       * DataQualityCodes are listed in Appendix C.
       *
       * @param TargetTag
       * @param CalculatedIndex
       * @param TargetSegment
       * @param NewQuality
       * @return ER_Success ER_InvalidParameter ER_Failure
       */
      public static native short PutOneDataQuality(Pointer TargetTag, long CalculatedIndex,
              int TargetSegment, int NewQuality);

      /**
       * This function allows the user to output data to the TargetSegment
       * DataSegment of the element specified by the CalculatedIndex for the
       * specified TargetTag DataTag. The data to be output is contained in the
       * variable pointed to by NewValue whose data type is specified by
       * ValueTypeCode. The ErrorCode returned indicates whether or not the
       * output was successful. See the PutData( ) function for additional
       * information.
       *
       * @param TargetTag
       * @param CalculatedIndex
       * @param TargetSegment
       * @param NewValue
       * @param ValueTypeCode
       * @return ER_Success ER_UnknownError ER_InsufficientMemory ER_NotArray
       * ER_SegmentNotFound ER_InvalidDateFormat ER_SegmentDisabled
       * ER_InvalidDateTimeFormat ER_InvalidParameter ER_InvalidConversion
       * ER_Failure
       */
      public static native short PutOneValue(Pointer TargetTag, long CalculatedIndex,
              int TargetSegment, Pointer NewValue, short ValueTypeCode);

      /**
       * This function allows the user to output data to the TargetSegment
       * DataSegment starting at the element specified by CalculatedIndex1 and
       * ending with the element specified by CalculatedIndex2 for the
       * specified TargetTag DataTag. The data to be output is contained in the
       * array variable pointed to by NewValue whose data type is specified by
       * ValueTypeCode. The ErrorCode returned indicates whether or not the
       * output was successful.
       *
       * See the PutData( ) function for additional information.
       *
       * @param TargetTag
       * @param CalculatedIndex1
       * @param CalculatedIndex2
       * @param TargetSegment
       * @param NewValue
       * @param ValueTypeCode
       * @return ER_Success ER_UnknownError ER_InsufficientMemory ER_NotArray
       * ER_SegmentNotFound ER_InvalidDateFormat ER_SegmentDisabled
       * ER_InvalidDateTimeFormat ER_InvalidParameter ER_InvalidConversion
       * ER_Failure
       */
      public static native short PutArrayValues(Pointer TargetTag, long CalculatedIndex1,
              long CalculatedIndex2, int TargetSegment, Pointer NewValue,
              short ValueTypeCode);


      //DataTag Write Routines

      /**
       * This function is used by client applications to write data to the
       * specified TargetTag DataTag. The data to be written was previously
       * output using the PutData( ) function (specifying the Output
       * DataSegment). The ErrorCode returned indicates whether or not the
       * write was successful. This function will not return until the write
       * is complete. In the UNIX versions, this function utilizes the SIGIO
       * signal. If this signal is blocked, this function will not operate
       * correctly.
       *
       * @param TargetTag
       * @return ER_Success ER_SecurityViolation ER_ModuleNotFound
       * ER_InvalidParameter ER_InsufficientMemory ER_UnknownError
       * ER_TransmitError ER_ModuleBusy ER_SegmentNotFound ER_DataTypeMismatch
       * ER_NoMessageReceived ER_NoCommBuffersAvailable ER_InvalidRequest
       */
      public static native short WriteTag(Pointer TargetTag);

      /**
       * This function is used to begin the process of writing data to the
       * specified Target Tag Data Tag. This command only begins the process
       * and does not actually perform any writing. It is expected that the
       * user will execute ContinueWriteTag() commands until the write process
       * is complete.
       *
       * @param TargetTag
       * @return ER_ProcessPending ER_InvalidParameter ER_InvalidRequest
       * ER_NoCommBuffersAvailable
       */
      public static native short WriteTagAsync(Pointer TargetTag);

      /**
       * This function will continue the process of writing data to the
       * specified Target Tag Data Tag. This command functions similar to
       * the WriteTag command except that a ProcessPending can be returned
       * if the response to the write request has not yet arrived. The user
       * should continue executing this routine until an ER_ProcessPending is
       * NOT returned. In between calls, the user should execute the DoPolling()
       * command to allow the system to process the communication messages that
       * may have been received by the API.
       *
       * @param TargetTag
       * @return ER_NoMessageReceived ER_ModuleNotFound ER_ModuleBusy
       * ER_SegmentNotFound ER_InvalidParameter ER_InsufficientMemory
       * ER_UnknownError ER_InvalidRequest ER_ProcessPending
       * ER_SecurityViolation ER_Success ER_DataTypeMismatch ER_TransmitError
       */
      public static native short ContinueWriteTag(Pointer TargetTag);

      /**
       * This function is used by client applications to write the specified
       * State to the specified BitPosition [least significant
       * bit = BitPosition 0 (zero)] of the specified TargetTag DataTag.
       * The ErrorCode returned indicates whether or not the write was
       * successful. In the UNIX versions, this function utilizes the SIGIO
       * signal. If this signal is blocked, this function will not operate
       * correctly.
       *
       * @param TargetTag
       * @param BitPosition
       * @param State
       * @return ER_Success ER_SecurityViolation ER_ModuleNotFound
       * ER_InvalidParameter ER_InsufficientMemory ER_UnknownError
       * ER_TransmitError ER_SegmentDisabled ER_SegmentNotFound ER_InvalidBit
       * ER_NoMessageReceived ER_DataTypeMismatch ER_InvalidRequest
       * ER_NoCommBuffersAvailable
       */
      public static native short WriteBit(Pointer TargetTag, short BitPosition,
              boolean State);

      /**
       * This function is used to begin the process of writing the specified
       * State to the specified BitPosition of the specified TargetTag DataTag.
       * This command only begins the process and does not actually perform any
       * writing. It is expected that the user will execute ContinueWriteBit()
       * commands (discussed next) until the process is complete.
       *
       * @param TargetTag
       * @param BitPosition
       * @param State
       * @return ER_ProcessPending ER_InvalidParameter ER_InvalidRequest
       * ER_UnknownError ER_SegmentDisabled ER_NoCommBuffersAvailable
       * ER_InvalidBit
       */
      public static native short WriteBitAsync(Pointer TargetTag, short BitPosition,
              boolean State);

      /**
       * This function is used to continue the process of writing the specified
       * State to the specified BitPosition of the specified TargetTag DataTag.
       * This command functions similar to the WriteBit command except that
       * a ProcessPending can be returned if the response to the request has
       * not yet arrived. The user should continue executing this routine until
       * an ER_ProcessPending is NOT returned. In between calls, the user
       * should execute the DoPolling() command to allow the system to process
       * the communication messages that may have been received by the API.
       *
       * @param TargetTag
       * @param BitPosition
       * @param State
       * @return ER_NoMessageReceived ER_TransmitError ER_ModuleBusy
       * ER_ModuleNotFound ER_InvalidParameter ER_SegmentNotFound
       * ER_UnknownError ER_InvalidRequest ER_ProcessPending
       * ER_SecurityViolation ER_Success ER_DataTypeMismatch
       */
      public static native short ContinueWriteBit(Pointer TargetTag, short BitPosition,
              boolean State);

      /**
       * This function is used by client applications to set the Enable flag of
       * the specified TargetTag DataTag to the TRUE state. An Enabled DataTag
       * normally means that the server application can update the data
       * automatically based on its calculated value. In the UNIX versions,
       * this function utilizes the SIGIO signal. If this signal is blocked,
       * this function will not operate correctly.
       *
       * @param TargetTag
       * @return ER_Success ER_UnknownError ER_ModuleNotFound ER_InvalidRequest
       * ER_InsufficientMemory ER_ModuleBusy ER_TransmitError
       * ER_SecutityViolation ER_NoMessageReceived ER_SegmentNotFound
       * ER_InvalidParameter ER_NoCommBuffersAvailable
       */
      public static native short EnableTag(Pointer TargetTag);

      /**
       * This function is used to begin the process of setting the enable flag
       * of the specified Target Tag Data Tag to the TRUE state. This command
       * only begins the process and does not actually complete any
       * communication. It is expected that the user will execute
       * ContinueEnableTag() commands (discussed below) until the process is
       * complete.
       *
       * @param TargetTag
       * @return ER_ProcessPending ER_InvalidParameter ER_NoCommBuffersAvailable
       */
      public static native short EnableTagAsync(Pointer TargetTag);

      /**
       * This function will continue the process of setting the enable flag of
       * the specified Target Tag Data Tag to the TRUE state. This command
       * functions similar to the EnableTag command (discussed above) except
       * that a ProcessPending can be returned if the response to the request
       * has not yet arrived. The user should continue executing this routine
       * until an ER_ProcessPending is NOT returned. In between calls, the user
       * should execute the DoPolling() command to allow the system to process
       * the communication messages that may have been received by the API.
       *
       * @param TargetTag
       * @return ER_NoMessageReceived ER_TransmitError ER_ModuleBusy
       * ER_ModuleNotFound ER_InvalidParameter ER_SegmentNotFound
       * ER_UnknownError ER_InsufficientMemory ER_ProcessPending
       * ER_InvalidRequest ER_Success ER_SecurityViolation
       */
      public static native short ContinueEnableTag(Pointer TargetTag);

      /**
       * This function is used by client applications to set the Enable flag of
       * the specified TargetTag DataTag to the FALSE state. A Disabled DataTag
       * normally means that the server application should not update the
       * data automatically based on its calculated value, since the client
       * application may want to force the data to some other value.
       * In the UNIX versions, this function utilizes the SIGIO signal. If this
       * signal is blocked, this function will not operate correctly.
       *
       * @param TargetTag
       * @return ER_Success ER_UnknownError ER_ModuleNotFound ER_InvalidRequest
       * ER_InsufficientMemory ER_ModuleBusy ER_TransmitError
       * ER_SecurityViolation ER_NoMessageReceived ER_SegmentNotFound
       * ER_InvalidParameter ER_NoCommBuffersAvailable
       */
      public static native short DisableTag(Pointer TargetTag);

      /**
       * This function is used to begin the process of setting the enable flag
       * of the specified Target Tag Data Tag to the FALSE state. This command
       * only begins the process and does not actually complete any
       * communication. It is expected that the user will execute
       * ContinueDisableTag() commands (discussed next) until the process is
       * complete.
       *
       * @param TargetTag
       * @return ER_ProcessPending ER_InvalidParameter ER_NoCommBuffersAvailable
       */
      public static native short DisableTagAsync(Pointer TargetTag);

      /**
       * This function will continue the process of setting the enable flag of
       * the specified Target Tag Data Tag to the FALSE state. This command
       * functions similar to the DisableTag command except that a
       * ProcessPending can be returned if the response to the request has not
       * yet arrived. The user should continue executing this routine until
       * an ER_ProcessPending is NOT returned. In between calls, the user
       * should execute the DoPolling() command to allow the system to process
       * the communication messages that may have been received by the API.
       *
       * @param TargetTag
       * @return ER_NoMessageReceived ER_TransmitError ER_ModuleBusy
       * ER_ModuleNotFound ER_InvalidParameter ER_SegmentNotFound
       * ER_UnknownError ER_InsufficientMemory ER_ProcessPending
       * ER_InvalidRequest ER_Success ER_SecurityViolation
       */
      public static native short ContinueDisableTag(Pointer TargetTag);



    static {
       Native.register("c:\\windows\\system32\\APXAPIWX.DLL");
    }
    
}
