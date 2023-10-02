package com.example.application.data.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class TarefaDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private String categoria;
    private LocalDate dataCriacao = LocalDate.now();
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarefaDTO tarefaDTO = (TarefaDTO) o;
        return Objects.equals(id, tarefaDTO.id) && Objects.equals(titulo, tarefaDTO.titulo) && Objects.equals(descricao, tarefaDTO.descricao) && Objects.equals(categoria, tarefaDTO.categoria) && Objects.equals(dataCriacao, tarefaDTO.dataCriacao) && Objects.equals(status, tarefaDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descricao, categoria, dataCriacao, status);
    }

    @Override
    public String toString() {
        return "TarefaDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", status='" + status + '\'' +
                '}';
    }
}
