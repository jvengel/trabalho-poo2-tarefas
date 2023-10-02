package com.example.application.views.main;

import com.example.application.data.entity.TarefaDTO;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.Arrays;
import java.util.List;

public class TarefaBind extends FormLayout {
    private Binder<TarefaDTO> binder;

    public TarefaBind(boolean readOnly) {
        binder = new Binder<>(TarefaDTO.class);

        TextField titulo = new TextField();
        addFormItem(titulo, "Titulo");
        binder.forField(titulo)
                .asRequired()
                .bind(TarefaDTO::getTitulo, TarefaDTO::setTitulo);
        titulo.setReadOnly(readOnly);
        TextArea descricao = new TextArea();
        descricao.setMaxLength(255);
        descricao.setPlaceholder("Descrição da tarefa");
        descricao.setValueChangeMode(ValueChangeMode.EAGER);
        descricao.addValueChangeListener(event -> {
            event.getSource()
                    .setHelperText(event.getValue().length()+" / "+255);
        });
        addFormItem(descricao, "Descrição");
        binder.forField(descricao)
                .asRequired()
                .bind(TarefaDTO::getDescricao, TarefaDTO::setDescricao);
        descricao.setReadOnly(readOnly);
        ComboBox<String> categoria = new ComboBox<>();
        categoria.setItems(getCategorias());
        addFormItem(categoria, "Categoria");
        binder.forField(categoria)
                .asRequired()
                .bind(TarefaDTO::getCategoria, TarefaDTO::setCategoria);
        categoria.setReadOnly(readOnly);
        ComboBox<String> status = new ComboBox<>();
        status.setItems(getStatus());
        addFormItem(status, "Status");
        binder.forField(status)
                .asRequired()
                .bind(TarefaDTO::getStatus, TarefaDTO::setStatus);
        status.setReadOnly(readOnly);

        setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2),
                new ResponsiveStep("1000px", 3));
    }

    public Binder<TarefaDTO> getBinder() {
        return binder;
    }

    private List<String> getCategorias(){
        return Arrays.asList("Reuniões", "Projetos", "Treinamento","Pesquisa","Manutenção",
                "Estudo","Lazer","Trabalho","Casa","Mercado","Outros");
    }

    private List<String> getStatus(){
        return Arrays.asList("Concluída","Pendente", "Cancelada", "Em andamento");
    }
}
