package cvdevelopers.takehome.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cvdevelopers.takehome.cache.dao.ClientCacheDao
import cvdevelopers.takehome.models.Client

@Database(entities = [Client::class], version = 1)
abstract class TakehomeDatabase: RoomDatabase() {

  abstract fun clientCacheDao(): ClientCacheDao

  companion object {
    @Volatile
    private var INSTANCE: TakehomeDatabase? = null

    fun getDatabase(context: Context): TakehomeDatabase {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }
      synchronized(this) {
        val instance = Room.databaseBuilder(
            context.applicationContext,
            TakehomeDatabase::class.java,
            "room_database"
        ).build()
        INSTANCE = instance
        return instance
      }
    }
  }
}