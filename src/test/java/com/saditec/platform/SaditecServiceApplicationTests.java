package com.saditec.platform;

import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesTypeRepository;
import com.saditec.platform.security.auth.repository.IRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SaditecServiceApplicationTests {

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IEducationalAndRecreationalFacilitiesTypeRepository typeRepository;

	@Test
	void contextLoadsAndSeedsData() {
		assertThat(roleRepository.count()).isEqualTo(2);
		assertThat(typeRepository.count()).isEqualTo(3);
	}
}
