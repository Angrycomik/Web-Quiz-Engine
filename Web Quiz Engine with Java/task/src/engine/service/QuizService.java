package engine.service;

import engine.converter.converters;
import engine.dto.QuizGetDTO;
import engine.dto.QuizPostDTO;
import engine.entity.Quiz;
import engine.repository.QuizRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    Logger logger = LoggerFactory.getLogger(QuizService.class);
    @Autowired
    QuizRepo repository;

    public QuizGetDTO getQuizDTO(Long id){
        Optional<Quiz> quiz = repository.findById(id);
        if(quiz.isPresent()){
            return converters.convertQuizToQuizGetDTO(quiz.get());
        }
        return null;
    }
    public Quiz getQuiz(Long id){
        Optional<Quiz> quiz = repository.findById(id);
        if(quiz.isPresent()){
            return quiz.get();
        }
        return null;
    }
    public List<QuizGetDTO> getAllQuizes(){
        List<Quiz> quizzes = repository.findAll();
        return quizzes.stream().map(converters::convertQuizToQuizGetDTO).toList();
    }
    public Quiz saveQuiz(QuizPostDTO quizPostDTO){
        logger.info("Saving Quiz: " + quizPostDTO.toString());
        Quiz quiz = new Quiz(quizPostDTO.title(),quizPostDTO.text(),quizPostDTO.options(),quizPostDTO.answer());
        return repository.save(quiz);
    }

}
