package com.practice.barbershop.general;

import lombok.Getter;

/**
 * This is enum class witch represents berber degree. Value type is double.
 * @author David
 * @see <a href="https://hostbarbershop.ru/blog/o-nas/kategorii-barberov/">Source</a>
 */
@Getter
public enum BarberDegree {
    JUNIOR_BARBER(1),
    BARBER(1.4),
    SENIOR_BARBER(1.8),
    TOP_BARBER(2.2);

    private final double extraCharge;

    BarberDegree(final double extraCharge) {
        this.extraCharge = extraCharge;
    }

}
