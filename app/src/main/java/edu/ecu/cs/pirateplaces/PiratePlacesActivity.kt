package edu.ecu.cs.pirateplaces

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

const val KEY_INDEX = "index"
private const val REQUEST_CHECKIN_CODE = 0

class PiratePlacesActivity : AppCompatActivity() {

    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var locationTextView: TextView
    private lateinit var nameTextView: TextView
    private var nextIndex = 0
    private var prevIndex = 0

    private val locationViewModel: LocationViewModel by lazy {
        ViewModelProviders.of(this).get(LocationViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pirate_places)

        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        locationTextView = findViewById(R.id.location_text_view)
        nameTextView = findViewById(R.id.name_text_view)

        nextButton.setOnClickListener {
            nextIndex = locationViewModel.moveToNext()
            showToast()
            updateLocation()
            updateName()

        }

        prevButton.setOnClickListener{
            prevIndex = locationViewModel.moveToPrev()
            showToast()
            updateLocation()
            updateName()
        }

        locationTextView.setOnClickListener{
            val currentIndex = locationViewModel.currentIndex
            val intent = CheckInActivity.newIntent(this, currentIndex)
            startActivityForResult(intent, REQUEST_CHECKIN_CODE)
        }

        updateLocation()
        updateName()

    }

    private fun checkCheckIn(){
        if(locationViewModel.isCheckedIn){
            Toast.makeText(this, R.string.checkin_toast, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK){
            return
        }

        if(requestCode == REQUEST_CHECKIN_CODE){
            locationViewModel.isCheckedIn = data?.getBooleanExtra(EXTRA_CHECKED_IN, false) ?: false
            checkCheckIn()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, locationViewModel.currentIndex)
    }

    private fun updateLocation(){
        val locationTextResId = locationViewModel.currentLocationText

        locationTextView.setText(locationTextResId)

    }

    private fun updateName(){
        val nameTextResId = locationViewModel.currentNameText

        nameTextView.setText(nameTextResId)
    }


    private fun showToast(){
        when {
            locationViewModel.isFirstPlace(nextIndex) -> Toast.makeText(
                this,
                R.string.beg_toast,
                Toast.LENGTH_SHORT
            ).show()
            locationViewModel.isFirstPlace(prevIndex) -> Toast.makeText(
                this,
                R.string.beg_toast,
                Toast.LENGTH_SHORT
            ).show()
            locationViewModel.isLastPlace(nextIndex) -> Toast.makeText(
                this,
                R.string.end_toast,
                Toast.LENGTH_SHORT
            ).show()
            locationViewModel.isLastPlace(prevIndex) -> Toast.makeText(
                this,
                R.string.end_toast,
                Toast.LENGTH_SHORT
            ).show()
        }


    }
}
