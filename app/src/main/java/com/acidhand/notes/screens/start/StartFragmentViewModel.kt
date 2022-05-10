package androis.example.notes.screens.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androis.example.notes.database.firebase.AppFirebaseRepository
import androis.example.notes.database.room.AppRoomDatabase
import androis.example.notes.database.room.AppRoomRepository
import androis.example.notes.utilits.REPOSITORY
import androis.example.notes.utilits.TYPE_FIREBASE
import androis.example.notes.utilits.TYPE_ROOM
import androis.example.notes.utilits.showToast

class StartFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application

    fun initDatabase(type: String, onSuccess: () -> Unit) {
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(mContext).getAppRoomDao()
                REPOSITORY = AppRoomRepository(dao)
                onSuccess()
            }
            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase({ onSuccess() }, { showToast(it) })
            }
        }
    }
}
