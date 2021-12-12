package mx.dev.shell.android.shellnotesapp.app.flow.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import mx.dev.shell.android.shellnotesapp.core.usecase.NoteUseCase
import mx.dev.shell.android.shellnotesapp.core.usecase.NoteUseCaseImpl

@Module
@InstallIn(FragmentComponent::class)
abstract class NoteModule {

    @Binds
    abstract fun bindNoteUseCase(impl: NoteUseCaseImpl): NoteUseCase
}