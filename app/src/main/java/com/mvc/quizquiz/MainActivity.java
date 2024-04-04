package com.mvc.quizquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.mvc.quizquiz.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;
    private int currentIndex;
    private int highestScore;
    private TrackScore score;
    private SharedPrefs prefs;
    private int currentScore;

    List<Questions> questionsList;
    public int questionsSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        prefs = new SharedPrefs(MainActivity.this);
        currentIndex = prefs.getSavedIndex();

        currentScore = prefs.getCurrentScore();

        highestScore = prefs.getHighestScore();
        score = new TrackScore();
        score.setScore(currentScore);


        questionsList = new Data().getQuestions(new AsyncResponse() {
            @Override
            public void questionsGotten(List<Questions> questions) {
                questionsSize = questions.size();
                binding.questionText.setText(questions.get(currentIndex).getQuestion());

                binding.questionNo.setText(String.format("Question: %d/%d", currentIndex + 1, questionsSize));
                binding.highestScoreText.setText("Highest: " + highestScore);
                binding.currentScoreText.setText(String.valueOf("Score: " + score.getScore()));

                //Log.d("TAG", "questionsGotten: " + questionsList);
                if (questionsSize <= 0) {
                    Snackbar.make(binding.cardView, "There is a problem in Loading Questions.", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }

        });

        binding.trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        binding.falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

            }
        });
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionsSize > 0) {
                    incrementIndex();
                    questionUpdate();
                }
            }
        });

    }

    private void checkAnswer(boolean userInput) {
        if (userInput == questionsList.get(currentIndex).isAns()) {
            questionUpdate(); // This make animation happen.
            fadeAnim();
            Snackbar.make(binding.cardView, R.string.correct, Snackbar.LENGTH_SHORT)
                    .show();
            increaseScore();
        } else {
            questionUpdate(); // This make animation happen.
            shakeAnim();
            Snackbar.make(binding.cardView, R.string.incorrect, Snackbar.LENGTH_SHORT)
                    .show();
            decreaseScore();
        }
        disableButtons();


    }

    private void incrementIndex() {
        currentIndex = (currentIndex + 1) % questionsSize;
    }

    private void decreaseScore() {
        if (currentScore > 0){
            currentScore -= 100;
            score.setScore(currentScore);
            binding.currentScoreText.setText("Score: " + score.getScore());
        }
    }

    private void increaseScore() {
        currentScore += 100;
        score.setScore(currentScore);
        prefs.setHighestScore(currentScore);
        highestScore = prefs.getHighestScore();
        binding.highestScoreText.setText("Highest: " + highestScore);
        binding.currentScoreText.setText(String.valueOf("Score: " + score.getScore()));
    }

    private void questionUpdate() {
        String question = questionsList.get(currentIndex).getQuestion();
        binding.questionText.setText(question);
        binding.questionNo.setText(String.format("Question: %d/%d", currentIndex + 1, questionsSize));
        enableButtons();
    }

    private void fadeAnim() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionText.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionText.setTextColor(Color.WHITE);
                incrementIndex();
                questionUpdate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnim() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);

        binding.cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionText.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionText.setTextColor(Color.WHITE);
                incrementIndex();
                questionUpdate();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void disableButtons(){
        binding.trueBtn.setEnabled(false);
        binding.falseBtn.setEnabled(false);
    }
    private void enableButtons(){
        binding.trueBtn.setEnabled(true);
        binding.falseBtn.setEnabled(true);
    }
    @Override
    protected void onPause() {
        prefs.setCurrentScore(currentScore);
        prefs.setSavedIndex(currentIndex);
        super.onPause();
    }
}