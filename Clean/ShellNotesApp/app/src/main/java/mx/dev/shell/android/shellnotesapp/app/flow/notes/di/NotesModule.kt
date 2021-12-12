package mx.dev.shell.android.shellnotesapp.app.flow.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import mx.dev.shell.android.shellnotesapp.core.usecase.NotesUseCase
import mx.dev.shell.android.shellnotesapp.core.usecase.NotesUseCaseImpl

@Module
@InstallIn(FragmentComponent::class)
abstract class NotesModule {

    @Binds
    abstract fun bindNotesUseCase(impl: NotesUseCaseImpl): NotesUseCase
}