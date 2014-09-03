package com.changhong.client.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.system.domain.AppDescription;
import com.changhong.system.domain.Examination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午5:42
 */
@Repository("clientDao")
public class ClientDaoImpl extends HibernateEntityObjectDao implements ClientDao {

    private static final Log logger = LogFactory.getLog(ClientDaoImpl.class);

    public AppDescription loadAppDescription() {
        return getHibernateTemplate().get(AppDescription.class, 1);
    }

    public List<Examination> loadExaminationCategories() {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery("from Examination e where e.published = true order by e.timestamp desc");

        List<Examination> examinations = query.list();
        return examinations;
    }

    public Examination loadLatestExamination(int examinationId) {
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery("from Examination e where e.published = true and e.id = " + examinationId);
        query.setMaxResults(1);

        List<Examination> examinations = query.list();
        if (examinations.isEmpty()) {
            return null;
        }
        return examinations.get(0);
    }

    /**
     * 用户答案处理步骤：
     *
     * 1 - 判断该用户在client_result里面是否已提交同一试卷的答案
     * 2 - 如果1为false, 先向client_result里面插入一条数据
     * 3 - 如果1为false, 查看result_info是否有该用户，如果有的话，更新积分，如果没的话，添加用户并更新积分（积分每次添加100）
     * 4 - 如果1为true, 则不处理result_info的数据
     * 5 - 把每个答案的结果处理到每个答案中
     */
    public void handleUserResult(int examinationId, String mac, String answers) {
        long start = System.currentTimeMillis();
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();

        //第一步
        SQLQuery query = session.createSQLQuery("select id from client_result where mac_address = '" + mac + "' and examination_id= " + examinationId);
        List submitResults = query.list();
        if (submitResults.isEmpty()) {

            //第二步
            SQLQuery insert = session.createSQLQuery("insert client_result(mac_address, result, examination_id) values ('" + mac + "', '" + answers + "', " + examinationId + ")");
            insert.executeUpdate();

            //第三步
            SQLQuery query1 = session.createSQLQuery("select id from client_info where mac_address = '" + mac + "'");
            if (query1.list().isEmpty()) {
                SQLQuery insert1 = session.createSQLQuery("insert client_info(mac_address, experience) values ('" + mac + "', 100)");
                insert1.executeUpdate();
            } else {
                SQLQuery update1 = session.createSQLQuery("update client_info set experience = experience + 100 where mac_address = '" + mac + "'");
                update1.executeUpdate();
            }
        } else {
           //第二步
           SQLQuery insert = session.createSQLQuery("insert client_result(mac_address, result, examination_id) values ('" + mac + "', '" + answers + "', " + examinationId + ")");
           insert.executeUpdate();

            //第四步，不处理
        }

        //第五步
        Examination examination = (Examination) getHibernateTemplate().find("from Examination e where e.id = " + examinationId).get(0);
        examination.handleClientResultStatistic(answers);

        long end = System.currentTimeMillis();
        long during = end - start;
        logger.info("client " + mac + " handle result " + during + "ms");
    }
}
