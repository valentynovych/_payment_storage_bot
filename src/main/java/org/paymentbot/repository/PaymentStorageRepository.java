package org.paymentbot.repository;

import org.paymentbot.model.PaymentStorage;
import org.paymentbot.model.UserPaymentsDTO;
import org.paymentbot.model.UserStorage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PaymentStorageRepository extends CrudRepository<PaymentStorage, Long> {

    @Override
    Optional<PaymentStorage> findById(Long id);

    @Query("SELECT ps FROM usersPaymentTable ps WHERE ps.userStorage =:userStorage")
    List<PaymentStorage> findAllByUserStorage(UserStorage userStorage);

    List<PaymentStorage> findAllByUserStorage_ChatId(Long chatId);

     @Query(value = "SELECT new org.paymentbot.model.UserPaymentsDTO" +
           "(udt.firstName, udt.chatId, upt.value, upt.date) " +
           "FROM usersDataTable udt " +
           "LEFT JOIN usersPaymentTable upt ON udt.chatId = upt.userStorage.chatId")
     List<UserPaymentsDTO> findAllPaymentsAllUser();



}
