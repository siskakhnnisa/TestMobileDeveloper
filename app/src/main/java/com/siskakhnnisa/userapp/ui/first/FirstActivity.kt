package com.siskakhnnisa.userapp.ui.first

import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.siskakhnnisa.userapp.R
import com.siskakhnnisa.userapp.databinding.ActivityFirstBinding
import com.siskakhnnisa.userapp.ui.second.SecondActivity

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCheck.setOnClickListener{
            val textInput= binding.etPalindrome.text.toString()
            val isPalindrome = isPalindrome(textInput)
            var message = if (isPalindrome) "isPalindrome" else "not palindrome"
            if (textInput.isEmpty()) {
                message = "Please fill the text form"
            }
            showDialogAlert("Palindrome Checker", message)
        }

        binding.btnNext.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()) {
                showDialogAlert("Warning", "Please enter your name")
            }else{
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra(SecondActivity.TAG, binding.etName.text.toString())
                startActivity(intent)
            }
        }
    }

    private fun isPalindrome(input: String): Boolean {
        val cleanedInput = input.replace(" ", "").lowercase()
        return cleanedInput == cleanedInput.reversed()
    }

    private fun showDialogAlert(title: String, message: String){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}