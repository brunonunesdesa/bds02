package com.devsuperior.bds02.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	EventRepository repository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		Optional<Event> obj = repository.findById(id);
		Event entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new EventDTO(entity);
	}

	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		
		Optional<City> obj = cityRepository.findById(dto.getCityId());
		City city = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		entity.setCity(city);
	}
	
}
