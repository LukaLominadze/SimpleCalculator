package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class MainActivity : AppCompatActivity() {
    private lateinit var textResult: TextView
    private lateinit var textEquation: TextView

    private lateinit var buttonC: Button
    private lateinit var buttonEqual: Button
    private lateinit var buttonBackspace: Button

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textResult = findViewById(R.id.textResult);
        textEquation = findViewById(R.id.textEquation);
        buttonC = findViewById(R.id.buttonC);
        buttonEqual = findViewById(R.id.buttonEqual);
        buttonBackspace = findViewById(R.id.buttonBackspace);

        val numButtons: List<Button> = listOf(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
            findViewById(R.id.buttonAdd),
            findViewById(R.id.buttonSub),
            findViewById(R.id.buttonMultiply),
            findViewById(R.id.buttonDivide),
            findViewById(R.id.buttonDot),
            findViewById(R.id.buttonBracketLeft),
            findViewById(R.id.buttonBracketRight),
        );
        for (i in 1..numButtons.size) {
            numButtons[i - 1].setOnClickListener { onButtonClicked(numButtons[i - 1].text) };
        }
        buttonC.setOnClickListener{ onButtonCClicked() }
        buttonEqual.setOnClickListener{ onEqualClicked() }
        buttonBackspace.setOnClickListener{ onBackspaceClicked() }
    }

    private fun onButtonClicked(number: CharSequence) {
        textEquation.append(number);
    }

    private fun onButtonCClicked() {
        if (textEquation.text.isEmpty()) {
            textResult.text = "0";
        }
        textEquation.text = "";
    }

    private fun onEqualClicked() {
        try {
            var context = Context.enter();
            context.optimizationLevel = -1;
            var scriptable = context.initStandardObjects();
            var result = context.evaluateString(scriptable, textEquation.text.toString(),
                "Javascript", 1, null).toString();
            textResult.text = result;
            textEquation.text = result;
        }
        catch(e: Exception) {
            textResult.text = "Err";
            textEquation.text = "";
        }
    }

    private fun onBackspaceClicked() {
        if (textEquation.text.isNotEmpty()) {
            textEquation.text = textEquation.text.substring(0, textEquation.text.length - 1);
        }
    }
}