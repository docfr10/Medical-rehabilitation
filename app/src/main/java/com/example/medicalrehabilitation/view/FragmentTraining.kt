package com.example.medicalrehabilitation.view

import android.app.AlertDialog
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentTrainingBinding
import com.example.medicalrehabilitation.viewmodel.FragmentTrainingViewModel


class FragmentTraining : Fragment() {
    private lateinit var soundOfStop: MediaPlayer //Звук, оповещающий об окончании упражнения
    private lateinit var mediaController: MediaController //Элементы управления видео(пауза, перемотка)

    private var isPause = false

    private lateinit var viewModel: FragmentTrainingViewModel
    private lateinit var binding: FragmentTrainingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[FragmentTrainingViewModel::class.java]

        val recommendations: Array<String> = resources.getStringArray(R.array.recommendations)
        binding.recommendationsTextView.text = viewModel.getRecommendations(recommendations)
        binding.exerciseTextView.text =
            getText(R.string.description1) //Текстовое поле, отображающее информацию об упражнении
        soundOfStop = MediaPlayer.create(context, R.raw.sound_stop)
        mediaController = MediaController(context)
        binding.trainingVideoView.setBackgroundColor(Color.TRANSPARENT) //Отображение видеофайла, который выбран в Uri

        //Обработка Back Stack
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (viewModel.touchCounter.value == 1)
                    findNavController().navigate(R.id.action_blankFragmentTraining_to_blankFragmentHome)
                else {
                    val toast =
                        Toast.makeText(context, R.string.return_to_main_menu, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.BOTTOM, 0, 0)
                    toast.show()
                    viewModel.timerForTouch()
                    viewModel.touchCounter.value = 1
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.pauseButton.setOnClickListener { pauseButtonClicked() } //Кнопка "Пауза" ставит таймер и проигрываемое видео на паузу
        binding.nextButton.setOnClickListener { nextButtonClicked() } //Кнопка "Следующее упражнение" переключает упражнение
        binding.abouttrainImageButton.setOnClickListener { aboutTrainImageButtonClicked() } //Кнопка с изображением "Об упражнении"
        binding.plus30secButton.setOnClickListener { plus30Sec() } //Кнопка "+ 30 сек"
        binding.nextButton2.setOnClickListener {
            //При нажатии кнопки "Далее" на экране отдыха проверяем:
            if (viewModel.counterNumberOfTraining.value == 4)
            //Если это последнее упражнение запускаем NextTrainingFragment
                nextTraining()
            else
            //Иначе запускаем экран отдыха
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

    private fun videoPlay(binding: FragmentTrainingBinding) {
        viewModel.videoPlay(binding)
    }

    private fun videoPause(binding: FragmentTrainingBinding) {
        viewModel.videoPause(binding)
    }

    private fun timerResume(
        isRestTimer: Boolean,
        binding: FragmentTrainingBinding,
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

    //Вызов экрана отдыха
    private fun rest() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.push_down_in)
        binding.linerLayoutBlankRest.animation = animation
        binding.linerLayoutBlankRest.animate()
        animation.start()

        binding.linerLayoutBlankRest.isVisible = true
        binding.nextButton.isVisible = false
        binding.pauseButton.isVisible = false
        timerResume(true, binding, soundOfStop)
    }

    private fun plus30Sec() {
        viewModel.plus30Sec(binding, soundOfStop)
    }

    private fun nextButtonClickedOnRest() {
        binding.linerLayoutBlankRest.isVisible = false
        binding.nextButton.isVisible = true
        videoChange()

        val animation = AnimationUtils.loadAnimation(context, R.anim.push_up_out)
        binding.linerLayoutBlankRest.animation = animation
        binding.linerLayoutBlankRest.animate()
        animation.start()
    }

    //Вызов NextTrainingFragment
    private fun nextTraining() {
        findNavController().navigate(R.id.action_blankFragmentTraining_to_blankFragmentNextTraining)
    }
}