package com.liquibase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
    @Table(name="HOUSE")
    @Entity
    public class House implements Serializable {
        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HOUSE_SEQ")
        @SequenceGenerator(name="HOUSE_SEQ", sequenceName = "HOUSE_SEQ", allocationSize=1, initialValue = 0)
        @Column(name="id")
        private Integer id;
        @Column(name="owner")
        private String owner;
        @Column(name = "fullypaid")
        private Boolean fullypaid;
        
//        @JoinColumn(name="ITEM", referencedColumnName = "ID", nullable=false)
        @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="house")
        private List<Item> items = new ArrayList<>();

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}
        
		public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public boolean getFullypaid() {
            return fullypaid;
        }

        public void setFullypaid(boolean fullypaid) {
            this.fullypaid = fullypaid;
        }

    }