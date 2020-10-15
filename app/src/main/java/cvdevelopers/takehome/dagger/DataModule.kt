package cvdevelopers.takehome.dagger

import android.app.Application
import cvdevelopers.takehome.cache.TakehomeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

  @Provides
  @Singleton
  fun providesDatabase(application: Application) = TakehomeDatabase.getDatabase(application)

  @Provides
  @Singleton
  fun providesClientCacheDao(database: TakehomeDatabase) = database.clientCacheDao()
}