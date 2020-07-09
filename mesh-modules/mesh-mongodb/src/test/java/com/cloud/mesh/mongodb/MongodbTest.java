/*
 *  Copyright 2019 https://github.com/romeoblog/spring-cloud.git Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cloud.mesh.mongodb;

import com.cloud.mesh.mongodb.dao.PersonRepository;
import com.cloud.mesh.mongodb.entity.PersonDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MongodbTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void insertTest() {
        PersonDTO p = new PersonDTO("5", "Eric", 34);

        // Insert is used to initially store the object into the database.
        personRepository.insert(p);
        log.info("Insert: " + p);
    }

    @Test
    public void findTest() {
        // Find
        PersonDTO p = personRepository.findById("4").orElse(null);
        log.info("Found: " + p);
    }

    @Test
    public void updateTest() {
        // Update
        mongoTemplate.updateFirst(Query.query(where("name").is("Joe")), update("age", 35), PersonDTO.class);
        PersonDTO p = mongoTemplate.findOne(query(where("name").is("Joe")), PersonDTO.class);

        log.info("Updated: " + p);
    }

    @Test
    public void deleteTest() {
        // Delete
        PersonDTO p = mongoTemplate.findOne(query(where("name").is("Joe")), PersonDTO.class);

        mongoTemplate.remove(p);
    }

    @Test
    public void findAllTest() {

        List<PersonDTO> people = mongoTemplate.findAll(PersonDTO.class);
        log.info("Number of people = : " + people.size());

    }

    @Test
    public void findByNameLikeTest () {
        List<PersonDTO> joe = personRepository.findByNameLike("Joe");

        log.info("Found: " + joe);
    }

    @Test
    public void findByNameLikePageTest () {
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<PersonDTO> joe = personRepository.findByNameLike("Joe", pageRequest);

        log.info("Found: " + joe.getTotalPages());
    }

    @Test
    public void criteriaTest () {
        // add additional criteria to the query
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Eric"));
        List<PersonDTO> result = mongoTemplate.find(query, PersonDTO.class);

        log.info("Found: " + result);
    }

    @Test
    public void criteriaRegexTest () {
        // Creates a criterion using a {@literal $regex} operator.
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^E"));
        List<PersonDTO> result = mongoTemplate.find(query, PersonDTO.class);

        log.info("Found: " + result);

    }

}