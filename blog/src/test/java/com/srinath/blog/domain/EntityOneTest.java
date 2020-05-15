package com.srinath.blog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.srinath.blog.web.rest.TestUtil;

public class EntityOneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityOne.class);
        EntityOne entityOne1 = new EntityOne();
        entityOne1.setId(1L);
        EntityOne entityOne2 = new EntityOne();
        entityOne2.setId(entityOne1.getId());
        assertThat(entityOne1).isEqualTo(entityOne2);
        entityOne2.setId(2L);
        assertThat(entityOne1).isNotEqualTo(entityOne2);
        entityOne1.setId(null);
        assertThat(entityOne1).isNotEqualTo(entityOne2);
    }
}
