package com.ecomerse.merchantmode.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesId")
    private Long salesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @Column(name = "totalSales", nullable = false)
    private BigDecimal totalSales;

    @Column(name = "totalItemSold")
    private Integer totalItemSold;

    @Column(name = "periodStart", nullable = false)
    private Instant periodStart;

    @Column(name = "periodEnd", nullable = false)
    private Instant periodEnd;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesItem> salesItems = new ArrayList<>();

    public Sales() {}

    public Sales(Store store, BigDecimal totalSales, Integer totalItemSold, Instant periodStart, Instant periodEnd) {
        this.store = store;
        this.totalSales = totalSales;
        this.totalItemSold = totalItemSold;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public Long getSalesId() {
        return salesId;
    }
    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }
    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }
    public BigDecimal getTotalSales() {
        return totalSales;
    }
    public void setTotalSales(BigDecimal totalSales) {
        if(totalSales.signum() < 0) throw new IllegalArgumentException("Total sales cannot be negative");
        this.totalSales = totalSales;
    }
    public Integer getTotalItemSold() {
        return totalItemSold;
    }
    public void setTotalItemSold(Integer totalItemSold) {
        if(totalItemSold < 0) throw new IllegalArgumentException("Total item sold cannot be negative");
        this.totalItemSold = totalItemSold;
    }
    public Instant getPeriodStart() {
        return periodStart;
    }
    public void setPeriodStart(Instant periodStart) {
        this.periodStart = periodStart;
    }
    public Instant getPeriodEnd() {
        return periodEnd;
    }
    public void setPeriodEnd(Instant periodEnd) {
        this.periodEnd = periodEnd;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public List<SalesItem> getSalesItems() {
        return salesItems;
    }
    public void setSalesItems(List<SalesItem> salesItems) {
        this.salesItems = salesItems;
    }

    public void addSalesItem(SalesItem salesItem) {
        this.salesItems.add(salesItem);
        salesItem.setSales(this);
    }
    public void removeSalesItem(SalesItem salesItem) {
        this.salesItems.remove(salesItem);
        salesItem.setSales(null);
    }

}
