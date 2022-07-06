package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.END
import com.dicoding.courseschedule.util.START
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener  {

    private lateinit var viewModel: AddCourseViewModel

    private lateinit var etCourseName:TextInputEditText
    private lateinit var spinnerDay: Spinner
    private lateinit var etLecturer:TextInputEditText
    private lateinit var etNote:TextInputEditText
    private lateinit var tvStartTime:TextView
    private lateinit var tvEndTime:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        etCourseName = findViewById(R.id.add_ed_course_name)
        spinnerDay = findViewById(R.id.day)
        etLecturer = findViewById(R.id.add_ed_lecturer)
        etNote = findViewById(R.id.add_ed_note)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        viewModel.saved.observe(this){
            val saved = it.getContentIfNotHandled()?: false
            if(saved){
                Toast.makeText(this, "Course Saved", Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this, "Form must not empty", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                //TODO 12 : Create AddTaskViewModel and insert new task to database
                viewModel.insertCourse(
                    courseName = etCourseName.text.toString(),
                    day = spinnerDay.selectedItemPosition,
                    startTime = tvStartTime.text.toString(),
                    endTime = tvEndTime.text.toString(),
                    lecturer = etLecturer.text.toString(),
                    note = etNote.text.toString()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDatePickerStart(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, START)
    }
    fun showDatePickerEnd(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, END)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val time = "$hour:$minute"
        if (tag == START){
            tvStartTime.text = time
        }else if (tag == END){
            tvEndTime.text = time
        }
    }
}