
Java for Apacs+/Quadlog

This project allows the use of the Apacs + / Quadlog communication APIs through an application developed in Java.

To run the application you must have "APPLICATION PROGRAMMING INTERFACE (API) TOOLKIT VERSION 4.00 OR HIGHER", part number PN15939-617V4.xx

The migration of the supplied headers for the C language and its interface with the native libraries is done using the JNA project as shown in the source code.

Although the Apacs + / Quadlogs have reached the end of their useful life, by this year Siemens must close the Apacs 2020 project.

Given the quality and robustness of this equipment, there are surely many in operation around the world.

From the diagram shown we can point out how the use of these libraries is integrated into the control architecture.

For example, if the control architecture already includes the migration process to PCS7, the EMI [2] must be incorporated, allowing the connection to the controllers via Ethernet. If the EMI exist, all communications are routed through these computers.


If you do not have the EMI, in addition to the Toolkit, you must have a Part # 16413-51 PCI-Type M-BUS Interface (MBI-PCI) Card on your PC. In this way, the Gateway indicated in the diagram would be implemented.

In either case, APIs can communicate through an existing RNI.

With any of those options, the APP developed in Java will be able to access Apacs in the field.

In the near future I hope to integrate these libraries in the Apache PLC4X project. This would greatly simplify the use of the library.

Almost every aspect of the driver is included in the test sections, from live list, tag read and write, and PC status monitoring.

Remember to test safely, both for people and for the process.

Have fun


1. https://mall.industry.siemens.com/mall/en/WW/Catalog/Products/10032040?activeTab=ProductInformation#
2. https://mall.industry.siemens.com/mall/en/WW/Catalog/Products/10016181#
