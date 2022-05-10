package androis.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androis.example.notes.databinding.ActivityMainBinding
import androis.example.notes.utilits.APP_ACTIVITY
import androis.example.notes.utilits.AppPreference

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        APP_ACTIVITY = this

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.title)

        AppPreference.getPreference(this)
    }
}