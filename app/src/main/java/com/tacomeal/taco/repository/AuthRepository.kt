package com.tacomeal.taco.repository

import com.tacomeal.taco.model.*
import com.tacomeal.taco.remote.Api
import com.tacomeal.taco.util.Resource
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


}