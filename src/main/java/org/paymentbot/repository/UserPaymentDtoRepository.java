package org.paymentbot.repository;

import org.paymentbot.model.UserPaymentDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPaymentDtoRepository extends CrudRepository<UserPaymentDTO, Long> {

    @Query(value = "SELECT NEW org.paymentbot.model.UserPaymentDTO(dt.chatId, dt.firstName, ps.value,ps.date) FROM usersDataTable dt JOIN usersPaymentTable ps ON dt.chatId = ps.userStorage.chatId")
    List<UserPaymentDTO> findAllUserPaymentByChatId();

}
