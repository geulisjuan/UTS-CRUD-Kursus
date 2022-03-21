package andini.com

import andini.com.adapter.CourseRVAdapter
import andini.com.databinding.ActivityMainBinding
import andini.com.databinding.BottomSheetLayoutBinding
import andini.com.model.CourseRVModal
import andini.com.view.AddCourseActivity
import andini.com.view.EditCourseActivity
import andini.com.view.LoginActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity(), CourseRVAdapter.CourseClickInterface {
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null
    private var courseRVModalArrayList: ArrayList<CourseRVModal?>? = null
    private var courseRVAdapter: CourseRVAdapter? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        firebaseDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        courseRVModalArrayList = ArrayList()
        databaseReference = firebaseDatabase!!.getReference("Kursus")
        binding!!.floating.setOnClickListener {
            val i = Intent(this@MainActivity, AddCourseActivity::class.java)
            startActivity(i)
        }
        courseRVAdapter = CourseRVAdapter(courseRVModalArrayList, this, this)
        binding!!.recyclerview.layoutManager = LinearLayoutManager(this)
        binding!!.recyclerview.adapter = courseRVAdapter
        courses
    }

    private val courses: Unit
        get() {
            courseRVModalArrayList!!.clear()
            databaseReference!!.addChildEventListener(object : ChildEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    binding?.mainLoading?.visibility = View.GONE
                    courseRVModalArrayList!!.add(snapshot.getValue(CourseRVModal::class.java))
                    courseRVAdapter?.notifyDataSetChanged()
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    binding?.mainLoading?.visibility = View.GONE
                    courseRVAdapter?.notifyDataSetChanged()
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onChildRemoved(snapshot: DataSnapshot) {
                    courseRVAdapter?.notifyDataSetChanged()
                    binding?.mainLoading?.visibility = View.GONE
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    courseRVAdapter?.notifyDataSetChanged()
                    binding?.mainLoading?.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

    override fun onCourseClick(position: Int) {
        displayBottomSheet(courseRVModalArrayList!![position])
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.idLogOut -> {
                Toast.makeText(applicationContext, "User Logged Out", Toast.LENGTH_LONG).show()
                mAuth!!.signOut()
                val i = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun displayBottomSheet(modal: CourseRVModal?) {
        val bottomSheetTeachersDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val mBottomSheetBinding = BottomSheetLayoutBinding.inflate(layoutInflater, null, false)
        val view: View = mBottomSheetBinding.root
        bottomSheetTeachersDialog.setContentView(view)
        bottomSheetTeachersDialog.setCancelable(false)
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true)
        bottomSheetTeachersDialog.show()
        if (modal != null) {
            mBottomSheetBinding.namaDetail.text = modal.courseName
            mBottomSheetBinding.penjelasanDetail.text = modal.courseDescription
            mBottomSheetBinding.jenjangDetail.text = "Jenjang " + modal.bestSuitedFor
            mBottomSheetBinding.biayaDetail.text = "RP " + modal.coursePrice
            Picasso.get().load(modal.courseImg).into(mBottomSheetBinding.imgDetail)

        }
        mBottomSheetBinding.btnUbah.setOnClickListener {
            val i = Intent(this@MainActivity, EditCourseActivity::class.java)
            i.putExtra("kursus", modal)
            startActivity(i)
            bottomSheetTeachersDialog.hide()
        }
        mBottomSheetBinding.btnDetail.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            if (modal != null) {
                i.data = Uri.parse(modal.courseLink)
            }
            startActivity(i)
        }
    }
}