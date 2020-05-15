package com.srinath.blog.repository;

import com.srinath.blog.domain.EntityOne;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EntityOne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityOneRepository extends JpaRepository<EntityOne, Long> {
}
