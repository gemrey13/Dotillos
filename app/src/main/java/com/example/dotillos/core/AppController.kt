package com.example.dotillos.core

import android.app.Application

class AppController : Application() {
    private val supabaseurl = "https://skxqdprdgewdkmtdvrhy.supabase.co"
    private val supabaseanonkey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNreHFkcHJkZ2V3ZGttdGR2cmh5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjEwMjkzNDEsImV4cCI6MjA3NjYwNTM0MX0.Ngouzsql-3NS-MhEigrsaAvULFzEKIx6z_jjnMDYypA"
    override fun onCreate() {
        super.onCreate()
        SupabaseClientManager.initialize(
            url = supabaseurl,
            anonKey = supabaseanonkey
        )
    }
}
