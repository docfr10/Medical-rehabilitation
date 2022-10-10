package com.example.medicalrehabilitation.exercisehistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalrehabilitation.databinding.FragmentExerciseHistoryBinding

//Класс, обноваляющий RecyclerView
class ExerciseHistoryAdapter :
    ListAdapter<ExerciseHistoryModel, ExerciseHistoryAdapter.MyViewHolder>(ExerciseDiffCallBack()) {

    private var dateList = emptyList<ExerciseHistoryModel>()

    class MyViewHolder(val binding: FragmentExerciseHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            binding = FragmentExerciseHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dateList[position]
        holder.binding.idTxt.text = currentItem.id.toString()
        holder.binding.dateOfExercise.text = currentItem.dateOfExercise
        holder.binding.howWasExercise.text = currentItem.howWasExercise
        //todo Реализовать отображение списка невыполненых упражнений и того на сколько было больно
        //holder.binding.howWasPainful.text = currentItem.howWasPainful
        //holder.binding.failedExercises.text = currentItem.failedExercises
    }

    override fun getItemCount(): Int {
        return dateList.size
    }
}

//Класс, проверяющий изменения в позициях
class ExerciseDiffCallBack : DiffUtil.ItemCallback<ExerciseHistoryModel>() {
    override fun areItemsTheSame(
        oldItem: ExerciseHistoryModel,
        newItem: ExerciseHistoryModel
    ): Boolean {
        return ((oldItem.id == newItem.id)
                && (oldItem.dateOfExercise == newItem.dateOfExercise)
                && (oldItem.howWasExercise == newItem.howWasExercise))
    }

    override fun areContentsTheSame(
        oldItem: ExerciseHistoryModel,
        newItem: ExerciseHistoryModel
    ): Boolean {
        return oldItem == newItem
    }
}
