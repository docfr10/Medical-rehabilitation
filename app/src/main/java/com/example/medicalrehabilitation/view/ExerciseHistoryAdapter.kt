package com.example.medicalrehabilitation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentExerciseHistoryBinding
import com.example.medicalrehabilitation.exercisehistory.ExerciseHistoryModel

class ExerciseHistoryAdapter : RecyclerView.Adapter<ExerciseHistoryAdapter.MyViewHolder>() {

    private var dateList = emptyList<ExerciseHistoryModel>()

    class MyViewHolder(val binding: FragmentBlankFragmentExerciseHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            binding = FragmentBlankFragmentExerciseHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dateList[position]
        holder.binding.idTxt.text = currentItem.id.toString()
        holder.binding.dateOfExercise.text = currentItem.dateOfExercise
        holder.binding.howWasExercise.text = currentItem.howWasExercise
        holder.binding.howWasPainful.text = currentItem.howWasPainful
        holder.binding.failedExercises.text = currentItem.failedExercises
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(exercise: List<ExerciseHistoryModel>) {
        this.dateList = exercise
        notifyDataSetChanged()
    }
}