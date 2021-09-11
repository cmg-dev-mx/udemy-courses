package mx.dev.shell.android.coroutinesroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "password_hash")
    val passwordHash: Int,
    @ColumnInfo(name = "info")
    val info: String
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
