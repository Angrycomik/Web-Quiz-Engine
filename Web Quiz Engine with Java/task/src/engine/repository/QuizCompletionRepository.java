package engine.repository;

import engine.entity.AppUser;
import engine.entity.QuizCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizCompletionRepository extends JpaRepository<QuizCompletion, Long> {
    Page<QuizCompletion> findAllByUser(AppUser user, PageRequest pageRequest);
}
