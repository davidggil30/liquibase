package com.liquibase.liquibase;

    import java.io.Serializable;
import jakarta.persistence.*;
	@Table(name="ITEM")
    @Entity
    public class Item implements Serializable {
        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_SEQ")
        @SequenceGenerator(name="ITEM_SEQ", sequenceName = "ITEM_SEQ", allocationSize=1, initialValue = 0)
        @Column(name="id")
        private Integer id;
        @Column(name="name")
        private String name;

        public Item() {}
        
        public Item(String name) {
        	this.name = name;
        }
        
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }