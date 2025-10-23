package com.example.dotillos.core

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.postgrest

object SupabaseClientManager {
    private var client: SupabaseClient? = null

    val supabaseClient: SupabaseClient
        get() = client ?: throw IllegalStateException("SupabaseClientManager not initialized. Call initialize() first.")

    fun initialize(url: String, anonKey: String) {
        if (client == null) {
            client = createSupabaseClient(
                supabaseUrl = url,
                supabaseKey = anonKey
            ) {
                install(Postgrest)
                install(Auth)
            }
//            println("Supabase Client Initialized.")
        }
    }

    val database
        get() = supabaseClient.postgrest

}
