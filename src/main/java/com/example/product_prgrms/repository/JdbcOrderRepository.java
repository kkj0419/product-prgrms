package com.example.product_prgrms.repository;

import com.example.product_prgrms.exception.OrderInsertException;
import com.example.product_prgrms.model.Email;
import com.example.product_prgrms.model.Order;
import com.example.product_prgrms.model.OrderItem;
import com.example.product_prgrms.model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private static final Logger logger= LoggerFactory.getLogger(JdbcOrderRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Order insert(Order order) throws OrderInsertException {

        List<OrderItem> items = order.getOrderItems();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update("INSERT INTO orders(email, address, order_status, created_at, updated_at)"
                    + " values(:email, :address, :orderStatus, :createdAt, :updatedAt)", toOrderParamMap(order), keyHolder);

            order.generateKey(keyHolder.getKey().longValue());
            for (OrderItem item : items) {
                insertItem(item, order.getOrderId());
            }
        }catch(DataAccessException e){
            logger.error("ORDER INSERT ERROR : {}",e.getStackTrace());
            throw new OrderInsertException();
        }
        return order;
    }

    private void insertItem(OrderItem item, long orderId) {
        var orderItemSource = toOrderItemParamMap(item, orderId);
        jdbcTemplate.update("INSERT INTO order_items(order_id, product_id, price, quantity)"
                + " values(:orderId, :productId, :price, :quantity)", orderItemSource);
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query("SELECT * FROM orders", orderRowMapper);
    }

    @Override
    public Optional<Order> findById(long orderId) {

        Optional<Order> findOne;
        var items = findItemsByOrderId(orderId);
        try{
            findOne = Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM orders where order_id = :orderId",
                            Collections.singletonMap("orderId", orderId), orderRowMapper));
        }catch(EmptyResultDataAccessException e){
            logger.error("Got Empty result by id {} ", orderId);
            return Optional.empty();
        }
        findOne.get().addItems(items);
        return findOne;
    }

    private List<OrderItem> findItemsByOrderId(long orderId) {
        return jdbcTemplate.query("SELECT * FROM order_items where order_id = :orderId", Collections.singletonMap("orderId", orderId), orderItemRowMapper);
    }


    private SqlParameterSource toOrderParamMap(Order order) {
        var source = new MapSqlParameterSource();

        source.addValue("email", order.getEmail().toString());
        source.addValue("address", order.getAddress());
        source.addValue("orderStatus", order.getOrderStatus().toString());
        source.addValue("createdAt", Timestamp.valueOf(order.getCreatedAt()));
        source.addValue("updatedAt", Timestamp.valueOf(order.getUpdatedAt()));

        return source;
    }


    private SqlParameterSource toOrderItemParamMap(OrderItem item, long orderId) {
        var source = new MapSqlParameterSource();

        source.addValue("orderId", orderId);
        source.addValue("productId", item.productId());
        source.addValue("price", item.price());
        source.addValue("quantity", item.quantity());

        return source;
    }

    private static final RowMapper<Order> orderRowMapper = (resultSet, i) -> {

        long orderId = resultSet.getLong("order_id");
        String orderStatus = resultSet.getString("order_status");
        String email = resultSet.getString("email");
        String address = resultSet.getString("address");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();

        return new Order(orderId, new Email(email), address, OrderStatus.valueOf(orderStatus), createdAt, updatedAt, new ArrayList<>());
    };

    private static final RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) -> {
        long productId = resultSet.getLong("product_id");
        long price = resultSet.getLong("price");
        int quantity = resultSet.getInt("quantity");
        return new OrderItem(productId, price, quantity);
    };
}
