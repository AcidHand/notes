package androis.example.notes.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androis.example.notes.utilits.REPOSITORY

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val allNotes = REPOSITORY.allNotes
    fun signOut() {
        REPOSITORY.signOut()
    }
}