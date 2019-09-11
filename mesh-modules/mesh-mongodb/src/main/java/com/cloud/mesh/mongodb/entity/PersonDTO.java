package com.cloud.mesh.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * PersonDTO DEMO
 *
 * @author willlu.zheng
 * @date 2019-09-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "mongodb-person")
public class PersonDTO {

    private String id;

    private String name;

    private Integer age;

}
