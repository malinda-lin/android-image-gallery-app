package com.onramp.android.takehome.imageData

// This class defines single image attributes
data class Image (
        val alt_description: String,
        val created_at: String,
        val description: String,
        val id: String,
        val links: Links,
        val urls: Urls,
        val user: User
)
