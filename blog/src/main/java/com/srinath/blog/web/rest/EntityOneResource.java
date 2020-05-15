package com.srinath.blog.web.rest;

import com.srinath.blog.domain.EntityOne;
import com.srinath.blog.repository.EntityOneRepository;
import com.srinath.blog.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.srinath.blog.domain.EntityOne}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntityOneResource {

    private final Logger log = LoggerFactory.getLogger(EntityOneResource.class);

    private static final String ENTITY_NAME = "entityOne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityOneRepository entityOneRepository;

    public EntityOneResource(EntityOneRepository entityOneRepository) {
        this.entityOneRepository = entityOneRepository;
    }

    /**
     * {@code POST  /entity-ones} : Create a new entityOne.
     *
     * @param entityOne the entityOne to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityOne, or with status {@code 400 (Bad Request)} if the entityOne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-ones")
    public ResponseEntity<EntityOne> createEntityOne(@RequestBody EntityOne entityOne) throws URISyntaxException {
        log.debug("REST request to save EntityOne : {}", entityOne);
        if (entityOne.getId() != null) {
            throw new BadRequestAlertException("A new entityOne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityOne result = entityOneRepository.save(entityOne);
        return ResponseEntity.created(new URI("/api/entity-ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-ones} : Updates an existing entityOne.
     *
     * @param entityOne the entityOne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityOne,
     * or with status {@code 400 (Bad Request)} if the entityOne is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityOne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-ones")
    public ResponseEntity<EntityOne> updateEntityOne(@RequestBody EntityOne entityOne) throws URISyntaxException {
        log.debug("REST request to update EntityOne : {}", entityOne);
        if (entityOne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntityOne result = entityOneRepository.save(entityOne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityOne.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entity-ones} : get all the entityOnes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityOnes in body.
     */
    @GetMapping("/entity-ones")
    public ResponseEntity<List<EntityOne>> getAllEntityOnes(Pageable pageable) {
        log.debug("REST request to get a page of EntityOnes");
        Page<EntityOne> page = entityOneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entity-ones/:id} : get the "id" entityOne.
     *
     * @param id the id of the entityOne to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityOne, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-ones/{id}")
    public ResponseEntity<EntityOne> getEntityOne(@PathVariable Long id) {
        log.debug("REST request to get EntityOne : {}", id);
        Optional<EntityOne> entityOne = entityOneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entityOne);
    }

    /**
     * {@code DELETE  /entity-ones/:id} : delete the "id" entityOne.
     *
     * @param id the id of the entityOne to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-ones/{id}")
    public ResponseEntity<Void> deleteEntityOne(@PathVariable Long id) {
        log.debug("REST request to delete EntityOne : {}", id);
        entityOneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
