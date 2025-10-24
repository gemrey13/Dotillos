package com.example.dotillos.repository

import android.util.Log
import com.example.dotillos.core.SupabaseClientManager.supabaseClient
import com.example.dotillos.models.ProfileModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns


suspend fun getProfiles(): List<ProfileModel> {
    val result = supabaseClient.from("profiles").select()
    return result.decodeList<ProfileModel>()
}

suspend fun getFilteredProfiles(id: String): List<ProfileModel> {
    val result = supabaseClient.from("profiles")
        .select(columns = Columns.list("id", "name", "role", "phone")) {
        filter {
            ProfileModel::id eq id
        }
    }
    val decoded = result.decodeList<ProfileModel>()
    Log.d("ProfileFetch", "UserId=$id, Profiles=$decoded")
    return decoded
}