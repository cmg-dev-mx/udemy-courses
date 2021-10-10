package mx.dev.shell.android.groovy.playlistDetail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import mx.dev.shell.android.groovy.playlists.PlaylistApi
import java.lang.RuntimeException
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(
    private val api: PlaylistApi
) {

    suspend fun fetchPlaylistDetail(id: String): Flow<Result<PlaylistDetail>> {
        return flow {
            emit(Result.success(api.fetchPlaylistById(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}
