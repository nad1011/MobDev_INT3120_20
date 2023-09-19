package lecture5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import lecture5.ui.theme.MobDev_HomeworkTheme

class MainLec5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobDev_HomeworkTheme {
                TabScreen()
            }
        }
    }
}

