package com.la_guarida.hecate_backend.adapters.implementations;

import com.la_guarida.hecate_backend.adapters.interfaces.IUserAdapter;
import com.la_guarida.hecate_backend.models.UserModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserAdapterJPA implements IUserAdapter {
    @PersistenceContext
    private final EntityManager entityManager;

    public UserAdapterJPA(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UserModel> findById(Long id) {
        UserModel user = entityManager.find(UserModel.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public boolean findIfExistsByEmail(String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) FROM UserModel u WHERE u.email = :email", Long.class
        ).setParameter("email", email).getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional
    public UserModel createUser(UserModel userModel) {
        entityManager.persist(userModel);
        return userModel;
    }

    @Override
    @Transactional
    public UserModel updateUser(UserModel userModel) {
        return entityManager.merge(userModel);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        UserModel user = entityManager.find(UserModel.class, userId);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<UserModel> getAllUsers() {
        return entityManager.createQuery(
                "SELECT u FROM UserModel u", UserModel.class)
                .getResultList();
    }

    @Override
    public List<UserModel> filterByName(String name) {
        return entityManager.createQuery(
                "SELECT u FROM UserModel u WHERE u.fullName LIKE :name", UserModel.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        List<UserModel> users = entityManager.createQuery(
                "SELECT u FROM UserModel u WHERE u.email = :email", UserModel.class
        ).setParameter("email", email).getResultList();
        if (users.isEmpty()) {
            return Optional.empty();
        } else {
        return Optional.of(users.getFirst());
        }
    }
}
