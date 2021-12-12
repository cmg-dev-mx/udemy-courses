package mx.dev.shell.android.shellnotesapp.app.flow.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import mx.dev.shell.android.shellnotesapp.core.repository.NoteRepository
import mx.dev.shell.android.shellnotesapp.db.source.NoteDataSourceImpl
import mx.dev.shell.android.shellnotesapp.repository.implementation.NoteRepositoryImpl
import mx.dev.shell.android.shellnotesapp.repository.source.NoteDataSource

@Module
@InstallIn(FragmentComponent::class)
abstract class NoteRepositoryModule {

    @Binds
    abstract fun bindNoteRepository(impl: NoteRepositoryImpl): NoteRepository

    @Binds
    abstract fun bindNoteDataSource(impl: NoteDataSourceImpl): NoteDataSource
}