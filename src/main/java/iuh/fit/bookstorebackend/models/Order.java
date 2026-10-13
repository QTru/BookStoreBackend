package iuh.fit.bookstorebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import iuh.fit.bookstorebackend.models.enums.PaymentMethod;
import iuh.fit.bookstorebackend.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"account", "items"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Setter(AccessLevel.NONE)
    @Column(name = "subtotal", nullable = false, precision = 19, scale = 0)
    private BigDecimal subtotal;
    @Setter(AccessLevel.NONE)
    @Column(name = "discount", nullable = false, precision = 19, scale = 0)
    private BigDecimal discount;
    @Setter(AccessLevel.NONE)
    @Column(name = "total", nullable = false, precision = 19, scale = 0)
    private BigDecimal total;
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnoreProperties("orders")
    private Account account;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"order", "orderItems"})
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();

        BigDecimal newSubtotal = BigDecimal.ZERO;
        BigDecimal rawDiscount = BigDecimal.ZERO;

        for (OrderItem item : items) {
            item.setOrder(this);
            item.calculateAmount();
            newSubtotal = newSubtotal.add(item.getAmount());
            rawDiscount = rawDiscount.add(item.getAmount().multiply(item.getDiscountPercentage()));
        }

        this.subtotal = newSubtotal;
        this.discount = rawDiscount.divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        this.total = this.subtotal.subtract(this.discount);
    }
}
