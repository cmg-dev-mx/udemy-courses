package mx.dev.shell.android.groovy.playlists

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(FragmentComponent::class)
class PlaylistsModule {

    @Provides
    fun playlistApi(retrofit: Retrofit): PlaylistApi {
        return retrofit.create(PlaylistApi::class.java)
    }

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.7:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}