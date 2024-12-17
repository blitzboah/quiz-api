import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [userId, setUserId] = useState('');
  const [isUserValid, setIsUserValid] = useState(false);
  const [quizStarted, setQuizStarted] = useState(false);
  const [question, setQuestion] = useState(null);
  const [selectedOption, setSelectedOption] = useState('');
  const [questionCount, setQuestionCount] = useState(0);
  const [results, setResults] = useState([]);

  const MAX_QUESTIONS = 5;

  const validateUser = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/validate-user?userId=${userId}`
      );
      if (response.status === 200) {
        setIsUserValid(true);
      } else {
        alert('User does not exist. Please enter a valid User ID.');
      }
    } catch (error) {
      console.error('Error validating user:', error);
      alert('Error connecting to server.');
    }
  };

  const startQuiz = async () => {
    if (!isUserValid) return;
    setQuizStarted(true);
    fetchNextQuestion();
  };

  const fetchNextQuestion = async () => {
    setSelectedOption('');
    try {
      const response = await axios.get('http://localhost:8080/api/question');
      if (response.status === 200) {
        setQuestion(response.data);
      }
    } catch (error) {
      console.error('Error fetching question:', error);
    }
  };

  const resetQuizData = async () => {
    try {
      const response = await axios.delete(
        `http://localhost:8080/api/delete?userId=${userId}`
      );
      if (response.status === 200) {
        setResults([]);
        setIsUserValid(false);
        setQuizStarted(false);
        setQuestion(null);
        setSelectedOption('');
        setQuestionCount(0);
        setUserId('');
      }
    } catch (error) {
      console.error('Error resetting quiz data:', error);
      alert('Failed to reset quiz data');
    }
  };

  const submitAnswer = async () => {
    if (!selectedOption) return;

    // Map option to the actual answer text
    let selectedAnswer;
    switch (selectedOption) {
      case 'A':
        selectedAnswer = question.optionA;
        break;
      case 'B':
        selectedAnswer = question.optionB;
        break;
      case 'C':
        selectedAnswer = question.optionC;
        break;
      case 'D':
        selectedAnswer = question.optionD;
        break;
      default:
        selectedAnswer = ''; // Fallback in case of invalid option
    }

    try {
      const response = await axios.post(
        `http://localhost:8080/api/submit`,
        {},
        {
          params: {
            userId: userId,
            questionId: question.questionId,
            answer: selectedAnswer, // Send the actual answer text
          },
        }
      );

      if (response.status === 200) {
        setQuestionCount((prevCount) => {
          const newCount = prevCount + 1;

          if (newCount >= MAX_QUESTIONS) {
            fetchResults();
            setQuizStarted(false);
          } else {
            fetchNextQuestion();
          }

          return newCount;
        });
      } else {
        console.error('Error submitting answer');
      }
    } catch (error) {
      console.error('Error submitting answer:', error);
    }
  };


  const fetchResults = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/results?userId=${userId}`
      );
      if (response.status === 200) {
        setResults(response.data);
        setQuizStarted(false);
        console.log(response.data);

      }
    } catch (error) {
      console.error('Error fetching results:', error);
    }
  };

  const handleOptionSelect = (option) => {
    setSelectedOption(option);
  };

  return (
    <div className="App">
      <header className="App-header">
        {!quizStarted && !isUserValid && (
          <div className="user-form">
            <h1>Welcome to the Quiz</h1>
            <p>Please enter your User ID to start the quiz: (enter 1)</p>
            <input
              type="text"
              placeholder="Enter User ID"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
            />
            <button onClick={validateUser}>Validate User</button>
          </div>
        )}

        {!quizStarted && isUserValid && !results.length && (
          <div>
            <h2>User ID Verified!</h2>
            <button onClick={startQuiz}>Start Quiz</button>
          </div>
        )}

        {quizStarted && question && (
          <div className="question-container">
            <h2>{question.question}</h2>
            <div className="options">
              <button
                className={`option-button ${selectedOption === 'A' ? 'selected' : ''}`}
                onClick={() => handleOptionSelect('A')}
              >
                {question.optionA}
              </button>
              <button
                className={`option-button ${selectedOption === 'B' ? 'selected' : ''}`}
                onClick={() => handleOptionSelect('B')}
              >
                {question.optionB}
              </button>
              <button
                className={`option-button ${selectedOption === 'C' ? 'selected' : ''}`}
                onClick={() => handleOptionSelect('C')}
              >
                {question.optionC}
              </button>
              <button
                className={`option-button ${selectedOption === 'D' ? 'selected' : ''}`}
                onClick={() => handleOptionSelect('D')}
              >
                {question.optionD}
              </button>
            </div>
            <button
              className="submit-button"
              onClick={submitAnswer}
              disabled={!selectedOption}
            >
              Submit Answer
            </button>
          </div>
        )}

        {results.length > 0 && (
          <div className="results-container">
            <h2>Quiz Results</h2>
            <p>Total Questions Answered: {results.length}</p>
            <ul>
              {results.map((result, index) => (
                <li key={index}>
                  <div><strong>Question:</strong> {result.questionText}</div>
                  <div><strong>Your Answer:</strong> {result.userAnswer}</div>
                  <div><strong>correct?:</strong> {result.correctAnswer ? 'Correct' : 'Incorrect'}</div>
                  <br></br>
                </li>
              ))}
            </ul>
            <button onClick={resetQuizData}>Reset Quiz</button>
          </div>
        )}
      </header>
    </div>
  );
}

export default App;
