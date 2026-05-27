package com.la_guarida.hecate_backend.adapters.implementations;

import com.la_guarida.hecate_backend.adapters.interfaces.IReservationAdapter;
import com.la_guarida.hecate_backend.dtos.ReservationRequest;
import com.la_guarida.hecate_backend.models.ReservationModel;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationAdapterJDBC implements IReservationAdapter {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationModel> reservationRowMapper = (rs, nowNum) -> {
        ReservationModel reservation = new ReservationModel();
        reservation.setReservationId(rs.getLong("reservation_id"));
        reservation.setUserId(rs.getLong("user_id"));
        reservation.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        reservation.setServiceName(rs.getString("service_name"));
        reservation.setStatus(rs.getString("status"));

        return reservation;
    };

    public ReservationAdapterJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationModel createReservation(ReservationModel reservation) {
        jdbcTemplate.update(
                "INSERT INTO reservation (user_id, date_time, service_name, status) VALUES (?, ?, ?, ?)",
                reservation.getUserId(), reservation.getDateTime(), reservation.getServiceName(), reservation.getStatus());
        return reservation;
    }

    @Override
    public List<ReservationModel> findAllByUserId(Long userId) {
        return this.jdbcTemplate.query(
                "SELECT r.* FROM reservation r WHERE r.user_id = ?",
                reservationRowMapper, userId
        );
    }

    @Override
    public List<ReservationModel> findByFilters(Long userId, LocalDateTime dateTime, String serviceName, String status) {
        StringBuilder sql = new StringBuilder("SELECT * FROM reservation WHERE 1 = 1");
        List<Object> params = new ArrayList<>();

        if (userId != null) {
            sql.append(" AND user_id = ?");
            params.add(userId);
        }

        if (dateTime != null) {
            sql.append(" AND date_time = ?");
            params.add(dateTime);
        }

        if (serviceName != null && !serviceName.isEmpty()) {
            sql.append(" AND service_name = ?");
            params.add(serviceName);
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status);
        }

        return this.jdbcTemplate.query(sql.toString(), reservationRowMapper, params.toArray());
    }

    @Override
    @Transactional
    public void updateReservation(Long reservationId, ReservationRequest newData) {
        this.jdbcTemplate.update(
                "UPDATE reservation SET date_time = ?, service_name = ? WHERE reservation_id = ?",
                newData.getDateTime(), newData.getServiceName(), reservationId);
    }

    @Override
    @Transactional
    public void deleteReservation(Long reservationId) {
        this.jdbcTemplate.update(
                "DELETE FROM reservation WHERE reservation_id = ?",
                reservationId
        );
    }

    @Override
    public Optional<ReservationModel> findById(Long reservationId) {
        List<ReservationModel> results = jdbcTemplate.query(
                "SELECT * FROM reservation WHERE reservation_id = ?",
                reservationRowMapper, reservationId);
        return results.stream().findFirst();
    }

    @Override
    public boolean existsByUserIdAndDateTime(Long userId, LocalDateTime dateTime) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE user_id = ? AND date_time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, dateTime);
        return count != null && count > 0;
    }
}
