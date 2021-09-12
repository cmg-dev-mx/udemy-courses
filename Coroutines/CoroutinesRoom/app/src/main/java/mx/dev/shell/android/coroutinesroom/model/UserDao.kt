package mx.dev.shell.android.coroutinesroom.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User) : Long

    @Query("SELECT * FROM user WHERE user_name = :username")
    fun getUser(username: String): User?

    @Query("DELETE FROM user WHERE id = :id")
    fun deleteUser(id: Long)
}