package com.example.calcalator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calcalator.R

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var currentInput: String = ""
    private var currentOperator: String? = ""
    private var firstOperand: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.num)

        // เพิ่ม OnClickListener ให้กับปุ่มทุกปุ่มตัวเลข
        val numberButtons = arrayOf(
            R.id.nb1, R.id.nb2, R.id.nb3, R.id.nb4,
            R.id.nb5, R.id.nb6, R.id.nb7, R.id.nb8,
            R.id.nb9 ,R .id.nb0, R.id.btJ 
        )

        for (buttonId in numberButtons) {
            findViewById<Button>(buttonId).setOnClickListener {
                onNumberButtonClick(it)
            }
        }

        // เพิ่ม OnClickListener ให้กับปุ่มลบ
        findViewById<Button>(R.id.del).setOnClickListener {
            onDeleteButtonClick()
        }

        // เพิ่ม OnClickListener ให้กับปุ่มลบทั้งหมด
        findViewById<Button>(R.id.btC).setOnClickListener {
            clearAll()
        }


        // เพิ่ม OnClickListener ให้กับปุ่มเพิ่ม, ลบ, คูณ, หาร, modulo
        val operatorButtons = arrayOf(
            R.id.btB, R.id.btL, R.id.btX,R.id.btH, R.id.btP
        )

        for (buttonId in operatorButtons) {
            findViewById<Button>(buttonId).setOnClickListener {
                onOperatorButtonClick(it)
            }
        }

        // เพิ่ม OnClickListener ให้กับปุ่มเท่ากับ
        findViewById<Button>(R.id.btT).setOnClickListener {
            onEqualsButtonClick()
        }
        Log.e("cs", "Result: $currentInput")
        Log.e("cc", "Result: $currentOperator")
        Log.e("we", "Result: $resultTextView")
        Log.e("rd", "Result: $firstOperand")
    }

    private fun clearAll() {
        currentInput = "0"
        currentOperator = null
        firstOperand = null
        updateResult()
    }


    private fun onNumberButtonClick(view: View) {
        val buttonText = (view as Button).text.toString()

        if (currentInput == "0") {
            // กรณีที่เป็นเลข 0 เลขเดียวบนหน้าจอ ไม่เพิ่มจำนวนเลข 0
            currentInput = buttonText
        } else {
            currentInput += buttonText
        }

        updateResult()
    }

    private fun onDeleteButtonClick() {
        if (currentInput.length > 1) {
            // ลบตัวเลขที่พิมพ์ล่าสุดทีละ 1 หลัก
            currentInput = currentInput.substring(0, currentInput.length - 1)
        } else {
            // หากเหลือเลขหลักเดียว กดลบซ้ำเป็นเลข 0
            currentInput = "0"
        }

        updateResult()
    }

    private fun onOperatorButtonClick(view: View) {
        currentOperator = (view as Button).text.toString()
        firstOperand = currentInput.toDouble()
        currentInput = "0"
    }

    private fun onEqualsButtonClick() {
        if (currentOperator != null && firstOperand != null) {
            val secondOperand = currentInput.toDouble()
            val result = when (currentOperator) {
                "+" -> firstOperand!! + secondOperand
                "-" -> firstOperand!! - secondOperand
                "X" -> firstOperand!! * secondOperand
                "/" -> firstOperand!! / secondOperand
                "%" -> firstOperand!! % secondOperand
                else -> throw IllegalArgumentException("Invalid operator")
            }

            currentInput = result.toString()
            currentOperator = null
            firstOperand = null

            updateResult()
        }
    }

    private fun updateResult() {
        resultTextView.text = currentInput
    }
}
