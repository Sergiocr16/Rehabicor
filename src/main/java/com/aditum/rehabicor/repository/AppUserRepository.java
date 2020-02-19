package com.aditum.rehabicor.repository;

import com.aditum.rehabicor.domain.AppUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AppUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("select app_user from AppUser app_user where app_user.user.login = ?#{principal.username}")
    List<AppUser> findByUserIsCurrentUser();

}
