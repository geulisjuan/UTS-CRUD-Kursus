package andini.com.view

import andini.com.MainActivity
import andini.com.databinding.ActivityLoginBinding
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        mAuth = FirebaseAuth.getInstance()
        binding!!.newUser.setOnClickListener {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        }
        binding!!.btnMasuk.setOnClickListener {
            binding!!.loginLoading.visibility = View.VISIBLE
            val email: String = binding!!.mail.text.toString()
            val password: String = binding!!.pwd.text.toString()
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                Toast.makeText(
                    this@LoginActivity,
                    "Data tidak boleh kosong gan..",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        binding!!.loginLoading.visibility = View.GONE
                        Toast.makeText(
                            this@LoginActivity,
                            "Login berhasil gan..",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val i = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        binding!!.loginLoading.visibility = View.GONE
                        Toast.makeText(
                            this@LoginActivity,
                            "Kesalahan saat login gan..",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = mAuth!!.currentUser
        if (user != null) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}