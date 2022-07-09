package com.dbank.ist.referencedata.nace.repository;

import com.dbank.ist.referencedata.nace.entity.Nace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NaceDataRepository extends CrudRepository<Nace, Integer> {
    Optional<Nace> findByOrder(int order);
}
