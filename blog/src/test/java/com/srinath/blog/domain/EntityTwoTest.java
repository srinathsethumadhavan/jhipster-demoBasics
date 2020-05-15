package com.srinath.blog.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.srinath.blog.web.rest.TestUtil;

public class EntityTwoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityTwo.class);
        EntityTwo entityTwo1 = new EntityTwo();
        entityTwo1.setId(1L);
        EntityTwo entityTwo2 = new EntityTwo();
        entityTwo2.setId(entityTwo1.getId());
        assertThat(entityTwo1).isEqualTo(entityTwo2);
        entityTwo2.setId(2L);
        assertThat(entityTwo1).isNotEqualTo(entityTwo2);
        entityTwo1.setId(null);
        assertThat(entityTwo1).isNotEqualTo(entityTwo2);
    }
}
