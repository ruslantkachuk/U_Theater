package com.epam.university.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.epam.university.dao.AuditoriumDao;
import com.epam.university.entity.Auditorium;
import com.epam.university.service.AuditoriumService;

public class AuditoriumServiceImpl implements AuditoriumService {
    @Resource
    private AuditoriumDao auditoriumDao;

    public List<Auditorium> getAuditoriums() {
        return auditoriumDao.getAll();
    }

    public Integer getSeatsNumber(final Auditorium auditorium) {
        return auditorium.getSeats();
    }

    public List<Integer> getVipSeats(final Auditorium auditorium) {
        return auditorium.getVipSeats();
    }
}
