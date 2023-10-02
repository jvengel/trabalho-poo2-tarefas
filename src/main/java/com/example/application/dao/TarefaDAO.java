package com.example.application.dao;

import com.example.application.data.database.ConnectDatabaseSQL;
import com.example.application.data.entity.TarefaDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {
    static Connection connection;

    static {
        try {
            connection = ConnectDatabaseSQL.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void adicionarTarefa(TarefaDTO tarefaDTO) throws SQLException {

        String sql = """
                INSERT INTO tarefas (titulo, descricao, categoria, dataCriacao, status)
                VALUES (?, ?, ?, ?, ?)
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, tarefaDTO.getTitulo());
            preparedStatement.setString(2, tarefaDTO.getDescricao());
            preparedStatement.setString(3, tarefaDTO.getCategoria());
            preparedStatement.setDate(4, Date.valueOf(tarefaDTO.getDataCriacao()));
            preparedStatement.setString(5, tarefaDTO.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public static List<TarefaDTO> listarTarefas() throws SQLException {
        List<TarefaDTO> tarefas = new ArrayList<>();
        ResultSet resultSet = null;
        String sql = """
                SELECT * FROM tarefas;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TarefaDTO tarefaDTO = new TarefaDTO();
                tarefaDTO.setId(resultSet.getInt("id"));
                tarefaDTO.setTitulo(resultSet.getString("titulo"));
                tarefaDTO.setDescricao(resultSet.getString("descricao"));
                tarefaDTO.setCategoria(resultSet.getString("categoria"));
                tarefaDTO.setDataCriacao(resultSet.getDate("dataCriacao").toLocalDate());
                tarefaDTO.setStatus(resultSet.getString("status"));
                tarefas.add(tarefaDTO);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return tarefas;
    }

    public static List<TarefaDTO> listarTarefasAndamento() throws SQLException {
        List<TarefaDTO> tarefas = new ArrayList<>();
        ResultSet resultSet = null;
        String sql = """
                SELECT * FROM tarefas where status = 'Em andamento';
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TarefaDTO tarefaDTO = new TarefaDTO();
                tarefaDTO.setId(resultSet.getInt("id"));
                tarefaDTO.setTitulo(resultSet.getString("titulo"));
                tarefaDTO.setDescricao(resultSet.getString("descricao"));
                tarefaDTO.setCategoria(resultSet.getString("categoria"));
                tarefaDTO.setDataCriacao(resultSet.getDate("dataCriacao").toLocalDate());
                tarefaDTO.setStatus(resultSet.getString("status"));
                tarefas.add(tarefaDTO);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return tarefas;
    }

    public static List<TarefaDTO> listarTarefasConcluida() throws SQLException {
        List<TarefaDTO> tarefas = new ArrayList<>();
        ResultSet resultSet = null;
        String sql = """
                SELECT * FROM tarefas where status = 'Concluída';
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TarefaDTO tarefaDTO = new TarefaDTO();
                tarefaDTO.setId(resultSet.getInt("id"));
                tarefaDTO.setTitulo(resultSet.getString("titulo"));
                tarefaDTO.setDescricao(resultSet.getString("descricao"));
                tarefaDTO.setCategoria(resultSet.getString("categoria"));
                tarefaDTO.setDataCriacao(resultSet.getDate("dataCriacao").toLocalDate());
                tarefaDTO.setStatus(resultSet.getString("status"));
                tarefas.add(tarefaDTO);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return tarefas;
    }

    public static List<TarefaDTO> listarTarefasPendente() throws SQLException {
        List<TarefaDTO> tarefas = new ArrayList<>();
        ResultSet resultSet = null;
        String sql = """
                SELECT * FROM tarefas where status = 'Pendente';
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TarefaDTO tarefaDTO = new TarefaDTO();
                tarefaDTO.setId(resultSet.getInt("id"));
                tarefaDTO.setTitulo(resultSet.getString("titulo"));
                tarefaDTO.setDescricao(resultSet.getString("descricao"));
                tarefaDTO.setCategoria(resultSet.getString("categoria"));
                tarefaDTO.setDataCriacao(resultSet.getDate("dataCriacao").toLocalDate());
                tarefaDTO.setStatus(resultSet.getString("status"));
                tarefas.add(tarefaDTO);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return tarefas;
    }

    public static TarefaDTO buscarTarefaPorId(Integer id) throws SQLException {
        ResultSet resultSet = null;
        String sql = """
                SELECT * FROM tarefas WHERE id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                TarefaDTO tarefaDTO = new TarefaDTO();
                tarefaDTO.setId(resultSet.getInt("id"));
                tarefaDTO.setTitulo(resultSet.getString("titulo"));
                tarefaDTO.setDescricao(resultSet.getString("descricao"));
                tarefaDTO.setCategoria(resultSet.getString("categoria"));
                tarefaDTO.setDataCriacao(resultSet.getDate("dataCriacao").toLocalDate());
                tarefaDTO.setStatus(resultSet.getString("status"));
                return tarefaDTO;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public static void atualizarTarefa(TarefaDTO tarefaDTO) throws SQLException {
        String sql = """
                UPDATE tarefas SET titulo = ?, descricao = ?, categoria = ?, dataCriacao = ?, status = ? WHERE id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, tarefaDTO.getTitulo());
            preparedStatement.setString(2, tarefaDTO.getDescricao());
            preparedStatement.setString(3, tarefaDTO.getCategoria());
            preparedStatement.setDate(4, Date.valueOf(tarefaDTO.getDataCriacao()));
            preparedStatement.setString(5, tarefaDTO.getStatus());
            preparedStatement.setInt(6, tarefaDTO.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public static void deletarTarefa(Integer id) throws SQLException {
        String sql = """
                DELETE FROM tarefas WHERE id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
