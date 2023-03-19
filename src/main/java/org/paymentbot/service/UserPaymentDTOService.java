package org.paymentbot.service;

import org.paymentbot.model.UserPaymentDTO;
import org.paymentbot.repository.UserPaymentDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPaymentDTOService {
    @Autowired
    private UserPaymentDtoRepository userPaymentDTORepository;

    public List<UserPaymentDTO> findAllUserPaymentByChatId(){
        return userPaymentDTORepository.findAllUserPaymentByChatId();
    }
}
