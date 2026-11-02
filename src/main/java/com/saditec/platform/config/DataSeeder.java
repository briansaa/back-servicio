package com.saditec.platform.config;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.entity.TEducationalAndRecreationalFacilityHourEntity;
import com.saditec.platform.entity.TEducationalAndRecreationalFacilityTypeEntity;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesRepository;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesTypeRepository;
import com.saditec.platform.security.auth.entity.TRoleEntity;
import com.saditec.platform.security.auth.reference.EPermissionReference;
import com.saditec.platform.security.auth.repository.IRoleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

/**
 * Carga datos mínimos para poder usar la API apenas se levanta:
 * roles, tipos de espacio, espacios y bloques horarios.
 * Solo inserta si las tablas están vacías, así que es seguro reiniciar.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true")
public class DataSeeder implements ApplicationRunner {

    private static final String SEED_USER = "seed";

    private final IRoleRepository roleRepository;
    private final IEducationalAndRecreationalFacilitiesTypeRepository typeRepository;
    private final IEducationalAndRecreationalFacilitiesRepository facilityRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedRoles();
        seedFacilities();
    }

    private void seedRoles() {
        for (EPermissionReference reference : EPermissionReference.values()) {
            if (roleRepository.findByDescriptionIsIn(List.of(reference.name())).isEmpty()) {
                TRoleEntity role = new TRoleEntity();
                role.setDescription(reference.name());
                stamp(role);
                roleRepository.save(role);
                log.info("Rol creado: {}", reference.name());
            }
        }
    }

    private void seedFacilities() {
        if (typeRepository.count() > 0) return;

        List<TEducationalAndRecreationalFacilityHourEntity> hours = List.of(
                hour(8, 10), hour(10, 12), hour(14, 16), hour(16, 18)
        );

        createType("Laboratorios", "Laboratorios de cómputo y ciencias para prácticas.",
                List.of("Laboratorio de Cómputo 101", "Laboratorio de Redes 204"), "Pabellón A", hours);
        createType("Polideportivo", "Canchas y espacios deportivos del campus.",
                List.of("Cancha de Fútbol", "Cancha de Vóley"), "Polideportivo", hours);
        createType("Auditorios", "Espacios para charlas, exposiciones y eventos.",
                List.of("Auditorio Principal"), "Pabellón C", hours);

        log.info("Datos de ejemplo cargados: tipos, espacios y horarios");
    }

    private void createType(String name, String description, List<String> facilities, String location,
                            List<TEducationalAndRecreationalFacilityHourEntity> hours) {
        TEducationalAndRecreationalFacilityTypeEntity type = new TEducationalAndRecreationalFacilityTypeEntity();
        type.setName(name);
        type.setDescription(description);
        stamp(type);
        type = typeRepository.save(type);

        for (String facilityName : facilities) {
            TEducationalAndRecreationalFacilityEntity facility = new TEducationalAndRecreationalFacilityEntity();
            facility.setDescription(facilityName);
            facility.setLocation(location);
            facility.setRecreationalFacilityType(type);
            stamp(facility);
            facility = facilityRepository.save(facility);
            for (TEducationalAndRecreationalFacilityHourEntity hour : hours) {
                if (hour.getEducationalAndRecreationalFacilityEntities() == null) {
                    hour.setEducationalAndRecreationalFacilityEntities(new HashSet<>());
                }
                hour.getEducationalAndRecreationalFacilityEntities().add(facility);
            }
        }
        hours.forEach(entityManager::merge);
    }

    private TEducationalAndRecreationalFacilityHourEntity hour(int start, int end) {
        TEducationalAndRecreationalFacilityHourEntity hour = new TEducationalAndRecreationalFacilityHourEntity();
        hour.setStartTime(LocalTime.of(start, 0));
        hour.setEndTime(LocalTime.of(end, 0));
        hour.setEducationalAndRecreationalFacilityEntities(new HashSet<>());
        stamp(hour);
        entityManager.persist(hour);
        return hour;
    }

    private void stamp(com.saditec.platform.entity.TBaseEntity entity) {
        entity.setEnabled(true);
        entity.setCreatedBy(SEED_USER);
        entity.setUpdatedBy(SEED_USER);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
    }
}
