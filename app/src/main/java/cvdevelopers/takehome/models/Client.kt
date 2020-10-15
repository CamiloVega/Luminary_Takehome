package cvdevelopers.takehome.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(
    @PrimaryKey
    val email: String,
    @Embedded
    val id: Id,
    @Embedded
    val name: Name,
    @Embedded
    val picture: Picture
) {

}