package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import demo.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
