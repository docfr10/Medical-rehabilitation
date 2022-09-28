package com.example.medicalrehabilitation.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.medicalrehabilitation.R

class BlankFragmentHomeViewModel(application: Application) : AndroidViewModel(application) {
    fun aboutButtonClicked() {
        val context: Context = getApplication()
        AlertDialog.Builder(context)
            .setMessage(R.string.about_app)
            .setTitle((R.string.about))
            .setPositiveButton((R.string.clear)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }
}