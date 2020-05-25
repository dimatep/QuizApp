package learning.self.kotlin.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN //Remove the status bar on app

        val userName : String = intent.getStringExtra(Constants.USER_NAME)
        name_tv.text = userName

        val totalQuestions = intent.getIntExtra(Constants.TOATAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.USER_SCORE, 0)

        score_tv.text = "Your score is $correctAnswers out of $totalQuestions"

        //go back to main activity
        finish_btn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
