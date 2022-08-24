package com.example.apex.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.data.source.local.ApexLocalDataSource

@Database(entities = [ApexListHeader::class,ApexItem::class] , version = 2, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {

    abstract fun apexDao(): ApexLocalDataSource
}