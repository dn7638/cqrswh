package cqrswh.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import cqrswh.DeliveryApplication;
import cqrswh.domain.DeliveryStarted;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Delivery_table")
@Data
//<<< DDD / Aggregate Root
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    private String customerId;

    private Integer quantity;

    private Long orderId;

    @PostPersist
    public void onPostPersist() {
        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }

    public static DeliveryRepository repository() {
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(
            DeliveryRepository.class
        );
        return deliveryRepository;
    }

    //<<< Clean Arch / Port Method
    public static void addToDeliveryList(OrderPlaced orderPlaced) {
        //implement business logic here:
    Delivery delivery = new Delivery();
    delivery.setAddress(orderPlaced.getAddress());
    delivery.setQuantity(orderPlaced.getQty());
    delivery.setCustomerId(orderPlaced.getCustomerId());
    delivery.setOrderId(orderPlaced.getId());
    repository().save(delivery);

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
