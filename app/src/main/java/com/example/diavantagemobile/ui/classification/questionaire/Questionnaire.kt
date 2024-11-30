package com.example.diavantagemobile.ui.classification.questionaire

const val questionBasis = "Have you EVER been told by a doctor, nurse or other health professional that "


object Questionnaire{
    val questions: ArrayList<Question> = ArrayList()

    fun getQuestion(index: Int): Question? {
        return questions.getOrNull(index - 1)
    }

    fun initQuestions(){
        questions.add(
            Question(
                id = 1,
                questionText = questionBasis + "your blood pressure is high?",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 2,
                questionText = questionBasis + "your blood cholesterol is high?",
                answers = arrayListOf("Yes", "No"),
            )
        )

        questions.add(
            Question(
                id = 3,
                questionText = "Have you had your cholesterol checked within past five years?",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 4,
                questionText = "What is your Body Mass Index (BMI)?",
                answers = arrayListOf(),
                args = QuestionArgs(
                    type = "DecimalInput",
                )
            )
        )

        questions.add(
           Question(
               id = 5,
               questionText = "Have you smoked at least 100 cigarettes in your entire life?",
               answers = arrayListOf("Yes", "No")
           )
        )

        questions.add(
            Question(
                id = 6,
                questionText = "(Ever told) you had a stroke?",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 7,
                questionText = questionBasis + "you have coronary heart disease (CHD) or myocardial infarction (MI)",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 8,
                questionText = "Did you perform physical activity or exercise during the past 30 days other than your regular job",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 9,
                questionText = "Do you consume 1 or more fruit per day?",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 10,
                questionText = "Do you consume 1 or more vegetables per day?",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 11,
                questionText = "Do you consume much alcohol?  (adult men having more than 14 drinks per week and adult women having more than 7 drinks per week)",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 12,
                questionText = "Do you have any kind of health care coverage?",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 13,
                questionText = "Was there a time in the past 12 months when you needed to see a doctor but could not because of cost?",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 14,
                questionText = "Would you say that in general your health is:",
                answers = arrayListOf("Excellent", "Very good", "Good", "Fair", "Poor")
            )
        )

        questions.add(
            Question(
                id = 15,
                questionText = " Thinking about your mental health, which includes stress, depression and problems with emotions, for how many days during the past 30 days was your mental health not good?",
                answers = arrayListOf(),
                args = QuestionArgs(
                    type = "SliderInput",
                    range = 0f..30f,
                    steps = 30,
                )
            )
        )

        questions.add(
            Question(
                id = 16,
                questionText = "Now thinking about your physical health, which includes physical illness and injury, for how many days during the past 30 days was your physical health not good?",
                answers = arrayListOf(),
                args = QuestionArgs(
                    type = "SliderInput",
                    range = 0f..30f,
                    steps = 30,
                )
            )
        )

        questions.add(
            Question(
                id = 17,
                questionText = "Do you have serious difficulty walking or climbing stairs?",
                answers = arrayListOf("Yes", "No")
            )
        )

        questions.add(
            Question(
                id = 18,
                questionText = "What is your sex?",
                answers = arrayListOf("Male", "Female")
            )
        )

        questions.add(
            Question(
                id = 19,
                questionText = "How old are you?",
                answers = arrayListOf(
                    "18 to 24",
                    "25 to 29",
                    "30 to 34",
                    "35 to 39",
                    "40 to 44",
                    "45 to 49",
                    "50 to 54",
                    "55 to 59",
                    "60 to 64",
                    "65 to 69",
                    "70 to 74",
                    "75 to 79",
                    "80 or older",
                    )
            )
        )

        questions.add(
            Question(
                id = 20,
                questionText = "What is the highest grade or years of school you completed?",
                answers = arrayListOf(
                    "Never attended school or kindergarten",
                    "Elementary school",
                    "Some high school",
                    "High school graduate",
                    "College 1 to 3 years",
                    "College 4 years or more"
                )
            )
        )

        questions.add(
            Question(
                id = 21,
                questionText = "What is your annual household income from all sources:",
                answers = arrayListOf(
                    "Less than $10 000",
                    "More than $10 000, less than $15 000",
                    "More than $15 000, less than $20 000",
                    "More than $20 000, less than $25 000",
                    "More than $25 000, less than $35 000",
                    "More than $35 000, less than $50 000",
                    "More than $50 000, less than $75 000",
                    "$75 000 or more",
                )
            )
        )

    }
}