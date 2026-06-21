package com.homecontentlock.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.homecontentlock.domain.model.ActivationSecret
import com.homecontentlock.domain.repository.IPasswordValidator
import java.security.MessageDigest
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordValidatorImpl @Inject constructor(
    private val context: Context
) : IPasswordValidator {

    private val sharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "activation_secret_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun validate(password: String): Boolean {
        val savedHash = sharedPreferences.getString("password_hash", null)
        return savedHash != null && savedHash == hashPassword(password)
    }

    override suspend fun save(password: String) {
        val hash = hashPassword(password)
        sharedPreferences.edit().putString("password_hash", hash).apply()
        sharedPreferences.edit().putLong("created_at", Instant.now().toEpochMilli()).apply()
    }

    override suspend fun getActivationSecret(): ActivationSecret? {
        val hash = sharedPreferences.getString("password_hash", null)
        val createdAt = sharedPreferences.getLong("created_at", -1)
        
        return if (hash != null && createdAt != -1L) {
            ActivationSecret(
                passwordHash = hash,
                createdAt = Instant.ofEpochMilli(createdAt)
            )
        } else {
            null
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}