package org.commandmosaic.samples.springbootjpa.dto;

import org.commandmosaic.samples.springbootjpa.entities.Memo;

import java.util.Objects;


public class MemoDTO {

    public MemoDTO() {
        // empty constructor
    }

    public MemoDTO(Memo memo) {
        this.id = memo.getId();
        this.text = memo.getText();
    }

    private Long id;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        MemoDTO memoDTO = (MemoDTO) o;
        return Objects.equals(id, memoDTO.id) && Objects.equals(text, memoDTO.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "MemoDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
