package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int updateTransfer(Transfer transfer) {
       String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) \n" +
               "VALUES (1, 2, ?, ?, ?) RETURNING transfer_id;";
       int transferId = 0;
        try {
           transferId = jdbcTemplate.queryForObject(sql, int.class, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
        return transferId;
    }

    public void updateAccountFrom(BigDecimal amount, int id) {
        String sql = "UPDATE account\n" +
                "SET balance = balance - ?\n" +
                "WHERE account_id = ?;";
        try {
            jdbcTemplate.update(sql, amount, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    public void updateAccountTo(BigDecimal amount, int id) {
        String sql = "UPDATE account\n" +
                "SET balance = balance + ?\n" +
                "WHERE account_id = ?;";
        try {
            jdbcTemplate.update(sql, amount, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }
    @Override
    public int transfer(Account account, Transfer transfer) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Insufficient funds.");
        }
        int transferId = updateTransfer(transfer);
        updateAccountFrom(transfer.getAmount(), transfer.getAccountFrom());
        updateAccountTo(transfer.getAmount(), transfer.getAccountTo());

    return transferId;
    }
    @Override
    public List<Integer> getTransfers(int id) {
        List<Integer> transferIdList = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount\n" +
                "FROM transfer\n" +
                "JOIN account ON account.account_id = transfer.account_from\n" +
                "WHERE transfer.account_from = ?\n" +
                "OR transfer.account_to = ?\n" +
                "ORDER BY transfer_id;";
        try {
                SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, id);
                while (results.next()) {
                    transferIdList.add(mapRowToTransfer(results).getTransferId());
                }
            } catch(CannotGetJdbcConnectionException e){
                throw new DaoException("Unable to connect to server or database", e);
            } catch(DataIntegrityViolationException e){
                throw new DaoException("Data integrity violation", e);
            }catch(NullPointerException e){
                throw new NullPointerException();
            }
            return transferIdList;
        }


    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount\n" +
                "FROM transfer\n" +
                "WHERE transfer_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return transfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet t){
        Transfer transfer = new Transfer();
        transfer.setTransferId(t.getInt("transfer_id"));
        transfer.setTransferTypeId(t.getInt("transfer_type_id"));
        transfer.setTransferStatusId(t.getInt("transfer_status_id"));
        transfer.setAccountFrom(t.getInt("account_from"));
        transfer.setAccountTo(t.getInt("account_to"));
        transfer.setAmount(t.getBigDecimal("amount"));
        return transfer;
    }
}
