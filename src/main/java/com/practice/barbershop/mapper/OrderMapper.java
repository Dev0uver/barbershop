package com.practice.barbershop.mapper;


import com.practice.barbershop.dto.OrderDto;
import com.practice.barbershop.model.Barber;
import com.practice.barbershop.model.Client;
import com.practice.barbershop.model.Order;

/** Convert OrderDto to Order and Order to OrderDto
 * @author David
 */
public class OrderMapper {
    /**
     * Convert Order to OrderDto
     * @param entity Order
     * @return OrderDto
     */
    public static OrderDto toDto(Order entity) {
        OrderDto order = new OrderDto();

        order.setId(entity.getId());
        order.setMark(entity.getMark());
        order.setDay(entity.getDay());
        order.setTime(entity.getTime());
        order.setPhone(entity.getPhone());
        order.setClientName(entity.getClientName());
        order.setBarber_id(entity.getBarber().getId());
        order.setClient_id(entity.getClient().getId());
        order.setPrice(entity.getPrice());

        return order;
    }
    /**
     * Convert OrderDto to Order
     * @param dto OrderDto
     * @return Order
     */
    public static Order toEntity(OrderDto dto) {
        Order order = new Order();

        order.setId(dto.getId());
        order.setDay(dto.getDay());
        order.setTime(dto.getTime());
        order.setMark(dto.getMark());
        order.setClientName(dto.getClientName());
        order.setPhone(dto.getPhone());
        order.setPrice(dto.getPrice());

        Client client = new Client();
        client.setId(dto.getClient_id());
        order.setClient(client);

        Barber barber = new Barber();
        barber.setId(dto.getBarber_id());
        order.setBarber(barber);

        return order;
    }
}
