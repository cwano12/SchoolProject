package com.wano.repository;

import com.wano.models.TreatmentCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Chris on 2/11/2018.
 */
public interface TreatmentCenterRepository extends JpaRepository<TreatmentCenter, Integer> {

    List<TreatmentCenter> findByZip(String zip);
}
