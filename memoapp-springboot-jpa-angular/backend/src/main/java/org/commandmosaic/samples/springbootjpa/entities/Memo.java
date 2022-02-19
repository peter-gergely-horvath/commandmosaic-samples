package org.commandmosaic.samples.springbootjpa.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MEMOS")
public class Memo {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User owner;

    @Column
    private String text;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memo memo = (Memo) o;
        return Objects.equals(id, memo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Memo{" +
                "id=" + id +
                ", owner=" + owner +
                ", text='" + text + '\'' +
                '}';
    }
}
