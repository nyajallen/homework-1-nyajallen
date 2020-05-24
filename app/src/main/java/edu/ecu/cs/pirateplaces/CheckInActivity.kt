package edu.ecu.cs.pirateplaces

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

const val EXTRA_CHECKED_IN = "edu.ecu.cs.pirateplaces.checked_in"
const val EXTRA_CURRENT_INDEX = "edu.ecu.cs.pirateplaces.current_index"

class CheckInActivity : AppCompatActivity() {

    private var currentIndex = 0

    private lateinit var checkinButton: Button
    private lateinit var locationTextView: TextView
    private lateinit var checkInTextView: TextView

    private val locationViewModel: LocationViewModel by lazy {
        ViewModelProviders.of(this).get(LocationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)

        currentIndex = intent.getIntExtra(EXTRA_CURRENT_INDEX, 0)
        checkinButton = findViewById(R.id.checkin_button)
        locationTextView = findViewById(R.id.location_text_view)
        checkInTextView = findViewById(R.id.checkin_text_view)

        updateLocation()

        checkinButton.setOnClickListener{
            updateCheckInText()
            updateCheckInButton()
            setCheckInResult(true)
        }


    }

    companion object {
        fun newIntent(packageContext: Context, currentIndex: Int): Intent {
            return Intent(packageContext, CheckInActivity::class.java).apply {
                putExtra(EXTRA_CURRENT_INDEX, currentIndex)
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, locationViewModel.currentIndex)
    }

    private fun setCheckInResult(isCheckedIn: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_CHECKED_IN, isCheckedIn)
        }
        setResult(Activity.RESULT_OK, data)
    }

    private fun updateLocation(){
        locationViewModel.currentIndex = currentIndex
        val locationResId = locationViewModel.currentLocationText

        locationTextView.setText(locationResId)
    }

    private fun updateCheckInText(){
        checkInTextView.setText(R.string.checkin_text)
    }

    private fun updateCheckInButton(){
        checkinButton.setText(R.string.checkin_text)
    }
}
