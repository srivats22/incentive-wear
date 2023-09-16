package com.experiment.srivats.incentivewear.presentation.helper.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val USER_UID = stringPreferencesKey("user_uid")
        private val USER_AUTH_STATE = booleanPreferencesKey("u_auth_state")
    }

    val getAccessToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_UID] ?: ""
    }

    val getAuthState: Flow<Boolean> = context.dataStore.data.map {pref ->
        pref[USER_AUTH_STATE] ?: false
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_UID] = token
        }
    }

    suspend fun saveAuthState(state: Boolean) {
        context.dataStore.edit { pref ->
            pref[USER_AUTH_STATE] = state
        }
    }
}