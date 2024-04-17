package com.practice.barbershop.mapper;


import com.practice.barbershop.dto.ClientDto;
import com.practice.barbershop.model.Client;

/** Convert ClientDto to Client and Client to ClientDto
 * @author David
 */
public class ClientMapper {
    /**
     * Convert Client to ClientDto
     * @param entity Client
     * @return ClientDto
     */
    public static ClientDto toDto(Client entity) {
        ClientDto client = new ClientDto();

        client.setId(entity.getId());
        client.setName(entity.getName());
        client.setPhone(entity.getPhone());
        client.setLogin(entity.getLogin());
        client.setPassword(entity.getPassword());
        client.setDeleted(entity.getDeleted());

        return client;
    }
    /**
     * Convert ClientDto to Client
     * @param dto ClientDto
     * @return Client
     */
    public static Client toEntity(ClientDto dto) {
        Client client = new Client();

        client.setId(dto.getId());
        client.setDeleted(dto.getDeleted());
        client.setPassword(dto.getPassword());
        client.setLogin(dto.getLogin());
        client.setName(dto.getName());
        client.setPhone(dto.getPhone());

        return client;
    }
}
