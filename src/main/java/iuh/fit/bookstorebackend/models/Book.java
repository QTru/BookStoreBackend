package iuh.fit.bookstorebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"authors", "categories", "publishers", "orderItems"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "cover_image_url", nullable = false)
    private String coverImageUrl;
    @Column(name = "page_count", nullable = false)
    private int pageCount;
    @Column(name = "description", nullable = false, length = 2000)
    private String description;
    @Column(name = "price", nullable = false, precision = 19, scale = 0)
    private BigDecimal price;
    @Version
    private Long version;
    @Column(name = "discount_percentage", nullable = false, precision = 19, scale = 2)
    private BigDecimal discountPercentage;
    @Column(name = "start_discount_date")
    private LocalDateTime startDiscountDate;
    @Column(name = "end_discount_date")
    private LocalDateTime endDiscountDate;
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;
    @ManyToMany
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonIgnoreProperties("books")
    @Builder.Default
    private Set<Author> authors = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("books")
    @Builder.Default
    private Set<Category> categories = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "books_publishers",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    @JsonIgnoreProperties("books")
    @Builder.Default
    private Set<Publisher> publishers = new HashSet<>();
    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties({"book", "items"})
    @Builder.Default
    private Set<OrderItem> orderItems = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }
}
