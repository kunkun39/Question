package com.changhong.system.repository;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.Examination;
import com.changhong.system.domain.Question;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午9:59
 */
public interface FaqDao extends EntityObjectDao {

    List<Examination> loadAllExamination();

    List<Examination> loadExaminations(String filterTitle, int startPosition, int pageSize);

    int loadExaminationSize(String title);

    List<Question> loadQuestionsByExaminationId(int examinationId, int sequence);

    Question loadQuestionBySequence(int examinationId, int sequence);
}
