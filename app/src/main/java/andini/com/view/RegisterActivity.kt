package andini.com.view

import andini.com.databinding.ActivityRegisterBinding
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var binding: ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        mAuth = FirebaseAuth.getInstance()
        binding!!.userMasuk.setOnClickListener {
            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(i)
        }
        binding!!.daftar.setOnClickListener {
            binding!!.loadingRegis.visibility = View.VISIBLE
            val userName: String = binding!!.mail.text.toString()
            val pwd: String = binding!!.pwd.text.toString()
            val cnfPwd: String = binding!!.ulPwd.text.toString()
            if (pwd != cnfPwd) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password tidak cocok gan..",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(
                    cnfPwd
                )
            ) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Data tidak boleh kosong gan..",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mAuth!!.createUserWithEmailAndPassword(userName, pwd)
                    .addOnCompleteListener { task: Task<AuthResult?> ->
                        if (task.isSuccessful) {
                            binding!!.loadingRegis.visibility = View.GONE
                            Toast.makeText(
                                this@RegisterActivity,
                                "Berhasil mendaftar..",
                                Toast.LENGTH_SHORT
                            ).show()
                            val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            binding!!.loadingRegis.visibility = View.GONE
                            Toast.makeText(
                                this@RegisterActivity,
                                "Pendafataran Gagal..",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}