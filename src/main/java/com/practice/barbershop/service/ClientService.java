package com.practice.barbershop.service;

import com.practice.barbershop.dto.ClientDto;
import com.practice.barbershop.general.MyService;
import com.practice.barbershop.mapper.ClientMapper;
import com.practice.barbershop.model.Client;
import com.practice.barbershop.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is service class for maintain the client entity
 */
@Service
@AllArgsConstructor
public class ClientService implements MyService<ClientDto, Client> {

    private final ClientRepository clientRepository;

    /**
     * Return Client entity by his id
     * @param id entity id
     * @return Client entity
     */
    @Override
    public ClientDto getDtoById(Long id) {
        return ClientMapper.toDto(clientRepository.getClientById(id)
                .orElseThrow(() -> new RuntimeException("Client with id=" + id + " not found.")));
    }

    /**
     * Return Client entity by his id
     * @param id entity id
     * @return Client entity
     */
    @Override
    public Client getEntityById(Long id) {
        return clientRepository.getClientById(id)
                .orElseThrow(() -> new RuntimeException("Client with id=" + id + " not found."));
    }

    /**
     * Save or change entity in db
     * @param client entity object
     */
    @Override
    @Transactional
    public ClientDto save(ClientDto client) {
        client.setId(null);
        Client savedClient = clientRepository.save(ClientMapper.toEntity(client));
        return ClientMapper.toDto(savedClient);
    }

    @Override
    @Transactional
    public ClientDto update(ClientDto dto) {
        return ClientMapper.toDto(clientRepository.save(ClientMapper.toEntity(dto)));
    }

    @Override
    public List<ClientDto> getAllDto() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(ClientMapper::toDto)
                .collect(Collectors.toList());
    }
}
