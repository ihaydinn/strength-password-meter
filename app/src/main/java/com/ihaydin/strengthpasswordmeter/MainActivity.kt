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

        view.setStrengthCountTintColor(R.color.cardview_light_background)
        view.setStrengthBarSizeValue(16)
        view.setStrengthBarSpacerValue(10)
        view.setStrengthTextHeightValue(20)
        view.setTextPaddingStart(8f)

        val states = listOf(
            StrengthPasswordView.State(
                "Çok Zayıf",
                ContextCompat.getColor(this, android.R.color.holo_red_dark),
                1
            ),
            StrengthPasswordView.State(
                "Zayıf",
                ContextCompat.getColor(this, android.R.color.holo_red_light),
                2
            ),
            StrengthPasswordView.State(
                "Orta",
                ContextCompat.getColor(this, android.R.color.holo_orange_light),
                3
            ),
            StrengthPasswordView.State(
                "Güçlü",
                ContextCompat.getColor(this, android.R.color.holo_green_light),
                4
            ),
            StrengthPasswordView.State(
                "Çok Güçlü",
                ContextCompat.getColor(this, android.R.color.holo_green_dark),
                5
            )
        )

        view.setList(states)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, value: Int, p2: Boolean) {
                if (value != 0) {
                    when (value) {
                        1 -> {
                            view.setStrengthEnabledValues(0)
                        }
                        2 -> {
                            view.setStrengthEnabledValues(1)
                        }
                        3 -> {
                            view.setStrengthEnabledValues(2)
                        }
                        4 -> {
                            view.setStrengthEnabledValues(3)
                        }
                        5 -> {
                            view.setStrengthEnabledValues(4)
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
                            view.setStrengthEnabledValues(0)
                        }
                        value.toString() == "zayor" -> {
                            view.setStrengthEnabledValues(2)
                        }
                        value.toString() == "zayorgü" -> {
                            view.setStrengthEnabledValues(4)
                        }
                        else -> {
                            view.setStrengthEnabledValues(0)
                        }
                    }
                }

            }
        })

    }
}