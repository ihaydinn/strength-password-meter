package com.ihaydin.strengthpasswordmeter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<StrengthPasswordView>(R.id.strengthPasswordView)
        val etContent = findViewById<EditText>(R.id.etContent)

        view.setTintColor(R.color.cardview_light_background)
        view.setStrengthValue(5)
        view.setStrengthBarSizeValue(50)
        view.setStrengthBarSpacerValue(10)
        view.setStrengthTextHeightValue(40)

        val states = listOf(
            StrengthPasswordView.State(
                "Zayıff",
                ContextCompat.getColor(this, R.color.purple_500)
            ),
            StrengthPasswordView.State(
                "Orta",
                ContextCompat.getColor(this, R.color.teal_700)
            ),
            StrengthPasswordView.State(
                "Güçlü",
                ContextCompat.getColor(this, R.color.teal_200)
            )
        )

        view.setList(states)


        etContent.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()){
                    when {
                        p0.toString() == "zay" -> {
                            view.setSelection(0)
                            view.setTintSize(1)
                        }
                        p0.toString() == "or" -> {
                            view.setSelection(1)
                            view.setTintSize(3)
                        }
                        p0.toString()=="gü" -> {
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