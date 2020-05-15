package com.srinath.blog.web.rest;

import com.srinath.blog.domain.EntityTwo;
import com.srinath.blog.repository.EntityTwoRepository;
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
 * REST controller for managing {@link com.srinath.blog.domain.EntityTwo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntityTwoResource {

    private final Logger log = LoggerFactory.getLogger(EntityTwoResource.class);

    private static final String ENTITY_NAME = "entityTwo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityTwoRepository entityTwoRepository;

    public EntityTwoResource(EntityTwoRepository entityTwoRepository) {
        this.entityTwoRepository = entityTwoRepository;
    }

    /**
     * {@code POST  /entity-twos} : Create a new entityTwo.
     *
     * @param entityTwo the entityTwo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityTwo, or with status {@code 400 (Bad Request)} if the entityTwo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-twos")
    public ResponseEntity<EntityTwo> createEntityTwo(@RequestBody EntityTwo entityTwo) throws URISyntaxException {
        log.debug("REST request to save EntityTwo : {}", entityTwo);
        if (entityTwo.getId() != null) {
            throw new BadRequestAlertException("A new entityTwo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityTwo result = entityTwoRepository.save(entityTwo);
        return ResponseEntity.created(new URI("/api/entity-twos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-twos} : Updates an existing entityTwo.
     *
     * @param entityTwo the entityTwo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityTwo,
     * or with status {@code 400 (Bad Request)} if the entityTwo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityTwo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-twos")
    public ResponseEntity<EntityTwo> updateEntityTwo(@RequestBody EntityTwo entityTwo) throws URISyntaxException {
        log.debug("REST request to update EntityTwo : {}", entityTwo);
        if (entityTwo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntityTwo result = entityTwoRepository.save(entityTwo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityTwo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entity-twos} : get all the entityTwos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityTwos in body.
     */
    @GetMapping("/entity-twos")
    public ResponseEntity<List<EntityTwo>> getAllEntityTwos(Pageable pageable) {
        log.debug("REST request to get a page of EntityTwos");
        Page<EntityTwo> page = entityTwoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entity-twos/:id} : get the "id" entityTwo.
     *
     * @param id the id of the entityTwo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityTwo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-twos/{id}")
    public ResponseEntity<EntityTwo> getEntityTwo(@PathVariable Long id) {
        log.debug("REST request to get EntityTwo : {}", id);
        Optional<EntityTwo> entityTwo = entityTwoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entityTwo);
    }

    /**
     * {@code DELETE  /entity-twos/:id} : delete the "id" entityTwo.
     *
     * @param id the id of the entityTwo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-twos/{id}")
    public ResponseEntity<Void> deleteEntityTwo(@PathVariable Long id) {
        log.debug("REST request to delete EntityTwo : {}", id);
        entityTwoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
