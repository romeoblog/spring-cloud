package com.cloud.mesh.mongodb.dao;

import com.cloud.mesh.mongodb.entity.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Declare query methods on the interface.
 *
 * @author willlu.zheng
 * @date 2019-09-06
 */
public interface PersonRepository extends MongoRepository<PersonDTO, String> {

    List<PersonDTO> findByNameLike(String name);

    Page<PersonDTO> findByNameLike(String name, Pageable pageable);

}