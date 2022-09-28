package com.wen.googlesignin_lib

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.unity3d.player.UnityPlayer


class MainActivity : UnityPlayerActivity() {

    var tag:String = "ShowLog"

    // Google Sign
    val RC_SIGN_IN = 1
    val web_client_id = " Your Client_id "
    var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Toast.makeText(this, "OnCreate!", Toast.LENGTH_SHORT).show()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(web_client_id)
            .requestServerAuthCode(web_client_id)
            .requestProfile()
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

        // Check User has already signed
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if(account!=null) {
            signIn()
        }
    }

    fun signIn() {
        val intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    fun signOut() {
        mGoogleSignInClient!!.signOut()
        Toast.makeText(this, "已登出!", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)

                if (account != null) {
                    Log.d(tag,account.displayName + "," + account.email)
                    receiveStr(account.displayName + "\n" + account.email + "\n" + account.serverAuthCode + "\n" + account.idToken)

                    Toast.makeText(this, "已登入!", Toast.LENGTH_SHORT).show()
                };
            } catch (e: ApiException) {
                     Log.d(tag,"failed code=" + e.statusCode)
                     receiveStr("failed code=" + e.statusCode.toString())
            }
        }
    }

    // Unity Receive
    fun receiveStr(str: String?) {
        UnityPlayer.UnitySendMessage("Canvas","GetInfo",str);
    }
}