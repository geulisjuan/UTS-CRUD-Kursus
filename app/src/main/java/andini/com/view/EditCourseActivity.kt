package andini.com.view

import andini.com.MainActivity
import andini.com.databinding.ActivityEditCourseBinding
import andini.com.model.CourseRVModal
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.lang.Exception


class EditCourseActivity : AppCompatActivity() {
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    var courseRVModal: CourseRVModal? = null
    private var courseID: String? = null
    private var binding: ActivityEditCourseBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCourseBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        firebaseDatabase = FirebaseDatabase.getInstance()
        courseRVModal = intent.getParcelableExtra("kursus")
        if (courseRVModal != null) {
            binding!!.namaKursusEdit.setText(courseRVModal!!.courseName)
            binding!!.biayaKursusEdit.setText(courseRVModal!!.coursePrice)
            binding!!.jenjangKursusEdit.setText(courseRVModal!!.bestSuitedFor)
            binding!!.linkGambarEdit.setText(courseRVModal!!.courseImg)
            binding!!.linkKursusEdit.setText(courseRVModal!!.courseLink)
            binding!!.penjelasanKursusEdit.setText(courseRVModal!!.courseDescription)
            courseID = courseRVModal!!.courseId
        }
        databaseReference = firebaseDatabase!!.getReference("Kursus").child(courseID!!)
        binding!!.perbarui.setOnClickListener {
            binding!!.editLoading.visibility = View.VISIBLE
            Toast.makeText(
                this@EditCourseActivity,
                "Berhasil Perbarui Kursus..",
                Toast.LENGTH_SHORT
            ).show()
            val courseName: String = binding!!.namaKursusEdit.text.toString()
            val courseDesc: String = binding!!.penjelasanKursusEdit.text.toString()
            val coursePrice: String = binding!!.biayaKursusEdit.text.toString()
            val bestSuited: String = binding!!.jenjangKursusEdit.text.toString()
            val courseImg: String = binding!!.linkGambarEdit.text.toString()
            val courseLink: String = binding!!.linkKursusEdit.text.toString()

            try {
                val map: MutableMap<String, Any?> =
                    HashMap()
                map["courseName"] = courseName
                map["courseDescription"] = courseDesc
                map["coursePrice"] = coursePrice
                map["bestSuitedFor"] = bestSuited
                map["courseImg"] = courseImg
                map["courseLink"] = courseLink
                map["courseId"] = courseID
                databaseReference!!.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        binding!!.editLoading.visibility = View.GONE
                        databaseReference!!.updateChildren(map)

                        startActivity(Intent(this@EditCourseActivity, MainActivity::class.java))
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@EditCourseActivity, "Gagal Perbarui..", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }catch (e : Exception){
                Toast.makeText(applicationContext,"Terjadi Kesalahan",Toast.LENGTH_SHORT).show()
            }

        }
        binding!!.hapus.setOnClickListener { deleteCourse() }
    }

    private fun deleteCourse() {
        databaseReference!!.removeValue()
        Toast.makeText(this, "Kursus Terhapus..", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@EditCourseActivity, MainActivity::class.java))
    }
}