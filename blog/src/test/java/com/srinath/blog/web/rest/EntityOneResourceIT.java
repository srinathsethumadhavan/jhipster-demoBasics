package com.srinath.blog.web.rest;

import com.srinath.blog.BlogApp;
import com.srinath.blog.domain.EntityOne;
import com.srinath.blog.repository.EntityOneRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EntityOneResource} REST controller.
 */
@SpringBootTest(classes = BlogApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EntityOneResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EntityOneRepository entityOneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntityOneMockMvc;

    private EntityOne entityOne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityOne createEntity(EntityManager em) {
        EntityOne entityOne = new EntityOne()
            .name(DEFAULT_NAME);
        return entityOne;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityOne createUpdatedEntity(EntityManager em) {
        EntityOne entityOne = new EntityOne()
            .name(UPDATED_NAME);
        return entityOne;
    }

    @BeforeEach
    public void initTest() {
        entityOne = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntityOne() throws Exception {
        int databaseSizeBeforeCreate = entityOneRepository.findAll().size();

        // Create the EntityOne
        restEntityOneMockMvc.perform(post("/api/entity-ones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entityOne)))
            .andExpect(status().isCreated());

        // Validate the EntityOne in the database
        List<EntityOne> entityOneList = entityOneRepository.findAll();
        assertThat(entityOneList).hasSize(databaseSizeBeforeCreate + 1);
        EntityOne testEntityOne = entityOneList.get(entityOneList.size() - 1);
        assertThat(testEntityOne.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEntityOneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entityOneRepository.findAll().size();

        // Create the EntityOne with an existing ID
        entityOne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityOneMockMvc.perform(post("/api/entity-ones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entityOne)))
            .andExpect(status().isBadRequest());

        // Validate the EntityOne in the database
        List<EntityOne> entityOneList = entityOneRepository.findAll();
        assertThat(entityOneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEntityOnes() throws Exception {
        // Initialize the database
        entityOneRepository.saveAndFlush(entityOne);

        // Get all the entityOneList
        restEntityOneMockMvc.perform(get("/api/entity-ones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityOne.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getEntityOne() throws Exception {
        // Initialize the database
        entityOneRepository.saveAndFlush(entityOne);

        // Get the entityOne
        restEntityOneMockMvc.perform(get("/api/entity-ones/{id}", entityOne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entityOne.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingEntityOne() throws Exception {
        // Get the entityOne
        restEntityOneMockMvc.perform(get("/api/entity-ones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntityOne() throws Exception {
        // Initialize the database
        entityOneRepository.saveAndFlush(entityOne);

        int databaseSizeBeforeUpdate = entityOneRepository.findAll().size();

        // Update the entityOne
        EntityOne updatedEntityOne = entityOneRepository.findById(entityOne.getId()).get();
        // Disconnect from session so that the updates on updatedEntityOne are not directly saved in db
        em.detach(updatedEntityOne);
        updatedEntityOne
            .name(UPDATED_NAME);

        restEntityOneMockMvc.perform(put("/api/entity-ones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntityOne)))
            .andExpect(status().isOk());

        // Validate the EntityOne in the database
        List<EntityOne> entityOneList = entityOneRepository.findAll();
        assertThat(entityOneList).hasSize(databaseSizeBeforeUpdate);
        EntityOne testEntityOne = entityOneList.get(entityOneList.size() - 1);
        assertThat(testEntityOne.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEntityOne() throws Exception {
        int databaseSizeBeforeUpdate = entityOneRepository.findAll().size();

        // Create the EntityOne

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityOneMockMvc.perform(put("/api/entity-ones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entityOne)))
            .andExpect(status().isBadRequest());

        // Validate the EntityOne in the database
        List<EntityOne> entityOneList = entityOneRepository.findAll();
        assertThat(entityOneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntityOne() throws Exception {
        // Initialize the database
        entityOneRepository.saveAndFlush(entityOne);

        int databaseSizeBeforeDelete = entityOneRepository.findAll().size();

        // Delete the entityOne
        restEntityOneMockMvc.perform(delete("/api/entity-ones/{id}", entityOne.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntityOne> entityOneList = entityOneRepository.findAll();
        assertThat(entityOneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
