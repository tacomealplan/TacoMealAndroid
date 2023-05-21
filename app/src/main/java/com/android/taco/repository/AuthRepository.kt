package com.android.taco.repository

import com.android.taco.model.*
import com.android.taco.remote.Api
import com.android.taco.util.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ActivityScoped
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend fun logIn(email : String, password : String) = flow {
        try {
            auth.let{ login->
                login.signInWithEmailAndPassword(email,password).await()
                emit(Resource.Success(true))
                    /*.addOnCompleteListener {task: Task<AuthResult> ->
                        if(!task.isSuccessful){
                            println("Login Failed with ${task.exception}")
                            emit(Resource.Success(auth.currentUser))
                        }else{
                            emit(Error(e.message ?: "ERROR_MESSAGE"))
                        }
                    }*/
            }
        }catch (e : Exception){
            emit(Error(e.message ?: "ERROR_MESSAGE"))
        }
    }

    suspend fun firebaseSignInAnonymously() = flow {
        try {
            auth.signInAnonymously().await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Error(e.message ?: "ERROR_MESSAGE"))
        }
    }

    /*suspend fun makeRequest(id: String): Resource<CommonResponse> {
        val response = try {
            val body = RequestBody()
            body.add( "-")
            api.request(body = body)
        } catch(e: Exception) {
            return Resource.Error(e.message.toString())
        }
        return Resource.Success(response)
    }*/
}