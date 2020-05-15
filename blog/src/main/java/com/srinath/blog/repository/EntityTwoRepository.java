package com.srinath.blog.repository;

import com.srinath.blog.domain.EntityTwo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EntityTwo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityTwoRepository extends JpaRepository<EntityTwo, Long> {
}
