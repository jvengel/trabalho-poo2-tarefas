package com.example.application.views;

import com.example.application.dao.TarefaDAO;
import com.example.application.data.entity.TarefaDTO;
import com.example.application.views.main.TarefaBind;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Route("pendente")
public class GridProMainPendente extends Div {

    List<TarefaDTO> database;

    {
        try {
            database = new ArrayList<>(TarefaDAO.listarTarefasPendente());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public GridProMainPendente(){
        GridPro<TarefaDTO> grid = new GridPro<>();
        setColumns(grid);
        grid.setItems(database);

        grid.addCellEditStartedListener(tarefaClickChange -> {
            grid.addItemPropertyChangedListener(tarefaAfterChange -> {
                validaMudancas(tarefaClickChange.getItem(), tarefaAfterChange.getItem());
                grid.getDataProvider().refreshAll();
            });
        });

        add(grid);
    }

    private void setColumns(GridPro<TarefaDTO> grid) {
        Renderer<TarefaDTO> dataCriacaoRender = new TextRenderer<>(
                tarefa -> getDataCreation(tarefa)
                        .format(birthdayFormatter));

        grid.addEditColumn(TarefaDTO::getTitulo).text(TarefaDTO::setTitulo)
                .setHeader("Titulo");

        grid.addEditColumn(TarefaDTO::getDescricao).text(TarefaDTO::setDescricao)
                .setHeader("Descrição");


        List<String> categorias = getCategorias();

        List<String> status = getStatus();

        grid.addEditColumn(TarefaDTO::getStatus).select(TarefaDTO::setStatus, status)
                .setHeader("Status")
                .setSortable(true);

        grid.addEditColumn(TarefaDTO::getCategoria).select(TarefaDTO::setCategoria, categorias)
                .setHeader("Categoria")
                .setSortable(true);


        DatePicker datePicker = new DatePicker();
        datePicker.setWidthFull();

        grid.addEditColumn(GridProMainPendente::getDataCreation,
                        dataCriacaoRender)
                .custom(datePicker,
                        (tarefa, newValue) -> tarefa
                                .setDataCriacao(dateFromLocalDate(newValue)))
                .setHeader("Criação")
                .setSortable(true);

        grid.addColumn(new ComponentRenderer<>(Button::new, (button, tarefa) -> {
            button.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
            button.addClickListener(e -> {
                removerTarefa(tarefa);
                grid.getDataProvider().refreshAll();
                grid.setItems(database);
            });
            button.setIcon(new Icon(VaadinIcon.TRASH));
        }));

    }

    private void removerTarefa(TarefaDTO tarefa) {
        try {
            delete(tarefa);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final DateTimeFormatter birthdayFormatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.getDefault());

    private static LocalDate getDataCreation(TarefaDTO tarefaDTO) {
        return tarefaDTO.getDataCriacao();
    }

    public static LocalDate dateFromLocalDate(LocalDate localDate) {
        return localDate;
    }

    private static Boolean teveAlteracao(TarefaDTO beforeChangeClick, TarefaDTO afterChangeClick) {
        if (afterChangeClick.equals(beforeChangeClick)) {
            return true;
        } else {
            return false;
        }
    }

    Optional<TarefaDTO> find(Integer id) {
        return database.stream().filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    void delete(TarefaDTO item) throws SQLException {
        TarefaDAO.deletarTarefa(item.getId());
        database.removeIf(entity -> entity.getId().equals(item.getId()));
        enviaNotificacao("Tarefa removida com sucesso!", false);
    }

    void persist(TarefaDTO tarefaAfterChange) {
        try {
            if (tarefaAfterChange.getId()==null){
                tarefaAfterChange.setId(database.stream().map(TarefaDTO::getId).max(Comparator.naturalOrder())
                        .orElse(0) + 1);
            }

            final Optional<TarefaDTO> tarefaExist = find(tarefaAfterChange.getId());
            if (tarefaExist.isPresent()) {
                int position = database.indexOf(tarefaExist.get());
                database.remove(tarefaExist.get());
                database.add(position, tarefaAfterChange);
                TarefaDAO.atualizarTarefa(tarefaAfterChange);
                enviaNotificacao("Alteração salva com sucesso!", false);
            } else {
                database.add(tarefaAfterChange);
                TarefaDAO.adicionarTarefa(tarefaAfterChange);
                enviaNotificacao("Tarefa adicionada com sucesso!", false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void validaMudancas(TarefaDTO tarefaClickChange, TarefaDTO tarefaAfterChange){
        Boolean teveAlteracao = teveAlteracao(tarefaClickChange, tarefaAfterChange);
        if (teveAlteracao) {
            persist(tarefaAfterChange);
        }
    }

    public void enviaNotificacao(String mensagem, Boolean error) {
        Notification notification = Notification
                .show(mensagem);
        if (!error) {
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        notification.setPosition(Notification.Position.BOTTOM_END);
        notification.setDuration(3000);
    }

    private List<String> getCategorias(){
        return Arrays.asList("Reuniões", "Projetos", "Treinamento","Pesquisa","Manutenção",
                "Estudo","Lazer","Trabalho","Casa","Mercado","Outros");
    }

    private List<String> getStatus(){
        return Arrays.asList("Concluída","Pendente", "Cancelada", "Em andamento");
    }


}
