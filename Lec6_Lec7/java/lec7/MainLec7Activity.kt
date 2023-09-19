package lec7

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import lec7.ui.theme.HomeworkTheme

class MainLec7Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeworkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {

    var (name,setName) = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current as ComponentActivity
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data!!.getStringExtra("feedback")
            if (data != null) {
                setName(data)
            }
        } else {
            // The activity failed
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column (modifier = Modifier.align(Alignment.Center)){
            Text(text = name)
            Button(
                onClick = {
                    launcher.launch(
                        Intent(context, TestActivity::class.java).putExtra("myName", "Dan")
                    )
                }
            ) {
                Text(text = "Explicit Intent")
            }
            ImplicitIntentButtons(modifier = Modifier)
        }
    }
}