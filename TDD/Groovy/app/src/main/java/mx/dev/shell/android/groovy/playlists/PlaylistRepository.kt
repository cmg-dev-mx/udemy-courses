package mx.dev.shell.android.groovy.playlists

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val service: PlaylistService,
    private val mapper: PlaylistMapper
) {

    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> =
        service.fetchPlaylists()
            .map {
                if (it.isSuccess) {
                    Result.success(mapper(it.getOrNull()!!))
                } else {
                    Result.failure(it.exceptionOrNull()!!)
                }
            }
}
