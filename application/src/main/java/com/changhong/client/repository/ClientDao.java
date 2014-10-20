package com.changhong.client.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.AppDescription;
import com.changhong.system.domain.Examination;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-7-21
 * Time: 下午5:42
 */
public interface ClientDao extends EntityObjectDao {

    AppDescription loadAppDescription();

    List<Examination> loadExaminationCategories(String examinationType);

    Examination loadLatestExamination(int examinationId);

    void handleUserResult(int examinationId, String mac, String answers);
}
