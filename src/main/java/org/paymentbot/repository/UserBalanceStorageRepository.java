package org.paymentbot.repository;

import org.paymentbot.model.UserBalanceStorage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserBalanceStorageRepository extends CrudRepository<UserBalanceStorage, Long> {

    @Override
    @Query(value = "SELECT ub FROM userBalanceTable ub WHERE ub.userStorage.chatId =:userChatId")
    Optional<UserBalanceStorage> findById(Long userChatId);

    @Override
    <S extends UserBalanceStorage> S save(S entity);

    @Override
    <S extends UserBalanceStorage> Iterable<S> saveAll(Iterable<S> entities);
    @Transactional
    @Modifying
    @Query(value = "UPDATE userBalanceTable ub " +
            "SET ub.userBalance =:balance " +
            "WHERE ub.userStorage.chatId =:chatId")
    void updateBalance (@Param("chatId") Long chatId, @Param("balance") Double balance);

}
