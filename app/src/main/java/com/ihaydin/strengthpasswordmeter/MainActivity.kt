package com.ihaydin.strengthpasswordmeter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<StrengthPasswordView>(R.id.strengthPasswordView)
        val etContent = findViewById<EditText>(R.id.etContent)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        view.setTintColor(R.color.cardview_light_background)
        view.setStrengthValue(5)
        view.setStrengthBarSizeValue(16)
        view.setStrengthBarSpacerValue(10)
        view.setStrengthTextHeightValue(20)
        view.setTextPaddingStart(8f)

        val states = listOf(
            StrengthPasswordView.State(
                "Çok Zayıf",
                ContextCompat.getColor(this, android.R.color.holo_red_dark)
            ),
            StrengthPasswordView.State(
                "Zayıf",
                ContextCompat.getColor(this, android.R.color.holo_red_light)
            ),
            StrengthPasswordView.State(
                "Orta",
                ContextCompat.getColor(this, android.R.color.holo_orange_light)
            ),
            StrengthPasswordView.State(
                "Güçlü",
                ContextCompat.getColor(this, android.R.color.holo_green_light)
            ),
            StrengthPasswordView.State(
                "Çok Güçlü",
                ContextCompat.getColor(this, android.R.color.holo_green_dark)
            )
        )

        view.setList(states)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, value: Int, p2: Boolean) {
                if (value != 0) {
                    when (value) {
                        1 -> {
                            view.setSelection(0)
                            view.setTintSize(1)
                        }
                        2 -> {
                            view.setSelection(1)
                            view.setTintSize(2)
                        }
                        3 -> {
                            view.setSelection(2)
                            view.setTintSize(3)
                        }
                        4 -> {
                            view.setSelection(3)
                            view.setTintSize(4)
                        }
                        5 -> {
                            view.setSelection(4)
                            view.setTintSize(5)
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(value: Editable?) {
                if (!value.isNullOrEmpty()) {
                    when {
                        value.toString() == "zay" -> {
                            view.setSelection(0)
                            view.setTintSize(1)
                        }
                        value.toString() == "zayor" -> {
                            view.setSelection(1)
                            view.setTintSize(3)
                        }
                        value.toString() == "zayorgü" -> {
                            view.setSelection(2)
                            view.setTintSize(5)
                        }
                        else -> {
                            view.setSelection(0)
                            view.setTintSize(1)
                        }
                    }
                }

            }
        })

    }
}