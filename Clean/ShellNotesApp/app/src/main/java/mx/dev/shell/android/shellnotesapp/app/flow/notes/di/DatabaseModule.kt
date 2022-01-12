package mx.dev.shell.android.shellnotesapp.app.flow.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import mx.dev.shell.android.shellnotesapp.db.dao.NoteDao
import mx.dev.shell.android.shellnotesapp.db.dao.NoteDaoImpl

@Module
@InstallIn(FragmentComponent::class)
abstract class DatabaseModule {

    @Binds
    abstract fun provideNoteDao(impl: NoteDaoImpl): NoteDao
}