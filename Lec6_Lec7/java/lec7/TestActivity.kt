package lec7

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent : Intent = this.intent
        var myName = intent.getStringExtra("myName")
        setContent {

                val context = LocalContext.current as ComponentActivity
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier.fillMaxSize(),

                ) {
                    Column(modifier = Modifier.align(Alignment.Center),){
                        myName?.let { Greeting(it) }
                        Button(onClick = {
                            setResult(RESULT_OK, Intent().putExtra("feedback", "Hello $myName"))
                            onBackPressedDispatcher.onBackPressed()
                        }) {
                            Text(text = "Go back")
                        }
                    }
                }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

