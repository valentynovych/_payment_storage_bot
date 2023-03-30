package org.paymentbot.repository;

import org.paymentbot.model.UserStorage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStorageRepository extends CrudRepository<UserStorage, Long> {
    @Query(value ="SELECT chatId FROM usersDataTable")
    List<Long> findAllChatIdUser();
}
