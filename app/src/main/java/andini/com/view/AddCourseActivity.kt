package andini.com.view

import andini.com.MainActivity
import andini.com.databinding.ActivityAddCourseBinding
import andini.com.model.CourseRVModal
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class AddCourseActivity : AppCompatActivity() {
    private var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    private var courseID: String? = null
    private var binding: ActivityAddCourseBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("Kursus")
        binding!!.simpanKursus.setOnClickListener {
            binding!!.tambahLoading.visibility = View.VISIBLE
            val courseName: String = binding!!.namaKursus.text.toString()
            val courseDesc: String = binding!!.penjelasanKursus.text.toString()
            val coursePrice: String = binding!!.biayaKursus.text.toString()
            val bestSuited: String = binding!!.jenjangKursus.text.toString()
            val courseImg: String = binding!!.linkGambar.text.toString()
            val courseLink: String = binding!!.linkKursus.text.toString()
            courseID = courseName
            val courseRVModal = CourseRVModal(
                courseID,
                courseName,
                courseDesc,
                coursePrice,
                bestSuited,
                courseImg,
                courseLink
            )
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    databaseReference!!.child(courseID!!).setValue(courseRVModal)
                    Toast.makeText(
                        this@AddCourseActivity,
                        "Hehehe Kursus Berhasil ditambahkan..",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@AddCourseActivity, MainActivity::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AddCourseActivity, "Waduh gagal gan..", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}