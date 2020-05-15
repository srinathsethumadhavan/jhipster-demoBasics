package com.srinath.blog.web.rest;

import com.srinath.blog.BlogApp;
import com.srinath.blog.domain.EntityTwo;
import com.srinath.blog.repository.EntityTwoRepository;

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
 * Integration tests for the {@link EntityTwoResource} REST controller.
 */
@SpringBootTest(classes = BlogApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EntityTwoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private EntityTwoRepository entityTwoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntityTwoMockMvc;

    private EntityTwo entityTwo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityTwo createEntity(EntityManager em) {
        EntityTwo entityTwo = new EntityTwo()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return entityTwo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityTwo createUpdatedEntity(EntityManager em) {
        EntityTwo entityTwo = new EntityTwo()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return entityTwo;
    }

    @BeforeEach
    public void initTest() {
        entityTwo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntityTwo() throws Exception {
        int databaseSizeBeforeCreate = entityTwoRepository.findAll().size();

        // Create the EntityTwo
        restEntityTwoMockMvc.perform(post("/api/entity-twos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entityTwo)))
            .andExpect(status().isCreated());

        // Validate the EntityTwo in the database
        List<EntityTwo> entityTwoList = entityTwoRepository.findAll();
        assertThat(entityTwoList).hasSize(databaseSizeBeforeCreate + 1);
        EntityTwo testEntityTwo = entityTwoList.get(entityTwoList.size() - 1);
        assertThat(testEntityTwo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEntityTwo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEntityTwo.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createEntityTwoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entityTwoRepository.findAll().size();

        // Create the EntityTwo with an existing ID
        entityTwo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityTwoMockMvc.perform(post("/api/entity-twos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entityTwo)))
            .andExpect(status().isBadRequest());

        // Validate the EntityTwo in the database
        List<EntityTwo> entityTwoList = entityTwoRepository.findAll();
        assertThat(entityTwoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEntityTwos() throws Exception {
        // Initialize the database
        entityTwoRepository.saveAndFlush(entityTwo);

        // Get all the entityTwoList
        restEntityTwoMockMvc.perform(get("/api/entity-twos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityTwo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getEntityTwo() throws Exception {
        // Initialize the database
        entityTwoRepository.saveAndFlush(entityTwo);

        // Get the entityTwo
        restEntityTwoMockMvc.perform(get("/api/entity-twos/{id}", entityTwo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entityTwo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingEntityTwo() throws Exception {
        // Get the entityTwo
        restEntityTwoMockMvc.perform(get("/api/entity-twos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntityTwo() throws Exception {
        // Initialize the database
        entityTwoRepository.saveAndFlush(entityTwo);

        int databaseSizeBeforeUpdate = entityTwoRepository.findAll().size();

        // Update the entityTwo
        EntityTwo updatedEntityTwo = entityTwoRepository.findById(entityTwo.getId()).get();
        // Disconnect from session so that the updates on updatedEntityTwo are not directly saved in db
        em.detach(updatedEntityTwo);
        updatedEntityTwo
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restEntityTwoMockMvc.perform(put("/api/entity-twos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntityTwo)))
            .andExpect(status().isOk());

        // Validate the EntityTwo in the database
        List<EntityTwo> entityTwoList = entityTwoRepository.findAll();
        assertThat(entityTwoList).hasSize(databaseSizeBeforeUpdate);
        EntityTwo testEntityTwo = entityTwoList.get(entityTwoList.size() - 1);
        assertThat(testEntityTwo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntityTwo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEntityTwo.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingEntityTwo() throws Exception {
        int databaseSizeBeforeUpdate = entityTwoRepository.findAll().size();

        // Create the EntityTwo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityTwoMockMvc.perform(put("/api/entity-twos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entityTwo)))
            .andExpect(status().isBadRequest());

        // Validate the EntityTwo in the database
        List<EntityTwo> entityTwoList = entityTwoRepository.findAll();
        assertThat(entityTwoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntityTwo() throws Exception {
        // Initialize the database
        entityTwoRepository.saveAndFlush(entityTwo);

        int databaseSizeBeforeDelete = entityTwoRepository.findAll().size();

        // Delete the entityTwo
        restEntityTwoMockMvc.perform(delete("/api/entity-twos/{id}", entityTwo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntityTwo> entityTwoList = entityTwoRepository.findAll();
        assertThat(entityTwoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
