package com.cinereviewapp.cinereview_api.repository;

import com.cinereviewapp.cinereview_api.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Long> {
}