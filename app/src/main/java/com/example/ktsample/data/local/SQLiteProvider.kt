package com.example.ktsample.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SQLiteProvider @Inject constructor(@ApplicationContext val context: Context){

}