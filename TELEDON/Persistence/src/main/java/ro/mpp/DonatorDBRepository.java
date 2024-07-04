package ro.mpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class DonatorDBRepository implements IDonatorRepository{
    private JdbcUtils dbUtils;
    private static final Logger logger= (Logger) LogManager.getLogger();

    public DonatorDBRepository(Properties properties) {
        logger.info("Initializing DonatorDBRepository with properties: {} ",properties);
        dbUtils = new JdbcUtils(properties);    }

    @Override
    public void add(Donator elem) {
        logger.traceEntry("Saving task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("insert into donator (nume, adresa, telefon) values (?, ?, ?)"))
        {
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getAdresa());
            preStmt.setString(3, elem.getTelefon());
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
    public void delete(Donator elem) {
        logger.traceEntry("Deleting task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("delete from donator where id =?"))
        {
            preStmt.setInt(1, elem.getId());
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
    public void update(Donator elem, Integer id) {
        logger.traceEntry("Updating DONATOR with ID: {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE donator SET nume = ?, adresa = ?, telefon = ? WHERE id = ?")) {
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getAdresa());
            preStmt.setString(3, elem.getTelefon());
            preStmt.setInt(4, id);
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public Donator findById(Integer id) {
        logger.traceEntry("Find by ID: {}", id);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("select * from donator where id=?"))
        {
            preStmt.setInt(1, id);
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    String nume=result.getString("nume");
                    String adresa=result.getString("adresa");
                    String telefon=result.getString("telefon");
                    Donator donator=new Donator(nume, adresa, telefon);
                    donator.setId(id);
                    return donator;
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
    public Iterable<Donator> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Donator> listaDonatori=new ArrayList<>();
        try(PreparedStatement preStmt= con.prepareStatement("select * from donator"))
        {
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer id=result.getInt("id");
                    String nume=result.getString("nume");
                    String adresa=result.getString("adresa");
                    String telefon=result.getString("telefon");
                    Donator donator=new Donator(nume, adresa, telefon);
                    donator.setId(id);
                    listaDonatori.add(donator);
                }
            }
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }logger.traceExit(listaDonatori);
        return listaDonatori;
    }

    @Override
    public Collection<Donator> getAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Donator> listaDonatori=new ArrayList<>();
        try(PreparedStatement preStmt= con.prepareStatement("select * from donator"))
        {
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer id=result.getInt("id");
                    String nume=result.getString("nume");
                    String adresa=result.getString("adresa");
                    String telefon=result.getString("telefon");
                    Donator donator=new Donator(nume, adresa, telefon);
                    donator.setId(id);
                    listaDonatori.add(donator);
                }
            }
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }logger.traceExit(listaDonatori);
        return listaDonatori;
    }

    @Override
    public Donator findDonatorByName(String nume) {
        logger.traceEntry("Find Donator by Name: {}", nume);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM donator WHERE nume=?")) {
            preStmt.setString(1, nume);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Integer id = result.getInt("id");
                    String adresa = result.getString("adresa");
                    String telefon = result.getString("telefon");
                    Donator donator = new Donator(nume, adresa, telefon);
                    donator.setId(id);
                    return donator;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit("Donator not found");
        return null;
    }


    @Override
    public Integer findIdByNumeAdresaTelefon(String nume, String adresa, String telefon) {
        logger.traceEntry("Find ID by Nume, Adresa, Telefon: {}, {}, {}", nume, adresa, telefon);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT id FROM donator WHERE nume = ? AND adresa = ? AND telefon = ?")) {
            preStmt.setString(1, nume);
            preStmt.setString(2, adresa);
            preStmt.setString(3, telefon);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Integer id = result.getInt("id");
                    logger.traceExit(id);
                    return id;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit("Donator not found");
        return null;
    }

}
