package org.example.client_service.service;

import org.example.client_service.model.Client;
import org.example.client_service.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    public Client save (Client client){
        if(clientRepository.getByEmail(client.getEmail()) != null){
            int id = clientRepository.getByEmail(client.getEmail()).getId();
            client.setId(id);
        }
        return clientRepository.save(client);
    }
    public Client getById(Integer integer){
        return clientRepository.findById(integer).get();
    }
    public Client getByEmail(String email){
        return clientRepository.getByEmail(email);
    }
}
