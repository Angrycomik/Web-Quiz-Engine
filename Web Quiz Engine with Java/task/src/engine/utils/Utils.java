package engine.utils;

import engine.entity.Quiz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Utils
{
    public static boolean checkAnswer(List<Integer> correctAnswers, List<Integer> answer)
    {
        if(correctAnswers == null || correctAnswers.isEmpty()){
            return answer == null || answer.isEmpty();
        }
        if(correctAnswers.size() < answer.size()){return false;}

        HashSet<Integer> set = new HashSet<>(correctAnswers);
        for(var i : answer){
            if(!set.contains(i)){return false;}
            set.remove(i);
        }
        return set.isEmpty();
    }
}
