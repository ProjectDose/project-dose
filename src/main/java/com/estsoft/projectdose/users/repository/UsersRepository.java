package com.estsoft.projectdose.users.repository;

import com.estsoft.projectdose.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);
	Optional<Users> findByNickname(String nickname);
	Optional<Users> findByResetToken(String resetToken);
}
