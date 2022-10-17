# GoogleSingnIn

GoogleSingnIn is a library for Unity

## Usage

```
1. Add UnityPlayerActivity.java
2. Add Unity classes.jar to /libs and as a Library
3. AndroidManifest.xml add `<uses-permission android:name="android.permission.INTERNET" />

4. build.gradle Add :
   implementation 'com.google.android.gms:play-services-games:23.0.0'
   implementation 'com.google.android.gms:play-services-auth:20.3.0'

5. web_client_id Add GoogleCloud `Client ID`

Finally build .aar for Unity 

```
