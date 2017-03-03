package com.epam.university.service;

import java.util.List;

import com.epam.university.entity.Auditorium;

public interface AuditoriumService {
    List<Auditorium> getAuditoriums();

    Integer getSeatsNumber(Auditorium auditorium);

    List<Integer> getVipSeats(Auditorium auditorium);
}
