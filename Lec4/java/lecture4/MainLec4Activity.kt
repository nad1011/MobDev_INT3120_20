package lecture4

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lecture4.ui.theme.TestTheme

class MainLec4Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                Column(Modifier.padding(16.dp)) {
                    Column(Modifier.padding(bottom = 8.dp)) {
                        BasicTextField("Enter your Name")
                        BasicTextField("Enter your phone number")
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)) {
                        SimpleRadioButton(listOf("Cheese", "2 x Cheese", "None"), Modifier)
                        SimpleRadioButton(listOf("Square", "Round"), Modifier)
                        SimpleCheckboxComponent(listOf("Pepperoni", "Mushroom", "Veggies"), Modifier)
                    }
                    BigTexField()
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)) {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .shadow(2.dp, RoundedCornerShape(5.dp))
                                .height(36.dp)
                                .align(Alignment.CenterStart),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(214, 215, 215, 255),
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "Exit")
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .shadow(2.dp, RoundedCornerShape(5.dp))
                                .height(36.dp)
                                .align(Alignment.CenterEnd),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(214, 215, 215, 255),
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "SMS - PLACE YOUR ORDER")
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTextField(hint: String) {
    var value by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true
    val singleLine = true
    BasicTextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth()
            .indicatorLine(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(255, 63, 128, 255),
                    unfocusedIndicatorColor = Color(189, 189, 189, 255),
                )
            ),
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = "",
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            interactionSource = interactionSource,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Unspecified
            ),
            label = { Text(hint) },
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp
            )
        )
    }
}

@Composable
fun SimpleRadioButton(options: List<String>, modifier: Modifier) {

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }
// Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Row(
        modifier
            .selectableGroup()
            .padding(bottom = 8.dp)) {
        options.forEach { text ->
            Row(
                Modifier
//                    .height(32.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null, // null recommended for accessibility with screenreaders
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(255, 63, 128, 255),
                    )
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleCheckboxComponent(options: List<String>, modifier: Modifier) {
    // in below line we are setting
    // the state of our checkbox.
    val checkedState = remember { mutableStateOf(false) }
    // in below line we are displaying a row
    // and we are creating a checkbox in a row.
    Row(modifier.selectableGroup()) {
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
                    Checkbox(
                        checked = checkedState.value,
                        // below line is use to add padding
                        // to our checkbox.
                        modifier = Modifier.padding(end = 5.dp),
                        // below line is use to add on check
                        // change to our checkbox.
                        onCheckedChange = { checkedState.value = it },
                    )
                }
                // below line is use to add text to our check box and we are
                // adding padding to our text of checkbox
                Text(text = option, modifier = Modifier.padding(end = 5.dp))
            }

        }
    }
}

@Composable
fun BigTexField() {
    Column(Modifier.padding(top = 4.dp)){
        Text(text = "Your ordering:", color = Color(146, 146, 146, 255))
        BasicTextField(
            value = "",
            onValueChange = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(221, 214, 214, 255)),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestTheme {
        Column(Modifier.padding(16.dp)) {
            Column(Modifier.padding(bottom = 8.dp)) {
                BasicTextField("Enter your Name")
                BasicTextField("Enter your phone number")
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)) {
                SimpleRadioButton(listOf("Cheese", "2 x Cheese", "None"), Modifier)
                SimpleRadioButton(listOf("Square", "Round"), Modifier)
                SimpleCheckboxComponent(listOf("Pepperoni", "Mushroom", "Veggies"), Modifier)
            }
            BigTexField()
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(5.dp))
                        .height(36.dp)
                        .align(Alignment.CenterStart),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(214, 215, 215, 255),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Exit")
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(5.dp))
                        .height(36.dp)
                        .align(Alignment.CenterEnd),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(214, 215, 215, 255),
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "SMS - PLACE YOUR ORDER")
                }
            }
        }
    }
}