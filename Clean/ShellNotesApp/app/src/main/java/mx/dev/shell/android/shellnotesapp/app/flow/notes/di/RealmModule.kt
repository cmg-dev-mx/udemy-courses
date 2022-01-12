package mx.dev.shell.android.shellnotesapp.app.flow.notes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.dev.shell.android.shellnotesapp.db.base.RealmProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm() = RealmProvider.getDb()
}