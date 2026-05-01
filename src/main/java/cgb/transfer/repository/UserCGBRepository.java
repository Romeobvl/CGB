package cgb.transfer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cgb.transfer.entity.UserCGB;

@Repository
public interface UserCGBRepository extends JpaRepository<UserCGB, String> {
	
	Optional<UserCGB> findByUsername(String username);
}

