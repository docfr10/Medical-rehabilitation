package com.example.medicalrehabilitation.view

import android.app.AlertDialog
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.medicalrehabilitation.viewmodel.BlankFragmentTrainingViewModel
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentTrainingBinding

class BlankFragmentTraining : Fragment() {
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании упражнения
    private lateinit var mediaController: MediaController //Элементы управления видео(пауза, перемотка)

    private var isPause = false

    private lateinit var viewModel: BlankFragmentTrainingViewModel
    private lateinit var binding: FragmentBlankFragmentTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankFragmentTrainingBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[BlankFragmentTrainingViewModel::class.java]

        binding.exerciseTextView.text =
            getText(R.string.description1) //Текстовое поле, отображающее информацию об упражнении
        soundOfStop = MediaPlayer.create(context, R.raw.sound_stop)
        mediaController = MediaController(context)
        binding.trainingVideoView.setBackgroundColor(Color.TRANSPARENT) //Отображение видеофайла, который выбран в Uri

        val recommendations: Array<String> = resources.getStringArray(R.array.recommendations)
        binding.recommendationsTextView.text = viewModel.getRecommendations(recommendations)

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.pauseButton.setOnClickListener { pauseButtonClicked() } //Кнопка "Пауза" ставит таймер и проигрываемое видео на паузу
        binding.nextButton.setOnClickListener { nextButtonClicked() } //Кнопка "Следующее упражнение" переключает упражнение
        binding.abouttrainImageButton.setOnClickListener { aboutTrainImageButtonClicked() } //Кнопка с изображением "Об упражнении"

        binding.plus30secButton.setOnClickListener { plus30Sec() }
        binding.nextButton2.setOnClickListener {
            if (viewModel.counterNumberOfTraining.value == 0)
                nextTraining()
            else
                nextButtonClickedOnRest()
        }

        timerResume(false, binding, soundOfStop)
        videoPlay(binding)
    }

    override fun onPause() {
        super.onPause()
        timerPause()
        soundPause(soundOfStop)
        videoPause(binding)
    }

    private fun pauseButtonClicked() {
        isPause = if (!isPause) {
            timerPause()
            videoPause(binding)
            binding.pauseButton.setText(R.string.resume)
            true
        } else {
            timerResume(false, binding, soundOfStop)
            videoPlay(binding)
            binding.pauseButton.setText(R.string.pause)
            false
        }
    }

    private fun nextButtonClicked() {
        onPause()
        rest()
        videoChange()
    }

    private fun aboutTrainImageButtonClicked() {
        val builder = AlertDialog.Builder(context)
        aboutExercise(builder)
    }

    private fun videoChange() {
        viewModel.videoChange(binding)
        videoPlay(binding)
        changeDescription()
    }

    private fun changeDescription() {
        viewModel.changeDescription(binding)
    }

    private fun videoPlay(binding: FragmentBlankFragmentTrainingBinding) {
        viewModel.videoPlay(binding)
    }

    private fun videoPause(binding: FragmentBlankFragmentTrainingBinding) {
        viewModel.videoPause(binding)
    }

    private fun timerResume(
        isRestTimer: Boolean,
        binding: FragmentBlankFragmentTrainingBinding,
        soundOfStop: MediaPlayer
    ) {
        viewModel.timerResume(isRestTimer, binding, soundOfStop)
    }

    private fun timerPause() {
        viewModel.timerPause()
    }

    private fun soundPause(soundOfStop: MediaPlayer) {
        viewModel.soundPause(soundOfStop)
    }

    private fun aboutExercise(builder: AlertDialog.Builder) {
        viewModel.aboutExercise(builder)
    }

    //Вызов activity отдыха
    private fun rest() {
        binding.linerLayoutBlankRest.isVisible = true
        binding.nextButton.isVisible = false
        timerResume(true, binding, soundOfStop)
    }

    private fun plus30Sec() {
        viewModel.plus30Sec(binding, soundOfStop)
    }

    private fun nextButtonClickedOnRest() {
        binding.linerLayoutBlankRest.isVisible = false
        binding.nextButton.isVisible = true
    }

    //Вызов activity выбора следующей тренировки
    private fun nextTraining() {
        findNavController().navigate(R.id.action_blankFragmentTraining_to_blankFragmentNextTraining)
    }
}