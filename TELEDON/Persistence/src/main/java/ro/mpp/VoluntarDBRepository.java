package ro.mpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.JdbcUtils;
//import ro.mpp.teledon.model.Voluntar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class VoluntarDBRepository implements IVoluntarRepository{
    private JdbcUtils dbUtils;
    private static final Logger logger= (Logger) LogManager.getLogger();
    public VoluntarDBRepository(Properties properties) {
        logger.info("Initializing VoluntarDBRepository with properties: {} ",properties);
        dbUtils = new JdbcUtils(properties);    }

    @Override
    public void add(Voluntar elem) {
        logger.traceEntry("Saving task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("insert into voluntar (nume, username, parola) values (?, ?, ?)"))
        {
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getUsername());
            preStmt.setString(3, elem.getParola());
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
    public void delete(Voluntar elem) {
        logger.traceEntry("Deleting task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("delete from voluntar where id =?"))
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
    public void update(Voluntar elem, Integer id) {
        logger.traceEntry("Updating VOLUNTAR with ID: {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE voluntar SET nume = ?, username = ?, parola = ? WHERE id = ?")) {
            preStmt.setString(1, elem.getNume());
            preStmt.setString(2, elem.getUsername());
            preStmt.setString(3, elem.getParola());
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
    public Voluntar findById(Integer id) {
        logger.traceEntry("Find by ID: {}", id);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("select * from voluntar where id=?"))
        {preStmt.setInt(1, id);
            try(ResultSet result=preStmt.executeQuery())
            {

                if (result.next())
                {
                    String nume=result.getString("nume");
                    String username=result.getString("username");
                    String parola=result.getString("parola");
                    Voluntar voluntar=new Voluntar(nume, username, parola);
                    voluntar.setId(id);
                    return voluntar;
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
    public Iterable<Voluntar> findAll() {
        logger.traceEntry("Find all");
        Connection con=dbUtils.getConnection();
        List<Voluntar> listaVoluntari=new ArrayList<>();
        try(PreparedStatement preStmt= con.prepareStatement("select * from voluntar"))
        {
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer id=result.getInt("id");
                    String nume=result.getString("nume");
                    String username=result.getString("username");
                    String parola=result.getString("parola");
                    Voluntar voluntar=new Voluntar(nume, username, parola);
                    voluntar.setId(id);
                    listaVoluntari.add(voluntar);
                }
            }
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }logger.traceExit(listaVoluntari);
        return listaVoluntari;
    }

    @Override
    public Collection<Voluntar> getAll() {
        logger.traceEntry("Get all");
        Connection con=dbUtils.getConnection();
        List<Voluntar> listaVoluntari=new ArrayList<>();
        try(PreparedStatement preStmt= con.prepareStatement("select * from voluntar"))
        {
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer id=result.getInt("id");
                    String nume=result.getString("nume");
                    String username=result.getString("username");
                    String parola=result.getString("parola");
                    Voluntar voluntar=new Voluntar(nume, username, parola);
                    voluntar.setId(id);
                    listaVoluntari.add(voluntar);
                }
            }
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }logger.traceExit(listaVoluntari);
        return listaVoluntari;
    }

    public Integer findIdByNumeUsernameParola(String nume, String username, String parola) {
        logger.traceEntry("Find id by Nume, Username, and Parola: {}, {}, {}", nume, username, parola);
        Integer id = null;
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT id FROM voluntar WHERE nume = ? AND username = ? AND parola = ?")) {
            preStmt.setString(1, nume);
            preStmt.setString(2, username);
            preStmt.setString(3, parola);
            ResultSet result = preStmt.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return id;
    }
    @Override
    public Voluntar findByUsernameParola(String username, String parola) {
        logger.traceEntry("Find by username and parssword: {}", username);

        Voluntar voluntar = null;
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM voluntar WHERE username = ? AND parola = ?")) {
            preStmt.setString(1, username);
            preStmt.setString(2, parola);

            ResultSet result = preStmt.executeQuery();
            if (result.next()) {
                Integer id = result.getInt("id");
                String nume = result.getString("nume");
                voluntar = new Voluntar(nume, username, parola);
                voluntar.setId(id);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return voluntar;
    }

}
