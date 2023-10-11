package com.example.backend.attachment.entity;

import com.example.backend.item.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "origin_name")
    private String originName;
    @Column(name = "store_name")
    private String storeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Attachment(String originName, String storeName, Item item) {
        this.originName = originName;
        this.storeName = storeName;
        this.item = item;
    }

    public String getStoreName(){
        return this.storeName;
    }

    public void setNotUse() {
        this.item = null;
    }
}
