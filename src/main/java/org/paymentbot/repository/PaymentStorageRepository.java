package org.paymentbot.repository;

import org.paymentbot.model.PaymentStorage;
import org.paymentbot.model.UserPaymentDTO;
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

//    List<PaymentStorage> findAllByUserStorage(UserStorage userStorage);
    @Query("SELECT ps FROM usersPaymentTable ps WHERE ps.userStorage =:userStorage")
    List<PaymentStorage> findAllByUserStorage(UserStorage userStorage);
    List<PaymentStorage> findAllByUserStorage_ChatId(Long chatId);
//    @Query(value = "SELECT NEW org.paymentbot.model.UserPaymentDTO(dt.chatId, dt.firstName, ps.value, ps.date) FROM usersPaymentTable ps JOIN usersDataTable dt ON dt.chatId = ps.userStorage.chatId WHERE dt.chatId =:chatId")
//    List<UserPaymentDTO> findAllUserPaymentByChatId(Long chatId);

//    @Query(value = "SELECT NEW org.paymentbot.model.UserPaymentDTO" +
//            "(dt.firstName, (SELECT ps " +
//            "FROM usersPaymentTable ps " +
//            "WHERE ps.userStorage.chatId =:chatId)) " +
//            "FROM usersDataTable dt WHERE dt.chatId =:chatId")
//    UserPaymentDTO findAllPaymentsUserByChatId(@Param("chatId") Long chatId);

//    @Query(value = "SELECT new org.paymentbot.model.UserPaymentDTO" +
//            "(dt.firstName, ps.idPayment, ps.userStorage.chatId, ps.value, ps.date) " +
//            "FROM usersDataTable dt " +
//            "JOIN dt.paymentStorageList ps " +
//            "WHERE dt.chatId = :chatId AND ps.userStorage.chatId = :chatId")
//            UserPaymentDTO findAllPaymentsUserByChatId(@Param("chatId") Long chatId);

//    @Query(value = "SELECT NEW org.paymentbot.model.UserPaymentDTO(dt.chatId, dt.firstName, ps.value,ps.date) FROM usersDataTable dt LEFT JOIN usersPaymentTable ps ON dt.chatId = ps.userStorage.chatId")
//    List<UserPaymentDTO> findAllUserPaymentByChatId();


    /**
     * GTP
     */

    @Query(value = "SELECT dt.firstName, ps " +
            "FROM usersDataTable dt " +
            "LEFT JOIN usersPaymentTable ps ON dt.chatId = ps.userStorage.chatId " +
            "WHERE dt.chatId =:chatId")
    List<Object[]> findAllPaymentsUserByChatId(@Param("chatId") long chatId);


}
