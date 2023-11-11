package com.tacomeal.taco.data

import android.content.SharedPreferences
import com.tacomeal.taco.Application
import com.google.gson.JsonSyntaxException
import java.sql.Time

class SharedPref{
    companion object {
        private const val keySkipOnboarding = "skip_onboarding"

        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var editor : SharedPreferences.Editor
        private const val current_account = "current_account"
        private const val file_path = "file_path"

        @Volatile private var instance: SharedPref? = null
        private val lock = Any()

        operator fun invoke() : SharedPref = instance
            ?: synchronized(lock) {
            instance
                ?: makeCustomSharedPreferences().also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences() : SharedPref {
            SharedPref.sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(
                Application.appContext)
            editor = androidx.preference.PreferenceManager.getDefaultSharedPreferences(
                Application.appContext).edit()
            return SharedPref()
        }
    }

    fun setSkipOnboarding(){
        editor.putBoolean(keySkipOnboarding, true)
        SharedPref.editor.commit()
    }


    fun getSkipOnboarding() : Boolean{
        return SharedPref.Companion.sharedPreferences.getBoolean(keySkipOnboarding, false)
    }

    /*fun currentUserId(userSession: UserSession?) {
        editor.putString(user_session, newInstance().toJson(userSession))
        editor.commit()
        userSession?.userUID?.let {
            userSession.userName?.let {
                    it1 -> saveUsersUsername(it, it1)
            }
        }
    }

    val userSession: UserSession?
        get() = try {
            val userSession: UserSession
            val jsonUserSession = sharedPreferences.getString(user_session, "")
            if(jsonUserSession.isNullOrEmpty()||jsonUserSession=="null")
                null
            else{
                userSession = newInstance().fromJson(jsonUserSession, UserSession::class.java)
                userSession
            }
        } catch (e: JsonSyntaxException) {
            null
        }*/


}
