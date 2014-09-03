package com.changhong.system.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.Examination;
import com.changhong.system.domain.Question;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 上午9:59
 */
@Repository("faqDao")
public class FaqDaoImpl extends HibernateEntityObjectDao implements FaqDao {

    public List<Examination> loadAllExamination() {
        return getHibernateTemplate().find("from Examination");
    }

    public List<Examination> loadExaminations(String filterTitle, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("from Examination e ");
        if (StringUtils.hasText(filterTitle)) {
            builder.append("where e.title like '" + filterTitle + "%' ");
        }
        builder.append("order by e.id desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<Examination> examinations = query.list();
        return examinations;
    }

    public int loadExaminationSize(String filterTitle) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(e.id) from Examination e ");
        if (StringUtils.hasText(filterTitle)) {
            builder.append("where e.title like '" + filterTitle + "%'");
        }
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }

    public List<Question> loadQuestionsByExaminationId(int examinationId, int sequence) {
        StringBuilder builder = new StringBuilder();
        builder.append("from Question q where q.examination.id = " + examinationId + " ");
        if (sequence > 0) {
            builder.append("and q.sequence > " + sequence + " ");
        }
        builder.append("order by q.sequence asc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        List<Question> questions = query.list();
        return questions;
    }

    public Question loadQuestionBySequence(int examinationId, int sequence) {
        return (Question) getHibernateTemplate().find("from Question q where q.examination.id = " + examinationId + " and q.sequence = " + sequence).get(0);
    }
}
