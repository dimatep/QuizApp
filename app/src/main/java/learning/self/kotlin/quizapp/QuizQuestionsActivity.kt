package learning.self.kotlin.quizapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import org.w3c.dom.Text

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition:Int = 1
    private var mQuestionsList:ArrayList<Question>? = null
    private var mSelectedOptionPosition:Int = 0
    private var mCorrectAnswers:Int = 0
    private var mUserName:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME) //dont need it here, pass it to result activity

        mQuestionsList = Constants.getQuestions()
        setQuestion()

        option_one_tv.setOnClickListener(this)
        option_two_tv.setOnClickListener(this)
        option_three_tv.setOnClickListener(this)
        option_four_tv.setOnClickListener(this)
        submit_btn.setOnClickListener(this)

    }

    private fun setQuestion(){
        val question = mQuestionsList!![mCurrentPosition - 1] //starts from 0

        defaultOptionsView()

        if(mCurrentPosition == mQuestionsList!!.size){ //set finish only on the final question
            submit_btn.text = "FINISH"
        }else{
            submit_btn.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        progress_tv.text = "$mCurrentPosition" + "/" + "${mQuestionsList!!.size}"

        question_tv.text = question!!.question
        image_iv.setImageResource(question.image)
        option_one_tv.text = question.optionOne
        option_two_tv.text = question.optionTwo
        option_three_tv.text = question.optionThree
        option_four_tv.text = question.optionFour
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()

        options.add(0,option_one_tv)
        options.add(1,option_two_tv)
        options.add(2,option_three_tv)
        options.add(3,option_four_tv)

        for(op in options){ // set the default options view to all options
            op.setTextColor(Color.parseColor("#7A8089"))
            op.typeface = Typeface.DEFAULT
            op.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.option_one_tv -> selectedOptionView(option_one_tv,1)
            R.id.option_two_tv -> selectedOptionView(option_two_tv ,2)
            R.id.option_three_tv -> selectedOptionView(option_three_tv,3)
            R.id.option_four_tv -> selectedOptionView(option_four_tv,4)
            R.id.submit_btn -> {
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            setQuestion()
                        }else->{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName)
                            intent.putExtra(Constants.USER_SCORE, mCorrectAnswers)
                            intent.putExtra(Constants.TOATAL_QUESTIONS,mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionsList!!.size){
                        submit_btn.text = "FINISH"
                    }else{
                        submit_btn.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer:Int, drawableView: Int){
        when(answer){
            1->option_one_tv.background = ContextCompat.getDrawable(this,drawableView)
            2->option_two_tv.background = ContextCompat.getDrawable(this,drawableView)
            3->option_three_tv.background = ContextCompat.getDrawable(this,drawableView)
            4->option_four_tv.background = ContextCompat.getDrawable(this,drawableView)
        }

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNumber

        //set the text to bold black with another background
        tv.setTextColor(Color.BLACK)
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)
    }


}
