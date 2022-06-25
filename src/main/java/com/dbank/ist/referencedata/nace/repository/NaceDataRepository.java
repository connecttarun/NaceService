package com.dbank.ist.referencedata.nace.repository;

import com.dbank.ist.referencedata.nace.entity.Nace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaceDataRepository extends CrudRepository<Nace, Integer> {

}
