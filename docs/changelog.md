# Change Log
All notable changes, such as SDK releases, updates and fixes, are documented in this file.

## Version 1.3.1.230925144644
+ Fix some bugs.
+ Improve face detection capabilities
+ Add new feature for doc detection by NFC

## Version 1.3.0.230907171559
+ Fix some bugs.

**Notice:**

If other SDKs are integrated at the same time, and you run into issue "More than one file was found with OS independent path 'lib/arm64-v8a/libc++_shared.so'".
The main reason is that the ZOLOZ SDK and other SDKs add the libc++_shared.so library. The solution is to add the following configuration in the build.gradle.

```groovy
  packagingOptions {
    pickFirst 'lib/arm64-v8a/libc++_shared.so'
    pickFirst 'lib/armeabi-v7a/libc++_shared.so'
  }
```

## Version 1.2.17.230721110219
+ Fix some bugs.

## Version 1.2.16.230625174717
+ Fix some bugs.

## Version 1.2.15.230522102749
+ Fix some bugs.

## Version 1.2.14.230511093806
+ Fix some bugs.

## Version 1.2.13.230404102843
+ Fix some bugs.

## Version 1.2.12.230322103026
+ Fix some bugs.

## Version 1.2.11.230224141004
+ Add text to the face capture interface as it supports displaying multiple paragraphs of custom content.
+ Fix some bugs.

## Version 1.2.10.230112141843
+ Fix face retry data issues

## Version 1.2.9.221014103813
+ Fix some bugs.

## Version 1.2.9.221008144313
+ Fix some bugs.
+ Improve ui config capabilities

## Version 1.2.8.220817145238
+ Accessibility Support

## Version 1.2.7.220715155219
+ Improve face detection capabilities


## Version 1.2.6.220705114203
+ Fix device id issues

## Version 1.2.6.220616153250
+ Improve face detection capabilities


## Version 1.2.5.220513101757
+ Improve face detection capabilities

## Version 1.2.4.220419103223
+ Improve face detection capabilities

## Version 1.2.3.220328142726

+ Fix compatibility issues on some devices

## Version 1.2.3.220215114217

+ Improve face detection capabilities

## Version 1.2.2

+ fix the conflict class for MPaaS.

## Version 1.2.1

+ Improve flash detection capabilities
+ Improve camera focus capabilities
+ Improve the reverse side of documents detection capabilities
+ Improve occlusion detection capabilities

## Version 1.2.0

+ Support multi face detection
+ Improve occlusion detection capabilities

## Version 1.1.0

+ Improve SDK security.
+ Fixed the camera focus issue on old Samsung devices.

## Version 1.0.3

+ Fixed webview config.

## Version 1.0.2

+ Fixed UI Configuration bugs.

## Version 1.0.0

+ First release ZOLOZ sdk for RealId/Connect product.