package com.project.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.entities.UserInfo;

@Repository
@Transactional
public interface InfoRepository extends CrudRepository<UserInfo,Integer>{

}
