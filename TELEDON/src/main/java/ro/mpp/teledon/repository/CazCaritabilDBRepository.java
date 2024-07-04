package ro.mpp.teledon.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.teledon.JdbcUtils;
import ro.mpp.teledon.model.CazCaritabil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class CazCaritabilDBRepository implements ICazCaritabilRepository{
    public JdbcUtils dbUtils;
    private static final Logger logger= (Logger) LogManager.getLogger();

    public CazCaritabilDBRepository(Properties properties) {
        logger.info("Initializing CazCaritabilDBRepository with properties: {} ",properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public void add(CazCaritabil elem) {
        logger.traceEntry("Saving task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("insert into caz_caritabil (nume, descriere, suma_stransa, suma_finala) values (?, ?, ?, ?)"))
        {
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getDescriere());
            preStmt.setLong(3, elem.getSumaStransa());
            preStmt.setLong(4, elem.getSumaFinala());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(CazCaritabil elem) {
        logger.traceEntry("Deleting task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("delete from caz_caritabil where id =?"))
        {
            preStmt.setLong(1, elem.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Deleted {} instances", result);
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(CazCaritabil elem, Integer id) {
        logger.traceEntry("Updating car with ID: {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE caz_caritabil SET nume = ?, descriere = ?, suma_stransa = ? , suma_finala= ? WHERE id = ?")) {
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getDescriere());
            preStmt.setInt(3, elem.getSumaStransa());
            preStmt.setInt(4, elem.getSumaFinala());
            preStmt.setInt(5, id);

            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public CazCaritabil findById(Integer id) {
        logger.traceEntry("Find by ID: {}", id);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("select * from caz_caritabil where id=?"))
        {
            preStmt.setInt(1, id);
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    String nume=result.getString("nume");
                    String descriere=result.getString("descriere");
                    Integer suma_stransa=result.getInt("suma_stransa");
                    Integer suma_finala=result.getInt("suma_finala");
                    CazCaritabil cazCaritabil=new CazCaritabil(nume, descriere, suma_stransa, suma_finala);
                    cazCaritabil.setId(id);
                    return cazCaritabil;
                }
            }
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<CazCaritabil> findAll() {
        logger.traceEntry("Find all");
        Connection con=dbUtils.getConnection();
        List<CazCaritabil> listaCazuriCaritabile=new ArrayList<>();
        try(PreparedStatement preStmt= con.prepareStatement("select * from caz_caritabil"))
        {
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer id=result.getInt("id");
                    String nume=result.getString("nume");
                    String descriere=result.getString("descriere");
                    Integer suma_stransa=result.getInt("suma_stransa");
                    Integer suma_finala=result.getInt("suma_finala");
                    CazCaritabil cazCaritabil=new CazCaritabil(nume, descriere, suma_stransa, suma_finala);
                    cazCaritabil.setId(id);
                    listaCazuriCaritabile.add(cazCaritabil);
                }
            }
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }logger.traceExit(listaCazuriCaritabile);
        return listaCazuriCaritabile;
    }

    @Override
    public Collection<CazCaritabil> getAll() {
        logger.traceEntry("Get all");
        Connection con=dbUtils.getConnection();
        List<CazCaritabil> listaCazuriCaritabile=new ArrayList<>();
        try(PreparedStatement preStmt= con.prepareStatement("select * from caz_caritabil"))
        {
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer id=result.getInt("id");
                    String nume=result.getString("nume");
                    String descriere=result.getString("descriere");
                    Integer suma_stransa=result.getInt("suma_stransa");
                    Integer suma_finala=result.getInt("suma_finala");
                    CazCaritabil cazCaritabil=new CazCaritabil(nume, descriere, suma_stransa, suma_finala);
                    cazCaritabil.setId(id);
                    listaCazuriCaritabile.add(cazCaritabil);
                }
            }
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }logger.traceExit(listaCazuriCaritabile);
        return listaCazuriCaritabile;
    }

    @Override
    public Integer findIdByNumeDescriereSuma(String nume, String descriere, Integer sumaDonata, Integer sumaFinala) {
        logger.traceEntry("Find ID by nume, descriere, sumaDonata, sumaFinala: {}, {}, {}, {}", nume, descriere, sumaDonata, sumaFinala);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT id FROM caz_caritabil WHERE nume = ? AND descriere = ? AND suma_stransa = ? AND suma_finala = ?")) {
            preStmt.setString(1, nume);
            preStmt.setString(2, descriere);
            preStmt.setInt(3, sumaDonata);
            preStmt.setInt(4, sumaFinala);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    logger.traceExit("Found ID: {}", id);
                    return id;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit("ID not found");
        return null;
    }

}
