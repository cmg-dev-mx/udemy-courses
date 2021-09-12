package mx.dev.shell.android.coroutinesroom.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class
    ],
    version = 1
)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var instance: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(
                context,
                UserDataBase::class.java,
                "user-database"
            ).build()
    }
}