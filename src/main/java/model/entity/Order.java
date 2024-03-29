package model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ORDERS")
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "ORDER_ID")
  private Long id;

  @Temporal(TemporalType.TIMESTAMP)
  private Date orderDate; // 주문 날짜

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "DELIVERY_ID")
  private Delivery delivery;

  public void setMember(Member member) {
    // 기존 관계 제거
    if (this.member != null) {
      this.member.getOrders().remove(this);
    }
    this.member = member; // order -> member
    member.getOrders().add(this); // member -> order
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem); // order -> orderItem
    orderItem.setOrder(this);// orderItem -> order
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }
}