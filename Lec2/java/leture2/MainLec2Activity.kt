package leture2

import android.annotation.SuppressLint
import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.example.mobdev_homework.R


class MainLec2Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_lec2)

        getSupportActionBar()?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#3d4db8")))

        val numberPicker: NumberPicker = findViewById(R.id.numberPicker)

        numberPicker.minValue = 100
        numberPicker.maxValue = 1000
        numberPicker.value = 999
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("1")
        menu?.add("2")
        return super.onCreateOptionsMenu(menu)
    }
}